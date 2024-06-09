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
    private int life;
    @Column
    private int score;
    @Column
    private int level;

    public User() {
    }

    public User( String user, String password, int life, int score, int level) {
        this.user = user;
        this.password = password;
        this.life = life;
        this.score = score;
        this.level = level;
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

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", life=" + life +
                ", score=" + score +
                ", level=" + level +
                '}';
        }

}