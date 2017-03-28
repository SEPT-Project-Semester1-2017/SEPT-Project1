package business;

import java.util.HashMap;

/**
 * Each business owner can have many employees, each employee has separate working days/time in a week.
 * @author ranlu
 *
 */
public class Employee {

	private String firstname;
	private String lastname;
	private String email;
	private String contactNumber;

	//	The first string contains days information in a week, the second contains time in each day.
	private HashMap<String, String> workingSchedule = new HashMap<String, String>();


	//create constructor to call methods from Employee to other class
	public Employee(){}

	public String getFirstName(){
		return firstname;
	}

	public void setFirstName(String firstname){
		this.firstname = firstname;
	}

	public String getLastName(){
		return lastname;
	}

	public void setLastName(String lastname){
		this.lastname = lastname;
	}

	public String getEmail(){
		return email;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getContactNumber(){
		return contactNumber;
	}

	public void setContactNumber(String contactNumber){
		this.contactNumber = contactNumber;
	}

	public HashMap<String, String> getWorkingSchedule(){
		return workingSchedule;
	}

	public void setWorkingSchedule(HashMap<String,String> workingSchedule){
		this.workingSchedule = workingSchedule;
	}
}