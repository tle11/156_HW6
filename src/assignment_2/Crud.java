package assignment_2;

import java.util.ArrayList;
import java.util.List;
import assignment_2.DatabaseInfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Crud {


	public static ArrayList<Person> getPersonData(ArrayList<Person> person){
		//ArrayList<Person> p = new ArrayList<Person>();
		//retrieves Person info
		String personQuery = "SELECT p.personCode, p.brokerType, p.brokerIdentifier, "
				+ "p.lastName, p.firstName, a.addressStreet, a.city, a.state, "
				+ "a.zipCode, a.country, e.emailAddress from Person as p "
				+ "JOIN Address as a on a.personId = p.personId "
				+ "JOIN Email as e on e.personId = p.personId;";
		
		ResultSet rs = null;
		try {
			rs = DatabaseAccess.executeSelect(personQuery);

			while(rs.next()){
				Address a = new Address(rs.getString("addressStreet"),
						rs.getString("city"),
						rs.getString("state"),
						rs.getString("zipCode"),
						rs.getString("country"));
				String personCode = rs.getString("personCode");
				String type = rs.getString("brokerType");
				String identifier = rs.getString("brokerIdentifier");
				String lastName = rs.getString("lastName");
				String firstName = rs.getString("firstName");
				String email= rs.getString("emailAddress");
				String emailArr [] = (email == null ? null : email.split(","));
				Person p = new Person(personCode, type, identifier, lastName, firstName, a, emailArr);
				person.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DatabaseAccess.closeConnection(rs);
		return person;

	}

	
	public static ArrayList<Asset> getAssetData(ArrayList<Asset> asset){
		//Retrieves Asset information
		String assetQuery = "SELECT a.assetCode, a.assetType, a.label, a.apr,"
				+ "a.quarterlyDiv, a.baseROR, a.betaMeasure, a.stockSym, a.sharePrice, "
				+ "a.omegaMeasure, a.totalValue from Asset a; ";
		ResultSet rs = null;

		try{
			rs = DatabaseAccess.executeSelect(assetQuery);

			while(rs.next()){
				if(rs.getString("assetType").equalsIgnoreCase("S")){
					Stock s = new Stock(rs.getString("assetCode"),
							rs.getString("assetType"),
							rs.getString("label"),
							rs.getDouble("quarterlyDiv"),
							rs.getDouble("baseROR"),
							rs.getDouble("betaMeasure"),
							rs.getString("stockSym"),
							rs.getDouble("sharePrice"));
					
					asset.add(s);
				} else if(rs.getString("assetType").equalsIgnoreCase("D")){
					DepositAccount da = new DepositAccount(rs.getString("assetCode"),
							rs.getString("assetType"),
							rs.getString("label"),
							rs.getDouble("apr"));
					asset.add(da);
				} else {
					PrivateInvestment pa = new PrivateInvestment(rs.getString("assetCode"),
							rs.getString("assetType"),
							rs.getString("label"),
							rs.getDouble("quarterlyDiv"),
							rs.getDouble("baseROR"),
							rs.getDouble("omegaMeasure"),
							rs.getDouble("totalValue"));
					asset.add(pa);
				}
			}
		} catch (SQLException e){
			e.printStackTrace();
		}
		DatabaseAccess.closeConnection(rs);
		return asset;
	}

	public static ArrayList<Portfolio> getPortfolioData(ArrayList<Portfolio> portfolio, ArrayList<Person> personList, ArrayList<Asset> assetList){
		//Retrieves portfolios
		String portfolioQuery = "SELECT p.portCode, p.ownerCode, p.managerCode, "
				+ "p.benCode, a.assetCode, pa.assetValue from Portfolio p "
				+ "JOIN PortfolioAsset pa on pa.portfolioId = p.portfolioId "
				+ "JOIN Asset a on a.assetId = pa.assetId;";
		ResultSet rs = null;

		try{
			rs = DatabaseAccess.executeSelect(portfolioQuery);
			String pCode = "";
			Portfolio p = null;
			while(rs.next()){
				if(!rs.getString("portCode").equals(pCode)){
					
					pCode = rs.getString("portCode");
					if(p != null) portfolio.add(p);
					p = new Portfolio();

					p.pCode = rs.getString("portCode");
					String ownerCode = rs.getString("ownerCode");
					String managerCode = rs.getString("managerCode");
					String benCode = rs.getString("benCode");
					String assetCode = rs.getString("assetCode");
					Double assetValue = rs.getDouble("assetValue");

					p.aList = new ArrayList<Asset>();

					for(Person person: personList){
						if(ownerCode != null){
							if(ownerCode.equals(person.getCode())){
								p.owner = new Person(person);
							}
						}

						if(managerCode != null){
							if(managerCode.equals(person.getCode())){
								p.manager =  new Person(person);
							}
						}

						if(benCode != null){
							if(benCode.equals(person.getCode())){
								p.bnf = new Person(person);
							}
						}
					}

					if(!assetCode.equals("")){
						for(Asset asset: assetList){
							if(assetCode.equals(asset.code)){
								if(asset.getType().equalsIgnoreCase("S")){
									Stock stock = new Stock((Stock)asset);
									stock.setNumShare(assetValue);
									p.aList.add(stock);

								}
								else if(asset.getType().equalsIgnoreCase("D")){
									DepositAccount depositAccount = new DepositAccount((DepositAccount)asset);
									depositAccount.setTotBal(assetValue);
									p.aList.add(depositAccount);
								}
								else if(asset.getType().equalsIgnoreCase("P")){
									PrivateInvestment pi = new PrivateInvestment((PrivateInvestment)asset);
									pi.setPerStake(assetValue);
									p.aList.add(pi);
								}
							}
						}
					}

				}else if (pCode.equals(rs.getString("portCode"))){  //detecting if multiple records has the same code.  if so, we add the asset to previous assetlist
					String assetCode = rs.getString("assetCode");
					String assetValue = rs.getString("assetValue");

					if(assetCode != null){
						for(Asset asset: assetList){
							if(assetCode.equals(asset.code)){
								if(asset.getType().equalsIgnoreCase("S")){
									Stock stock = new Stock((Stock)asset);
									p.aList.add(stock);
								}
								else if(asset.getType().equalsIgnoreCase("D")){
									DepositAccount depositAccount = new DepositAccount((DepositAccount)asset);
									p.aList.add(depositAccount);
								}
								else if(asset.getType().equalsIgnoreCase("P")){
									PrivateInvestment pi = new PrivateInvestment((PrivateInvestment)asset);
									p.aList.add(pi);
								}
							}
						}
					} 
				}
				//Portfolio p = new Portfolio(portCode, ownerCode, managerCode, benCode, assetList);
			} if (p != null) portfolio.add(p);
		} catch(SQLException e){
			e.printStackTrace();
		}

		DatabaseAccess.closeConnection(rs);
		
		return portfolio;
	}
}

