package views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import minimax.Minimax;
import minimax.MinimaxNode;
import minimax.MinimaxObserver;
import model.Plateau;

public class MinimaxView extends JFrame implements MinimaxObserver {
	
	private static final long serialVersionUID = -1392931382448321928L;
	
	
	
	private Minimax minimax;
	
	private BreadcrumbNodes breadcrumb;
	private JPanel center = null;
	private MinimaxNodeView [] nodes = null;
	
	
	public MinimaxView(Minimax minimax) {
		super("Vue minimax");
		setLayout(new BorderLayout());
		
		this.minimax = minimax;
		listenMinimax();
		
		breadcrumb = new BreadcrumbNodes();
		breadcrumb.setPreferredSize(new Dimension(600, 100));
		add(breadcrumb, BorderLayout.NORTH);
		
		setSize(600, 600);
		setVisible(true);
	}
	
	
	
	private void listenMinimax() {
		final MinimaxObserver instance = this;
		
		minimax.addObserver(this);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				minimax.removeObserver(instance);
			}
		});
	}



	@Override
	public void updateProcessed(MinimaxNode current, MinimaxNode [] childs, MinimaxNode best_selected) {
		breadcrumb.updateBread(new MinimaxNodeView[] {new MinimaxNodeView((Plateau) current)});
		
		
		if(center != null) remove(center);
		center = new JPanel();
		center.setLayout(new GridLayout(6, 6));
		add(center, BorderLayout.CENTER);
		
		nodes = new MinimaxNodeView[childs.length];
		int i = 0;
		
		for (MinimaxNode child : childs) {
			Plateau p = (Plateau) child;
			MinimaxNodeView node = new MinimaxNodeView(p);
			nodes[i++] = node;
			center.add(node);
		}
		
		validate();
	}

}
