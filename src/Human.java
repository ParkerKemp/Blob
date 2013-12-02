public class Human extends Player{

	public Human(Color color){
		this.color = color;
	}
	
	public void init(){
	
	}
	
	public void update(Board board){
		//!!! Needs a special case for no possible moves.
		
		//On click, try to add a piece
		if(InputHandler.leftMouseDown()){
			Move move = new Move();
			move.destination = TileID.fromCoord(InputHandler.mouse());
			if(board.tryMove(move, this))
				Blob.aiTurn = true;
		}			
	}
}
