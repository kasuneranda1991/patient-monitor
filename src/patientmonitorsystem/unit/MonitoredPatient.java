/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patientmonitorsystem.unit;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;

import patientmonitorsystem.data.ConsolidatedDataRecord;
import patientmonitorsystem.data.MonitorDataRecord;
import patientmonitorsystem.monitors.Monitor;

/**
 * This class creates a objects of monitored patients
 * 
 * @author Madahapola Kankanamalage Kasun Eranda - 12216898
 */
public class MonitoredPatient {

    private String patientId; // monitored patient ID
    private ArrayList<Monitor> monitors = new ArrayList<Monitor>(); // Patient monitored Monitors
    private HashMap<String, ArrayList<MonitorDataRecord>> data = new HashMap<>(); /*
                                                                                   * patient monitors related data
                                                                                   * <Monitor ID, Monitor data>
                                                                                   */

    /**
     * Monitored Patients construction with patient id and monitors
     * 
     * @param id       - Patient ID
     * @param monitors - Monitors used for monitor the patient (Varargs)
     */
    public MonitoredPatient(String id, Monitor... monitors) {
        this.patientId = id; // assign patient id to patientId instance variable

        for (Monitor monitor : monitors) { // add patient monitors to the monitors instance ArrayList
            this.monitors.add(monitor);
            data.put(monitor.getMonitorID(), new ArrayList<MonitorDataRecord>());
        }
    }

    /**
     * Start Patient monitoring
     */
    public void start() {
        for (Monitor monitor : monitors) {
            monitor.start(patientId);
        }
    }

    /**
     * Configure monitors
     */
    public void configure() {
        for (Monitor monitor : monitors) {
            monitor.configure(patientId);
        }
    }

    /**
     * Read patient monitors with specific time interval
     * 
     * @param clock Time when the reading taking
     */
    public void readPatientMonitors(int clock) {

        /*
         * monitors array may contains one or more of Oxygen, Temperature, Blood
         * Pressure
         * monitors
         */
        for (Monitor monitor : monitors) {
            MonitorDataRecord dataRecord = monitor.read(patientId);
            if (monitor.getStatus() != Monitor.Status.FAILED) {
                if (dataRecord != null)
                    dataRecord.setTime(clock);// set the time to data record
                System.out.println(" Monitor Unit Display (" + patientId + ") : " + dataRecord.toString());
            }
            data.get(monitor.getMonitorID()).add(dataRecord); // save data record in HashMap under monitor id
        }
    }

    /**
     * Public consolidate method for Monitored Patients
     * 
     * @param lower     - reading start time for consolidation
     * @param upper     - reading end time for the consolidation
     * @param delayTime - delay time between two records
     */
    public void consolidate(int lower, int upper, int delayTime, Formatter f) {

        f.format("\nThe consolidated readings for time %d - %d are: %n", lower * delayTime,
                upper * delayTime);
        System.out.printf("\nThe consolidated readings for time %d - %d are: %n", lower * delayTime,
                upper * delayTime);

        // iterate through the list of the patient's monitors and consolidate their
        for (Monitor monitor : monitors) {
            // don't consolidate if the monitor has failed - just output the status
            if (monitor.getStatus() == Monitor.Status.FAILED) {

                f.format("%s <%s %s %s monitor> %n", patientId,
                        monitor.getMonitorID(), monitor.getType(), monitor.getStatus());
                System.out.printf("%s <%s %s %s monitor> %n", patientId,
                        monitor.getMonitorID(), monitor.getType(), monitor.getStatus());
            } else {
                consolidate(monitor, lower, upper, delayTime, f);
            }
        }
    }

    /**
     * This is the private class which is use to create statistics of consolidated
     * readings
     * 
     * @param monitor   - Monitor object BP/ Temperature/ Oxygen
     * @param lower     - lower reading start time
     * @param upper     - upper reading start time
     * @param delayTime - delay between two readings
     * @param f         - formatter object
     */
    private void consolidate(Monitor monitor, int lower, int upper, int delayTime, Formatter f) {
        ArrayList<MonitorDataRecord> records = data.get(monitor.getMonitorID());
        List<MonitorDataRecord> window = new ArrayList<>();
        int n = 0;
        // copy the records in the window of time from the array of records into
        // the window of records to be consolidated
        for (int i = lower; i <= upper; i++) {
            MonitorDataRecord r = records.get(i);
            if (r != null) {
                n++;
                window.add(r);
            }
        }
        if (n != 0) {
            // create the consolidated record - pass in monitor and window start time- end
            // time
            ConsolidatedDataRecord cdr = new ConsolidatedDataRecord(monitor, lower * delayTime, upper * delayTime);
            /*
             * setStatistics works out the statistics for the newly created consolidated
             * record (cdr)
             */
            // from the records in window and sets the statistical values in cdr
            cdr.setStatistics(window);

            f.format("%s: %s%n", patientId, cdr);
            System.out.printf("%s: %s%n", patientId, cdr);

        }
    }
}
