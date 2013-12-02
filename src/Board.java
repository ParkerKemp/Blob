import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;

enum Color{WHITE, BLACK};

public class Board {
	
	// (0, 0) is bottom left corner, (6, 6) is top right.
	private Piece[][] tiles = new Piece[7][7];
	//public Human human = new Human();
	public Human human = new Human(Color.WHITE);
	public AI ai = new AI(Color.BLACK);
	private Move lastMove;
	
	public Board deepCopy(){
		Board board = new Board();
		//for(int a = 0; a < 7; a++)
		//	for(int b = 0; b < 7; b++)
		//		board.tiles[a][b] = tiles[a][b].deepCopy();
		board.human = (Human)human.deepCopy();
		board.ai = (AI)ai.deepCopy();
		
		for(Piece piece: board.human.pieces)
			board.tiles[piece.tile.x][piece.tile.y] = piece;
		
		board.lastMove = lastMove.deepCopy();
		return board;
	}

	public void init(){
		// Empty game squares
		for(int a = 0; a < 7; a++)
			for(int b = 0; b < 7; b++)
				tiles[a][b] = null;
		
		// Initialize game board with starting pieces
		//tiles[0][0] = new Piece(new TileID(0, 0), ai);
		//tiles[6][6] = new Piece(new TileID(6, 6), ai);
		addPiece(new TileID(0, 0), ai);
		addPiece(new TileID(6, 6), ai);
		
		addPiece(new TileID(0, 6), human);
		addPiece(new TileID(6, 0), human);
		
		System.out.println("Num pieces at start: " + human.pieces.size());
		//tiles[0][6] = new Piece(new TileID(0, 6), human);
		//tiles[6][0] = new Piece(new TileID(6, 0), human);
	}
	
	public boolean onBoard(Ivector l){
		//Checks whether the given display coords are on the board
		return (l.x > 85 && l.x < 715) && (l.y > 85 && l.y < 715);
	}
	
	public boolean tileExists(TileID tile) {
		// Checks if the given tile coords are not out of range
		
		if (null == tile)
			return false;
		
		if (tile.x > 6 || tile.y > 6 || tile.x < 0 || tile.y < 0)
			return false;
		
		return true;
	}
	
	public boolean tryMove(Move move, Player player){
		if(!tileExists(move.destination))
			return false;
		
		if(!tileIsEmpty(move.destination))
			return false;
		
		if(move.source != null && (tileIsEmpty(move.source) || !tileExists(move.source)))
			return false;
		
		makeMove(move, player);
		return true;
	}
	
	/*public Piece placePiece(Ivector l, Color color){
		//This function attempts to place a piece on a tile using coords from mouse location
		//Returns reference to the piece on success, returns null on failure
		
		//If location isn't on the board, then fail
		if(!onBoard(l))
			return null;
		
		//Convert coords into a tileID
		TileID tile = TileID.fromCoord(l);
		
		//If the tile is already occupied, then fail
		if(!tileIsEmpty(TileID.fromCoord(l)))
			return null;
		
		//If the move is invalid, then fail
		//if (!moveIsValid(tile, white))
		//	return null;

		//If we make it this far, we're golden
		Piece piece = new Piece(color, tile);
		
		//Save the reference in the tile grid
		tiles[tile.x][tile.y] = piece; 
		
		//Return the reference for Player to keep
		return piece;
	}*/
	
	public boolean tileIsEmpty(TileID tile){
		//Checks whether a tile is occupied or not.
		//The TileID must be valid, i.e. not outside of [7][7] array bounds
		
		return tiles[tile.x][tile.y] == null;
	}
	
	public boolean gameOver(){
		//for(int a = 0; a < 6; a++)
		//	for(int b = 0; b < 6; b++)
		//		if(tiles[a][b] == null)
		//			return false;
		//return true;
		return human.pieces.size() + ai.pieces.size() >= 49;
	}
	
	public void movePiece(TileID source, TileID destination){
		tiles[destination.x][destination.y] = tiles[source.x][source.y];
		tiles[source.x][source.y] = null;
		tiles[destination.x][destination.y].setTile(destination); 
	}
	
	public void addPiece(TileID destination, Player player){
		player.pieces.add(tiles[destination.x][destination.y] = new Piece(destination, player));
	}
	
	public void makeMove(Move move, Player player){
		lastMove = move.deepCopy();
		//String string = new String(player.color == Color.WHITE ? "white" : "black");
		if(move.source != null){
			//System.out.println("Move " + string + " piece from (" + move.source.x + ", " + move.source.y + ") to (" + move.destination.x + ", " + move.destination.y + ")");
			movePiece(move.source, move.destination);
		}
		else{
			//System.out.println("Add " + string + " piece at (" + move.destination.x + ", " + move.destination.y + ")");
			
			addPiece(move.destination, player);
		}
	}
	
	public void revertMove(Move move){
		//System.out.println("revert");
		if(move.source != null)
			movePiece(move.destination, move.source);
		else{
			Piece temp = tiles[move.destination.x][move.destination.y];
			temp.owner.pieces.remove(temp.owner.pieces.size() - 1);
			tiles[move.destination.x][move.destination.y] = null;
		}
	}
	
	public boolean winner(Color color){
		int white = 0, black = 0;
		for(int a = 0; a < 7; a++)
			for(int b = 0; b < 7; b++)
				if(tiles[a][b] != null)
					if(tiles[a][b].color == Color.WHITE)
						white++;
					else
						black++;
		
		switch(color){
		case WHITE:
			return white > black;
		case BLACK:
			return black > white;
		default:
			return false;
		}
	}
	
	public int evaluate(){
		//if(ai.pieces.size() == human.pieces.size())
		//	return 0;
		
		//return ai.pieces.size() > human.pieces.size() ? 1 : -1;
		return ai.pieces.size() - human.pieces.size();
	}
	
	public void draw(){
		//Draw the board
		
		//Clear whole screen as black
		glClearColor(0, 0, 0, 1);
		glClear(GL_COLOR_BUFFER_BIT);
		

		glEnable(GL_SCISSOR_TEST);

		//Clear scissored area as green
		glClearColor(0, 1, 0, 1);
		glClear(GL_COLOR_BUFFER_BIT);
		
		//Draw grid
		glColor4f(0, 0, 0, 1);
		glBegin(GL_LINES);
		
		for(int a = 0; a < 8; a++){
			
			//Horizontal
			glVertex2f(85, a * 90 + 85);
			glVertex2f(715, a * 90 + 85);
			
			//Vertical
			glVertex2f(a * 90 + 85, 85);
			glVertex2f(a * 90 + 85, 715);
		}
		glEnd();
		
		glDisable(GL_SCISSOR_TEST);
		
		for (Piece[] pieceArray : tiles) {
			for (Piece piece : pieceArray) {
				if (null != piece)
					piece.draw();
			}
		}
	}
}
