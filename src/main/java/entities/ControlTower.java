package entities;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import utilities.ServerSelectionPolitics;

public class ControlTower extends ServerSelectionPolitics<Airstrip> {

    private Queue<Plane> generalQueue;
    boolean serversHaveQueue;
    private int lastIndex;
    private final int serversSize;

    private Comparator lessQueueComparator = new Comparator<Airstrip>() {
        @Override
        public int compare(Airstrip a1, Airstrip a2) {
            int v1 = a1.isOccuped() ? a1.getQueue().size() + 1 : a1.getQueue().size();
            int v2 = a2.isOccuped() ? a2.getQueue().size() + 1 : a2.getQueue().size();
            int result = Integer.compare(v1, v2);
            return result != 0 ? result : a1.getNumber() - a2.getNumber();
        }
    };
    private Comparator firstReleaseComparator = new Comparator<Airstrip>() {
        @Override
        public int compare(Airstrip a1, Airstrip a2) {
            float v1 = a1.isOccuped() ? Float.MAX_VALUE : a1.getLastClearance();
            float v2 = a2.isOccuped() ? Float.MAX_VALUE : a2.getLastClearance();
            int result = Float.compare(v1, v2);
            return result != 0 ? result : a1.getNumber() - a2.getNumber();
        }
    };

    private Comparator moreDurabilityComparator = new Comparator<Airstrip>() {
        @Override
        public int compare(Airstrip a1, Airstrip a2) {
            float v1 = a1.isOccuped() ? -Float.MAX_VALUE : a1.getDurability();
            float v2 = a2.isOccuped() ? -Float.MAX_VALUE : a2.getDurability();
            int result = Float.compare(v2, v1);
            return result != 0 ? result : a1.getNumber() - a2.getNumber();
        }
    };

    public ControlTower(int size, String selectionPolitic, boolean serversHaveQueue) {
        this.servers = new ArrayList(size);
        this.serversHaveQueue = serversHaveQueue;
        this.lastIndex = -1;
        this.serversSize = size;
        if (serversHaveQueue) {
            for (int i = 1; i <= size; i++) {
                this.servers.add(new Airstrip(i, true));

                switch (selectionPolitic) {
                    case "Less queue":
                        this.comparator = lessQueueComparator;
                        break;
                    case "First release":
                        this.comparator = firstReleaseComparator;
                        break;
                    case "More durability":
                        this.comparator = moreDurabilityComparator;
                        break;
                    default:
                        break;
                }
            }
        } else {
            for (int i = 1; i <= size; i++) {
                this.servers.add(new Airstrip(i, false));
            }
            this.generalQueue = new LinkedList();
            switch (selectionPolitic) {
                case "First release":
                    this.comparator = firstReleaseComparator;
                    break;
                case "More durability":
                    this.comparator = moreDurabilityComparator;
                    break;
                default:
                    break;

            }
        }

    }

    public boolean serversHaveQueue() {
        return serversHaveQueue;
    }

    public void sortServers() {
        this.servers.sort(comparator);
    }

    public Queue<Plane> getQueue() {
        return this.generalQueue;
    }

    @Override
    public Airstrip selectServer() {
        try {
            this.servers.sort(comparator);
            return servers.getFirst();
        } catch (Exception e) {
            return servers.get((++lastIndex) % this.serversSize);
        }
    }

}
