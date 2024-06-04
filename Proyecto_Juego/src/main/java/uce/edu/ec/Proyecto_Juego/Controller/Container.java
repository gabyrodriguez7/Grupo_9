package uce.edu.ec.Proyecto_Juego.Controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import uce.edu.ec.Proyecto_Juego.Model.Figure.Alien;
import uce.edu.ec.Proyecto_Juego.Model.Figure.Bullet;
import uce.edu.ec.Proyecto_Juego.Model.Figure.Ship;
import uce.edu.ec.Proyecto_Juego.Model.Figure.User;
import uce.edu.ec.Proyecto_Juego.Model.Stage.Levels;
import uce.edu.ec.Proyecto_Juego.Model.Stage.Line;
import uce.edu.ec.Proyecto_Juego.Window.Window;
import uce.edu.ec.Proyecto_Juego.Window.End;

import java.awt.Graphics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
public class Container {

	// genera los aliens entre 750 en x y de 50 en y
	final int RANDOM_X_ALIEN = 750;
	final int RANDOM_Y_ALIEN = 50;

	List<Levels> levels = new ArrayList<>();
	List<Alien> aliens = new ArrayList<>();
	List<Bullet> bullets_Ship = new ArrayList<>();
	List<Bullet> bullets_Alien = new ArrayList<>();

	Ship nave = new Ship();
	Random random = new Random();
	Line line = new Line();

	// para usar los niveles fuera del draw se le resta 2, dentro del draw 1 porque se dibuja despues se aumenta
	private static int level;
	private int showLevel;
	private Window window;
	private User user= new User();
	private String username ;
	private String password ;


	public Container(Window window) {
		this.window = window;
		level = 1;
		this.showLevel = 0;
		// se crea un nivel con las caracteristicas del nivel
		levels.add(new Levels("Nivel 1", 5, 1, 5, 100, 5, 1));
		levels.add(new Levels("Nivel 2", 10, 2, 10, 35, 10, 1));
		levels.add(new Levels("Nivel 3", 1, 3, 15, 1, 10, 2.5f));

	}

	public void Draw(Graphics graphics) {
		line.draw(graphics);
		nave.draw(graphics);

		if (level == 1 && allAliensDead()) {
			handleLevelTransition(graphics, 1);
		} else if (level == 2 && allAliensDead()) {
			handleLevelTransition(graphics, 2);
		} else if (level == 3 && allAliensDead()) {
			handleLevelTransition(graphics, 3);
		}

		// se dibuja los aliens, las balas y la nave
		for (Alien alien : aliens) {
			alien.draw(graphics);
		}

		for (Bullet bullet : bullets_Alien) {
			bullet.draw(graphics);
		}

		for (Bullet bullet : bullets_Ship) {
			bullet.draw(graphics);
		}
	}

	private void handleLevelTransition(Graphics graphics, int currentLevel) {
		Ship.setLevel(levels.get(currentLevel - 1).getLevel());
		Ship.setShowLevel(true);
		nave.draw(graphics);

		// se para el hilo durante 1.5 segundos para mostrar en que nivel se encuentra
		try {
			Thread.sleep(1500);
			Ship.setShowLevel(false);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < levels.get(currentLevel - 1).getNumAlien(); i++) {
			if (currentLevel == 3) {
				aliens.add(new Alien(400, 100, levels.get(currentLevel - 1).getSizeAlien(), currentLevel));
			} else {
				aliens.add(new Alien(random.nextInt(RANDOM_X_ALIEN), random.nextInt(RANDOM_Y_ALIEN) + 40, levels.get(currentLevel - 1).getSizeAlien(), currentLevel));
			}
		}

		level += 1;
	}

	public void moveLeft(int move) {
		nave.moveLeft(move);
	}

	public void moveRight(int move) {
		nave.moveRight(move);
	}

	public void moveDown(int move) {
		// hace que el alien se quede estatico en el nivel 3
		if (level - 1 != 3) {
			for (Alien alien : aliens) {
				alien.moveDown(move);
			}
		}

		for (Bullet bullet : bullets_Alien) {
			bullet.moveDown(move * 10);
		}
	}

	public void moveUp(int move) {
		for (Bullet bullet : bullets_Ship) {
			bullet.moveUp(move * 5);
		}
	}

	public void createShoot_Ship() {
		if (nave.getLife() > 0) {
			bullets_Ship.add(new Bullet(nave, level - 1, 0));
		}
	}

	public void createShoot_Alien() {
		if (aliens.size() > 0) {
			int shot_Alien = random.nextInt(aliens.size());

			if (level - 1 == 1) {
				bullets_Alien.add(new Bullet(aliens.get(shot_Alien), level - 1, 0));
			} else if (level - 1 == 2) {
				bullets_Alien.add(new Bullet(aliens.get(shot_Alien), level - 1, 1));
				bullets_Alien.add(new Bullet(aliens.get(shot_Alien), level - 1, 4));
			} else if (level - 1 == 3) {
				bullets_Alien.add(new Bullet(aliens.get(shot_Alien), level - 1, 0));
				bullets_Alien.add(new Bullet(aliens.get(shot_Alien), level - 1, 1));
				bullets_Alien.add(new Bullet(aliens.get(shot_Alien), level - 1, 4));
			}
		}
	}

	public void updateGame() {
		checkCollisions();
		removeDeadAliens();
		removeOffScreenBullets();
		closeWindowAndShowGameOver();
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

					if (level - 1 == 3) {
						if (nave.getLife() <= 50) {
							alien.reduceLife(5);
							nave.moreScore(levels.get(level - 2).getMoreScore());
						} else if (nave.getLife() > 50 && nave.getLife() <= 75) {
							alien.reduceLife(10);
							nave.moreScore(levels.get(level - 2).getMoreScore());
						} else if (nave.getLife() > 75) {
							alien.reduceLife(15);
							nave.moreScore(levels.get(level - 2).getMoreScore());
						}
					} else {
						alien.reduceLife(levels.get(level - 2).getLifeAlien());
						nave.moreScore(levels.get(level - 2).getMoreScore());
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
				nave.reduceLife(levels.get(level - 2).getLessLife());
				bulletIterator.remove();
				break;
			}
		}
	}

	private void removeDeadAliens() {
		aliens.removeIf(alien -> alien.getLife() <= 0);
	}

	private void removeOffScreenBullets() {
		bullets_Ship.removeIf(bullet -> bullet.pos_Y < 0);
		aliens.removeIf(alien -> alien.getPoints_Y()[1] > (int) (600 * 0.66));
		bullets_Alien.removeIf(bullet -> bullet.pos_Y > 600);
	}

	public boolean allAliensDead() {
		return aliens.isEmpty();
	}

	public boolean levelThreeAndAllAliensDead() {
		return level == 4 && allAliensDead();
	}

	public void closeWindowAndShowGameOver() {

		if (nave.getLife() <= 0 || levelThreeAndAllAliensDead()) {
			window.dispose();
			new End(nave.getScore(), nave.getLife()).setVisible(true);
			yourMethod();

		}
	}

	public void yourMethod() {

		// URL del endpoint en el otro proyecto
		String url = "http://localhost:8080/createUser";


		// Configuración de cabeceras para la solicitud POST
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		// Creación de la entidad HTTP con los datos y las cabeceras

		user.setUser(Window.getUsername());
		user.setPassword(Window.getPassword());
		user.setLife(nave.getLife());
		user.setScore(nave.getScore());
		user.setLevel(level);


		HttpEntity<User> request = new HttpEntity<>(user, headers);

		// Instancia de RestTemplate
		RestTemplate restTemplate = new RestTemplate();

		// Envío de la solicitud POST
		ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

		// Manejo de la respuesta si es necesario
		if (response.getStatusCode().is2xxSuccessful()) {
			System.out.println("Solicitud POST enviada correctamente");
		} else {
			System.out.println("La solicitud POST no se pudo enviar correctamente");
		}
	}


}
