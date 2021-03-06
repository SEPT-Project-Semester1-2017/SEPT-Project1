package database;

import java.io.File;


import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Logger;


/**
 * Manage databases include delete database if existed, create a AppointmentBookingSystem database,
 * create Business, Customerinfo, Employee, BusinessTime, WorkingTime and Booking tables and insert initial entities for them.
 * Also, include functions for searching database, create new entities.
 * @author ranlu
 */
public class DatabaseManager {

    Connection c = null;
    Statement stmt = null;
    private static Logger logger = Logger.getLogger("database.DatabaseManager");

	public DatabaseManager(){}


	/**
	 * delete existing database in local files, the path is /Users/'username'/AppointmentBookingSystem.db
	 */
	public void deleteDatabase(){
        File f = new File("AppointmentBookingSystem.db");
    	f.delete();
	}

	/**
	 * This method will create a database in /Users/me/ using SQLite.
	 * @param fileName will be retrieved from clientModel, default will be AppointmentBookingSystem.
	 */
    public void createNewDatabase(String fileName) {
        String url = "jdbc:sqlite:" + fileName;
        try{
        	c = DriverManager.getConnection(url);
			stmt = c.createStatement();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * create new table business
     */
    public void createBusinessTable(){
        try {
          String sql = "CREATE TABLE Business " +
                       "(business_name	        TEXT	NOT NULL, " +
                       " business_owner_name	TEXT	NOT NULL, " +
                       " address            	TEXT	NOT NULL, " +
                       " phone        			TEXT	NOT NULL, " +
                       " username        		TEXT    PRIMARY KEY     NOT NULL, " +
                       " password         		TEXT    NOT NULL)";
          stmt.executeUpdate(sql);
        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
    }


    /**
     * Insert entities for business table.
     */
    public void insertInitialEntitiesForBusiness(){
        try {
          c.setAutoCommit(false);
          String sql = "INSERT INTO Business (business_name,business_owner_name,address,phone,username,password) " +
                       "VALUES ('Da Guido Melbourne', 'Williams','130 Lygon St, Carlton', '+61 3 8528 4547','owner1','owner1' );";
          stmt.executeUpdate(sql);

          sql = "INSERT INTO Business (business_name,business_owner_name,address,phone,username,password) " +
                "VALUES ('TONI&GUY Georges', 'Tom', '195 Little Collins St, Melbourne', '(03) 9654 9444', 'owner', 'owner');";
          stmt.executeUpdate(sql);

          c.commit();

        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
    }


	/**
	 * Insert username, password, firstname, lastname,
	 *  address and contact number into business database
	 */
    public boolean insertIntoBusiness(String business_name, String business_owner_name,
			String address, String phone, String username, String password){
        try {
          c.setAutoCommit(false);
          String sql = "INSERT INTO Business (business_name,business_owner_name,address,phone,username,password) " +
                       "VALUES ('"+ business_name +"', '"+ business_owner_name +"','"+ address +"', '"+ phone +"', '"+ username +"','"+ password +"' );";
          stmt.executeUpdate(sql);
          c.commit();
          logger.info("A new business owner has been inserted into Business.");
          return true;
        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
		return false;
    }

	/**
	 * Get all business owners in the database.
	 */
	public ArrayList<ArrayList<String>> getAllBusiness(){
        try {
			ArrayList<ArrayList<String>> business = new ArrayList<ArrayList<String>>();
			String sql = "select * from Business";
			ResultSet result = stmt.executeQuery(sql);
			while(result.next()){
				ArrayList<String> temp = new ArrayList<String>();
				temp.add(result.getString("business_name"));
				temp.add(result.getString("address"));
				temp.add(result.getString("phone"));
				business.add(temp);
			}
            return business;
          } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
          }
        return null;
	}


	/**
	 * Get one business owner when the customer select a business name.
	 */
	public ArrayList<String> getOneBusiness(String business_name){
        try {
			ArrayList<String> business = new ArrayList<String>();
			String sql = "select * from Business where business_name = '"+business_name+"'";
			ResultSet result = stmt.executeQuery(sql);
			while(result.next()){
				business.add(result.getString("business_name"));
				business.add(result.getString("address"));
				business.add(result.getString("phone"));
				business.add(result.getString("username"));
			}
            return business;
          } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
          }
        return null;
	}


    /**
     * create new table customerinfo
     */
    public void createCustomerInfoTable(){
        try {
          String sql = "CREATE TABLE Customerinfo " +
                       "(first_name	        TEXT	NOT NULL, " +
                       " last_name			TEXT    NOT NULL, " +
                       " address            TEXT	NOT NULL, " +
                       " contact_number     TEXT	NOT NULL, " +
                       " username        	TEXT    PRIMARY KEY     NOT NULL, " +
                       " password         	TEXT    NOT NULL)";
          stmt.executeUpdate(sql);

        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
    }


	/**
	 * Search Customerinfo table to find customer information.
	 */
	public ArrayList<String> getCustomerinfo(String username){
		try{
			ArrayList<String> customer = new ArrayList<String>();
			String sql = "select * from Customerinfo where username = '"+username+"'";
			ResultSet result = stmt.executeQuery(sql);
			if(result.next()){
				customer.add(result.getString("first_name"));
				customer.add(result.getString("last_name"));
				customer.add(result.getString("address"));
				customer.add(result.getString("contact_number"));

				return customer;
			}
		}
		catch (Exception exp){
			exp.printStackTrace();
		}
		return null;
	}


    /**
     * Insert entities for customerinfo table.
     */
    public void insertInitialEntitiesForCustomerInfo(){
        try {
          c.setAutoCommit(false);
          String sql = "INSERT INTO Customerinfo (first_name,last_name,address,contact_number,username,password) " +
                       "VALUES ('David', 'Beckham','London', '0123456789', 'customer1','customer1' );";
          stmt.executeUpdate(sql);

          sql = "INSERT INTO Customerinfo (first_name,last_name,address,contact_number,username,password) " +
                "VALUES ('Ran','Lu','Melbourne','0123456789','customer','customer');";
          stmt.executeUpdate(sql);
          c.commit();
        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
    }


	/**
	 * Search business database to find if username and password exist and they are in the business name.
	 */
	public boolean searchBusiness(String username, String password){
		try{
			   String business = "select * from Business where username = '" + username + "' and password = '" + password + "'";
			ResultSet result = stmt.executeQuery(business);
			if(result.next()){
				return true;
			}
		}
		catch (Exception exp){
			exp.printStackTrace();
		}
		return false;
	}


	/**
	 *
	 * @return true if username exists in the customerinfo database and password matches username in the same row
	 */
	public boolean searchCustomer(String username, String password){
		try{
			   String customer = "select * from Customerinfo where username = '" + username + "' and password = '" + password + "'";
			ResultSet result = stmt.executeQuery(customer);
			if(result.next()){
				return true;
			}
		}
		catch (Exception exp){
			exp.printStackTrace();
		}
		return false;
	}


	/**
	 * Search existing customer by contact number.
	 * @return customer's firstname and lastname.
	 */
	public ArrayList<ArrayList<String>> searchCustomerByNumber(String contact_number){
		try{
			boolean check = false;
			ArrayList<ArrayList<String>> customer = new ArrayList<ArrayList<String>>();
			String sql = "select * from Customerinfo where contact_number = '" + contact_number + "'";
			ResultSet result = stmt.executeQuery(sql);
			while(result.next()){
				ArrayList<String> temp = new ArrayList<String>();
				temp.add(result.getString("first_name"));
				temp.add(result.getString("last_name"));
				customer.add(temp);
				check = true;
			}
			if(check == true){
				return customer;
			}
		}
		catch (Exception exp){
			exp.printStackTrace();
		}
		return null;
	}


	/**
	 * Search business database to find if input username exists
	 * @param input retrieved from clientModel
	 * @return true if username exists in the business database
	 */
	public boolean searchBusinessUserName(String input){
		try{
			   String userCheck = "select * from Business where username = '" + input + "'";
			   ResultSet result = stmt.executeQuery(userCheck);
			if(result.next()){
				return true;
			}
		}
		catch (Exception exp){
			exp.printStackTrace();
		}
		return false;
	}


	/**
	 * Match user's input username with data in customerinfo to find if the username is correct.
	 */
	public boolean searchCustomerUserName(String input){
		try{
			   String userCheck = "select * from Customerinfo where username = '" + input + "'";
			   ResultSet result = stmt.executeQuery(userCheck);
			if(result.next()){
				return true;
			}
		}
		catch (Exception exp){
			exp.printStackTrace();
		}
		return false;
	}


	/**
	 * Insert username, password, firstname, lastname,
	 *  address and contact number into customerinfo database
	 */
    public boolean insertIntoCustomer(String firstname, String lastname,
			String address, String contactNumber, String username, String password){
        try {
          c.setAutoCommit(false);
          String sql = "INSERT INTO Customerinfo (first_name,last_name,address,contact_number,username,password) " +
                       "VALUES ('"+ firstname +"', '"+ lastname +"','"+ address +"', '"+ contactNumber +"', '"+ username +"','"+ password +"' );";
          stmt.executeUpdate(sql);
          c.commit();
          logger.info("A new customer has been inserted into Customerinfo.");
          return true;
        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
		return false;
    }


    /**
     * Get business name for a given username in the business table.
     * @return business name for the given username.
     */
	public String getBusinessName(String username){
		try{
	        String businessName;
			//create a query for SQLite search
			   String getBusiness = "select business_name from Business where username = '" + username + "'";
			//get all tuples from business table that matches the input
			   ResultSet result = stmt.executeQuery(getBusiness);
			if(result.next()){
				businessName = result.getString("business_name");
				return businessName;
			}
		}
		catch (Exception exp){
			exp.printStackTrace();
		}
		return null;
	}


    /**
     * Get name for a given username in the customer table.
     * @return name for the given username.
     */
	public String getCustomerName(String username){
		try{
	        String firstname;
	        String lastname;
			//create a query for SQLite search
			   String getName = "select * from Customerinfo where username = '" + username + "'";
			   ResultSet result = stmt.executeQuery(getName);
			if(result.next()){
				firstname = result.getString("first_name");
				lastname = result.getString("last_name");
				return firstname + " " + lastname;
			}
		}
		catch (Exception exp){
			exp.printStackTrace();
		}
		return null;
	}


    /**
     * create new table Employee.
     */
    public void createEmployeeTable(){
        try {
          String sql = "CREATE TABLE Employee " +
                       "(first_name	        TEXT	NOT NULL," +
                       " last_name			TEXT	NOT NULL, " +
                       " owner_username     TEXT	NOT NULL, " +
                       " email			    TEXT 		 PRIMARY KEY     NOT NULL, " +
                       " contact_number     TEXT	NOT NULL, " +
                       "FOREIGN KEY(owner_username)	REFERENCES Business(username))";
          stmt.executeUpdate(sql);
        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
    }

    
    /**
     * Search employee's full name.
     */
	public boolean searchEmployeeName(String first_name, String last_name, String owner_username){
		try{
			   String str = "select * from Employee where first_name = '" + first_name + "' and last_name = '" + last_name + "' and owner_username = '" + owner_username + "'";
			ResultSet result = stmt.executeQuery(str);
			if(result.next()){
				return true;
			}
		}
		catch (Exception exp){
			exp.printStackTrace();
		}
		return false;
	}

	
	/**
	 * Match user's input email with data in Employee to find if the email already exists.
	 */
	public boolean searchEmployeeEmail(String email){
		try{
			   String check = "select * from Employee where email = '" + email + "'";
			   ResultSet result = stmt.executeQuery(check);
			if(result.next()){
				return true;
			}
		}
		catch (Exception exp){
			exp.printStackTrace();
		}
		return false;
	}


	/**
	 * Match user's input email with data in Employee to find employee's first name and last name.
	 */
	public ArrayList<String> searchOneEmployee(String email){
		try{
			ArrayList<String> name = new ArrayList<String>();
			String check = "select * from Employee where email = '" + email + "'";
			ResultSet result = stmt.executeQuery(check);
			if(result.next()){
				name.add(result.getString("first_name"));
				name.add(result.getString("last_name"));
				name.add(result.getString("contact_number"));
				return name;
			}
		}
		catch (Exception exp){
			exp.printStackTrace();
		}
		return null;
	}


	/**
	 * Search employee's email by his/her full name.
	 */
	public String searchEmployeeEmailByName(String first_name, String last_name){
		try{
			String email;
			String check = "select * from Employee where first_name = '" + first_name + "' and last_name = '" + last_name + "'";
			ResultSet result = stmt.executeQuery(check);
			if(result.next()){
				email = result.getString("email");
				return email;
			}
		}
		catch (Exception exp){
			exp.printStackTrace();
		}
		return null;
	}


    /**
     * set new employee in database
     */
    public void setEmployee(String firstname, String lastname, String owner_username, String email, String contact_number){
        try {
          c.setAutoCommit(false);
          String sql = "INSERT INTO Employee (first_name,last_name,owner_username,email,contact_number) " +
                  "VALUES ('"+ firstname +"', '"+ lastname +"','"+ owner_username +"', '"+ email +"', '"+ contact_number +"');";
          stmt.executeUpdate(sql);
          c.commit();
          logger.info("A new employee has been inserted into Employee.");
        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
    }

    /**
     * Update employee's information.
     */
    public void updateEmployee(String first_name, String last_name, String owner_username, String contact_number){
        try {
          c.setAutoCommit(false);
          String sql = "UPDATE Employee set contact_number = '" + contact_number + "' where first_name = '" + first_name + "' and last_name = '" + last_name + "' and owner_username = '" + owner_username + "';";
          stmt.executeUpdate(sql);
          c.commit();
          logger.info(first_name +" " +last_name + "'s information has been updated.");
        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
    }
    
    
    /**
     * Get all employees for one business owner.
     */
    public ArrayList<ArrayList<String>> getEmployee(String username){
        try {
			ArrayList<ArrayList<String>> employee = new ArrayList<ArrayList<String>>();
			String sql = "select * from Employee where owner_username = '" + username + "'";
			ResultSet result = stmt.executeQuery(sql);
			while(result.next()){
				ArrayList<String> temp = new ArrayList<String>();
				temp.add(result.getString("first_name"));
				temp.add(result.getString("last_name"));
				temp.add(result.getString("email"));
				temp.add(result.getString("contact_number"));
				employee.add(temp);
			}
            return employee;
          } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
          }
        return null;
    }


    /**
     * Insert entities for employee table.
     */
    public void insertInitialEntitiesForEmployee(){
        try {
          c.setAutoCommit(false);
          String sql = "INSERT INTO Employee (first_name,last_name,owner_username,email,contact_number) " +
                       "VALUES ('Smith', 'Jo', 'owner', 'Smith@gmail.com', '03 9654 9444' );";
          stmt.executeUpdate(sql);

          sql = "INSERT INTO Employee (first_name,last_name,owner_username,email,contact_number) " +
                  "VALUES ('Andrew', 'Dion', 'owner', 'Andrew@gmail.com', '0123 456 789' );";
          stmt.executeUpdate(sql);


          sql = "INSERT INTO Employee (first_name,last_name,owner_username,email,contact_number) " +
                  "VALUES ('Giuseppe', 'Verdi', 'owner1', 'Giuseppe@gmail.com', '(+61) 03 8528 4547' );";
          stmt.executeUpdate(sql);

          c.commit();
        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
    }



    /**
     * create new table BusinessTime, store each business owner's business date and time.
     */
    public void createBusinessTimeTable(){
        try {
          String sql = "CREATE TABLE BusinessTime " +
                       "(business_day	    TEXT	NOT NULL," +
                       " owner_username     TEXT	NOT NULL, " +
                       " open_time     		TEXT	NOT NULL, " +
                       " closing_time       TEXT	NOT NULL, " +
                       "PRIMARY KEY(business_day,owner_username,open_time,closing_time)," +
                       "FOREIGN KEY(owner_username)	REFERENCES Business(username))";
          stmt.executeUpdate(sql);
        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
    }


	/**
	 * Search existing business hour entity.
	 */
	public boolean searchBusinessHours(String business_day, String owner_username){
		try{
			String check = "select * from BusinessTime where business_day = '" + business_day + "' and owner_username = '" + owner_username + "'";
			ResultSet result = stmt.executeQuery(check);
			if(result.next()){
				return true;
			}
		}
		catch (Exception exp){
			exp.printStackTrace();
		}
		return false;
	}

    /**
     * Get business time for a given business in the BusinessTime.
     * @param username received from cilentModel, passed to the businessController.
     * @return business time for the given business username.
     */
	public ArrayList<ArrayList<String>> getBusinessTime(String username){
		try{
			ArrayList<ArrayList<String>> businessTime = new ArrayList<ArrayList<String>>();
			String sql = "select * from BusinessTime where owner_username = '" + username + "'";
			ResultSet result = stmt.executeQuery(sql);
			while(result.next()){
				ArrayList<String> temp = new ArrayList<String>();
				temp.add(result.getString("business_day"));
				temp.add(result.getString("open_time"));
				temp.add(result.getString("closing_time"));
				businessTime.add(temp);
			}
			return businessTime;
		}
		catch (Exception e){
	          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
		return null;
	}


	/**
	 * Update existing business hours for one business owner.
	 */
	public boolean updateBusinessHours(String business_day, String owner_username, String open_time, String closing_time){
        try {
            c.setAutoCommit(false);
            String sql = "UPDATE BusinessTime set open_time = '" + open_time + "' where owner_username = '" + owner_username + "' and business_day = '" + business_day + "';";
            stmt.executeUpdate(sql);
            sql = "UPDATE BusinessTime set closing_time = '" + closing_time + "' where owner_username = '" + owner_username + "' and business_day = '" + business_day + "';";
            stmt.executeUpdate(sql);
            c.commit();
            logger.info("Business hours has been updated for " + owner_username + " in BusinessTime.");
            return true;
          } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
          }
		return false;
	}


    /**
     * set business time for a business owner.
     */
    public boolean setBusinessTime(String business_day, String owner_username, String open_time, String closing_time){
        try {
          c.setAutoCommit(false);
          String sql = "INSERT INTO BusinessTime (business_day,owner_username,open_time,closing_time) " +
                  "VALUES ('"+ business_day +"', '"+ owner_username +"','"+ open_time +"', '"+ closing_time +"');";
          stmt.executeUpdate(sql);
          c.commit();
          logger.info("New business hours has been inserted for " + owner_username + " in BusinessTime.");
          return true;
        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
		return false;
    }


    /**
     * Initial entities for business hours will be created when starting the program. Customer can view these information when select
     * view available day/time for booking.
     */
    public void insertInitialEntitiesForBusinessHours(){
        try {
            c.setAutoCommit(false);

            //entities for Toni&Guy
            String sql = "INSERT INTO BusinessTime (business_day,owner_username,open_time,closing_time) " +
                         "VALUES ('Monday', 'owner', '9:00', '18:30' );";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO BusinessTime (business_day,owner_username,open_time,closing_time) " +
                    "VALUES ('Tuesday', 'owner', '9:00', '18:30' );";
            stmt.executeUpdate(sql);
       		sql = "INSERT INTO BusinessTime (business_day,owner_username,open_time,closing_time) " +
               "VALUES ('Wednesday', 'owner', '10:00', '20:00' );";
       		stmt.executeUpdate(sql);
       		sql = "INSERT INTO BusinessTime (business_day,owner_username,open_time,closing_time) " +
                    "VALUES ('Thursday', 'owner', '10:00', '21:00' );";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO BusinessTime (business_day,owner_username,open_time,closing_time) " +
                            "VALUES ('Friday', 'owner', '10:00', '21:00' );";
            stmt.executeUpdate(sql);
       		sql = "INSERT INTO BusinessTime (business_day,owner_username,open_time,closing_time) " +
                    "VALUES ('Saturday', 'owner', '9:30', '17:30' );";
            stmt.executeUpdate(sql);

            //entities for Da Guido Melbourne la Pasta
       		sql = "INSERT INTO BusinessTime (business_day,owner_username,open_time,closing_time) " +
                    "VALUES ('Monday', 'owner1', '18:00', '22:00' );";
            stmt.executeUpdate(sql);
       		sql = "INSERT INTO BusinessTime (business_day,owner_username,open_time,closing_time) " +
                    "VALUES ('Tuesday', 'owner1', '18:00', '22:00' );";
            stmt.executeUpdate(sql);
       		sql = "INSERT INTO BusinessTime (business_day,owner_username,open_time,closing_time) " +
                    "VALUES ('Wednesday', 'owner1', '18:00', '22:00' );";
            stmt.executeUpdate(sql);
       		sql = "INSERT INTO BusinessTime (business_day,owner_username,open_time,closing_time) " +
                    "VALUES ('Thursday', 'owner1', '18:00', '22:00' );";
            stmt.executeUpdate(sql);
       		sql = "INSERT INTO BusinessTime (business_day,owner_username,open_time,closing_time) " +
                    "VALUES ('Friday', 'owner1', '18:00', '22:00' );";
            stmt.executeUpdate(sql);
       		sql = "INSERT INTO BusinessTime (business_day,owner_username,open_time,closing_time) " +
                    "VALUES ('Saturday', 'owner1', '18:00', '22:00' );";
            stmt.executeUpdate(sql);
       		sql = "INSERT INTO BusinessTime (business_day,owner_username,open_time,closing_time) " +
                    "VALUES ('Sunday', 'owner1', '18:00', '22:00' );";
            stmt.executeUpdate(sql);
            c.commit();
        }
        catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    /**
     * create new table WorkingTime, store each employee's working time in a week, user day as primary key, email as foreign key.
     */
    public void createWorkingTimeTable(){
        try {
          String sql = "CREATE TABLE WorkingTime " +
                       "(day	 	 	TEXT	NOT NULL," +
                       " start_time	    TEXT	NOT NULL, " +
                       " end_time	    TEXT	NOT NULL, " +
                       " employee_email	TEXT	NOT NULL, " +
                       "PRIMARY KEY (day,start_time,end_time,employee_email)," +
                       "FOREIGN KEY(employee_email)	REFERENCES Employee(email))";
          stmt.executeUpdate(sql);
        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
    }


	/**
	 * Search existing working time entity.
	 */
	public boolean searchWorkingTime(String day, String start_time, String end_time,String employee_email){
		try{
			String check = "select * from WorkingTime where day = '" + day + "' and start_time = '" + start_time + "' and end_time = '" + end_time + "' and employee_email = '" + employee_email + "'";
			ResultSet result = stmt.executeQuery(check);
			if(result.next()){
				return true;
			}
		}
		catch (Exception exp){
			exp.printStackTrace();
		}
		return false;
	}


    /**
     * Get all working time for one employee.
     */
    public ArrayList<ArrayList<String>> getWorkingTime(String email){
        try {
			ArrayList<ArrayList<String>> workingTime = new ArrayList<ArrayList<String>>();
			String sql = "select * from WorkingTime where employee_email = '" + email + "'";
			ResultSet result = stmt.executeQuery(sql);
			while(result.next()){
				ArrayList<String> temp = new ArrayList<String>();
				temp.add(result.getString("day"));
				temp.add(result.getString("start_time"));
				temp.add(result.getString("end_time"));
				workingTime.add(temp);
			}
            return workingTime;
          } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
          }
        return null;
    }


    /**
     * set working time for an employee.
     */
    public boolean setWorkingTime(String day, String start_time, String end_time, String employee_email){
        try {
          c.setAutoCommit(false);
          String sql = "INSERT INTO WorkingTime (day,start_time,end_time,employee_email) " +
                  "VALUES ('"+ day +"', '"+ start_time +"','"+ end_time +"','"+ employee_email +"');";
          stmt.executeUpdate(sql);
          c.commit();
          logger.info("New working time for " + employee_email +" has been inserted into WorkingTime.");
          return true;
        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
		return false;
    }


    /**
     * Add initial entities for employees' working time.
     */
    public void insertInitialEntitiesForWorkingTime(){
        try {
            c.setAutoCommit(false);
            String sql = "INSERT INTO WorkingTime (day,start_time,end_time,employee_email) " +
                         "VALUES ('Monday', '9:00', '11:00', 'Smith@gmail.com');";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO WorkingTime (day,start_time,end_time,employee_email) " +
                    "VALUES ('Tuesday', '10:00', '15:00', 'Smith@gmail.com');";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO WorkingTime (day,start_time,end_time,employee_email) " +
            		"VALUES ('Monday', '10:00', '12:00', 'Andrew@gmail.com');";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO WorkingTime (day,start_time,end_time,employee_email) " +
            		"VALUES ('Tuesday', '13:00', '15:00', 'Andrew@gmail.com');";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO WorkingTime (day,start_time,end_time,employee_email) " +
            		"VALUES ('Wednesday', '19:00', '22:00', 'Giuseppe@gmail.com');";
            stmt.executeUpdate(sql);
            c.commit();
          } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
          }
    }


    /**
     * Create new table Booking, store each booking's time, owner_username, customer_username.
     */
    public void createBookingTable(){							//date, start time, employee are enough to define an unique booking.
        try {
          String sql = "CREATE TABLE Booking " +
                       "(date				TEXT	NOT NULL, " +
                       " start_time			TEXT	NOT NULL, " +
                       " employee_email		TEXT	NOT NULL, " +
                       " service			TEXT	NOT NULL, " +
                       " owner_username		TEXT	NOT NULL, " +
                       " customer_firstname	TEXT	NOT NULL, " +
                       " customer_lastname	TEXT	NOT NULL, " +
                       " customer_contact	TEXT	NOT NULL, " +
                       "PRIMARY KEY(date,start_time,employee_email,owner_username)," +
                       "FOREIGN KEY(employee_email) REFERENCES Employee(email)," +
                       "FOREIGN KEY(service) REFERENCES Service(name)," +
                       "FOREIGN KEY(owner_username) REFERENCES Business(username))";
          stmt.executeUpdate(sql);
        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
    }

    /**
     * Create a new booking entity in booking table.
     */
    public boolean setBooking(String date, String start_time, String employee_email,
    		String service, String owner_username, String customer_firstname, String customer_lastname, String customer_contact){
        try {
	          c.setAutoCommit(false);
	          String sql = "INSERT INTO Booking(date,start_time,employee_email,service,owner_username,customer_firstname,customer_lastname,customer_contact) " +
	                  "VALUES ('"+ date +"', '"+ start_time +"','"+ employee_email +"','"+ service +"','"+ owner_username +"','"+ customer_firstname+"','"+ customer_lastname+"','"+ customer_contact+"');";
	          stmt.executeUpdate(sql);
	          c.commit();
	          logger.info("A New booking for " + owner_username + " made by "+ customer_firstname + " " + customer_lastname +" has been inserted into Booking.");
	          return true;
        }
        catch(Exception e){
	          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	          System.exit(0);
        }
		return false;
    }


	/**
	 * Search the database to find if an existing booking has been created in the booking table.
	 */
	public boolean searchBooking(String date, String start_time, String employee_email, String owner_username){
		try{
			   String sql = "select * from Booking where date = '" + date + "' and start_time = '" + start_time + "' and employee_email = '" + employee_email + "' and owner_username = '" + owner_username + "'";
			ResultSet result = stmt.executeQuery(sql);
			if(result.next()){
				return true;
			}
		}
		catch (Exception exp){
			exp.printStackTrace();
		}
		return false;
	}


    /**
     * Get all bookings for one business owner.
     */
    public ArrayList<ArrayList<String>> getBookingForBusiness(String username){
        try {
			ArrayList<ArrayList<String>> booking = new ArrayList<ArrayList<String>>();
			String sql = "select * from Booking where owner_username = '" + username + "'";
			ResultSet result = stmt.executeQuery(sql);
			while(result.next()){
				ArrayList<String> temp = new ArrayList<String>();
				temp.add(result.getString("date"));
				temp.add(result.getString("start_time"));
				temp.add(result.getString("employee_email"));
				temp.add(result.getString("service"));
				temp.add(result.getString("customer_firstname"));
				temp.add(result.getString("customer_lastname"));
				temp.add(result.getString("customer_contact"));
				booking.add(temp);
			}
            return booking;
          } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
          }
        return null;
    }


    /**
     * Get all bookings for one employee.
     */
    public ArrayList<ArrayList<String>> getBookingForEmployee(String employee_email, String date){
        try {
			ArrayList<ArrayList<String>> booking = new ArrayList<ArrayList<String>>();
			String sql = "select * from Booking where employee_email = '" + employee_email + "' and date = '" + date + "'";
			ResultSet result = stmt.executeQuery(sql);
			while(result.next()){
				ArrayList<String> temp = new ArrayList<String>();
				temp.add(result.getString("start_time"));
				temp.add(result.getString("service"));
				booking.add(temp);
			}
            return booking;
          } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
          }
        return null;
    }


    /**
     * Insert initial entities for booking on test purposes.
     */
    public void insertInitialEntitiesForBooking(){
        try {
          c.setAutoCommit(false);

          String sql = "INSERT INTO Booking (date,start_time,employee_email,service,owner_username,customer_firstname,customer_lastname,customer_contact) " +
                       "VALUES ('2017-03-06','11:00','Smith@gmail.com','Men Haircut','owner','Bruce','Wayne','01234 56789');";
          stmt.executeUpdate(sql);
          sql = "INSERT INTO Booking (date,start_time,employee_email,service,owner_username,customer_firstname,customer_lastname,customer_contact) " +
                  "VALUES ('2017-03-13','15:00','Smith@gmail.com','Hair Colouring','owner','David','Short','0123 456 789');";
          stmt.executeUpdate(sql);
          sql = "INSERT INTO Booking (date,start_time,employee_email,service,owner_username,customer_firstname,customer_lastname,customer_contact) " +
                  "VALUES ('2017-04-18','12:00','Andrew@gmail.com','Women Haircut','owner','Ran','Lu','0123 456 789');";
          stmt.executeUpdate(sql);
          sql = "INSERT INTO Booking (date,start_time,employee_email,service,owner_username,customer_firstname,customer_lastname,customer_contact) " +
                  "VALUES ('2017-06-05','15:00','Smith@gmail.com','Women Haircut','owner','David','Short','0123 456 789');";
          stmt.executeUpdate(sql);
          sql = "INSERT INTO Booking (date,start_time,employee_email,service,owner_username,customer_firstname,customer_lastname,customer_contact) " +
                  "VALUES ('2017-06-06','17:00','Andrew@gmail.com','Men Haircut','owner','Ran','Lu','0123 456 789');";
          stmt.executeUpdate(sql);

          //booking for owner 1
          sql = "INSERT INTO Booking (date,start_time,employee_email,service,owner_username,customer_firstname,customer_lastname,customer_contact) " +
                  "VALUES ('2017-04-11','20:00','Giuseppe@gmail.com','Dinner','owner1','Ran','Lu','0123 456 789');";
          stmt.executeUpdate(sql);
          sql = "INSERT INTO Booking (date,start_time,employee_email,service,owner_username,customer_firstname,customer_lastname,customer_contact) " +
                  "VALUES ('2017-06-07','20:00','Giuseppe@gmail.com','Dinner','owner1','Ran','Lu','0123 456 789');";
          stmt.executeUpdate(sql);
          c.commit();
        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
    }


    /**
     * create new table Service, store services for each business owner.
     */
    public void createServiceTable(){
        try {
          String sql = "CREATE TABLE Service " +
                       "(name	 	 	TEXT	NOT NULL," +
                       " duration	    TEXT	NOT NULL, " +
                       " owner_username	TEXT	NOT NULL, " +
                       " description	TEXT, " +
                       "PRIMARY KEY (name,owner_username)," +
                       "FOREIGN KEY(owner_username)	REFERENCES Business(username))";
          stmt.executeUpdate(sql);
        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
    }

    /**
     * Add service for a business owner.
     */
    public boolean addService(String name, String duration, String owner_username, String description){
        try {
          c.setAutoCommit(false);
          String sql = "INSERT INTO Service (name,duration,owner_username,description) " +
                  "VALUES ('"+ name +"', '"+ duration +"','"+ owner_username +"','"+ description +"');";
          stmt.executeUpdate(sql);
          c.commit();
          logger.info("A new service has been inserted into Service.");
          return true;
        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
		return false;
    }


	/**
	 * Search the database to find if an existing service has been created in the service table.
	 */
	public boolean searchService(String name, String owner_username){
		try{
			   String sql = "select * from Service where name = '" + name + "' and owner_username = '" + owner_username + "'";
			ResultSet result = stmt.executeQuery(sql);
			if(result.next()){
				return true;
			}
		}
		catch (Exception exp){
			exp.printStackTrace();
		}
		return false;
	}


    /**
     * Insert initial entities for service table.
     */
	public void insertInitialEntitiesForService() {
        try {
            c.setAutoCommit(false);

            String sql = "INSERT INTO Service (name,duration,owner_username,description) " +
                         "VALUES ('Men Haircut','30','owner','The duration is 30 minutes, all employee can provide this service.');";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO Service (name,duration,owner_username,description) " +
                    "VALUES ('Women Haircut','60','owner','The duration is 60 minutes, all employee can provide this service.');";
            stmt.executeUpdate(sql);
       	 	sql = "INSERT INTO Service (name,duration,owner_username,description) " +
               "VALUES ('Hair Colouring','60','owner','The duration is 60 minutes, all employee can provide this service.');";
       		stmt.executeUpdate(sql);

       	 	sql = "INSERT INTO Service (name,duration,owner_username,description) " +
                    "VALUES ('Dinner','30','owner1','Dinner need to be reserved on day earlier.');";
            stmt.executeUpdate(sql);

            c.commit();
          } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
          }
	}


    /**
     * Get all service for one business owner.
     */
    public ArrayList<ArrayList<String>> getService(String username){
        try {
			ArrayList<ArrayList<String>> service = new ArrayList<ArrayList<String>>();
			String sql = "select * from Service where owner_username = '" + username + "'";
			ResultSet result = stmt.executeQuery(sql);
			while(result.next()){
				ArrayList<String> temp = new ArrayList<String>();
				temp.add(result.getString("name"));
				temp.add(result.getString("duration"));
				temp.add(result.getString("description"));
				service.add(temp);
			}
            return service;
          } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
          }
        return null;
    }


    /**
     * Get duration for one service.
     */
    public String getDuration(String service_name, String ownerID){
        try {
        	String duration;
			String sql = "select * from Service where name = '" + service_name + "' and owner_username = '" + ownerID + "'";
			ResultSet result = stmt.executeQuery(sql);
			while(result.next()){
				duration = result.getString("duration");
				return duration;
			}
          } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
          }
        return null;
    }
}
