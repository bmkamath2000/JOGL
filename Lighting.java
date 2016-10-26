package basics;


import java.nio.FloatBuffer;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.FPSAnimator; 
import javax.swing.JFrame;


class Lighting implements GLEventListener {
	
	float angle=0;
	GLUT glut;
	FloatBuffer position;
	float[] pos={0,0,1,1};;
@Override
   public void display(GLAutoDrawable arg0) {
      // method body
	 //  float a[]={0,0,0,1,0,0,1,1,0,0,1,0};
	   glut=new GLUT();
	   
	   GL2 gl = arg0.getGL().getGL2();
	   gl.glClear(GL2.GL_COLOR_BUFFER_BIT|GL2.GL_DEPTH_BUFFER_BIT);
	   gl.glLoadIdentity();
	   //{ (float)(2*Math.cos(angle)),0,(float) (2*Math.sin(angle)),1};
	   position=FloatBuffer.allocate(4);
	   position.clear();
	   position.put((float) (2*Math.cos(angle)));
	   position.put(0);
	   position.put((float) (2*Math.sin(angle)));
	   position.put(1);
	   position.rewind();
	   gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, position);
	   gl.glTranslated(-1,0,0);
	  for(int i=0;i<8;i++)
	  {
	   gl.glTranslated(0.2,0,0);
	   gl.glMateriali(GL2.GL_FRONT_AND_BACK,GL2.GL_SHININESS,i*16);
	   glut.glutSolidSphere(0.1, 20, 20);
	  }
	  
	  angle+=0.001f;
	  
   }
	
   @Override
   public void dispose(GLAutoDrawable arg0) {
      //method body
   }
   private GLU glu;
   @Override
   public void init(GLAutoDrawable arg0) {
	   GL2 gl = arg0.getGL().getGL2();
	   float[] material={0f,1f,0,1}; 
	   glu = new GLU();

	    gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
	    gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);
        
        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK,GL2.GL_AMBIENT_AND_DIFFUSE,material,0);
        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK,GL2.GL_SPECULAR,new float[] {1.5f,0f,0f,1},0);
        
        
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
      Lighting b = new Lighting();
      glcanvas.addGLEventListener(b);        
      glcanvas.setSize( 400, 400 );
        
      //creating frame
      final JFrame frame = new JFrame (" Basic Frame");
        
      //adding canvas to frame
      frame.getContentPane().add( glcanvas ); 
      frame.setSize(frame.getContentPane().getPreferredSize());                 
      frame.setVisible( true ); 
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      final FPSAnimator fps=new FPSAnimator(glcanvas,300,true);
      fps.start();
   }
	
}
