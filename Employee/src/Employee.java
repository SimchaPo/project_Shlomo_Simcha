
abstract class Employee {
	private String firstName;
	private String lastName;
	private String id;
	Employee(){
		firstName = "plony";
		lastName = "almony";
		id = "0";
	}
	Employee(String myFirstName, String myLastName, String myId) throws Exception{
		firstName = myFirstName;
		lastName = myLastName;
		setId(myId);
	}
	void setFirstName(String myFirstName){
		firstName = myFirstName;
	}
	String getFirstName() {
		return firstName; 
	}
	void setLastName(String myLastName){
		lastName = myLastName;
	}
	String getLastName() {
		return lastName; 
	}
	void setId(String myId) throws Exception{
		try{
			Integer.parseInt(myId);
		}
		catch(NumberFormatException e) {
	         throw new NumberFormatException("ID must be a number");
		}
		id = myId;
	}
	String getId() {
		return id; 
	}
	public String toString() {
		return "Name: " + firstName + " " + lastName + ", ID: " + id;
	}
	boolean equals (Employee e) {
		return firstName == e.firstName && lastName == e.lastName && id == e.id;
	}
	abstract double earnings();
}