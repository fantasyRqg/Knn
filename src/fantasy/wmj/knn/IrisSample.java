package fantasy.wmj.knn;

import fantasy.wmj.utils.TextUtils;

/**
 * Created by wuminjie on 28/11/2016.
 * <p>
 * 7. Attribute Information:
 * 1. sepal length in cm
 * 2. sepal width in cm
 * 3. petal length in cm
 * 4. petal width in cm
 * 5. class:
 * -- Iris Setosa
 * -- Iris Versicolour
 * -- Iris Virginica
 */
public class IrisSample {

    /**
     * position in {@link IrisDataSet#getAllData()}
     */
    public int index;

    /**
     * * 1. sepal length in cm
     */
    public float sepalLength;

    /**
     * * 2. sepal width in cm
     */
    public float sepalWidth;
    /**
     * * 3. petal length in cm
     */
    public float petalLength;
    /**
     * * 4. petal width in cm
     */
    public float petalWidth;

    /**
     * * 5. class:
     * -- Iris Setosa 0
     * -- Iris Versicolour 1
     * -- Iris Virginica 2
     */
    public int clazz;

    public void setClazz(int i) {
        clazz = clazz;
    }

    /**
     * * 5. class:
     * -- Iris Setosa 0
     * -- Iris Versicolour 1
     * -- Iris Virginica 2
     */
    public void setClazz(String clazzStr) {
        if (TextUtils.equals(clazzStr, "Iris-setosa")) {
            clazz = 0;
        } else if (TextUtils.equals(clazzStr, "Iris-versicolor")) {
            clazz = 1;
        } else if (TextUtils.equals(clazzStr, "Iris-virginica")) {
            clazz = 2;
        } else {
            throw new RuntimeException("Invalid class type = " + clazzStr);
        }
    }


    public String getClazzName() {
        switch (clazz) {
            case 0:
                return "Iris Setosa";
            case 1:
                return "Iris Versicolour";
            case 2:
                return "Iris Virginica";
            default:
                throw new RuntimeException("Invalid class type");
        }
    }

}
