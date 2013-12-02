import java.util.ArrayList;


public class MiniMaxNode {
	public Board board;
	public Move move;
	public ArrayList<MiniMaxNode> children = new ArrayList<MiniMaxNode>();
	
	public MiniMaxNode(Board board){
		this.board = board;
		this.move = null;
	}
	
	public MiniMaxNode(){
		move = null;
	}
}
