package uce.edu.ec.Proyecto_Juego.Model.Stage;

import uce.edu.ec.Proyecto_Juego.Model.Figure.Characters;
import uce.edu.ec.Proyecto_Juego.Model.Interfaces.Drawable;

import java.awt.Color;
import java.awt.Graphics;

public class Line implements Drawable {

	private int lineHeight;

	public Line() {
		this.lineHeight = (int)(600*0.66);
	}

	@Override
	public void draw(Graphics graphics) {
		graphics.setColor(Color.RED);
		graphics.fillRect(0, lineHeight, 800, 6);
	}

	@Override
	public void draw(Graphics graphics, Characters character) {

	}

	public int getLineHeight() {
		return lineHeight;
	}

	public void setLineHeight(int lineHeight) {
		this.lineHeight = lineHeight;
	}

}