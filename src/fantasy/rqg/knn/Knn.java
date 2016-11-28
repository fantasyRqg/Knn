package fantasy.rqg.knn;

/**
 * Created by rqg on 28/11/2016.
 */
public class Knn {

    /**
     * 训练集
     */
    private IrisSample[] mTrain;
    /**
     * 测试集
     */
    private IrisSample[] mTest;
    /**
     * K
     */
    private int K;

    /**
     * 点与点之间的距离矩阵，使用Index 来取出值
     */
    private float[][] mDistanceMatrix;

    public Knn(IrisSample[] train, IrisSample[] test, int k, float[][] distanceMatrix) {
        mTrain = train;
        mTest = test;
        K = k;
        mDistanceMatrix = distanceMatrix;
    }
}
