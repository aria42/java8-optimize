package optimize;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class NewtonMethodTest {
    @Test
    public void testGradientDescent() throws Exception {
        NewtonMethod minimizer = new NewtonMethod(gradFn -> IQuasiNewton.gradientDescent());

        // Simple Function
        IGradientFnMinimizer.Result res =  minimizer.minimize(new BasicGradientFn(1,TestUtils.xSquared),new double[]{1.0});
        assertArrayEquals(res.xmin, new double[]{0.0},0.0);
        assertEquals(res.fxmin, 0.0, 0.0);

        // Simple Function
        res =  minimizer.minimize(new BasicGradientFn(2,TestUtils.quartic));
        assertArrayEquals(res.xmin, new double[]{1.0,-2.0}, 1.0e-5);
        assertEquals(res.fxmin, 0.0, 1.0e-5);

    }
}
