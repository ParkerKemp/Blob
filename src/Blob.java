
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.Color;

import org.lwjgl.input.Keyboard;

import java.awt.Font;

public class Blob extends BasicGame {


	static boolean aiTurn = false;
	static TrueTypeFont titleFont, regularFont, giantFont, tinyFont;
	static String firstLabel = "";
	static String secondLabel = "";
	
    public static void main(String[] args) throws SlickException
    {
    	// Create new container, start game
         AppGameContainer app = new AppGameContainer(new Blob(""));
         
         app.setDisplayMode(800, 800, false);
         app.setTargetFrameRate(60);
         app.start();
    }
	
	public Blob(String title) {
		super("Blobs");
	}
	
	@Override
	public void init(GameContainer arg0) throws SlickException {
		// Initialize things here
		
		titleFont = new TrueTypeFont(new Font("Arial", Font.BOLD, 24), false);
		regularFont = new TrueTypeFont(new Font("Arial", Font.PLAIN, 20), false);
		giantFont = new TrueTypeFont(new Font("Arial", Font.BOLD, 50), false);
		tinyFont = new TrueTypeFont(new Font("Arial", Font.PLAIN, 17), false);
		firstLabel = "";
		secondLabel = "";
		Board.init();
		InputHandler.create();
		InputHandler.watchKey(Keyboard.KEY_A);
		InputHandler.watchKey(Keyboard.KEY_P);
	}

	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		// ran @ 60 fps. Logic goes here.
		InputHandler.update();
		
		if (!Board.gameOver()) {
			if(aiTurn){
				Board.ai.update();
				aiTurn = false;
			}
			else
				Board.human.update();
		}
		
		// Press P or A to start a new game with Player or AI first.
		if (InputHandler.keyDownEvent(Keyboard.KEY_P)) {
			firstLabel = "";
			secondLabel = "";
			Board.init();
			aiTurn = false;
			InputHandler.create();
			InputHandler.watchKey(Keyboard.KEY_A);
			InputHandler.watchKey(Keyboard.KEY_P);
		} else if (InputHandler.keyDownEvent(Keyboard.KEY_A)) {
			firstLabel = "";
			secondLabel = "";
			Board.init();
			aiTurn = true;
			InputHandler.create();
			InputHandler.watchKey(Keyboard.KEY_A);
			InputHandler.watchKey(Keyboard.KEY_P);
		}
	}
	
	@Override
	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		// draw things here
		
		Board.draw(arg1);
		
		titleFont.drawString(400f - titleFont.getWidth("Blobs!") / 2, 25f, "Blobs!", Color.green);
		tinyFont.drawString(400f - tinyFont.getWidth("Press P to start a new game with the Player going first, or A for the AI to go first.") / 2, 60f, "Press P to start a new game with the Player going first, or A for the AI to go first.", Color.gray);
		
		int playerScore = Board.human.pieces.size();
		int computerScore = Board.ai.pieces.size();
		
		titleFont.drawString(100f - titleFont.getWidth("Player: " + playerScore) / 2, 760f, "Player: " + playerScore, new Color(.4f, .4f, 1f));
		titleFont.drawString(650f - titleFont.getWidth("Computer: " + computerScore) / 2, 760f, "Computer: " + computerScore, Color.red);
		
		regularFont.drawString(400f - regularFont.getWidth(firstLabel) / 2, 750f, firstLabel, Color.white);
		tinyFont.drawString(400f - tinyFont.getWidth(secondLabel) / 2, 770f, secondLabel, Color.gray);
		
		if (Board.gameOver()) {
			if (Board.human.pieces.size() < Board.ai.pieces.size())
				giantFont.drawString(400f - giantFont.getWidth("Computer Wins!") / 2, 400f, "Computer Wins!", Color.red);
			else if (Board.human.pieces.size() > Board.ai.pieces.size())
				giantFont.drawString(400f - giantFont.getWidth("Player Wins!") / 2, 400f, "Player Wins!", Color.blue);
			else
				giantFont.drawString(400f - giantFont.getWidth("Tie Game!" + playerScore) / 2, 400f, "Tie Game!", Color.white); // Won't happen.
		}
	}
	
	public static void updateLabel(String s) {
		secondLabel = new String(firstLabel);
		firstLabel = new String(s);
	}
}
