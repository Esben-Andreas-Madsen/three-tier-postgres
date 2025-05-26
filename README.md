# Three Tier Postgres

A project with a domain revolving 'Artifact Auction' where various fictional items are sold off to bidders.



## Architecture

### Tier 1: TBD

### Tier 2: C# .net API

API responsible for handling requests and pass data between the third layer using gRPC client to serialize and de-serialize messages.

### Tier 3: Java Maven Datalayer

Recieves requests from the gRPC client on tier 2 which are processed and interact with a postgres database using DAO pattern.
The gRPC server utilizes connection pooling for efficiency, scalability and control.

### Entity Relationships

```mermaid

%% Entity Relationship Diagram for Auction System

erDiagram
    Bidders {
        int id PK
        string name
        decimal wealth
        int reputation
        string location
    }

    Artifacts {
        int id PK
        string name
        string origin_story
        int power_level
        enum rarity
        string last_known_location
        decimal estimated_value
    }

    Auctions {
        int id PK
        int artifact_id FK
        datetime start_date
        datetime end_date
        decimal starting_bid
        decimal highest_bid
        string status
    }

    Bids {
        int id PK
        int auction_id FK
        int bidder_id FK
        decimal bid_amount
        datetime bid_time
    }

    Transactions {
        int id PK
        int auction_id FK
        int buyer_id FK
        int seller_id FK
        decimal final_price
        datetime transaction_date
    }

    ArtifactOwnerships {
        int id PK
        int artifact_id FK
        int owner_id FK
        datetime acquisition_date
        datetime sale_date
    }

    ArtifactHistory {
        int id PK
        int artifact_id FK
        datetime event_date
        string event_description
        string involved_parties
    }

    Events {
        int id PK
        string name
        datetime date
        string location
        string description
    }

    EventArtifacts {
        int event_id FK
        int artifact_id FK
    }

    %% Relationships
    Artifacts ||--o{ Auctions : contains
    Artifacts ||--o{ ArtifactOwnerships : owned_by
    Artifacts ||--o{ ArtifactHistory : has
    Artifacts ||--o{ EventArtifacts : appears_in

    Bidders ||--o{ Bids : places
    Bidders ||--o{ Transactions : buys_sells
    Bidders ||--o{ ArtifactOwnerships : owns

    Auctions ||--o{ Bids : receives
    Auctions ||--|| Transactions : results_in

    Events ||--o{ EventArtifacts : showcases
```
