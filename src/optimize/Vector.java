package optimize;

public class Vector {

    public static double dotProduct(double[] a, double[] b) {
        double res = 0.0;
        for (int i=0; i < a.length; ++i) {
            res += a[i] * b[i];
        }
        return res;
    }


    // return vector (a*x + b*y)
    public static double[] add(double[] x, double a, double[] y, double b) {
        double[] res = new double[x.length];
        for (int i=0; i < res.length; ++i) {
            res[i] = a*x[i] + b*y[i];
        }
        return res;
    }

    public static void scaleInPlace(double[] x, double a) {
        for (int i=0; i < x.length; ++i) {
            x[i] *= a;
        }
    }
}
