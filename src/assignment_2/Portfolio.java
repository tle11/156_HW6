package assignment_2;

import java.util.ArrayList;

public class Portfolio {
	
	public String pCode;
	public Person owner;
	public Person manager;
	public Person bnf;
	public ArrayList<Asset> aList;
	
	public Portfolio(){
		
	}
	
	public Portfolio(String pCode, Person owner, Person manager, Person bnf, ArrayList<Asset> aList) {
		this.pCode = pCode;
		this.owner = owner;
		this.manager = manager;
		this.bnf = bnf;
		this.aList = aList;
	}
	
	
	/*
	 * Calculate the total value of all assets
	 */
	public double calcSum(){
		double sum = 0.0;
		for(Asset a: aList){
			sum += a.getValue();
		}
		return sum;
	}
	
	/*
	 * Calculate aggregate risk
	 */
	public double calcRisk(){
		double risk = 0.0;
		for(Asset a: aList){
			risk += a.getPhi() * (a.getValue()/ calcSum());
		}
		return risk;
	}
	
	public double calcFee(){
		double fee = 0.0;
		if(this.manager.getPersonType().equals("E")){
			fee = 10.00 * aList.size();
		}
		else {
			fee = 50.00 * aList.size();
		}
		return fee;
	}
	
	public double calcCommission(){
		double commission = 0.0;
			for(Asset a: aList){
				if (this.manager.getPersonType().equals("E")){
					commission = a.getAnnualReturn() * .05;
				}
				else {
					commission = a.getAnnualReturn() * .02;
				}
			}
		return commission;
		}
	
	public double calcTotalReturn(){
		double totalReturn = 0.0;
		for(Asset a: aList){
			totalReturn += a.getAnnualReturn();
		}
		return totalReturn;
	}
	
	public double calcTotal(){
		double total = 0.0;
		for(Asset a: aList){
			total += a.getValue();
		}
		return total;
	}
	
	public double calcTotalRisk(){
		double total = 0.0;
		total += this.calcRisk();
		return total;
	}
	
	public double calcTotalFee(){
		double total = 0.0;
		total += this.calcFee();
		return total;
	}
	
	public double calcTotalCommission(){
		double total = 0.0;
		total += this.calcCommission();
		return total;
	}
	
	public void addAsset(Asset a){
		aList.add(a);
	}
}
