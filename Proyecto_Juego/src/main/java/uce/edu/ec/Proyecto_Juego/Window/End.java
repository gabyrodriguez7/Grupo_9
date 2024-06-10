package uce.edu.ec.Proyecto_Juego.Window;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class End extends JFrame {

	private JPanel contentPane;
	private boolean blinkState = true;

	int score;
	int life;
	Image background;
	private boolean passLineRed;

	public End(int score, int life) {
		super("---End---");
		this.life = life;
		this.score = score;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600); // Ajustar el tamaÃ±o de la ventana
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
			// Carga la imagen como un recurso
			background = ImageIO.read(getClass().getResource("/space.jpg"));
		} catch (IOException e) {
			System.err.println("No se pudo encontrar la imagen en la ruta especificada.");
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

		// Configurar la fuente y el color del texto "Game Over" o "You Win!"
		g.setFont(new Font("Arial", Font.BOLD, 80));

		if (life < 1 || passLineRed) {

			g.setColor(Color.red);
			String gameOverText = "GAME OVER";
			int gameOverWidth = g.getFontMetrics().stringWidth(gameOverText);
			if (blinkState) {
				g.drawString(gameOverText, x - gameOverWidth / 2, y);
			}

		}else if (life > 0) {

			g.setColor(Color.green);
			String winText = "YOU WIN!";
			int winWidth = g.getFontMetrics().stringWidth(winText);
			if (blinkState) {
				g.drawString(winText, x - winWidth / 2, y);
			}

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

	public boolean isPassLineRed() {
		return passLineRed;
	}

	public void setPassLineRed(boolean passLineRed) {
		this.passLineRed = passLineRed;
	}

}
