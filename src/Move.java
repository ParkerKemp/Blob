
public class Move {
	public TileID source, destination;
	public int value;
	
	public Move(){
		source = null;
		destination = null;
		value = 0;
	}
	
	public Move deepCopy(){
		Move move = new Move();
		if(source != null)
			move.source = source.deepCopy();
		else
			move.source = null;
		
		if(destination != null)
			move.destination = destination.deepCopy();
		else move.destination = null;
		return move;
	}
	
	public Move(TileID source, TileID destination){
		this.source = source;
		this.destination = destination;
		value = 0;
	}
}
