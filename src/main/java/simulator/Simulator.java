package simulator;

import entities.ControlTower;
import entities.Plane;
import events.Arrival;
import events.End;
import java.util.ArrayList;
import java.util.Collections;
import utilities.Analytics;
import utilities.Bootstrap;
import utilities.Event;
import utilities.Generator;
import utilities.Variable;

public class Simulator {

    static final float endTime = 40320f;
    static final int servers = 3;
    static final int nexperiments = 50;
    static final String selectionPolitic = "Less queue";
    static final boolean serversHaveQueue = true;

    public static void results(ArrayList<Analytics> experiments) {

        ArrayList<Variable> variables = new ArrayList();
        int nExperiments = experiments.size();
        Variable<Integer> totalArrivals, totalLandings, maxQueue, minQueue;
        Variable<Float> minSystem, maxSystem, averageSystem, minWait, maxWait, averageWait, minIdle, maxIdle, totalIdle;
        ArrayList<Variable<Float>> durabilities = new ArrayList(), serversIdleTime = new ArrayList();
        totalArrivals = new Variable<>("Total arrivals", nExperiments, (Analytics experiment) -> {
            return experiment.getArrivals();
        });
        totalLandings = new Variable<>("Total landings", nExperiments, (Analytics experiment) -> {
            return experiment.getAirLandings();
        });
        maxQueue = new Variable<>("Maximum queue", nExperiments, (Analytics experiment) -> {
            return experiment.getWaitQueueMax();
        });
        minQueue = new Variable<>("Minimum queue", nExperiments, (Analytics experiment) -> {
            return experiment.getWaitQueueMin();
        });
        minSystem = new Variable<>("Minimum system time", nExperiments, (Analytics experiment) -> {
            return experiment.getSystemTimeMin();
        });
        maxSystem = new Variable<>("Maximum system time", nExperiments, (Analytics experiment) -> {
            return experiment.getSystemTimeMax();
        });
        averageSystem = new Variable<>("System time %", nExperiments, (Analytics experiment) -> {
            return experiment.getSystemTime() / experiment.getWayClearances();
        });
        minWait = new Variable<>("Minimum wait time", nExperiments, (Analytics experiment) -> {
            return experiment.getWaitTimeMin();
        });
        maxWait = new Variable<>("Maximum wait time", nExperiments, (Analytics experiment) -> {
            return experiment.getWaitTimeMax();
        });
        averageWait = new Variable<>("Wait time", nExperiments, (Analytics experiment) -> {
            return experiment.getWaitTime() / experiment.getAirLandings();
        });
        minIdle = new Variable<>("Minimum idle time", nExperiments, (Analytics experiment) -> {
            return experiment.getIdleTimeMin();
        });
        maxIdle = new Variable<>("Maximum idle time", nExperiments, (Analytics experiment) -> {
            return experiment.getIdleTimeMax();
        });
        totalIdle = new Variable<>("Idle time %", nExperiments, (Analytics experiment) -> {
            return experiment.getIdleTime() / endTime * 100;
        });
        for (int i = 1; i <= servers; i++) {
            final int index = i;
            durabilities.add(new Variable<Float>("Airstrip " + index + " durability", nExperiments, (Analytics experiment) -> {
                return experiment.getDurabilities().get(index);
            }));
            serversIdleTime.add(new Variable<Float>("Airstrip " + index + " idle time", nExperiments, (Analytics experiment) -> {
                return experiment.getServersIdleTime().get(index);
            }));
        }

        Collections.addAll(variables, totalArrivals, totalLandings, maxQueue, minQueue, minSystem, maxSystem, averageSystem, minWait, maxWait, averageWait, minIdle, maxIdle, totalIdle);
        for (Analytics experiment : experiments) {
            for (Variable variable : variables) {
                variable.addValue(experiment);
            }
            for (Variable v : durabilities) {
                v.addValue(experiment);
            }
            for (Variable v : serversIdleTime) {
                v.addValue(experiment);
            }
        }
        for (Variable variable : variables) {
            variable.CalculateAverage();
            variable.CalculateError();
            System.out.println(variable.getName() + variable.intervalo());
            System.out.println("\n");
        }
        for (Variable v : durabilities) {
            v.CalculateAverage();
            v.CalculateError();
            System.out.println(v.getName() + v.intervalo());
            System.out.println("\n");
        }
        for (Variable v : serversIdleTime) {
            v.CalculateAverage();
            v.CalculateError();
            System.out.println(v.getName() + v.intervalo());
            System.out.println("\n");
        }
    }

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        Generator generator = new Generator();
        ControlTower tower;
        Analytics analytic;
        Event start;
        Event end;
        ArrayList<Analytics> experiments = new ArrayList<>();
        generator.addExponential("Strong traffic", (float) 1 / 9);
        generator.addExponential("Normal traffic", (float) 1 / 15);
        generator.addUniform("Clearance time", 10, 25);
        generator.addNormal("Airstrip wear", 36, 5, 1);
        for (int i = 0; i < nexperiments; i++) {
            tower = new ControlTower(servers, selectionPolitic, serversHaveQueue);
            analytic = new Analytics(servers);
            start = new Arrival(tower, tower.selectServer(), new Plane(1, 0, false), 0);
            end = new End(endTime, tower);
            experiments.add(bootstrap.run(generator, start, end, analytic));
        }
        results(experiments);
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
        generator.addDiscreteEmpiric("Clearance time", new TreeMap(Map.of(
                0.38f, 8.0f,
                0.70f, 10.0f,
                0.80f, 13.0f,
                1.0f, 15.0f
        )));
 */
