package optimize;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Optional;

public class CachingGradientFn implements IGradientFn {
    private final int maxHistory;
    private final IGradientFn gradFn;

    private final LinkedList<HistoryEntry> history;

    class HistoryEntry {
        double[] input;
        double output;
        double[]  grad;

        HistoryEntry(double[] input, double output, double[] grad) {
            this.input = Arrays.copyOf(input,input.length);
            this.output = output;
            this.grad = Arrays.copyOf(grad,grad.length);
        }
    }

    public CachingGradientFn(int maxHistory, IGradientFn gradFn) {
        this.maxHistory = maxHistory;
        this.gradFn = gradFn;
        this.history = new LinkedList<>();
    }

    @Override
    public ValGradPair apply(double[] x) {
        Optional<HistoryEntry> foundEntry =
            this.history
                .stream()
                .filter(e -> Arrays.equals(e.input, x))
                .findFirst();
        if (foundEntry.isPresent()) {
            return new ValGradPair(foundEntry.get().output, foundEntry.get().grad);
        } else {
            ValGradPair result = this.gradFn.apply(x);
            HistoryEntry entry = new HistoryEntry(x,result.fx,result.grad);
            this.history.addFirst(entry);
            if (this.history.size() > this.maxHistory) {
                this.history.removeLast();
            }
            return result;
        }
    }

    @Override
    public int dimension() {
        return gradFn.dimension();
    }
}
