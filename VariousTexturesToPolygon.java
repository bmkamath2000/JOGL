package basics;




import javax.swing.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.*;

import java.io.File;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import com.jogamp.opengl.util.texture.Texture;



public class VariousTexturesToPolygon implements GLEventListener{
	File file;
	Texture texture;
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
        VariousTexturesToPolygon cube = new VariousTexturesToPolygon();
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
    public VariousTexturesToPolygon() {
    	    }
      
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
  
    public void display(GLAutoDrawable drawable) {    
        
        GL2 gl2 = drawable.getGL().getGL2(); // The object that contains all the OpenGL methods.
         
        gl2.glClear( GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT );
        
        gl2.glLoadIdentity();             // Set up modelview transform. 
        

        square(gl2,1,0,1);
        
    } // end display()

    public void init(GLAutoDrawable drawable) {
           // called when the panel is created
    	
    	//byte[] bytes = new byte[100*100*4];
    	   ByteBuffer buffer ;//= ByteBuffer.wrap(bytes);
    	   
    	   // Create a non-direct ByteBuffer with a 10 byte capacity
    	   // The underlying storage is a byte array.
    	   buffer = ByteBuffer.allocate(10*10*4);
    	    
    	   // Create a memory-mapped ByteBuffer with a 10 byte capacity.
    	  // buffer = ByteBuffer.allocateDirect(10*10*4);
        

        

        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                // to do use a formula here to create a texture
            	int red=(int)((0xff)*Math.sin(10*x*Math.PI/180));
            	int green=(0xff);
            	int blue=(0xff);//*(x^y);
                buffer.put((byte) ((0xFF)&red));     // Red component
                buffer.put((byte) ((0xFF)&green));      // Green component
                buffer.put((byte) ((0xFF)&blue));               // Blue component
                buffer.put((byte) (0xFF));    // Alpha component. Only for RGBA
            }
        }

        buffer.flip();
    	
    	GL2 gl2 = drawable.getGL().getGL2();
        gl2.glMatrixMode(GL2.GL_PROJECTION);
        gl2.glOrtho(-1, 1 ,-1, 1, -1, 1);
        gl2.glMatrixMode(GL2.GL_MODELVIEW);
        gl2.glClearColor( 0, 0, 0, 1 );
        gl2.glEnable(GL2.GL_DEPTH_TEST);
        gl2.glEnable(GL2.GL_LIGHTING);
        gl2.glEnable(GL2.GL_LIGHT0);
        gl2.glEnable(GL2.GL_TEXTURE_2D);
      //  gl2.glEnable(GL2.GL_BLEND);
      //  gl2.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
        
        gl2.glTexImage2D(GL2.GL_TEXTURE_2D, 0, GL2.GL_RGBA, 10, 10, 0, GL2.GL_RGBA, GL2.GL_UNSIGNED_BYTE, buffer);

        gl2.glTexEnvi(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_DECAL);
 
        gl2.glTexParameteri(GL2.GL_TEXTURE_2D,GL2.GL_TEXTURE_MIN_FILTER,GL2.GL_NEAREST);
        gl2.glTexParameteri(GL2.GL_TEXTURE_2D,GL2.GL_TEXTURE_MAG_FILTER,GL2.GL_NEAREST);
        gl2.glTexParameteri(GL2.GL_TEXTURE_2D,GL2.GL_TEXTURE_WRAP_S,GL2.GL_REPEAT);
      	gl2.glTexParameteri(GL2.GL_TEXTURE_2D,GL2.GL_TEXTURE_WRAP_T,GL2.GL_REPEAT);
    }

    public void dispose(GLAutoDrawable drawable) {
            // called when the panel is being disposed
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
            // called when user resizes the window
    }
   
}

