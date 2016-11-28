package fantasy.tz.knn;

/**
 * Created by tz on 28/11/2016.
 */
abstract public class DistanceMatrix {
    private IrisDataSet mIrisDataSet;

    private float[][] mDistanceMatrix;

    public DistanceMatrix(IrisDataSet irisDataSet) {
        mIrisDataSet = irisDataSet;

        caclDistanceMatrix();
    }

    /**
     * 获取距离各个样品的距离矩阵
     *
     * @return 距离矩阵
     */
    public float[][] getMatrix() {
        return mDistanceMatrix;
    }

    protected void caclDistanceMatrix() {
        IrisSample[] allData = mIrisDataSet.getAllData();

        mDistanceMatrix = new float[allData.length][allData.length];

        for (int i = 0; i < allData.length; i++) {
            for (int j = 0; j < allData.length; j++) {
                mDistanceMatrix[i][j] = getDistance(mIrisDataSet.getSample(i), mIrisDataSet.getSample(j));
            }
        }
    }

    abstract protected float getDistance(IrisSample a, IrisSample b);
}
