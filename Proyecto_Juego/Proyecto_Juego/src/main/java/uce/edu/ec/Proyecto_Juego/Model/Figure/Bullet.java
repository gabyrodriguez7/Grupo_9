package uce.edu.ec.Proyecto_Juego.Model.Figure;

import uce.edu.ec.Proyecto_Juego.Model.Interfaces.Drawable;
import uce.edu.ec.Proyecto_Juego.Model.Interfaces.Movable;

import java.awt.Color;
import java.awt.Graphics;



public class Bullet implements Drawable, Movable {

	public int pos_X;
	public int pos_Y;
	private int level;

	public Bullet(Characters character, int level) {
		this.level = level;
		pos_X = character.getPoints_X()[0];
		pos_Y = character.getPoints_Y()[0];
	}

	@Override
	public void draw(Graphics graphics) {
	}

	@Override
	public void draw(Graphics graphics, Characters character) {

		if (character instanceof Alien){
			if(level ==1){
				graphics.setColor(Color.WHITE);
				graphics.fillOval(pos_X-5, pos_Y+20, 10, 15);

			}else if (level == 2) {
				graphics.setColor(Color.WHITE);
				graphics.fillOval(pos_X - 5 - (character.getPoints_X()[0]-character.getPoints_X()[2]), pos_Y+15, 10, 15);
				graphics.fillOval(pos_X - 5 + (character.getPoints_X()[0]-character.getPoints_X()[2]), pos_Y+15, 10, 15);


			}else if (level == 3) {
				graphics.setColor(Color.WHITE);
				graphics.fillOval(pos_X - 5, pos_Y, 10, 15);
				graphics.fillOval(pos_X - 5 - (character.getPoints_X()[0]-character.getPoints_X()[2]), pos_Y+15, 10, 15);
				graphics.fillOval(pos_X - 5 + (character.getPoints_X()[0]-character.getPoints_X()[2]), pos_Y+15, 10, 15);

			}

		} else if (character instanceof Ship) {
			graphics.setColor(Color.WHITE);
			graphics.fillOval(pos_X -5, pos_Y, 10, 15);
		}


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
