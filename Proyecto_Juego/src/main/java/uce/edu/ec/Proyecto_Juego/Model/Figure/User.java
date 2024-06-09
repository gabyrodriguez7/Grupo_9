package uce.edu.ec.Proyecto_Juego.Model.Figure;

public class User {

    private int id;
    private String user;
    private String password;
    private int lifeShip;
    private int score;
    private int level;
    private int lifeAllien;
    private int numAllien;

    public User() {
    }

    public User(int id, String user, String password, int lifeShip, int score, int level, int lifeAllien, int numAllien) {
        this.id = id;
        this.user = user;
        this.password = password;
        this.lifeShip = lifeShip;
        this.score = score;
        this.level = level;
        this.lifeAllien = lifeAllien;
        this.numAllien = numAllien;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLifeShip() {
        return lifeShip;
    }

    public void setLifeShip(int lifeShip) {
        this.lifeShip = lifeShip;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLifeAllien() {
        return lifeAllien;
    }

    public void setLifeAllien(int lifeAllien) {
        this.lifeAllien = lifeAllien;
    }

    public int getNumAllien() {
        return numAllien;
    }

    public void setNumAllien(int numAllien) {
        this.numAllien = numAllien;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", lifeShip=" + lifeShip +
                ", score=" + score +
                ", level=" + level +
                ", lifeAllien=" + lifeAllien +
                ", numAllien=" + numAllien +
                '}';
    }

}