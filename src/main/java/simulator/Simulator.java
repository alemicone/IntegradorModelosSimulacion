package simulator;

import utilities.Bootstrap;
import utilities.Generator;

public class Simulator {

    public static void main(String[] args) {
        float end = 40320f;
        int servers = 5;
        Bootstrap bootstrap = new Bootstrap();
        Generator generator = new Generator();
        generator.addExponential("Strong traffic", (float) 1 / 9);
        generator.addExponential("Normal traffic", (float) 1 / 15);
        generator.addUniform("Clearance time", 10, 25);
        generator.addNormal("Airstrip wear", 36, 5, 1);

        bootstrap.run(generator, servers, end);
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
