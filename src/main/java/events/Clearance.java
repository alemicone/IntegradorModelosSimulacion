package events;

import entities.Airstrip;
import entities.ControlTower;
import entities.Plane;
import utilities.Analytics;
import utilities.Generator;
import utilities.Event;
import utilities.FutureEventsList;

public class Clearance extends Event<Plane, Airstrip> {

    private final ControlTower tower;

    public Clearance(ControlTower tower, Airstrip airstrip, Plane entity, float clock) {
        super(airstrip, entity, 2, clock);

        this.tower = tower;
    }

    @Override
    public boolean process(FutureEventsList fel, Generator generator) {
        Plane next;
        if (this.server.getQueue().isEmpty()) {
            this.server.setOccuped(false);
        } else {
            next = this.server.getQueue().remove();
            this.tower.sort();
            this.server.setEntity(next);
            this.server.setOccuped(true);
            next.setLandingClock(this.clock);
            this.server.setDurability(this.server.getDurability() - generator.getDistribution("Airstrip wear").get());
            next.setWaitTime(this.clock - next.getArrivalClock());
            fel.insert(new Clearance(this.tower, this.server, next, this.clock + generator.getDistribution("Clearance time").get()));
        }
        this.tower.sort();
        /*if (this.getEntity().Wasinqueue()) {
            this.getEntity().setWaitTime(this.getEntity().getLandingClock() - this.getEntity().getArrivalClock());
        }*/

        this.entity.setSystemTime(this.clock - this.entity.getArrivalClock());

        return true;
    }

    @Override
    public void analytics(Analytics analytics) {
        this.server.setLastClearance(this.clock);
        analytics.increaseAirlanding(1);
        analytics.increaseWayclearance(1);
        analytics.increaseSystemTime(this.entity.getSystemTime());
        analytics.increaseWaitTime(this.entity.getWaitTime());
        analytics.setSystemTimeMax(this.entity.getSystemTime());
        analytics.setSystemTimeMin(this.entity.getSystemTime());
        analytics.setWaitTimeMax(this.entity.getWaitTime());
        analytics.setWaitTimeMin(this.entity.getWaitTime());
    }
}
