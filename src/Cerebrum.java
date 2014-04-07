import java.awt.Color;


public class Cerebrum {
	
	public void useNodeField(Node[] nodes) {
		//Do not change node data other than color. Enjoy!
		if (nodes == null) return;
		for (Node n:nodes)
			n.setColor(new Color((int)(Math.random()*Integer.MAX_VALUE)));
	}
	
}
