package simulator;

import java.util.ArrayList;
import java.util.Collections;
import utilities.Analytics;
import utilities.Bootstrap;
import utilities.Generator;
import utilities.Variable;

public class Simulator {

    public static void results(ArrayList<Analytics> experiments, float end) {
        /*
        ○ Tiempo total, máximo y mínimo (distinto de cero) de ocio. Representar el total como
        una proporción del total del tiempo de simulación.
        ○ Durabilidad de suelo final de cada pista.
         */
        ArrayList<Variable> variables = new ArrayList();
        int nExperiments = experiments.size();
        Variable<Integer> totalArrivals, totalLandings, maxQueue, minQueue;
        Variable<Float> minSystem, maxSystem, averageSystem, minWait, maxWait, averageWait, minIdle, maxIdle, totalIdle;
        totalArrivals = new Variable<>("Total Arrivals", nExperiments, (Analytics experiment) -> {
            return experiment.getArrivals();
        });
        totalLandings = new Variable<>("Total Landings", nExperiments, (Analytics experiment) -> {
            return experiment.getAirLandings();
        });
        maxQueue = new Variable<>("Maximum Queue", nExperiments, (Analytics experiment) -> {
            return experiment.getWaitQueueMax();
        });
        minQueue = new Variable<>("Minimum Queue", nExperiments, (Analytics experiment) -> {
            return experiment.getWaitQueueMin();
        });
        minSystem = new Variable<>("Minimum System Time", nExperiments, (Analytics experiment) -> {
            return experiment.getSystemTimeMin();
        });
        maxSystem = new Variable<>("Maximum System Time", nExperiments, (Analytics experiment) -> {
            return experiment.getSystemTimeMax();
        });
        averageSystem = new Variable<>("Average System Time", nExperiments, (Analytics experiment) -> {
            return experiment.getSystemTime() / experiment.getWayClearances();
        });
        minWait = new Variable<>("Minimum Wait Time", nExperiments, (Analytics experiment) -> {
            return experiment.getWaitTimeMin();
        });
        maxWait = new Variable<>("Maxmimum Wait Time", nExperiments, (Analytics experiment) -> {
            return experiment.getWaitTimeMax();
        });
        averageWait = new Variable<>("Average Wait Time", nExperiments, (Analytics experiment) -> {
            return experiment.getWaitTime() / experiment.getAirLandings();
        }
        );
        minIdle = new Variable<>("Minimum Idle Time", nExperiments, (Analytics experiment) -> {
            return experiment.getIdleTimeMin();
        });
        maxIdle = new Variable<>("Maximum Idle Time", nExperiments, (Analytics experiment) -> {
            return experiment.getIdleTimeMax();
        });
        totalIdle = new Variable<>("Idle Time", nExperiments, (Analytics experiment) -> {
            return experiment.getIdleTime() / end;
        }
        );
        Collections.addAll(variables, totalArrivals, totalLandings, maxQueue, minQueue, minSystem, maxSystem, averageSystem, minWait, maxWait, averageWait, minIdle, maxIdle, totalIdle);

        for (Analytics experiment : experiments) {
            for (Variable variable : variables) {
                variable.addValue(experiment);
            }
        }
        for (Variable variable : variables) {
            variable.CalculateAverage();
            variable.CalculateError();
            System.out.println(variable.getName() + variable.intervalo());
        }

    }

    public static void main(String[] args) {
        float end = 40320f;
        int servers = 5;
        Bootstrap bootstrap = new Bootstrap();
        Generator generator = new Generator();
        ArrayList<Analytics> experiments = new ArrayList<>();
        generator.addExponential("Strong traffic", (float) 1 / 9);
        generator.addExponential("Normal traffic", (float) 1 / 15);
        generator.addUniform("Clearance time", 10, 25);
        generator.addNormal("Airstrip wear", 36, 5, 1);
        for (int i = 0; i < 50; i++) {
            experiments.add(bootstrap.run(generator, servers, end));
        }
        results(experiments, end);
    }
}

/*
Para determinar si un evento sucede en el intervalo de tráfico alto, es decir, de 9 a 13 o de 20 a 23
como hay 1440 minutos en un día, se modulariza el clock con este número, que sería el equivalente a
convertirlo a 24hs, luego hay que mirar si cae en el rango de 9 a 13 o 20 a 23 en minutos,
 */
 /*
        generator.addDiscreteEmpiric("Time between landings", new TreeMap(Map.of(
                0.35f, 10.0f,
                0.80f, 15.0f,
                1.0f, 17.0f
        )));
        generator.addDiscreteEmpiric("Clearanceeeee time", new TreeMap(Map.of(
                0.38f, 8.0f,
                0.70f, 10.0f,
                0.80f, 13.0f,
                1.0f, 15.0f
        )));
 */
