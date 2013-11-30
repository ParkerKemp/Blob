class TileID{
	// This class converts an x,y board coordinate into real drawing space coordinates. (Correct me if I'm wrong, parker)
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
	
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}