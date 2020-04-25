package icesi.vip.alien.service.inventoryManagement;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.el.stream.Stream;

import lombok.extern.java.Log;

@Log
public class InventoryDetDemand {

	public static final String CONTINUOUS_SQ = "(s,Q)";
	public static final String CONTINUOUS_SS = "(s,S)";
	public static final String PERIODIC_RS = "(R,S)";

	public enum TimeUnit {
		Annual, Biannual, Quarterly, Bimonthly, Monthly, Weekly, Daily
	}
	
	private InventorySystem invSystem;

	public InventoryDetDemand(String system) {
		if (system.equals(CONTINUOUS_SQ))
			invSystem = new ContinuousRevSQ();
		else if (system.equals(CONTINUOUS_SS))
			invSystem = new ContinuousRevSS();
		else if (system.equals(PERIODIC_RS))
			invSystem = new PeriodicRevRS();
	}

	public void configureSQ(float annualDemand, float orderCost, float keepingCost, float leadTime, float serviceLevel,
			float standardDeviationDemand, float standardDeviationLeadTime, short businessDays, TimeUnit timeUnit) {
		ContinuousRevSQ sq = (ContinuousRevSQ) invSystem;
		sq.setTimeUnit(timeUnit);
		sq.setBusinessDays(businessDays);
		sq.setAnnualDemand(annualDemand);
		sq.setKeepingCost(keepingCost);
		sq.setLeadTime(leadTime);
		sq.setOrderCost(orderCost);
		sq.setServiceLevel(serviceLevel);
		sq.setStandardDeviationDailyDemand(standardDeviationDemand);
		sq.setStandardDeviationLeadTime(standardDeviationLeadTime);
	}

	public void configureSS(int maxLevelInventory, int minLevelInventory) throws Exception {
		ContinuousRevSS ss = (ContinuousRevSS) invSystem;
		if(maxLevelInventory-minLevelInventory >0) {
		ss.setMaxLevelInventory(maxLevelInventory);
		ss.setMinLevelInventory(minLevelInventory);
		} else throw new Exception("Max level inventory can't be smaller than min level inventory");
	}

	public void configureRS(short reviewTime, int availableInventory, float demand, float leadTime, float serviceLevel,
			float standardDeviationDemand, TimeUnit dT) throws Exception {
		if(standardDeviationDemand< demand) {
		PeriodicRevRS rs = (PeriodicRevRS) invSystem;
		rs.setAvailableInventory(availableInventory);
		rs.setDailyDemand(demand);
		rs.setLeadTime(leadTime);
		rs.setReviewTime(reviewTime);
		rs.setServiceLevel(serviceLevel);
		rs.setStandardDeviationDailyDemand(standardDeviationDemand);
		} else throw new Exception("Standard deviation daily demand can't be greater than demand");
	}

	public double getQuantity() {
		return invSystem.calculateQuantity();
	}

	public double getSafetyStock() {
		if (invSystem instanceof ContinuousRevSQ) {
			ContinuousRevSQ sq = (ContinuousRevSQ) invSystem;
			return sq.calculateSafetyStock();
		} else if (invSystem instanceof PeriodicRevRS) {
			PeriodicRevRS rs = (PeriodicRevRS) invSystem;
			return rs.calculateSafetyStock();
		} else
			return -1;
	}

	public double getReorderPoint() {
		if (invSystem instanceof ContinuousRevSQ) {
			ContinuousRevSQ sq = (ContinuousRevSQ) invSystem;
			return sq.calculateReorderPoint();
		} else
			return -1;
	}

	public static double calculateZ(double sL) {
		NormalDistribution d = new NormalDistribution();
		return d.inverseCumulativeProbability(sL);
	}

	public InventorySystem getInvSystem() {
		return invSystem;
	}
}
