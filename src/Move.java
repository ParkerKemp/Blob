
public class Move {
	//Stores data representing a move. If source tile is null, then it's an "add" move (using destination tile)
	//Otherwise, it's a "jump" move from source to destination
	
	public TileID source, destination;
	public int value;
	
	public Move(){
		source = null;
		destination = null;
		value = 0;
	}
	
	public Move(TileID source, TileID destination){
		this.source = source;
		this.destination = destination;
		value = 0;
	}
}
