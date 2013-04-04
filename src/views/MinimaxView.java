package views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import minimax.Minimax;
import minimax.MinimaxNode;
import minimax.MinimaxObserver;
import model.Plateau;

public class MinimaxView extends JFrame implements MinimaxObserver {
	
	private static final long serialVersionUID = -1392931382448321928L;
	
	
	
	private Minimax minimax;
	
	private JPanel main;
	private MinimaxNodeView [] nodes = null;
	
	
	public MinimaxView(Minimax minimax) {
		super("Vue minimax");
		
		this.minimax = minimax;
		listenMinimax();
		
		setSize(600, 600);
		setLayout(new GridLayout(6, 6));
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
		if(nodes != null) {
			for (MinimaxNodeView node : nodes) {
				getContentPane().remove(node);
			}
		}
		
		nodes = new MinimaxNodeView[childs.length];
		int i = 0;
		
		for (MinimaxNode child : childs) {
			Plateau p = (Plateau) child;
			MinimaxNodeView node = new MinimaxNodeView(p);
			nodes[i++] = node;
			add(node);
		}
		
		validate();
	}

}
