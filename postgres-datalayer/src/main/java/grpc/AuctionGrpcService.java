package grpc;

import database.postgres.services.AuctionService;

public class AuctionGrpcService extends GatewayServiceGrpc.GatewayServiceImplBase{
    private final AuctionService auctionService;

    public AuctionGrpcService(AuctionService auctionService) {
        this.auctionService = auctionService;
    }
}
