package entities;

import java.util.LinkedList;
import utilities.Server;

public class Airstrip extends Server<Plane> {

    float durability;
    float lastClearance;

    public Airstrip(int number, boolean hasQueue) {
        this.number = number;
        this.durability = 3000;
        this.idleTime = 0;
        this.idleTimeMax = 0;
        this.idleTimeMin = 0;
        this.lastClearance = 0;
        if(hasQueue){
        this.queue = new LinkedList();
        }
    }

    public float getLastClearance() {
        return lastClearance;
    }

    public void setLastClearance(float lastClearance) {
        this.lastClearance = lastClearance;
    }

    public float getDurability() {
        return durability;
    }

    public void setDurability(float durability) {
        this.durability = durability;
    }

}
