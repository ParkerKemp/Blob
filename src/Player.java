
import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;

public class Player{

	protected ArrayList<Piece> pieces = new ArrayList<Piece>();
	protected Color color;
	
	public Player deepCopy(){
		Player player = new Player();
		player.color = color;
		for(Piece piece: pieces){
			player.pieces.add(piece.deepCopy(player));
		}
		return player;
	}
	
	public Player(){
		
	}
	
	public Player(Color color){
		this.color = color;
	}
	
	public void init(Color color){
		this.color = color;
	}

	public void addPiece(Board board){
		//Try to place a piece on the board using mouse location, returns true if successful.
		
		//pieces.add(board.placePiece(InputHandler.mouse(), Color.WHITE));
		

		
		//if (null != tempPiece)
		//	return true;
		//else
		//	return false;
	}
	
	protected ArrayList<Move> availableMoves(Board board){
		ArrayList<Move> moves = new ArrayList<Move>();
		boolean[][] adjacenceFlags = new boolean[7][7];
		for(int a = 0; a < 7; a++)
			for(int b = 0; b < 7; b++)
				adjacenceFlags[a][b] = true;
		//System.out.println("Num pieces: " + pieces.size());
		for(Piece piece: pieces){
			for(TileID tile: piece.tile.adjacentTiles()){
				if(board.tileIsEmpty(tile)){
					if(adjacenceFlags[tile.x][tile.y])
						moves.add(new Move(null, tile));
					adjacenceFlags[tile.x][tile.y] = false;
				}
			}
		}
		for(Piece piece: pieces){
			for(TileID tile: piece.tile.jumpTiles()){
				if(board.tileIsEmpty(tile))
					moves.add(new Move(piece.tile, tile));
			}
		}
		//System.out.println("Num moves: " + moves.size());
		return moves;
	}
	
	// Keeping two different lists of pieces on the board to be drawn separately seems unnecessary.
	// We can keep a running list in the Player, but I feel like they should all be drawn the same.
	/*public void drawPieces(){
		for(int a = 0; a < pieces.size(); a++)
			pieces.get(a).draw();
	}*/
}
