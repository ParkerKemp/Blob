import static org.lwjgl.opengl.GL11.*;

public class Board {
	
	private static int tiles[][];
	
	public static void draw(){
		glDisable(GL_SCISSOR_TEST);
		
		glClearColor(0, 0, 0, 1);
		glClear(GL_COLOR_BUFFER_BIT);
		
		glEnable(GL_SCISSOR_TEST);
		
		glClearColor(0, 1, 0, 1);
		glClear(GL_COLOR_BUFFER_BIT);
		
		glColor4f(0, 0, 0, 1);
		glBegin(GL_LINES);
		
		for(int a = 0; a < 8; a++){
			glVertex2f(85, a * 90 + 85);
			glVertex2f(715, a * 90 + 85);
			glVertex2f(a * 90 + 85, 85);
			glVertex2f(a * 90 + 85, 715);
		}
		glEnd();
	}
}
