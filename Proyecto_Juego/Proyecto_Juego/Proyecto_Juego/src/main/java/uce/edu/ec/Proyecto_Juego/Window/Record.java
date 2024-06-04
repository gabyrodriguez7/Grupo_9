package uce.edu.ec.Proyecto_Juego.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Record extends JFrame {

	private final int SCREEN_WIDTH = 800;
	private final int SCREEN_HEIGHT = 600;

	private boolean leftPressed = false;
	private boolean rightPressed = false;
	private boolean hPressed = false;
	int i = 0;

	private JPanel lienzo;

	public Record() {
		super("Record");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

		lienzo = new JPanel();
		lienzo.setBackground(new Color(10, 10, 10));
		setContentPane(lienzo);

		//addKeyListener(this);
		setVisible(true);

	}

	@Override
	public void paint(Graphics g) {


	}



}


