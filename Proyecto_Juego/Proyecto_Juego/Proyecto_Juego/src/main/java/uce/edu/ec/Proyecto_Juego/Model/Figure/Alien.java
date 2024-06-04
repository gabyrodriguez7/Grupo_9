package uce.edu.ec.Proyecto_Juego.Model.Figure;

import uce.edu.ec.Proyecto_Juego.Model.Interfaces.Collisionable;
import uce.edu.ec.Proyecto_Juego.Model.Interfaces.Drawable;
import uce.edu.ec.Proyecto_Juego.Model.Interfaces.LifeReducible;
import uce.edu.ec.Proyecto_Juego.Model.Interfaces.Movable;

import java.awt.*;


public class Alien extends Characters implements Drawable, Movable, Collisionable, LifeReducible {
	int[] alien_X = {25, 0, 0, 50, 50};
	int[] alien_Y = {25, 50, 0, 0, 50};

	int[] life_X = new int[4];
	int[] life_Y = new int[4];

	private int life;
	private boolean alive;
	private float sizeAlien;
	private float wideAlien;

	public Alien(int ran_X, int ran_Y, float sizeAlien, int level) {

		super.setPoints_X(alien_X);
		super.setPoints_Y(alien_Y);
		this.sizeAlien = sizeAlien;
		this.alive = true;
		this.life = 100;

		if (level == 3){

			this.wideAlien = ((int) ((getPoints_X()[3] - getPoints_X()[2])*sizeAlien) /100);

			for (int i = 0; i < getPoints_X().length; i++) {
				// logica para incrementar el tamaño del alien y moverlo por la pantalla
				getPoints_X()[i] *= (int) sizeAlien;
				getPoints_Y()[i] *= (int) sizeAlien;
				getPoints_X()[i] += ran_X - wideAlien * 100/sizeAlien;
				getPoints_Y()[i] += ran_Y ;
			}

		}else {

			this.wideAlien = ((float) ((getPoints_X()[3] - getPoints_X()[2])*sizeAlien) /100);

			for (int i = 0; i < getPoints_X().length; i++) {
				// logica para incrementar el tamaño del alien y moverlo por la pantalla
				getPoints_X()[i] *= (int) sizeAlien;
				getPoints_Y()[i] *= (int) sizeAlien;
				getPoints_X()[i] += ran_X ;
				getPoints_Y()[i] += ran_Y;
			}
		}



		// se asinga despues apra que coja el ancho del alien y nos arroamos un calculo



		// como se asigna la vida del alien con las cordenas del alien y poniendo un poco arriba del mismo
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

		if (life > 0) {
			//dibuja al Alien
			graphics.setColor(Color.GREEN);
			graphics.fillPolygon(getPoints_X(), getPoints_Y(), 5);

			// Dibuja la vida del Alien
			graphics.setColor(Color.WHITE);
			graphics.fillPolygon(life_X, life_Y, 4);
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

		for (int i = 0; i < getPoints_Y().length; i++) {
			getPoints_Y()[i] += move;
		}

		for (int i = 0; i < life_Y.length; i++) {
			life_Y[i] += move;
		}

	}

	@Override
	public void moveLeft(int move) {
	}

	@Override
	public void moveRight(int move) {
	}

	@Override
	public boolean Collision(Bullet bullet) {
		// obtiene el X y Y de la bala
		int bulletX = bullet.getX();
		int bulletY = bullet.getY();

		// delimita las cordenadas del alien para verificar colisiones
		int min_X = getMinX();
		int max_X = getMaxX();
		int min_Y = getMinY();
		int max_Y = getMaxY();

		// si los maximos y minismo se ecunetra con las cordenadas del la bala verifica colision
		if (bulletX >= min_X && bulletX <= max_X && bulletY >= min_Y && bulletY <= max_Y) {
			return true;
		}

		return false;

	}

	@Override
	public void reduceLife(int reduceLife){

		if(life>0){
			life -= reduceLife;
			for (int i=1; i<=2; i++){
				life_X[i]-= (int) (reduceLife*wideAlien);
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

	public int getLife() {
		return life;
	}
}