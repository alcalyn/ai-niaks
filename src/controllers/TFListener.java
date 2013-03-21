package controllers;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public abstract class TFListener implements DocumentListener {
	
	
	private JTextField tf;
	
	public TFListener(JTextField tf) {
		this.tf = tf;
	}

	@Override
	public final void changedUpdate(DocumentEvent e) {
		allUpdate(e);
	}

	@Override
	public final void insertUpdate(DocumentEvent e) {
		allUpdate(e);
	}

	@Override
	public final void removeUpdate(DocumentEvent e) {
		allUpdate(e);
	}
	
	private void allUpdate(DocumentEvent e) {
		changeEvent(tf.getText());
	}
	
	public abstract void changeEvent(String s);

}
