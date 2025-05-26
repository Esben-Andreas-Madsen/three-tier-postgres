package DTOs;

import java.util.Date;

public class ArtifactHistory {
    private int id;
    private int artifact_id;
    private Date event_date;
    private String event_descriptions;
    private String involved_parties;

    public ArtifactHistory(int id, int artifact_id, Date event_date, String event_descriptions, String involved_parties) {
        this.id = id;
        this.artifact_id = artifact_id;
        this.event_date = event_date;
        this.event_descriptions = event_descriptions;
        this.involved_parties = involved_parties;
    }

    public ArtifactHistory(int artifact_id, Date event_date, String event_descriptions, String involved_parties) {
        this.artifact_id = artifact_id;
        this.event_date = event_date;
        this.event_descriptions = event_descriptions;
        this.involved_parties = involved_parties;
    }

    public ArtifactHistory() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArtifactId() {
        return artifact_id;
    }

    public void setArtifactId(int artifact_id) {
        this.artifact_id = artifact_id;
    }

    public Date getEventDate() {
        return event_date;
    }

    public void setEventDate(Date event_date) {
        this.event_date = event_date;
    }

    public String getEventDescriptions() {
        return event_descriptions;
    }

    public void setEventDescriptions(String event_descriptions) {
        this.event_descriptions = event_descriptions;
    }

    public String getInvolvedParties() {
        return involved_parties;
    }

    public void setInvolvedParties(String involved_parties) {
        this.involved_parties = involved_parties;
    }

    @Override
    public String toString() {
        return "ArtifactHistory{" +
                "id=" + id +
                ", artifact_id=" + artifact_id +
                ", event_date=" + event_date +
                ", event_descriptions='" + event_descriptions + '\'' +
                ", involved_parties='" + involved_parties + '\'' +
                '}';
    }
}
