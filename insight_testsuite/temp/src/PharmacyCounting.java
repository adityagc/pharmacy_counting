import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.math.BigDecimal;
import java.math.BigInteger;
/**
 * This is my submission for the Insight data engineering fellows coding challenge.
 * @author Aditya Chindhade 
 */
//TODO make your own test cases and test extensively!
public class PharmacyCounting {
	public void readCSV(String inputpath, String outputpath) {
		String line = "";
	    String csvSplitBy = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";

	    try (BufferedReader br = new BufferedReader(new FileReader(inputpath))) {
	    	Map<String, Drug> drugmap = new HashMap<String, Drug>();
	    	br.readLine();
	        while ((line = br.readLine()) != null) {
	            String[] prescription = line.split(csvSplitBy);
	            if (prescription.length != 5) continue;
//	            System.out.println(Arrays.toString(prescription));
	            /**
	             * Try to assign first element of row as first name.
	             */
	            String firstname;
	            try {
	                firstname = prescription[1].trim().toLowerCase();
	            } catch (Exception ex) {
	        	   firstname =  "";
	            }
	            /**
	             * Try to assign second element of row as last name.
	             */
	            String lastname;
	            try {
	            	lastname = prescription[2].trim().toLowerCase();
	            } catch (Exception ex) {
	            	lastname =  "";
	            }
	            /**
	             * Combining first name and last name for unique identification.
	             */
	            String prescriber = firstname + lastname;
	            /**
	             * Try to assign third element of row as drug name.
	             */
	            String drugname;
	            String drugkey;
	            if (prescription[3] != null) {
	            	drugname = prescription[3];
	            	drugkey = drugname.trim().toLowerCase();
	            } else {
	            	drugname = "";
	            	drugkey = "";
	            }
	            /**
	             * Try to assign fourth element of row as cost.
	             */
	            BigDecimal drugcost;
	            try {
	                drugcost = new BigDecimal(prescription[4]);
	            } catch (Exception ex) {
	                drugcost =  BigDecimal.ZERO;
	            }
	           /**
	            * Adding information to HashMap.
	            */
        	   if (!drugmap.containsKey(drugkey)) {
        		   Drug newdrug = new Drug(drugname);
        		   newdrug.setCost(drugcost);
        		   newdrug.setCount(BigInteger.ONE);
        		   newdrug.setPrescriber(prescriber);
        		   drugmap.put(drugkey, newdrug);
        		   
        	   } else {
        		   Drug olddrug = drugmap.get(drugkey);
        		   olddrug.setCost(olddrug.getCost().add(drugcost));
        		   if (olddrug.setPrescriber(prescriber) == true) {
        			   olddrug.setCount(olddrug.getCount().add(BigInteger.ONE));
        		   }
        	   }
	        }
//	        System.out.println(drugmap.values());
	        List<Drug> druglist = new ArrayList<Drug>(drugmap.values());
//	        System.out.println(druglist);
	        Collections.sort(druglist);
//	        System.out.println(druglist);
	        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputpath))){
	        	bw.append("drug_name,num_prescriber,total_cost");
	        	for (Drug currdrug : druglist) {
	        		bw.append(currdrug.toString());
	        	}
//	        	bw.append("\n");
	        } catch (IOException e) {
	        	System.out.println("Please enter valid output file name!");
	        }
	    } catch (IOException e) {
	        System.out.println("Please enter valid input file name!");
	    }
	}
	public static void main(String[] args) {
		final long startTime = System.currentTimeMillis();
		PharmacyCounting newcounter = new PharmacyCounting();
		if (args.length != 2) {
			System.out.println("Please run with the following arguments: input_file_path, output_file_path.");
			return;
		}
		newcounter.readCSV(args[0], args[1]);
		final long endTime = System.currentTimeMillis();
		System.out.println("Total execution time: " + (endTime - startTime));
	}
	private class Drug implements Comparable<Drug>{
		private Set<String> prescriber_set;
		private BigInteger prescription_count;
		private BigDecimal total_cost;
		private String drug_name;
		/**
		 * Constructor for Drug class.
		 * @param name of drug.
		 */
		public Drug(String name) {
			prescriber_set = new HashSet<String>();
			prescription_count = BigInteger.ONE;
			total_cost = BigDecimal.ZERO;
			drug_name = name;
		}
		
		public String getName() {
			return drug_name;
		}
		public BigDecimal getCost() {
			return total_cost;
		}
		public BigInteger getCount() {
			return prescription_count;
		}
		public Set<String> getPrescribers() {
			return prescriber_set;
		}
		public void setCost(BigDecimal cost) {
			total_cost = cost;
		}
		public void setCount(BigInteger newcount) {
			prescription_count = newcount;
		}
		public boolean setPrescriber(String newprescriber) {
			return prescriber_set.add(newprescriber);
		}
		@Override
		public String toString()	 {
			StringBuilder sb = new StringBuilder();
			sb.append("\n").append(drug_name).append(",").append(String.valueOf(prescription_count)).append(",").append(total_cost.toBigInteger().toString());
			return sb.toString(); 
		}
		public int compareTo(Drug other) {
			return -this.total_cost.compareTo(other.total_cost);
		}
	}
}
