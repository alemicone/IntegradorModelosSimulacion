package events;

import utilities.Analytics;
import utilities.Event;
import utilities.FutureEventsList;
import utilities.Generator;

public class End extends Event {

    public End(float clock) {
        super(null, null, 0, clock);
    }

    @Override
    public boolean process(FutureEventsList fel, Generator generator) {
        return false;
    }

    @Override
    public void analytics(Analytics analytics) {
    }
}
