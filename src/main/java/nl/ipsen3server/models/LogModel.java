package nl.ipsen3server.models;

import java.util.Date;
import java.sql.Time;
import java.sql.Timestamp;


/**
 * @author Anthony Scheeres
 */
public class LogModel {

    private int logId;
    private Timestamp timestamp;
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

    }

    private Timestamp getCurrentTime(){
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        return timestamp;
    }

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
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
