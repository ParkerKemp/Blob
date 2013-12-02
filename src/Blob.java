
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;

public class Blob {
	//private static Human player = new Human();
	private static Board board = new Board();
	static int i = 0;
	static boolean aiTurn = false;
	
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
		
		//Initialize player with white = true and AI = false
		//player.init();
		//board.human.init(Color.WHITE);
		//board.ai.init(Color.BLACK);
		
		board.init();
		InputHandler.create();
		
		
	}
	
	public static void update(){
		InputHandler.update();
		if(aiTurn){
			board.ai.update(board);
			aiTurn = false;
		}
		else
			board.human.update(board);
	}
	
	public static void draw(){

		board.draw();
		
		drawPieces();
		
		Display.update();
	}

	public static void drawPieces(){
		//This function will also contain ai.drawPieces
		
		// See Player class.
		//player.drawPieces();
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
		
		//Set scissor area (for drawing the board easily)
		glScissor(85, 85, 630, 630);
	}
}
