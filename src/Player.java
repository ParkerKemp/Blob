
import static org.lwjgl.opengl.GL11.*;

class Piece{
	boolean white;
	float x, y;
	
	public void draw(){
		glTranslatef(x, y, 0);
		glBegin(GL_TRIANGLE_STRIP);
		glVertex2f(x - 30, y - 30);
		glVertex2f(x - 30, y + 30);
		glVertex2f(x + 30, y - 30);
		glVertex2f(x + 30, y + 30);
		glEnd();
	}
}

public class Player {
	private boolean white, ai;
	private Piece[] pieces;
	
	public void init(boolean white, boolean ai){
		this.white = white;
		this.ai = ai;
		for(int a = 0; a < 49; a++)
			pieces[a] = new Piece();
	}
	
	public void update(){
		
	}
	
	public void drawPieces(){
		for(int a = 0; a < pieces.length; a++)
			pieces[a].draw();
	}
}
