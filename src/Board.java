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
		return (l.x > 85 && l.x < 715) && (l.y > 85 && l.y < 715);
	}
	
	public static Piece placePiece(Ivector l, boolean white){
		if(!onBoard(l))
			return null;
		
		TileID tile = TileID.fromCoord(l);
		
		if(!tileIsEmpty(TileID.fromCoord(l)))
			return null;
		
		if(!white)
			System.out.println("fail");
		Piece piece = new Piece(white, tile);
		
		tiles[tile.x][tile.y] = piece; 
		
		return piece;
	}
	
	public static boolean tileIsEmpty(TileID tile){
		return tiles[tile.x][tile.y] == null;
	}
	
	public static void draw(){
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
		
		glDisable(GL_SCISSOR_TEST);
	}
}
