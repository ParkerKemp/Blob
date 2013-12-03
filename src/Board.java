import static org.lwjgl.opengl.GL11.*;

enum Color{WHITE, BLACK};

public class Board {
	
	// (0, 0) is bottom left corner, (6, 6) is top right.
	private static Piece[][] tiles = new Piece[7][7];
	public static Human human = new Human(Color.WHITE);
	public static AI ai = new AI(Color.BLACK);

	public static void init(){
		// Empty game squares
		for(int a = 0; a < 7; a++)
			for(int b = 0; b < 7; b++)
				tiles[a][b] = null;
		
		//Add initial pieces
		addPiece(new TileID(0, 0), human);
		addPiece(new TileID(6, 6), human);
		
		addPiece(new TileID(0, 6), ai);
		addPiece(new TileID(6, 0), ai);
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
	
	public static boolean tryMove(Move move, Player player){
		if(!tileExists(move.destination))
			return false;
		
		if(!tileIsEmpty(move.destination))
			return false;
		
		if(move.source != null && (tileIsEmpty(move.source) || !tileExists(move.source)))
			return false;
		
		makeMove(move, player);
		return true;
	}
	
	public static boolean tileIsEmpty(TileID tile){
		//Checks whether a tile is occupied or not.
		//The TileID must be valid, i.e. not outside of [7][7] array bounds
		
		return tiles[tile.x][tile.y] == null;
	}
	
	public static boolean gameOver(){
		//Return true if board is full
		
		return human.pieces.size() + ai.pieces.size() >= 49;
	}
	
	public static void movePiece(TileID source, TileID destination){
		//Move a piece from source tile to destination tile (move must be validated already)
		
		tiles[destination.x][destination.y] = tiles[source.x][source.y];
		tiles[source.x][source.y] = null;
		tiles[destination.x][destination.y].setTile(destination); 
	}
	
	public static void addPiece(TileID destination, Player player){
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
	
	public static int evaluate(){
		//Return board value from AI's perspective. Value is the difference between AI's pieces and human's pieces
		return ai.pieces.size() - human.pieces.size();
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
