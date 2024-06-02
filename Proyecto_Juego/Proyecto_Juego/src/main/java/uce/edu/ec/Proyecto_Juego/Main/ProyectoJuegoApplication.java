package uce.edu.ec.Proyecto_Juego.Main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import uce.edu.ec.Proyecto_Juego.Model.Figure.User;
import uce.edu.ec.Proyecto_Juego.Window.Window;

@SpringBootApplication
public class ProyectoJuegoApplication {

	public static void main(String[] args) {
		Window juego = new Window();
		SpringApplication.run(ProyectoJuegoApplication.class, args);
		





	}

}
