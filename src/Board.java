import static org.lwjgl.opengl.GL11.*;

class TileID{
	public int x, y;
	
	public Ivector centerCoord(){
		Ivector i = new Ivector();
		i.x = x * 90 + 130;
		i.y = y * 90 + 130;
		return i;
	}
	
	public static TileID fromCoord(Ivector l){
		TileID tile = new TileID();
		tile.x = ((int)l.x - 85) / 90;
		tile.y = ((int)l.y - 85) / 90;
		return tile;
	}
}

public class Board {
	
	private static Piece[][] tiles = new Piece[7][7];

	public static void init(){
		for(int a = 0; a < 7; a++)
			for(int b = 0; b < 7; b++)
				tiles[a][b] = null;
	}
	
	public static boolean onBoard(Ivector l){
		//Checks whether the given coords are on the board
		
		return (l.x > 85 && l.x < 715) && (l.y > 85 && l.y < 715);
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
	}
}
