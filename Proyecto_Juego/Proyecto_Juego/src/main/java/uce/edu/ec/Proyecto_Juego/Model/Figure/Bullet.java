package uce.edu.ec.Proyecto_Juego.Model.Figure;

import uce.edu.ec.Proyecto_Juego.Model.Interfaces.Drawable;
import uce.edu.ec.Proyecto_Juego.Model.Interfaces.Movable;

import java.awt.Color;
import java.awt.Graphics;



public class Bullet implements Drawable, Movable {

	public int pos_X;
	public int pos_Y;

	public Bullet(Characters character) {
		pos_X = character.getPoints_X()[0];
		pos_Y = character.getPoints_Y()[0];
	}

	@Override
	public void draw(Graphics graphics) {
		graphics.setColor(Color.WHITE);
		graphics.fillOval(pos_X - 10, pos_Y, 10, 15);
	}

	@Override
	public void draw(Graphics graphics, Characters character) {
		// TODO Auto-generated method stub
	}

	@Override
	public void moveUp(int move) {
		pos_Y -= move;
	}

	@Override
	public void moveDown(int move) {
		pos_Y += move + 5;
	}

	@Override
	public void moveLeft(int move) {
		// TODO Auto-generated method stub
	}

	@Override
	public void moveRight(int move) {
		// TODO Auto-generated method stub
	}

	public int getX() {
		return pos_X;
	}

	public int getY() {
		return pos_Y;
	}
}
