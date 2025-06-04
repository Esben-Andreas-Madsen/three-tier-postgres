package database.postgres.services;

import DTOs.Auction;

import java.util.List;

public interface AuctionService {
    Auction createAuction(Auction auction);
    Auction getAuctionById(int id);
    boolean updateAuction(Auction auction);
    boolean deleteAuction(int id);
    List<Auction> getAllAuctions();
}
