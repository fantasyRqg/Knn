package fantasy.wmj;

import java.util.Arrays;
import java.util.Random;
import java.util.Stack;

/**
 * Created by rqg on 01/12/2016.
 */
class MazeA {

    static final char WALL = '#';
    static final char START = 'S';
    static final char TREASURE = '*';
    static final char PATH = '.';
    static final char EMPTY = ' ';
    static final char NO_TOUCH = '\0';
    static final char NULL = '`';


    static Random mRandom = new Random(System.currentTimeMillis());

    static final int LEFT = 0;
    static final int UP = 1;
    static final int RIGHT = 2;
    static final int DOWN = 3;


    public static class Point {
        public Point() {
        }

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int x;
        public int y;
    }

    /**
     * 放置 宝藏
     *
     * @param maze
     * @param n
     */
    static void putTreasure(char[][] maze, int n) {
        while (true) {
            int x = mRandom.nextInt(n - 1) + 1;
            int y = mRandom.nextInt(n - 1) + 1;

            if (maze[y][x] == EMPTY) {
                maze[y][x] = TREASURE;
                return;
            }
        }
    }


    /**
     * 当前点是否可用
     *
     * @param map
     * @param x
     * @param y
     * @return
     */
    static boolean validPoint(char[][] map, int x, int y) {
        if (x <= 0 || y <= 0)
            return false;

        if (map.length - 1 > y && map[y].length - 1 > x) {
            return true;
        }


        return false;
    }

    /**
     * dfs 搜索可以成为路的方格
     *
     * @param map 迷宫
     * @param sx  开始位置 x
     * @param sy  开始位置 y
     */
    static void buildMaze(char[][] map, int sx, int sy) {


        Stack<Point> stack = new Stack<>();

        stack.push(new Point(sx, sy));


        while (stack.size() != 0) {
            Point p = stack.pop();

            int x = p.x;
            int y = p.y;

            if (!canGo(map, x, y)) {
                continue;
            }

            map[y][x] = EMPTY;

            int[] order = new int[]{RIGHT, LEFT, UP, DOWN};

            shuffle(order, mRandom);

            for (int d : order) {
                switch (d) {
                    case RIGHT:
                        stack.push(new Point(x + 1, y));
                        break;
                    case LEFT:
                        stack.push(new Point(x - 1, y));
                        break;
                    case UP:
                        stack.push(new Point(x, y - 1));
                        break;
                    case DOWN:
                        stack.push(new Point(x, y + 1));
                        break;
                }
            }
        }


    }

    /**
     * 随即重排列数组
     *
     * @param array 重拍数组
     * @param rnd   随机变量
     */
    static void shuffle(int[] array, Random rnd) {
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
     * 山下左右 四个方向只能有一个 EMPTY
     *
     * @param map
     * @param x   探索点 x
     * @param y   探索点 y
     * @return x，y 点能否探索
     */
    static boolean canGo(char[][] map, int x, int y) {
        if (!validPoint(map, x, y))
            return false;

        int count = 0;

        if (validPoint(map, x - 1, y) && map[y][x - 1] == EMPTY) {
            count++;
        }
        if (validPoint(map, x + 1, y) && map[y][x + 1] == EMPTY) {
            count++;
        }
        if (validPoint(map, x, y - 1) && map[y - 1][x] == EMPTY) {
            count++;
        }
        if (validPoint(map, x, y + 1) && map[y + 1][x] == EMPTY) {
            count++;
        }


        return count <= 1;
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
    static char look(char[][] map, int x, int y, int direction, int distance) {

        char p = NULL;

        switch (direction) {
            case UP:
                y -= distance;
                break;
            case DOWN:
                y += distance;
                break;
            case LEFT:
                x -= distance;
                break;
            case RIGHT:
                x += distance;
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
    static void printMap(char[][] maze) {
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
        //增加墙的位置
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
        buildMaze(maze, 1, 1);
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


        printMap(maze);

        find(maze, 1, 1);

        maze[1][1] = START;

        printMap(maze);

    }

    /**
     * dfs 寻找宝藏
     *
     * @param maze 迷宫
     * @param x    开始位置 x
     * @param y    开始位置 y
     * @return 是否找到宝藏
     */
    static boolean find(char[][] maze, int x, int y) {
        if (!validPoint(maze, x, y)) {
            return false;
        }

        char current = maze[y][x];

        if (current == TREASURE) {
            return true;
        } else if (current == EMPTY || current == START) {

            maze[y][x] = PATH;
            if (find(maze, x - 1, y)) {
                return true;
            } else if (find(maze, x + 1, y)) {
                return true;
            } else if (find(maze, x, y - 1)) {
                return true;
            } else if (find(maze, x, y + 1)) {
                return true;
            } else {
                maze[y][x] = EMPTY;
                return false;
            }
        }
        return false;


    }
}
