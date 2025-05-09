package entities;

public class Plane {

    private float waitTime;
    private float systemTime;
    private float landingClock;
    private float arrivalClock;
    private boolean wasInQueue;
    int id;

    public Plane(int id, float arrivalClock, boolean wasInQueue) {
        this.id = id;
        this.landingClock = arrivalClock;
        this.arrivalClock = arrivalClock;
        this.wasInQueue = wasInQueue;
    }

    public float getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(float waitTime) {
        this.waitTime = waitTime;
    }

    public float getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(float systemTime) {
        this.systemTime = systemTime;
    }

    public float getLandingClock() {
        return landingClock;
    }

    public void setLandingClock(float landingClock) {
        this.landingClock = landingClock;
    }

    public float getArrivalClock() {
        return arrivalClock;
    }

    public void setArrivalClock(float arrivalClock) {
        this.arrivalClock = arrivalClock;
    }

    public boolean Wasinqueue() {
        return wasInQueue;
    }

    public void setWasInQueue(boolean wasInQueue) {
        this.wasInQueue = wasInQueue;
    }

    public int getId() {
        return this.id;
    }

}
