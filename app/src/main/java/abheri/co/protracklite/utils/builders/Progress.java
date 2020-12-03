package abheri.co.protracklite.utils.builders;

public class Progress {
    String date;
    long progress_id, topicdata_id;
    int progress;

    public Progress(String date, long progress_id, long topicdata_id, int progress) {
        this.date = date;
        this.progress_id = progress_id;
        this.topicdata_id = topicdata_id;
        this.progress = progress;
    }

    public Progress() {

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getProgress_id() {
        return progress_id;
    }

    public void setProgress_id(long progress_id) {
        this.progress_id = progress_id;
    }

    public long getTopicdata_id() {
        return topicdata_id;
    }

    public void setTopicdata_id(long topicdata_id) {
        this.topicdata_id = topicdata_id;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
