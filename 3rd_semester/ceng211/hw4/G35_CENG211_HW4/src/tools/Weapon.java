package tools;

import java.util.Random;

import enums.WeaponType;

/*
 * Represents a weapon that can be used by a character.
 */
public class Weapon {

    public WeaponType type;
    public int damage;

    public Weapon() {
        this.type = WeaponType.values()[new Random().nextInt(WeaponType.values().length)];
        this.damage = new Random().nextInt(11) + 10;
    }
    
}
