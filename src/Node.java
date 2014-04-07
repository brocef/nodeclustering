import java.awt.Color;
import java.awt.Graphics2D;


public class Node {
	public static final int X_OFFSET = 10;
	public static final int Y_OFFSET = 10;
	private int x, y;
	private Color color;
	
	public Node(int x, int y) {
		this.x = x;
		this.y = y;
		color = Color.BLACK;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public String toString() {
		return String.format("%d,%d", x, y);
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Node) {
			Node n = (Node) o;
			return (n.getX() == x && n.getY() == y);
		}
		return false;
	}
	
	public void drawNode(Graphics2D g) {
		g.setColor(color);
		g.fillRect((x+X_OFFSET)-2, (y+Y_OFFSET)-2, 5, 5);
	}
}
