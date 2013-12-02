import java.util.ArrayList;

public class AI extends Player{

	
	public AI(Color color){
		this.color = color;
	}
	
	public void update(Board board){
		Move move = minimax(board);
		//System.out.println("Final move is at " + move.destination.x + " " + move.destination.y);
		board.makeMove(move, this);
	}
	
	private Move minimax(Board board){
		Move bestMove = null;
		int bestValue = -1;
		for(Move move: availableMoves(board)){
			board.makeMove(move, this);
			if(maxMove(board, 0) > bestValue || bestMove == null)
				bestMove = move;
			
			board.revertMove(move);
		}
		return bestMove;
	}

	private int maxMove(Board board, int depth){
		int temp, bestValue = -1;
		if(board.gameOver() || depth >= 2)
			return board.evaluate();
		
		for(Move testMove: availableMoves(board)){
			board.makeMove(testMove, this);
			if(bestValue < (temp = minMove(board, depth + 1)))
				bestValue = temp;
			
			board.revertMove(testMove);
		}
		return bestValue;
	}
	
	private int minMove(Board board, int depth){
		int temp, bestValue = 1;
		
		for(Move testMove: board.human.availableMoves(board)){
			board.makeMove(testMove, board.human);
			if(bestValue > (temp = maxMove(board, depth + 1)))
				bestValue = temp;

			board.revertMove(testMove);
		}
		return bestValue;
	}
}
