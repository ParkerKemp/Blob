
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

		//Board.draw();
		
		drawPieces();
		
		Display.update();
	}

	public static void drawPieces(){
		
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
