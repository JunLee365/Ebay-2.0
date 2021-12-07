package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jdbc.Jdbc;
import model.Auction;
import model.Bid;
import model.Customer;
import model.Employee;
import model.Item;
import model.Login;

public class ItemDao {

	// M ADDED 3]
	public List<Item> getItems() {
		
		/*
		 * The students code to fetch data from the database will be written here
		 * Query to fetch details of all the items has to be implemented
		 * Each record is required to be encapsulated as a "Item" class object and added to the "items" List
		 */

		System.out.println("*************** getItems() ***************");
		System.out.println("*******Get all Items**********");
		List<Item> items = new ArrayList<Item>();
				
		/*Sample data begins*/
		// ADDED 3]
		try {
			ResultSet rs = Jdbc.newStatement("SELECT * FROM Item;");

			while(rs.next()) {
				Item item=new Item();
				item.setItemID(rs.getInt("ItemID"));
				item.setName(rs.getString("Name"));
				item.setType(rs.getString("Type"));
				item.setYearManufactured(rs.getInt("Manufactured"));
				item.setNumCopies(rs.getInt("NumCopies"));
				item.setDescription(rs.getString("Description"));
				items.add(item);
			}
		} catch(Exception e) {
			System.out.println(e);
		}
		/*Sample data ends*/

		return items;
	}

	// M ADDED 18]
	public List<Item> getBestsellerItems() {
		
		/*
		 * The students code to fetch data from the database will be written here
		 * Query to fetch details of the bestseller items has to be implemented
		 * Each record is required to be encapsulated as a "Item" class object and added to the "items" List
		 */

		System.out.println("*************** getBestsellerItems() ***************");
		List<Item> items = new ArrayList<Item>();
		
		
		/*Sample data begins*/
		/*
		for (int i = 0; i < 5; i++) {
			Item item = new Item();
			item.setItemID(123);
			item.setDescription("sample description");
			item.setType("BOOK");
			item.setName("Sample Book");
			item.setNumCopies(2);
			items.add(item);
		}
		*/


		// ADDED 18]
		try {
			ResultSet rs = Jdbc.newStatement(
				"SELECT Item.*, Auction.Copies_Sold\n" +
				"FROM Auction, Item\n" +
				"WHERE \n" +
				"\tAuction.ItemID = Item.ItemID AND \n" +
				"\tAuction.Copies_Sold > 0\n" +
				"GROUP BY Auction.ItemID\n" +
				"ORDER BY Auction.Copies_Sold DESC;");

			while(rs.next()) {
				Item item = new Item();
				item.setItemID(rs.getInt("ItemID"));
				item.setDescription(rs.getString("Description"));
				item.setType(rs.getString("Type"));
				item.setName(rs.getString("Name"));
				item.setNumCopies(rs.getInt("NumCopies"));
				items.add(item);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		/*Sample data ends*/
		
		return items;

	}

	// M
	public List<Item> getSummaryListing(String searchKeyword) {
		
		/*
		 * The students code to fetch data from the database will be written here
		 * Query to fetch details of summary listing of revenue generated by a particular item or item type must be implemented
		 * Each record is required to be encapsulated as a "Item" class object and added to the "items" ArrayList
		 * Store the revenue generated by an item in the soldPrice attribute, using setSoldPrice method of each "item" object
		 */

		System.out.println("*************** getSummaryListing() ***************");
		List<Item> items = new ArrayList<Item>();
		/*Sample data begins*/
		try {
			// ResultSet rs = Jdbc.newStatement("SELECT * FROM Customer;");
			if (searchKeyword == null){
				searchKeyword = "";
			}
			//Need more[applies to the other properties]
			String queryStatement="SELECT Item.Name, Item.Type, Item.Description, SUM(BidWon.BidPrice) AS TotalGenerated FROM BidWon, Auction, Item"
					+ " WHERE BidWon.AuctionID = Auction.AuctionID AND Auction.ItemID = Item.ItemID AND"
					+ " name LIKE '%"+searchKeyword+"%' GROUP BY Item.Name, Item.Type";
//			String queryStatement2="SELECT Customer.Name, Item.Name, Item.Type, Item.Description, SUM(BidWon.BidPrice) AS TotalGenerated FROM BidWon, Auction, Item, Customer"
//					+ " WHERE BidWon.AuctionID = Auction.AuctionID AND Auction.ItemID = Item.ItemID AND"
//					+ " name LIKE '%"+searchKeyword+"%' GROUP BY Item.Name, Item.Type,Customer.Name";
//			String queryStatement2="SELECT Item.Name, Item.Type, Item.Description, SUM(BidWon.BidPrice) AS TotalGenerated FROM BidWon, Auction, Item "
//					+ " WHERE BidWon.AuctionID = Auction.AuctionID AND Auction.ItemID = Item.ItemID AND"
//					+ " name LIKE '%"+searchKeyword+"%'";
//			String test ="SELECT Item.Name, Item.Type, SUM(BidWon.BidPrice) AS TotalGenerated FROM BidWon, Auction, Item "
//					+ " WHERE BidWon.AuctionID = Auction.AuctionID AND Auction.ItemID = Item.ItemID ORDER BY "
//					+ " name LIKE '%"+searchKeyword+"%'";
			ResultSet rs = Jdbc.newStatement(queryStatement);
			while(rs.next()) {
				Item item = new Item();
				item.setSoldPrice(rs.getInt("TotalGenerated"));
				item.setName(rs.getString("Name"));
				item.setDescription(rs.getString("Description"));
				item.setType(rs.getString("Type"));
				items.add(item);
			}
			System.out.println(items.size() + ": " + queryStatement);
		}
		catch(Exception e) {
			System.out.println(e);
		}
		/*Sample data ends*/
		return items;
	}

	// CR
	public List<Item> getItemSuggestions(String customerID) {
		
		/*
		 * The students code to fetch data from the database will be written here
		 * Query to fetch item suggestions for a customer, indicated by customerID, must be implemented
		 * customerID, which is the Customer's ID for whom the item suggestions are fetched, is given as method parameter
		 * Each record is required to be encapsulated as a "Item" class object and added to the "items" ArrayList
		 */
		
		System.out.println("*************** getItemSuggestions() ***************");
		List<Item> items = new ArrayList<Item>();
		
		/*Sample data begins*/
		for (int i = 0; i < 4; i++) {
			Item item = new Item();
			item.setItemID(123);
			item.setDescription("sample description");
			item.setType("BOOK");
			item.setName("Sample Book");
			item.setNumCopies(2);
			items.add(item);
		}
		/*Sample data ends*/
		
		return items;

	}

	// C Items sold by Seller -> View Item
	public List getItemsBySeller(String sellerID) {
		
		/*
		 * The students code to fetch data from the database will be written here
		 * Query to fetch items sold by a given seller, indicated by sellerID, must be implemented
		 * sellerID, which is the Sellers's ID who's items are fetched, is given as method parameter
		 * The bid and auction details of each of the items should also be fetched
		 * The bid details must have the highest current bid for the item
		 * The auction details must have the details about the auction in which the item is sold
		 * Each item record is required to be encapsulated as a "Item" class object and added to the "items" List
		 * Each bid record is required to be encapsulated as a "Bid" class object and added to the "bids" List
		 * Each auction record is required to be encapsulated as a "Auction" class object and added to the "auctions" List
		 * The items, bids and auctions Lists have to be added to the "output" List and returned
		 */

		System.out.println("*************** getItemsBySeller() ***************");
		List output = new ArrayList();
		List<Item> items = new ArrayList<Item>();
		List<Bid> bids = new ArrayList<Bid>();
		List<Auction> auctions = new ArrayList<Auction>();
		
		/*Sample data begins*/
		for (int i = 0; i < 4; i++) {
			Item item = new Item();
			item.setItemID(123);
			item.setDescription("sample description");
			item.setType("BOOK");
			item.setName("Sample Book");
			items.add(item);
			
			Bid bid = new Bid();
			bid.setCustomerID("123-12-1234");
			bid.setBidPrice(120);
			bids.add(bid);
			
			Auction auction = new Auction();
			auction.setMinimumBid(100);
			auction.setBidIncrement(10);
			auction.setAuctionID(123);
			auctions.add(auction);
		}
		/*Sample data ends*/
		
		output.add(items);
		output.add(bids);
		output.add(auctions);
		
		return output;
	}

	// C ADDED
	public List<Item> getItemTypes() {
		
		/*
		 * The students code to fetch data from the database will be written here
		 * Each record is required to be encapsulated as a "Item" class object and added to the "items" ArrayList
		 * A query to fetch the unique item types has to be implemented
		 * Each item type is to be added to the "item" object using setType method
		 */
		
		System.out.println("*************** getItemTypes() ***************");
		List<Item> items = new ArrayList<Item>();
		
		/*Sample data begins*/
		/*for (int i = 0; i < 6; i++) {
			Item item = new Item();
			item.setType("BOOK");
			items.add(item);
		}*/

		// ADDED
		try {
			String queryStatement = "SELECT Item.Type FROM Item GROUP BY Item.Type;";
			System.out.println(queryStatement);
			ResultSet rs = Jdbc.newStatement(queryStatement);

			while(rs.next()) {
				Item item = new Item();
				item.setType(rs.getString("Type"));
				items.add(item);
				System.out.println("Type: " + item.getType());
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
		/*Sample data ends*/
		
		return items;
	}

	// C View Items in Auctions -> Search by Item Name
	public List getItemsByName(String itemName) {
		
		/*
		 * The students code to fetch data from the database will be written here
		 * The itemName, which is the item's name on which the query has to be implemented, is given as method parameter
		 * Query to fetch items containing itemName in their name has to be implemented
		 * Each item's corresponding auction data also has to be fetched
		 * Each item record is required to be encapsulated as a "Item" class object and added to the "items" List
		 * Each auction record is required to be encapsulated as a "Auction" class object and added to the "auctions" List
		 * The items and auctions Lists are to be added to the "output" List and returned
		 */

		System.out.println("*************** getItemsByName() ***************");
		List output = new ArrayList();
		List<Item> items = new ArrayList<Item>();
		List<Auction> auctions = new ArrayList<Auction>();
		
		/*Sample data begins*/
		for (int i = 0; i < 4; i++) {
			Item item = new Item();
			item.setItemID(123);
			item.setDescription("sample description");
			item.setType("BOOK");
			item.setName("Sample Book");
			items.add(item);
			
			Auction auction = new Auction();
			auction.setMinimumBid(100);
			auction.setBidIncrement(10);
			auctions.add(auction);
		}
		/*Sample data ends*/
		
		output.add(items);
		output.add(auctions);
		
		return output;
	}

	// C View Items in Auctions -> Search by Item Type
	public List getItemsByType(String itemType) {
		
		/*
		 * The students code to fetch data from the database will be written here
		 * The itemType, which is the item's type on which the query has to be implemented, is given as method parameter
		 * Query to fetch items containing itemType as their type has to be implemented
		 * Each item's corresponding auction data also has to be fetched
		 * Each item record is required to be encapsulated as a "Item" class object and added to the "items" List
		 * Each auction record is required to be encapsulated as a "Auction" class object and added to the "auctions" List
		 * The items and auctions Lists are to be added to the "output" List and returned
		 */

		System.out.println("*************** getItemsByType() ***************");
		List output = new ArrayList();
		List<Item> items = new ArrayList<Item>();
		List<Auction> auctions = new ArrayList<Auction>();
				
		/*Sample data begins*/
		for (int i = 0; i < 4; i++) {
			Item item = new Item();
			item.setItemID(123);
			item.setDescription("sample description");
			item.setType("BOOK");
			item.setName("Sample Book");
			items.add(item);
			
			Auction auction = new Auction();
			auction.setMinimumBid(100);
			auction.setBidIncrement(10);
			auctions.add(auction);
		}
		/*Sample data ends*/
		
		output.add(items);
		output.add(auctions);
		
		return output;
	}

	// C
	public List<Item> getBestsellersForCustomer(String customerID) {

		/*
		 * The students code to fetch data from the database will be written here.
		 * Each record is required to be encapsulated as a "Item" class object and added to the "items" ArrayList.
		 * Query to get the Best-seller list of items for a particular customer, indicated by the customerID, has to be implemented
		 * The customerID, which is the customer's ID for whom the Best-seller items have to be fetched, is given as method parameter
		 */

		System.out.println("*************** getBestsellersForCustomer() ***************");
		List<Item> items = new ArrayList<Item>();
				
		/*Sample data begins*/
		for (int i = 0; i < 6; i++) {
			Item item = new Item();
			item.setItemID(123);
			item.setDescription("sample description");
			item.setType("BOOK");
			item.setName("Sample Book");
			item.setNumCopies(50);
			items.add(item);
		}
		/*Sample data ends*/
		
		return items;

	}
}
