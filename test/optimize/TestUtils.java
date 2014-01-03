package optimize;

import java.util.function.Function;

/**
 * Created by aria42 on 12/26/13.
 */
public class TestUtils {

    // f(x) -> x^2, 2*x
    public final static Function<double[], ValGradPair> xSquared =
            x -> new ValGradPair(x[0]*x[0], new double[]{2*x[0]});

    public final static Function<double[], ValGradPair> quartic =
            x -> {
              double val = Math.pow(x[0] - 1.0, 4.0) + Math.pow(x[1] + 2.0, 4.0);
              double[] grad = new double[]{4 * Math.pow(x[0] - 1.0, 3.0), 4 * Math.pow(x[1]+2.0, 3.0)};
              return new ValGradPair(val, grad);
            };

}
