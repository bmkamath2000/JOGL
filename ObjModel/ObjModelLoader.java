package ObjModel;



import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import com.jogamp.opengl.GLAutoDrawable;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.glu.GLU;
//import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.gl2.GLUT;

 
public class ObjModelLoader extends GLJPanel implements GLEventListener,MouseMotionListener, MouseListener
{	final static int MAX_VERTICES=1000000;
	final static int MAX_FACES=1000000;
	final static int MAX_NORMALS=1000000;
   float vertices[][];int vertex_count=0;
   int faces[][];int face_count=0;
   float normals[][];int normal_count=0;
   int moving,startx,starty;
	float angx=0,angy=0,angz=0;
	
	float angle=0;
	GLUT glut;
	FloatBuffer position;
	float[] pos={0,0,1,1};
	float[] ambientLight={0.2f, 0.2f, 0.2f, 1.0f};
	float[] Lt0amb = {0.3f, 0.3f, 0.3f, 1.0f};
	float[] Lt0diff = {1.0f, 1.0f, 1.0f, 1.0f};
	float[] Lt0spec = {1.0f, 1.0f, 1.0f, 1.0f};
	int usesnormals=0;
   @Override 
   public void display( GLAutoDrawable drawable ) { 
   
      final GL2 gl = drawable.getGL().getGL2();
      glut=new GLUT();
      gl.glClear(GL2.GL_COLOR_BUFFER_BIT|GL2.GL_DEPTH_BUFFER_BIT);
      gl.glLoadIdentity();
      //gl.glEnable(GL2.GL_SMOOTH);
      gl.glShadeModel(GL2.GL_FLAT);
      gl.glRotatef(angx,1, 0, 0); 
		gl.glRotatef(angy,0, 1, 0);
		gl.glRotatef(angz,0, 0, 1);
      gl.glBegin(GL2.GL_TRIANGLES);
      for(int i=0;i<face_count;i++)
      {
    	  if(usesnormals==1)
    	  {
    	  gl.glNormal3f(normals[faces[i][3]][0],normals[faces[i][3]][1],normals[faces[i][3]][2]);
    	  }
    	  gl.glVertex3f(vertices[faces[i][0]][0],vertices[faces[i][0]][1],vertices[faces[i][0]][2]);
    	  if(usesnormals==1)
    	  {
    	  gl.glNormal3f(normals[faces[i][4]][0],normals[faces[i][4]][1],normals[faces[i][4]][2]);
    	  }
    	  gl.glVertex3f(vertices[faces[i][1]][0],vertices[faces[i][1]][1],vertices[faces[i][1]][2]);
    	  if(usesnormals==1)
    	  {
    	  gl.glNormal3f(normals[faces[i][5]][0],normals[faces[i][5]][1],normals[faces[i][5]][2]);
    	  }
    	  gl.glVertex3f(vertices[faces[i][2]][0],vertices[faces[i][2]][1],vertices[faces[i][2]][2]);
      }
      gl.glEnd();
      gl.glFlush();
     // angle+=0.1f;
      repaint();
   } 
   
   @Override 
   public void dispose( GLAutoDrawable arg0 ) { 
      //method body 
   } 
   
   @Override 
   public void init( GLAutoDrawable arg0 ) { 
      // method body   
	   BufferedReader br;
	   float minx=-1,maxx=1,miny=-1,maxy=1,minz=-1,maxz=1,x,y,z,vnx,vny,vnz;
	   float[] material={1f,0f,0,1}; 
	   vertices=new float[MAX_VERTICES][3];
	   faces=new int[MAX_FACES][6];
	   normals=new float[MAX_NORMALS][3];

	   try {
		   br= new BufferedReader(new FileReader("E:\\workspace(juno)\\Textures\\src\\ObjModel\\elepham.obj"));
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();
		    String tokens[];
		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line=line.trim().replaceAll("\\s+", " ");
		        tokens=line.split(" ");
		        System.out.println(line);
		        if(tokens[0].length()==0)
		        {
		        	
		        }
		        else if(tokens[0].length()==1&&tokens[0].charAt(0)=='\n')
		        {
		        	
		        }
		        else if(tokens[0].length()==1 && tokens[0].charAt(0)=='v')
		        {
		        	x=Float.parseFloat(tokens[1]);
		        	y=Float.parseFloat(tokens[2]);
		        	z=Float.parseFloat(tokens[3]);
		        	if(x<minx) minx=x;
		        	if(x>maxx) maxx=x;
		        	if(y<miny) miny=y;
		        	if(y>maxy) maxy=y;
		        	if(z<minz) minz=z;
		        	if(z>maxz) maxz=z;
		        	//System.out.println("Vertex=>"+line+"    "+x+" "+y+" "+z);
		        	emitVertex(x,y,z);
		        }
		        else if(tokens[0].length()==1&&tokens[0].charAt(0)=='f')
		        {
		        	if(tokens.length==5)
		        	{
		        		String first[],second[],third[],fourth[];
		        		if(tokens[1].indexOf('/')!=-1)
		        		{
		        			usesnormals=1;
		        		first=tokens[1].split("/");
		        		second=tokens[2].split("/");
		        		third=tokens[3].split("/");
		        		fourth=tokens[4].split("/");
		        		System.out.println("Face=>"+line+"   "+Integer.parseInt(first[2])+" "+Integer.parseInt(second[2])+" "+Integer.parseInt(third[2])+" "+Integer.parseInt(fourth[2]));
		        		emitFace(Integer.parseInt(first[0]),Integer.parseInt(first[2]),Integer.parseInt(second[0]),Integer.parseInt(second[2]),Integer.parseInt(third[0]),Integer.parseInt(third[2]),Integer.parseInt(fourth[0]),Integer.parseInt(fourth[2]));
		        		}
		        		else
		        		{
		        			usesnormals=0;
		        			System.out.println("Face=>"+line+"   "+Integer.parseInt(tokens[1])+" "+Integer.parseInt(tokens[2])+" "+Integer.parseInt(tokens[3])+" "+Integer.parseInt(tokens[4]));
			        		emitFace(Integer.parseInt(tokens[1]),Integer.parseInt(tokens[2]),Integer.parseInt(tokens[3]),Integer.parseInt(tokens[4]));
			        		
		        		}
		        	}
		        	else if(tokens.length==4)
		        	{
		        		String first[],second[],third[];
		        		if(tokens[1].indexOf('/')!=-1)
		        		{
		        			usesnormals=1;
		        		first=tokens[1].split("/");
		        		second=tokens[2].split("/");
		        		third=tokens[3].split("/");
		        		//System.out.println("Face normals=>"+line+"   "+Integer.parseInt(first[2])+" "+Integer.parseInt(second[2])+" "+Integer.parseInt(third[2]));
		        		emitFace(Integer.parseInt(first[0]),Integer.parseInt(first[2]),Integer.parseInt(second[0]),Integer.parseInt(second[2]),Integer.parseInt(third[0]),Integer.parseInt(third[2]));
		        		}
		        		else
		        		{
		        			usesnormals=0;
			        		System.out.println("Face normals=>"+line+"   "+Integer.parseInt(tokens[1])+" "+Integer.parseInt(tokens[2])+" "+Integer.parseInt(tokens[3]));
			        		emitFace(Integer.parseInt(tokens[1]),Integer.parseInt(tokens[2]),Integer.parseInt(tokens[3]));
			        		
		        		}
		        
		        	}
		        }
		        else if(tokens[0].charAt(0)=='#')
		        {
		        	
		        }
		        else if(tokens[0].length()==2 && tokens[0].equals("vn"))
		        {
		        	vnx=Float.parseFloat(tokens[1]);
		        	vny=Float.parseFloat(tokens[2]);
		        	vnz=Float.parseFloat(tokens[3]);
		        	System.out.println("Normal=>"+line+"   "+vnx+" "+vny+" "+vnz);
	        		emitNormal(vnx,vny,vnz);
	        
		        }
		        else if(tokens[0].length()==2 && tokens[0]=="vt")
		        {
		        	
		        }
		        else if(tokens[0].length()==5&&tokens[0]=="usemtl")
		        {
		        	
		        }
		        else if(tokens[0].length()==1&&tokens[0]=="s")
		        {
		        	
		        }
		        
		        line = br.readLine();
		        
		    }
		    String everything = sb.toString();
		    
		    
		    br.close();
		}catch(IOException e)
		{
			e.printStackTrace();
		}
	   finally
		{ 
		
		}
	   GL2 gl=arg0.getGL().getGL2();
	   gl.glMatrixMode(GL2.GL_PROJECTION);
	   gl.glLoadIdentity();
	   gl.glOrtho(minx*1.5f, maxx*1.5f, miny*1.5f, maxy*1.5f, minz*1.5f, maxz*1.5f);
	   gl.glMatrixMode(GL2.GL_MODELVIEW);
       gl.glEnable(GL2.GL_DEPTH_TEST);
       gl.glEnable(GL2.GL_NORMALIZE);
	   gl.glEnable(GL2.GL_LIGHTING);
	   
       gl.glEnable(GL2.GL_LIGHT0);
       gl.glEnable(GL2.GL_LIGHT1);
       float[] bluish={0.3f,0.3f,0.7f,1};
       float[] position={400f,400f,400f,1.0f};
       gl.glLightfv(GL2.GL_LIGHT1,GL2.GL_AMBIENT,Lt0amb,0);
       gl.glLightfv(GL2.GL_LIGHT1,GL2.GL_SPECULAR,Lt0spec,0);
       gl.glLightfv(GL2.GL_LIGHT0,GL2.GL_POSITION,position,0);
       gl.glLightfv(GL2.GL_LIGHT1,GL2.GL_POSITION,position,0);
       gl.glLightfv(GL2.GL_LIGHT1,GL2.GL_DIFFUSE,bluish,0);
       gl.glLightfv(GL2.GL_LIGHT1,GL2.GL_SPECULAR,bluish,0);

       gl.glMaterialfv(GL2.GL_FRONT_AND_BACK,GL2.GL_AMBIENT_AND_DIFFUSE,material,0);
       gl.glMaterialfv(GL2.GL_FRONT_AND_BACK,GL2.GL_SPECULAR,new float[] {1.5f,0f,0f,1},0);
       
       gl.glMateriali(GL2.GL_FRONT_AND_BACK,GL2.GL_SHININESS,48);
       gl.glLightModeli(GL2.GL_LIGHT_MODEL_TWO_SIDE,GL2.GL_TRUE);

      
   }
   
   @Override 
   public void reshape( GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4 ) { 
      // method body 
   } 
   
   public static void main(String args[])
	{
		JFrame window = new JFrame("Object Model Loader in JOGL");
       // The canvas
       ObjModelLoader panel = new ObjModelLoader();
        panel.setPreferredSize(new Dimension(1000,1000));       
       
       
       window.setContentPane(panel);
       window.pack();
       window.setLocation(0,0);
       window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       window.setVisible(true);
       panel.requestFocusInWindow();		
	}
   ObjModelLoader()
   {
   	super( new GLCapabilities(null) ); // Makes a panel with default OpenGL "capabilities".
   	GLJPanel drawable = new GLJPanel();               // new GLJPanel inside GLJPanel
   	drawable.setPreferredSize(new Dimension(600,600));
   	setLayout(new BorderLayout());
   	add(drawable, BorderLayout.CENTER);
   	drawable.addGLEventListener(this); // Set up events for OpenGL drawing!
   	drawable.addMouseListener(this);
   	drawable.addMouseMotionListener(this);
   	
   }
   public void emitVertex(float x,float y,float z)
   {
	   vertices[vertex_count][0]=x;
	   vertices[vertex_count][1]=y;
	   vertices[vertex_count++][2]=z;
   }
   public void emitNormal(float vx,float vy,float vz)
   {
	   normals[normal_count][0]=vx;
	   normals[normal_count][1]=vy;
	   normals[normal_count++][2]=vz;
   }
   public void emitFace(int first,int firstvn,int second,int secondvn,int third,int thirdvn,int fourth,int fourthvn)
   {
	   faces[face_count][0]=first-1;
	   faces[face_count][1]=second-1;
	   faces[face_count][2]=third-1;
	   faces[face_count][3]=firstvn-1;
	   faces[face_count][4]=secondvn-1;
	   faces[face_count++][5]=thirdvn-1;
	   
	   faces[face_count][0]=first-1;
	   faces[face_count][1]=third-1;
	   faces[face_count][2]=fourth-1;
	   faces[face_count][3]=firstvn-1;
	   faces[face_count][4]=thirdvn-1;
	   faces[face_count++][5]=fourthvn-1;
	   
   }
   public void emitFace(int first,int second,int third)
   {
	   faces[face_count][0]=first-1;
	   faces[face_count][1]=second-1;
	   faces[face_count++][2]=third-1;
   }
   public void emitFace(int first,int second,int third,int fourth)
   {
	   faces[face_count][0]=first-1;
	   faces[face_count][1]=second-1;
	   faces[face_count++][2]=third-1;
	   
	   faces[face_count][0]=first-1;
	   faces[face_count][1]=third-1;
	   faces[face_count++][2]=fourth-1;
	   
	   
   }
   public void emitFace(int first,int firstvn,int second,int secondvn,int third,int thirdvn)
   {
	   faces[face_count][0]=first-1;
	   faces[face_count][1]=second-1;
	   faces[face_count][2]=third-1;
	   faces[face_count][3]=firstvn-1;
	   faces[face_count][4]=secondvn-1;
	   faces[face_count++][5]=thirdvn-1;
	   
   }
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
			
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		moving=1;
		startx=arg0.getX();
		starty=arg0.getY();
	
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
			moving=0;
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
int x,y;
		
		x=arg0.getX();
		y=arg0.getY();
			angy=angy+(x-startx);
			angz=angz+(y-starty);
			startx=x;
			starty=y;
			repaint();
	
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
} //end of class






