
class BasePlusCommissionEmployee extends CommissionEmployee {
	private int baseSalary;

	public int getBaseSalary() {
		return baseSalary;
	}
	public void setBaseSalary(int myBaseSalary) throws Exception {
		if(myBaseSalary < 0) {		
			throw new Exception("Base salary can't be less then 0");
		}
		baseSalary = myBaseSalary;
	}
	@Override
	double earnings() {
		return super.earnings() + baseSalary;
	}
	BasePlusCommissionEmployee(){
		super();
		baseSalary = 0;
	}
	BasePlusCommissionEmployee(String myFirstName, String myLastName, String myId, int myGrossSales, int myCommision, int myBaseSalary) throws Exception{
		super(myFirstName, myLastName, myId, myGrossSales, myCommision);
		setBaseSalary(myBaseSalary);
	}
	@Override
	public String toString() {
		return super.toString() + ", baseSalary: " + baseSalary;
	}
	boolean equals(BasePlusCommissionEmployee e) {
		return super.equals(e) && baseSalary == e.baseSalary;
	}
}
