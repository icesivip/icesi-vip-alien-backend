package icesi.vip.alien.service.inventoryManagement;

public class PeriodicRevRS extends InventorySystem {

	@Override
	public double calculateQuantity() {
		return Math.round(Math.sqrt(2*calculateAnnualDemand()*(getOrderCost()+getReviewCost())/getKeepingCost()));
	}

}
