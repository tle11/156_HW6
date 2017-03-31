package assignment_2;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class PortfolioReport {

	public static void main(String [] args){

		StringBuilder sb = new StringBuilder();

		ArrayList<Person> pList = Crud.getPersonData(new ArrayList<Person>());
		ArrayList<Asset> aList = Crud.getAssetData(new ArrayList<Asset>());
		ArrayList<Portfolio> portList = Crud.getPortfolioData(new ArrayList<Portfolio>(), pList, aList);

			//prints out portfolio summary report		
			sb.append("Portfolio Summary Report\n");
			sb.append(("=================================================================================================================================================\n"));
			sb.append(String.format("%-12s %-20s %-34s %-9s %-13s %-23s %-22s %-13s\n", 
					"Portfolio", "Owner", "Manager", "Fees", "Commissions", "Weighted Risk", "Return", "Total"));

			double totalVal = 0.0, totalRet = 0.0, totalCom = 0.0, totalRisk = 0.0, totalFees = 0.0 ;
			
			for(Portfolio p: portList){
				totalVal += p.calcTotal();
				totalRet += p.calcTotalReturn();
				totalCom += p.calcCommission();
				totalRisk += p.calcRisk();
				totalFees += p.calcFee();
				
				if (p.owner != null){
					sb.append(String.format("%-12s %-20s %-22s %5s %10.2f %5s %10.2f %15.4f %5s %10.2f %5s %15.2f\n", 
							p.pCode, p.owner.getFullName(), p.manager.getFullName(), "$", p.calcFee(), "$", p.calcCommission(), p.calcRisk(), "$", p.calcTotalReturn(), "$", p.calcTotal()));
				}
			}
			
			sb.append("\n\t \t \t\t\t\t   ==============================================================================================\n");
			
			sb.append(String.format("%56s %5s %10.2f %5s %10.2f %15.4f %5s %10.2f %5s %15.2f", "Total", "$", totalFees, "$", totalCom,  totalRisk, "$", totalRet, "$", totalVal));
		
			sb.append("\n");
			sb.append("Portfolio Details");
			sb.append("========================================================="
					+ "=======================================================================\n\n\n");

			for(Portfolio p: portList){
				sb.append("Portfolio: " + p.pCode + "\n");
				sb.append("-------------------------------------------\n");
				if (p.owner != null ){
					sb.append(String.format("%-15s %-15s\n", "Owner:", p.owner.getFullName()));
				}

				if (p.manager != null){
					sb.append(String.format("%-15s %-15s\n", "Manager:", p.manager.getFullName()));
				}
				
				if(p.bnf != null){
					sb.append(String.format("%-15s %-15s\n", "Beneficiary: ", p.bnf.getFullName()));
				}

				sb.append("\n\nAssets\n");
				sb.append(String.format("%-12s %-25s %-30s %-8s %14s %21s\n", 
						"Code", "Asset", "Return Rate", "Risk", "Annual Return", "Value"));

				for(Asset a: p.aList){
					sb.append(String.format("%-12s %-25s %-30.2f %-9.2f %5s %7.2f %5s %15.2f\n", a.code, a.label, 
							a.getReturnRate(), a.getRisk(), "$", a.getAnnualReturn(), "$", a.getValue()));
				}
				sb.append(String.format("%130s\n","--------------------------------------------------------------------------"));
				sb.append(String.format("%64s %9.2f %5s %12.2f %5s %15.2f \n", "Totals \t", p.calcTotalRisk(), "$", p.calcTotalReturn(), "$", p.calcTotal()));
				sb.append("\n");
			}
			
			
		System.out.println(sb);
	}
}

