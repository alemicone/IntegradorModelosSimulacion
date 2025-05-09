package entities;

import java.util.ArrayList;

import java.util.Comparator;
import utilities.ServerSelectionPolitics;

public class ControlTower extends ServerSelectionPolitics<Airstrip> {

    public ControlTower(int size) {
        int i;
        this.servers = new ArrayList(size);
        for (i = 1; i <= size; i++) {
            this.servers.add(new Airstrip(i));
        }
        this.comparator = new Comparator<Airstrip>() {
            @Override
            public int compare(Airstrip a1, Airstrip a2) {
                int v1 = a1.isOccuped() ? a1.getQueue().size() + 1 : a1.getQueue().size();
                int v2 = a2.isOccuped() ? a2.getQueue().size() + 1 : a2.getQueue().size();
                return (v2 - v1) != 0 ? v1 - v2 : a1.getNumber() - a2.getNumber();

            }
        };
    }

    public void sort() {
        this.servers.sort(comparator);
    }

    @Override
    public Airstrip selectServer() {
        return servers.getFirst();
    }

}
