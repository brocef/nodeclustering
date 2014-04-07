import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


public class Core implements PaintHandle {
	//CONSTANTS
	public static final int MAX_X = 500;
	public static final int MAX_Y = 500;
	//END CONSTANTS
	
	private NodeFrame frame;
	private NodeFrameEventHandler handler;
	private Node[] nodes;
	private NodeBucket[] buckets;
	private Cerebrum brain;

	public static void main(String[] args) {
		Core c = new Core();
		c.run(args);
	}
	
	public void run(String[] args) {
		nodes = null;

		buckets = new NodeBucket[100];
		for (int i=0; i<buckets.length; i++) {
			buckets[i] = new NodeBucket(i%NodeBucket.WIDTH, i/NodeBucket.WIDTH, NodeBucket.WIDTH);
		}
		
		brain = new Cerebrum();
		handler = new NodeFrameEventHandler(this);
		frame = new NodeFrame("Node Grouping", this, handler);
		frame.repaint();
	}
	
	public void useNodeField() {
		brain.useNodeField(nodes);
	}

	@SuppressWarnings("unused")
	private Node[] getNeighbors(Node n) {
		ArrayList<Node> neighbors = new ArrayList<Node>();
		int x = n.getX()/NodeBucket.WIDTH;
		int y = n.getY()/NodeBucket.WIDTH;

		neighbors.addAll(buckets[(x-1)+(y+1)*NodeBucket.WIDTH].getNodeCollection());
		neighbors.addAll(buckets[(x-1)+(y)*NodeBucket.WIDTH].getNodeCollection());
		neighbors.addAll(buckets[(x-1)+(y-1)*NodeBucket.WIDTH].getNodeCollection());

		neighbors.addAll(buckets[(x+1)+(y+1)*NodeBucket.WIDTH].getNodeCollection());
		neighbors.addAll(buckets[(x+1)+(y)*NodeBucket.WIDTH].getNodeCollection());
		neighbors.addAll(buckets[(x+1)+(y-1)*NodeBucket.WIDTH].getNodeCollection());

		neighbors.addAll(buckets[(x)+(y+1)*NodeBucket.WIDTH].getNodeCollection());
		neighbors.addAll(buckets[(x)+(y-1)*NodeBucket.WIDTH].getNodeCollection());
		
		return neighbors.toArray(new Node[neighbors.size()]);
	}
	
	public void saveNodeField() {
		if (nodes == null || nodes.length == 0) return;
		PrintWriter out;
		try {
			String name = frame.getSaveFieldText();
			File f = new File(name);
			if (!f.exists()) f.createNewFile();
			System.out.println(f.getAbsolutePath());
			out = new PrintWriter(f);
		
			out.println(nodes.length);
			for (Node n:nodes)
				out.println(n.toString());
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void createRandomNodeField() {
		if (frame.getRandomFieldCount() > 0)
			handler.generateNewNodeField(frame.getRandomFieldCount());
	}
	
	public void setNewFieldButtonColor(Color c) {
		frame.setNewFieldButtonColor(c);
	}
	
	public void updateNodeField(Node[] field) {
		nodes = field;
		frame.repaint();
	}
	
	public void repaint() {
		frame.repaint();
	}
	
	public void paint(Graphics2D g) {
		paintGrid(g);
		paintNodes(g);
	}

	public void paintGrid(Graphics2D g) {
		for (int i=0; i<11; i++) {
			g.drawLine(i*50+Node.X_OFFSET, 0, i*50+Node.X_OFFSET, 550);
			g.drawLine(0, i*50+Node.Y_OFFSET, 550, i*50+Node.Y_OFFSET);
		}
	}

	public void paintNodes(Graphics2D g) {
		if (nodes != null && nodes.length > 0) {
			g.setColor(Color.BLUE);
			for (Node n:nodes) {
				if (n == null) {
					System.err.println("Node was null????");
					continue;	
				}
				n.drawNode(g);
			}
		} else {
			//Perhaps our temp node field has some stuff for us
			g.setColor(Color.BLUE);
			ArrayList<Node> field = handler.field;
			for (Node n:field) {
				if (n == null) {
					System.err.println("Node was null????");
					continue;	
				}
				n.drawNode(g);
			}
		}
	}
}
