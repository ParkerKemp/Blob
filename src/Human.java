public class Human extends Player{
	private Piece grabbedPiece;
	private boolean moving = false;
	private Ivector mouse;

	public Human(PieceColor color){
		this.color = color;
	}
	
	public void init(){
	
	}
	
	public void update(){
		// Flip mouse coord
		mouse = InputHandler.mouse();
		mouse.y = 800 - mouse.y;
		
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
			if(Board.tileIsEmpty(Tile.fromCoord(mouse)))
				addPiece();
			else if((grabbedPiece = Board.pieceAt(Tile.fromCoord(mouse))).owner == this)
				moving = true;
	}
	
	public void movingState(){
		// Player is moving a piece to a spot 2 squares away.
		grabbedPiece.position = mouse;
		
		if(InputHandler.leftMouseUp()){
			movePiece();
			moving = false;
			grabbedPiece.position = grabbedPiece.tile.centerCoord();
		}
	}
	
	private void movePiece(){
		// actually move the piece
		Move move = new Move();
		move.destination = Tile.fromCoord(mouse);
		move.source = grabbedPiece.tile;
		if(validJump(move))
			if(Board.tryMove(move, this)) {
				Blob.aiTurn = true;
				Blob.updateLabel("Player moves from " + move + ".");
			}
	}
	
	private boolean validJump(Move move){
		// return false if invalid
		for(Move testMove: availableMoves())
			if(move.equals(testMove))
				return true;
		return false;
	}
	
	private void addPiece(){
		Move move = new Move();
		move.destination = Tile.fromCoord(mouse);
			
		for (Tile currentTile : validSpawns()) {
			if (move.destination.x == currentTile.x && move.destination.y == currentTile.y)
				if(Board.tryMove(move, this)) {
					Blob.aiTurn = true;
					Blob.updateLabel("Player spawn at " + move + ".");
				}
					
		}
	}
	
	public boolean isMoving() {
		return moving;
	}
	
	public Piece getGrabbedPiece() {
		return grabbedPiece;
	}
}
