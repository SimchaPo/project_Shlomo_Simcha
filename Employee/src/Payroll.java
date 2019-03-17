
public class Payroll {
	/**
	 * this program handles employee earns
	 * @author Simcha Podolsky, 311215149
	 */
	public static void main(String[] args) throws Exception {
		try {
			Employee[] employers = new Employee[3];
			employers[0] = new HourlyEmployee("simcha", "podolsky", "311215149", 200, 25);
			employers[1] = new CommissionEmployee("dan", "zilberstain", "1111111", 4, 250);
			employers[2] = new BasePlusCommissionEmployee("shlomo", "meirzon", "25556699", 2, 10, 1400);
			for(Employee i : employers) {
				System.out.println(i.toString());
			} 
		} catch(NumberFormatException e) {
	         System.out.println(e.getMessage());
		}
	}
}
