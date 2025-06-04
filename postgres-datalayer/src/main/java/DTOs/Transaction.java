package DTOs;

import java.util.Date;

public class Transaction {
    private int id;
    private int auction_id;
    private int buyer_id;
    private int seller_id;
    private int final_price;
    private Date transaction_date;

    public Transaction(int auction_id, int buyer_id, int seller_id, int final_price) {
        this.auction_id = auction_id;
        this.buyer_id = buyer_id;
        this.seller_id = seller_id;
        this.final_price = final_price;
    }

    public Transaction(int id, int auction_id, int buyer_id, int seller_id, int final_price, Date transaction_date) {
        this.id = id;
        this.auction_id = auction_id;
        this.buyer_id = buyer_id;
        this.seller_id = seller_id;
        this.final_price = final_price;
        this.transaction_date = transaction_date;
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

    public int getBuyerId() {
        return buyer_id;
    }

    public void setBuyerId(int buyer_id) {
        this.buyer_id = buyer_id;
    }

    public int getSellerId() {
        return seller_id;
    }

    public void setSellerId(int seller_id) {
        this.seller_id = seller_id;
    }

    public int getFinalPrice() {
        return final_price;
    }

    public void setFinalPrice(int final_price) {
        this.final_price = final_price;
    }

    public Date getTransactionDate() {
        return transaction_date;
    }

    public void setTransactionDate(Date transaction_date) {
        this.transaction_date = transaction_date;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", auction_id=" + auction_id +
                ", buyer_id=" + buyer_id +
                ", seller_id=" + seller_id +
                ", final_price=" + final_price +
                ", transaction_date=" + transaction_date +
                '}';
    }
}
