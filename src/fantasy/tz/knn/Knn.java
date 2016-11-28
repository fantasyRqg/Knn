package fantasy.tz.knn;

import java.util.*;

/**
 * Created by tz on 28/11/2016.
 */
public class Knn {

    private IrisDataSet mIrisDataSet;

    /**
     * 训练集
     */
    private int[] mTrainId;
    /**
     * 测试集
     */
    private int[] mTestId;
    /**
     * K
     */
    private int K;

    /**
     * 点与点之间的距离矩阵，使用Index 来取出值
     */
    private float[][] mDistanceMatrix;

    public Knn(IrisDataSet irisDataSet, int[] trainId, int[] testId, int k, float[][] distanceMatrix) {
        mIrisDataSet = irisDataSet;
        mTrainId = trainId;
        mTestId = testId;
        K = k;
        mDistanceMatrix = distanceMatrix;
    }

    /**
     * @return accuracy
     */
    public float doKnn() {
        int correctCount = 0;

        for (int i : mTestId) {
            TestSample testSample = predictTestSample(mTrainId, i, mDistanceMatrix, K);

            if (testSample.isPredictCorrect()) {
                correctCount++;
            }

        }

//        String sb = "K = " +
//                K +
//                " , Accuracy = " +
//                correctCount * 100f / mTrainId.length +
//                "%";
//
//
//        System.out.println(sb);


        return correctCount * 100f / mTestId.length;
    }


    /**
     * 通过index 获取 IrisSample
     *
     * @param index index
     * @return
     */
    private IrisSample getIrisSampleByIndex(int index) {
        return mIrisDataSet.getSample(index);
    }

//
//    public static class Node {
//        int clazz;
//        int idx;
//
//        public Node(int clazz, int idx) {
//            this.clazz = clazz;
//            this.idx = idx;
//        }
//
//        @Override
//        public String toString() {
//            return "clazz = " + clazz + " idx = " + idx;
//        }
//    }

    public static class TestSample {
        public IrisSample mSample;

        public int preClazz;

        public TestSample(IrisSample sample, int preClazz) {
            mSample = sample;
            this.preClazz = preClazz;
        }

        public boolean isPredictCorrect() {
            return preClazz == mSample.clazz;
        }
    }

    private TestSample predictTestSample(int[] trainIds, int testId, float[][] distanceMatrix, int k) {

        IrisSample testSample = getIrisSampleByIndex(testId);

        NavigableMap<Float, Integer> dts = new TreeMap<Float, Integer>(); // 保存与测试样本i距离最近的k个点
        float[] raw = distanceMatrix[testSample.index];

        //test
        float[] tmp = new float[trainIds.length];
        for (int i = 0; i < trainIds.length; i++) {
            tmp[i] = raw[getIrisSampleByIndex(trainIds[i]).index];
        }


        Arrays.sort(tmp);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < k; i++) {
            sb.append(tmp[i]).append(",");
        }

        String smmmall = sb.toString();


        for (int j = 0; j < trainIds.length; j++) {

            IrisSample trainSample = getIrisSampleByIndex(trainIds[j]);
            int colIndex = trainSample.index;

            if (dts.size() < k) // 把前面k个插入dts中
            {
                // 插入时会自动排序，按dts中的double排序，最小的排在最后
                dts.put(raw[colIndex], trainSample.clazz);
            } else {
                Map.Entry<Float, Integer> node = dts.pollLastEntry();
                if (raw[colIndex] < node.getKey()) // 把当前测试样本i到当前训练样本之间的欧氏距离与dts中最小距离比较，若更小就更新dts
                {
                    dts.put(raw[colIndex], trainSample.clazz);
                } else {
                    dts.put(node.getKey(), node.getValue());
                }
            }
        }

        int preClazz = -1;
        Map<Integer, Float> tds = new HashMap<>();
        double weight = 0.0;
        // 下面for循环主要是求出与测试样本i最邻近的k个样本点中大多数属于的类别，即将其作为测试样本点i的类别
        for (Map.Entry<Float, Integer> it : dts.entrySet()) {
            // 不考虑权重的情况，在 k 个样例中只要出现就加 1
            // 这里是考虑距离与权重的关系，距离越大权重越小
            Float val = tds.get(it.getValue());
            if (val == null)
                val = 0.0f;
            val += 1.0f / it.getKey();
            tds.put(it.getValue(), val);
            if (val > weight) {
                weight = val;
                preClazz = it.getValue(); // 保存一下类别
            }
        }


        return new TestSample(testSample, preClazz);
    }
}
