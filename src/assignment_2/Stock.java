package assignment_2;

public class Stock extends Asset {

	private double quarterlyDividend;
	private double baseRateOfReturn;
	private double betaMeasure;
	private String stockSymbol;
	private double sharePrice;
	private double numShare;
	

	//constructor for stocks class
	public Stock(String code, String type, String label, double quarterlyDividend, double baseRateOfReturn,
			double betaMeasure, String stockSymbol, double sharePrice) {
		super(code, type, label);
		this.quarterlyDividend = quarterlyDividend;
		this.baseRateOfReturn = baseRateOfReturn/100.0;
		this.betaMeasure = betaMeasure;
		this.stockSymbol = stockSymbol;
		this.sharePrice = sharePrice;
	}
	
	/*
	 * Copy constructor, necessary since 
	 * values for assets changes during file parsing
	 */	
	public Stock(Stock s){
		this.code = s.code;
		this.type = s.type;
		this.label = s.label;
		this.quarterlyDividend = s.quarterlyDividend;
		this.baseRateOfReturn = s.baseRateOfReturn;
		this.betaMeasure = s.betaMeasure;
		this.stockSymbol = s.stockSymbol;
		this.sharePrice = s.sharePrice;
		
	}
	
	public double getRisk(){
		return this.betaMeasure;
	}
	
	public double getNumShare(){
		return this.numShare;
	}
	
	public void setNumShare(double numShare){
		this.numShare = numShare;
	}
	
	public double getValue(){
		return this.numShare * this.sharePrice;
	}
	
	public double getAnnualReturn(){
		return baseRateOfReturn * getValue() + (4 * (this.numShare * this.quarterlyDividend));
	}
	
	public double getReturnRate(){
		return (getAnnualReturn()/getValue()) * 100;
	}
	
	public double getPhi(){
		return this.betaMeasure;
	}
	
} 
