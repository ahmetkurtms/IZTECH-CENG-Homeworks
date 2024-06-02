package entity;

public abstract class Entity {
    public int points;
    public int attack;
    public int speed;
    public int numberOfTurns;
    public boolean isGuarded;

    public Entity(int points, int attack, int speed) {
        this.points = points;
        this.attack = attack;
        this.speed = speed;
        this.numberOfTurns = 1;
        this.isGuarded = false;
    }

    public Entity(int points, int attack, int speed, int numberOfTurns, boolean isGuarded) {
        this.points = points;
        this.attack = attack;
        this.speed = speed;
        this.numberOfTurns = numberOfTurns;
        this.isGuarded = isGuarded;
    }

    public int getSpeed() {
        return speed;
    }
}
