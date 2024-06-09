package uce.edu.ec.Proyecto_Juego.Model.Interfaces;

import uce.edu.ec.Proyecto_Juego.Model.Figure.Characters;

import java.awt.Graphics;


public interface Drawable {
		
	void draw(Graphics graphics); 
	void draw(Graphics graphics, Characters character);

}