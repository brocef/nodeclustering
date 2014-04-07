import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.File;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class NodeFrame extends JFrame {
	private static final long serialVersionUID = -5341951897538939238L;
	private PaintHandle ph;
	private NodeFrameEventHandler handler;
	private File file;
	private final JTextField nameField, randomField;
	private final JButton newBtn;
	
	public NodeFrame(String title, final PaintHandle painter, final NodeFrameEventHandler evt_handler) {
		super(title);
		this.ph = painter;
		this.handler = evt_handler;
		this.file = null;
		this.setResizable(false);
		JPanel panel = new JPanel() {
			private static final long serialVersionUID = -7764637868098614024L;
			
			@Override
			public void paint(Graphics gfx) {
				Rectangle r = gfx.getClipBounds();
				gfx.clearRect(0, 0, r.width, r.height);
				
				gfx.setColor(new Color(200, 200, 200));
				gfx.fillRect(0, 0, r.width, r.height);
				
				gfx.setColor(Color.BLACK);
				paintWrapper((Graphics2D) gfx);
			}
		};
		setLayout(null);
		
		panel.setPreferredSize(new Dimension(520, 520));
		add(panel);
		panel.setBounds(0,0,520,520);
		panel.addMouseListener(handler);
		//panel.addMouseListener(new FieldBuilder());
		
		JPanel sidepanel = new JPanel();
		sidepanel.setPreferredSize(new Dimension(150,500));
		add(sidepanel);
		//sidepanel.setBackground(Color.BLACK);
		sidepanel.setBounds(520,0,150,500);
		//sidepanel.setLayout(new BoxLayout(sidepanel, BoxLayout.PAGE_AXIS));
		
		JButton loadBtn = new JButton("Load Node Field");
		loadBtn.setActionCommand("load");
		
		newBtn = new JButton("Create Node Field");
		newBtn.setActionCommand("new");
		
		JLabel ranCountLabel = new JLabel("# of Random Nodes");
		randomField = new JTextField();
		randomField.setColumns(8);
		final JButton ranBtn = new JButton("Create Random Field");
		ranBtn.setActionCommand("new_rand");
		
		JButton useBtn = new JButton("Use Current Field");
		useBtn.setActionCommand("use");
		
		JLabel nameFieldLabel = new JLabel("File Name");
		nameField = new JTextField();
		JButton saveBtn = new JButton("Save Current Field");
		saveBtn.setActionCommand("save");
		
		JButton clearBtn = new JButton("Clear CWF");
		clearBtn.setActionCommand("clear");
		
		nameField.setPreferredSize(new Dimension(120, 20));
		
		loadBtn.addActionListener(handler);
		newBtn.addActionListener(handler);
		ranBtn.addActionListener(handler);
		useBtn.addActionListener(handler);
		saveBtn.addActionListener(handler);
		clearBtn.addActionListener(handler);
		
		sidepanel.add(new JLabel("Node Grouping Options"));
		sidepanel.add(Box.createRigidArea(new Dimension(0,5)));
		sidepanel.add(loadBtn);
		sidepanel.add(Box.createRigidArea(new Dimension(0,10)));
		sidepanel.add(ranCountLabel);
		sidepanel.add(Box.createRigidArea(new Dimension(0,5)));
		sidepanel.add(randomField);
		sidepanel.add(Box.createRigidArea(new Dimension(0,5)));
		sidepanel.add(ranBtn);
		sidepanel.add(Box.createRigidArea(new Dimension(0,5)));
		sidepanel.add(newBtn);
		sidepanel.add(Box.createRigidArea(new Dimension(0,5)));
		sidepanel.add(useBtn);
		sidepanel.add(Box.createRigidArea(new Dimension(0,10)));
		sidepanel.add(nameFieldLabel);
		sidepanel.add(Box.createRigidArea(new Dimension(0,5)));
		sidepanel.add(nameField);
		sidepanel.add(Box.createRigidArea(new Dimension(0,5)));
		sidepanel.add(saveBtn);
		sidepanel.add(Box.createRigidArea(new Dimension(0,10)));
		sidepanel.add(clearBtn);
		
		setPreferredSize(new Dimension(670, 550));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	public int getRandomFieldCount() {
		try {
			return (randomField.getText()==null?0:Integer.parseInt(randomField.getText()));
		} catch (NumberFormatException e) {
			return 0;
		}
	}
	
	public void setNewFieldButtonColor(Color c) {
		newBtn.setBackground(c);
	}
	
	public String getSaveFieldText() {
		return nameField.getText();
	}
	
	public void paintWrapper(Graphics2D g) {
		ph.paint(g);
	}
	
	public File getNodeFile() {
		return file;
	}
}
