import java.util.ArrayList;
import java.util.Collection;


public class NodeBucket {
	public static final int WIDTH = 10;
	private int x, y, width;
	private int realx, realy;
	private ArrayList<Node> nodes;
	
	public NodeBucket(int x, int y, int width) {
		this.x = x;
		this.y = y;
		this.width = width;
		realx = x*width;
		realy = y*width;
		nodes = new ArrayList<Node>();
	}
	
	public Node[] getNodes() {
		return nodes.toArray(new Node[nodes.size()]);
	}
	
	public Collection<Node> getNodeCollection() {
		return nodes;
	}
	
	public void addNode(Node n) {
		if (isInBucket(n)) {
			nodes.add(n);
		} else  {
			System.err.println("Node "+n+" could not be added to bucket "+this);
		}
	}
	
	private boolean isInBucket(Node n) {
		return (n.getX() >= realx && n.getX() < (realx+width) && n.getY() >= realy && n.getY() < (realy+width));
	}
	
	public String toString() {
		return String.format("[(%d,%d) wid:%d, n:%d]", x, y, width, nodes.size());
	}
}
