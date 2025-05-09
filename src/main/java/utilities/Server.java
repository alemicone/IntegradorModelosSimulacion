package utilities;

import java.util.LinkedList;
import java.util.Queue;

public abstract class Server<T> {

    protected Queue<T> queue = new LinkedList();
    protected T entity;
    protected boolean occuped;
    protected float idleTime;
    protected float idleTimeMax;
    protected float idleTimeMin;
    protected int number;

    public float getIdleTime() {
        return idleTime;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setIdleTime(float idleTime) {
        this.idleTime = idleTime;
    }

    public void increaseIdleTime(float idleTime) {
        this.idleTime += idleTime;
    }

    public float getIdleTimeMax() {
        return idleTimeMax;
    }

    public void setIdleTimeMax(float idleTimeMax) {
        if (this.idleTimeMax < idleTimeMax) {
            this.idleTimeMax = idleTimeMax;
        }
    }

    public float getIdleTimeMin() {
        return idleTimeMin;
    }

    public void setIdleTimeMin(float idleTimeMin) {
        if ((this.idleTimeMin > idleTimeMin || this.idleTimeMin == 0) && idleTimeMin != 0) {
            this.idleTimeMin = idleTimeMin;
        }
    }

    public void setOccuped(boolean b) {
        this.occuped = b;
    }

    public boolean isOccuped() {
        return this.occuped;
    }

    public Queue<T> getQueue() {
        return queue;
    }

    public void addQueue(T entity) {
        this.queue.add(entity);
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

}
