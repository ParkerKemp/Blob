import static org.lwjgl.opengl.GL11.*;

class Tile{
	// This class converts an x,y board coordinate into real drawing space coordinates. (Correct me if I'm wrong, parker)
	public int x, y;
	
	public Tile(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Tile () {}
	
	public Ivector centerCoord(){
		Ivector i = new Ivector();
		i.x = x * 90 + 130;
		i.y = y * 90 + 130;
		return i;
	}
	
	public static Tile fromCoord(Ivector l){
		Tile tile = new Tile();
		tile.x = ((int)l.x - 85) / 90;
		tile.y = ((int)l.y - 85) / 90;
		return tile;
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