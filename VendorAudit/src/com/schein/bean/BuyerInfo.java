/*
 * Created on Jun 20, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.schein.bean;

/**
 * @author Sridhar.Muppa
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class BuyerInfo extends User{
	private static final long serialVersionUID = 1L;
	private String goals;				//Buyer Goals
	/**
		 * Returns the Buyer Goals.
		 * @return String
		 */
	
	public BuyerInfo() {
			super();
			this.goals="";
		}
		
		public String getGoals() {
			return goals;
		}

		/**
		 * Sets the Buyer Goals.
		 * @param Buyer Goals to set
		 */
		public void setGoals(String goals) {
			this.goals = goals;
		}
		
}
