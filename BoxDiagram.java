package week5;

import acm.program.GraphicsProgram;
import acm.graphics.GCompound;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GPoint;
import acm.graphics.GRect;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;

public class BoxDiagram extends GraphicsProgram {

	public void init() {
		contents = new HashMap<String, GObject>();
		createController();
		addMouseListeners();
	}

	private void createController() {
		nameField = new JTextField(MAX_NAME);
		nameField.addActionListener(this);
		addButton = new JButton("Add");
		removeButton = new JButton("Remove");
		clearButton = new JButton("Clear");
		add(new JLabel("Name"), SOUTH);
		add(nameField, SOUTH);
		add(addButton, SOUTH);
		add(removeButton, SOUTH);
		add(clearButton, SOUTH);
	}

	private void addBox(String name) {
		GCompound box = new GCompound();
		GRect outline = new GRect(BOX_WIDTH, BOX_HEIGHT);
		GLabel label = new GLabel(name);
		box.add(outline, -BOX_WIDTH / 2, -BOX_HEIGHT / 2);
		box.add(label, -label.getWidth() / 2, label.getAscent() / 2);
		add(box, getWidth() / 2, getHeight() / 2);
		contents.put(name, box);
	}

	private void removeBox(String name) {
		GObject obj = contents.get(name);
		if (obj != null) {
			remove(obj);
		}
	}

	private void removeContents() {
		Iterator<String> iterator = contents.keySet().iterator();
		while (iterator.hasNext()) {
			removeBox(iterator.next());
		}

		contents.clear();
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == addButton || source == nameField) {
			addBox(nameField.getText());
		} else if (source == removeButton) {
			removeBox(nameField.getText());
		} else if (source == clearButton) {
			removeContents();
		}
	}

	public void mousePressed(MouseEvent e) {
		last = new GPoint(e.getPoint());
		currentObject = getElementAt(last);
	}

	public void mouseDragged(MouseEvent e) {
		if (currentObject != null) {
			currentObject.move(e.getX() - last.getX(), e.getY() - last.getY());
			last = new GPoint(e.getPoint());
		}
	}

	public void mouseClicked(MouseEvent e) {
		if (currentObject != null)
			currentObject.sendToFront();
	}

	private static final int MAX_NAME = 25;
	private static final double BOX_WIDTH = 120;
	private static final double BOX_HEIGHT = 50;

	public HashMap<String, GObject> contents;
	public JTextField nameField;
	public JButton addButton;
	public JButton removeButton;
	public JButton clearButton;
	public GObject currentObject;
	public GPoint last;
}
