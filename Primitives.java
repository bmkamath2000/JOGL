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


class Primitives implements GLEventListener {

   @Override
   public void display(GLAutoDrawable arg0) {
      // method body
	 //  float a[]={0,0,0,1,0,0,1,1,0,0,1,0};
	   GLUT glut=new GLUT();
	   GL2 gl = arg0.getGL().getGL2();
	   gl.glClear(GL2.GL_COLOR_BUFFER_BIT|GL2.GL_DEPTH_BUFFER_BIT);
	   gl.glLoadIdentity();
	   
	   gl.glBegin(GL2.GL_POLYGON);
	   gl.glNormal3d(0, 0, 1);
	   gl.glVertex3d(-0.5, -0.5, 0);
	   gl.glVertex3d(0.5, -0.5, 0);
	   gl.glVertex3d(0.5, -0.25, 0);
	   gl.glVertex3d(0.25,0,0);
	   gl.glVertex3d(-0.25,0,0);
	   gl.glVertex3d(-0.5,-0.25,0);
	   gl.glEnd();
	   gl.glTranslated(0,0.4,0);
	   glut.glutSolidSphere(0.2, 20, 20);
	   gl.glTranslated(0,0.3,0);
	   glut.glutSolidSphere(0.2, 20, 20);
	   gl.glTranslated(0,0.2,0);
	   glut.glutSolidSphere(0.2, 20, 20);
	   double d=(1.0/10)*(2*Math.PI),r=0.2;
	   gl.glTranslated(-0.4,0.4,0);
	   gl.glRotatef(90,0,1,0);
	   gl.glBegin(GL2.GL_QUAD_STRIP);
	   for(int i=0;i<=10;i++)
	   {
		   gl.glNormal3d(Math.cos(d*i), Math.sin(d*i), 0);
		   gl.glVertex3d(r*Math.cos(d*i), r*Math.sin(d*i), 0.4);
		   gl.glVertex3d(r*Math.cos(d*i), r*Math.sin(d*i), 0);
	   }
	   gl.glEnd();
   }
	
   @Override
   public void dispose(GLAutoDrawable arg0) {
      //method body
   }
   private GLU glu;
   @Override
   public void init(GLAutoDrawable arg0) {
	   GL2 gl = arg0.getGL().getGL2();
	   float[] material={1f,0f,0,1}; 
	   glu = new GLU();

	    gl.glClearColor(0.0f, 1.0f, 0.0f, 1.0f);
	    gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);
        
        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK,GL2.GL_AMBIENT_AND_DIFFUSE,material,0);
        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK,GL2.GL_SPECULAR,new float[] {1.5f,0f,0f,1},0);
        gl.glMateriali(GL2.GL_FRONT_AND_BACK,GL2.GL_SHININESS,128);
        
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
      Primitives b = new Primitives();
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
