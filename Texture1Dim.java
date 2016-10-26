/* program to draw a 1D texture on a cube */
package basics;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.*;
import com.jogamp.opengl.util.FPSAnimator;
import java.io.File;

import java.nio.ByteBuffer;

import com.jogamp.opengl.util.texture.Texture;

import com.jogamp.common.nio.Buffers;

public class Texture1Dim implements GLEventListener, KeyListener {
	File file;
	Texture texture;
    
    public static void main(String[] args) {
        JFrame window = new JFrame("A Simple Unlit Cube -- ARROW KEYS ROTATE");
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        // The canvas
        final GLCanvas glcanvas = new GLCanvas(capabilities);
        Texture1Dim cube = new Texture1Dim();
        glcanvas.addGLEventListener(cube);        
        glcanvas.setSize(400,400);
        glcanvas.addKeyListener(cube);
        
        window.add( glcanvas );
        window.pack();
        window.setLocation(50,50);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        glcanvas.requestFocusInWindow();
        FPSAnimator animator=new FPSAnimator(glcanvas,300,true);
        animator.start();
    }
    
    /**
     * Constructor for class UnlitCube.
     */
    public Texture1Dim() {
    	
    	
    }
    
    //-------------------- methods to draw the cube ----------------------
    
    double rotateX = 15;    // rotations of the cube about the axes
    double rotateY = 15;
    double rotateZ = 0;
    
    private void square(GL2 gl2, double r, double g, double b) {
        gl2.glColor4d(r,g,b,1);
        gl2.glBegin(GL2.GL_POLYGON);
        gl2.glNormal3f(0,0,1);
        gl2.glTexCoord2d(0, 0);
        gl2.glVertex3d(-0.5, -0.5, 0.5);
        gl2.glTexCoord2d(1, 0);
        gl2.glVertex3d(0.5, -0.5, 0.5);
        gl2.glTexCoord2d(1, 1);
        gl2.glVertex3d(0.5, 0.5, 0.5);
        gl2.glTexCoord2d(0, 1);
        gl2.glVertex3d(-0.5, 0.5, 0.5);
        gl2.glEnd();
    }
    
    private void cube(GL2 gl2, double size) {
        gl2.glPushMatrix();
        gl2.glScaled(size,size,size); // scale unit cube to desired size
        
        square(gl2,1, 1, 1); // red front face
        
        gl2.glPushMatrix();
        gl2.glRotated(90, 0, 1, 0);
        square(gl2,1, 1, 1); // green right face
        gl2.glPopMatrix();
        
        gl2.glPushMatrix();
        gl2.glRotated(-90, 1, 0, 0);
        square(gl2,1, 1, 1); // blue top face
        gl2.glPopMatrix();
        
        gl2.glPushMatrix();
        gl2.glRotated(180, 0, 1, 0);
        square(gl2,1, 1, 1); // cyan back face
        gl2.glPopMatrix();
        
        gl2.glPushMatrix();
        gl2.glRotated(-90, 0, 1, 0);
        square(gl2,1, 1, 1); // magenta left face
        gl2.glPopMatrix();
        
        gl2.glPushMatrix();
        gl2.glRotated(90, 1, 0, 0);
        square(gl2,1, 1, 1); // yellow bottom face
        gl2.glPopMatrix();
        
        gl2.glPopMatrix(); // Restore matrix to its state before cube() was called.
    }
    
    
    //-------------------- GLEventListener Methods -------------------------

    /**
     * The display method is called when the panel needs to be redrawn.
     * The is where the code goes for drawing the image, using OpenGL commands.
     */
    public void display(GLAutoDrawable drawable) {    
        
        GL2 gl2 = drawable.getGL().getGL2(); // The object that contains all the OpenGL methods.
         
        gl2.glClear( GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT );
        
        gl2.glLoadIdentity();             // Set up modelview transform. 
        gl2.glRotated(rotateZ,0,0,1);
        gl2.glRotated(rotateY,0,1,0);
        gl2.glRotated(rotateX,1,0,0);

        cube(gl2,1);
        
    } // end display()

    public void init(GLAutoDrawable drawable) {
           // called when the panel is created
    	ByteBuffer textureData1D =Buffers.newDirectByteBuffer(4*256);
    	for(int i=0;i<256;i++)
    	{
    		Color c=Color.getHSBColor((1.0f/256)*i,1,1);
    		textureData1D.put((byte)c.getRed());
    		textureData1D.put((byte)c.getGreen());
    		textureData1D.put((byte)c.getBlue());
    		textureData1D.put((byte)(0xff));
    	}
    	textureData1D.rewind();
    	
    	GL2 gl2 = drawable.getGL().getGL2();
    	
        gl2.glMatrixMode(GL2.GL_PROJECTION);
        gl2.glLoadIdentity();
        gl2.glOrtho(-1, 1 ,-1, 1, -1, 1);
        gl2.glMatrixMode(GL2.GL_MODELVIEW);
        gl2.glClearColor( 0, 0, 0, 1 );
        gl2.glEnable(GL2.GL_DEPTH_TEST);
        gl2.glEnable(GL2.GL_LIGHTING);
        gl2.glEnable(GL2.GL_LIGHT0);
        gl2.glEnable(GL2.GL_TEXTURE_1D);
        gl2.glTexImage1D(GL2.GL_TEXTURE_1D,0,GL2.GL_RGBA,256,0,GL2.GL_RGBA,GL2.GL_UNSIGNED_BYTE,textureData1D);
        gl2.glTexEnvi(GL2.GL_TEXTURE_1D, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_DECAL);
        gl2.glTexParameteri(GL2.GL_TEXTURE_1D,GL2.GL_TEXTURE_MIN_FILTER,GL2.GL_NEAREST);
        gl2.glTexParameteri(GL2.GL_TEXTURE_1D,GL2.GL_TEXTURE_WRAP_S,GL2.GL_REPEAT);
        gl2.glTexParameteri(GL2.GL_TEXTURE_1D,GL2.GL_TEXTURE_WRAP_T,GL2.GL_REPEAT);
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
        
    }

    public void keyReleased(KeyEvent evt) {
    }
    
    public void keyTyped(KeyEvent evt) {
    }
    
}

