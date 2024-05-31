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

		Timer timer = new Timer(20, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				container.moveDown(1);
				container.moveUp(5);
				container.updateGame(); // Llamada al método updateGame para verificar colisiones y eliminar aliens

				if (i % 15 == 0) {
					System.out.println("hola");
					container.createShoot_Alien();
				}
				repaint();
				i++;
			}
		});
		timer.start();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		container.Draw(g);

	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				container.moveLeft(10);
				break;
			case KeyEvent.VK_RIGHT:
				container.moveRight(10);
				break;
			case KeyEvent.VK_H:
				container.createShoot_Ship();
				break;
			default:
				break;
		}

		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}


