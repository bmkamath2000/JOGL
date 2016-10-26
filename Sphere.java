package basics;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

import javax.swing.JFrame;


class Sphere implements GLEventListener {

   @Override
   public void display(GLAutoDrawable arg0) {
      // method body
	   GL2 gl = arg0.getGL().getGL2();
	   GLUT glut=new GLUT();
	   gl.glClear(GL2.GL_COLOR_BUFFER_BIT|GL2.GL_DEPTH_BUFFER_BIT);
	   glut.glutSolidSphere(0.2, 20, 20);
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
	    gl.glEnable(GL2.GL_LIGHTING);
	    gl.glEnable(GL2.GL_LIGHT0);
	    float[]  bluish={ 0.3f , 0.3f , 1f , 1 };

	    gl.glLightfv(GL2.GL_LIGHT1,GL2.GL_DIFFUSE,bluish,0);
	    gl.glLightfv(GL2.GL_LIGHT1,GL2.GL_SPECULAR,bluish,0);
	    gl.glMaterialfv(GL2.GL_FRONT_AND_BACK,GL2.GL_AMBIENT_AND_DIFFUSE,bluish,0);

	    gl.glLightModeli(GL2.GL_LIGHT_MODEL_TWO_SIDE,GL2.GL_TRUE);
        gl.glEnable(GL2.GL_NORMALIZE);
        gl.glEnable(GL2.GL_DEPTH_TEST);
   
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
      Sphere b = new Sphere();
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
