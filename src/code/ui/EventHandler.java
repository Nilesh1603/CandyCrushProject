package code.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import code.model.Model;

public class EventHandler implements ActionListener {
 
	private Model _model;
	private int _x;
	

	public EventHandler(int i,Model m) {
	     _x=i;
		_model = m;
				
	}
	
	@Override public void actionPerformed(ActionEvent e) {
		
		
	
		_model.match();
		
		
		_model.swap(_x);
	}
	
	

}
