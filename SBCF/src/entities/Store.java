/**
 * Store
 */
package entities;

import java.util.HashMap;

/**
 * @author Liang Wang
 *
 */
public class Store {
//	Identifier of the store.
	private String id;
//	Statistics for the total customer flow of this store. 
//	The Key denotes the month, and the Value the number of the customers consuming at this store.
	private HashMap<Integer, Integer> cf;
//	Statistics for the customer flow diverted.
	private HashMap<Integer, Integer> cfd;
//	The probability of consuming at this store.
	private HashMap<Integer, Double> ppm;
	private double p;
	
	public Store(String id, double p){
		this.id = id;
		cf = new HashMap<Integer, Integer>();
		cfd = new HashMap<Integer, Integer>();
		ppm = new HashMap<Integer, Double>();
		for(int i = 1; i <= 12; i ++) {
			cf.put(i, 0);
			cfd.put(i, 0);
			ppm.put(i, 0d);
		}
		this.p = p;
	}
	/**
	 * Calculate the probability of consuming at this store according to the customer flow in the given month.
	 * @param month The month for statistics.
	 * @param m The total number of customers visiting the shopping mall in the given month.
	 * @param v The computational accuracy.
	 */
	public void calculateP(int month, int m, int v){
		try {
			if(cf.get(month) == null || m == 0 || v < 0) {
				throw new Exception("Wrong argument(s)!");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		p = (double)Math.round(((double)cf.get(month) / m) * Math.pow(10, v)) / Math.pow(10, v);
		ppm.put(month, p);
	}
	
	public void setId(String id) {this.id = id;}
	
	public String getId() {return this.id;}
	
	public void setCf(HashMap<Integer, Integer> cf) {this.cf = cf;}
	
	public HashMap<Integer, Integer> getCf() {return this.cf;}
	
	public void setCfd(HashMap<Integer, Integer> cfd) {this.cfd = cfd;}
	
	public HashMap<Integer, Integer> getCfd() {return this.cfd;}
	
	public void setPpm(HashMap<Integer, Double> ppm) {this.ppm = ppm;}
	
	public HashMap<Integer, Double> getPpm() {return this.ppm;}
	
	public void setP(double p) {this.p = p;}
	
	public double getP() {return this.p;}
	
	public static void main(String[] args) {
//		HashMap<Integer, Integer> cf =new HashMap<Integer, Integer>();
//		System.out.println(cf.get(3)/2);
//		System.out.println(Math.pow(10, 0));
//		double t=(double)1/6;
//		System.out.println((double)Math.round(t*10000)/10000);
//		Store s = new Store("s", 0);
//		s.getCf().put(3, 1);
//		s.calculateP(3,6,4);
//		System.out.println(s.getP());
//		System.out.println(1 / 10d);
	}
}
