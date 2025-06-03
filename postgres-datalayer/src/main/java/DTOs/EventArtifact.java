package DTOs;

public class EventArtifact {
    private int event_id;
    private int artifact_id;

    public EventArtifact(int event_id, int artifact_id) {
        this.event_id = event_id;
        this.artifact_id = artifact_id;
    }

    public int getEventId() {
        return event_id;
    }

    public void setEventId(int event_id) {
        this.event_id = event_id;
    }

    public int getArtifactId() {
        return artifact_id;
    }

    public void setArtifactId(int artifact_id) {
        this.artifact_id = artifact_id;
    }

    @Override
    public String toString() {
        return "EventArtifact{" +
                "event_id=" + event_id +
                ", artifact_id=" + artifact_id +
                '}';
    }
}
