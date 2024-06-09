package ec.uce.edu.basicJPA.models;

import jakarta.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",nullable = false)
    private int id;
    //, unique = true
    @Column(nullable = false)
    private String user;
    @Column(nullable = false)
    private String password;
    @Column
    private int lifeShip;
    @Column
    private int score;
    @Column
    private int level;
    @Column
    private int lifeAllien;
    @Column
    private int numAllien;

    public User() {
    }

    public User(int id, String user, int lifeShip, int score, String password, int lifeAllien, int level, int numAllien) {
        this.id = id;
        this.user = user;
        this.lifeShip = lifeShip;
        this.score = score;
        this.password = password;
        this.lifeAllien = lifeAllien;
        this.level = level;
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

    public int getLifeShip() {
        return lifeShip;
    }

    public void setLifeShip(int lifeShip) {
        this.lifeShip = lifeShip;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
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

}