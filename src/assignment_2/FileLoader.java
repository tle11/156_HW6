package assignment_2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class FileLoader {

	//method parse reads Person.dat
	public ArrayList<Person> parsePersons(String fileName){
		ArrayList<Person> pList = new ArrayList<Person>();

		Scanner s = null;
		try {
			s = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} 

		int numRecords = Integer.parseInt(s.nextLine());
		while(s.hasNext()) {
			String line = s.nextLine();
			String tokens[] = line.split(";");
			String code = tokens[0];
			String broker[] = tokens[1].split(",");

			// you need to initialize the variables on an outer scope to be accessed later
			String personType = "";
			String identifier = "";
			double fee = 0.0;
			double commission = 0.0;

			if(broker.length > 1) {	
				personType = broker[0];
				identifier = broker[1];
			} 

			String name[] = tokens[2].split(",");
			String lastName = name[0].trim(); //removing empty spaces with .trim()
			String firstName = name[1].trim();   
			String address[] = tokens[3].split(",");
			String street = address[0];
			String city = address[1];
			String state = address[2];
			String zipCode = address[3];
			String country = address[4];
			String email [] = null;

			if(tokens.length > 4) {
				email = tokens[4].split(",");
			}
			//Address a = new Address(street, city, state, zipCode, country); //creates Address object
			//Person p = new Person(code, personType, identifier, lastName, firstName, a, email); //creates Person object
			//pList.add(p); //adds object to list
		}
		return pList;
	}//end of parsePerson method

	//method identifies the type of assets in Assets.dat
	public ArrayList<Asset> parseAsset(String fileName){
		ArrayList<Asset> aList = new ArrayList<Asset>();

		Scanner s = null;
		try {
			s = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} 

		int numRecords = Integer.parseInt(s.nextLine());
		while(s.hasNext()) {
			String line = s.nextLine();
			String tokens [] = line.split(";");

			if(tokens[1].equals("D")){
				parseDA(aList, tokens);
			} //identifies for direct account

			else if (tokens[1].equals("S")){
				parseStocks(aList, tokens);
			} //identifies for stocks

			else if(tokens[1].equals("P")){  
				parsePI(aList, tokens);
			} // identifies for private investments

		}
		return aList; 
	} //end of parseAsset method

	//method parse reads the deposit account items in Assets.dat
	public void parseDA(ArrayList<Asset>aList, String tokens[]){
		String code = tokens[0];
		String type = tokens[1];
		String label = tokens[2];
		double apr = Double.parseDouble(tokens[3]);

		DepositAccount da = new DepositAccount(code, label, type, apr); //creates a new DirectAccount object
		aList.add(da); //adds object to list

	} //end of parseDA method

	//method parse reads the stock items in Assets.dat
	public void parseStocks(ArrayList<Asset> aList, String tokens[]){
		String code = tokens[0];
		String type = tokens[1];
		String label = tokens[2];
		double quartDiv = Double.parseDouble(tokens[3]);
		double baseROR = Double.parseDouble(tokens[4]);
		double betaMeasure = Double.parseDouble(tokens[5]);
		String stockSym = tokens[6];
		double sharePrice = Double.parseDouble(tokens[7]);

		Stock s = new Stock(code, type, label, quartDiv, baseROR, betaMeasure, stockSym, sharePrice); //creates new Stocks object
		aList.add(s); //add Stocks object to list

	} //end of parseStocks method

	//method parse reads the private investments items in Assets.dat
	public void parsePI(ArrayList<Asset> aList, String tokens[]){
		String code = tokens[0];
		String type = tokens[1];
		String label = tokens[2];
		double quartDiv = Double.parseDouble(tokens[3]);
		double baseROR = Double.parseDouble(tokens[4]);
		double omMeasure = Double.parseDouble(tokens[5]);
		double totVal = Double.parseDouble(tokens[6]);

		PrivateInvestment pInvest = new PrivateInvestment(code, type, label, quartDiv, baseROR,omMeasure, totVal);
		aList.add(pInvest);

	} //end of parsePI method

	public ArrayList<Portfolio> parsePortfolio(String fileName, ArrayList<Person>pLists,ArrayList<Asset> aLists){
		ArrayList<Portfolio> portList = new ArrayList<Portfolio>();
		Scanner s = null;
		try {
			s = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} 

		int numRecords = Integer.parseInt(s.nextLine());
		while(s.hasNextLine()){
			String line = s.nextLine();
			String tokens [] = line.split(";");
			int size = tokens.length;
			String pCode = tokens[0];
			String oCode = tokens[1];
			String mCode = tokens[2];

			String benCode = ""; //placeholder var
			/*
			 * Validation to ensure that beneficiary code exists
			 */
			if (size > 3 && tokens[3].length() > 0){
				benCode = tokens[3];
			}

			ArrayList<Asset> tempList = new ArrayList<Asset>();
			String aInfo[] = null;
			if(size > 4 && tokens[4].length() > 0){
				aInfo = tokens[4].split(",");
				/*
				 * For each aInfo, we loop
				 * through the aLists (list of assets) to find the 
				 * object that we need, then add that object to the temp
				 * list.  We will instantiate the Portfolio object using 
				 * temp list. 
				 */

				for(String aCode: aInfo) {
					if (aCode.length() > 0) {
						String assetCode = aCode.split(":")[0];
						Double assetNum = Double.parseDouble(aCode.split(":")[1]);

						for(Asset a: aLists){
							if(a.getCode().equals(assetCode)){
								if(a instanceof DepositAccount){
									DepositAccount temp = new DepositAccount((DepositAccount) a); //use copy constructor since we're reassigning values.
									temp.setTotBal(assetNum);
									tempList.add(temp);
								} 
								else if (a instanceof Stock){
									Stock temp = new Stock((Stock) a);
									temp.setNumShare(assetNum);
									tempList.add(temp);
								} 
								else if(a instanceof PrivateInvestment){
									PrivateInvestment temp = new PrivateInvestment((PrivateInvestment) a);
									temp.setPerStake(assetNum);
									tempList.add(temp);
								}
							}
						}
					}
				}
			}

			Person owner = null; 			
			Person manager = null;
			Person beneficiary = null;
			/*
			 *for every person in pLists, if each person's code equals
			 *to pCode, then we set it to the object
			 */
			for(Person p: pLists){
				if(p.getCode().equals(oCode)){ //use copy constructor in case we have to reassign values.
					owner= new Person(p);
				}
				if(p.getCode().equals(mCode)) {
					manager= new Person(p);
				}
				if(p.getCode().equals(benCode)){
					beneficiary = new Person(p);
				}
			}
			Portfolio p = new Portfolio(pCode, owner, manager, beneficiary, tempList);
			portList.add(p);	
		}


		return portList;	
	}
}//end of FileLoader class


