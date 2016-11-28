package fantasy.wmj.knn;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wmj on 28/11/2016.
 */
public class IrisDataSet {
    private IrisSample[] mAllData;


    private IrisDataSet(IrisSample[] mAllData) {
        this.mAllData = mAllData;
    }

    /**
     * 从文件中加载所有数据
     *
     * @throws FileNotFoundException 数据文件 "iris.data"未找到
     */
    public static IrisDataSet loadIrisDataFromFile() throws IOException {
        // read data from file
        BufferedReader reader = new BufferedReader(new FileReader("iris.data"));
        List<IrisSample> sampleList = new ArrayList<>();//暂时存储所有的数据


        String line = null;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.length() == 0)
                continue;

            try {
                IrisSample sample = parseIrisSample(line);
                sampleList.add(sample);
            } catch (Exception e) {
                System.out.println("error line = " + line);
                e.printStackTrace();
            }
        }


        //将所有数据转存到数组中，并标记序号。后面使用的时候直接用index 替代实例
        IrisSample[] temp = new IrisSample[sampleList.size()];

        for (int i = 0; i < temp.length; i++) {
            IrisSample sample = sampleList.get(i);
            sample.index = i;
            temp[i] = sample;
        }

        return new IrisDataSet(temp);
    }


    /**
     * parse one line to {@link IrisSample} data
     *
     * @param raw string line raw data
     * @return IrisSample instance
     * @throws Exception 解析错误或者数据空缺都会造成异常抛出。
     */
    private static IrisSample parseIrisSample(String raw) throws Exception {
        IrisSample sample = new IrisSample();

        String[] values = raw.split(",");

        sample.sepalLength = Float.parseFloat(values[0]);
        sample.sepalWidth = Float.parseFloat(values[1]);
        sample.petalLength = Float.parseFloat(values[2]);
        sample.petalWidth = Float.parseFloat(values[3]);

        sample.setClazz(values[4]);

        return sample;

    }


    public IrisSample[] getAllData() {
        return mAllData;
    }

    public IrisSample getSample(int i) {
        return mAllData[i];
    }
}
