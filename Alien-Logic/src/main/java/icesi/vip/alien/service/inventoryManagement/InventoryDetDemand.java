package icesi.vip.alien.service.inventoryManagement;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import org.apache.commons.math3.distribution.NormalDistribution;

import lombok.Getter;

public class InventoryDetDemand {

	public static final String CONTINUOUS_SQ = "(s,Q)";
	public static final String CONTINUOUS_SS = "(s,S)";
	public static final String PERIODIC_RS = "(R,S)";

	public enum TimeUnit {
		Annual, Biannual, Quarterly, Bimonthly, Monthly, Weekly, Daily
	}

	@Getter
	private InventorySystem invSystem;
	@Getter
	private String messageSolution;

	public InventoryDetDemand(String system) {
		if (system.equals(CONTINUOUS_SQ))
			invSystem = new ContinuousRevSQ();
		else if (system.equals(CONTINUOUS_SS))
			invSystem = new ContinuousRevSS();
		else if (system.equals(PERIODIC_RS))
			invSystem = new PeriodicRevRS();
	}

	public void writeMessageSolution() {
		messageSolution = "<html><body>";
		if (invSystem instanceof ContinuousRevSQ) {
			ContinuousRevSQ sq = (ContinuousRevSQ) invSystem;
			messageSolution += "The politic of the Inventory Control System (s,Q) would be to order Q = "
					+ withoutDecimal(sq.calculateQuantity()) + " units";
			messageSolution += "<br>once the Efective Inventory level reduces to s = " + withoutDecimal(sq.calculateReorderPoint())
					+ " units.";
			messageSolution += "<br>Remember then the Safety Stock is SS= " + withoutDecimal(sq.calculateSafetyStock()) + " units.";
		} else if (invSystem instanceof PeriodicRevRS) {
			PeriodicRevRS rs = (PeriodicRevRS) invSystem;
			double revTime = rs.calculateReviewTime();
			messageSolution += "The politic of the Inventory Control System (R,S) would be to review the inventory every R = "
					+ withoutDecimal(revTime) + " " + rs.convertUnitTimeToContext(revTime);
			messageSolution += "<br>and to order an amount of units equal to S = " + withoutDecimal(rs.calculateMaxInventory())
					+ " minus the Effective Inventory at the review time.";
		} else if (invSystem instanceof ContinuousRevSS) {
			ContinuousRevSS ss = (ContinuousRevSS) invSystem;
			if (ss.getEffectiveInventory() <= ss.calculateReorderPoint()) {
				messageSolution += "The politic of the Inventory Control System (s,S) would be to order Q = "
						+ withoutDecimal(ss.calculateQForSS()) + " units";
				messageSolution += "<br>since the Effective Inventory reached the reorder point.";
			} else {
				messageSolution += "Since the Effective Inventory hasn't reached the reorder point,";
				messageSolution += "<br>there are no units to order at the moment.";
			}
		} else
			messageSolution += "The politic of the Inventory Control System has not been found";
		messageSolution += "</body></html>";
	}

	public static double calculateSafetyFactor(double sL) {
		NormalDistribution d = new NormalDistribution();
		double percentage = sL / 100;
		return Math.abs(d.inverseCumulativeProbability(1 - percentage));
	}
	
	/**
	 * Rounds a number to three decimal places
	 * 
	 * @param d
	 *            The number to be rounded
	 * @return Rounded value with the desired format
	 */
	public static double roundDouble(double d) {
		if(Math.abs(d)<0.0000001)
			return 0;
		DecimalFormatSymbols separadoresPersonalizados = new DecimalFormatSymbols();
		separadoresPersonalizados.setDecimalSeparator('.');
		DecimalFormat df = new DecimalFormat("#.###", separadoresPersonalizados);
		return Double.parseDouble(df.format(d));
	}
	
	private String withoutDecimal(double d) {
		DecimalFormat df = new DecimalFormat("#.###");
		df.setDecimalSeparatorAlwaysShown(false);
		return df.format(d);
	}
}
