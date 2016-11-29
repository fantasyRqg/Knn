package fantasy.wmj;

import fantasy.wmj.knn.DistanceMatrix;
import fantasy.wmj.utils.ArrayUtils;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by rqg on 29/11/2016.
 */

public class Maze {


    public static final char WALL = '#';
    public static final char START = 'S';
    public static final char TREASURE = '*';
    public static final char PATH = '.';
    public static final char EMPTY = ' ';
    public static final char NO_TOUCH = '\0';

    public static final char NULL = '`';


    public static final int LEFT = 0;
    public static final int UP = 1;
    public static final int RIGHT = 2;
    public static final int DOWN = 3;


    public static Random mRandom = new Random(System.currentTimeMillis());

    /**
     * 产生迷宫
     *
     * @param n
     * @return
     */
    public static char[][] generateMaze(int n) {
        n = n + 1;
        char[][] maze = new char[n][n];

        for (char[] raw : maze) {
            Arrays.fill(raw, NO_TOUCH);
        }
        maze[1][1] = START;


        //wall
        for (int i = 0; i < n; i++) {
            maze[0][i] = WALL;
            maze[n - 1][i] = WALL;
            maze[i][0] = WALL;
            maze[i][n - 1] = WALL;
        }

        if (mRandom.nextBoolean()) {
            explore(maze, 1, 2);

            if (couldExplore(maze, 1, 1, RIGHT))
                explore(maze, 2, 1);

        } else {
            explore(maze, 2, 1);
            if (couldExplore(maze, 1, 1, DOWN))
                explore(maze, 1, 2);
        }

        return maze;
    }


    public static boolean isValidPoint(char[][] map, int x, int y) {
        if (x < 0 || y < 0)
            return false;

        if (map.length > x && map[x].length > y) {
            return true;
        }


        return false;
    }

    public static void explore(char[][] map, int x, int y) {
        if (!isValidPoint(map, x, y) || map[x][y] != NO_TOUCH) {
            return;
        }

        map[x][y] = EMPTY;

        int[] order = new int[]{RIGHT, LEFT, UP, DOWN};

        shuffle(order, mRandom);

        for (int d : order) {
            switch (d) {
                case RIGHT:
                    if (couldExplore(map, x, y, RIGHT)) {
                        explore(map, x + 1, y);
                    }
                    break;
                case LEFT:
                    if (couldExplore(map, x, y, LEFT)) {
                        explore(map, x - 1, y);
                    }
                    break;
                case UP:
                    if (couldExplore(map, x, y, UP)) {
                        explore(map, x, y - 1);
                    }
                    break;
                case DOWN:
                    if (couldExplore(map, x, y, DOWN)) {
                        explore(map, x, y + 1);
                    }
                    break;
            }
        }

    }

    /**
     * 随即重排列数组
     *
     * @param array 重拍数组
     * @param rnd   随机变量
     */
    public static void shuffle(int[] array, Random rnd) {
        // Shuffle array
        for (int i = array.length; i > 1; i--)
            swap(array, i - 1, rnd.nextInt(i));
    }

    /**
     * Swaps the two specified elements in the specified array.
     */
    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    /**
     * 产生地图时使用，能否在指定方向继续探索
     *
     * @param map
     * @param x
     * @param y
     * @param direction
     * @return
     */
    public static boolean couldExplore(char[][] map, int x, int y, int direction) {
        char next1, next2;

        next1 = peek(map, x, y, direction, 1);
        next2 = peek(map, x, y, direction, 2);

        char s1 = peek(map, x, y, (direction + 1) % 4, 1);
        char s2 = peek(map, x, y, (direction + 3) % 4, 1);

        return next1 == NO_TOUCH && next2 != NULL && next2 != EMPTY && s1 != EMPTY && s2 != EMPTY;
    }


    /**
     * 查看下个位置
     *
     * @param map       地图
     * @param x         当前x
     * @param y         当前 y
     * @param direction 方向
     * @param distance  查看距离当前点的距离
     * @return
     */
    public static char peek(char[][] map, int x, int y, int direction, int distance) {

        char p = NULL;

        switch (direction) {
            case LEFT:
                y -= distance;
                break;
            case RIGHT:
                y += distance;
                break;
            case UP:
                x -= distance;
                break;
            case DOWN:
                x += distance;
                break;

        }

        if (map.length > x && x > 0 && map[x].length > y && y > 0) {
            p = map[x][y];
        }


        return p;
    }


    /**
     * 打印地图
     *
     * @param maze
     */
    public static void printMap(char[][] maze) {
        StringBuilder sb = new StringBuilder();


        for (char[] raw : maze) {
            for (char p : raw) {
                sb.append(p);
            }
            sb.append("\n");
        }

        System.out.println(sb.toString());
    }

    public static void main(String[] args) {
        char[][] chars = generateMaze(10);


        printMap(chars);

    }
}
