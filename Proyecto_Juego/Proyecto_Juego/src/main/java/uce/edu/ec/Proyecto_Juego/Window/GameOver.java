package uce.edu.ec.Proyecto_Juego.Window;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

public class GameOver extends JFrame {

	private static final long serialVersionUID = 1L;
	private boolean gameOverDisplayed = false;
	private JPanel contentPane;
	private boolean blinkState = true;

	int score;
	int life;
	Image background;

	public GameOver(int score, int life) {
		super("---Game Over---");
		this.life = life;
		this.score = score;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600); // Ajustar el tamaño de la ventana
		setLocationRelativeTo(null);

		setFocusable(true);

		contentPane = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (background != null) {
					g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
					drawScore(g);
				}
			}
		};
		contentPane.setBackground(Color.black);
		setContentPane(contentPane);

		try {
			URL resource = getClass().getResource("/space.jpg");
			if (resource != null) {
				background = ImageIO.read(resource);
			} else {
				System.err.println("No se pudo encontrar la imagen en la ruta especificada.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		Timer timer = new Timer(500, e -> {
			blinkState = !blinkState;
			repaint();
		});
		timer.start();
	}

	private void drawScore(Graphics g) {
		int x = getWidth() / 2;
		int y = 150;

		// Configurar la fuente y el color del texto "Game Over"
		g.setFont(new Font("Arial", Font.BOLD, 80));
		g.setColor(Color.red);
		String gameOverText = "GAME OVER";
		int gameOverWidth = g.getFontMetrics().stringWidth(gameOverText);
		if (blinkState) {
			g.drawString(gameOverText, x - gameOverWidth / 2, y);
		}

		// Configurar la fuente y el color del puntaje
		g.setFont(new Font("Arial", Font.BOLD, 40));
		g.setColor(Color.green);
		String scoreText = "Score: " + score;
		int scoreWidth = g.getFontMetrics().stringWidth(scoreText);
		g.drawString(scoreText, x - scoreWidth / 2, y + 100);

		// Configurar la fuente y el color de la vida
		g.setFont(new Font("Arial", Font.BOLD, 40));
		g.setColor(Color.white);
		String lifeText = "Su vida es: " + life;
		int lifeWidth = g.getFontMetrics().stringWidth(lifeText);
		g.drawString(lifeText, x - lifeWidth / 2, y + 200);
	}
}
