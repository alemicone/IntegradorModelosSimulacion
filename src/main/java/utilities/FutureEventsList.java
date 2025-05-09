package utilities;

import java.util.ArrayList;
import java.util.Comparator;

public class FutureEventsList {

    private final ArrayList<Event> list;
    private final Comparator<Event> sorter;

    public FutureEventsList() {
        this.list = new ArrayList();
        this.sorter = (Comparator<Event>) (Event e1, Event e2) -> {
            float dif = e1.getClock() - e2.getClock();

            if (dif != 0) {
                return (dif < 0) ? -1 : 1;
            } else {
                if (e1.getPriority() < e2.getPriority()) {
                    return 1;
                } else if (e1.getPriority() > e2.getPriority()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        };
    }

    public Event inminent() {
        return this.list.removeFirst();
    }

    public void insert(Event e) {
        this.list.add(e);
        this.list.sort(sorter);
    }

    @Override
    public String toString() {
        String print = "FutureEventsList{";
        for (Event e : list) {
            print += e.toString();
        }
        print += "}";
        return print;
    }

}
