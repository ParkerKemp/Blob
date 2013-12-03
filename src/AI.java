import java.util.ArrayList;
import java.util.Random;

public class AI extends Player{

	
	public AI(Color color){
		this.color = color;
	}
	
	public void update(){
		//Unlike a normal update() function, this is not incremental
		
		if (availableMoves().size() > 0) {
			//Pick the best move (or one of them)
			Move move = pickMove();
			
			//Make a move
			Board.makeMove(move, this);
		}
	}
	
	private Move pickMove(){
		//Use alpha-beta minimax to find one of the best moves to make
		
		ArrayList<Move> bestMoves = new ArrayList<Move>();
		Random rand = new Random();
		int tempValue, bestValue = -100;
		
		for(Move move: availableMoves()){
			
			//Try a move
			Board.makeMove(move, this);
			
			//Run alphaBeta algorithm
			tempValue = miniMaxAlphaBeta(4, -100, 100, false);
			
			//Check if new move is worthy, or if it's the first one
			if(tempValue >= bestValue || bestMoves.size() == 0){
				
				//If new move is of higher value, then reset list of moves
				//If not, then it's equal value, so keep list
				if(tempValue > bestValue)
					bestMoves.clear();
				
				bestMoves.add(move);
				bestValue = tempValue;
			}
			
			//Reverse move to restore board to normal state
			Board.revertMove(move);
		}
		
		//bestMoves contains all the best moves (equal value), so pick one randomly
		return bestMoves.get(rand.nextInt(bestMoves.size()));
	}
	
	private int miniMaxAlphaBeta(int depth, int a, int b, boolean maximize){
		//Alpha-beta pruning minimax algorithm
		
		//If board is full or depth limit is reached, then evaluate board
		if(Board.gameOver() || depth == 0)
			return Board.evaluate();
		
		//Maximize player
		if(maximize){
			for(Move testMove: availableMoves()){
				
				//Try a move
				Board.makeMove(testMove, this);
				
				//Go deeper
				a = Math.max(a, miniMaxAlphaBeta(depth - 1, a, b, false));
				
				//Revert move to restore board to previous state
				Board.revertMove(testMove);
				
				if(b <= a)
					break;
			}
			return a;
		}
		
		//Minimize player
		else{
			for(Move testMove: Board.human.availableMoves()){
				
				//Try a move
				Board.makeMove(testMove, Board.human);
				
				//Go deeper
				b = Math.min(b, miniMaxAlphaBeta(depth - 1, a, b, true));
				
				//Revert move to restore board to previous state
				Board.revertMove(testMove);
				
				if (b <= a)
					break;
			}
			return b;
		}
	}
}
