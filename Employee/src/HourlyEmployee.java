
class HourlyEmployee extends Employee {
	private int hours;
	private int wage;

	HourlyEmployee(){
		super();
		hours = 0;
		wage = 0;
	}
	HourlyEmployee(String myFirstName, String myLastName, String myId, int myHours, int myWage) throws Exception{
		super(myFirstName, myLastName, myId);
		setHours(myHours);
		setWage(myWage);
	}
	@Override
	double earnings() {
		return hours * wage;
	}
	public int getHours() {
		return hours;
	}
	public void setHours(int myHours) throws Exception {
		if(myHours < 0)
			throw new NumberFormatException("Hours can't be less then 0");
		hours = myHours;
	}
	public int getWage() {
		return wage;
	}
	public void setWage(int myWage) throws Exception {
		if(myWage < 0)
			throw new NumberFormatException("Wage can't be less then 0");
		wage = myWage;
	}
	@Override
	public String toString() {
		return super.toString() + ", hours: " + hours + ", wage: " + wage; 
	}
	boolean equals(HourlyEmployee e) {
		return super.equals(e) && hours == e.hours && wage == e.wage;
	}
}
