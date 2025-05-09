package utilities;

import entities.Airstrip;
import entities.ControlTower;
import entities.Plane;
import events.End;
import events.Arrival;

public class Bootstrap {

    public void run(Generator generator, int serverQuantity, float end) {
        Analytics analytic = new Analytics();
        FutureEventsList fel = new FutureEventsList();
        ControlTower tower = new ControlTower(serverQuantity);
        Event<Plane, Airstrip> e;
        fel.insert(new End(end));
        fel.insert(new Arrival(tower, tower.selectServer(), new Plane(1, 0, false), 0));
        e = fel.inminent();
        while (e.process(fel, generator)) {
            e.analytics(analytic);
            e = fel.inminent();
        }
        analytic.endAnalytics(tower);
        analytic.showResults(end, tower);
    }
}
