package project5;
import java.util.*;

public class Hero{
    /**
     * 记录英雄当前的生命值（HP）。
     * 生命值会在移动时减少 1，再根据节点生命值进行恢复或继续扣减。
     */
    private int hp;

    // 初始英雄，默认生命值为 0
    public Hero(){
        hp = 0;
    }

    /**
     * 获得宝物（正值）或受到伤害（负值）时更新生命值。
     * 该方法在起点或事件结算时使用。
     */
    public void getHP(int loot){
        this.hp += loot;
    }

    /**
     * 返回当前生命值，便于调试或外部读取。
     */
    public int HP() { return hp; }


    // 复制构造函数，用于保存当前英雄状态
    public Hero(Hero other) { this.hp = other.hp; }


    /**
     * 在迷宫节点间移动。规则：
     * 1. 先扣除 1 点移动消耗。
     * 2. 若扣除后生命值为负，则直接失败。
     * 3. 抵达后再结算目标节点的生命值增减。
     * @param det 目的节点（包含该节点的生命值变化）
     * @return 抵达并存活返回 true，否则返回 false。
     */
    public boolean travel(MazeNode det){
        hp -= 1;           // 先支付移动成本
        if(hp < 0) return false;
        hp += det.health;  // 结算到达后的生命变化
        return hp >= 0;
    }
}
