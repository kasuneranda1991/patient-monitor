/*
 * The MIT License
 *
 * Copyright 2023 kasun.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package patientmonitorsystem.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import patientmonitorsystem.monitors.Monitor;

/**
 * This class responsible for consolidating the monitor recordings
 * @author kasun
 */
public class ConsolidatedDataRecord {

    private String type; // monitor type
    private String id; // monitor id
    private Monitor.Status status; // monitor status
    private double t1;// start time for the consolidated record
    private double t2;// end time for the consolidated record
    private int valid;// number of valid readings in the time period (window)
    private int invalid;// number of invalid readings in the time period
    private double average;// average value for the time period
    private double maximum;// maximum value in the time period
    private double minimum;// minimum value in the time period

    /**
     * Consolidated data parameterized constructor
     * 
     * @param s  - a Monitor - Oxygen, Temperature, BP
     * @param t1 - start time for the consolidation
     * @param t2 - end time for the consolidation
     */
    public ConsolidatedDataRecord(Monitor s, double t1, double t2) {
        this.t1 = t1;
        this.t2 = t2;
        this.status = s.getStatus();
        this.type = s.getType();
        this.id = s.getMonitorID();
    }

    /**
     * Set statistics
     * 
     * @param window List of consolidated data [ MonitorID, MonitorType, time:
     *               Time, value: Reading | ... ]
     */
    public void setStatistics(List<MonitorDataRecord> window) {
        ArrayList<Double> values = new ArrayList<>();
        double totalOfReadings = 0;
        for (MonitorDataRecord monitorDataRecord : window) {
            if (monitorDataRecord != null) {
                if(monitorDataRecord.getValue() != 9999){                    
                    values.add(monitorDataRecord.getValue());
                    totalOfReadings += monitorDataRecord.getValue();
                    valid++;
                }else{
                    invalid++;
                }
            } else {
                invalid++;
            }
        }

        maximum = Collections.max(values, null);
        minimum = Collections.min(values, null);
        average = totalOfReadings / valid;
    }

    /**
     * Override to string method
     * 
     * @return String
     */
    @Override
    public String toString() {
        return String.format("<%s, %s, %s, <%.1f, %.1f>, %s,%s,%.1f,%.1f,%.1f>", id, type, status, t1, t2, valid, invalid,
                average, maximum, minimum);
    }
}
