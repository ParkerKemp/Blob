
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.Color;
import java.awt.Font;

public class Blob {
	static boolean aiTurn = false;
	static TrueTypeFont font;
	
	public static void main(String[] args){
		init();
		setUpDisplay();
		initOpenGL();
		
		while(!Display.isCloseRequested()){
			update();
			draw();
		}
	}
	
	public static void init(){
		font = new TrueTypeFont(new Font("Times New Roman", Font.BOLD, 24), false);
		Board.init();
		InputHandler.create();
	}
	
	public static void update(){
		InputHandler.update();
		if(aiTurn){
			Board.ai.update();
			aiTurn = false;
		}
		else
			Board.human.update();
	}
	
	public static void draw(){

		Board.draw();

		Display.update();
		font.drawString(100, 100, "TESTING", Color.white);
	}
	
	private static void setUpDisplay(){
		try {
			Display.setDisplayMode(new DisplayMode(800, 800));
			Display.create();
		} catch (LWJGLException e){
			e.printStackTrace();
		}
	}

	private static void initOpenGL() {
		glMatrixMode(GL_PROJECTION);
		glOrtho(0, 800, 0, 800, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		
		//Set scissor area (for drawing the board easily)
		glScissor(85, 85, 630, 630);
	}
}
