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

	// lista de objetos que queremos tener y vamos a modificar en pantalla porque se varios
	List<Levels> levels = new ArrayList<>();
	List<Alien> aliens = new ArrayList<>();
	List<Bullet> bullets_Ship = new ArrayList<>();
	List<Bullet> bullets_Alien = new ArrayList<>();

	// objetos que se dibujan solo uno en pantalla
	Ship nave;
	Line line = new Line();

	// varaibles de clase que se usa para distincas cosas en el container
	Random random = new Random();
	private static int level;

	private Window window;
	private User user= new User();
	private User userContinue= new User();
	private End end;
	private boolean touchRedLine;


	public Container(Window window) {

		this.window = window;

		// creacion de niveles con las caracteristicas de cada nivel
		levels.add(new Levels(1,"Nivel 1", 5, 1, 5, 1, 5, 1, 100));
		levels.add(new Levels(1,"Nivel 2", 10, 2, 10, 3, 10, 1, 100));
		levels.add(new Levels(1,"Nivel 3", 1, 3, 15, 1, 15, 2.5f, 100));

		// logica para crear un juego desde cero
		if (window.isRegisterButtonPressed()){
			System.out.println("entra por aqui por el boton de registro");
			nave = new Ship(false, 100);
			level = 1;
		}

	}

	public void Draw(Graphics graphics) {

		line.draw(graphics);
		nave.draw(graphics);

		if (level == 1 && aliens.isEmpty()) {
			handleLevelTransition(graphics, level-1);
		} else if (level == 2 && aliens.isEmpty()) {
			handleLevelTransition(graphics, level-1);
		} else if (level == 3 && aliens.isEmpty()) {
			handleLevelTransition(graphics, level-1);
		}else {
			levels.get(level-2).draw(graphics);
		}

		// se dibuja los aliens y las balas de la nave y de los aliens
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

	// metodo para mostrar titulo en pantalla y controla la creacion de los villanos dinamicamente
	private void handleLevelTransition(Graphics graphics, int currentLevel) {

		Ship.setLevel(levels.get(currentLevel).getLevelName());
		Ship.setShowLevel(true);
		nave.draw(graphics);

		// se para el hilo durante 1.5 segundos para mostrar en que nivel se encuentra
		try {
			Thread.sleep(1500);
			Ship.setShowLevel(false);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < levels.get(currentLevel).getNumAlien(); i++) {
			if (currentLevel + 1 == 3) {
				aliens.add(new Alien(400, 100, levels.get(currentLevel).getSizeAlien(), currentLevel+1, levels.get(currentLevel).getLifeAlien()));
			} else {
				aliens.add(new Alien(random.nextInt(RANDOM_X_ALIEN), random.nextInt(RANDOM_Y_ALIEN) + 40, levels.get(currentLevel).getSizeAlien(), currentLevel+1, levels.get(currentLevel).getLifeAlien()));
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

	// movimiento de la nave y las balas del mismo hacia abajo
	public void moveDown(int move) {
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

	// crea los disparos del la nave cuando su vida es mayor a 0
	public void createShoot_Ship() {
		if (nave.getLife() > 0) {
			bullets_Ship.add(new Bullet(nave, level - 1, 0));
		}
	}

	// crea los disparos del alien siempre y cuando la lista de aliens no este vacia
	public void createShoot_Alien() {

		if (!aliens.isEmpty()) {
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

	// metodo que actuliza el juego a cada rato para verificar ciertas condiciones y controlar la logica de muerte
	public void updateGame(boolean closeWindow) {
		checkCollisions();
		removeDeadAliens();
		removeOffScreenBullets();
		closeWindowAndShowGameOver(closeWindow);
	}

	//logica que maneja la colision entre las balas, los aliens y la nave
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
							nave.moreScore(levels.get(level - 2).getScoreXBullet());
						} else if (nave.getLife() > 50 && nave.getLife() <= 75) {
							alien.reduceLife(10);
							nave.moreScore(levels.get(level - 2).getScoreXBullet());
						} else if (nave.getLife() > 75) {
							alien.reduceLife(15);
							nave.moreScore(levels.get(level - 2).getScoreXBullet());
						}
					} else {
						alien.reduceLife(100/levels.get(level - 2).getReduceLifeAlien());
						nave.moreScore(levels.get(level - 2).getScoreXBullet());
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
				nave.reduceLife(levels.get(level - 2).getReduceLifeShip());
				bulletIterator.remove();
				break;
			}
		}
	}

	// metodo lambda que remueve al marciano cuando su vida sea inferioro o igual a 1
	private void removeDeadAliens() {
		aliens.removeIf(alien -> alien.getLife() <= 1);
	}

	// remueve las balas de la pantalla y al marciano cuanod cruce la lunea roja
	private void removeOffScreenBullets() {
		bullets_Ship.removeIf(bullet -> bullet.pos_Y < 0);
		touchRedLine = aliens.stream()
				.anyMatch(alien -> alien.getPoints_Y()[1] > line.getLineHeight());
		bullets_Alien.removeIf(bullet -> bullet.pos_Y > 600);
	}

	public boolean levelThreeAndAllAliensDead() {
		return level == 4  && aliens.isEmpty();
	}

	// logica para perder o ganar segun corresponda, y guardar datos cuando se cierra la ventana
	public void closeWindowAndShowGameOver(boolean closeWindow) {

		if (nave.getLife() <= 0 || levelThreeAndAllAliensDead() || touchRedLine || closeWindow) {
			window.dispose();
			//new End(nave.getScore(), nave.getLife()).setVisible(true);
			end = new End(nave.getScore(), nave.getLife());
			end.setVisible(true);
			end.setPassLineRed(touchRedLine);
			if (window.isRegisterButtonPressed()) {
				postUser(); // Realizar la solicitud POST solo si el botón de registro se ha presionado
			} else {
				updateUser();
			}
		}

	}

	// metodo para hacer un post en la web
	public void postUser() {

		// URL del endpoint en el otro proyecto
		String url = "http://localhost:8080/createUser";

		// Configuración de cabeceras para la solicitud POST
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		// Creación de la entidad HTTP con los datos y las cabeceras
		user.setUser(Window.getUsername());
		user.setPassword(Window.getPassword());
		user.setLifeShip(nave.getLife());
		user.setScore(nave.getScore());
		user.setLevel(level-1);
		if (level-1 == 3 ){
			if (!aliens.isEmpty()){
				user.setLifeAllien(aliens.get(0).getLife());
			}else{
				user.setLifeAllien(0);
			}
		} else {
			user.setLifeAllien(100);
		}
		user.setNumAllien(aliens.size());

		HttpEntity<User> request = new HttpEntity<>(user, headers);

		// Instancia de RestTemplate
		RestTemplate restTemplate = new RestTemplate();

		// Envío de la solicitud POST
		ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

		// Manejo de la respuesta si es necesario
		if (response.getStatusCode().is2xxSuccessful()) {
			System.out.println("Solicitud POST enviada correctamente" + user.toString());
		} else {
			System.out.println("La solicitud GET no se pudo enviar correctamente");
		}
	}

	// metodo para recuperar un usuario que devulve un bolean si este existe
	public  boolean getUser(String username, String password) {

		System.out.println("entra por el get");

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

					userContinue = user;
					System.out.println("Solicitud GET exitosa. Usuario obtenido: " + user.toString());

					// setea el nivel de donde nos quedamos
					nave = new Ship(true, user.getLifeShip());
					level = user.getLevel();
					nave.setScore(user.getScore());
					levels.get(level-1).setNumAlien(user.getNumAllien());
					levels.get(level-1).setLifeAlien(user.getLifeAllien());

					return true;

				} else {
					System.out.println("El cuerpo de la respuesta está vacío o mal formateado.");
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

	// metodo para actulizar un usurio de una partida continuada
	public void updateUser() {
		try {
			// URL del endpoint en el otro proyecto
			String url = "http://localhost:8080/updateUser";

			// Configuración de cabeceras para la solicitud PUT
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			// Instancia de RestTemplate
			RestTemplate restTemplate = new RestTemplate();

			// Crear el objeto User con los campos que se van a actualizar
			User updatedUser = new User();
			updatedUser.setLifeShip(nave.getLife());
			updatedUser.setScore(nave.getScore());
			updatedUser.setLevel(level-1);
			if (level-1 == 3 ){
				if (!aliens.isEmpty()){
					updatedUser.setLifeAllien(aliens.get(0).getLife());
				}else{
					updatedUser.setLifeAllien(0);
				}
			} else {
				updatedUser.setLifeAllien(100);
			}
			updatedUser.setNumAllien(aliens.size());

			System.out.println(updatedUser.toString());

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