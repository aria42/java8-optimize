package optimize;

public class BacktrackingLineSearcher implements ILineMinimizer {

    private final double alpha, beta, minStepLen;

    public BacktrackingLineSearcher(double alpha, double beta, double minStepLen) {
        this.alpha = alpha;
        this.beta = beta;
        this.minStepLen = minStepLen;
    }

    @Override
    public Result minimize(IGradientFn gradFn, double[] x, double[] dir) {
        final ValGradPair valGradPair = gradFn.apply(x);
        final double f0 = valGradPair.fx;
        final double[] grad = valGradPair.grad;
        if (Vector.dotProduct(grad, grad) < minStepLen) {
            return new Result(0.0, f0);
        }
        final double delta = beta * Vector.dotProduct(grad, dir);
        double stepLen = 1.0;
        while (stepLen >= minStepLen) {
            final double[] stepX = Vector.add(x, 1.0, dir, stepLen);
            final double fx = gradFn.apply(stepX).fx;
            if (fx <= f0 + stepLen*delta) {
                return new Result(stepLen, fx);
            }
            stepLen *= alpha;
        }
        throw new RuntimeException("Step-size underflow");
    }
}
