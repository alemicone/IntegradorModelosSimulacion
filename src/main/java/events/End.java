package events;

import entities.ControlTower;
import utilities.Analytics;
import utilities.Event;
import utilities.FutureEventsList;
import utilities.Generator;

public class End extends Event {

    private final ControlTower tower;

    public End(float clock, ControlTower tower) {
        super(null, null, 0, clock);
        this.tower = tower;
    }

    @Override
    public boolean process(FutureEventsList fel, Generator generator) {
        return false;
    }

    @Override
    public void analytics(Analytics analytics) {
        analytics.endAnalytics(tower, this.clock);
        //analytics.showResults(clock, tower);
    }
}
