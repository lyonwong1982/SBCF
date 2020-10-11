/**
 * The customer.
 */
package entities;

import java.util.ArrayList;

/**
 * @author Liang Wang
 *
 */
public class Customer {
//	The identifier of this customer.
	private String id;
//	The diversion-vouchers.
	private ArrayList<DiversionVoucher> dv;
//	Is this customer an old one?
	private boolean isOld;
	
	public Customer(String id) {
		this.id = id;
		dv = new ArrayList<DiversionVoucher>();
		isOld = false;
	}
	
	public void setId(String id) {this.id = id;}
	public void setDv(ArrayList<DiversionVoucher> dv) {this.dv = dv;}
	public void setIsOld(boolean isOld) {this.isOld = isOld;}
	public String getId() {return this.id;}
	public ArrayList<DiversionVoucher> getDv() {return this.dv;}
	public boolean getIsOld() {return this.isOld;}
	
}
