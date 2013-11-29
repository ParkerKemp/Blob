
import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;

class Piece{
	//position is the window coords for rendering, tile is the ID of the tile it's placed on
	
	boolean white;
	Ivector position;
	TileID tile;
	
	public Piece(boolean white, TileID tile){
		this.white = white;
		this.tile = tile;
		position = tile.centerCoord();
	}
	
	public void draw(){
		//Translate to position and draw a 40x40 square
		
		//Set color
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
		if(ai)
			aiUpdate();
		else
			playerUpdate();
	}
	
	public void aiUpdate(){
		//This is where the minimax algorithm needs to go
		
	}
	
	public void playerUpdate(){
		
		//On click, try to add a piece
		if(InputHandler.leftMouseDown())
			tryToAddPiece();
	}
	
	public void tryToAddPiece(){
		//Try to place a piece on the board using mouse location
		
		Piece tempPiece;
		tempPiece = Board.placePiece(InputHandler.mouse(), white);
		
		//If tempPiece is null, then we failed to place the piece
		if(tempPiece != null)
			pieces.add(tempPiece);
	}
	
	public void drawPieces(){
		for(int a = 0; a < pieces.size(); a++)
			pieces.get(a).draw();
	}
}
