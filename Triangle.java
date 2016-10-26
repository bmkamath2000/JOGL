// Translate a triangle with varying amounts
      
      
      package basics;
      import com.jogamp.opengl.GL2;
      import com.jogamp.opengl.GLAutoDrawable;
      import com.jogamp.opengl.GLCapabilities;
      import com.jogamp.opengl.GLEventListener;
      import com.jogamp.opengl.GLProfile;
      import com.jogamp.opengl.awt.GLCanvas;
      import com.jogamp.opengl.util.FPSAnimator;
      import javax.swing.JFrame;


      class Triangle implements GLEventListener {

         @Override
         public void display(GLAutoDrawable arg0) {
            // method body
      	   GL2 gl = arg0.getGL().getGL2();
      	   gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
     // 	   gl.glColor3f(0, 1,0);
      	   gl.glLoadIdentity();
      	   float tx=0;
      	  for(int i=0;i<5;i++)
      	  {
      		  gl.glPushMatrix();
      		  gl.glTranslatef(tx, 0, 0);
      		  gl.glScaled(0.5, 0.5, 0.5);
      	   gl.glBegin( GL2.GL_TRIANGLES );  
           
           // Drawing Using Triangles 
         
           gl.glColor3f( 1.0f, 0.0f, 0.0f );   // Red 
           gl.glVertex3f( -0.5f,-0.5f,0.0f );    // left
     		
           gl.glColor3f( 0.0f,1.0f,0.0f );     // green 
           gl.glVertex3f( 0.5f,-0.5f,0.0f ); // right 
     		
           gl.glColor3f( 0.0f,0.0f,1.0f );     // blue 
           gl.glVertex3f( 0.5f,0.9f,0f );   // top 
     		
           gl.glEnd();
           tx+=0.2f;
           gl.glPopMatrix();
      	  }
      	gl.glBegin( GL2.GL_TRIANGLES );  
        
        // Drawing Using Triangles 
      
        gl.glColor3f( 1.0f, 0.0f, 0.0f );   // Red 
        gl.glVertex3f( -0.5f,-0.5f,0.0f );    // left
  		
        gl.glColor3f( 0.0f,1.0f,0.0f );     // green 
        gl.glVertex3f( 0.5f,-0.5f,0.0f ); // right 
  		
        gl.glColor3f( 0.0f,0.0f,1.0f );     // blue 
        gl.glVertex3f( 0.5f,0.9f,0f );   // top 
  		
        gl.glEnd();
     
          }
      	
         @Override
         public void dispose(GLAutoDrawable arg0) {
            //method body
         }
      
         @Override
         public void init(GLAutoDrawable arg0) {
      	   GL2 gl = arg0.getGL().getGL2();
      

      	    gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
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
            Triangle b = new Triangle();
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
