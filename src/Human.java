public class Human extends Player{
	private Piece grabbedPiece;
	private boolean moving = false;

	public Human(Color color){
		this.color = color;
	}
	
	public void init(){
	
	}
	
	public void update(){
		if (availableMoves().size() == 0)
			Blob.aiTurn = true;
		
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
		grabbedPiece.position = InputHandler.mouse();
		
		if(InputHandler.leftMouseUp()){
			movePiece();
			moving = false;
			grabbedPiece.position = grabbedPiece.tile.centerCoord();
		}
	}
	
	private void movePiece(){
		Move move = new Move();
		move.destination = TileID.fromCoord(InputHandler.mouse());
		move.source = grabbedPiece.tile;
		if(validJump(move))
			if(Board.tryMove(move, this))
				Blob.aiTurn = true;
	}
	
	private boolean validJump(Move move){
		for(Move testMove: availableMoves())
			if(move.equals(testMove))
				return true;
		return false;
	}
	
	private void addPiece(){
		Move move = new Move();
		move.destination = TileID.fromCoord(InputHandler.mouse());
			
		for (TileID currentTile : validSpawns()) {
			if (move.destination.x == currentTile.x && move.destination.y == currentTile.y)
				if(Board.tryMove(move, this))
					Blob.aiTurn = true;
		}
	}
}
