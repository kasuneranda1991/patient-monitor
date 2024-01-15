/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patientmonitorsystem.monitors;

import patientmonitorsystem.data.MonitorDataRecord;

/**
 * Parent abstract class of Monitors
 *
 * @author Madahapola Kankanamalage Kasun Eranda - 12216898
 */
public abstract class Monitor {

    public static final int INVALID = 9999; // number of invalid readings

    /**
     * Constant values for monitor type. this helps to improve the code
     * re-usability.
     */
    public static final String OXYGEN = "oxygen"; // Type value of Oxygen Monitor
    public static final String TEMPERATURE = "temperature"; // Type value of Temperature Monitor
    public static final String BLOOD_PRESSURE = "MAP"; // Type value of Blood Pressure Monitor

    private String type; // this indicates monitor type (temperature, oxygen, blood pressure)
    private String monitorID; // monitor unique Id
    private Status status; // monitor current state

    /**
     * Monitors running states
     */
    public static enum Status {
        IDLE, // Monitor status when a monitor ready for take the readings
        RUNNING, // Monitor status when a monitor taking readings
        FAILED// Monitor status when a monitor reading failed
    }

    /**
     * Monitor class parameterized constructor
     *
     * @param type - this indicates type of the monitor Ex: Oxygen
     * @param id   - monitor unique ID Ex: oxygen1
     */
    public Monitor(String type, String id) {
        this.type = type;
        this.monitorID = id;
    }

    /**
     * Set monitor current state
     *
     * @param s - Set the Monitor state
     */
    public void setStatus(Status s) {
        this.status = s;
    }

    /**
     * gives monitor type
     *
     * @return - returns monitor type Ex: oxygen
     */
    public String getType() {
        return type;
    }

    /**
     * Gives Monitor Id
     *
     * @return - gives monitor unique id
     */
    public String getMonitorID() {
        return monitorID;
    }

    /**
     * Get Monitor current state
     *
     * @return - gives monitor current state Ex: IDLE, RUNNING
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Monitor data reading method
     *
     * @param patientId - patient ID
     * @return MonitorDataRecord
     */
    public abstract MonitorDataRecord read(String patientId);

    /**
     * Monitor configuration method
     *
     * @param patientId - patient ID
     * @return boolean
     */
    public abstract boolean configure(String patientId);

    /**
     * Monitor starting method
     *
     * @param patientId - patient ID
     * @return boolean
     */
    public abstract boolean start(String patientId);
}
