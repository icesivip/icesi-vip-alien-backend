package icesi.vip.alien.service.inventoryManagement;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.el.stream.Stream;

public class InventoryForDeterministicDemand {

	private static final String CONTINUOUS_SQ = "(s,Q)";
	private static final String CONTINUOUS_SS = "(s,S)";
	private static final String PERIODIC_RS = "(R,S)";

	private InventorySystem invSystem;

	public InventoryForDeterministicDemand(String system) {
		if (system.equals(CONTINUOUS_SQ))
			invSystem = new ContinuousRevSQ();
		else if (system.equals(CONTINUOUS_SS))
			invSystem = new ContinuousRevSS();
		else if (system.equals(PERIODIC_RS))
			invSystem = new PeriodicRevRS();
	}

	public void configureSQ(float annualDemand, float orderCost, float keepingCost, float leadTime, float serviceLevel,
			float standardDeviationDailyDemand, float standardDeviationLeadTime, short businessDays) throws NumberException {
		ContinuousRevSQ sq = (ContinuousRevSQ) invSystem;
		sq.setAnnualDemand(annualDemand);
		sq.setBusinessDays(businessDays);
		sq.setKeepingCost(keepingCost);
		sq.setLeadTime(leadTime);
		sq.setOrderCost(orderCost);
		sq.setServiceLevel(serviceLevel);
		sq.setStandardDeviationDailyDemand(standardDeviationDailyDemand);
		sq.setStandardDeviationLeadTime(standardDeviationLeadTime);
	}

	public void configureSS(int maxLevelInventory, int minLevelInventory) throws NumberException {
		ContinuousRevSS ss = (ContinuousRevSS) invSystem;
		if(maxLevelInventory-minLevelInventory >0) {
		ss.setMaxLevelInventory(maxLevelInventory);
		ss.setMinLevelInventory(minLevelInventory);
		} else throw new NumberException("Max level inventory", "can't be smaller than", minLevelInventory);
	}

	public void configureRS(byte reviewTime, int availableInventory, float dailyDemand, float leadTime, float serviceLevel,
			float standardDeviationDailyDemand) throws NumberException {
		if(standardDeviationDailyDemand< dailyDemand) {
		PeriodicRevRS rs = (PeriodicRevRS) invSystem;
		rs.setAvailableInventory(availableInventory);
		rs.setDailyDemand(standardDeviationDailyDemand);
		rs.setLeadTime(leadTime);
		rs.setReviewTime(reviewTime);
		rs.setServiceLevel(serviceLevel);
		rs.setStandardDeviationDailyDemand(standardDeviationDailyDemand);
		} else throw new NumberException("Standard deviation daily demand","can't be greater than ",dailyDemand);
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

	public double calculateZ(double sL) {
		NormalDistribution d = new NormalDistribution();
		return d.inverseCumulativeProbability(sL);
	}

	public InventorySystem getInvSystem() {
		return invSystem;
	}
}
