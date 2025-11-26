package project5;

import java.util.*;
import java.io.*;

public class BinaryTreeMaze {
    public static void main(String[] args) {

        // If bad args, still match the required error format as closely as possible
        if (args.length != 1) {
            System.out.println("Error: the file " + (args.length > 0 ? new File(args[0]).getAbsolutePath() : "") + " does not exist.");
            return;
        }

        File f = new File(args[0]);
        String abs = f.getAbsolutePath();

        if (!f.exists() || !f.isFile()) {
            System.out.println("Error: the file " + abs + " does not exist.");
            return;
        }

        Maze tree = new Maze();

        try (Scanner sc = new Scanner(f)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;

                Scanner lineScanner = new Scanner(line);
                if (!lineScanner.hasNext()) { lineScanner.close(); continue; }

                String name = lineScanner.next();
                if (!lineScanner.hasNextInt()) { lineScanner.close(); continue; }

                int health = lineScanner.nextInt();
                lineScanner.close();

                tree.add(name, health);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: the file " + abs + " does not exist.");
            return;
        }

        if (tree.isEmpty()) {
            System.out.println("Maze does not contain any nodes.");
            return;
        }

        tree.findPath();
        for (String ans : tree.getRoute()) {
            System.out.println(ans);
        }
    }
}

