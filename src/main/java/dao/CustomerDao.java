package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Customer;

import jdbc.Jdbc;

import static jdbc.Jdbc.base;


public class CustomerDao {
	/*
	 * This class handles all the database operations related to the customer table
	 */

	// CR TEST THIS
	/**
	 * @param searchKeyword
	 * @return ArrayList<Customer> object
	 */
	public List<Customer> getCustomers(String searchKeyword) {
		/*
		 * This method fetches one or more customers based on the searchKeyword and returns it as an ArrayList
		 */
		System.out.println("*************** getCustomers() ***************");
		System.out.println("*************** Get All Customers based on keywords ***************");
		List<Customer> customers = new ArrayList<Customer>();

		/*
		 * The students code to fetch data from the database based on searchKeyword will be written here
		 * Each record is required to be encapsulated as a "Customer" class object and added to the "customers" List
		 */

		// ADDED
		/*Sample data begins*/
		try {
			// ResultSet rs = Jdbc.newStatement("SELECT * FROM Customer;");

			if (searchKeyword == null){
				searchKeyword = "";
			}
			//Need more[applies to the other properties]
			String queryStatement="select * from customer where customerID LIKE '%"+searchKeyword+"%'";
			ResultSet rs = Jdbc.newStatement(queryStatement);

			while(rs.next()) {
				Customer customer=new Customer();
				customer.setCustomerID(rs.getString("CustomerId"));
				customer.setEmail(rs.getString("Email"));
				customer.setFirstName(rs.getString("FirstName"));
				customer.setLastName(rs.getString("LastName"));
				customer.setAddress(rs.getString("Address"));
				customer.setCity(rs.getString("City"));
				customer.setState(rs.getString("State"));
				customer.setZipCode(rs.getInt("ZipCode"));
				customer.setTelephone(rs.getString("Telephone"));
				customer.setCreditCard(rs.getString("CreditCardNum"));
				customer.setRating(rs.getInt("Rating"));
				customers.add(customer);
			}
			System.out.println(customers.size() + ": " + queryStatement);
		}
		catch(Exception e) {
			System.out.println(e);
		}
		/*Sample data ends*/
				
		return customers;
	}

	// TESTED
	//Note: This is the business logic for that customer
	public Customer getCustomer(String customerID) {

		/*
		 * This method fetches the customer details and returns it
		 * customerID, which is the Customer's ID who's details have to be fetched, is given as method parameter
		 * The students code to fetch data from the database will be written here
		 * The customer record is required to be encapsulated as a "Customer" class object
		 */

		System.out.println("*************** getCustomer() ***************");
		System.out.println("*******Get Customer by ID**********");
		Customer customer = new Customer();

		/*Sample data begins*/
		//Old
//		Customer customer = new Customer();
//		customer.setCustomerID("111-11-1111");
//		customer.setAddress("123 Success Street");
//		customer.setLastName("Lu");
//		customer.setFirstName("Shiyong");
//		customer.setCity("Stony Brook");
//		customer.setState("NY");
//		customer.setEmail("shiyong@cs.sunysb.edu");
//		customer.setZipCode(11790);
//		customer.setTelephone("5166328959");
//		customer.setCreditCard("1234567812345678");
//		customer.setRating(1);


		//New

		try {
//			Class.forName("com.mysql.cj.jdbc.Driver");
//			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:8081/cse305pa3","root","40302000");
//			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/cse305pa3?"+"user=root&password=40302000");
//			System.out.println("*************Successful Connection **************");
//			Statement st=con.createStatement();
//			ResultSet ss =Jdbc.newStatement("select  from customer");

			String queryStatement="select * from customer where customerID='"+customerID+"'";
			System.out.println(queryStatement);
			ResultSet rs = Jdbc.newStatement(queryStatement);

			while(rs.next()) {
				customer.setCustomerID(rs.getString("CustomerId"));
				customer.setFirstName(rs.getString("FirstName"));
				customer.setLastName(rs.getString("LastName"));
				customer.setAddress(rs.getString("Address"));
				customer.setCity(rs.getString("City"));
				customer.setState(rs.getString("State"));
				customer.setZipCode(rs.getInt("ZipCode"));
				customer.setEmail(rs.getString("Email"));
				customer.setTelephone(rs.getString("Telephone"));
				customer.setCreditCard(rs.getString("CreditCardNum"));
				customer.setRating(rs.getInt("Rating"));
				System.out.println("ID: " + customer.getCustomerID());
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
		/*Sample data ends*/

		return customer;
	}

	// CR TESTED
	public List<Customer> getCustomerMailingList() {

		/*
		 * This method fetches the all customer mailing details and returns it
		 * The students code to fetch data from the database will be written here
		 * Each customer record is required to be encapsulated as a "Customer" class object and added to the "customers" List
		 */

		System.out.println("*************** getCustomerMailingList() ***************");
		System.out.println("*******Get Customer by ID**********");
		List<Customer> customers = new ArrayList<Customer>();

		/*Sample data begins*/
		// ADDED 11] (Not really. Simplified the query)
		System.out.println("*******Get all customers' mailing information**********");
		try {
			ResultSet rs = Jdbc.newStatement("select * from customer;");

			while(rs.next()) {
				Customer customer=new Customer();
				customer.setCustomerID(rs.getString("CustomerId"));
				customer.setFirstName(rs.getString("firstName"));
				customer.setLastName(rs.getString("lastName"));
				customer.setAddress(rs.getString("Address"));
				customer.setCity(rs.getString("City"));
				customer.setState(rs.getString("State"));
				customer.setZipCode(rs.getInt("ZipCode"));
				customer.setEmail(rs.getString("Email"));
				customers.add(customer);
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
		/*Sample data ends*/

		return customers;
	}

	// M TESTED
	public Customer getHighestRevenueCustomer() {
		/*
		 * This method fetches the customer who generated the highest total revenue and returns it
		 * The students code to fetch data from the database will be written here
		 * The customer record is required to be encapsulated as a "Customer" class object
		 */

		System.out.println("*************** getHighestRevenueCustomer() ***************");
		System.out.println("*******Get Customer with highest revenue**********");
		Customer customer = new Customer();

		// ADDED 7]
		/*Sample data begins*/
		try {
			ResultSet rs = Jdbc.newStatement(
	"SELECT Customer.*, SUM(BidWon.BidPrice) AS TotalSpent\n" +
				"FROM BidWon, Customer\n" +
				"GROUP BY CustomerID\n" +
				"ORDER BY TotalSpent DESC\n" +
				"LIMIT 1;");

			while(rs.next()) {
				customer.setCustomerID(rs.getString("CustomerID"));
				customer.setLastName(rs.getString("LastName"));
				customer.setEmail(rs.getString("Email"));
				customer.setFirstName(rs.getString("FirstName"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		/*Sample data ends*/

		return customer;
	}

	// C TESTED
	public List<Customer> getSellers() {

		/*
		 * This method fetches the all seller details and returns it
		 * The students code to fetch data from the database will be written here
		 * The seller (which is a customer) record is required to be encapsulated as a "Customer" class object and added to the "customers" List
		 */

		System.out.println("*************** getSellers() ***************");
		List<Customer> customers = new ArrayList<Customer>();

		/*Sample data begins*/

		// ADDED
		try {
			ResultSet rs = Jdbc.newStatement(
	"SELECT Distinct C.*\n" +
				"FROM Customer as C, Post as P\n" +
				"WHERE C.CustomerID = P.CustomerID;");

			while(rs.next()) {
				Customer customer=new Customer();
				customer.setCustomerID(rs.getString("CustomerId"));
				customer.setFirstName(rs.getString("firstName"));
				customer.setLastName(rs.getString("lastName"));
				customer.setAddress(rs.getString("Address"));
				customer.setCity(rs.getString("City"));
				customer.setState(rs.getString("State"));
				customer.setZipCode(rs.getInt("ZipCode"));
				customer.setEmail(rs.getString("Email"));
				customers.add(customer);
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
		/*Sample data ends*/

		return customers;

	}


	// TESTED [by login]
	public String getCustomerID(String username) {
		/*
		 * This method returns the Customer's ID based on the provided email address
		 * The students code to fetch data from the database will be written here
		 * username, which is the email address of the customer, who's ID has to be returned, is given as method parameter
		 * The Customer's ID is required to be returned as a String
		 */
		System.out.println("*************** getCustomerID() ***************");
		// System.out.println("*******Get Customer by ID based on email addr**********");
		String foundCustomerID=null;
		try {
			String queryStatement="select customerID from customer where email=\""+username+"\";";
			ResultSet rs = Jdbc.newStatement(queryStatement);

			while(rs.next()) {
				foundCustomerID = rs.getString("CustomerID");
			}
		}
		catch(Exception e ) {
			System.out.println(e);
		}
		System.out.println("CustomerID: " + foundCustomerID);
		return foundCustomerID;
	}

	// CR TESTED
	public String addCustomer(Customer customer) {

		/*
		 * All the values of the add customer form are encapsulated in the customer object.
		 * These can be accessed by getter methods (see Customer class in model package).
		 * e.g. firstName can be accessed by customer.getFirstName() method.
		 * The sample code returns "success" by default.
		 * You need to handle the database insertion of the customer details and return "success" or "failure" based on result of the database insertion.
		 */
		
		/*Sample data begins*/
		System.out.println("*************** addCustomer() ***************");
		System.out.println("*******Add a customer **********");
		try {
			String queryStatement="INSERT INTO Customer"
					+ "(customerID, firstName, lastName, Address, City, State, zipCode, telephone, email, creditCardNum, rating)"
					+ " VALUES "
					+ "('"
					+ customer.getCustomerID()+"', '"
					+ customer.getFirstName()+"', '"
					+ customer.getLastName()+"', '"
					+ customer.getAddress()+"', '"
					+ customer.getCity()+"', '"
					+ customer.getState()+"', "
					+ customer.getZipCode()+", '"
					+ customer.getTelephone()+"', '"
					+ customer.getEmail()+"', '"
					+ customer.getCreditCard()+"', "
					+ customer.getRating()
					+ ");";
			System.out.println(queryStatement);
			Jdbc.deleteStatement(queryStatement);
			return "success";
		}
		catch(Exception e) {
			System.out.println(e);
			return "failure";
		}
		/*Sample data ends*/

	}

	// CR TESTED
	public String editCustomer(Customer customer) {
		/*
		 * All the values of the edit customer form are encapsulated in the customer object.
		 * These can be accessed by getter methods (see Customer class in model package).
		 * e.g. firstName can be accessed by customer.getFirstName() method.
		 * The sample code returns "success" by default.
		 * You need to handle the database update and return "success" or "failure" based on result of the database update.
		 */
		
		/*Sample data begins*/
		System.out.println("*************** editCustomer() ***************");
		System.out.println("*******Edit a customer **********");
		try {
//			String fetchCustomerID= customer.getCustomerID();
//			Customer chosen=getCustomer(fetchCustomerID);
//			System.out.println("Particular Customer: "+ chosen);
//			System.out.println("First name:    "+chosen.getFirstName());
			//Edit properties
//			chosen.setFirstName(customer.getFirstName());
//			System.out.println("First name:    "+chosen.getFirstName());
			String queryStatement="UPDATE Customer set "
					+ "firstName = '"+ customer.getFirstName()+ "',"
					+ "lastName = '"+ customer.getLastName()+ "', "
					+ "Address = '"+ customer.getAddress()+ "', "
					+ "City = '"+ customer.getCity()+ "', "
					+ "State = '"+ customer.getState()+ "', "
					+ "zipCode = "+ customer.getZipCode()+ ", "
					+ "telephone= '"+ customer.getTelephone()+ "', "
					+ "email = '"+ customer.getEmail()+ "', "
					+ "creditCardNum = '"+ customer.getCreditCard()+ "', "
					+ "rating = "+ customer.getRating() + " where customerID= '"+customer.getCustomerID()+"'";
			System.out.println(queryStatement);
			Jdbc.deleteStatement(queryStatement);
			return "success";
		}
		catch(Exception e) {
			System.out.println(e);
			return "failure";
		}
		/*Sample data ends*/

	}

	// CR TESTED
	public String deleteCustomer(String customerID) {

		/*
		 * This method deletes a customer returns "success" string on success, else returns "failure"
		 * The students code to delete the data from the database will be written here
		 * customerID, which is the Customer's ID who's details have to be deleted, is given as method parameter
		 */

		/*Sample data begins*/
		System.out.println("*************** deleteCustomer() ***************");
		System.out.println("*******Deleting a customer**********");
		try {
			String queryStatement="DELETE FROM customer WHERE customerID='"+customerID+"'";
			Jdbc.deleteStatement(queryStatement);
			return "success";
		}
		catch(Exception e) {
			System.out.println(e);
			return "failure";
		}
		/*Sample data ends*/
	}

}


