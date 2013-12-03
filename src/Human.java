public class Human extends Player{

	public Human(Color color){
		this.color = color;
	}
	
	public void init(){
	
	}
	
	public void update(){
		//!!! Needs a special case for no possible moves.
		
		//On click, try to add a piece
		if(InputHandler.leftMouseDown()){
			Move move = new Move();
			move.destination = TileID.fromCoord(InputHandler.mouse());
			
			for (TileID currentTile : validSpawns()) {
				if (move.destination.x == currentTile.x && move.destination.y == currentTile.y)
					if(Board.tryMove(move, this))
						Blob.aiTurn = true;
			}
			
		}			
	}
}
