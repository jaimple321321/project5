package project5;
import java.util.*;

public class Hero{
    /**
     * Record the current HP
     * It will decreases before moving.
     */
    private int hp;

    // Default HP is zero, but we never use it.
    /** Create a hero with zero starting health. */
    public Hero(){
        hp = 0;
    }

    /** Increase current health by the given loot value. */
    public void getHP(int loot){
        this.hp += loot;
    }

    /** Read current health value. */
    public int HP() { return hp; }


    // Copy a new hero object.
    /** Create a copy of another hero's health status. */
    public Hero(Hero other) { this.hp = other.hp; }


    /** Spend one health to travel and gain the child's health if still alive. */
    public boolean travel(MazeNode det){
        hp -= 1;           // Pay
        if(hp < 0) return false;
        hp += det.health;  // Calculate
        return hp >= 0;
    }
}
