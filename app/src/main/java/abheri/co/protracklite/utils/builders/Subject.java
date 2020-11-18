package abheri.co.protracklite.builders;

import java.util.List;

import abheri.co.protracklite.data.Topic;

public class Subject {
    private long id;
    private String Name, Description;
    List<Topic> topics;

    public Subject(long id, String name, String description, List<Topic> topics) {
        this.id = id;
        Name = name;
        Description = description;
        this.topics = topics;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    public Subject() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
