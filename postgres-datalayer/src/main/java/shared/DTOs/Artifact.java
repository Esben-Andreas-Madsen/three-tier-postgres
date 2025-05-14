package shared.DTOs;

public class Artifact {
    private int id;
    private String name;
    private String origin_story;
    private int power_level;
    private Rarity rarity;  // Change to Rarity enum
    private String last_known_location;
    private int estimated_value;

    // Constructor with id
    public Artifact(int id, String name, String origin_story, int power_level, Rarity rarity, String last_known_location, int estimated_value) {
        this.id = id;
        this.name = name;
        this.origin_story = origin_story;
        this.power_level = power_level;
        this.rarity = rarity;
        this.last_known_location = last_known_location;
        this.estimated_value = estimated_value;
    }

    // Constructor without id
    public Artifact(String name, String origin_story, int power_level, Rarity rarity, String last_known_location, int estimated_value) {
        this.name = name;
        this.origin_story = origin_story;
        this.power_level = power_level;
        this.rarity = rarity;
        this.last_known_location = last_known_location;
        this.estimated_value = estimated_value;
    }

    // Copy constructor (not fully implemented here)
    public Artifact(Artifact requestToArtifact) {
        this.id = requestToArtifact.id;
        this.name = requestToArtifact.name;
        this.origin_story = requestToArtifact.origin_story;
        this.power_level = requestToArtifact.power_level;
        this.rarity = requestToArtifact.rarity;
        this.last_known_location = requestToArtifact.last_known_location;
        this.estimated_value = requestToArtifact.estimated_value;
    }

    @Override
    public String toString() {
        return "Artifact{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", origin_story='" + origin_story + '\'' +
                ", power_level=" + power_level +
                ", rarity=" + rarity +  // Enum will automatically be converted to its name
                ", last_known_location='" + last_known_location + '\'' +
                ", estimated_value=" + estimated_value +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrigin_story() {
        return origin_story;
    }

    public void setOrigin_story(String origin_story) {
        this.origin_story = origin_story;
    }

    public int getPower_level() {
        return power_level;
    }

    public void setPower_level(int power_level) {
        this.power_level = power_level;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    public String getLast_known_location() {
        return last_known_location;
    }

    public void setLast_known_location(String last_known_location) {
        this.last_known_location = last_known_location;
    }

    public int getEstimated_value() {
        return estimated_value;
    }

    public void setEstimated_value(int estimated_value) {
        this.estimated_value = estimated_value;
    }
}
