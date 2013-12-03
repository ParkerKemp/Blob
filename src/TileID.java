import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.util.ArrayList;

class TileID{
	// This class identifies a board tile, and can be defined explicitly or from window coordinates
	
	public int x, y;
	
	public TileID(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public TileID () {}
	
	public boolean equals(TileID tile){
		return x == tile.x && y == tile.y;
	}

	public Ivector centerCoord(){
		Ivector i = new Ivector();
		i.x = x * 90 + 130;
		i.y = y * 90 + 130;
		return i;
	}
	
	public static TileID fromCoord(Ivector l){
		TileID tile = new TileID();
		tile.x = ((int)l.x - 85) / 90;
		tile.y = ((int)l.y - 85) / 90;
		return tile;
	}
	
	public ArrayList<TileID> adjacentTiles() {
		// returns tiles empty and adjacent to a given tile. Tiles are considered adjacent if immediately next to or are in a 2 square radius.
		
		ArrayList<TileID> tiles = new ArrayList<TileID>();
		
		int 	leftBound = Math.max(x - 1, 0), 
				rightBound = Math.min(x + 1, 6), 
				upperBound = Math.min(y + 1, 6), 
				lowerBound = Math.max(y - 1, 0);
		
		for(int a = leftBound; a <= rightBound; a++)
			for(int b = lowerBound; b <= upperBound; b++)
				tiles.add(new TileID(a, b));
		
		//System.out.println("Adjacent tiles: " + tiles.size());
		
		return tiles;
	}
	
	public ArrayList<TileID> jumpTiles() {
		// returns tiles empty and adjacent to a given tile. Tiles are considered adjacent if immediately next to or are in a 2 square radius.
		
		ArrayList<TileID> tiles = new ArrayList<TileID>();
		
		int 	leftBound = x - 2, 
				rightBound = x + 2, 
				upperBound = y + 2, 
				lowerBound = y - 2;
		
		if(x < 2)
			leftBound = x;
		else if(x > 4)
			rightBound = x;
		
		if(y < 2)
			lowerBound = y;
		else if(y > 4)
			upperBound = y;
		
		for(int a = leftBound; a <= rightBound; a += 2)
			for(int b = lowerBound; b <= upperBound; b += 2)
				tiles.add(new TileID(a, b));
		
		//System.out.println("Jump tiles: " + tiles.size());
		
		return tiles;
	}
	
	public void draw() {
		// draw the tile (for predicted moves)
		Ivector drawCoordinates = centerCoord();
		
		// Translate drawing matrix
		glPushMatrix();
		glTranslatef(drawCoordinates.x, drawCoordinates.y, 0);
		
		// draw light green fill
		glColor3f(.5f, 1f, .5f);
		glBegin(GL_TRIANGLES);
		
		glVertex2f(-45, -45);
		glVertex2f(-45, 45);
		glVertex2f(45, 45);
		
		glVertex2f(45, 45);
		glVertex2f(45, -45);
		glVertex2f(-45, -45);
		
		glEnd();
		
		// Draw light blue outline
		glColor3f(.2f, .2f, 1f);
		glLineWidth(2f);
		
		glBegin(GL_LINES);
		
		glVertex2f(-45, -45);
		glVertex2f(45, -45);
		
		glVertex2f(45, -45);
		glVertex2f(45, 45);
		
		glVertex2f(45, 45);
		glVertex2f(-45, 45);
		
		glVertex2f(-45, 45);
		glVertex2f(-45, -45);
		
		glEnd();
		glLineWidth(1f);
		
		
		glPopMatrix();
	}
	
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}