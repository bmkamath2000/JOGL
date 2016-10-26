package basics;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import javax.swing.JFrame;


class DisplayList implements GLEventListener {
	private int list;
	private void renderTriangle(GL2 gl, float size) {
	    gl.glBegin(GL2.GL_TRIANGLES);
	        gl.glVertex3f(-1*size, -1*size, 0);
	        gl.glVertex3f(1*size, -1*size, 0);
	        gl.glVertex3f(0*size, 1*size, 0);
	    gl.glEnd();
	}

	private void buildDisplayList(GL2 gl) {
	    list = gl.glGenLists(1);
	    gl.glNewList(list, GL2.GL_COMPILE);
	        renderTriangle(gl, 100);
	    gl.glEndList();
	}
   @Override
   public void display(GLAutoDrawable arg0) {
      // method body
	   GL2 gl = arg0.getGL().getGL2();
	   gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
	   gl.glColor3f(0, 1, 0);
	    //renderTriangle(gl, 100);
	    gl.glCallList(list);
   }
	
   @Override
   public void dispose(GLAutoDrawable arg0) {
      //method body
   }
   private GLU glu;
   @Override
   public void init(GLAutoDrawable arg0) {
	   GL2 gl = arg0.getGL().getGL2();
	    glu = new GLU();
	    
	    gl.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
	    buildDisplayList(gl);
   }
	
   @Override
   public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
	    GL2 gl = drawable.getGL().getGL2();
	    //GLU glu = GLU.createGLU(gl);

	    gl.glMatrixMode(GL2.GL_PROJECTION);
	    gl.glLoadIdentity();
	    glu.gluOrtho2D(-width/2, width/2, -height/2, height/2);
	    gl.glMatrixMode(GL2.GL_MODELVIEW);
	    gl.glLoadIdentity();
	    gl.glViewport(0, 0, width, height);
	}
   public static void main(String[] args) {
   
      //getting the capabilities object of GL2 profile
      final GLProfile profile = GLProfile.get(GLProfile.GL2);
      GLCapabilities capabilities = new GLCapabilities(profile);
        
      // The canvas
      final GLCanvas glcanvas = new GLCanvas(capabilities);
      DisplayList b = new DisplayList();
      glcanvas.addGLEventListener(b);        
      glcanvas.setSize(400, 400);
        
      //creating frame
      final JFrame frame = new JFrame (" Basic Frame");
        
      //adding canvas to frame
      frame.add(glcanvas);
      frame.setSize( 640, 480 );
      frame.setVisible(true);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }
	
}
