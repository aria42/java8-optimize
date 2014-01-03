package optimize;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.function.Function;

public class CachingGradientFnTest {

    class CountingFn<T,R> implements Function<T,R> {
        private int callCount = 0;
        private final Function<T,R> fn;

        CountingFn(Function<T, R> fn) {
            this.fn = fn;
        }

        @Override
        public R apply(T t) {
            callCount++;
            return fn.apply(t);
        }
    }

    @Test
    public void testCachingGradientFn() throws Exception {
        CountingFn<double[],ValGradPair> countXSquared = new CountingFn<>(TestUtils.xSquared);
        CachingGradientFn cacheGradFn =
                new CachingGradientFn(1, new BasicGradientFn(1,countXSquared));

        // Underlying fn should only be called once
        cacheGradFn.apply(new double[]{1.0});
        assertEquals(countXSquared.callCount, 1);
        cacheGradFn.apply(new double[]{1.0});
        assertEquals("Result should be cached",countXSquared.callCount, 1);

        // Replace cache should trigger fresh call
        cacheGradFn.apply(new double[]{2.0});
        cacheGradFn.apply(new double[]{1.0});
        assertEquals("Cache should be cleared",countXSquared.callCount, 3);
        cacheGradFn.apply(new double[]{1.0});
        assertEquals("Result should be cached",countXSquared.callCount, 3);
    }
}
