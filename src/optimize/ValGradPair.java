package optimize;

public class ValGradPair {
    public final double fx;
    public final double[] grad;

    public ValGradPair(double fx, double[] grad) {
        this.fx = fx;
        this.grad = grad;
    }
}
