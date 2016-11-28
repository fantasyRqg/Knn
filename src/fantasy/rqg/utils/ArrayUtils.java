package fantasy.rqg.utils;

import java.util.Random;
import java.util.RandomAccess;

/**
 * Created by rqg on 28/11/2016.
 */

public class ArrayUtils {
    /**
     * Randomly permute the specified list using the specified source of
     * randomness.  All permutations occur with equal likelihood
     * assuming that the source of randomness is fair.<p>
     * <p>
     * This implementation traverses the list backwards, from the last element
     * up to the second, repeatedly swapping a randomly selected element into
     * the "current position".  Elements are randomly selected from the
     * portion of the list that runs from the first element to the current
     * position, inclusive.<p>
     * <p>
     * This method runs in linear time.  If the specified list does not
     * implement the {@link RandomAccess} interface and is large, this
     * implementation dumps the specified list into an array before shuffling
     * it, and dumps the shuffled array back into the list.  This avoids the
     * quadratic behavior that would result from shuffling a "sequential
     * access" list in place.
     *
     * @param array the list to be shuffled.
     * @param rnd   the source of randomness to use to shuffle the list.
     */
    public static <T> void shuffle(T[] array, Random rnd) {
        // Shuffle array
        for (int i = array.length; i > 1; i--)
            swap(array, i - 1, rnd.nextInt(i));
    }


    /**
     * Swaps the two specified elements in the specified array.
     */
    private static <T> void swap(T[] arr, int i, int j) {
        T tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
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
}
