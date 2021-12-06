package dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import jdbc.Jdbc;
import model.Bid;
import model.Customer;
import model.Item;

public class BidDao {

	// C ADDED 13] View Bid History -> See Auction
	public List<Bid> getBidHistory(String auctionID) {
		
		System.out.println("*************** getBidHistory() ***************");
		List<Bid> bids = new ArrayList<Bid>();

		/*
		 * The students code to fetch data from the database
		 * Each record is required to be encapsulated as a "Bid" class object and added to the "bids" ArrayList
		 * auctionID, which is the Auction's ID, is given as method parameter
		 * Query to get the bid history of an auction, indicated by auctionID, must be implemented
		 */

		/*Sample data begins*/
		// ADDED 13]
		try {
			ResultSet rs = Jdbc.newStatement(
	"SELECT \n" +
				"\tBid.CustomerID,\n" +
				"\tBid.BidTime,\n" +
				"\tBid.BidPrice\n" +
				"FROM Bid,Auction\n" +
				"WHERE\n" +
				"\tBid.AuctionID = Auction.AuctionID AND\n" +
				"\tBid.AuctionID = " + Integer.parseInt(auctionID) + "\n" +
				"ORDER BY BidTime;");

			while(rs.next()) {
				Bid bid = new Bid();
				bid.setAuctionID(rs.getInt("AuctionID"));
				bid.setCustomerID(rs.getString("CustomerId"));
				bid.setBidTime(rs.getString("BidTime"));
				bid.setBidPrice(rs.getFloat("BidPrice"));
				bids.add(bid);
			}
		} catch(Exception e) {
			System.out.println(e);
		}
		/*Sample data ends*/
		
		return bids;
	}

	// C ADDED 14]
	public List<Bid> getAuctionHistory(String customerID) {
		
		System.out.println("*************** getAuctionHistory() ***************");
		List<Bid> bids = new ArrayList<Bid>();

		/*
		 * The students code to fetch data from the database
		 * Each record is required to be encapsulated as a "Bid" class object and added to the "bids" ArrayList
		 * customerID, which is the Customer's ID, is given as method parameter
		 * Query to get the bid history of all the auctions in which a customer participated, indicated by customerID, must be implemented
		 */

		/*Sample data begins*/
		for (int i = 0; i < 10; i++) {
			Bid bid = new Bid();
			bid.setAuctionID(123);
			bid.setCustomerID("123-12-1234");
			bid.setBidTime("2008-12-11");
			bid.setBidPrice(100 + 2 * i);
			System.out.println(
					bid.getCustomerID() + " " +
					bid.getAuctionID() + " " +
					bid.getBidPrice() + " " +
					bid.getBidTime());
			bids.add(bid);			
		}

		// ADDED 14]
		try {
			ResultSet rs = Jdbc.newStatement(
	"SELECT Bid.* FROM Bid, Auction WHERE Bid.AuctionID = Auction.AuctionID AND Bid.CustomerID = \""+ customerID +"\" ORDER BY Bid.BidTime DESC;");

			while(rs.next()) {
				Bid bid = new Bid();
				bid.setAuctionID(rs.getInt("AuctionID"));
				bid.setCustomerID(rs.getString("CustomerId"));
				bid.setBidTime(rs.getString("BidTime"));
				bid.setBidPrice(rs.getFloat("BidPrice"));
				System.out.println(
						bid.getCustomerID() + " " +
						bid.getAuctionID() + " " +
						bid.getBidPrice() + " " +
						bid.getBidTime());
				bids.add(bid);
			}
		} catch(Exception e) {
			System.out.println(e);
		}
		/*Sample data ends*/
		
		return bids;
	}

	// C [long trace]
	public Bid submitBid(String auctionID, String itemID, Float currentBid, Float maxBid, String customerID) {
		
		System.out.println("*************** submitBid() ***************");
		Bid bid = new Bid();

		/*
		 * The students code to insert data in the database
		 * auctionID, which is the Auction's ID for which the bid is submitted, is given as method parameter
		 * itemID, which is the Item's ID for which the bid is submitted, is given as method parameter
		 * currentBid, which is the Customer's current bid, is given as method parameter
		 * maxBid, which is the Customer's maximum bid for the item, is given as method parameter
		 * customerID, which is the Customer's ID, is given as method parameter
		 * Query to submit a bid by a customer, indicated by customerID, must be implemented
		 * After inserting the bid data, return the bid details encapsulated in "bid" object
		 */

		/*Sample data begins*/
		bid.setAuctionID(123);
		bid.setCustomerID("123-12-1234");
		bid.setBidTime("2008-12-11");
		bid.setBidPrice(currentBid);
		/*Sample data ends*/
		
		return bid;
	}

	// M
	public List<Bid> getSalesListing(String searchKeyword) {
		
		System.out.println("*************** getSalesListing() ***************");
		List<Bid> bids = new ArrayList<Bid>();

		/*
		 * The students code to fetch data from the database
		 * Each record is required to be encapsulated as a "Bid" class object and added to the "bids" ArrayList
		 * searchKeyword, which is the search parameter, is given as method parameter
		 * Query to  produce a list of sales by item name or by customer name must be implemented
		 * The item name or the customer name can be searched with the provided searchKeyword
		 */

		/*Sample data begins*/
		for (int i = 0; i < 10; i++) {
			Bid bid = new Bid();
			bid.setAuctionID(123);
			bid.setCustomerID("123-12-1234");
			bid.setBidTime("2008-12-11");
			bid.setBidPrice(100);
			bids.add(bid);			
		}
		/*Sample data ends*/
		
		return bids;
	}

}
