package basics;


import javax.swing.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.*;

public class PaddleWheel implements GLEventListener{

    /**
     * A main routine to create and show a window that contains a
     * panel of type UnlitCube.  The program ends when the
     * user closes the window.
     */
    public static void main(String[] args) {
        JFrame window = new JFrame("A Simple Unlit Cube -- ARROW KEYS ROTATE");
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        // The canvas
        final GLCanvas glcanvas = new GLCanvas(capabilities);
        PaddleWheel cube = new PaddleWheel();
        glcanvas.addGLEventListener(cube);        
        glcanvas.setSize(400,400);

        
        window.add( glcanvas );
        window.pack();
        window.setLocation(50,50);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        glcanvas.requestFocusInWindow();
       
    }
    
    /**
     * Constructor for class UnlitCube.
     */
    public PaddleWheel() {
    	    }
      
    private void paddle(GL2 gl2, double r, double g, double b) {
        gl2.glColor3d(r,g,b);
      for(int i=0;i<36;i++)
      {
       gl2.glRotated(10,1,0,0);
    	gl2.glBegin(GL2.GL_POLYGON);
        gl2.glVertex2d(-0.7, 1);
        gl2.glVertex2d(0.7, 1);
        gl2.glVertex2d(1, 2);
        gl2.glVertex2d(-1, 2);
        gl2.glEnd();
      }
    }
  
    public void display(GLAutoDrawable drawable) {    
        
        GL2 gl2 = drawable.getGL().getGL2(); // The object that contains all the OpenGL methods.
         
        gl2.glClear( GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT );
        
        gl2.glLoadIdentity();             // Set up modelview transform. 
        
        gl2.glRotatef(60,0,1,0);
        gl2.glScaled(0.5,0.5,0.5);
        paddle(gl2,1,1,0);
        
    } // end display()

    public void init(GLAutoDrawable drawable) {
           // called when the panel is created
    	GL2 gl2 = drawable.getGL().getGL2();
        gl2.glMatrixMode(GL2.GL_PROJECTION);
        gl2.glOrtho(-2, 2 ,-2, 2, -2, 2);
        gl2.glMatrixMode(GL2.GL_MODELVIEW);
        gl2.glClearColor( 0, 0, 0, 1 );
        gl2.glEnable(GL2.GL_DEPTH_TEST);
        
        gl2.glEnable(GL2.GL_LIGHTING);
        gl2.glEnable(GL2.GL_LIGHT0);
        gl2.glEnable(GL2.GL_LIGHT1);
        gl2.glLightModeli(GL2.GL_LIGHT_MODEL_TWO_SIDE,GL2.GL_TRUE);
        gl2.glEnable(GL2.GL_NORMALIZE);
       }

    public void dispose(GLAutoDrawable drawable) {
            // called when the panel is being disposed
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
            // called when user resizes the window
    }
   
}

