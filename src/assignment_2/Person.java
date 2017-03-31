package assignment_2;

public class Person {

	private String code;
	private String personType;
	private String identifier;
	private  Address address;  
	private String lastName;
	private String firstName;
	private String email[];

	//constructor for Person class
	public Person (String code, String personType, String identifier, String lastName, String firstName, Address address, String email[]){
		this.code = code;
		this.personType = personType;
		this.identifier = identifier;
		this.address = address;
		this.lastName = lastName;
		this.firstName = firstName;
		this.email = email;
	}
	
	//copy constructor of Person
	public Person(Person p){
		this.code = p.code;
		this.personType = p.personType;
		this.address = p.address;
		this.lastName = p.lastName;
		this.firstName = p.firstName;
		this.email = p.email;
	}
	
	

	//getter method for last name
	public String getLastName() {
		return this.lastName;
	}
	
	//setter method for last name
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getFirstName(){
		return this.firstName;
	}
	
	//getter method for email list
	public String[] getEmailList(){
		if (!(this.email == null) ){
			return this.email;
		}
		return null;
	}
	
	public String getFullName(){
		return this.lastName + ", " + this.firstName;
	}
	
	//getter method to get address
	public Address getAddress(){
		return this.address;
	}
	
	public String getCode(){
		return this.code;
	}
	
	public String getPersonType(){
		return this.personType;
	}
} //end of Person class
