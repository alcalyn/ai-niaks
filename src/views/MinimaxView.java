package views;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.JFrame;

import minimax.Minimax;
import minimax.MinimaxNode;
import minimax.MinimaxObserver;

public class MinimaxView extends JFrame implements MinimaxObserver {
	
	private static final long serialVersionUID = -1392931382448321928L;
	
	
	
	private Minimax minimax;
	
	
	
	public MinimaxView(Minimax minimax) {
		super("Vue minimax");
		
		this.minimax = minimax;
		minimax.addObserver(this);
		
		initFrame();
	}
	
	private void initFrame() {
		final MinimaxObserver instance = this;
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				minimax.addObserver(instance);
			}
		});
		
		setSize(200, 600);
		setLayout(new BorderLayout());
		setVisible(true);
	}



	@Override
	public void updateProcessed(ArrayList<MinimaxNode> childs, MinimaxNode best_selected) {
		// TODO updater la frame, afficher les childs sous forme de miniature de plateau, et leur minimax/eval
	}

}
