import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import java.util.ArrayList;

enum PieceColor{WHITE, BLACK};

public class Board {
	
	// (0, 0) is bottom left corner, (6, 6) is top right.
	private static Piece[][] tiles = new Piece[7][7];
	public static Human human;
	public static AI ai; 

	public static void init(){
		
		human = new Human(PieceColor.WHITE);
		ai = new AI(PieceColor.BLACK);
		
		// Empty game squares
		for(int a = 0; a < 7; a++)
			for(int b = 0; b < 7; b++)
				tiles[a][b] = null;
		
		//Add initial pieces
		addPiece(new Tile(0, 0), human);
		addPiece(new Tile(6, 6), human);
		
		addPiece(new Tile(0, 6), ai);
		addPiece(new Tile(6, 0), ai);
	}
	
	public static boolean onBoard(Ivector l){
		//Checks whether the given display coords are on the board
		return (l.x > 85 && l.x < 715) && (l.y > 85 && l.y < 715);
	}
	
	public static boolean tileExists(Tile tile) {
		// Checks if the given tile coords are not out of range
		
		if (null == tile)
			return false;
		
		if (tile.x > 6 || tile.y > 6 || tile.x < 0 || tile.y < 0)
			return false;
		
		return true;
	}
	
	public static boolean tryMove(Move move, Player player){
		// return false if move cannot be made
		if(!tileExists(move.destination))
			return false;
		
		if(!tileIsEmpty(move.destination))
			return false;
		
		if(move.source != null && (tileIsEmpty(move.source) || !tileExists(move.source)))
			return false;
		
		makeMove(move, player);
		return true;
	}

	
	public static boolean tileIsEmpty(Tile tile){
		//Checks whether a tile is occupied or not.
		//The TileID must be valid, i.e. not outside of [7][7] array bounds
		
		return null == tiles[tile.x][tile.y];
	}
	
	public static boolean gameOver(){
		//Return true if board is full
		
		return human.pieces.size() + ai.pieces.size() >= 49;
	}
	
	public static void movePiece(Tile source, Tile destination){
		//Move a piece from source tile to destination tile (move must be validated already)
		
		tiles[destination.x][destination.y] = tiles[source.x][source.y];
		tiles[source.x][source.y] = null;
		tiles[destination.x][destination.y].setTile(destination); 
	}
	
	public static void addPiece(Tile destination, Player player){
		//Add a piece to destination tile belonging to the given player
		
		player.pieces.add(tiles[destination.x][destination.y] = new Piece(destination, player));
	}
	
	public static void makeMove(Move move, Player player){
		//Make a move. Move must be validated before it's passed to this function
		
		if(move.source != null)
			movePiece(move.source, move.destination);
		else
			addPiece(move.destination, player);
	}
	
	public static void revertMove(Move move){
		//Make the move in reverse (assumes that it was the last move made)
		
		if(move.source != null)
			movePiece(move.destination, move.source);
		else{
			
			//Remove last element from player pieces, nullify reference in tiles array
			Piece temp = tiles[move.destination.x][move.destination.y];
			temp.owner.pieces.remove(temp.owner.pieces.size() - 1);
			tiles[move.destination.x][move.destination.y] = null;
		}
	}
	
	public static Piece pieceAt(Tile tile){
		return tiles[tile.x][tile.y];
	}
	
	public static int evaluate(){
		//Return board value from AI's perspective. Value is the difference between AI's pieces and human's pieces
		return ai.pieces.size() - human.pieces.size();
	}
	
	public static void draw(Graphics g){
		
		//Draw the board
		g.setColor(Color.green);
		g.fillRect(85, 85, 630, 630);
		
		//Draw grid
		g.setColor(Color.black);
		
		for(int a = 0; a < 8; a++){
			//Horizontal
			g.drawLine(85, a * 90 + 85, 715, a * 90 + 85);
			
			//Vertical
			g.drawLine(a * 90 + 85, 85, a * 90 + 85, 715);
		}
		
		//Draw Labels.
		for(int x = 0; x < 7; x ++) {
			Blob.regularFont.drawString(85 + 85/2 + x * 90 - Blob.regularFont.getWidth("" + Util.toChar(x)) / 2, 720f, "" + Util.toChar(x), Color.white);
		}
		for(int y = 0; y < 7; y ++) {
			Blob.regularFont.drawString(70 - Blob.regularFont.getWidth("" + y) / 2, 85 + 85/2 + y * 90, "" + y, Color.white);
		}
		// Draw spawns or moves.
		if (!human.isMoving())
			for (Tile tile : human.validSpawns())
				tile.draw(g, false);
		else {
			Tile sourceTile = human.getGrabbedPiece().getTile();
			boolean drawTile;
			for (Tile tile : sourceTile.jumpTiles()) {
				drawTile = false;
				
				for(Tile unoccupied : human.validMoves())
					if (tile.equals(unoccupied))
						drawTile = true;
				
				if (drawTile)
					tile.draw(g, true);
			}
		}
		
		//Draw all pieces onto the game board
		for (Piece[] pieceArray : tiles) {
			for (Piece piece : pieceArray) {
				
				if (null != piece) 
					piece.draw(g);
				
			}
		}
	
	}	
}
