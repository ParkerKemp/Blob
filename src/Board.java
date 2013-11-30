import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;

public class Board {
	
	// (0, 0) is bottom left corner, (6, 6) is top right.
	private static Piece[][] tiles = new Piece[7][7];

	public static void init(){
		// Empty game squares
		for(int a = 0; a < 7; a++)
			for(int b = 0; b < 7; b++)
				tiles[a][b] = null;
		
		// Initialize game board with starting pieces
		tiles[0][0] = new Piece(true, new TileID(0, 0));
		tiles[6][6] = new Piece(true, new TileID(6, 6));
		
		tiles[0][6] = new Piece(false, new TileID(0, 6));
		tiles[6][0] = new Piece(false, new TileID(6, 0));
	}
	
	public static boolean onBoard(Ivector l){
		//Checks whether the given display coords are on the board
		return (l.x > 85 && l.x < 715) && (l.y > 85 && l.y < 715);
	}
	
	public static boolean tileExists(TileID tile) {
		// Checks if the given tile coords are not out of range
		
		if (null == tile)
			return false;
		
		if (tile.x > 6 || tile.y > 6 || tile.x < 0 || tile.y < 0)
			return false;
		
		return true;
	}
	
	public static Piece placePiece(Ivector l, boolean white){
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
		if (!moveIsValid(tile, white))
			return null;

		//If we make it this far, we're golden
		Piece piece = new Piece(white, tile);
		
		//Save the reference in the tile grid
		tiles[tile.x][tile.y] = piece; 
		
		//Return the reference for Player to keep
		return piece;
	}
	
	public static boolean tileIsEmpty(TileID tile){
		//Checks whether a tile is occupied or not.
		//The TileID must be valid, i.e. not outside of [7][7] array bounds
		
		return tiles[tile.x][tile.y] == null;
	}
	

	public static ArrayList<TileID> adjacentTiles(TileID tile) {
		// returns tiles empty and adjacent to a given tile. Tiles are considered adjacent if immediately next to or are in a 2 square radius.
		TileID adjacentTiles [] = {
			new TileID(tile.x - 1, tile.y - 1),
			new TileID(tile.x -1, tile.y),
			new TileID(tile.x -1, tile.y + 1),
			new TileID(tile.x, tile.y - 1),
			new TileID(tile.x, tile.y + 1),
			new TileID(tile.x + 1, tile.y - 1),
			new TileID(tile.x + 1, tile.y),
			new TileID(tile.x + 1, tile.y + 1),
			
			new TileID(tile.x - 2, tile.y - 2),
			new TileID(tile.x -2, tile.y),
			new TileID(tile.x -2, tile.y + 2),
			new TileID(tile.x, tile.y - 2),
			new TileID(tile.x, tile.y + 2),
			new TileID(tile.x + 2, tile.y - 2),
			new TileID(tile.x + 2, tile.y),
			new TileID(tile.x + 2, tile.y + 2),
		};
		
		ArrayList <TileID> openTiles = new ArrayList <TileID> ();
		
		for (TileID currentTile: adjacentTiles) {
			if (tileExists(currentTile))
				openTiles.add(currentTile);
		}
		
		return openTiles;
	}
	
	public static ArrayList <TileID> availableMoves(boolean isWhite) {
		// returns all moves for white or black
		ArrayList <TileID> availableMoves  = new ArrayList <TileID> ();
		
		for (Piece[] pieceArray : tiles) {
			for (Piece piece : pieceArray) {
				
				if (null != piece && piece.isWhite() == isWhite) {
					TileID currentTile = piece.getTile();
					for (TileID openTile : adjacentTiles(currentTile)) {
						availableMoves.add(openTile);
					}
				}
					
			}
		}
		return availableMoves;
	}

	public static boolean moveIsValid(TileID newMove, boolean isWhite) {
		// returns whether or not a move is valid
		if (!tileExists(newMove)) 
			return false;
		
		ArrayList<TileID> availableMoves = availableMoves(isWhite);
		
		if (null == availableMoves)
			return false;	// Special Case: No available Moves!
		
		for (TileID availableMove : availableMoves) {
			if (newMove.x == availableMove.x && newMove.y == availableMove.y)
				return true;
		}
		
		return false;
	}
	
	public static void draw(){
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
