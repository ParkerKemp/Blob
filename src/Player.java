
import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;


public class Player{
	private boolean aiTurn;
	
	public void init(boolean aiTurn){
		this.aiTurn = aiTurn;
	}
	
	public void update(){
		if(aiTurn)
			aiUpdate();
		else
			playerUpdate();
	}
	
	public void aiUpdate(){
		//This is where the minimax algorithm needs to go
		
	}
	
	public void playerUpdate(){
		//!!! Needs a special case for no possible moves.
		
		//On click, try to add a piece
		if(InputHandler.leftMouseDown())
			// If successful, change turns.
			if (addPiece());
				//aiTurn = true;
				
	}
	
	public boolean addPiece(){
		//Try to place a piece on the board using mouse location, returns true if successful.
		
		Piece tempPiece;
		tempPiece = Board.placePiece(InputHandler.mouse(), true);
		
		if (null != tempPiece)
			return true;
		else
			return false;
	}
	
	// Keeping two different lists of pieces on the board to be drawn separately seems unnecessary.
	// We can keep a running list in the Player, but I feel like they should all be drawn the same.
	/*public void drawPieces(){
		for(int a = 0; a < pieces.size(); a++)
			pieces.get(a).draw();
	}*/
}
