package optimize;

@FunctionalInterface
public interface IGradientFnMinimizer {

    public static class Result {
        public final double fxmin;
        public final double[] xmin;

        public Result(double fxmin, double[] xmin) {
            this.fxmin = fxmin;
            this.xmin = xmin;
        }
    }

    default public Result minimize(IGradientFn gradFn) {
        return this.minimize(gradFn, new double[gradFn.dimension()]);
    }

    public Result minimize(IGradientFn gradFn, double[] initGuess);
}
