package uce.edu.ec.Proyecto_Juego.Controller;

import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import uce.edu.ec.Proyecto_Juego.Model.Figure.Alien;
import uce.edu.ec.Proyecto_Juego.Model.Figure.Bullet;
import uce.edu.ec.Proyecto_Juego.Model.Figure.Ship;
import uce.edu.ec.Proyecto_Juego.Model.Figure.User;
import uce.edu.ec.Proyecto_Juego.Model.Stage.Levels;
import uce.edu.ec.Proyecto_Juego.Model.Stage.Line;
import uce.edu.ec.Proyecto_Juego.Window.Window;
import uce.edu.ec.Proyecto_Juego.Window.End;

import java.awt.Graphics;

import java.util.*;

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
	boolean validar=false;
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
		return level == 4  && allAliensDead();
	}

	public void closeWindowAndShowGameOver() {

		if (nave.getLife() <= 0 || levelThreeAndAllAliensDead()) {
			window.dispose();
			new End(nave.getScore(), nave.getLife()).setVisible(true);
			if (window.isRegisterButtonPressed()) {
				postUser(); // Realizar la solicitud POST solo si el botón de registro se ha presionado
			} else {
				updateUser();
			}

		}
	}

	public void postUser() {

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
		user.setLevel(level-1);


		HttpEntity<User> request = new HttpEntity<>(user, headers);

		// Instancia de RestTemplate
		RestTemplate restTemplate = new RestTemplate();

		// Envío de la solicitud POST
		ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

		// Manejo de la respuesta si es necesario
		if (response.getStatusCode().is2xxSuccessful()) {
			System.out.println("Solicitud POST enviada correctamente");
		} else {
			System.out.println("La solicitud GET no se pudo enviar correctamente");
		}
	}

	public boolean getUser(String username, String password) {

		// URL del endpoint en el otro proyecto
		String url = "http://localhost:8080/getUser";

		// Configuración de cabeceras para la solicitud GET
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		// Instancia de RestTemplate
		RestTemplate restTemplate = new RestTemplate();

		try {
			// Crear los parámetros para la solicitud GET
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
					.queryParam("user", username)
					.queryParam("password", password);

			// Realizar la solicitud GET y obtener la respuesta
			ResponseEntity<User> response = restTemplate.exchange(
					builder.toUriString(),
					HttpMethod.GET,
					null,
					User.class
			);

			// Manejo de la respuesta si es necesario
			if (response.getStatusCode().is2xxSuccessful()) {
				User user = response.getBody();
				if (user != null) {
					System.out.println("Solicitud GET exitosa. Usuario obtenido: " + user.toString());
					return true;
				} else {
					System.out.println("Usuario no encontrado.");
					return false;
				}
			} else {
				System.out.println("La solicitud GET no se pudo enviar correctamente");
				return false;
			}
		} catch (HttpClientErrorException e) {
			if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
				System.out.println("Usuario no encontrado.");
			} else {
				System.out.println("Error en la solicitud: " + e.getStatusCode());
			}
			return false;
		} catch (Exception e) {
			System.out.println("Error inesperado: " + e.getMessage());
			return false;
		}
	}


	public void updateUser() {
		try {
			// URL del endpoint en el otro proyecto
			String url = "http://localhost:8080/updateUser";

			// Configuración de cabeceras para la solicitud PUT
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			// Instancia de RestTemplate
			RestTemplate restTemplate = new RestTemplate();

			// Verificar si el usuario existe antes de actualizar
			boolean userExists = getUser(Window.getUsername(), Window.getPassword());

			if (!userExists) {
				System.out.println("No se puede actualizar. El usuario no existe.");
				return;
			}

			// Crear el objeto User con los campos que se van a actualizar
			User updatedUser = new User();
			updatedUser.setLife(nave.getLife());
			updatedUser.setScore(nave.getScore());
			updatedUser.setLevel(level-1);

			// Crear la entidad HTTP con los datos y las cabeceras
			HttpEntity<User> request = new HttpEntity<>(updatedUser, headers);

			// Realizar la solicitud PUT y obtener la respuesta
			ResponseEntity<User> response = restTemplate.exchange(
					url + "?user=" + Window.getUsername() + "&password=" + Window.getPassword(),
					HttpMethod.PUT,
					request,
					User.class
			);

			// Manejo de la respuesta
			if (response.getStatusCode().is2xxSuccessful()) {
				User user = response.getBody();
				if (user != null) {
					System.out.println("Solicitud PUT exitosa. Usuario actualizado: " + user.toString());
				} else {
					System.out.println("Usuario no encontrado.");
				}
			} else {
				System.out.println("La solicitud PUT no se pudo enviar correctamente");
			}
		} catch (Exception e) {
			System.out.println("Error durante la solicitud PUT: " + e.getMessage());
		}
	}




}
