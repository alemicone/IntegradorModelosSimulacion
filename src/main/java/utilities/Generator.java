package utilities;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;
import java.util.SortedMap;
import java.util.function.Supplier;

public class Generator {

    Random random;
    HashMap<String, Supplier<Float>> distributions;

    public Generator() {
        this.random = new Random();
        this.distributions = new HashMap();
    }

    public Generator(long seed) {
        this.random = new Random(seed);
        this.distributions = new HashMap();
    }

    public void addUniform(String name, float a, float b) {
        this.distributions.put(name, () -> {
            return a + (b - a) * random.nextFloat();
        });
    }

    public void addExponential(String name, float lambda) {
        this.distributions.put(name, () -> {
            return (float) ((-1 / lambda) * Math.log(1 - random.nextFloat()));
        });
    }

    public void addNormalJava(String name, float mu, float sigma) {
        this.distributions.put(name, () -> {
            return (float) this.random.nextGaussian() * sigma + mu;
        });
    }

    public void addNormal(String name, int convolutionNumber, float mu, float sigma) {
        this.distributions.put(name, () -> {
            float randomVariable = 0;
            for (int i = 0; i < convolutionNumber; i++) {
                randomVariable += this.random.nextFloat();
            }

            randomVariable = (randomVariable - convolutionNumber * 0.5f) / ((float) Math.sqrt(convolutionNumber * 0.083333333333333f));
            return randomVariable * sigma + mu;
        });
    }

    public void addDiscreteEmpiric(String name, SortedMap<Float, Float> values) {
        this.distributions.put(name, () -> {
            float n = random.nextFloat();
            for (Entry<Float, Float> e : values.entrySet()) {
                if (n <= e.getKey()) {
                    return e.getValue();
                }
            }
            throw new RuntimeException("error");
        });
    }

    public Supplier<Float> getDistribution(String name) {
        return this.distributions.get(name);
    }
}

/*
        Empírica discreta.
Empírica continua. (opcional)
        Uniforme.
Triangular. (opcional)
        Exponencial.
        Normal.
Poisson. (opcional)
 */
