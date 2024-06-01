package uce.edu.ec.Proyecto_Juego.Model.Stage;

public class Levels {

    private String level;// Nivel
    private int numAlien;// numeros de aliens por nivel
    private int numBullet;// numero de balas que dispara el alien por nivel
    private int lessLife;// vida que quita cada disparo del alien
    private int lifeAlien;// vida que se le quita al marciano con cad disparo
    private int moreScore;// socre que se suma dependiendo del nivel
    private float sizeAlien;// el tamaño del alien dependiendo el niveo

    public Levels(String level, int numAlien, int numBullet, int lessLife, int lifeAlien, int moreScore, float sizeAlien) {
        this.level = level;
        this.numAlien = numAlien;
        this.numBullet = numBullet;
        this.lessLife = lessLife;
        this.lifeAlien = lifeAlien;
        this.moreScore = moreScore;
        this.sizeAlien = sizeAlien;
    }

    public String getLevel() {
        return level;
    }

    public int getNumAlien() {
        return numAlien;
    }

    public int getNumBullet() {
        return numBullet;
    }

    public int getLessLife() {
        return lessLife;
    }

    public int getLifeAlien() {
        return lifeAlien;
    }

    public int getMoreScore() {
        return moreScore;
    }

    public float getSizeAlien() {
        return sizeAlien;
    }

}
