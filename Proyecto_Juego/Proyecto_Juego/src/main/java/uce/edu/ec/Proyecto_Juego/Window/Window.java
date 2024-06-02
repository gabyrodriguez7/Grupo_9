package uce.edu.ec.Proyecto_Juego.Window;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

import uce.edu.ec.Proyecto_Juego.Controller.Container;

public class Window extends JFrame implements KeyListener {

	private final int SCREEN_WIDTH = 800;
	private final int SCREEN_HEIGHT = 600;

	private boolean leftPressed = false;
	private boolean rightPressed = false;
	private boolean hPressed = false;
	int i = 0;
	static String username ;
	static String password ;

	private JPanel registrationPanel;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JButton startButton;

	private JPanel lienzo;
	Container container;

	public Window() {
		super("GALAGA");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

		createRegistrationPanel();
		setContentPane(registrationPanel); // Establecer el panel de registro como el contenido inicial
		setBackground(new Color(10, 10, 10));


		setVisible(true);

	}

	private void createRegistrationPanel() {
		registrationPanel = new JPanel(new GridBagLayout());
		registrationPanel.setBackground(new Color(20, 20, 20));
		registrationPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

// Crear GridBagConstraints para configurar el posicionamiento y el tamaño de los componentes
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5); // Espaciado entre componentes

// Label y campo de usuario
		JLabel usernameLabel = new JLabel("Usuario:");
		usernameLabel.setForeground(Color.WHITE);
		usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
		registrationPanel.add(usernameLabel, gbc);

		gbc.gridx = 1;
		JTextField usernameField = new JTextField(20); // Ancho predeterminado
		registrationPanel.add(usernameField, gbc);

// Label y campo de contraseña
		gbc.gridx = 0;
		gbc.gridy = 1;
		JLabel passwordLabel = new JLabel("Contraseña:");
		passwordLabel.setForeground(Color.WHITE);
		passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
		registrationPanel.add(passwordLabel, gbc);

		gbc.gridx = 1;
		JPasswordField passwordField = new JPasswordField(20);
		registrationPanel.add(passwordField, gbc);

// Botón de inicio de sesión
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2; // El botón ocupa dos columnas
		gbc.anchor = GridBagConstraints.CENTER; // Centrar el botón
		gbc.fill = GridBagConstraints.HORIZONTAL; // El botón se extiende horizontalmente
		gbc.insets = new Insets(20, 0, 0, 0); // Espaciado adicional en la parte superior del botón

		JButton startButton = new JButton("Siguiente");
		startButton.setBackground(new Color(30, 144, 255));
		startButton.setForeground(Color.WHITE);
		startButton.setFocusPainted(false);
		startButton.setFont(new Font("Arial", Font.BOLD, 14));
		registrationPanel.add(startButton, gbc);

		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				 username = usernameField.getText();
				 password = new String(passwordField.getPassword());

				container = new Container(Window.this);
				switchToGamePanel();
			}
		});
	}

	private void switchToGamePanel() {
		lienzo = new JPanel();
		lienzo.setBackground(new Color(10, 10, 10));
		setContentPane(lienzo); // Cambiar al panel del juego

		container = new Container(this); // Inicializar container

		Timer timer = new Timer(1, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
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

		// Agregar el listener de teclado al nuevo contenido (panel de juego)
		lienzo.addKeyListener(this);
		lienzo.requestFocusInWindow(); // Asegurar que el panel de juego tenga el foco del teclado
		lienzo.setFocusable(true); // Asegurar que el panel de juego sea focuseable
	}


	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (container != null) {
			container.Draw(g);
			container.updateGame();
		}

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
			case KeyEvent.VK_SPACE:
				hPressed = true;
				if (container != null) {
					container.createShoot_Ship();
				}
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
			case KeyEvent.VK_SPACE:
				hPressed = true;
				break;
			default:

		}

	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
}