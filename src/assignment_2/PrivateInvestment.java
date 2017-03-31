package assignment_2;

public class PrivateInvestment extends Asset {

	private double omegaMeasure;
	private double totalValue;
	private double quarterlyDividend;
	private double baseRateOfReturn;
	private double perStake;
	
	//constructor for Private Investment class
	public PrivateInvestment(String code, String type, String label, double quarterlyDividend, double baseRateOfReturn,
				double omegaMeasure, double totalValue) {
		super(code, type, label);
		this.quarterlyDividend = quarterlyDividend;
		this.baseRateOfReturn = baseRateOfReturn/100.0;
		this.omegaMeasure = omegaMeasure;
		this.totalValue = totalValue;
	}
	
	/*
	 * Copy constructor, necessary since 
	 * values for assets changes during file parsing
	 */
	public PrivateInvestment(PrivateInvestment p){
		this.code = p.code;
		this.type = p.type;
		this.label = p.label;
		this.quarterlyDividend = p.quarterlyDividend;
		this.baseRateOfReturn = p.baseRateOfReturn;
		this.omegaMeasure = p.omegaMeasure;
		this.totalValue = p.totalValue;
	}
	
	//getter method for omega measure
	public double getPhi(){
		return this.getRisk();
	}

	//getter method for base rate of return
	public double getBaseRateOfReturn(){
		return this.baseRateOfReturn;
	}
	
	public void setPerStake(double perStake){
		this.perStake = perStake/100;
	}
	
	public double getRisk(){
		return omegaMeasure + Math.exp(-100000/totalValue);
	}
	
	public double getValue(){
		return this.totalValue * this.perStake;
	}
	
	public double getAnnualReturn(){
		return baseRateOfReturn *  this.totalValue + (4 * this.quarterlyDividend);
	}
	
	public double getReturnRate(){
		return (getAnnualReturn()/totalValue) * 100;
	}
	
	
} //end of PrivateInvestment class
