package optimize;

import java.util.function.Function;

public class BasicGradientFn implements IGradientFn {
    private final int dimension;
    private final Function<double[], ValGradPair> fn;

    public BasicGradientFn(int dimension, Function<double[], ValGradPair> fn) {
        this.dimension = dimension;
        this.fn = fn;
    }

    @Override
    public ValGradPair apply(double[] x) {
        return this.fn.apply(x);
    }

    @Override
    public int dimension() {
        return this.dimension;
    }
}
