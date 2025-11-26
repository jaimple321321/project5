package project5;
import java.util.*;

public class Hero{
    private int hp;

    // 初始英雄，默认生命值为 0
    public Hero(){
        hp = 0;
    }

    // 获得宝物（正值）或受到伤害（负值）时更新生命值
    public void getHP(int loot){
        this.hp += loot;
    }
    public int HP() { return hp; }


    // 复制构造函数，用于保存当前英雄状态
    public Hero(Hero other) { this.hp = other.hp; }


    // 在迷宫节点间移动，结点的 health 可能为正或负
    // 返回 false 表示生命值耗尽，英雄无法继续前进
    public boolean travel(MazeNode det){
        hp += det.health -1;
        if(hp < 0) return false;
        return true;
    }
}
