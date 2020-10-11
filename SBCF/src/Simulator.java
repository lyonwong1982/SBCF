import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import entities.Customer;
import entities.DiversionVoucher;
import entities.Store;
import tools.RandTools;
import tools.Reportor;

/**
 * Simulator for Balancing Customer Flow
 */

/**
 * @author Liang Wang
 *
 */
public class Simulator {
//	The lower bound of the number of customers
	public int lowerBound;
//	The stores
	public ArrayList<Store> stores;
//	The customers
	public ArrayList<Customer> customers;
//	The active customers
	public ArrayList<Customer> activeCustomers;
//	The scope of all diversion-vouchers.
	ArrayList<Store> scope;
//	The probability of old customers returning to the shopping mall.
	double rtn;
//	The probability of customers using diversion-vouchers to guide store selection.
	double qid;
	
	public Simulator() {
		lowerBound = 10000;
		stores = new ArrayList<Store>();
		customers = new ArrayList<Customer>();
		activeCustomers = new ArrayList<Customer>();
		scope = new ArrayList<Store>();
		rtn = 0.5;
		qid = 0.5;
	}
	
	/**
	 * Create a new store.
	 * @param id the Id of the new store.
	 * @param p the initiative probability of consuming at the store.
	 * @return return true if the new store is successfully created.
	 */
	public boolean createStore(String id, double p) {
		if("".equals(id)) {return false;}
		if(p>1 || p<0) {return false;}
		for(int i = 0; i < stores.size(); i ++) {
			if(stores.get(i).getId() == id) {return false;}
		}
//		double q = p;
//		for(int i = 0; i < stores.size(); i ++) {
//			q += stores.get(i).getP();
//		}
//		if(q > 1) {return false;}
		stores.add(new Store(id, p));
		return true;
	}
	/**
	 * Get the probability of visiting a given store.
	 * @param store the store to be visited.
	 * @param hasVoucher true denotes that the customer has at least one diversion-voucher.
	 * @return The desired probability.
	 */
	public double getVisitProb(Store store, boolean hasVoucher) {
		if(hasVoucher) {
			double pS = 0;
			for(int i = 0; i < scope.size(); i ++) {
				pS += scope.get(i).getP();
			}
			if(pS == 0) {return 0;}
			if(scope.contains(store)) {
				return store.getP() * qid / pS + store.getP() * (1 - qid);
			}
			else {
				return store.getP() * (1 - qid);
			}
		}
		else {
			return store.getP();
		}
	}
	
	public static void main(String[] args) {
//		Initiation
		Simulator smt = new Simulator();
//		Configuration: create four new stores.
		smt.createStore("s1", 0.05d);
		smt.createStore("s2", 0.1d);
		smt.createStore("s3", 0.2d);
		smt.createStore("s4", 0.7d);
//		Configuration: create the scope of all diversion-vouchers.
		for(int i = 0; i < 3; i++) {
			smt.scope.add(smt.stores.get(i));
		}
//		Configuration: the probability of old customers returning to the shopping mall.
		smt.rtn = 0.3;
//		Configuration: the probability of customers using diversion-vouchers to guide store selection.
		smt.qid = 0.5;
//		Configuration: the lower bound of the number of customers.
		smt.lowerBound = 10000;
		/*Start customer behavior*/
		for(int M = 1; M <= 12; M ++) {//Run every quarter.
//			Determine how many old customers will return to the shopping mall.
			smt.activeCustomers.clear();
			for(int i = 0; i < smt.customers.size(); i ++) {
				if(smt.customers.get(i).getIsOld()) {
					if(RandTools.probabilityTest(smt.rtn, 4)) {
						smt.activeCustomers.add(smt.customers.get(i));
					}
				}
			}
			int lb = smt.activeCustomers.size();
			if(lb < smt.lowerBound) {
				//Increase new customers, when the lower bound is not satisfied.
				for(int i = 0; i < smt.lowerBound - lb; i ++) {
					smt.customers.add(new Customer("c" + smt.customers.size()));
					smt.activeCustomers.add(smt.customers.get(smt.customers.size() - 1));
				}
			}
//			Visit stores by their probabilities.
			for(int i = 0; i < smt.activeCustomers.size(); i ++) {
				smt.activeCustomers.get(i).setIsOld(true);
				for(int j = 0; j < smt.stores.size(); j ++) {
					boolean hasVoucher = !smt.activeCustomers.get(i).getDv().isEmpty();
					DiversionVoucher dv = null;
					if(hasVoucher) {
						dv = smt.activeCustomers.get(i).getDv().get(0);
					}
					double prob = smt.getVisitProb(smt.stores.get(j), hasVoucher);
					if(RandTools.probabilityTest(prob, 4)) {//Consume successfully.
						smt.stores.get(j).getCf().put(M,smt.stores.get(j).getCf().get(M) + 1);
						if(smt.scope.contains(smt.stores.get(j))) {//The store can issue and collect diversion-voucher.
							if(hasVoucher) {
								if(dv.getIssuer() != smt.stores.get(j)) {//The current store is the issuer of the diversion-voucher.
//									Record the customer flow that is diverted.
									smt.stores.get(j).getCfd().put(M,smt.stores.get(j).getCfd().get(M) + 1);
								}
								if(RandTools.probabilityTest(smt.qid, 4)) {//The customer use a diversion-voucher.
									smt.activeCustomers.get(i).getDv().clear();
								}
							}
//							Be rewarded a diversion-voucher.
							smt.activeCustomers.get(i).getDv().add(new DiversionVoucher(0, 0, 0, 0, smt.stores.get(j), smt.scope));
						}
					}
				}
			}
//			Update p of each store.
			for(int i = 0; i < smt.stores.size(); i ++) {
				smt.stores.get(i).calculateP(M, smt.activeCustomers.size(), 4);
			}
//			Increase the lower Bound by 10%.
			smt.lowerBound = (int)Math.round(smt.lowerBound * 1.5);
		}
		/*End customer behavior*/
		/*Start to do statistics*/
		ArrayList<LinkedHashMap<String, String>> printContent = new ArrayList<LinkedHashMap<String,String>>();
		for(int M = 1; M <= 12; M ++) {
//			Calculate Sigma^2 in each month.
			double sum = 0;
			double sigma2 = 0;
			for(int i = 0; i < smt.stores.size(); i ++) {
				sum += smt.stores.get(i).getPpm().get(M);
			}
			for(int i = 0; i < smt.stores.size(); i ++) {
				sigma2 += Math.pow(smt.stores.get(i).getPpm().get(M) - sum, 2);
			}
			sigma2 = sigma2 / smt.stores.size();
			sigma2 = Math.round(sigma2 * Math.pow(10, 4)) / Math.pow(10, 4);
//			Create print content.
			LinkedHashMap<String, String> tuple = new LinkedHashMap<String, String>();
			tuple.put("Month", String.valueOf(M));
			tuple.put("Variance", String.valueOf(sigma2));
			for(int i = 0; i < smt.stores.size(); i ++) {
				tuple.put(smt.stores.get(i).getId() + "_" + "cf", String.valueOf(smt.stores.get(i).getCf().get(M)));
				tuple.put(smt.stores.get(i).getId() + "_" + "cfd", String.valueOf(smt.stores.get(i).getCfd().get(M)));
				tuple.put(smt.stores.get(i).getId() + "_" + "p", String.valueOf(smt.stores.get(i).getPpm().get(M)));
			}
			printContent.add(tuple);
		}
		Reportor.report(printContent, "Report");
		/*End to do statistics*/
	}
}
