package DTOs;

public class Bid {
    private int id;
    private int auction_id;
    private int bidder_id;
    private int bid_amount;
    private int bid_time;

    public Bid(int id, int auction_id, int bidder_id, int bid_amount, int bid_time) {
        this.id = id;
        this.auction_id = auction_id;
        this.bidder_id = bidder_id;
        this.bid_amount = bid_amount;
        this.bid_time = bid_time;
    }

    public Bid(int auction_id, int bidder_id, int bid_amount, int bid_time) {
        this.auction_id = auction_id;
        this.bidder_id = bidder_id;
        this.bid_amount = bid_amount;
        this.bid_time = bid_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuctionId() {
        return auction_id;
    }

    public void setAuctionId(int auction_id) {
        this.auction_id = auction_id;
    }

    public int getBidderId() {
        return bidder_id;
    }

    public void setBidderId(int bidder_id) {
        this.bidder_id = bidder_id;
    }

    public int getBidAmount() {
        return bid_amount;
    }

    public void setBidAmount(int bid_amount) {
        this.bid_amount = bid_amount;
    }

    public int getBidTime() {
        return bid_time;
    }

    public void setBidTime(int bid_time) {
        this.bid_time = bid_time;
    }

    @Override
    public String toString() {
        return "Bid{" +
                "id=" + id +
                ", auction_id=" + auction_id +
                ", bidder_id=" + bidder_id +
                ", bid_amount=" + bid_amount +
                ", bid_time=" + bid_time +
                '}';
    }
}
