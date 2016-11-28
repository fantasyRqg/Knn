package fantasy.rqg;

import fantasy.rqg.utils.ArrayUtils;

import java.util.Random;

public class Main {
    public static void main(String[] args) {

        Integer[] testIntArrary = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9};

        for (int i = 0; i < 10; i++) {
            ArrayUtils.shuffle(testIntArrary, new Random(System.currentTimeMillis()));
            printArray(testIntArrary);
        }

    }


    public static void printArray(Integer[] aa) {
        StringBuilder sb = new StringBuilder();

        for (int i : aa) {
            sb.append(i).append(",");
        }

        System.out.println(sb.toString());
    }
}

