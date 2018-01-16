package mortennobel;

import com.jogamp.opengl.util.FPSAnimator;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class SimpleShaderExample extends GLCanvas implements GLEventListener {

   private boolean updateUniformVars = true;
   private int vertexShaderProgram;
   private int fragmentShaderProgram;
   private int shaderprogram;
   private final FPSAnimator animator = new FPSAnimator(this,4,true);
   private float x = -2;
   private float y = -2;
   private float height = 4;
   private float width = 4;
   private int iterations = 255;

   public SimpleShaderExample() {
      addGLEventListener(this);
        animator.start();
    }

    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        // Enable VSync
        gl.setSwapInterval(1);
      gl.glShadeModel(GL2.GL_FLAT);
      try {
         attachShaders(gl);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   private String[] loadShaderSrc(String name){
      StringBuilder sb = new StringBuilder();
      try{
         InputStream is = getClass().getResourceAsStream(name);
         BufferedReader br = new BufferedReader(new InputStreamReader(is));
         String line;
         while ((line = br.readLine())!=null){
            sb.append(line);
            sb.append('\n');
         }
         is.close();
      }
      catch (Exception e){
         e.printStackTrace();
      }
      System.out.println("Shader is "+sb.toString());
      return new String[]{sb.toString()};
   }

   private void attachShaders(GL2 gl) throws Exception {
      vertexShaderProgram = gl.glCreateShader(GL2.GL_VERTEX_SHADER);
      fragmentShaderProgram = gl.glCreateShader(GL2.GL_FRAGMENT_SHADER);

      String[] vsrc = new String[]{"uniform float mandel_x;\n" +
            "uniform float mandel_y;\n" +
            "uniform float mandel_width;\n" +
            "uniform float mandel_height; \n" +
            "uniform float mandel_iterations;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "\tgl_TexCoord[0] = gl_MultiTexCoord0;\n" +
            "\tgl_Position = ftransform();\n" +
            "}"};
      gl.glShaderSource(vertexShaderProgram, 1, vsrc, null, 0);
      gl.glCompileShader(vertexShaderProgram);

      String[] fsrc = new String[]{"uniform float mandel_x;\n" +
            "uniform float mandel_y;\n" +
            "uniform float mandel_width;\n" +
            "uniform float mandel_height; \n" +
            "uniform float mandel_iterations;\n" +
            "\n" +
            "float calculateMandelbrotIterations(float x, float y) {\n" +
            "\tfloat xx = 0.0;\n" +
            "    float yy = 0.0;\n" +
            "    float iter = 0.0;\n" +
            "    while (xx * xx + yy * yy <= 4.0 && iter<mandel_iterations) {\n" +
            "        float temp = xx*xx - yy*yy + x;\n" +
            "        yy = 2.0*xx*yy + y;\n" +
            "\n" +
            "        xx = temp;\n" +
            "\n" +
            "        iter ++;\n" +
            "    }\n" +
            "    return iter;\n" +
            "}\n" +
            "\n" +
            "vec4 getColor(float iterations) {\n" +
            "\tfloat oneThirdMandelIterations = mandel_iterations/3.0;\n" +
            "\tfloat green = iterations/oneThirdMandelIterations;\n" +
            "\tfloat blue = (iterations-1.3*oneThirdMandelIterations)/oneThirdMandelIterations;\n" +
            "\tfloat red = (iterations-2.2*oneThirdMandelIterations)/oneThirdMandelIterations;\n" +
            "\treturn vec4(red,green,blue,1.0);\n" +
            "}\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "\tfloat x = mandel_x+gl_TexCoord[0].x*mandel_width;\n" +
            "\tfloat y = mandel_y+gl_TexCoord[0].y*mandel_height;\n" +
            "\tfloat iterations = calculateMandelbrotIterations(x,y);\n" +
            "\tgl_FragColor = getColor(iterations);\n" +
            "}"};
      gl.glShaderSource(fragmentShaderProgram, 1, fsrc, null, 0);
      gl.glCompileShader(fragmentShaderProgram);

      shaderprogram = gl.glCreateProgram();
      gl.glAttachShader(shaderprogram, vertexShaderProgram);
      gl.glAttachShader(shaderprogram, fragmentShaderProgram);
      gl.glLinkProgram(shaderprogram);
      gl.glValidateProgram(shaderprogram);
      IntBuffer intBuffer = IntBuffer.allocate(1);
      gl.glGetProgramiv(shaderprogram, GL2.GL_LINK_STATUS,intBuffer);
      if (intBuffer.get(0)!=1){
         gl.glGetProgramiv(shaderprogram, GL2.GL_INFO_LOG_LENGTH,intBuffer);
         int size = intBuffer.get(0);
         System.err.println("Program link error: ");
         if (size>0){
            ByteBuffer byteBuffer = ByteBuffer.allocate(size);
            gl.glGetProgramInfoLog(shaderprogram, size, intBuffer, byteBuffer);
            for (byte b:byteBuffer.array()){
               System.err.print((char)b);
            }
         } else {
            System.out.println("Unknown");
         }
         System.exit(1);
      }
      gl.glUseProgram(shaderprogram);
   }

   public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        GLU glu = new GLU();

        if (height <= 0) { // avoid a divide by zero error!

            height = 1;
        }
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluOrtho2D(0, 1, 0, 1);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }



    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

      if (updateUniformVars){
         updateUniformVars(gl);
      }

        // Reset the current matrix to the "identity"
        gl.glLoadIdentity();

        // Draw A Quad
        gl.glBegin(GL2.GL_QUADS);
      {
         gl.glTexCoord2f(0.0f, 1.0f);
         gl.glVertex3f(0.0f, 1.0f, 1.0f);  // Top Left
         gl.glTexCoord2f(1.0f, 1.0f);
         gl.glVertex3f(1.0f, 1.0f, 1.0f);   // Top Right
         gl.glTexCoord2f(1.0f, 0.0f);
         gl.glVertex3f(1.0f, 0.0f, 1.0f);  // Bottom Right
         gl.glTexCoord2f(0.0f, 0.0f);
         gl.glVertex3f(0.0f, 0.0f, 1.0f); // Bottom Left
      }
      // Done Drawing The Quad
        gl.glEnd();

        // Flush all drawing operations to the graphics card
        gl.glFlush();
    }

   private void updateUniformVars(GL2 gl) {
      int mandel_x = gl.glGetUniformLocation(shaderprogram, "mandel_x");
      int mandel_y = gl.glGetUniformLocation(shaderprogram, "mandel_y");
      int mandel_width = gl.glGetUniformLocation(shaderprogram, "mandel_width");
      int mandel_height = gl.glGetUniformLocation(shaderprogram, "mandel_height");
      int mandel_iterations = gl.glGetUniformLocation(shaderprogram, "mandel_iterations");
      assert(mandel_x!=-1);
      assert(mandel_y!=-1);
      assert(mandel_width!=-1);
      assert(mandel_height!=-1);
      assert(mandel_iterations!=-1);

      gl.glUniform1f(mandel_x, x);
      gl.glUniform1f(mandel_y, y);
      gl.glUniform1f(mandel_width, width);
      gl.glUniform1f(mandel_height, height);
      gl.glUniform1f(mandel_iterations, iterations);

   }

   public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

   public static void main(String[] args) {
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            JFrame jf = new JFrame();
            jf.setSize(800,800);
            jf.getContentPane().setLayout(new BorderLayout());
            jf.getContentPane().add(new SimpleShaderExample(), BorderLayout.CENTER);
            jf.setVisible(true);
         }
      });

   }

@Override
public void dispose(GLAutoDrawable arg0) {
	// TODO Auto-generated method stub
	
}
}