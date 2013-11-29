
import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;

class Piece{
	boolean white;
	Ivector position;
	TileID tile;
	
	public Piece(boolean white, TileID tile){
		if(!white)
			System.out.println("fail");
		this.white = white;
		this.tile = tile;
		position = tile.centerCoord();
	}
	
	public void draw(){
		if(white)
			glColor4f(1, 1, 1, 1);
		else
			glColor4f(0, 0, 0, 1);
		
		glPushMatrix();
		
		glTranslatef(position.x, position.y, 0);
		glBegin(GL_TRIANGLE_STRIP);
		glVertex2f(-20, -20);
		glVertex2f(-20, 20);
		glVertex2f(20, -20);
		glVertex2f(20, 20);
		glEnd();
		
		glPopMatrix();
	}
}

public class Player{
	private boolean white, ai;
	private ArrayList<Piece> pieces = new ArrayList<Piece>();
	
	public void init(boolean white, boolean ai){
		
		this.white = white;
		this.ai = ai;
	}
	
	public void update(){
		if(!white)
			System.out.println("fail");
		if(ai)
			aiUpdate();
		else
			playerUpdate();
	}
	
	public void aiUpdate(){
		
	}
	
	public void playerUpdate(){
		if(InputHandler.leftMouseDown())
			tryToAddPiece();
	}
	
	public void tryToAddPiece(){
		Piece tempPiece;
		if(!white)
			System.out.println("fail");
		tempPiece = Board.placePiece(InputHandler.mouse(), white);
		if(tempPiece != null){
			pieces.add(tempPiece);
			System.out.println("added");
		}
	}
	
	public void drawPieces(){
		for(int a = 0; a < pieces.size(); a++)
			pieces.get(a).draw();
	}
}
