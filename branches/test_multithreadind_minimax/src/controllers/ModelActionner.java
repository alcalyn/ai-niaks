package controllers;

import java.awt.event.ActionListener;

import exceptions.NiaksException;

import model.Niaks;

public abstract class ModelActionner implements ActionListener {
	
	
	protected Niaks niaks;
	private ExceptionCatcher view;
	
	
	public ModelActionner(Niaks niaks, ExceptionCatcher view) {
		this.niaks = niaks;
		this.view = view;
	}
	
	public ModelActionner(Niaks niaks) {
		this.niaks = niaks;
		this.view = null;
	}
	
	
	protected void catchException(NiaksException e) {
		if(view != null) {
			view.catchException(e);
		}
	}
	

}
