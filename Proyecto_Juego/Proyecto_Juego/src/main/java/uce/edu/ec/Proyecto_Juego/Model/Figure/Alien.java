package uce.edu.ec.Proyecto_Juego.Model.Figure;

import uce.edu.ec.Proyecto_Juego.Model.Interfaces.Collisionable;
import uce.edu.ec.Proyecto_Juego.Model.Interfaces.Drawable;
import uce.edu.ec.Proyecto_Juego.Model.Interfaces.Movable;

import java.awt.Color;
import java.awt.Graphics;



public class Alien extends Characters implements Drawable, Movable, Collisionable {
	int[] alien_X = {25, 0, 0, 50, 50};
	int[] alien_Y = {25, 50, 0, 0, 50};
	private boolean alive = true;

	public Alien(int ran_X, int ran_Y) {
		super.setPoints_X(alien_X);
		super.setPoints_Y(alien_Y);

		for (int i = 0; i < getPoints_X().length; i++) {
			getPoints_X()[i] += ran_X;
			getPoints_Y()[i] += ran_Y;
		}
	}

	@Override
	public void draw(Graphics graphics) {
		if (alive) {
			graphics.setColor(Color.GREEN);
			graphics.fillPolygon(getPoints_X(), getPoints_Y(), 5);
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
		for (int i = 0; i < getPoints_Y().length; i++) {
			getPoints_Y()[i] += move;
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
			alive = false;
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
}