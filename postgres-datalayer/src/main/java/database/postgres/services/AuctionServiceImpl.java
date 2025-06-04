package database.postgres.services;

import DTOs.Auction;
import database.postgres.DAOs.AuctionDAO;

import java.util.List;

public class AuctionServiceImpl implements AuctionService {
    private final AuctionDAO auctionDAO;

    public AuctionServiceImpl(AuctionDAO auctionDAO) {
        this.auctionDAO = auctionDAO;
    }

    @Override
    public Auction createAuction(Auction auction) {
        return auctionDAO.createAuction(auction);
    }

    @Override
    public Auction getAuctionById(int id) {
        return auctionDAO.getAuctionById(id);
    }

    @Override
    public boolean updateAuction(Auction auction) {
        return auctionDAO.updateAuction(auction);
    }

    @Override
    public boolean deleteAuction(int id) {
        return auctionDAO.deleteAuction(id);
    }

    @Override
    public List<Auction> getAllAuctions() {
        return auctionDAO.getAllAuctions();
    }
}
