package uce.edu.ec.Proyecto_Juego.Controller;

import uce.edu.ec.Proyecto_Juego.Model.Figure.Alien;
import uce.edu.ec.Proyecto_Juego.Model.Figure.Bullet;
import uce.edu.ec.Proyecto_Juego.Model.Figure.Ship;
import uce.edu.ec.Proyecto_Juego.Model.Stage.Levels;
import uce.edu.ec.Proyecto_Juego.Model.Stage.Line;

import java.awt.Graphics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Container {

	final int RANDOM_X_ALIEN = 750;
	final int RANDOM_Y_ALIEN = 100;

	List<Levels> levels = new ArrayList<>();
	List<Alien> aliens = new ArrayList<>();
	List<Bullet> bullets_Ship = new ArrayList<>();
	List<Bullet> bullets_Alien = new ArrayList<>();

	Ship nave = new Ship();
	Random random = new Random();
	Line line = new Line();

	private static int level = 1;
	private int showLevel=0;


	public Container() {

		levels.add(new Levels("Nivel 1", 5, 1, 5, 100, 5,1));
		levels.add(new Levels( "Nivel 2", 10, 1, 10, 40, 10,1));
		levels.add(new Levels( "Nivel 3", 1, 1, 15, 1, 10,2.5f));

	}


	public void Draw(Graphics graphics) {
		line.draw(graphics);
		nave.draw(graphics);

		if(level == 1 && aliens.size() == 0) {

			Ship.setLevel(levels.get(level-1).getLevel());
			Ship.setShowLevel(true);
			nave.draw(graphics);
			while (showLevel != 1000000){
				System.out.println(showLevel);
				showLevel += 1;

				if(showLevel == 1000000){
					Ship.setShowLevel(false);
				}
			}

			for (int i = 0; i < levels.get(level-1).getNumAlien(); i++) {
				aliens.add(new Alien(random.nextInt(RANDOM_X_ALIEN), random.nextInt(RANDOM_Y_ALIEN), levels.get(level-1).getSizeAlien()));
			}
			level +=1;
			showLevel = 0;

		} else if (level == 2 && aliens.size() == 0) {

			Ship.setLevel(levels.get(level-1).getLevel());
			Ship.setShowLevel(true);
			nave.draw(graphics);
			while (showLevel != 1000000){
				System.out.println(showLevel);
				showLevel += 1;

				if(showLevel == 1000000){
					Ship.setShowLevel(false);
				}
			}

			for (int i = 0; i < levels.get(level-1).getNumAlien(); i++) {
				aliens.add(new Alien(random.nextInt(RANDOM_X_ALIEN), random.nextInt(RANDOM_Y_ALIEN), levels.get(level-1).getSizeAlien()));
			}
			level +=1;
			showLevel = 0;

		} else if (level == 3 && aliens.size() == 0) {

			Ship.setLevel(levels.get(level-1).getLevel());
			Ship.setShowLevel(true);
			nave.draw(graphics);
			while (showLevel != 1000000){
				System.out.println(showLevel);
				showLevel += 1;

				if(showLevel == 1000000){
					Ship.setShowLevel(false);
				}
			}

			for (int i = 0; i < levels.get(level-1).getNumAlien(); i++) {
				aliens.add(new Alien(random.nextInt(RANDOM_X_ALIEN), random.nextInt(RANDOM_Y_ALIEN), levels.get(level-1).getSizeAlien()));
			}
			level +=1;
			showLevel = 0;

		}


		for (Alien alien : aliens) {
			alien.draw(graphics);

			for (Bullet bullet : bullets_Alien) {
				bullet.draw(graphics, alien);
			}

		}

		for (Bullet bullet : bullets_Ship) {
			bullet.draw(graphics, nave);
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
		if(level-1 != 3){


		}

		for (Bullet bullet : bullets_Alien) {
			bullet.moveDown(move*3);
		}
	}

	public void moveUp(int move) {
		for (Bullet bullet : bullets_Ship) {
			bullet.moveUp(move*5);
		}
	}

	public void createShoot_Ship() {
		if(Ship.getLife() > 0) {
			bullets_Ship.add(new Bullet(nave, level-1));
		}
	}

	public void createShoot_Alien() {
		if (aliens.size()>0) {
			bullets_Alien.add(new Bullet(aliens.get(random.nextInt(aliens.size())), level-1));
		}
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

					System.out.println(level-2);

					if(level-1 ==3){
						if (Ship.getLife() <= 50){
							System.out.println("entro por 5 pts");
							alien.reduceLifeAlien(5);
							nave.moreScore(levels.get(level-2).getMoreScore());
						} else if (Ship.getLife() > 50  && Ship.getLife() <= 75) {
							System.out.println("entro por 10 pts");
							alien.reduceLifeAlien(10);
							nave.moreScore(levels.get(level-2).getMoreScore());
						} else if (Ship.getLife() > 75) {
							System.out.println("entro por 15 pts");
							alien.reduceLifeAlien(15);
							nave.moreScore(levels.get(level-2).getMoreScore());
						}

					}else{
						System.out.println("no entro");
						alien.reduceLifeAlien(levels.get(level-2).getLifeAlien());
						nave.moreScore(levels.get(level-2).getMoreScore());
					}

					break;

                }
			}
			if (bulletRemoved) break;
		}


		bulletIterator = bullets_Alien.iterator();
		while (bulletIterator.hasNext()) {
			Bullet bullet = bulletIterator.next();
			if (nave.isAlive() && nave.Collision(bullet)) {
				nave.reduceLife(levels.get(level-2).getLessLife());
				bulletIterator.remove();
				break;
			}
		}

	}

	private void removeDeadAliens() {
		aliens.removeIf(alien -> alien.getLife()<=0);
	}

	private void removeOffScreenBullets() {

		bullets_Ship.removeIf(bullet -> bullet.pos_Y < 0);
		aliens.removeIf(alien -> alien.getPoints_Y()[1]>(int)(600*0.66) );
		bullets_Alien.removeIf(bullet -> bullet.pos_Y > 600);
	}


}