package optimize;

import java.util.function.Function;
import java.util.function.Supplier;

public class NewtonMethod implements IGradientFnMinimizer {

    private final Function<IGradientFn, IQuasiNewton> quasiNewtonFn;
    private final Opts opts;

    public static class Opts {
        public int maxIters;
        public double tolerance;

        public double alpha = 0.5;
        public double beta = 0.01;
        public double stepLenTolerance = 1.0e-10;

        public Opts() {
            this.maxIters = 150;
            this.tolerance = 1.0e-10;
        }

        public ILineMinimizer lineMinimizer() {
            return new BacktrackingLineSearcher(alpha, beta, stepLenTolerance);
        }
    }

    public NewtonMethod(Function<IGradientFn, IQuasiNewton> quasiNewtonFn) {
        this(quasiNewtonFn, new Opts());
    }

    public NewtonMethod(Function<IGradientFn, IQuasiNewton> quasiNewtonFn, Opts opts) {
        this.quasiNewtonFn = quasiNewtonFn;
        this.opts = opts;
    }

    private double[] step(IGradientFn gradFn, double[] x, ILineMinimizer ls, IQuasiNewton qn) {
        double[] grad = gradFn.apply(x).grad;
        double[] dir = qn.implictMultiply(grad);
        Vector.scaleInPlace(dir,-1.0);
        ILineMinimizer.Result lsRes = ls.minimize(gradFn, x, dir);
        double stepLen = lsRes.stepLength;
        return Vector.add(x, 1.0, dir, stepLen);
    }

    @Override
    public Result minimize(IGradientFn gradFn, double[] initGuess) {
        IQuasiNewton qn = this.quasiNewtonFn.apply(gradFn);
        ILineMinimizer lm = this.opts.lineMinimizer();
        double[] x = initGuess;
        for (int i=0; i < opts.maxIters; ++i) {
            // iteration
            ValGradPair curRes = gradFn.apply(x);
            double[] xnew = step(gradFn, x, lm, qn);
            ValGradPair newRes = gradFn.apply(xnew);
            if (newRes.fx > curRes.fx) {
                throw new RuntimeException("");
            }
            double larger = Math.min(Math.abs(curRes.fx), Math.abs(newRes.fx));
            double relDiff = larger > 0.0 ? Math.abs(newRes.fx-curRes.fx)/larger : 0.0;
            System.out.printf("[Iteration %d] Ended with value %.5f and relDiff %.10f\n", i, newRes.fx, relDiff);
            if (relDiff < opts.tolerance) {
                break;
            }
            // update
           qn.update(Vector.add(xnew,1.0,x,-1.0), Vector.add(newRes.grad,1.0,curRes.grad,-1.0));
           x = xnew;
        }
        return new Result(gradFn.apply(x).fx, x);
    }


}
