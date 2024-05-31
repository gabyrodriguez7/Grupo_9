package uce.edu.ec.Proyecto_Juego.Model.Stage;

import uce.edu.ec.Proyecto_Juego.Model.Figure.Characters;
import uce.edu.ec.Proyecto_Juego.Model.Interfaces.Drawable;

import java.awt.Color;
import java.awt.Graphics;


public class Line implements Drawable {
	
	@Override
	public void draw(Graphics graphics) {
		graphics.setColor(Color.RED);		
		graphics.fillRect(0, (int)(600*0.66), 800, 6);	
	}

	@Override
	public void draw(Graphics graphics, Characters character) {

	}

	
	

}
