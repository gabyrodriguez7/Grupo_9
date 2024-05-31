package uce.edu.ec.Proyecto_Juego.Model.Figure;

import uce.edu.ec.Proyecto_Juego.Model.Interfaces.Collisionable;
import uce.edu.ec.Proyecto_Juego.Model.Interfaces.Drawable;
import uce.edu.ec.Proyecto_Juego.Model.Interfaces.Movable;

import java.awt.Color;
import java.awt.Graphics;

public class Ship extends Characters implements Drawable, Movable, Collisionable {

	private boolean alive = true;

	public Ship() {
		super.setPoints_X(new int[]{400, 425, 375});
		super.setPoints_Y(new int[]{476, 526, 526});
	}

	@Override
	public void draw(Graphics graphics) {
		if (alive) {
			graphics.setColor(Color.white);
			graphics.fillPolygon(getPoints_X(), getPoints_Y(), 3);
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
		for (int i = 0; i < getPoints_X().length; i++) {
			getPoints_X()[i] -= move;
		}
	}

	@Override
	public void moveRight(int move) {
		for (int i = 0; i < getPoints_X().length; i++) {
			getPoints_X()[i] += move;
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