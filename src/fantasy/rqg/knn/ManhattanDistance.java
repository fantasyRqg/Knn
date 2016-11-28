package fantasy.rqg.knn;

/**
 * Created by rqg on 28/11/2016.
 */
public class ManhattanDistance extends DistanceMatrix {
    public ManhattanDistance(IrisDataSet irisDataSet) {
        super(irisDataSet);
    }

    @Override
    protected float getDistance(IrisSample a, IrisSample b) {
        return Math.abs(a.petalWidth - b.petalWidth) +
                Math.abs(a.petalLength - b.petalLength) +
                Math.abs(a.sepalLength - b.sepalLength) +
                Math.abs(a.sepalWidth - b.sepalWidth);
    }
}
