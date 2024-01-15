/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patientmonitorsystem.data;

/**
 * Stores the monitors readings as data record.
 * 
 * @author Madahapola Kankanamalage Kasun Eranda - 12216898
 */
public class MonitorDataRecord {
    private String type; // Monitor Type - Oxygen, Blood pressure, Temperature
    private String id; // Monitor ID
    private double time; // Monitor data recorded time
    private double value; // Monitor Reading

    /**
     * This is the parameterized MonitorDataRecord constructor which is use to store
     * monitor's reading.
     * 
     * @param id    - Is the monitor id
     * @param type  - Indicates the monitor type like temperature, Blood pressure or
     *              Oxygen
     * @param value - This indicates the single value of the monitor reading taken
     *              by specific time
     */
    public MonitorDataRecord(String id, String type, double value) { // parameterized constructor
        this.type = type;
        this.id = id;
        this.value = value;
    }

    /**
     * set the time when the monitor reading taken
     * @param t - time
     */
    public void setTime(double t) {
        this.time = t;
    }

    /**
     * Get monitor type
     * 
     * @return String monitor type
     */
    public String getType() {
        return type;
    }

    /**
     * Get Monitor ID
     * 
     * @return int Monitor ID
     */
    public String getId() {
        return id;
    }

    /**
     * Get the time when the reading was taken by the monitor
     * 
     * @return double time
     */
    public double getTime() {
        return time;
    }

    /**
     * Get the monitor reading value
     * 
     * @return double value
     */
    public double getValue() {
        return value;
    }

    /**
     * Override toString method of super class
     */
    @Override
    public String toString() {
    // Sample output "<oxy1, oxygen, time: 0.0,value: 95.0>"
        return String.format(
                "<%s, %s, time: %.1f, value: %.1f>",
                getId(),
                getType(),
                getTime(),
                getValue()

        );
    }
}
