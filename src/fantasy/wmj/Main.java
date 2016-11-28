package fantasy.wmj;

import fantasy.wmj.knn.DistanceMatrix;
import fantasy.wmj.knn.IrisDataSet;
import fantasy.wmj.knn.ManhattanDistance;
import fantasy.wmj.utils.ArrayUtils;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws IOException {
        //加载数据集
        IrisDataSet irisDataSet = IrisDataSet.loadIrisDataFromFile();

        //计算距离矩阵，使用曼哈顿距离
        DistanceMatrix matrix = new ManhattanDistance(irisDataSet);

        int[] dataIds = new int[irisDataSet.getAllData().length];


        //产生数据id数组
        for (int i = 0; i < dataIds.length; i++) {
            dataIds[i] = i;
        }


        //准备通过 交叉验证 检测合适的k， 当前 从2开始最大到30
        for (int k = 2; k < 30; k++) {
            //shuffle array
            ArrayUtils.shuffle(dataIds, new Random(System.currentTimeMillis()));

            CrossValid crossValid = new CrossValid(5, dataIds, k, irisDataSet, matrix.getMatrix());

            List<Float> resutls = crossValid.crossValid();


            float avg = 0f;


            for (int i = 0; i < resutls.size(); i++) {
                avg += resutls.get(i);
            }

            avg = avg / resutls.size();

            StringBuilder sb = new StringBuilder();
            sb.append("K = ")
                    .append(k)
                    .append(" avg accuracy = " + avg)
                    .append(" , accuracy = ");
            for (int i = 0; i < resutls.size(); i++) {
                sb.append(resutls.get(i)).append(",");
            }

            System.out.println(sb.toString());
        }

    }
}

