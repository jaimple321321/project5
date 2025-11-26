package project5;
import java.util.*;

public class Hero{
    private int hp;

    public Hero(){
        hp = 0;
    }

    public void getHP(int loot){
        this.hp += loot;
    }
    public int HP() { return hp; }


    public Hero(Hero other) { this.hp = other.hp; }


    public boolean travel(MazeNode det){
        hp += det.health -1;
        if(hp < 0) return false;
        return true;
    }
}
