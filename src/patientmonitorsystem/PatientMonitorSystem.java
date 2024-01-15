/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patientmonitorsystem;

import patientmonitorsystem.monitors.BloodPressure;
import patientmonitorsystem.monitors.Oxygen;
import patientmonitorsystem.monitors.Temperature;
import patientmonitorsystem.unit.MonitoredPatient;
import patientmonitorsystem.unit.MonitoringStation;

/**
 * Main class of the Patient Monitoring system.
 * This application developed as a part of the <b>COIT20256 Data Structures and
 * Algorithms</b> subject requirement as a assignment.
 * Program is about health monitoring system which is capable of monitoring a
 * patient Temperature, Blood Pressure, Oxygen level.
 * Therefore, program has three monitor classes to monitor those. Once reading
 * completed System can store those readings.
 * 
 * @author Madahapola Kankanamalage Kasun Eranda - 12216898
 */
public class PatientMonitorSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        /**
         * ----------------------------------
         * NOTE : Message from developer
         * 
         * I have added all the basic tests inside this main method
         * 
         * First test is open for execution. For the rest, I have wrapped using block comments.
         * Please kindly uncomment test that you want to run and rest keep inside the block comment.
         * 
         * Thank You
         * 
         * Demonstration of coding block below
         * 
         *         main method(){
         *         
         *                   ============== Phase 1 – One monitored patient with one monitor (oxygen monitor)
         *                   |||||||  Test1 - Open
         *                   ==============
         *                  
         *                   ============== Phase 2A – recognize a faulty oxygen monitor
         *                  ||||||||  Test2 - wrapped by block comment
         *                   ==============
         *                  
         *                  
         *                   ============== Phase 2B – Add a temperature monitor
         *                  ||||||||  Test3 - wrapped by block comment
         *                   ==============
         * 
         *                   ============== Phase 2C | 3A | 3B
         *                  ||||||||  Test3 - wrapped by block comment
         *                   ==============
         * 
         *          }
         * 
         */
        
        /**
         * Static variables
         */
        final String PATIENT_ID_1 = "PID0001"; // patient id - 1
        final String OXY_MONITOR_ID_1 = "oxy1"; // oxygen monitor id - 1
        final String TEMP_MONITOR_ID_1 = "temp1"; // temperature monitor id - 1
        final String BP_MONITOR_ID_1 = "bp1"; // blood pressure monitor id - 1

        
        /**
         * =================================================================
         * Phase 1 – One monitored patient with one monitor (oxygen monitor)
         * =================================================================
         */

        
        // Test data for oxygen monitor
        double[] oxygen = { 95, 95, 97, 97, 97, 97, 98, 95, 90, 90, 90, 95, 99, 98 };
        int numReadings = oxygen.length; // note all arrays for all patients must be the same length

        // Create Monitoring station object
        MonitoringStation station = new MonitoringStation(numReadings, 10, 6);

        // Create Oxygen Monitor ob object
        Oxygen oxygen1 = new Oxygen(OXY_MONITOR_ID_1, oxygen);

        // Create Monitored Patient object
        MonitoredPatient patient1 = new MonitoredPatient(PATIENT_ID_1, oxygen1);

        // add the patient to be monitored to the monitoring station
        station.addPatient(patient1);

        System.out.println();
        station.run(); // start the simulation
        

        /**
         * ============================================
         * Phase 2A – recognize a faulty oxygen monitor
         * ============================================
         */

        /*
        // Test data for oxygen monitor
        double[] oxygen = {95, 95, 97, 97, 97, 97, 98, -1, 90, 90, -1, 0, 6.5, 4};
        int numReadings = oxygen.length; // note all arrays for all patients must be the same length

        // Create Monitoring station object
        MonitoringStation station = new MonitoringStation(numReadings, 10, 6);

        // Create Oxygen Monitor ob object
        Oxygen oxygen1 = new Oxygen(OXY_MONITOR_ID_1, oxygen);

        // Create Monitored Patient object
        MonitoredPatient patient1 = new MonitoredPatient(PATIENT_ID_1, oxygen1);

        // add the patient to be monitored to the monitoring station
        station.addPatient(patient1);

        System.out.println();
        station.run(); // start the simulation
        */

      
      
       /**
        * =====================================
        * Phase 2B – Add a temperature monitor
        * =====================================
        */  
      
        /*
        // Test data for oxygen monitor
        double[] oxygen = {95, 95, 97, 97, 97, 97, 98, -1, 90, 90, -1, 0, 6.5, 4};
        double[] temperatures=  {36.5, 35, 37, 37, 37, 37, 38.5, 38.5, 37.5, 36.5, -1, 36.5, -1, 34 };
         
        int numReadings = oxygen.length; // note all arrays for all patients must be the same length

        // Create Monitoring station object
        MonitoringStation station = new MonitoringStation(numReadings, 10, 6);

        // Create Oxygen Monitor ob object
        Oxygen oxygen1 = new Oxygen(OXY_MONITOR_ID_1, oxygen);

        //Create Temperature monitor for Patient 1
        Temperature temp1 = new Temperature(TEMP_MONITOR_ID_1, temperatures);
        
        // Create Monitored Patient object
        MonitoredPatient patient1 = new MonitoredPatient(PATIENT_ID_1, oxygen1,temperature1);

        // add the patient to be monitored to the monitoring station
        station.addPatient(patient1);

        System.out.println();
        station.run(); // start the simulation
        */
        
        
        /**
         * Below code contains three basic test
         * =======================================
         * Phase 2C – Add a blood pressure Monitor 
         * ===========================================================
         * Phase 3A– Consolidating records – all output to the console
         * =================================================================================
         * Phase 3B– Consolidating records – consolidated records written to the output file
         * =================================================================================
         */
      
        /*
        // Test data for oxygen monitor
        double[] oxygen = {95, 95, 97, 97, 97, 97, 98, -1, 90, 90, -1, 0, 6.5, 4};
        double[] temperatures=  {36.5, 35, 37, 37, 37, 37, 38.5, 38.5, 37.5, 36.5, -1, 36.5, -1, 34 };
        
         // 11 and 12 consecutive invalid readings so monitor fails
         double [] systolic = {100, 120, 200, 160, -1, 98.5, -1, 98.5, 90, 90, 10, -1, 6.5, 4 }; // 12th invalid 
         double [] diastolic  = {50, 70, 90, 90, 80, 98.5, -1, 98.5, -1, 90, -1, 10, 6.5, 4 };      //  11th invalid
         
        int numReadings = oxygen.length; // note all arrays for all patients must be the same length

        // Create Monitoring station object
        MonitoringStation station = new MonitoringStation(numReadings, 10, 6);

        // Create Oxygen Monitor ob object
        Oxygen oxygen1 = new Oxygen(OXY_MONITOR_ID_1, oxygen);

        //Create Temperature monitor for Patient 1
        Temperature temp1 = new Temperature(TEMP_MONITOR_ID_1, temperatures);
        
        //Create Blood Pressure monitor for patient 1
        BloodPressure bp1 = new BloodPressure(BP_MONITOR_ID_1, systolic, diastolic);
        
        // Create Monitored Patient object
        MonitoredPatient patient1 = new MonitoredPatient(PATIENT_ID_1, oxygen1,temp1,bp1);

        // add the patient to be monitored to the monitoring station
        station.addPatient(patient1);

        System.out.println();
        station.run(); // start the simulation
        
        */
    }

}
