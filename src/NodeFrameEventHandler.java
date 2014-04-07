import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;


public class NodeFrameEventHandler implements ActionListener, MouseListener {
	private boolean makingField;
	public ArrayList<Node> field;
	//private NodeBucket[] buckets;
	private Core core;
	
	public NodeFrameEventHandler(Core core) {
		makingField = false;
		field = new ArrayList<Node>();
		this.core = core;
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		//System.err.println(evt.getActionCommand());
		switch(evt.getActionCommand()) {
		case "clear":
			disableFieldMaking();
			field.clear();
			core.updateNodeField(getNodeField());
			break;
		case "load":
			disableFieldMaking();
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new File("."));
			int returnval = chooser.showOpenDialog(null);
			if (returnval == JFileChooser.APPROVE_OPTION) {
				updateNodeField(chooser.getSelectedFile());
				core.updateNodeField(getNodeField());
				field.clear();
			}
			break;
		case "new":
			makingField = !makingField;
			JButton newBtn = (JButton) evt.getSource();
			newBtn.setBackground((makingField?Color.GREEN:Color.RED));
			if (makingField) core.updateNodeField(null);
			else core.updateNodeField(getNodeField());
			break;
		case "new_rand":
			disableFieldMaking();
			core.createRandomNodeField();
			core.updateNodeField(getNodeField());
			break;
		case "use":
			disableFieldMaking();
			core.useNodeField();
			break;
		case "save":
			disableFieldMaking();
			//System.out.println("Saving");
			core.saveNodeField();
			break;
		}
		core.repaint();
	}
	
	private void disableFieldMaking() {
		if (makingField) {
			makingField = false;
			core.setNewFieldButtonColor(Color.RED);
			core.updateNodeField(getNodeField());
		}
	}
	
	public Node[] getNodeField() {
		return field.toArray(new Node[field.size()]);
	}
	
	public void updateNodeField(File f) {
		int nodecount;
		try {
			BufferedReader in = new BufferedReader(new FileReader(f));
			String line = in.readLine();
			nodecount = Integer.parseInt(line);
			field = new ArrayList<Node>(nodecount);
			for (int i=0; i<nodecount; i++) {
				line = in.readLine();
				if (line == null || line.length()==0) System.exit(1);
				
				String x, y;
				int comma = line.indexOf(',');
				x = line.substring(0, comma);
				y = line.substring(comma+1);
				field.add(new Node(Integer.parseInt(x), Integer.parseInt(y)));
			}
			in.close();
		} catch(NumberFormatException e) {
			System.err.println(f.getName() + " was an invalid node list file.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (!makingField) return;
		Node n;
		Point p = e.getPoint();
		if (p.x <=0 || p.y <= 0) return;
		int nx = (p.x-Node.X_OFFSET);
		int ny = (p.y-Node.Y_OFFSET);
		if (nx < 0 || ny < 0 || nx >= Core.MAX_X || ny >= Core.MAX_Y) return;
		n = new Node(nx, ny);
		field.add(n);
		core.repaint();
		//System.out.printf("Adding node from p(%d,%d) at [%d,%d]\n",p.x,p.y,n.getX(),n.getY());
	}
	
	public void generateNewNodeField(int nodecount) {
		/*buckets = new NodeBucket[100];
		for (int i=0; i<buckets.length; i++) {
			buckets[i] = new NodeBucket(i%NodeBucket.WIDTH, i/NodeBucket.WIDTH, NodeBucket.WIDTH);
		}
		
		field.clear();
		for (int i=0; i<nodecount; i++) {
			int x = (int)(Math.random()*100);
			int y = (int)(Math.random()*100);
			Node n = new Node(x, y);
			field.add(n);
			buckets[x/NodeBucket.WIDTH+(y/NodeBucket.WIDTH)*NodeBucket.WIDTH].addNode(n);
		}*/
		//System.err.println("Generating "+nodecount+" nodes");
		field.clear();
		for (int i=0; i<nodecount; i++) {
			int x = (int)(Math.random()*Core.MAX_X);
			int y = (int)(Math.random()*Core.MAX_Y);
			Node n = new Node(x, y);
			field.add(n);
		}
	}

}
