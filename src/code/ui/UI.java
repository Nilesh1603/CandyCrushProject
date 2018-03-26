package code.ui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import code.model.Model;

public class UI implements Runnable {

	private JFrame _frame;
	private Model _model;

	public ArrayList<JButton> _labels;

	@Override
	public void run() {
		
		_frame = new JFrame("Nilesh's Lab 11");
		_frame.setLayout(new GridLayout(5, 5));

		_labels = new ArrayList<JButton>();
		for (int i = 0; i < 25; i++) {

			JButton label = new JButton();
			_labels.add(label);

		}
		_model = new Model(); // create the model for this UI
		_model.addObserver(this);

		for (int i = 0; i < 25; i++) {
			_frame.add(_labels.get(i), i);
			ActionListener x;
			x = new EventHandler(i, _model);
			_labels.get(i).addActionListener(x);

		}

		update();

		// standard JFrame method calls
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_frame.pack();
		_frame.setVisible(true);

	}

	public void redborder(int i) {

		_labels.get(i).setBorder(BorderFactory.createLineBorder(Color.red, 3));
	}

	public void update() {
		// update the icon on each label

		for (int i = 0; i < 25; i = i + 1) {

			_labels.get(i).setIcon(new ImageIcon("Images/" + _model.getImageFileName(i)));
			_labels.get(i).setBorder(BorderFactory.createLineBorder(Color.white, 2));
		}

		hint();

		// make sure JFrame is appropriately sized (needed when _spin text
		// changes)
		_frame.pack();
	}

	public void disable() {
		for (int i = 0; i < 25; i = i + 1) {
			_labels.get(i).setBorder(BorderFactory.createLineBorder(Color.white));
		}

	}

	public void swap(int a, int b) {
		JButton temp = new JButton();
		temp.setIcon(_labels.get(a).getIcon());
		_labels.get(a).setIcon(_labels.get(b).getIcon());
		_labels.get(b).setIcon(temp.getIcon());

	}

	public void hint() {
		int x = 0;
		int z = 0;

		for (int i = 0; i < 25; i = i + 1) {
			for (int j = 1; j < 25; j++) {

				if (_model.findhint(i, j)) {
					x = i;
					z = j;
					break;
				}
			}
		}

		if (x != 0 || z != 0) {
			_labels.get(x).setBorder(BorderFactory.createLineBorder(Color.green, 3));
			_labels.get(z).setBorder(BorderFactory.createLineBorder(Color.green, 3));
		} else {

			System.out.println("GameOver");
			System.exit(0);
		}
	}
}
