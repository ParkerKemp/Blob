import java.util.ArrayList;
import java.util.Random;

public class AI extends Player{

	
	public AI(Color color){
		this.color = color;
	}
	
	public void update(Board board){
		Move move = pickMove(board);
		//System.out.println("Final move is at " + move.destination.x + " " + move.destination.y);
		board.makeMove(move, this);
	}
	
	private Move pickMove(Board board){
		ArrayList<Move> bestMoves = new ArrayList<Move>();
		Random rand = new Random();
		//Move bestMove = null;
		int tempValue, bestValue = -100;
		for(Move move: availableMoves(board)){
			board.makeMove(move, this);
			//tempValue = minimax(board, 0, true);
			tempValue = alphaBeta(board, 0, -100, 100, true);
			if(tempValue >= bestValue || bestMoves.size() == 0){
				if(tempValue > bestValue)
					bestMoves.clear();
				bestMoves.add(move);
				bestValue = tempValue;
			}
			
			board.revertMove(move);
		}
		
		return bestMoves.get(rand.nextInt(bestMoves.size()));
	}
	
	private int minimax(Board board, int depth, boolean maximize){
		int temp, bestValue;
		if(board.gameOver() || depth >= 4)
			return board.evaluate();
		
		if(maximize){
			bestValue = -100;
			for(Move testMove: availableMoves(board)){
				board.makeMove(testMove, this);
				if(bestValue < (temp = minimax(board, depth + 1, false)))
					bestValue = temp;
				
				board.revertMove(testMove);
			}
		}
		else{
			bestValue = 100;
			for(Move testMove: availableMoves(board)){
				board.makeMove(testMove, this);
				if(bestValue > (temp = minimax(board, depth + 1, true)))
					bestValue = temp;
				
				board.revertMove(testMove);
			}
		}
		return bestValue;
	}

	private int alphaBeta(Board board, int depth, int a, int b, boolean maximize){
		int temp, bestValue;
		if(board.gameOver() || depth >= 4)
			return board.evaluate();
		
		if(maximize){
			for(Move testMove: availableMoves(board)){
				board.makeMove(testMove, this);
				a = Math.max(a, alphaBeta(board, depth + 1, a, b, false));
				board.revertMove(testMove);
				if(b <= a)
					break;
			}
			return a;
		}
		else{
			for(Move testMove: availableMoves(board)){
				board.makeMove(testMove, this);
				b = Math.min(b, alphaBeta(board, depth + 1, a, b, true));
				//if(bestValue > (temp = minimax(board, depth + 1, true)))
				//	bestValue = temp;
				board.revertMove(testMove);
				if (b <= a)
					break;
			}
			return b;
		}
	}
	
	private int maxMove(Board board, int depth){
		int temp, bestValue = -100;
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
		int temp, bestValue = 100;
		
		for(Move testMove: board.human.availableMoves(board)){
			board.makeMove(testMove, board.human);
			if(bestValue > (temp = maxMove(board, depth + 1)))
				bestValue = temp;

			board.revertMove(testMove);
		}
		return bestValue;
	}
}
