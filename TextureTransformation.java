package basics;


import javax.swing.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.*;

import java.io.File;

import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;


public class TextureTransformation implements GLEventListener{
	File file;
	Texture texture;
	float sx=1,sy=1,sz=1;
    /**
     * A main routine to create and show a window that contains a
     * panel of type UnlitCube.  The program ends when the
     * user closes the window.
     */
    public static void main(String[] args) {
        JFrame window = new JFrame("A Simple program to texture map a polygon");
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        // The canvas
        final GLCanvas glcanvas = new GLCanvas(capabilities);
        TextureTransformation cube = new TextureTransformation();
        glcanvas.addGLEventListener(cube);        
        glcanvas.setSize(400,400);

        
        window.add( glcanvas );
        window.pack();
        window.setLocation(50,50);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        glcanvas.requestFocusInWindow();
        FPSAnimator fps=new FPSAnimator(glcanvas,300,true);
        fps.start();
    }
    
    /**
     * Constructor for class UnlitCube.
     */
    public TextureTransformation() {
    	    }
      
    private void square(GL2 gl2, double r, double g, double b) {
        gl2.glColor3d(r,g,b);
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
        
        gl2.glMatrixMode(GL2.GL_TEXTURE);
       // gl2.glLoadIdentity();
       // gl2.glScaled(sx, sy, sz);
        // gl2.glTranslated(0.1,0,0);
        gl2.glRotatef(0.1f, 0, 0, 1);
        gl2.glMatrixMode(GL2.GL_MODELVIEW);
        square(gl2,1,1,1);
        
        sx-=0.001;
        sy-=0.001;
        sz-=0.001;
        
    } // end display()

    public void init(GLAutoDrawable drawable) {
           // called when the panel is created
    	try {                
	        /*URL textureURL;
	        textureURL=getClass().getClassLoader().getResource("texture/Jerry.jpg");*/
    		 file=new File("E:\\workspace(juno)\\JOGL1\\src\\texture\\Jerry.jpg");
	        texture=TextureIO.newTexture(file,true);
	       } catch (Exception ex) {
	            // handle exception...
	    	   System.out.println("Exception");
	       }
    	GL2 gl2 = drawable.getGL().getGL2();
        gl2.glMatrixMode(GL2.GL_PROJECTION);
        gl2.glOrtho(-1, 1 ,-1, 1, -1, 1);
        gl2.glMatrixMode(GL2.GL_MODELVIEW);
        gl2.glClearColor( 0, 0, 0, 1 );
        gl2.glEnable(GL2.GL_DEPTH_TEST);
        //texture.lip();
        texture.enable(gl2);
        texture.bind(gl2);
        texture.setTexParameteri(gl2,GL2.GL_TEXTURE_WRAP_S,GL2.GL_REPEAT);
      	texture.setTexParameteri(gl2,GL2.GL_TEXTURE_WRAP_T,GL2.GL_REPEAT);
    }

    public void dispose(GLAutoDrawable drawable) {
            // called when the panel is being disposed
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
            // called when user resizes the window
    }
   
}

