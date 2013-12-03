
public class Move {
	//Stores data representing a move. If source tile is null, then it's an "add" move (using destination tile)
	//Otherwise, it's a "jump" move from source to destination
	
	public TileID source, destination;
	
	public Move(){
		source = null;
		destination = null;
	}
	
	public Move(TileID source, TileID destination){
		this.source = source;
		this.destination = destination;
	}
	
	public boolean equals(Move move){
		if(source != null && move.source != null)
			return source.equals(move.source) && destination.equals(move.destination);
		else if(source != null || move.source != null)
			return false;
		return source.equals(move.source) && destination.equals(move.destination);
	}
}
