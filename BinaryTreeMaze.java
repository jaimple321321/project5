package project5;

import java.util.*;
import java.io.*;

public class BinaryTreeMaze {
    public static void main(String[] args) {
        // 主程序：负责读取输入文件并驱动迷宫搜索

        // 参数检查：确保仅传入一个文件路径，保持错误格式一致
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

        // 输入为空时给出提示
        if (tree.isEmpty()) {
            System.out.println("Maze does not contain any nodes.");
            return;
        }

        // 寻找最深层可达路径并输出
        tree.findPath();
        for (String ans : tree.getRoute()) {
            System.out.println(ans);
        }
    }
}

