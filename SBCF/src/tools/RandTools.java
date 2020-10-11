/**
 * Tools about random number generation.
 */
package tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * @author Liang Wang
 *
 */
public class RandTools {
	
	public static boolean probabilityTest(double p, int v) {
		try {
			if(p > 1 || p < 0 || v < 0) {
				throw new Exception("Wrong argument(s)!");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		long freq = Math.round(p * Math.pow(10, v));
		Random r = new Random();
		int rand = r.nextInt((int)Math.pow(10, v));
		if(rand < freq) {return true;}
		else {return false;}
	}
	
	public static void main(String[] args) {
//		double p = 0.00003546;
//		int v = 4;
//		long freq=Math.round(p*Math.pow(10, v));
//		System.out.println(freq);
//		Random r = new Random();
//		HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>();
//		int k = 0;
//		for(int i = 0; i < 10000; i++) {
//			k = r.nextInt(10);
//			if(hm.containsKey(k)) {
//				hm.put(k, hm.get(k).intValue() + 1);
//			}
//			else {
//				hm.put(k,1);
//			}
//		}
//		for(Integer key : hm.keySet()) {
//			System.out.println(key + ":" + hm.get(key) + "&" + (hm.get(k).intValue() / 10000d));
//		}
//		int n = 0;
////		Random r = new Random();
//		for(int i = 0; i < 10; i++) {
////			int k = r.nextInt(10000);
//			if(RandTools.probabilityTest(0.1667, 4)) {//10%
//				n ++;
//			}
//		}
//		System.out.println(n);
//		System.out.println(n / 10d);
	}
}
