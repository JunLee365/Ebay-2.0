-- RUN THESE SECOND
-- 3.1 Manager-Level Transactions

-- VIEW #1] View for highest bid for each auction corresponding to the price the item has been sold for a certain auction
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

-- used for getSalesReport()
-- 2] Obtain a sales report for a particular month
/* SELECT * 
FROM BidWon
WHERE BidTime BETWEEN '2021-06-01' -- (?) Particular month; first day
AND '2021-06-30'; -- (?) Last day of same month  */

-- 2 MOD]
SELECT BidWon.BidPrice, Item.Name 
FROM BidWon, Auction, Item WHERE 
BidWon.AuctionID = Auction.AuctionID AND
Auction.ItemID = Item.ItemID AND
BidTime LIKE '%2021-06%'; 

-- 3] Produce a comprehensive listing of all items
SELECT  *
FROM Item;

-- used for getSalesListing()
-- 4] Produce a list of sales by item name or by customer name
-- 4a] By item name
/*SELECT Item.Name, BidWon.BidPrice
FROM BidWon, Auction, Item
WHERE
	BidWon.AuctionID = Auction.AuctionID AND
	Auction.ItemID = Item.ItemID;*/

-- 4b] By customer name
/*SELECT Customer.FirstName, Customer.LastName, BidWon.BidPrice
FROM Customer, BidWon
WHERE
	Customer.CustomerID = BidWon.CustomerID;*/

-- 4 MOD]
SELECT * FROM
((SELECT B.* FROM BidWon as B, Auction as A, Item as I WHERE
	B.AuctionID = A.AuctionID AND
	A.ItemID = I.ItemID AND
    I.Name LIKE "%Rollin%")
UNION
(SELECT B.* FROM BidWon as B, Customer as C WHERE
	C.CustomerID = B.CustomerID AND
    (C.LastName LIKE "%Rollin%" OR
    C.FirstName LIKE "%Rollin%"))) as U
ORDER BY U.AuctionID DESC;

-- used for getSummaryListing()
-- 5] Produce a summary listing of revenue generated by a particular item, item type, or customer
-- 5a] By Item
SELECT Item.Name, SUM(BidWon.BidPrice) AS TotalGenerated
FROM BidWon, Auction, Item
WHERE 
	BidWon.AuctionID = Auction.AuctionID AND
	Auction.ItemID = Item.ItemID
GROUP BY Item.Name;

-- 5b] By Item type
SELECT Item.Type, SUM(BidWon.BidPrice) AS TotalGenerated
FROM BidWon, Auction, Item
WHERE 
	BidWon.AuctionID = Auction.AuctionID AND
	Auction.ItemID = Item.ItemID
GROUP BY Item.Type;

-- 5c] By Customer
SELECT CustomerID, SUM(BidWon.BidPrice) AS TotalGenerated
FROM BidWon
GROUP BY CustomerID;

-- 5 MOD] Attempt to modify this thing
SELECT Item.*, CustomerID, SUM(BidWon.BidPrice) AS TotalGenerated
FROM BidWon, Auction, Item
WHERE 
	BidWon.AuctionID = Auction.AuctionID AND
	Auction.ItemID = Item.ItemID
GROUP BY Item.Type, Item.Name, CustomerID;


-- used for getHighestRevenueEmployee()
-- 6] Determine which customer representative generated most total revenue
/*SELECT Auction.Monitor, SUM(BidWon.BidPrice) AS TotalGenerated
FROM BidWon, Auction, Employee
WHERE 
	BidWon.AuctionID = Auction.AuctionID AND
	Auction.Monitor = Employee.EmployeeID
GROUP BY Auction.Monitor
ORDER BY TotalGenerated DESC
LIMIT 1;*/

-- 6 MOD]
SELECT Employee.*, SUM(BidWon.BidPrice) AS TotalGenerated
FROM BidWon, Auction, Employee
WHERE 
	BidWon.AuctionID = Auction.AuctionID AND
	Auction.Monitor = Employee.EmployeeID
GROUP BY Auction.Monitor
ORDER BY TotalGenerated DESC
LIMIT 1;

-- used for getHighestRevenueCustomer()
-- 7]  Determine which customer generated most total revenue
/*SELECT CustomerID, SUM(BidWon.BidPrice) AS TotalSpent
FROM BidWon
GROUP BY CustomerID
ORDER BY TotalSpent DESC
LIMIT 1;*/

-- 7 MOD] 
SELECT Customer.*, SUM(BidWon.BidPrice) AS TotalSpent
FROM BidWon, Customer
GROUP BY CustomerID
ORDER BY TotalSpent DESC
LIMIT 1;

-- 8] Produce a Best-Sellers list of items
SELECT Item.Name, Auction.Copies_Sold
FROM Auction, Item
WHERE 
	Auction.ItemID = Item.ItemID AND 
	Auction.Copies_Sold > 0
GROUP BY Auction.ItemID
ORDER BY Auction.Copies_Sold DESC;
