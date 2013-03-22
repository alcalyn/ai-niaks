package controllers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public abstract class EnterListener implements KeyListener {

	@Override
	public final void keyTyped(KeyEvent e) {
	}

	@Override
	public final void keyPressed(KeyEvent e) {
	}

	@Override
	public final void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			enterReleased(e);
		}
	}
	
	public abstract void enterReleased(KeyEvent e);
	
}