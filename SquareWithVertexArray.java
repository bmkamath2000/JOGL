package basics;

import java.awt.event.*;
import javax.swing.*;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.*;
import com.jogamp.common.nio.Buffers;
import java.nio.FloatBuffer;


import com.jogamp.opengl.util.FPSAnimator;
/**
 * Use OpenGL to draw two cubes, one using glDrawArrays,
 * and one using glDrawElements.  The arrow keys can be
 * used to rotate both cubes.
 *
 * Note that this program does not use lighting.
 */
public class SquareWithVertexArray implements GLEventListener, KeyListener {

    /**
     * A main routine to create and show a window that contains a
     * panel of type CubesWithVertexArrays.  The program ends when the
     * user closes the window.
     */
    public static void main(String[] args) {
    	 //getting the capabilities object of GL2 profile
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
          
        // The canvas
        final GLCanvas glcanvas = new GLCanvas(capabilities);
        SquareWithVertexArray b = new SquareWithVertexArray();
        glcanvas.addGLEventListener(b);        
        glcanvas.addKeyListener(b);
        
        glcanvas.setSize(400, 400);
          
        //creating frame
        final JFrame frame = new JFrame (" Basic Frame");
          
        //adding canvas to frame
        frame.add(glcanvas);
        frame.setSize( 640, 480 );
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final FPSAnimator animator = new FPSAnimator(glcanvas, 300,true); 
        animator.start(); 
     
    }

    /**
     * Constructor for class UnlitCube.
     */
    public SquareWithVertexArray() {
        
        //setPreferredSize( new Dimension(600,300) );
        //addGLEventListener(this); // A listener is essential! The listener is where the OpenGL programming lives.
        //addKeyListener(this);
    }

    private double rotateX = 0;    // rotations of the cube about the axes
    private double rotateY = 0;
    private double rotateZ = 0;

    //---------------------- data for glDrawArrays and glDrawElements  ----------------

    /* Arrays for use with glDrawElements.  This is the data for a cube with 6 different
     * colors at the six vertices.  (Looks kind of strange without lighting.)
     */

    
    /* Arrays for use with glDrawArrays.  The coordinate array contains four sets of vertex
     * coordinates for each face.  The color array must have a color for each vertex.  Since
     * the color of each face is solid, there is a lot of redundancy in the color array.
     * There is also redundancy in the coordinate array, compared to using glDrawElements.
     * But note that it is impossible to use a single call to glDrawElements to draw a cube 
     * with six faces where each face has a different solid color, since with glDrawElements, 
     * the colors are associated with the vertices, not the faces.
     */

    float[] squareCoords = {-1,-1, 1,-1, 1,1, -1,1  }; 
    float[] squareColor = {0,1,0, 1,0,0, 1,0,0, 1,0,0  };

    FloatBuffer squareCoordBuffer = Buffers.newDirectFloatBuffer(squareCoords); 
    FloatBuffer cubeFaceColorBuffer=Buffers.newDirectFloatBuffer(squareColor);   

    
    
   
    

    //-------------------- GLEventListener Methods -------------------------

    /**
     * The display method is called when the panel needs to be redrawn.
     * The is where the code goes for drawing the image, using OpenGL commands.
     */
    public void display(GLAutoDrawable drawable) {    

        GL2 gl2 = drawable.getGL().getGL2(); // The object that contains all the OpenGL methods.

        gl2.glClear( GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT );

        gl2.glLoadIdentity();             // Set up modelview transform, first cube.
    
        
        gl2.glRotated(rotateZ,0,0,1);     // Apply rotations.
        gl2.glRotated(rotateY,0,1,0);
        gl2.glRotated(rotateX,1,0,0);
        
        gl2.glVertexPointer( 2, GL2.GL_FLOAT, 0, squareCoordBuffer );  // Set data type and location, first cube.
        gl2.glColorPointer( 3,GL2.GL_FLOAT, 0, cubeFaceColorBuffer );

        gl2.glEnableClientState( GL2.GL_VERTEX_ARRAY );
        gl2.glEnableClientState( GL2.GL_COLOR_ARRAY );
  //      gl2.glColor3f(0, 1, 1);
        gl2.glDrawArrays( GL2.GL_POLYGON, 0, 4 ); // Draw the first cube!
        

        
        
    } // end display()

    public void init(GLAutoDrawable drawable) {
        // called when the panel is created
        GL2 gl2 = drawable.getGL().getGL2();
        gl2.glMatrixMode(GL2.GL_PROJECTION);
        gl2.glOrtho(-2, 2, -2, 2, -2, 2);  // simple orthographic projection
        gl2.glMatrixMode(GL2.GL_MODELVIEW);
        gl2.glClearColor( 0.5F, 0.5F, 0.5F, 1 );
        gl2.glEnable(GL2.GL_DEPTH_TEST);
    }

    public void dispose(GLAutoDrawable drawable) {
        // called when the panel is being disposed
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        // called when user resizes the window
    }

    // ----------------  Methods from the KeyListener interface --------------

    public void keyPressed(KeyEvent evt) {
        int key = evt.getKeyCode();
        if ( key == KeyEvent.VK_LEFT )
            rotateY -= 15;
        else if ( key == KeyEvent.VK_RIGHT )
            rotateY += 15;
        else if ( key == KeyEvent.VK_DOWN)
            rotateX += 15;
        else if ( key == KeyEvent.VK_UP )
            rotateX -= 15;
        else if ( key == KeyEvent.VK_PAGE_UP )
            rotateZ += 15;
        else if ( key == KeyEvent.VK_PAGE_DOWN )
            rotateZ -= 15;
        else if ( key == KeyEvent.VK_HOME )
            rotateX = rotateY = rotateZ = 0;
       // repaint();
    }

    public void keyReleased(KeyEvent evt) {
    }

    public void keyTyped(KeyEvent evt) {
    }

}
