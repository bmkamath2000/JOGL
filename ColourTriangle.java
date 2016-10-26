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

 
public class ColourTriangle implements GLEventListener { 
   
   @Override 
   public void display( GLAutoDrawable drawable ) { 
   
      final GL2 gl = drawable.getGL().getGL2();
      gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
      //gl.glTranslatef(0.3f,0.3f,0f);
    //  gl.glScaled(0.5,0.5,1);
      
      gl.glBegin( GL2.GL_TRIANGLES );  
      
      // Drawing Using Triangles 
    
      gl.glColor3f( 1.0f, 0.0f, 0.0f );   // Red 
      gl.glVertex3f( 0.5f,0.7f,0.0f );    // Top 
		
      gl.glColor3f( 0.0f,1.0f,0.0f );     // green 
      gl.glVertex3f( -0.2f,-0.50f,0.0f ); // Bottom Left 
		
      gl.glColor3f( 0.0f,0.0f,1.0f );     // blue 
      gl.glVertex3f( 0.5f,-0.5f,0.0f );   // Bottom Right 
		
      gl.glEnd();         
      
   } 
   
   @Override 
   public void dispose( GLAutoDrawable arg0 ) { 
      //method body 
   } 
   
   @Override 
   public void init( GLAutoDrawable arg0 ) { 
      // method body    
   }
   
   @Override 
   public void reshape( GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4 ) { 
      // method body 
   } 
   
   public static void main( String[] args ) {    
   
      //getting the capabilities object of GL2 profile
      final GLProfile profile = GLProfile.get( GLProfile.GL2 ); 
      GLCapabilities capabilities = new GLCapabilities(profile);
          
      // The canvas  
      final GLCanvas glcanvas = new GLCanvas( capabilities ); 
      ColourTriangle triangle = new ColourTriangle(); 
      glcanvas.addGLEventListener( triangle ); 
      glcanvas.setSize( 400, 400 );   
      
      //creating frame 
      final JFrame frame = new JFrame (" Colored Triangle"); 
          
      //adding canvas to it 
      frame.getContentPane().add( glcanvas ); 
      frame.setSize( frame.getContentPane().getPreferredSize()); 
      frame.setVisible( true ); 
      
   } //end of main
	
} //end of class

