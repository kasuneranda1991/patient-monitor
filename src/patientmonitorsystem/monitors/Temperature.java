/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patientmonitorsystem.monitors;

import patientmonitorsystem.data.MonitorDataRecord;

/**
 * This class use to monitor the patient temperature
 * @author Madahapola Kankanamalage Kasun Eranda - 12216898
 */
public class Temperature extends Monitor {
    private int numErrs; // Monitor error count
    private double[] temps; // reading value
    private int nextIndex; // next index of the monitor data stream

    /**
     * Temperature constructor with parameters
     * 
     * @param monitorId monitor unique id
     * @param stream    monitor data stream
     */
    public Temperature(String monitorId, double[] stream) {
        super(TEMPERATURE, monitorId);
        this.temps = stream;
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
        System.out.printf("temperature monitor (%s) has been configured for patient %s \n", getMonitorID(), patientId);
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
        double reading = temps[nextIndex++];

        if (getStatus() != Status.FAILED) {
            // no of invalid reading occurrence recording & set system status as FAILED
            if (reading < 0 && 1 < ++numErrs) {
                setStatus(Status.FAILED); // set monitor status as FAILED when error count goes more than 1
                System.out.printf("MONITOR ALERT: %s %s monitor has failed \n", getMonitorID(),
                        TEMPERATURE);
            } else if (reading < 0 && 1 == numErrs) {
                reading = Monitor.INVALID;
            }

            if (getStatus() != Status.FAILED && reading != Monitor.INVALID) {
                if (reading > 37.5) { // check for fever
                    System.out.printf("PATIENT ALERT: %s %s  temperature %.2f - fever\n", patientId, getMonitorID(),
                            reading);
                }

                if (reading < 36) { // check for hypothermia
                    System.out.printf("PATIENT ALERT: %s %s  temperature %.2f - hypothermia\n", patientId,
                            getMonitorID(),
                            reading);
                }
            }
        }

        // create new Monitor data record
        return getStatus() == Status.FAILED
                ? null
                : new MonitorDataRecord(
                        getMonitorID(),
                        TEMPERATURE, reading);
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
        System.out.printf("\ntemperature monitor (%s) has been started for patient %s\n", getMonitorID(), patientId);
        return true;
    }
}
