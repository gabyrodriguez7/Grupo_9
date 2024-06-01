package uce.edu.ec.Proyecto_Juego.Window;

import uce.edu.ec.Proyecto_Juego.Controller.Container;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Window extends JFrame implements KeyListener {

	private final int SCREEN_WIDTH = 800;
	private final int SCREEN_HEIGHT = 600;

	private boolean leftPressed = false;
	private boolean rightPressed = false;
	private boolean hPressed = false;
	int i = 0;



	private JPanel lienzo;
	Container container = new Container();

	public Window() {
		super("GALATA");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

		lienzo = new JPanel();
		lienzo.setBackground(new Color(10, 10, 10));
		setContentPane(lienzo);

		addKeyListener(this);
		setVisible(true);

		Timer timer = new Timer(1, new ActionListener() {


			@Override
			public void actionPerformed(ActionEvent e) {
				 // Llamada al método updateGame para verificar colisiones y eliminar aliens
				container.moveUp(4);

				if (i % 4 == 0) {
					container.moveDown(1);
				}

				if (i % 100 == 0) {
					container.createShoot_Alien();
				}
				i++;


				repaint();



			}
		});
		timer.start();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		container.Draw(g);
		container.updateGame();

		if (leftPressed) {
			container.moveLeft(10);
		}
		if (rightPressed) {
			container.moveRight(10);
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				leftPressed = true;
				break;
			case KeyEvent.VK_RIGHT:
				rightPressed = true;
				break;
			case KeyEvent.VK_H:
				hPressed = true;
				container.createShoot_Ship();
				break;
			default:
				break;
		}

		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {

		switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				leftPressed = false;
				break;
			case KeyEvent.VK_RIGHT:
				rightPressed = false;
				break;
			case KeyEvent.VK_H:
				hPressed = true;
				break;
			default:

		}

	}


}


