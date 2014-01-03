package optimize;

@FunctionalInterface
public interface IQuasiNewton {

    public double[] implictMultiply(double[] dir);
    default public void update(double[] xDelta, double[] gradDelta) {

    }


    public static IQuasiNewton gradientDescent() {
        return dir -> dir;
    }
}
