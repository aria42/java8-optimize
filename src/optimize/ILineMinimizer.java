package optimize;


@FunctionalInterface
public interface ILineMinimizer {

    public static class Result {
        public final double stepLength;
        public final double fxmin;

        public Result(double stepLength, double fxmin) {
            this.stepLength = stepLength;
            this.fxmin = fxmin;
        }
    }


    public Result minimize(IGradientFn gradFn, double[] x, double[] dir);

}
