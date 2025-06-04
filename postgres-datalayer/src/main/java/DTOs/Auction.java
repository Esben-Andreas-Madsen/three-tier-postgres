package DTOs;

import java.util.Date;

public class Auction {
    private int id;
    private int artifact_id;
    private Date start_date;
    private Date end_date;
    private int start_bid;
    private int highest_bid;
    private String status;

    public Auction(int id, int artifact_id, Date start_date, Date end_date, int start_bid, int highest_bid, String status) {
        this.id = id;
        this.artifact_id = artifact_id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.start_bid = start_bid;
        this.highest_bid = highest_bid;
        this.status = status;
    }

    public Auction(int artifact_id, Date start_date, Date end_date, int start_bid, int highest_bid, String status) {
        this.artifact_id = artifact_id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.start_bid = start_bid;
        this.highest_bid = highest_bid;
        this.status = status;
    }

    public Auction() {
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

    public Date getStartDate() {
        return start_date;
    }

    public void setStartDate(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEndDate() {
        return end_date;
    }

    public void setEndDate(Date end_date) {
        this.end_date = end_date;
    }

    public int getStartBid() {
        return start_bid;
    }

    public void setStartBid(int start_bid) {
        this.start_bid = start_bid;
    }

    public int getHighestBid() {
        return highest_bid;
    }

    public void setHighestBid(int highest_bid) {
        this.highest_bid = highest_bid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Auction{" +
                "id=" + id +
                ", artifact_id=" + artifact_id +
                ", start_date=" + start_date +
                ", end_date=" + end_date +
                ", start_bid=" + start_bid +
                ", highest_bid=" + highest_bid +
                ", status='" + status + '\'' +
                '}';
    }
}
