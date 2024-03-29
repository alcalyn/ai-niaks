package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Coup;
import model.Humain;
import model.Joueur;
import model.Observer;
import model.Ordinateur;
import model.PartiePreparator;
import model.Pion;
import model.Strategies;
import model.Strategy;
import niakwork.NiakworkHostSocket;
import niakwork.NiakworkPlayer;
import niakwork.NiakworkPlayerSocket;
import controllers.DifficultListener;
import controllers.TFPseudoListener;
import controllers.TypeJoueurActionListener;

public class CardPrepare extends JPanel implements Observer {
	
	private static final long serialVersionUID = -536690658232712587L;
	
	
	
	private PartiePreparator partie_preparator;
	
	private JPanel grille_joueurs = null;
	

	public CardPrepare(PartiePreparator partie_preparator) {
		super();
		this.partie_preparator = partie_preparator;
		
		setLayout(new BorderLayout());
		
		initJoueurs();
	}
	
	
	private void initJoueurs() {
		if(grille_joueurs != null) {
			remove(grille_joueurs);
			grille_joueurs = null;
		}
		
		grille_joueurs = new JPanel(new GridBagLayout());
		grille_joueurs.setBackground(Color.WHITE);
		
		for(int i=0;i<6;i++) {
			if(i < partie_preparator.getJoueurs().size()) {
				addJoueurLine(partie_preparator.getJoueurs().get(i), grille_joueurs, i);
			} else if(i < partie_preparator.getJoueurs().size()+1) {
				addEmptyLine(grille_joueurs, i);
			} else {
				addDisabledEmptyLine(grille_joueurs, i);
			}
		}
		
		add(grille_joueurs, BorderLayout.CENTER);
	}

	
	private static GridBagConstraints lc(int line, int col) {
		GridBagConstraints c = new GridBagConstraints();
		
		c.insets = new Insets(4, 6, 4, 6);
		c.gridheight = 1;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = col;
		c.gridy = line;
		
		return c;
	}
	
	// Grille :
	// host | color | typePlayer | pseudo | CPU level
	
	private void addEmptyLine(JPanel p, int line) {
		int c = 0;
		p.add(new JLabel(), lc(line, c++));
		p.add(createEmpty(), lc(line, c++));
		p.add(createComboBox(true, line), lc(line, c++));
		p.add(new JLabel(), lc(line, c++));
		p.add(new JLabel(), lc(line, c++));
	}
	
	private void addDisabledEmptyLine(JPanel p, int line) {
		int c = 0;
		p.add(new JLabel(), lc(line, c++));
		p.add(new JLabel(), lc(line, c++));
		p.add(new JLabel(), lc(line, c++));
		p.add(new JLabel(), lc(line, c++));
		p.add(new JLabel(), lc(line, c++));
	}


	private void addJoueurLine(Joueur j, JPanel p, int line) {
		int c = 0;
		p.add(j == partie_preparator.getHost() ? createHostComponent() : new JLabel(), lc(line, c++));
		p.add(createColorComponent(j.getCouleur()), lc(line, c++));
		p.add(createComboBox(j, line), lc(line, c++));
		p.add(createTF(j), lc(line, c++));
		p.add((j instanceof Ordinateur) ? createCPULevel(partie_preparator, (Ordinateur) j) : new JLabel(), lc(line, c++));
	}

	
	private JComboBox createComboBox(boolean listening, int index) {
		JComboBox combo = new JComboBox();
		
		combo.addItem("Vide");
		combo.addItem("Joueur local");
		combo.addItem("Ordinateur");
		
		if(listening) combo.addActionListener(new TypeJoueurActionListener(partie_preparator, index));
		
		return combo;
	}
	
	private JComboBox createComboBox(Joueur c, int index) {
		JComboBox combo = createComboBox(false, index);
		
		if(c instanceof Humain)			combo.setSelectedIndex(1);
		if(c instanceof Ordinateur) 	combo.setSelectedIndex(2);
		if(c instanceof NiakworkPlayer) {
			combo.addItem("Joueur distant");
			combo.setSelectedIndex(3);
		}
		
		combo.addActionListener(new TypeJoueurActionListener(partie_preparator, index));
		
		return combo;
	}
	
	private JComboBox createCPULevel(PartiePreparator partie_preparator, Ordinateur ordinateur) {
		JComboBox combo = new JComboBox();
		
		Set<Entry<String, Strategy>> a = Strategies.getStrategiesAndDifficult().entrySet();
		for (Entry<String, Strategy> entry : a) {
			combo.addItem(entry.getKey());
		}
		
		combo.setSelectedItem(Strategies.getDifficultByStrategy(ordinateur.getStrategy()));
		combo.addActionListener(new DifficultListener(partie_preparator, ordinateur));
		
		return combo;
	}
	
	private JTextField createTF(Joueur j) {
		JTextField tf = new JTextField(16);
		
		tf.setText(j.getPseudo());
		tf.getDocument().addDocumentListener(new TFPseudoListener(tf, j));
		
		
		if(j instanceof Humain)	{
			if(j == partie_preparator.getHost()) {
				tf.setEditable(false);
				tf.setEnabled(false);
			}
		}
		if(j instanceof NiakworkPlayer) {
			tf.setEditable(false);
			tf.setEnabled(false);
		}
		if(j instanceof Ordinateur) {
			tf.setEditable(false);
			tf.setEnabled(false);
		}
		
		return tf;
	}
	
	private static JComponent createEmpty() {
		JPanel p = new JPanel();
		
		p.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		
		return p;
	}
	
	private static JTextField createEmptyJTF(int cols) {
		JTextField tf = new JTextField(cols);
		
		tf.setEditable(false);
		tf.setEnabled(false);
		
		return tf;
	}
	
	private static JComponent createHostComponent() {
		JTextField h = createEmptyJTF(3);
		h.setText("Hote");
		return h;
	}
	
	private static JComponent createColorComponent(Color c) {
		JPanel l = (JPanel) createEmpty();
		l.setBackground(c);
		return l;
	}
	

	@Override
	public void updateEtat(int etat_partie) {
	}


	@Override
	public void updateTaillePlateau(int taille) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void updateJoueurs(Joueur[] joueurs) {
		initJoueurs();
		validate();
	}


	@Override
	public void updatePions(Pion[][] pions, Coup coup) {
	}


	@Override
	public void updateCurrentPlayer(Joueur joueur) {
	}


	@Override
	public void updateProfil(String pseudo) {
	}


	@Override
	public void updateNiakwork(boolean isEnabled) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void updateNiakworkClientWantJoin(NiakworkPlayerSocket npsocket, String pseudo) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void updateNiakworkServerFound(NiakworkHostSocket nssocket,
			String pseudo) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void updateNiakworkHostDenied(NiakworkHostSocket nssocket,
			String reason) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void updateNiakworkHostAccept(NiakworkHostSocket nssocket) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void updateJoueurWon(Joueur joueur) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void updateGameFinished() {
		// TODO Auto-generated method stub
		
	}

}
