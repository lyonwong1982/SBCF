/**
 * The diversion-voucher
 */
package entities;

import java.util.ArrayList;

/**
 * @author Liang Wang
 *
 */
public class DiversionVoucher {
//	The identifier of this diversion-voucher.
	private int id;
//	The diversion-points in this voucher.
	private int dp;
//	The diversion-points for rewards.
	private int rw;
//	The total discount of this voucher.
	private int dc;
//	The issuer of this voucher.
	private Store issuer;
//	The scope of this voucher.
	private ArrayList<Store> scope;
	
	public DiversionVoucher(int id, int dp, int rw, int dc, Store issuer, ArrayList<Store> scope) {
		this.id = id;
		this.dp = dp;
		this.rw = rw;
		this.dc = dc;
		this.issuer = issuer;
		this.scope = scope;
	}
	
	public void setId(int id) {this.id = id;}
	
	public int getId() {return this.id;}
	
	public void setDp(int dp) {this.dp = dp;}
	
	public int getDp() {return this.dp;}
	
	public void setRw(int rw) {this.rw = rw;}
	
	public int getRw() {return this.rw;}
	
	public void setDc(int dc) {this.dc = dc;}
	
	public int getDc() {return this.dc;}
	
	public void setIssuer(Store issuer) {this.issuer = issuer;}
	
	public Store getIssuer() {return this.issuer;}
	
	public void setScope(ArrayList<Store> scope) {this.scope = scope;}
	
	public ArrayList<Store> getScope() {return this.scope;}
}
