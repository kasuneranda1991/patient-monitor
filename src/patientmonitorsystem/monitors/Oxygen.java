/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patientmonitorsystem.monitors;

import patientmonitorsystem.data.MonitorDataRecord;

/**
 * This class use to monitor a patient oxygen level
 * 
 * @author Madahapola Kankanamalage Kasun Eranda - 12216898
 */
public class Oxygen extends Monitor {
    private int numErrrs; // Monitor error count
    private double[] oxygen; // reading value
    private int nextIndex; // next index of the monitor data stream

    /**
     * Oxygen constructor with parameters
     * 
     * @param monitorId monitor unique id
     * @param stream    monitor data stream
     */
    public Oxygen(String monitorId, double[] stream) {
        super(OXYGEN, monitorId);
        this.oxygen = stream;
        setStatus(Status.IDLE); // set the monitor running state as idle
    }

    /**
     * Configure the monitor to take readings
     * 
     * @param patientId monitored patient id
     * @return boolean
     */
    @Override
    public boolean configure(String patientId) {
        setStatus(Status.IDLE); // set the monitor running state as IDLE
        System.out.println("\nConfiguring patient monitoring system");
        System.out.printf("oxygen monitor (%s) has been configured for patient %s\n", getMonitorID(), patientId);
        return true;
    }

    /**
     * Take the reading from the monitor and create new data records
     * 
     * @param patientId monitored patient id
     * @return MonitorDataRecord
     */
    @Override
    public MonitorDataRecord read(String patientId) {
        double reading = oxygen[nextIndex++];

        if (getStatus() != Status.FAILED) {
            // no of invalid reading occurrence recording & set system status as FAILED
            if (reading < 0 && 1 < ++numErrrs) {
                setStatus(Status.FAILED); // set monitor status as FAILED when error count goes more than 1
                System.out.printf("MONITOR ALERT: %s %s %s monitor has failed \n", patientId, getMonitorID(), OXYGEN);
            } else if (reading < 0 && 1 == numErrrs) {
                reading = Monitor.INVALID;
            }

            if (reading < 95 && 0 < reading) { // check reading below 95
                System.out.printf("PATIENT ALERT: %s %s  oxygen %.2f - level low\n", patientId, getMonitorID(),reading);
            }
        }

        // create new Monitor data record
        return getStatus() == Status.FAILED
                ? null
                : new MonitorDataRecord(
                        getMonitorID(),
                        OXYGEN, reading);
    }

    /**
     * Prepare monitor for take the readings
     * 
     * @param patientId monitored patient id
     * @return true
     */
    @Override
    public boolean start(String patientId) {
        setStatus(Status.RUNNING); // set the monitor running state as RUNNING
        System.out.printf("\noxygen monitor (%s) has been started for patient %s\n", getMonitorID(), patientId);
        return true;
    }
}
