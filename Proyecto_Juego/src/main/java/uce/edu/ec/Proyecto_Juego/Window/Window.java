package uce.edu.ec.Proyecto_Juego.Window;

import java.awt.*;
import java.awt.event.*;
import java.util.Locale;

import javax.swing.*;

import uce.edu.ec.Proyecto_Juego.Controller.Container;

public class Window extends JFrame implements KeyListener {

	// variables para controlar el tamaño de la ventana
	private final int SCREEN_WIDTH = 800;
	private final int SCREEN_HEIGHT = 600;

	// controla la logica de movimiento
	private boolean leftPressed = false;
	private boolean rightPressed = false;
	private boolean hPressed = false;

	// controla la logica de disparo y la velocidad de que bajen los marcianos
	int i = 0;

	// variables de clase donde se va a guardar el nombre y contraseña ingresados por el usuario
	static String username ;
	static String password ;

	// varaibles apra controlar la logica del panel para registrarse o continuar
	private JPanel registrationPanel;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JButton startButton;
	private JButton registerButton;
	private boolean registerButtonPressed = false;
	private boolean nextButtonPressed = false;

	// Variables para controlar el panel del juego y el contenido de la ventana
	private JPanel lienzo;
	Container container;

	public Window() {
		super("GALAGA");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

		createRegistrationPanel();
		setContentPane(registrationPanel); // Establecer el panel de registro como el contenido inicial
		setBackground(new Color(10, 10, 10));

		setLocationRelativeTo(null);

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
		usernameField = new JTextField(20); // Ancho predeterminado
		registrationPanel.add(usernameField, gbc);

		// Label y campo de contraseña
		gbc.gridx = 0;
		gbc.gridy = 1;
		JLabel passwordLabel = new JLabel("Contraseña:");
		passwordLabel.setForeground(Color.WHITE);
		passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
		registrationPanel.add(passwordLabel, gbc);

		gbc.gridx = 1;
		passwordField = new JPasswordField(20); // Ancho predeterminado
		registrationPanel.add(passwordField, gbc);

		// Botón de registro
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1; // El botón ocupa una columna
		gbc.anchor = GridBagConstraints.CENTER; // Centrar el botón
		gbc.fill = GridBagConstraints.HORIZONTAL; // El botón se extiende horizontalmente
		gbc.insets = new Insets(20, 0, 0, 5); // Espaciado adicional en la parte superior y derecha del botón

		registerButton = new JButton("Registrarse");
		registerButton.setBackground(new Color(30, 144, 255));
		registerButton.setForeground(Color.WHITE);
		registerButton.setFocusPainted(false);
		registerButton.setFont(new Font("Arial", Font.BOLD, 14));
		registrationPanel.add(registerButton, gbc);

		// Listener para el botón de registro y que cree una nueva partida
		registerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				registerButtonPressed = true;
				// Obtener el nombre de usuario y la contraseña del campo de texto y el campo de contraseña
				username = usernameField.getText();
				password = new String(passwordField.getPassword());

				container = new Container(Window.this);

				if(username.equals("") && password.equals("")){
					JOptionPane.showMessageDialog(Window.this, "Porfavor llene los campos Usuario y Contraseña.", "Error", JOptionPane.ERROR_MESSAGE);
				}else if(password.equals("")) {
					JOptionPane.showMessageDialog(Window.this, "Por favor llene el campo Contraseña", "Error", JOptionPane.ERROR_MESSAGE);
				}else if (username.equals("")) {
					JOptionPane.showMessageDialog(Window.this, "Porfavor llene el campo Usuario.", "Error", JOptionPane.ERROR_MESSAGE);
				}

				if (!username.equals("") && !password.equals("")) {
					switchToGamePanel();
				}

			}
		});

		// Botón de inicio de sesión
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.CENTER; // Centrar el botón
		gbc.fill = GridBagConstraints.HORIZONTAL; // El botón se extiende horizontalmente
		gbc.insets = new Insets(20, 5, 0, 0); // Espaciado adicional en la parte superior y izquierda del botón

		startButton = new JButton("Siguiente");
		startButton.setBackground(new Color(30, 144, 255));
		startButton.setForeground(Color.WHITE);
		startButton.setFocusPainted(false);
		startButton.setFont(new Font("Arial", Font.BOLD, 14));
		registrationPanel.add(startButton, gbc);

		// Listener para el botón de inicio de sesión y continue una partida
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				nextButtonPressed = true;
				// Obtener el nombre de usuario y la contraseña del campo de texto y el campo de contraseña
				username = usernameField.getText();
				password = new String(passwordField.getPassword());

				container = new Container(Window.this);
				boolean userFound = container.getUser(username, password);

				if (userFound) {
					switchToGamePanel();
				}else if(username.equals("") || password.equals("")){
					JOptionPane.showMessageDialog(Window.this, "Por favor llene todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(Window.this, "Usuario o contraseña incorrectos. Inténtelo de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}

		});
	}

	private void switchToGamePanel() {
		lienzo = new JPanel();
		lienzo.setBackground(new Color(10, 10, 10));
		setContentPane(lienzo);

		// timer para ejecutar las acciones de los aliens y las balas de los mismos
		Timer timer = new Timer(1, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				container.moveUp(4);

				if (i % 3 == 0) {
					container.moveDown(1);
				}

				if (i % 75 == 0) {
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

		// Agregar un WindowListener para manejar el cierre de la ventana, que gestiona que se guarde el juego
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				container.closeWindowAndShowGameOver(true);
				System.exit(0);
			}
		});
	}


	@Override
	public void paint(Graphics g) {
		super.paint(g);

		if (container != null) {
			container.Draw(g);
			container.updateGame(false);
		}

		if (leftPressed) {
			container.moveLeft(7);
		}
		if (rightPressed) {
			container.moveRight(7);
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

	public static String getUsername() {
		return username;
	}

	public static String getPassword() {
		return password;
	}

	public boolean isRegisterButtonPressed() {
		return registerButtonPressed;
	}

}