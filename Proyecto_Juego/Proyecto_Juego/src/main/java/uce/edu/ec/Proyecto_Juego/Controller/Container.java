package uce.edu.ec.Proyecto_Juego.Controller;

import uce.edu.ec.Proyecto_Juego.Model.Figure.Alien;
import uce.edu.ec.Proyecto_Juego.Model.Figure.Bullet;
import uce.edu.ec.Proyecto_Juego.Model.Figure.Ship;
import uce.edu.ec.Proyecto_Juego.Model.Stage.Line;

import java.awt.Graphics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Container {

	final int RANDOM_X_ALIEN = 750;
	final int RANDOM_Y_ALIEN = 200;

	List<Alien> aliens = new ArrayList<>();
	List<Bullet> bullets_Ship = new ArrayList<>();
	List<Bullet> bullets_Alien = new ArrayList<>();

	Ship nave = new Ship();
	Random random = new Random();
	Line line = new Line();

	public Container() {
		for (int i = 0; i < 5; i++) {
			aliens.add(new Alien(random.nextInt(RANDOM_X_ALIEN), random.nextInt(RANDOM_Y_ALIEN)));
		}
	}

	public void Draw(Graphics graphics) {
		line.draw(graphics);
		nave.draw(graphics);

		for (Alien alien : aliens) {
			alien.draw(graphics);
		}

		for (Bullet bullet : bullets_Ship) {
			bullet.draw(graphics);
		}

		for (Bullet bullet : bullets_Alien) {
			bullet.draw(graphics);
		}
	}

	public void moveLeft(int move) {
		nave.moveLeft(move);
	}

	public void moveRight(int move) {
		nave.moveRight(move);
	}

	public void moveDown(int move) {
		for (Alien alien : aliens) {
			alien.moveDown(move);
		}

		for (Bullet bullet : bullets_Alien) {
			bullet.moveDown(move);
		}
	}

	public void moveUp(int move) {
		for (Bullet bullet : bullets_Ship) {
			bullet.moveUp(move);
		}
	}

	public void createShoot_Ship() {
		bullets_Ship.add(new Bullet(nave));
	}

	public void createShoot_Alien() {
		bullets_Alien.add(new Bullet(aliens.get(random.nextInt(aliens.size()))));
	}

	public void updateGame() {
		checkCollisions();
		removeDeadAliens();
		removeOffScreenBullets();
	}

	private void checkCollisions() {

		Iterator<Bullet> bulletIterator = bullets_Ship.iterator();
		while (bulletIterator.hasNext()) {
			Bullet bullet = bulletIterator.next();
			boolean bulletRemoved = false;
			for (Alien alien : aliens) {
				if (alien.isAlive() && alien.Collision(bullet)) {
					bulletIterator.remove();
					bulletRemoved = true;
					break;
				}
			}
			if (bulletRemoved) break;
		}


		bulletIterator = bullets_Alien.iterator();
		while (bulletIterator.hasNext()) {
			Bullet bullet = bulletIterator.next();
			if (nave.isAlive() && nave.Collision(bullet)) {
				bulletIterator.remove();
				break;
			}
		}
	}

	private void removeDeadAliens() {
		aliens.removeIf(alien -> !alien.isAlive());
	}

	private void removeOffScreenBullets() {

		bullets_Ship.removeIf(bullet -> bullet.pos_Y < 0);


		bullets_Alien.removeIf(bullet -> bullet.pos_Y > 600);
	}
}