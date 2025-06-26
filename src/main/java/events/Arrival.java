package events;

import entities.Airstrip;
import entities.ControlTower;
import entities.Plane;
import events.Clearance;
import utilities.Analytics;
import utilities.Generator;
import utilities.Event;
import utilities.FutureEventsList;

public class Arrival extends Event<Plane, Airstrip> {

    private final ControlTower tower;

    public Arrival(ControlTower tower, Airstrip server, Plane entity, float clock) {
        super(server, entity, 1, clock);
        this.tower = tower;
    }

    @Override
    public boolean process(FutureEventsList fel, Generator generator) {
        float nextArrivaltime;
        if (this.tower.serversHaveQueue()) {
            if (this.server.isOccuped()) {
                this.server.getQueue().add(this.entity);
                this.entity.setWasInQueue(true);
            } else {
                this.entity.setLandingClock(this.clock);
                this.server.setOccuped(true);
                this.server.setEntity(this.entity);
                this.server.setDurability(this.server.getDurability() - generator.getDistribution("Airstrip wear").get());
                fel.insert(new Clearance(this.tower, this.server, this.entity, this.clock + generator.getDistribution("Clearance time").get()));
            }
        } else {
            if (this.server.isOccuped()) {
                this.tower.getQueue().add(this.entity);
                this.entity.setWasInQueue(true);
            } else {
                this.entity.setLandingClock(this.clock);
                this.server.setOccuped(true);
                this.server.setEntity(this.entity);
                this.server.setDurability(this.server.getDurability() - generator.getDistribution("Airstrip wear").get());
                fel.insert(new Clearance(this.tower, this.server, this.entity, this.clock + generator.getDistribution("Clearance time").get()));
            }
        }
        if ((540 <= this.clock % 1440 && this.clock % 1440 <= 780) || (1200 <= this.clock % 1440 && this.clock % 1440 <= 1380)) {
            nextArrivaltime = this.clock + generator.getDistribution("Strong traffic").get();
        } else {
            nextArrivaltime = this.clock + generator.getDistribution("Normal traffic").get();
        }
        fel.insert(new Arrival(this.tower, this.tower.selectServer(), new Plane(this.entity.getId() + 1, nextArrivaltime, false), nextArrivaltime));
        return true;
    }

    @Override
    public void analytics(Analytics analytics) {
        float idletime;
        analytics.increaseArrival(1);
        if (!this.tower.serversHaveQueue() && this.tower.getQueue().isEmpty() || (this.tower.serversHaveQueue() && this.server.getQueue().isEmpty())) {
            idletime = this.clock - this.server.getLastClearance();
            this.server.increaseIdleTime(idletime);
            this.server.setIdleTimeMax(idletime);
            this.server.setIdleTimeMin(idletime);
        }
        if (this.tower.serversHaveQueue()) {
            analytics.setWaitQueueMax(this.server.getQueue().size());
            analytics.setWaitQueueMin(this.server.getQueue().size());
        } else {
            analytics.setWaitQueueMax(this.tower.getQueue().size());
            analytics.setWaitQueueMin(this.tower.getQueue().size());

        }
    }

}
