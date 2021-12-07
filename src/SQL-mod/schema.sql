-- CREATE DATABASE Ebay2;
-- USE Ebay2;
-- drop schema cse305pa3;
create database cse305pa3;
use cse305pa3;

/*
Person
	Customer
	Employee
Item
Auction
Post
Bid
*/

/*
M Level: 
	-3, output missing for add, edit and delete employee, 
	-2 the list of sales should have item nama and customer name in where clause like searching for sales report by item name, 
	-3 same for the revenue generated by item name, type and customer 
CR Level: 
	-2, record sale output missing, 
	-2, output for add, edit, delete info for customer missing 
C Level: 
	-4, best seller list should be about the items that were sold, using your Bidwon view
*/  

-- Entity  tables [modified]
CREATE TABLE Customer ( 	
	CustomerID CHAR(12) NOT NULL,	-- changed from INT	
    LastName CHAR(20) NOT NULL, 	 
	FirstName CHAR(20) NOT NULL, 	   
    Address CHAR(64),
	City CHAR(20),				 
	State CHAR(2),
	ZipCode INTEGER,
    Telephone CHAR(13), 
	Email CHAR(64),
    
    -- Password_ CHAR(20),	-- added
	-- Role_ CHAR(40),		-- added
    
    Rating DECIMAL,
    CreditCardNum LONG,
	PRIMARY KEY (CustomerID)
);

CREATE TABLE Employee ( 	
	EmployeeID CHAR(12) NOT NULL,	-- changed from INT	
    LastName CHAR(20) NOT NULL, 	 
	FirstName CHAR(20) NOT NULL, 	   
    Address CHAR(64),
	City CHAR(20),				 
	State CHAR(2),
	ZipCode INTEGER,
    Telephone CHAR(13), 
	Email CHAR(64),
    
    -- Password_ CHAR(20),	-- added
	-- Role_ CHAR(40),		-- added
    
    StartDate DATE,		 
	HourlyRate DECIMAL(18, 2),	 
    Level_ CHAR(20),			-- added from INT
    Revenue CHAR(10),	-- added
    PRIMARY KEY (EmployeeID)
);

-- added table: Login
CREATE TABLE Login (		 
	username CHAR(64) NOT NULL UNIQUE, 
	password_ CHAR(20),
	role_ CHAR(40),
    PRIMARY KEY (username, role_)
);

CREATE TABLE Item (		
	ItemID INT NOT NULL,	
    Name CHAR(64), 			-- item_name
	Type CHAR(16),			-- item_type
    Manufactured  INT,		-- year_manufactured
    NumCopies INT, 			-- amount_in_stock
	Description VARCHAR(256),
    PRIMARY KEY (ItemID)
);

CREATE TABLE Auction (	
	AuctionID INT NOT NULL,	
    Monitor CHAR(12) NOT NULL,		-- foreign key -- changed from INT
    ItemID INTEGER NOT NULL,		-- foreign key
    Copies_Sold INT, 		
    MinimumBid DECIMAL(18, 2),	
	ReservePrice DECIMAL(18, 2),
	BidIncrement DECIMAL(18, 2),  

    ClosingBid DECIMAL(18, 2), 
    CurrentBid DECIMAL(18, 2), 
    CurrentHighBid DECIMAL(18, 2), 
	    
	PRIMARY KEY (AuctionID),
	FOREIGN KEY (Monitor) REFERENCES Employee(EmployeeID)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
	FOREIGN KEY (ItemID) REFERENCES Item (ItemID)
		ON DELETE CASCADE
		ON UPDATE CASCADE    
);

-- Relationship tables
CREATE TABLE Post (		
	PostDate DATETIME,			-- openDate
    ExpireDate DATETIME,		-- closeDate
    AuctionID INT NOT NULL,		-- foreign key
	CustomerId CHAR(12) NOT NULL,	-- foreign key, seller_id -- changed from INT
	PRIMARY KEY (AuctionID, CustomerId, PostDate),
	FOREIGN KEY(AuctionID) REFERENCES Auction(AuctionID)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
	FOREIGN KEY(CustomerId) REFERENCES Customer(CustomerId)
		ON DELETE CASCADE
		ON UPDATE CASCADE
);

CREATE TABLE Bid (		
	AuctionID INT NOT NULL,		-- foreign key
	CustomerId CHAR(12) NOT NULL,	-- foreign key, buyer_id -- changed from INT
	BidTime DATETIME,			-- bought_date
    BidPrice DECIMAL(18, 2),	-- bought_price
    -- Where is maxBid???
	PRIMARY KEY (AuctionID, CustomerId, BidTime),
	FOREIGN KEY(AuctionID) REFERENCES Auction(AuctionID)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
	FOREIGN KEY(CustomerId) REFERENCES Customer(CustomerId)
		ON DELETE CASCADE
		ON UPDATE CASCADE
);

-- VIEWS needed
CREATE VIEW BidWon AS
SELECT Bid.CustomerID, Bid.AuctionID, Bid.BidTime, Bid.BidPrice
	FROM (
		SELECT AuctionID, BidTime, MAX(BidPrice) as MaxBid
		FROM Bid
		GROUP BY AuctionID
	) as t, Bid
WHERE
	t.AuctionID = Bid.AuctionID AND
	t.MaxBid = Bid.BidPrice;
    
CREATE VIEW ItemTypeCustomerBought AS
SELECT BidWon.CustomerID, Item.Type
FROM BidWon, Auction, Item
WHERE 
	BidWon.AuctionID = Auction.AuctionID AND
	Auction.ItemID = Item.ItemID;

CREATE VIEW BestSellerItemByItemType AS
SELECT Item.Type, Item.Name, MAX(Auction.Copies_Sold)
FROM Auction, Item
WHERE Auction.ItemID = Item.ItemID
GROUP BY Item.Type;
