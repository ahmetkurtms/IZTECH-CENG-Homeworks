package entity.opponent;

import java.util.Deque;
import java.util.List;
import java.util.Random;

import entity.Entity;
import entity.character.Human;
import enums.OpponentType;
import tools.Weapon;


public class Opponent extends Entity {
    public int opponentID;
    public OpponentType type;

    public Opponent(int opponentID) {
        super(50 + new Random().nextInt(101), 5 + new Random().nextInt(21), 1 + new Random().nextInt(90));
        this.opponentID = opponentID;
        this.type = OpponentType.values()[new Random().nextInt(OpponentType.values().length)];
    }

    public Opponent(int opponentID, int points, int attack, int speed, int numberOfTurns, boolean isGuarded, OpponentType type) {
        super(points, attack, speed, numberOfTurns, isGuarded);
        this.opponentID = opponentID;
        this.type = type;
    }

    public void attack(Human<Weapon> human) {
        if (human.isGuarded) human.points -= this.attack / 4;
        else human.points -= this.attack;

        numberOfTurns--;
    }

    /*
     * Don’t do anything this turn and receive %50 less damage until the start of the next turn.
     */
    public void guard() {
        this.isGuarded = true;

        numberOfTurns--;
    }

    /**
     * Uses the special ability of the opponent. The opponents and turnOrderDeque is needed for WOLF's special ability.
     * 
     * @param human person to attack
     * @param opponents list of opponents, needed for WOLF's special ability
     * @param turnOrderDeque deque of turns, needed for WOLF's special ability
     */
    public void special(Human<Weapon> human, List<Opponent> opponents, Deque<Entity> turnOrderDeque) {
        switch (type) {
            case SLIME: // Attacks normally and increases its points equal to the damage dealt.
                // Attack based on guard
                if (human.isGuarded) human.points -= this.attack / 4;
                else human.points -= this.attack;
                // Increase points
                this.points += this.attack;
                this.points = this.points > 150 ? 150 : this.points;
                break;
            
            case GOBLIN: // Attacks normally and gets another turn immediately after the current one. It deals damage 0.7 × attack stat for both of these turns.
                // Attack based on guard
                if (human.isGuarded) human.points -= ((int) (this.attack * 0.7)) / 4;
                else human.points -= (int) (this.attack * 0.7);
                // Get another turn
                this.numberOfTurns++;
                // TODO: Make sure the goblin's normal attack is also (0.7 x attack) for the second turn, use specialState to track
                break;

            case ORC: // It deals damage for 2 × attack stat and skips its next turn.
                // Attack based on guard
                if (human.isGuarded) human.points -= this.attack / 2;
                else human.points -= this.attack * 2;
                // Skip next turn
                this.numberOfTurns--;
                break;

            case WOLF: // This action  has  20%  chance  of  success.  If successful,  an  identical  wolf  is  created,  and  it  joins  the  opponents.  If  this  is unsuccessful, nothing happens.    
                if (new Random().nextInt(100) < 20) {
                    Opponent newWolf = this.clone(opponents.size() + 1);
                    opponents.add(newWolf);
                    turnOrderDeque.addFirst(newWolf);
                }
                break;
        }
        numberOfTurns--;
    }

    public Opponent clone(int newOpponentID) {
        return new Opponent(newOpponentID, this.points, this.attack, this.speed, this.numberOfTurns, this.isGuarded, this.type);
    }

    @Override
    public String toString() {
        return "ID: " + opponentID + ", Type: " + type + ", Points: " + points + ", Attack: " + attack + ", Speed: " + speed;
    }
}
