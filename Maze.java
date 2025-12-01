package project5;

import java.util.*;

public class Maze extends BST<MazeNode> {

    private Hero man;
    private int mazeDepth;    // deepest leaf depth in EDGES
    private ArrayList<String> route;

    public Maze() {
        super();
        man = new Hero();
        route = new ArrayList<>();
    }

    // Keep original API used by BinaryTreeMaze
    public void add(String name, int health) {
        super.add(new MazeNode(name, health));
    }

    public void setHp() {
        MazeNode r = super.getRoot();
        if (r != null) man.getHP(r.health);
    }

    public ArrayList<String> getRoute() {
        return route;
    }

    public void findPath() {
        if (root == null) return;

        // BST.height(): empty=0, leaf=1 (levels)
        // We need deepest leaf depth in edges (root depth=0): edges = levels - 1
        mazeDepth = super.height() - 1;

        route.clear();
        // life at root: collect root.health upon starting at root
        man = new Hero();
        man.getHP(root.data.health);
        findPathHelper("", root, 0, man);
    }

    /**
     * life means current life after collecting curr.data.health already.
     * Travel rule (important for your failing cases):
     *  moving to a child costs 1 life, this will eariler than gain health from node.
     *  if you die during the move (life<0), you cannot "revive" by the gero's health
     *  after arriving, you gain child's health
     */
    private void findPathHelper(String path, Node<MazeNode> curr, int depth, Hero hero) {
        if (curr == null) return;
        if (hero.HP() < 0) return;

        String newPath = path.isEmpty() ? curr.data.name : path + " " + curr.data.name;

        // leaf
        if (curr.left == null && curr.right == null) {
            if (depth == mazeDepth) route.add(newPath);
            return;
        }

        if (curr.left != null) {
            //copy a hero reference
            Hero leftHero = new Hero(hero); 
            if (leftHero.travel(curr.left.data)) {
                findPathHelper(newPath, curr.left, depth + 1, leftHero);
            }
        }

        if (curr.right != null) {
            Hero rightHero = new Hero(hero);
            if (rightHero.travel(curr.right.data)) {
                findPathHelper(newPath, curr.right, depth + 1, rightHero);
            }
        }
    }
}
