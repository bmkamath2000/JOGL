package basics;
import com.jogamp.opengl.GLAutoDrawable;
import javax.swing.JFrame;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
//import com.jogamp.opengl.glu.GLU;


 

import com.jogamp.opengl.util.FPSAnimator; 

public class DrawCart implements GLEventListener { 
   private float frameno;  //for angle of rotation
      
   @Override 
   public void display( GLAutoDrawable drawable ) {
   
      final GL2 gl = drawable.getGL().getGL2(); 
      gl.glClear (GL2.GL_COLOR_BUFFER_BIT |  GL2.GL_DEPTH_BUFFER_BIT );  
      
      // Clear The Screen And The Depth Buffer 
      gl.glLoadIdentity();  // Reset The View     
              
      //triangle rotation
      gl.glTranslated(-frameno, 0, 0);
      gl.glScaled(0.7,0.7,1);
      drawcart(gl);
      
          
      gl.glFlush(); 
      
      frameno += 0.02f;  //assigning the angle     
      if(frameno>2.0f) frameno=-2;
   } 
   public void drawcart(GL2 gl)
   {
	   gl.glPushMatrix();
	   
	   gl.glPushMatrix();
	   
	   	drawwheel(gl);
	   	gl.glTranslatef(0.6f,0,0);
	   	drawwheel(gl);
	   	gl.glPopMatrix();
	   	gl.glColor3f(0,1,1);
	   gl.glBegin(GL2.GL_POLYGON);
	   gl.glVertex3f(-0.4f, 0, 0);
	   gl.glVertex3f(0.95f, 0, 0);
	   gl.glVertex3f(0.95f, 0.6f, 0);
	   gl.glVertex3f(-0.4f, 0.6f, 0);
	   gl.glEnd();
	   	gl.glPopMatrix();
	      
   }
   public void drawwheel(GL2 gl)
   {
	   gl.glPushMatrix();
	   gl.glRotatef(frameno*20,0,0,1);
	   gl.glScaled(0.3, 0.3, 1);
	   	gl.glColor3f(0,0,0);
	      drawdisk(gl,1);
	      gl.glColor3f(0.75f,0.75f,0.75f);
	      drawdisk(gl,0.9f);
	      gl.glColor3f(0,0,0);
	      drawdisk(gl,0.2f);
	      
	        gl.glBegin(GL2.GL_LINES);
	        for (int i = 0; i < 15; i++) {
	            gl.glVertex2f(0,0);
	            gl.glVertex2d(Math.cos(i*2*Math.PI/15), Math.sin(i*2*Math.PI/15));
	        }
	        gl.glEnd();
	      
	      gl.glPopMatrix();
   }

   public void drawdisk(GL2 gl,float rad)
   {
	      gl.glBegin(GL2.GL_POLYGON);
	      for(int i=0;i<32;i++)
	      {
	    	  
	    	  gl.glVertex2d(rad*Math.cos(i*2*Math.PI/32),rad*Math.sin(i*2*Math.PI/32));
	      }
	      gl.glEnd();
	   
   }
   @Override 
   public void dispose( GLAutoDrawable arg0 ) { 
      //method body 
   } 
   
   @Override 
   public void init( GLAutoDrawable arg0 ) { 
      // method body
	   GL2 gl=arg0.getGL().getGL2();
	   gl.glClearColor(1,1,0,1);
   }
   
   @Override 
   public void reshape( GLAutoDrawable drawable, int x, int y, int width, int height ) { }
   
      public static void main( String args[])
      {
	         //getting the capabilities object of GL2 profile
         final GLProfile profile  = GLProfile.get(GLProfile.GL2 ); 
         GLCapabilities capabilities  = new GLCapabilities( profile );

         // The canvas  
         final GLCanvas glcanvas = new GLCanvas( capabilities); 
         DrawCart triangle = new DrawCart(); 
         glcanvas.addGLEventListener( triangle ); 
         glcanvas.setSize( 400, 400 );  

         // creating frame 
         final JFrame frame = new JFrame ("Rotating Triangle");

         // adding canvas to it 
         frame.getContentPane().add( glcanvas ); 
         frame.setSize(frame.getContentPane() .getPreferredSize());                 
         frame.setVisible( true ); 
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
         //Instantiating and Initiating Animator 
         final FPSAnimator animator = new FPSAnimator(glcanvas, 300,true); 
         animator.start(); 
      
		
   } //end of main
	
} //end of class
