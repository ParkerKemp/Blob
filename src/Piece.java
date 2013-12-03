import static org.lwjgl.opengl.GL11.GL_TRIANGLE_STRIP;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class Piece{
	//position is the window coords for rendering, tile is the ID of the tile it's placed on
	
	PieceColor color;
	Ivector position;
	Tile tile;
	Player owner;
	
	public Piece(Tile tile, Player owner){
		this.color = owner.color;
		this.tile = tile;
		this.owner = owner;
		position = tile.centerCoord();
	}
	
	public Piece(){
		
	}
	
	public void setTile(Tile tile){
		this.tile = tile;
		this.position = tile.centerCoord();
	}
	
	public void draw(Graphics g){
		//Translate to position and draw a 40x40 square
		
		//Set color
		if(color == PieceColor.WHITE)
			g.setColor(Color.white);
		else
			g.setColor(Color.black);
		
		g.fillRect(position.x - 20, position.y - 20, 40, 40);
	}
	
	public PieceColor color() {
		return color;
	}
	
	public Tile getTile() {
		return tile;
	}
}