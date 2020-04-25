package icesi.vip.alien.service.inventoryManagement;

import java.sql.Time;
import icesi.vip.alien.service.inventoryManagement.InventoryDetDemand.TimeUnit;

public class PeriodicRevRS implements InventorySystem {

	private short reviewTime;
	private int availableInventory;
	private float demand;
	private float leadTime;
	private float serviceLevel;
	private float standardDeviationDemand;
	private TimeUnit timeUnit;
	
	public double calculateSafetyStock () {
		return InventoryDetDemand.calculateZ(serviceLevel) * calculateDeviationRPluSL();
	}
	
	private double calculateDeviationRPluSL () {
		return Math.sqrt(Math.pow(standardDeviationDemand, 2)* (reviewTime + leadTime));
	}
	
	@Override
	public double calculateQuantity() {
		return demand*(reviewTime + leadTime) + InventoryDetDemand.calculateZ(serviceLevel) * calculateDeviationRPluSL() - availableInventory;
	}

	public short getReviewTime() {
		return reviewTime;
	}

	public void setReviewTime(short reviewTime) {
		this.reviewTime = reviewTime;
	}

	public int getAvailableInventory() {
		return availableInventory;
	}

	public void setAvailableInventory(int availableInventory) {
		this.availableInventory = availableInventory;
	}

	public float getDailyDemand() {
		return demand;
	}

	public void setDailyDemand(float demand) {
//		switch (timeUnit) {
//		case Annual:
//			this.demand = demand/365;
//			break;
//		case Biannual:
//			this.demand = (float) (demand/182.5);
//			break;
//		case Quarterly:
//			this.demand = demand/91;
//			break;
//		case Bimonthly:
//			this.demand = demand/61;
//			break;
//		case Monthly:
//			this.demand = demand/30;
//			break;
//		case Weekly:
//			this.demand = demand/7;
//			break;
//		case Daily:
//			this.demand = demand;
//			break;
//		default:
//			break;
//		}
		this.demand = demand;
	}

	public float getLeadTime() {
		return leadTime;
	}

	public void setLeadTime(float leadTime) {
		this.leadTime = leadTime;
	}

	public float getServiceLevel() {
		return serviceLevel;
	}

	public void setServiceLevel(float serviceLevel) {
		this.serviceLevel = serviceLevel/100;
	}

	public float getStandardDeviationDailyDemand() {
		return standardDeviationDemand;
	}

	public void setStandardDeviationDailyDemand(float standardDeviationDemand) {
//		switch (timeUnit) {
//		case Annual:
//			this.standardDeviationDemand = standardDeviationDemand/365;
//			break;
//		case Biannual:
//			this.standardDeviationDemand = (float) (standardDeviationDemand/182.5);
//			break;
//		case Quarterly:
//			this.standardDeviationDemand = standardDeviationDemand/91;
//			break;
//		case Bimonthly:
//			this.standardDeviationDemand = standardDeviationDemand/61;
//			break;
//		case Monthly:
//			this.standardDeviationDemand = standardDeviationDemand/30;
//			break;
//		case Weekly:
//			this.standardDeviationDemand = standardDeviationDemand/7;
//			break;
//		case Daily:
//			this.standardDeviationDemand = standardDeviationDemand;
//			break;
//		default:
//			break;
//		}
		this.standardDeviationDemand = standardDeviationDemand;
	}

}
