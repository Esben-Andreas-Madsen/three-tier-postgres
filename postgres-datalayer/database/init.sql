-- Users (Bidders)
CREATE TABLE Bidders (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    wealth NUMERIC(15, 2) NOT NULL,
    reputation INTEGER CHECK (reputation BETWEEN 0 AND 100),
    location VARCHAR(100)
);

CREATE TYPE Rarity AS ENUM ('Common', 'Uncommon', 'Rare', 'Epic', 'Legendary', 'Mythic');

-- Artifacts
CREATE TABLE Artifacts (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    origin_story TEXT CHECK (LENGTH(origin_story) <= 2000),
    power_level INTEGER CHECK (power_level BETWEEN 0 AND 100),
    rarity Rarity NOT NULL,
    last_known_location VARCHAR(100),
    estimated_value NUMERIC(15, 2)
);

-- Auctions
CREATE TABLE Auctions (
    id SERIAL PRIMARY KEY,
    artifact_id INTEGER REFERENCES Artifacts(id) ON DELETE CASCADE,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    starting_bid NUMERIC(15, 2) NOT NULL,
    highest_bid NUMERIC(15, 2),
    status VARCHAR(20) CHECK (status IN ('upcoming', 'active', 'closed'))
);

-- Bids
CREATE TABLE Bids (
    id SERIAL PRIMARY KEY,
    auction_id INTEGER REFERENCES Auctions(id) ON DELETE CASCADE,
    bidder_id INTEGER REFERENCES Bidders(id) ON DELETE CASCADE,
    bid_amount NUMERIC(15, 2) NOT NULL,
    bid_time TIMESTAMP DEFAULT NOW()
);

-- Transactions
CREATE TABLE Transactions (
    id SERIAL PRIMARY KEY,
    auction_id INTEGER REFERENCES Auctions(id) ON DELETE CASCADE,
    buyer_id INTEGER REFERENCES Bidders(id),
    seller_id INTEGER REFERENCES Bidders(id),
    final_price NUMERIC(15, 2),
    transaction_date TIMESTAMP DEFAULT NOW()
);

-- Artifact Ownerships
CREATE TABLE ArtifactOwnerships (
    id SERIAL PRIMARY KEY,
    artifact_id INTEGER REFERENCES Artifacts(id) ON DELETE CASCADE,
    owner_id INTEGER REFERENCES Bidders(id),
    acquisition_date TIMESTAMP DEFAULT NOW(),
    sale_date TIMESTAMP
);

-- Artifact History
CREATE TABLE ArtifactHistory (
    id SERIAL PRIMARY KEY,
    artifact_id INTEGER REFERENCES Artifacts(id) ON DELETE CASCADE,
    event_date TIMESTAMP,
    event_description TEXT CHECK (LENGTH(event_description) <= 2000),
    involved_parties VARCHAR(200)
);

-- Events
CREATE TABLE Events (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    date TIMESTAMP NOT NULL,
    location VARCHAR(100),
    description TEXT CHECK (LENGTH(description) <= 2000)
);

-- Event-Artifact Relationship (many-to-many)
CREATE TABLE EventArtifacts (
    event_id INTEGER REFERENCES Events(id) ON DELETE CASCADE,
    artifact_id INTEGER REFERENCES Artifacts(id) ON DELETE CASCADE,
    PRIMARY KEY (event_id, artifact_id)
);
