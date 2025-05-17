/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

import entities.Airstrip;
import entities.ControlTower;

public class Analytics {

    private int airLandings;
    private int servers;
    private int wayClearances;
    private int arrivals;
    private int waitQueueMax;
    private int waitQueueMin;
    private float systemTime;
    private float systemTimeMax;
    private float systemTimeMin;
    private float waitTime;
    private float waitTimeMax;
    private float waitTimeMin;
    private float idleTime;
    protected float idleTimeMax;
    protected float idleTimeMin;
    protected float lastClearance;

    public Analytics() {
        this.servers = 1;
        this.airLandings = 0;
        this.wayClearances = 0;
        this.arrivals = 0;
        this.waitQueueMax = 0;
        this.waitQueueMin = 0;
        this.systemTime = 0;
        this.systemTimeMax = 0;
        this.systemTimeMin = 0;
        this.waitTime = 0;
        this.waitTimeMax = 0;
        this.waitTimeMin = 0;
        this.idleTime = 0;
        this.idleTimeMax = 0;
        this.idleTimeMin = 0;
    }

    public Analytics(int servers) {
        this.servers = servers;
        this.airLandings = 0;
        this.wayClearances = 0;
        this.arrivals = 0;
        this.waitQueueMax = 0;
        this.waitQueueMin = 0;
        this.systemTime = 0;
        this.systemTimeMax = 0;
        this.systemTimeMin = 0;
        this.waitTime = 0;
        this.waitTimeMax = 0;
        this.waitTimeMin = 0;
        this.idleTime = 0;
        this.idleTimeMax = 0;
        this.idleTimeMin = 0;
    }

    public void showResults(float end, ControlTower tower) {
        for (Airstrip a : tower.getServers()) {
            System.out.println("Torre:" + a.getNumber() + "\n"
                    + "Tiempo total de ocio:" + a.getIdleTime() + "\n"
                    + "Porcentaje total de ocio:" + a.getIdleTime() / end + "\n"
                    + "Tiempo maximo de ocio:" + a.getIdleTimeMax() + "\n"
                    + "Tiempo minimo de ocio:" + a.getIdleTimeMin() + "\n"
                    + "Durabilidad:" + a.getDurability() + "\n"
                    + "------------------------------------------------------------------------");
        }
        System.out.println(
                "Cantidad total de aeronaves que han arribado:" + this.arrivals + "\n"
                + "Cantidad total de aeronaves que han aterrizado:" + this.airLandings + "\n"
                + "Tiempo total de transito:" + this.systemTime + "\n"
                + "Tiempo medio de transito:" + (this.systemTime / this.wayClearances) + "\n"
                + "Tiempo maximo de transito:" + this.systemTimeMax + "\n"
                + "Tiempo minimo de transito:" + this.systemTimeMin + "\n"
                + "Tiempo total de espera:" + this.waitTime + "\n"
                + "Tiempo medio de espera:" + (this.waitTime / this.airLandings) + "\n"
                + "Tiempo maximo de espera:" + this.waitTimeMax + "\n"
                + "Tiempo minimo de espera:" + this.waitTimeMin + "\n"
                + "Tiempo total de ocio:" + this.idleTime + "\n"
                + "Porcentaje total de ocio:" + this.idleTime / end + "\n"
                + "Tiempo maximo de ocio:" + this.idleTimeMax + "\n"
                + "Tiempo minimo de ocio:" + this.idleTimeMin + "\n"
                + "Tamanio maximo de la cola de espera:" + this.waitQueueMax + "\n"
                + "Tamanio minimo de la cola de espera:" + this.waitQueueMin);
    }

    public void endAnalytics(ControlTower tower) {
        for (Airstrip server : tower.getServers()) {
            if (server.isOccuped()) {
                this.increaseAirlanding(1);
                this.increaseWaitTime(server.getEntity().getLandingClock() - server.getEntity().getArrivalClock());
                this.setWaitTimeMax(server.getEntity().getLandingClock() - server.getEntity().getArrivalClock());
                this.setWaitTimeMin(server.getEntity().getLandingClock() - server.getEntity().getArrivalClock());
            }
            this.increaseIdleTime(server.getIdleTime());
            this.setIdleTimeMax(server.getIdleTimeMax());
            this.setIdleTimeMin(server.getIdleTimeMin());
        }
    }

    public int getAirLandings() {
        return airLandings;
    }

    public void setAirLandings(int al) {
        this.airLandings = al;
    }

    public void increaseAirlanding(int al) {
        this.airLandings += al;
    }

    public int getArrivals() {
        return arrivals;
    }

    public void setArrivals(int arrivals) {
        this.arrivals = arrivals;
    }

    public void increaseArrival(int ar) {
        this.arrivals += ar;
    }

    public int getWayClearances() {
        return wayClearances;
    }

    public void setWayClearances(int wc) {
        this.wayClearances = wc;
    }

    public void increaseWayclearance(int wc) {
        this.wayClearances += wc;
    }

    public float getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(float systemTime) {
        this.systemTime = systemTime;
    }

    public void increaseSystemTime(float systemTime) {
        this.systemTime += systemTime;
    }

    public float getSystemTimeMax() {
        return systemTimeMax;
    }

    public void setSystemTimeMax(float systemTimeMax) {
        if (systemTimeMax > this.systemTimeMax) {
            this.systemTimeMax = systemTimeMax;
        }

    }

    public float getSystemTimeMin() {
        return systemTimeMin;
    }

    public void setSystemTimeMin(float systemTimeMin) {
        if ((systemTimeMin < this.systemTimeMin || this.systemTimeMin == 0) && systemTimeMin != 0) {
            this.systemTimeMin = systemTimeMin;
        }
    }

    public float getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(float waitTime) {
        this.waitTime = waitTime;
    }

    public void increaseWaitTime(float waitTime) {
        this.waitTime += waitTime;
    }

    public float getWaitTimeMax() {
        return waitTimeMax;
    }

    public void setWaitTimeMax(float waitTimeMax) {
        if (waitTimeMax > this.waitTimeMax) {
            this.waitTimeMax = waitTimeMax;
        }

    }

    public float getWaitTimeMin() {
        return waitTimeMin;
    }

    public void setWaitTimeMin(float waitTimeMin) {
        if ((waitTimeMin < this.waitTimeMin || this.waitTimeMin == 0) && waitTimeMin != 0) {
            this.waitTimeMin = waitTimeMin;
        }
    }

    public float getIdleTime() {
        return idleTime;
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
        if (idleTimeMax > this.idleTimeMax) {
            this.idleTimeMax = idleTimeMax;
        }
    }

    public float getIdleTimeMin() {
        return idleTimeMin;
    }

    public void setIdleTimeMin(float idleTimeMin) {
        if ((idleTimeMin < this.idleTimeMin || this.idleTimeMin == 0) && idleTimeMin != 0) {
            this.idleTimeMin = idleTimeMin;
        }
    }

    public int getWaitQueueMax() {
        return waitQueueMax;
    }

    public void setWaitQueueMax(int waitQueueMax) {
        if (waitQueueMax > this.waitQueueMax) {
            this.waitQueueMax = waitQueueMax;
        }
    }

    public int getWaitQueueMin() {
        return waitQueueMin;
    }

    public void setWaitQueueMin(int waitQueueMin) {
        if ((waitQueueMin < this.waitQueueMin || this.waitQueueMin == 0) && waitQueueMin != 0) {
            this.waitQueueMin = waitQueueMin;
        }
    }

    public int getServers() {
        return servers;
    }

    public void setServers(int servers) {
        this.servers = servers;
    }

}
