import static org.lwjgl.opengl.GL11.GL_TRIANGLE_STRIP;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

public class Piece{
	//position is the window coords for rendering, tile is the ID of the tile it's placed on
	
	Color color;
	Ivector position;
	TileID tile;
	Player owner;
	
	public Piece(TileID tile, Player owner){
		this.color = owner.color;
		this.tile = tile;
		this.owner = owner;
		position = tile.centerCoord();
	}
	
	public Piece(){
		
	}
	
	public void setTile(TileID tile){
		this.tile = tile;
		this.position = tile.centerCoord();
	}
	
	public void draw(){
		//Translate to position and draw a 40x40 square
		
		//Set color
		if(color == Color.WHITE)
			glColor4f(1, 1, 1, 1);
		else
			glColor4f(0, 0, 0, 1);
		
		glPushMatrix();
		
		glTranslatef(position.x, position.y, 0);
		glBegin(GL_TRIANGLE_STRIP);
		glVertex2f(-20, -20);
		glVertex2f(-20, 20);
		glVertex2f(20, -20);
		glVertex2f(20, 20);
		glEnd();
		
		glPopMatrix();
	}
	
	public Color color() {
		return color;
	}
	
	public TileID getTile() {
		return tile;
	}
}