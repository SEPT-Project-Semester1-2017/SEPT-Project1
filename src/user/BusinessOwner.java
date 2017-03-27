package user;

/**
 * business owner information will be stored in Businessowner table.
 */
public class BusinessOwner {
	private String BusinessName;
	private String BusinessOwnerName;
	private String address;
	private String phone;
	private String username;
	private String password;

	//create default constructor to evoke methods in the class
	public BusinessOwner(){}


	public String getBusinessName(){
		return BusinessName;
	}

	public void setBusinessName(String BusinessName){
		this.BusinessName = BusinessName;
	}

	public String getBusinessOwnerName(){
		return BusinessOwnerName;
	}

	public void setBusinessOwnerName(String BusinessOwnerName){
		this.BusinessOwnerName = BusinessOwnerName;
	}

	public String getaddress(){
		return address;
	}

	public void setaddress(String address){
		this.address = address;
	}

	public String getphone(){
		return phone;
	}

	public void setphone(String phone){
		this.phone = phone;
	}

	public String getusername(){
		return username;
	}

	public void setusername(String username){
		this.username = username;
	}

	public String getpassword(){
		return password;
	}

	public void setpassword(String password){
		this.password = password;
	}
}