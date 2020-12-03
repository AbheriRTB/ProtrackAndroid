package abheri.co.protracklite.utils.builders;

public class ProgressDetails {

    long id, map_id;
    int progress;
    String date;

    public ProgressDetails(long id, long map_id, int progress, String date) {
        this.id = id;
        this.map_id = map_id;
        this.progress = progress;
        this.date = date;
    }

    public ProgressDetails() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMap_id() {
        return map_id;
    }

    public void setMap_id(long map_id) {
        this.map_id = map_id;
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
