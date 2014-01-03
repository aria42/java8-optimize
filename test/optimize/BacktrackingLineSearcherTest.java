package optimize;

import static org.junit.Assert.*;
import org.junit.Test;

public class BacktrackingLineSearcherTest {
    @Test
    public void testBacktrackingLineSearcher() throws Exception {
        final ILineMinimizer ls = new BacktrackingLineSearcher(0.5,0.01,1.0e-10);
        ILineMinimizer.Result result = ls.minimize(
                new BasicGradientFn(1,TestUtils.xSquared),
                new double[]{1.0},
                new double[]{-1.0});
        assertEquals("", result.stepLength, 1.0,0.001);
        assertEquals("", result.fxmin, 0.0, 0.001);

        result = ls.minimize(
            new BasicGradientFn(1,TestUtils.xSquared),
            new double[]{0.0},
            new double[]{1.0});
        assertEquals("",result.stepLength,0.0, 0.001);
        assertEquals("",result.fxmin,0.0, 0.001);
    }
}
