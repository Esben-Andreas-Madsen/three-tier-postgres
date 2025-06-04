package DTOs;

public class Bidder {
    private int id;
    private String name;
    private int wealth;
    private int reputation;
    private String location;

    public Bidder(String name, int wealth, int reputation, String location) {
        this.name = name;
        this.wealth = wealth;
        this.reputation = reputation;
        this.location = location;
    }

    public Bidder(int id, String name, int wealth, int reputation, String location) {
        this.id = id;
        this.name = name;
        this.wealth = wealth;
        this.reputation = reputation;
        this.location = location;
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

    public int getWealth() {
        return wealth;
    }

    public void setWealth(int wealth) {
        this.wealth = wealth;
    }

    public int getReputation() {
        return reputation;
    }

    public void setReputation(int reputation) {
        this.reputation = reputation;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Bidder{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", wealth=" + wealth +
                ", reputation=" + reputation +
                ", location='" + location + '\'' +
                '}';
    }
}
