package uce.edu.ec.Proyecto_Juego.Model.Figure;

import uce.edu.ec.Proyecto_Juego.Model.Interfaces.Collisionable;
import uce.edu.ec.Proyecto_Juego.Model.Interfaces.Drawable;
import uce.edu.ec.Proyecto_Juego.Model.Interfaces.Movable;

import java.awt.*;

public class Ship extends Characters implements Drawable, Movable, Collisionable {

	private boolean alive = true;
	private static int life = 100;
	private int score = 0;
	private static boolean showLevel;
	private static String level;

	int [] life_X = {25, 175, 175, 25};
	int [] life_Y = {50, 50, 65, 65};



	public Ship() {
		super.setPoints_X(new int[]{400, 425, 375});
		super.setPoints_Y(new int[]{476, 526, 526});
	}

	@Override
	public void draw(Graphics graphics) {
		if (alive && life > 0) {
			graphics.setColor(Color.white);
			graphics.fillPolygon(getPoints_X(), getPoints_Y(), 3);
		}

		// Dibuja la Vida
		graphics.setColor(new Color(200,200,200));
		graphics.fillPolygon(life_X, life_Y, 4);

		graphics.setColor(Color.white);
		Font fontlife = new Font("SansSerif", Font.PLAIN, 14); // Cambia la fuente y el tamaño según tus preferencias
		graphics.setFont(fontlife);
		graphics.drawString(String.valueOf(life)+"%", 180, 63);

		// dibuja el Score
		graphics.setColor(Color.WHITE);
		Font fontSCore = new Font("SansSerif", Font.PLAIN, 20); // Cambia la fuente y el tamaño según tus preferencias
		graphics.setFont(fontSCore);
		graphics.drawString("Score: ", 670, 63);
		graphics.setColor(Color.GREEN);
		graphics.drawString(String.valueOf(score), 730, 64);

		// Dibuja el nivel en el que se ecunetra

		if(showLevel){
			float[] size ={};
			graphics.setColor(Color.WHITE);
			Font fontlevel = new Font("Courier New", Font.PLAIN, 150); // Cambia la fuente y el tamaño según tus preferencias
			graphics.setFont(fontlevel);
			graphics.drawString(level, 100, 300);
		}


	}

	@Override
	public void draw(Graphics graphics, Characters character) {
		// TODO Auto-generated method stub
	}

	@Override
	public void moveUp(int move) {
		// TODO Auto-generated method stub
	}

	@Override
	public void moveDown(int move) {
		// TODO Auto-generated method stub
	}

	@Override
	public void moveLeft(int move) {
		if(getPoints_X()[1]>=60){
			for (int i = 0; i < getPoints_X().length; i++) {
				getPoints_X()[i] -= move;
			}
		}
	}

	@Override
	public void moveRight(int move) {
		if (getPoints_X()[2] <= 740) {
			for (int i = 0; i < getPoints_X().length; i++) {
				getPoints_X()[i] += move;
			}
		}
	}

	@Override
	public boolean Collision(Bullet bullet) {
		int bulletX = bullet.getX();
		int bulletY = bullet.getY();


		int minX = getMinX();
		int maxX = getMaxX();
		int minY = getMinY();
		int maxY = getMaxY();


		if (bulletX >= minX && bulletX <= maxX && bulletY >= minY && bulletY <= maxY) {
			//alive = false; implementar la dimincudisminuciond e vida
			return true;
		}
		return false;
	}

	public void reduceLife(int reduce){

		if(life>0){
			life -= reduce;
			for (int i=1; i<=2; i++){
				life_X[i]-= (int) (reduce*1.5);
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

	public static int getLife() {
		return life;
	}


	public static void setShowLevel(boolean showLevel) {
		Ship.showLevel = showLevel;
	}

	public static void setLevel(String level) {
		Ship.level = level;
	}
}