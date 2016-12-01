package fantasy.wmj;


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


        //wall
        for (int i = 0; i < n; i++) {
            maze[0][i] = WALL;
            maze[n - 1][i] = WALL;
            maze[i][0] = WALL;
            maze[i][n - 1] = WALL;
        }

        //生成maze
        explore(maze, 1, 1);

        for (char[] raw : maze) {
            for (int i = 0; i < raw.length; i++) {
                if (raw[i] == NO_TOUCH) {
                    raw[i] = WALL;
                }
            }
        }
        //设置开始位置
        maze[1][1] = START;

        //放置宝藏
        putTreasure(maze, n);

        return maze;
    }

    public static void putTreasure(char[][] maze, int n) {
        while (true) {
            int x = mRandom.nextInt(n - 1) + 1;
            int y = mRandom.nextInt(n - 1) + 1;

            if (maze[y][x] == EMPTY) {
                maze[y][x] = TREASURE;
                return;
            }
        }
    }


    public static boolean isValidPoint(char[][] map, int x, int y) {
        if (x < 0 || y < 0)
            return false;

        if (map.length > y && map[y].length > x) {
            return true;
        }


        return false;
    }

    public static void explore(char[][] map, int x, int y) {
//        System.out.println("map = [" + map + "], x = [" + x + "], y = [" + y + "]");
        if (!isValidPoint(map, x, y) || map[y][x] != NO_TOUCH) {
            return;
        }

        map[y][x] = EMPTY;

        int[] order = new int[]{RIGHT, LEFT, UP, DOWN};

        shuffle(order, mRandom);

        for (int d : order) {
            switch (d) {
                case RIGHT:
                    if (couldExplore(map, x + 1, y, RIGHT)) {
                        explore(map, x + 1, y);
                    }
                    break;
                case LEFT:
                    if (couldExplore(map, x - 1, y, LEFT)) {
                        explore(map, x - 1, y);
                    }
                    break;
                case UP:
                    if (couldExplore(map, x, y - 1, UP)) {
                        explore(map, x, y - 1);
                    }
                    break;
                case DOWN:
                    if (couldExplore(map, x, y + 1, DOWN)) {
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
     * @param x         探索点 x
     * @param y         探索点 y
     * @param direction 探索方向
     * @return x，y 点能否探索
     */
    public static boolean couldExplore(char[][] map, int x, int y, int direction) {

        char next1, next2;

        next1 = peek(map, x, y, direction, 0);
        next2 = peek(map, x, y, direction, 1);

        char s1 = peek(map, x, y, (direction + 1) % 4, 1);
        char s2 = peek(map, x, y, (direction + 3) % 4, 1);
//        if (direction == LEFT || direction == UP) {
////            System.out.println("map = [" + map + "], x = [" + x + "," + y + "], direction = [" + direction + "]" + next1 + "," + next2 + "," + s1 + "," + s2);
//        }
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
                x -= distance;
                break;
            case RIGHT:
                x += distance;
                break;
            case UP:
                y -= distance;
                break;
            case DOWN:
                y += distance;
                break;

        }

        if (map.length > y && y >= 0 && map[y].length > x && x >= 0) {
            p = map[y][x];
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
        int n = 15;

        System.out.println("n = " + n + " \n");

        char[][] maze = generateMaze(n);


        printMap(maze);

        findTreasure(maze, 1, 1);

        maze[1][1] = START;

        printMap(maze);

    }

    /**
     * 寻找宝藏
     *
     * @param maze 迷宫
     * @param x    开始位置 x
     * @param y    开始位置 y
     * @return 是否找到宝藏
     */
    public static boolean findTreasure(char[][] maze, int x, int y) {
        if (maze.length - 1 <= y || y < 1 || x < 1 || maze[y].length - 1 <= x) {
            return false;
        }


        char current = maze[y][x];

        if (current == TREASURE) {
            return true;
        } else if (current == EMPTY || current == START) {

            maze[y][x] = PATH;
            if (findTreasure(maze, x - 1, y)) {
                return true;
            } else if (findTreasure(maze, x + 1, y)) {
                return true;
            } else if (findTreasure(maze, x, y - 1)) {
                return true;
            } else if (findTreasure(maze, x, y + 1)) {
                return true;
            } else {
                maze[y][x] = EMPTY;
                return false;
            }
        }
        return false;


    }
}
