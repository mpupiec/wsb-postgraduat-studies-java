package pl.wsb.java.flightapp.model.projection;

import pl.wsb.java.flightapp.model.Flight;

public class GroupFlightReadModel {
    private String description;
    private boolean done;

    public GroupFlightReadModel(Flight source) {
        description = source.getDescription();
        done = source.isDone();

    }

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

    public boolean isDone() {return done;}

    public void setDone(boolean done) {this.done = done;}

}
