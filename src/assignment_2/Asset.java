package assignment_2;

public abstract class Asset {
	
	protected String code;
	protected String label;
	protected String type;
	
	/*
	 * Need this empty construct because
	 * we're creating copy constructor in sub classes.
	 * Error will be thrown for declaration of 
	 * super constructor if this isn't used. 
	 */
	public Asset(){
		
	}
	
	//constructor for Asset class
	public Asset(String code, String type, String label) {
		this.code = code;
		this.label = label;
		this.type = type;
	}
	
	//getter method for code
	public String getCode(){
		return this.code;
	}
	
	//getter method for label
	public String getLabel(){
		return this.label;
	}
	
	//getter method for type
	public String getType(){
		return this.type;
	}
	
	/*
	 * Abstract methods: requiring sub classes 
	 * to implement their own.
	 */
	public abstract double getRisk();
	public abstract double getValue();
	public abstract double getAnnualReturn();
	public abstract double getReturnRate();
	public abstract double getPhi();
	
} 
