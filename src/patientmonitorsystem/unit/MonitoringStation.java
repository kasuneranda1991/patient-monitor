/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patientmonitorsystem.unit;

import java.util.ArrayList;
import java.util.Formatter;

/**
 * This class use to perform monitors functions
 * 
 * @author Madahapola Kankanamalage Kasun Eranda - 12216898
 */
public class MonitoringStation {

    private int clock; // time of reading taken
    private int lengthOfStream; // length of the reading taken by the monitor
    private int delayBetweenReadings; // delay between readings
    private int consolidationNumber; // consolidation number
    private ArrayList<MonitoredPatient> patients = new ArrayList<MonitoredPatient>(); // monitored patients

    /**
     * Parameterized Class constructor
     * 
     * @param los               - length of the monitor data stream
     * @param delay             - delay between readings
     * @param consolidationNunm - consolidation number
     */
    public MonitoringStation(int los, int delay, int consolidationNunm) {
        this.lengthOfStream = los;
        this.delayBetweenReadings = delay;
        this.consolidationNumber = consolidationNunm;
    }

    /**
     * Store Monitoring Station monitored patient on ArrayList type MonitoredPatient
     * 
     * @param p - object of MonitoredPatient
     */
    public void addPatient(MonitoredPatient p) {
        p.configure();
        p.start();
        patients.add(p);
    }

    /**
     * Monitoring patients
     */
    public void run() {
        readPatientMonitorData(); // reading patient data
    }

    /**
     * Read monitors data related to patient
     */
    private void readPatientMonitorData() {
        clock = 0; // set initial clock value
        int index = 0; // processing index of data stream
        int numInWindow = 0; // track readings in “window of time”

        try (Formatter f = new Formatter("consolidated.txt");) {
            while (index < lengthOfStream) {
                // All monitored patients are to read their monitors at the
                // same clock time
                for (MonitoredPatient patient : patients) {
                    
                    patient.readPatientMonitors(clock); // reading the data
                    // If we have read a multiple of the “consolidation
                    // number”/”window size”, then consolidate the last group of readings
                    if ((numInWindow + 1) == consolidationNumber) {
                        patient.consolidate(index - consolidationNumber + 1, index, delayBetweenReadings, f);
                        numInWindow -= consolidationNumber;
                    }
                }
                numInWindow++;
                index++; // increment processing index of data stream
                clock += delayBetweenReadings; // update clock
            }

            // if there are partial windows to consolidate then consolidate here
            if (numInWindow != 0) {
                for (MonitoredPatient patient : patients) {
                    patient.consolidate(index - numInWindow, index - 1, delayBetweenReadings, f);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

}
