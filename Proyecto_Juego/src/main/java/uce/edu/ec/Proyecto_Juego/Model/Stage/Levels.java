package uce.edu.ec.Proyecto_Juego.Model.Stage;

import uce.edu.ec.Proyecto_Juego.Model.Figure.Characters;
import uce.edu.ec.Proyecto_Juego.Model.Interfaces.Drawable;

import java.awt.*;

public class Levels implements Drawable {

    private int levelGame; // nivel del juego que maneja los atributos
    private String levelName; // nombre del Nivel del juego
    private int numAlien; // numeros de aliens que apareen en cada nivel
    private int numBulletAllien; // numero de balas que dispara el alien por nivel
    private int reduceLifeShip; // vida que quita cada disparo del alien
    private int reduceLifeAlien; // vida que se le quita al marciano con cad disparo
    private int scoreXBullet; // score que se suma por cada bala que impacta al marciano
    private float sizeAlien; // el tamaño del alien de cada nivel
    private int lifeAlien; // ls vida con la que incian los marcianos, usada para controlar la vida del jefe

    public Levels(int levelGame, String levelName, int numAlien, int numBulletAllien, int reduceLifeShip, int reduceLifeAlien, int scoreXBullet, float sizeAlien, int lifeAlien) {
        this.levelGame = levelGame;
        this.levelName = levelName;
        this.numAlien = numAlien;
        this.numBulletAllien = numBulletAllien;
        this.reduceLifeShip = reduceLifeShip;
        this.reduceLifeAlien = reduceLifeAlien;
        this.scoreXBullet = scoreXBullet;
        this.sizeAlien = sizeAlien;
        this.lifeAlien = lifeAlien;
    }

    public int getLevelGame() {
        return levelGame;
    }

    public void setLevelGame(int levelGame) {
        this.levelGame = levelGame;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public int getNumAlien() {
        return numAlien;
    }

    public void setNumAlien(int numAlien) {
        this.numAlien = numAlien;
    }

    public int getNumBulletAllien() {
        return numBulletAllien;
    }

    public void setNumBulletAllien(int numBulletAllien) {
        this.numBulletAllien = numBulletAllien;
    }

    public int getReduceLifeShip() {
        return reduceLifeShip;
    }

    public void setReduceLifeShip(int reduceLifeShip) {
        this.reduceLifeShip = reduceLifeShip;
    }

    public int getReduceLifeAlien() {
        return reduceLifeAlien;
    }

    public void setReduceLifeAlien(int reduceLifeAlien) {
        this.reduceLifeAlien = reduceLifeAlien;
    }

    public int getScoreXBullet() {
        return scoreXBullet;
    }

    public void setScoreXBullet(int scoreXBullet) {
        this.scoreXBullet = scoreXBullet;
    }

    public float getSizeAlien() {
        return sizeAlien;
    }

    public void setSizeAlien(float sizeAlien) {
        this.sizeAlien = sizeAlien;
    }

    public int getLifeAlien() {
        return lifeAlien;
    }

    public void setLifeAlien(int lifeAlien) {
        this.lifeAlien = lifeAlien;
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.setColor(Color.WHITE);
        Font fontlevel = new Font("Courier New", Font.PLAIN, 40); // Cambia la fuente y el tamaño según tus preferencias
        graphics.setFont(fontlevel);
        graphics.drawString(levelName, 350, 70);
    }

    @Override
    public void draw(Graphics graphics, Characters character) {
    }

}