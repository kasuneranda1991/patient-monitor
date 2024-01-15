/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patientmonitorsystem.monitors;

import patientmonitorsystem.data.MonitorDataRecord;

/**
 * This class uses to monitor the patient Blood pressure. Monitor takes two data streams in order to do the final calculation
 * @author Madahapola Kankanamalage Kasun Eranda - 12216898
 */
public class BloodPressure extends Monitor {
    private int numConsErrs; // number of consecutive errors
    private int nextIndex; // next index of the data stream
    private double[] systolic; // systolic reading
    private double[] diastolic; // diastolic reading

    /**
     * Blood pressure constructor
     * 
     * @param monitorId // monitor ID
     * @param syst      //array of systolic readings
     * @param dias      // array of diastolic readings
     */
    public BloodPressure(String monitorId, double[] syst, double[] dias) {
        super(BLOOD_PRESSURE, monitorId);
        this.systolic = syst;
        this.diastolic = dias;
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
        System.out.printf("blood pressure monitor (%s) has been configured for patient %s\n", getMonitorID(), patientId);
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
        // System.out.println(nextIndex);
        double reading_syst = systolic[nextIndex]; // a systolic reading from data stream
        double reading_dias = diastolic[nextIndex]; // a diastolic reading from data stream
        double reading = 1.0 / 3.0 * reading_syst + 2.0 / 3.0 * reading_dias; // calculate MAP value

        // check for invalid consecutive readings
        if (reading_dias < 0 || reading_syst < 0) {
            numConsErrs++;
        } else {
            numConsErrs = 0; // set error count to zero if there are no consecutive invalid readings
        }

        if (numConsErrs > 1) {
            setStatus(Status.FAILED); // set monitor status as FAILED when consecutive errors count goes more than 1
            System.out.printf("MONITOR ALERT: %s %s %s monitor has failed \n", patientId, getMonitorID(),
                    BLOOD_PRESSURE);
        } else if (numConsErrs > 0) {
            reading = Monitor.INVALID;
        }

        if (getStatus() != Status.FAILED && reading != Monitor.INVALID) {

            if (reading < 70) { // check for low BP
                System.out.printf("PATIENT ALERT: %s %s  blood pressure MAP: %.2f - LOW\n", patientId,
                        getMonitorID(),
                        reading);
            }
            if (reading > 100) { // check for high BP
                System.out.printf("PATIENT ALERT: %s %s  blood pressure MAP: %.2f - HIGH\n", patientId,
                        getMonitorID(),
                        reading);
            }

        }
        nextIndex++;

        // create new Monitor data record
        return getStatus() == Status.FAILED
                ? null
                : new MonitorDataRecord(
                        getMonitorID(),
                        BLOOD_PRESSURE, reading);
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
        System.out.printf("\nblood pressure monitor (%s) has been started for patient %s\n", getMonitorID(),
                patientId);
        return true;
    }

}
