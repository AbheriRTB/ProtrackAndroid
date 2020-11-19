package abheri.co.protracklite.utils;

public class GoalDetails {
    int goal_id;
    int subject_id;
    int topic_id;


    String goal_name;
    String goal_description;
    String subject_name;
    String topic_name;

    public  GoalDetails(){

    }

    public GoalDetails(int goal_id, int subject_id, int topic_id, String goal_name, String goal_description, String subject_name, String topic_name) {
        this.goal_id = goal_id;
        this.subject_id = subject_id;
        this.topic_id = topic_id;
        this.goal_name = goal_name;
        this.goal_description = goal_description;
        this.subject_name = subject_name;
        this.topic_name = topic_name;
    }

    public int getGoal_id() {
        return goal_id;
    }

    public void setGoal_id(int goal_id) {
        this.goal_id = goal_id;
    }

    public int getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }

    public int getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(int topic_id) {
        this.topic_id = topic_id;
    }

    public String getGoal_name() {
        return goal_name;
    }

    public void setGoal_name(String goal_name) {
        this.goal_name = goal_name;
    }

    public String getGoal_description() {
        return goal_description;
    }

    public void setGoal_description(String goal_description) {
        this.goal_description = goal_description;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public String getTopic_name() {
        return topic_name;
    }

    public void setTopic_name(String topic_name) {
        this.topic_name = topic_name;
    }
}
