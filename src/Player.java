
import java.util.ArrayList;

public class Player{

	protected ArrayList<Piece> pieces = new ArrayList<Piece>();
	protected PieceColor color;
	
	public Player(){
		
	}
	
	public Player(PieceColor color){
		this.color = color;
	}

	protected ArrayList<Move> availableMoves(){
		//Return an ArrayList of available (validated) moves
		
		ArrayList<Move> moves = new ArrayList<Move>();
		boolean[][] adjacenceFlags = new boolean[7][7];
		
		//Set all flags to true
		for(int a = 0; a < 7; a++)
			for(int b = 0; b < 7; b++)
				adjacenceFlags[a][b] = true;
		
		//Find all moves that add a piece at an adjacent tile
		for(Piece piece: pieces)
			for(Tile tile: piece.tile.adjacentTiles())
				if(Board.tileIsEmpty(tile)){
					
					//Check if tile is already spoken for before adding
					if(adjacenceFlags[tile.x][tile.y])
						moves.add(new Move(null, tile));
					
					//Set flag
					adjacenceFlags[tile.x][tile.y] = false;
				}
		
		//Find all moves that jump a piece 2 spaces
		for(Piece piece: pieces)
			for(Tile tile: piece.tile.jumpTiles())
				if(Board.tileIsEmpty(tile))
					moves.add(new Move(piece.tile, tile));
				
		return moves;
	}
	
	protected ArrayList<Tile> validSpawns(){
		//Return an ArrayList of available (validated) tileIDs to spawn to
		
		ArrayList<Tile> spawns = new ArrayList<Tile>();
		boolean[][] adjacenceFlags = new boolean[7][7];
		
		//Set all flags to true
		for(int a = 0; a < 7; a++)
			for(int b = 0; b < 7; b++)
				adjacenceFlags[a][b] = true;
		
		//Find all moves that add a piece at an adjacent tile
		for(Piece piece: pieces)
			for(Tile tile: piece.tile.adjacentTiles())
				if(Board.tileIsEmpty(tile)){
					
					//Check if tile is already spoken for before adding
					if(adjacenceFlags[tile.x][tile.y])
						spawns.add(tile);
					
					//Set flag
					adjacenceFlags[tile.x][tile.y] = false;
				}
				
		return spawns;
	}
	
	protected ArrayList<Tile> validMoves() {
		// Returns all valid moves 
		ArrayList <Tile> moves = new ArrayList<Tile>();
		boolean[][] adjacenceFlags = new boolean[7][7];
		
		//Set all flags to true
		for(int a = 0; a < 7; a++)
			for(int b = 0; b < 7; b++)
				adjacenceFlags[a][b] = true;
		
		//Find all moves that jump a piece 2 spaces
		for(Piece piece: pieces)
			for(Tile tile: piece.tile.jumpTiles())
				if(Board.tileIsEmpty(tile))
					moves.add(tile);
		
		return moves;
	}
}
