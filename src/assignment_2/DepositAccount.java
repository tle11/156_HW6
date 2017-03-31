package assignment_2;

public class DepositAccount extends Asset {

	private double APR;
	private double totalBal;

	public DepositAccount(String code, String type, String label, double APR) {
		super(code, type, label);
		this.APR = APR;
	}
	
	/*
	 * Copy constructor, necessary since 
	 * values for assets changes during file parsing
	 */
	public DepositAccount(DepositAccount da){
		this.code = da.code;
		this.type = da.type;
		this.label = da.type;
		this.APR = da.APR;
	}
	
	public void setTotBal(double totalBal){
		this.totalBal = totalBal;
	}
	
	public double getRisk(){
		return 0.0;
	}
	
	public double getValue(){
		return this.totalBal;
	}
	
	public double getAnnualReturn(){
		return getValue() * (Math.exp(this.APR/100) -1);
	}
	
	public double getReturnRate(){
		return (Math.exp(this.APR/100) - 1) * 100;
	}
	
	public double getPhi(){
		return 0.0;
	}
}
