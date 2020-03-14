package nl.ipsen3server.models;

public class BoxModel {
    private long experiment_id;
    private String experiment_phase;

    public BoxModel(){ }

    public BoxModel(long experiment_id, String experiment_phase){
        this.experiment_id = experiment_id;
        this.experiment_phase = experiment_phase;
    }

    public long getId(){
        return experiment_id;
    }

    public String getPhase(){
        return experiment_phase;
    }

    public void setId(long experiment_id){
        this.experiment_id = experiment_id;
    }

    public void setPhase(String experiment_phase){
        this.experiment_phase = experiment_phase;
    }
}
