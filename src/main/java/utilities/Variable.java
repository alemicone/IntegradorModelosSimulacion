package utilities;

import java.util.ArrayList;
import java.util.function.Function;

/**
 *
 * @param <T>
 */
public class Variable<T extends Number> {

    Function<Analytics, T> source;
    private final String name;
    private final ArrayList<T> values;
    private float average;
    private final int n;
    private float error;

    public Variable(String name, int n, Function<Analytics, T> source) {
        this.name = name;
        this.source = source;
        this.values = new ArrayList<>();
        this.n = n;

    }

    public void CalculateAverage() {
        float total = 0f;
        for (T value : values) {
            total += value.floatValue();
        }
        this.average = (float)total / n;
    }

    public void CalculateError() {
        float total = 0f;
        for (T value : values) {
            total += Math.pow(value.floatValue() - this.average, 2);
        }
        this.error = (float) Math.sqrt((float)total/(n - 1));

    }

    public String intervalo() {
        return "[" + (average - 1.96 * error / Math.sqrt(n)) + ',' + (average + 1.96 * error / Math.sqrt(n)) + "]";
    }

    public void addValue(Analytics experiment) {
        values.add(source.apply(experiment));
    }

    public String getName() {
        return name;
    }

    public ArrayList<T> getValues() {
        return values;
    }

}
