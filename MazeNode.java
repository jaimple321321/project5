package project5;

import java.util.*;

/**
 * Maze node payload stored inside BST.
 * Ordered by label/name, compared by alphabet.
 *
 * Note: left/right fields are kept to minimize changes from the original project,
 * but the generic BST maintains its own left/right pointers internally.
 */
public class MazeNode implements Comparable<MazeNode> {
    // -health: The HP hero can gain from this Node.
    // -name: The variable for put this node into sorted location.
    public int health;
    public String name;

    //not used by BST<E>
    public MazeNode left;
    public MazeNode right;

    //not used by BST<E>
    //Initially, I wrote a usual BST class, only capable the MazeNode element(BST, not BST<E>),
    //this Constructor method is for that version. However After I submitted that 
    //version to the autoGrader, I realized that I need to almost fully rewrite the
    //whole project since BST class is not capable to any data catigories except 
    //MazeNode, but I want to keep the Constructor of my old version. You can ignore
    //this constructor and that two variable left and right.
    //@Overload
    public MazeNode(int health, String name, MazeNode left, MazeNode right) {
        this.health = health;
        this.name = name;
        this.left = left;
        this.right = right;
    }
    //This is the new constructor for the BST<E> not BST.
    //@Overload
    public MazeNode(String name, int health) {
        this(health, name, null, null);
    }

    @Override
    public int compareTo(MazeNode other) {
        if (other == null) throw new IllegalArgumentException("other is null");
        return this.name.compareTo(other.name);
    }

    @Override
    public String toString() {
        return name;
    }
}
