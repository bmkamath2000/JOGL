package basics;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import javax.swing.JFrame;


class BasicFrame implements GLEventListener {

   @Override
   public void display(GLAutoDrawable arg0) {
      // method body
	   GL2 gl = arg0.getGL().getGL2();
	   gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
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
   }
	
   @Override
   public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {
      // method body
   }
	
   public static void main(String[] args) {
   
      //getting the capabilities object of GL2 profile
      final GLProfile profile = GLProfile.get(GLProfile.GL2);
      GLCapabilities capabilities = new GLCapabilities(profile);
        
      // The canvas
      final GLCanvas glcanvas = new GLCanvas(capabilities);
      BasicFrame b = new BasicFrame();
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
