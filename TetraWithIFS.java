package basics;

import java.awt.event.*;
import javax.swing.*;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.*;

import com.jogamp.opengl.util.FPSAnimator;
/**
 * Use OpenGL to draw two cubes, one using glDrawArrays,
 * and one using glDrawElements.  The arrow keys can be
 * used to rotate both cubes.
 *
 * Note that this program does not use lighting.
 */
public class TetraWithIFS implements GLEventListener, KeyListener {

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
        TetraWithIFS b = new TetraWithIFS();
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
    public TetraWithIFS() {
        
        //setPreferredSize( new Dimension(600,300) );
        //addGLEventListener(this); // A listener is essential! The listener is where the OpenGL programming lives.
        //addKeyListener(this);
    }

    private double rotateX = 15;    // rotations of the cube about the axes
    private double rotateY = -15;
    private double rotateZ = 0;

    //---------------------- data for glDrawArrays and glDrawElements  ----------------

    /* Arrays for use with glDrawElements.  This is the data for a cube with 6 different
     * colors at the six vertices.  (Looks kind of strange without lighting.)
     */

    private float[] vertexList = {  // Coordinates for the vertices of a cube.
            1,0,1,1,0,-1,-1,0,-1,-1,0,1,0,2,0};

    private float[] vertexColors = {  // An RGB color value for each vertex
            1,1,1,   1,0,0,   1,1,0,   0,1,0,
            0,0,1 };

    private int[][] faceList = {  // Vertex number for the six faces.
            {4,3,0},{4,0,1},{4,1,2},{4,2,3},{0,3,2,1}  };


        public void display(GLAutoDrawable drawable) {    

        GL2 gl2 = drawable.getGL().getGL2(); // The object that contains all the OpenGL methods.

        gl2.glClear( GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT );

        gl2.glLoadIdentity();             // Set up modelview transform, first cube.

        
        gl2.glRotated(rotateZ,0,0,1);     // Apply rotations.
        gl2.glRotated(rotateY,0,1,0);
        gl2.glRotated(rotateX,1,0,0);
        
    
        for(int i=0;i<faceList.length;i++)
        {
        	gl2.glBegin(GL2.GL_POLYGON);
        	int[] faceData=faceList[i];
        	for(int j=0;j<faceData.length;j++)
        	{
        		int vertexIndex=faceData[j];
        		gl2.glColor3fv(vertexColors,3*vertexIndex);
        		gl2.glVertex3fv(vertexList,3*vertexIndex);
        		
        	}
        	gl2.glEnd();
        }

       
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
