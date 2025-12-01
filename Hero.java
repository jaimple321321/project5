package project5;
import java.util.*;

public class Hero{
    /**
     * Record the current HP
     * It will decreases before moving.
     */
    private int hp;

    // Default HP is zero, but we never use it.
    public Hero(){
        hp = 0;
    }

    /**
     * Update the hp(contains lose hp).
     */
    public void getHP(int loot){
        this.hp += loot;
    }

    /**
     * Return the current HP.
     */
    public int HP() { return hp; }


    // Copy a new hero object.
    public Hero(Hero other) { this.hp = other.hp; }


    /**
     * Move in maze. Rule(sequence)
     * 1. Minus 1 HP first
     * 2. if HP after minus smllar or equals to 0, return false.
     * 3. calculate the variation after arrival.
     * @param det termination.
     * @return true if arrived and hp > 0, else false.
     */
    public boolean travel(MazeNode det){
        hp -= 1;           // Pay
        if(hp < 0) return false;
        hp += det.health;  // Calculate
        return hp >= 0;
    }
}
