package uce.edu.ec.Proyecto_Juego.Model.Figure;

import uce.edu.ec.Proyecto_Juego.Model.Interfaces.Collisionable;
import uce.edu.ec.Proyecto_Juego.Model.Interfaces.Drawable;
import uce.edu.ec.Proyecto_Juego.Model.Interfaces.Movable;

import java.awt.*;


public class Alien extends Characters implements Drawable, Movable, Collisionable {
	int[] alien_X = {25, 0, 0, 50, 50};
	int[] alien_Y = {25, 50, 0, 0, 50};

	int[] life_X = new int[4];
	int[] life_Y = new int[4];

	private int life=100;
	private boolean alive = true;
	private float size;


	public Alien(int ran_X, int ran_Y, float size) {
		super.setPoints_X(alien_X);
		super.setPoints_Y(alien_Y);
		this.size = size;



		for (int i = 0; i < getPoints_X().length; i++) {
			getPoints_X()[i]*= (int) size;
			getPoints_Y()[i]*= (int) size;
			getPoints_X()[i] += ran_X;
			getPoints_Y()[i] += ran_Y;
		}

		life_X[0] = getPoints_X()[2];
		life_X[1] = getPoints_X()[3];
		life_X[2] = getPoints_X()[3];
		life_X[3] = getPoints_X()[2];

		life_Y[0] = getPoints_Y()[2]-12;
		life_Y[1] = getPoints_Y()[2]-12;
		life_Y[2] = getPoints_Y()[2]-2;
		life_Y[3] = getPoints_Y()[2]-2;
	}

	@Override
	public void draw(Graphics graphics) {
		if (alive && life > 0) {
			graphics.setColor(Color.GREEN);
			graphics.fillPolygon(getPoints_X(), getPoints_Y(), 5);
		}

		graphics.setColor(new Color(200,200,200));
		graphics.fillPolygon(life_X, life_Y, 4);

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
		for (int i = 0; i < getPoints_Y().length; i++) {
			getPoints_Y()[i] += move;
		}

		for (int i = 0; i < life_Y.length; i++) {
			life_Y[i] += move;
		}


	}

	@Override
	public void moveLeft(int move) {
		// TODO Auto-generated method stub
	}

	@Override
	public void moveRight(int move) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean Collision(Bullet bullet) {
		int bulletX = bullet.getX();
		int bulletY = bullet.getY();

		// Bounding box for the alien
		int minX = getMinX();
		int maxX = getMaxX();
		int minY = getMinY();
		int maxY = getMaxY();

		// Check if bullet is within the bounding box of the alien
		if (bulletX >= minX && bulletX <= maxX && bulletY >= minY && bulletY <= maxY) {
			return true;
		}
		return false;
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

	public int[] getLife_X() {
		return life_X;
	}

	public void setLife_X(int[] life_X) {
		this.life_X = life_X;
	}

	public int[] getLife_Y() {
		return life_Y;
	}

	public void setLife_Y(int[] life_Y) {
		this.life_Y = life_Y;
	}

	public void reduceLifeAlien(int restLifeAlien){
		if(life>0){
			life -= restLifeAlien;
			for (int i=1; i<=2; i++){
				life_X[i]-= (int) ((int) (restLifeAlien*0.5)*size);
			}
		}
	}

	public int getLife() {
		return life;
	}
}