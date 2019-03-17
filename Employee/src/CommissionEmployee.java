
class CommissionEmployee extends Employee {
	private int grossSales;
	private int commision;

	CommissionEmployee(){
		super();
		grossSales = 0;
		commision = 0;
	}
	CommissionEmployee(String myFirstName, String myLastName, String myId, int myGrossSales, int myCommision) throws Exception{
		super(myFirstName, myLastName, myId);
		setGrossSales(myGrossSales);
		setCommision(myCommision);
	}

	@Override
	double earnings() {
		return (commision/100) * grossSales;
	}
	public int getGrossSales() {
		return grossSales;
	}
	public void setGrossSales(int myGrossSales) throws Exception {
		if(myGrossSales < 0)
			throw new NumberFormatException("Gross sales can't be less then 0");
		grossSales = myGrossSales;
	}
	public int getCommision() {
		return commision;
	}
	public void setCommision(int myCommision) throws Exception {
		if(myCommision < 0)
			throw new NumberFormatException("Copmmision can't be less then 0");
		commision = myCommision;
	}
	@Override
	public String toString() {
		return super.toString() + ", commision " + commision + ", grossSales: " + grossSales; 
	}
	boolean equals(CommissionEmployee e) {
		return super.equals(e) && commision == e.commision && grossSales == e.grossSales;
	}
}
