package optimize;

import java.util.function.Function;

// A gradient function is a function x: -> [f(x), grad f at x] along
// with a fixed integer dimension for the input array x
public interface IGradientFn extends Function<double[], ValGradPair> {
    public int dimension();
}
