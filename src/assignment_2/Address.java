
	package assignment_2;

	public class Address {

		private int addressId;
		private String street;
		private String city;
		private String state;
		private String zipCode; 
		private String country;
		
		//constructor for Address class
		public Address(String street, String city, String state, String zipCode, String country) {
			this.street = street;
			this.city = city;
			this.state = state;
			this.zipCode = zipCode;
			this.country = country;
			
		}
		
		public void setPrimaryId(int id){
			this.addressId = id;
		}
		//getter method for city
		public String getCity(){
			return this.city;
		}
		
	} //end of Address class

