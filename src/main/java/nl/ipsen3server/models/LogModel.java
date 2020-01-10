package nl.ipsen3server.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Time;
import java.sql.Timestamp;


/**
 * @author Anthony Scheeres
 */
public class LogModel {

    private int logId;
    private String timestamp;
    private String title;
    private String description;
    private int byUserId;
    private int experimentId;

    public LogModel(int logId,  String title, String description, int byUserId, int experimentId) {
        this.logId = logId;
        this.timestamp = getCurrentTime();
        this.title = title;
        this.description = description;
        this.byUserId = byUserId;
        this.experimentId = experimentId;
    }

    public LogModel(){
        this.timestamp = getCurrentTime();
        this.title = "TestTitle";
        this.description = "Empty description";
        this.experimentId = 1;
    }

    private String getCurrentTime(){
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date());
        return timestamp;
    }

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp() {
        this.timestamp = getCurrentTime();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getByUserId() {
        return byUserId;
    }

    public void setByUserId(int byUserId) {
        this.byUserId = byUserId;
    }

    public int getExperimentId() {
        return experimentId;
    }

    public void setExperimentId(int experimentId) {
        this.experimentId = experimentId;
    }
}
