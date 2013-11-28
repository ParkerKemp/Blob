
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;

public class Blob {
	public static void main(String[] args){
		setUpDisplay();
		initOpenGL();
		
		while(!Display.isCloseRequested()){
			update();
			draw();
		}
		
	}
	
	public static void update(){
		
	}
	
	public static void draw(){

		
		drawBoard();
		
		Display.update();
	}
	
	public static void drawBoard(){
		
		glDisable(GL_SCISSOR_TEST);
		
		glClearColor(0f, 0f, 0f, 1);
		glClear(GL_COLOR_BUFFER_BIT);
		
		glEnable(GL_SCISSOR_TEST);
		
		glClearColor(.9f, .9f, .9f, 1);
		glClear(GL_COLOR_BUFFER_BIT);
		
		glColor4f(0, 0, 0, 1);
		glBegin(GL_LINES);
		
		for(int a = 0; a < 10; a++){
			glVertex2f(85, a * 70 + 85);
			glVertex2f(715, a * 70 + 85);
			glVertex2f(a * 70 + 85, 85);
			glVertex2f(a * 70 + 85, 715);
		}
		glEnd();
	}
	
	private static void setUpDisplay(){
		try {
			Display.setDisplayMode(new DisplayMode(800, 800));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}

	private static void initOpenGL() {
		glMatrixMode(GL_PROJECTION);
		glOrtho(0, 800, 0, 800, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		
		glScissor(85, 85, 630, 630);
	}
}
