package icesi.vip.alien.service.inventoryManagement;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.el.stream.Stream;

public class InventoryForDeterministicDemand {

	private static final String CONTINUOUS_SQ = "(s,Q) Continuous review fixed-order-quantity system";
	private static final String CONTINUOUS_SS = "(s,S) Continuous review order-up-to system";
	private static final String PERIODIC_RS = "(R,S) Periodic review fixed-order interval system";

	private InventorySystem invSystem;

	public InventoryForDeterministicDemand(String system) {
		if (system.equals(CONTINUOUS_SQ))
			invSystem = new ContinuousRevSQ();
		else if (system.equals(CONTINUOUS_SS))
			invSystem = new ContinuousRevSS();
		else if (system.equals(PERIODIC_RS))
			invSystem = new PeriodicRevRS();
	}

	public void configureSQ(int annualDemand, float orderCost, float keepingCost, int leadTime, float serviceLevel,
			float standardDeviationDiaryDemand, float standardDeviationLeadTime, float businessDays) {
		ContinuousRevSQ sq = (ContinuousRevSQ) invSystem;
		sq.setAnnualDemand(annualDemand);
		sq.setBusinessDays(businessDays);
		sq.setKeepingCost(keepingCost);
		sq.setLeadTime(leadTime);
		sq.setOrderCost(orderCost);
		sq.setServiceLevel(serviceLevel);
		sq.setStandardDeviationDiaryDemand(standardDeviationDiaryDemand);
		sq.setStandardDeviationLeadTime(standardDeviationLeadTime);
	}

	public void configureSS(float maxLevelInventory, float minLevelInventory) {
		ContinuousRevSS ss = (ContinuousRevSS) invSystem;
		ss.setMaxLevelInventory(maxLevelInventory);
		ss.setMinLevelInventory(minLevelInventory);
	}

	public void configureRS(int reviewTime, int availableInventory, float diaryDemand, int leadTime, float serviceLevel,
			float standardDeviationDiaryDemand) {
		PeriodicRevRS rs = (PeriodicRevRS) invSystem;
		rs.setAvailableInventory(availableInventory);
		rs.setDiaryDemand(standardDeviationDiaryDemand);
		rs.setLeadTime(leadTime);
		rs.setReviewTime(reviewTime);
		rs.setServiceLevel(serviceLevel);
		rs.setStandardDeviationDiaryDemand(standardDeviationDiaryDemand);
	}

	public double calculateQuantity() {
		return invSystem.calculateQuantity();
	}

	public double calculateSecurityStock() {
		if (invSystem instanceof ContinuousRevSQ) {
			ContinuousRevSQ sq = (ContinuousRevSQ) invSystem;
			return sq.calculateSecurityStock();
		} else if (invSystem instanceof PeriodicRevRS) {
			PeriodicRevRS rs = (PeriodicRevRS) invSystem;
			return rs.calculateSecurityStock();
		} else
			return -1;
	}

	public double calculateReorderPoint() {
		if (invSystem instanceof ContinuousRevSQ) {
			ContinuousRevSQ sq = (ContinuousRevSQ) invSystem;
			return sq.calculateReOrderPoint();
		} else
			return -1;
	}

	public double calculateZ(double sL) {
		NormalDistribution d = new NormalDistribution();
		return d.inverseCumulativeProbability(sL);
	}

}
