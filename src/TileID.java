import java.util.ArrayList;

class TileID{
	// This class identifies a board tile, and can be defined explicitly or from window coordinates
	
	public int x, y;
	
	public TileID(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public TileID () {}

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
	
	public ArrayList<TileID> adjacentTiles() {
		// returns tiles empty and adjacent to a given tile. Tiles are considered adjacent if immediately next to or are in a 2 square radius.
		
		ArrayList<TileID> tiles = new ArrayList<TileID>();
		
		int 	leftBound = Math.max(x - 1, 0), 
				rightBound = Math.min(x + 1, 6), 
				upperBound = Math.min(y + 1, 6), 
				lowerBound = Math.max(y - 1, 0);
		
		for(int a = leftBound; a <= rightBound; a++)
			for(int b = lowerBound; b <= upperBound; b++)
				tiles.add(new TileID(a, b));
		
		//System.out.println("Adjacent tiles: " + tiles.size());
		
		return tiles;
	}
	
	public ArrayList<TileID> jumpTiles() {
		// returns tiles empty and adjacent to a given tile. Tiles are considered adjacent if immediately next to or are in a 2 square radius.
		
		ArrayList<TileID> tiles = new ArrayList<TileID>();
		
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
				tiles.add(new TileID(a, b));
		
		//System.out.println("Jump tiles: " + tiles.size());
		
		return tiles;
	}
	
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}