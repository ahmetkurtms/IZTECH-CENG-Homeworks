package entity.character;

import java.util.Random;

import entity.Entity;
import entity.opponent.Opponent;
import enums.AttackType;
import enums.HumanType;
import enums.SpecialState;
import enums.WeaponType;
import exceptions.InsufficientStaminaException;
import exceptions.SpecialAlreadyUsedException;
import tools.Weapon;

public class Human<W extends Weapon> extends Entity implements Character<W> {
    public String name;
    public int stamina;
    public HumanType type;
    public W weapon;
    public SpecialState specialState;


    @SuppressWarnings("unchecked") // This is needed because of the generic type W
    public Human(String name) {
        super(100 + new Random().nextInt(51), 20 + new Random().nextInt(21), 10 + new Random().nextInt(90));
        this.stamina = 10;
        this.name = name;
        this.type = HumanType.values()[new Random().nextInt(HumanType.values().length)];
        this.weapon = (W) new Weapon();
        this.specialState = SpecialState.AVAILABLE;
    }

    public void punch(Opponent opponent) throws InsufficientStaminaException {
        if (stamina < 1) throw new InsufficientStaminaException();

        int currAttack = getCurrentAttack(this.attack);
        handleSpecial();

        if (opponent.isGuarded) opponent.points -= currAttack * 0.4;
        else opponent.points -= currAttack * 0.8;

        stamina--;
        numberOfTurns--;
    }

    public void attackWithWeapon(Opponent opponent, AttackType attackType) throws InsufficientStaminaException {
        if ((weapon.type == WeaponType.SWORD || weapon.type == WeaponType.SPEAR) && (stamina < 2)) throw new InsufficientStaminaException();
        if (weapon.type == WeaponType.BOW) {
            if (attackType == AttackType.FIRST && stamina < 1) throw new InsufficientStaminaException();
            if (attackType == AttackType.SECOND && stamina < 3) throw new InsufficientStaminaException();
        }


        int combinedAttack = getCurrentAttack(this.attack + weapon.damage);
        handleSpecial();

        switch (weapon.type)
        {
            case SWORD:
                switch (attackType)
                {
                    case FIRST: // Slash
                        if (opponent.isGuarded) opponent.points -= combinedAttack / 2;
                        else opponent.points -= combinedAttack;
                        break;
                
                    case SECOND: // Stab
                    if (new Random().nextInt(100) < 75) {
                        if (opponent.isGuarded) opponent.points -= combinedAttack;
                        else opponent.points -= 2 * combinedAttack;
                    }
                        break;
                }
                stamina -= 2;
                break;
            
            case SPEAR:
                switch (attackType)
                {
                    case FIRST: // Stab
                        if (opponent.isGuarded) opponent.points -= 1.1 * combinedAttack / 2;
                        else opponent.points -= 1.1 * combinedAttack;
                        break;
                
                    case SECOND: // Throw
                        if (opponent.isGuarded) opponent.points -= combinedAttack;
                        else opponent.points -= 2 * combinedAttack;
                        numberOfTurns--;
                        break;
                }
                stamina -= 2;
                break;

            case BOW:
                switch (attackType)
                {
                    case FIRST: // Single arrow
                        if (opponent.isGuarded) opponent.points -= 0.4 * combinedAttack;
                        else opponent.points -= 0.8 * combinedAttack;
                        stamina--;
                        break;
                
                    case SECOND: // Double arrow
                        if (opponent.isGuarded) opponent.points -= 1.25 * combinedAttack;
                        else opponent.points -= 2.5 * combinedAttack;
                        stamina -= 3;
                        break;
                }
                break;
        }
        numberOfTurns--;
    }

    public void guard() {
        handleSpecial();

        this.isGuarded = true;
        stamina += 3;
        numberOfTurns--;
    }

    public void run() {
        // TODO: Implement run method
    }

    public void special(Opponent opponent) throws SpecialAlreadyUsedException {
        if (!(specialState == SpecialState.AVAILABLE)) throw new SpecialAlreadyUsedException();

        switch (type)
        {
            case KNIGHT:
                numberOfTurns--;
                specialState = SpecialState.USED_AND_SECOND_TURN;
                break;
            
            case HUNTER:
                specialState = SpecialState.USED_AND_FIRST_TURN;
                break;

            case SQUIRE:
                stamina = 10;
                specialState = SpecialState.USED_AND_SECOND_TURN;
                break;
            
            case VILLAGER:
                specialState = SpecialState.UNAVAILABLE;
                break;
        }
    }

    private int getCurrentAttack(int attack) {
        int currAttack = attack;
        if (specialState == SpecialState.USED_AND_FIRST_TURN || specialState == SpecialState.USED_AND_SECOND_TURN) {
            switch (type)
            {
                case KNIGHT: currAttack *= 3;   break;
                case HUNTER: currAttack *= 0.5; break;
                case SQUIRE: currAttack *= 0.5; break;
                case VILLAGER:                  break;
            }
        }
        return currAttack;
    }

    private void handleSpecial() {
        if (type == HumanType.HUNTER && specialState == SpecialState.USED_AND_SECOND_TURN) numberOfTurns += 2;

        switch (specialState)
        {
            case AVAILABLE:            specialState = SpecialState.USED_AND_FIRST_TURN;  break;
            case USED_AND_FIRST_TURN:  specialState = SpecialState.USED_AND_SECOND_TURN; break;
            case USED_AND_SECOND_TURN: specialState = SpecialState.UNAVAILABLE;          break;
            case UNAVAILABLE:                                                            break;
        }
    }

    @Override
    public String toString() {
        return name + ", Job: " + type + ", Points: " + points + ", Stamina: " + stamina + ", Attack: " + attack + ", Speed: " + speed + ", Weapon: " + weapon.type + " with +" + weapon.damage + " attack" ;
    }
}
