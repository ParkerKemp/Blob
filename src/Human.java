public class Human extends Player{
	private Piece grabbedPiece;
	private boolean moving = false;

	public Human(Color color){
		this.color = color;
	}
	
	public void init(){
	
	}
	
	public void update(){
		if(!moving)
			normalState();
		else
			movingState();
	}
	
	public void normalState(){
		//On click, try to add a piece
		if(InputHandler.leftMouseDown())
			if(Board.tileIsEmpty(TileID.fromCoord(InputHandler.mouse())))
				addPiece();
			else if((grabbedPiece = Board.pieceAt(TileID.fromCoord(InputHandler.mouse()))).owner == this)
				moving = true;
	}
	
	public void movingState(){
		if(InputHandler.leftMouseUp())
			moving = false;
		grabbedPiece.position = InputHandler.mouse();
	}
	
	private void addPiece(){
		Move move = new Move();
		move.destination = TileID.fromCoord(InputHandler.mouse());
		if(Board.tryMove(move, this))
			Blob.aiTurn = true;
	}
}
