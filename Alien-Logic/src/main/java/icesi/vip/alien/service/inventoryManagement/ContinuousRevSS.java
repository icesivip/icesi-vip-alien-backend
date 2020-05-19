package icesi.vip.alien.service.inventoryManagement;

import lombok.Getter;

public class ContinuousRevSS extends InventorySystem{
	
	@Getter
	private int effectiveInventory;
	
	@Override
	public double calculateQuantity() {
		return Math.round(Math.sqrt(2*calculateAnnualDemand()*(getOrderCost()+getReviewCost())/getKeepingCost()));
	}
	
	public double calculateQForSS() {
		return calculateMaxInventory() - calculateReorderPoint();
	}
}
