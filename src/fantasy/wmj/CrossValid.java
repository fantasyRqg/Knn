package fantasy.wmj;

import fantasy.wmj.knn.IrisDataSet;
import fantasy.wmj.knn.Knn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wuminjie on 28/11/2016.
 * <p>
 * 负责交叉验证交叉验证
 */
public class CrossValid {
    /**
     * 分割组数
     */
    private int mFold = 5;


    /**
     * 重拍的Ids
     */
    private int[] mDataIds;

    /**
     * the K of KNN
     */
    private int K;


    private IrisDataSet mIrisDataSet;

    private float[][] mDistanceMatrix;

    /**
     * @param fold           交叉验证次数
     * @param dataIds        数据集ID
     * @param k              K of KNN
     * @param irisDataSet    数据集合
     * @param distanceMatrix 距离矩阵
     */
    public CrossValid(int fold, int[] dataIds, int k, IrisDataSet irisDataSet, float[][] distanceMatrix) {
        mFold = fold;
        mDataIds = dataIds;
        K = k;
        mIrisDataSet = irisDataSet;
        mDistanceMatrix = distanceMatrix;
    }

    /**
     * 交叉验证
     * <p>
     *
     * @return 返回每次验证结果
     */
    public List<Float> crossValid() {
        int groupSize = mDataIds.length / mFold;

        int[][] groups = new int[mFold][];
        int i;
        for (i = 1; i < mFold; i++) {
            groups[i] = Arrays.copyOfRange(mDataIds, (i - 1) * groupSize, i * groupSize);
        }

        groups[0] = Arrays.copyOfRange(mDataIds, (i - 1) * groupSize, mDataIds.length);

        List<Float> accuracies = new ArrayList<>();

        for (int j = 0; j < mFold; j++) {
            float v = crossValid(groups, j);
            accuracies.add(v);
        }


        return accuracies;
    }

    private float crossValid(int[][] groups, int testIndex) {
        int[] testIds = groups[testIndex];
        int[] trainIds = copyExceptTestIds(groups, testIndex);

        Knn knn = new Knn(mIrisDataSet, trainIds, testIds, K, mDistanceMatrix);

        return knn.doKnn();

    }

    private int[] copyExceptTestIds(int[][] groups, int testIndex) {
        int len = mDataIds.length - groups[testIndex].length;

        int[] ids = new int[len];

        int idsCount = 0;

        for (int i = 0; i < groups.length; i++) {
            if (i != testIndex) {
                int[] src = groups[i];
                System.arraycopy(src, 0, ids, idsCount, src.length);
                idsCount += src.length;
            }
        }

        return ids;

    }


}
