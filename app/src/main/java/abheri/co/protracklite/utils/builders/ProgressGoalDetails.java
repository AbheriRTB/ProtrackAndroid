package abheri.co.protracklite.utils.builders;

public class ProgressGoalDetails {
    long goalID, mapID;
    int progress;
    String date;

    public ProgressGoalDetails(long goalID, long mapID, int progress, String date) {
        this.goalID = goalID;
        this.mapID = mapID;
        this.progress = progress;
        this.date = date;
    }

    public ProgressGoalDetails() {

    }

    public long getGoalID() {
        return goalID;
    }

    public void setGoalID(long goalID) {
        this.goalID = goalID;
    }

    public long getMapID() {
        return mapID;
    }

    public void setMapID(long mapID) {
        this.mapID = mapID;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
