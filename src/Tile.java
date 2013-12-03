import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;
import java.util.ArrayList;

class Tile{
	// This class identifies a board tile, and can be defined explicitly or from window coordinates
	
	public int x, y;
	
	public Tile(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Tile () {}
	
	public boolean equals(Tile tile){
		return x == tile.x && y == tile.y;
	}

	public Ivector centerCoord(){
		Ivector i = new Ivector();
		i.x = x * 90 + 130;
		i.y = y * 90 + 130;
		return i;
	}
	
	public static Tile fromCoord(Ivector l){
		Tile tile = new Tile();
		tile.x = ((int)l.x - 85) / 90;
		tile.y = ((int)l.y - 85) / 90;
		return tile;
	}
	
	public ArrayList<Tile> adjacentTiles() {
		// returns tiles empty and adjacent to a given tile. Tiles are considered adjacent if immediately next to or are in a 2 square radius.
		
		ArrayList<Tile> tiles = new ArrayList<Tile>();
		
		int 	leftBound = Math.max(x - 1, 0), 
				rightBound = Math.min(x + 1, 6), 
				upperBound = Math.min(y + 1, 6), 
				lowerBound = Math.max(y - 1, 0);
		
		for(int a = leftBound; a <= rightBound; a++)
			for(int b = lowerBound; b <= upperBound; b++)
				tiles.add(new Tile(a, b));
		
		//System.out.println("Adjacent tiles: " + tiles.size());
		
		return tiles;
	}
	
	public ArrayList<Tile> jumpTiles() {
		// returns tiles empty and adjacent to a given tile. Tiles are considered adjacent if immediately next to or are in a 2 square radius.
		
		ArrayList<Tile> tiles = new ArrayList<Tile>();
		
		int 	leftBound = x - 2, 
				rightBound = x + 2, 
				upperBound = y + 2, 
				lowerBound = y - 2;
		
		if(x < 2)
			leftBound = x;
		else if(x > 4)
			rightBound = x;
		
		if(y < 2)
			lowerBound = y;
		else if(y > 4)
			upperBound = y;
		
		for(int a = leftBound; a <= rightBound; a += 2)
			for(int b = lowerBound; b <= upperBound; b += 2)
				tiles.add(new Tile(a, b));
		
		//System.out.println("Jump tiles: " + tiles.size());
		
		return tiles;
	}
	
	public void draw(Graphics g, boolean specialOutline) {
		// draw the tile (for predicted moves)
		Ivector drawCoordinates = centerCoord();
		
		// draw light green fill
		g.setColor(new Color(.5f, 1f, .5f));
		g.fillRect(drawCoordinates.x - 45, drawCoordinates.y - 45, 90, 90);

		
		// Draw correct outline
		if (!specialOutline)
			g.setColor(new Color(.2f, .2f, 1f));
		else
			g.setColor(Color.red);
		g.drawLine(drawCoordinates.x - 45, drawCoordinates.y - 45, drawCoordinates.x + 45, drawCoordinates.y - 45);
		g.drawLine(drawCoordinates.x + 45, drawCoordinates.y - 45, drawCoordinates.x + 45, drawCoordinates.y + 45);
		g.drawLine(drawCoordinates.x + 45, drawCoordinates.y + 45, drawCoordinates.x - 45, drawCoordinates.y + 45);
		g.drawLine(drawCoordinates.x - 45, drawCoordinates.y + 45, drawCoordinates.x - 45, drawCoordinates.y - 45);
	}
	
	public String toString() {
		return "(" + Util.toChar(x) + ", " + y + ")";
	}
}