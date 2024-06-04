package uce.edu.ec.Proyecto_Juego.Model.Figure;

import uce.edu.ec.Proyecto_Juego.Model.Interfaces.Collisionable;
import uce.edu.ec.Proyecto_Juego.Model.Interfaces.Drawable;
import uce.edu.ec.Proyecto_Juego.Model.Interfaces.LifeReducible;
import uce.edu.ec.Proyecto_Juego.Model.Interfaces.Movable;

import java.awt.*;

public class Ship extends Characters implements Drawable, Movable, Collisionable, LifeReducible {

	int [] life_X = {25, 175, 175, 25};
	int [] life_Y = {50, 50, 65, 65};

	private boolean alive;
	private int life;
	private int score;
	private static boolean showLevel;
	private static String level;

	public Ship() {
		super.setPoints_X(new int[]{400, 425, 375});
		super.setPoints_Y(new int[]{476, 526, 526});

		this.alive = true;
		this.life = 100;
		this.score = 0;

	}

	@Override
	public void draw(Graphics graphics) {
		if (alive && life >= 0) {
			//dibuja la Nave
			graphics.setColor(Color.white);
			graphics.fillPolygon(getPoints_X(), getPoints_Y(), 3);

			// Dibuja la Vida
			graphics.setColor(Color.WHITE);
			graphics.fillPolygon(life_X, life_Y, 4);

		}

		// Dibuja el porcentaje de vida
		graphics.setColor(Color.white);
		Font fontlife = new Font("SansSerif", Font.PLAIN, 14); // Cambia la fuente y el tamaño según tus preferencias
		graphics.setFont(fontlife);
		graphics.drawString(String.valueOf(life)+"%", 180, 63);

		// Dibuja el Score
		graphics.setColor(Color.WHITE);
		Font fontSCore = new Font("SansSerif", Font.PLAIN, 20); // Cambia la fuente y el tamaño según tus preferencias
		graphics.setFont(fontSCore);
		graphics.drawString("Score: ", 670, 63);
		graphics.setColor(Color.GREEN);
		graphics.drawString(String.valueOf(score), 730, 64);

		// Dibuja el nivel en el que se encuentra
		if(showLevel){
			graphics.setColor(Color.WHITE);
			Font fontlevel = new Font("Courier New", Font.PLAIN, 150); // Cambia la fuente y el tamaño según tus preferencias
			graphics.setFont(fontlevel);
			graphics.drawString(level, 100, 300);
		}


	}

	@Override
	public void draw(Graphics graphics, Characters character) {
	}

	@Override
	public void moveUp(int move) {
	}

	@Override
	public void moveDown(int move) {
	}

	@Override
	public void moveLeft(int move) {

		if(getPoints_X()[1]>=65){
			for (int i = 0; i < getPoints_X().length; i++) {
				getPoints_X()[i] -= move;
			}
		}

	}

	@Override
	public void moveRight(int move) {

		if (getPoints_X()[2] <= 735) {
			for (int i = 0; i < getPoints_X().length; i++) {
				getPoints_X()[i] += move;
			}
		}

	}

	@Override
	public boolean Collision(Bullet bullet) {
		// Obtiene el X y Y de la bala
		int bulletX = bullet.getX();
		int bulletY = bullet.getY();

		// Delimita las cordenadas de la nave para verificar colisiones
		int minX = getMinX();
		int maxX = getMaxX();
		int minY = getMinY();
		int maxY = getMaxY();

		// Verifica si la bala colisiona con la nave
		if (bulletX >= minX && bulletX <= maxX && bulletY >= minY && bulletY <= maxY) {
			//alive = false; implementar la dimincudisminuciond e vida
			return true;
		}

		return false;

	}

	@Override
	public void reduceLife(int reduceLife){

		if (life > 0) {
			life -= reduceLife;
			if (life < 0) {
				life = 0;
			}
			for (int i = 1; i <= 2; i++) {
				life_X[i] -= (int) (reduceLife * 1.5);
				if (life_X[i] < 0) {
					life_X[i] = 0;
				}
			}
		}

	}

	public boolean isAlive() {
		return alive;
	}

	private int getMinX() {

		int minX = getPoints_X()[0];
		for (int x : getPoints_X()) {
			if (x < minX) {
				minX = x;
			}
		}

		return minX;

	}

	private int getMaxX() {

		int maxX = getPoints_X()[0];
		for (int x : getPoints_X()) {
			if (x > maxX) {
				maxX = x;
			}
		}

		return maxX;

	}

	private int getMinY() {

		int minY = getPoints_Y()[0];
		for (int y : getPoints_Y()) {
			if (y < minY) {
				minY = y;
			}
		}

		return minY;
	}

	private int getMaxY() {

		int maxY = getPoints_Y()[0];
		for (int y : getPoints_Y()) {
			if (y > maxY) {
				maxY = y;
			}
		}

		return maxY;

	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void moreScore(int moreScore){
		score += moreScore;
	}

	public int getLife() {
		return life;
	}

	public static void setShowLevel(boolean showLevel) {
		Ship.showLevel = showLevel;
	}

	public static void setLevel(String level) {
		Ship.level = level;
	}
}