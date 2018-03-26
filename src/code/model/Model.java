package code.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;

import javax.swing.ImageIcon;

import code.ui.UI;

public class Model {

	private UI _observer; // initialized by setObserver method
	private Integer _z;
	private Integer _b;
	private int _c;
	private HashSet<Integer> in;
	private ArrayList<String> _imageFileNames; // the names of the image files
	private ArrayList<String> _spinnerCurrentValues; // the image files to
	private Random _random; // display in the UI

	public Model() {
		in = new HashSet<Integer>();
		_c = 0;
		_random = new Random();

		_imageFileNames = new ArrayList<String>();
		_imageFileNames.add("Tile-0.png");
		_imageFileNames.add("Tile-1.png");
		_imageFileNames.add("Tile-2.png");
		_imageFileNames.add("Tile-3.png");
		_imageFileNames.add("Tile-4.png");
		_imageFileNames.add("Tile-5.png");
		_spinnerCurrentValues = new ArrayList<String>();
		for (int i = 0; i < 25; i = i + 1) {
			_spinnerCurrentValues.add(i, null);
		}

		spin(); // randomly select which images to display
	}

	public void spin() {
		for (int i = 0; i < 25; i = i + 1) {
			Collections.shuffle(_imageFileNames);
			_spinnerCurrentValues.set(i, _imageFileNames.get(0));
		}
		stateChanged(); // tell the UI that the model's state has changed
	}

	public boolean match() {

		for (int i = 1; i < _spinnerCurrentValues.size() - 1; i = i + 1) {
			if (((i - 1) % 5 != 0 && (i + 1) % 5 != 0 && (i) % 5 != 0)
					|| ((i - 1) % 5 != 4 && (i + 1) % 5 != 4 && (i) % 5 != 4)) {
				if (_spinnerCurrentValues.get(i - 1).equals(_spinnerCurrentValues.get(i))
						&& _spinnerCurrentValues.get(i).equals(_spinnerCurrentValues.get(i + 1))) {
					_observer.disable();
					return true;
				}
			} else
				;
		}
		for (int i = 5; i < _spinnerCurrentValues.size() - 5; i = i + 1) {

			if (_spinnerCurrentValues.get(i).equals(_spinnerCurrentValues.get(i - 5))
					&& _spinnerCurrentValues.get(i).equals(_spinnerCurrentValues.get(i + 5))) {
				_observer.disable();
				return true;
			}
		}
		_observer.disable();
		return false;

	}

	public void swap(int i) {
		boolean match;
		_z = i;
		_c++;
		_observer.redborder(i);
		if (_c % 2 == 0) {
			if (((_z % 5 != 4 || _b % 5 != 0) && (_b % 5 != 4 || _z % 5 != 0))
					&& (_z - _b == -1 || _z - _b == 1 || _z - _b == 5 || _z - _b == -5)) {

				String temp = _spinnerCurrentValues.get(_z);
				_spinnerCurrentValues.set(_z, _spinnerCurrentValues.get(_b));
				_spinnerCurrentValues.set(_b, temp);
				match = match();
				if (match) {
					_observer.swap(_z, _b);

					matchfordrop();

					match = false;
				} else {
					temp = _spinnerCurrentValues.get(_z);
					_spinnerCurrentValues.set(_z, _spinnerCurrentValues.get(_b));
					_spinnerCurrentValues.set(_b, temp);
				}

			} else {
				_observer.disable();
			}

		} else {
			_b = i;
		}
	}

	public void matchfordrop() {
		for (int i = 1; i < _spinnerCurrentValues.size() - 1; i++) {

			if ((i % 5 != 0 && (i + 1) % 5 != 0 && (i - 1) % 5 != 0)
					|| ((i - 1) % 5 != 4 && i % 5 != 4 && (i + 1) % 5 != 4)) {

				if (_spinnerCurrentValues.get(i - 1).equals(_spinnerCurrentValues.get(i))
						&& _spinnerCurrentValues.get(i).equals(_spinnerCurrentValues.get(i + 1))) {
					in.add(i);
					in.add(i - 1);
					in.add(i + 1);
				}
			}
		}
		for (int j = 5; j < _spinnerCurrentValues.size() - 5; j++) {
			if (_spinnerCurrentValues.get(j - 5).equals(_spinnerCurrentValues.get(j))
					&& _spinnerCurrentValues.get(j).equals(_spinnerCurrentValues.get(j + 5))) {
				in.add(j);
				in.add(j - 5);
				in.add(j + 5);
			}
		}

		for (Integer i : in) {
			_spinnerCurrentValues.set(i, "");
		}

		for (int i = 0; i < 25; i++) {
			try {
				in.remove(i);
			} catch (Exception e) {
				break;
			}
		}

		droptiles();
	}

	private void droptiles() {

		for (int j = 0; j < 5; j++) {

			for (int i = 24; i > 4; i--) {

				if (_spinnerCurrentValues.get(i) == "") {
					if (i > 4) {
						String temp = _spinnerCurrentValues.get(i);
						_spinnerCurrentValues.set(i, _spinnerCurrentValues.get(i - 5));
						_spinnerCurrentValues.set(i - 5, temp);
					}
					_observer._labels.get(i).setIcon(new ImageIcon("Images/" + _spinnerCurrentValues.get(i)));

				}
			}

			for (int i = 0; i < 5; i++) {
				if (_spinnerCurrentValues.get(i) == "") {
					String temp = _imageFileNames.get(_random.nextInt(_imageFileNames.size()));
					_spinnerCurrentValues.set(i, temp);
					_observer._labels.get(i).setIcon(new ImageIcon("Images/" + temp));
					if (match()) {
						String temp1 = _imageFileNames.get(_random.nextInt(_imageFileNames.size()));
						_spinnerCurrentValues.set(i, temp1);
						_observer._labels.get(i).setIcon(new ImageIcon("Images/" + temp1));
						matchfordrop();
					}
				}
			}
		}
		_observer.hint();
	}

	public void stateChanged() {
		if (_observer != null) {
			_observer.update(); // tell the UI to update
		}
	}

	public boolean findhint(int i, int j) {
		boolean ss = false;
		_z = i;
		_b = j;

		if (((_z % 5 != 4 || _b % 5 != 0) && (_b % 5 != 4 || _z % 5 != 0))
				&& (_z - _b == -1 || _z - _b == 1 || _z - _b == 5 || _z - _b == -5)) {

			String temp = _spinnerCurrentValues.get(_z);
			_spinnerCurrentValues.set(_z, _spinnerCurrentValues.get(_b));
			_spinnerCurrentValues.set(_b, temp);
			if (match()) {
				ss = true;
			}

			temp = _spinnerCurrentValues.get(_z);
			_spinnerCurrentValues.set(_z, _spinnerCurrentValues.get(_b));
			_spinnerCurrentValues.set(_b, temp);

		}

		return ss;
	}

	public void addObserver(UI ui) {
		_observer = ui;
	}

	public String getImageFileName(int i) {
		while (match())
			spin();
		return _spinnerCurrentValues.get(i);
	}

}
