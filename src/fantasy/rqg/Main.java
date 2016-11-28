package fantasy.rqg;

import fantasy.rqg.knn.DistanceMatrix;
import fantasy.rqg.knn.IrisDataSet;
import fantasy.rqg.knn.ManhattanDistance;
import fantasy.rqg.utils.ArrayUtils;

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


        for (int i = 0; i < dataIds.length; i++) {
            dataIds[i] = i;
        }


        for (int k = 2; k < 15; k++) {
            ArrayUtils.shuffle(dataIds, new Random(System.currentTimeMillis()));

            CrossValid crossValid = new CrossValid(5, dataIds, k, irisDataSet, matrix.getMatrix());

            List<Float> resutls = crossValid.crossValid();


            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < resutls.size(); i++) {
                sb.append(resutls.get(i)).append(",");
            }

            System.out.println(sb.toString());
        }

    }
}

