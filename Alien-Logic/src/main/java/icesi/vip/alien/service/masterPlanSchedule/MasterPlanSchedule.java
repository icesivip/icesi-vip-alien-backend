package icesi.vip.alien.service.masterPlanSchedule;

import java.util.ArrayList;
import java.util.List;

public class MasterPlanSchedule {
	
	public static final String LOTXLOT = "Lot for Lot";
	public static final String ECONOMIC_ORDER_QUANTITY= "Economic Order Quantity";
	public static final String PERIODS_OF_SUPPLY = "Periods Of Suply";
	public static final String PERIOD_ORDER_QUANTITY = "Period Order Quantity";
	public static final String LEAST_UNIT_COST = "Least Unit Cost";
	public static final String LEAST_TOTAL_COST = "Least Total Cost";
	
	public static final String ANNUAL = "Annual"; 
	public static final String MONTHLY = "Monthly";
	public static final String WEEKLY = "Weekly";
	public static final String DAILY = "Daily";
		
	private String lotSizingMethod;
	private int leadTime;
	private int initialStock;
	private int securityStock;
	private String productCode;
	private String productName;
	private double costArticle;
	private double preparationCost;
	private double maintenanceCost;
	private String periodicity;
	private int TPeriodOFSupply;
	
	public static LotSizingMethods lotSizingMethods;

	private List<Integer> bruteRequirements;
	private List<Integer> scheduledReceptions;
	private List<Integer> scheduledAvailableStock;
	private List<Integer> netRequirements;
	private List<Integer> planOrders;
	private List<Integer> planOrdersLxL;
	private List<Integer> releasedPlanOrders;
	
	public MasterPlanSchedule(String lotSizingMethod, int leadTime, int initialStock, int securityStock,
			String productCode, String productName, double costArticle, double preparationCost, double maintenanceCost,
			String periodicity, int TPeriodOFSupply) {
		this.lotSizingMethod = lotSizingMethod;
		this.leadTime = leadTime;
		this.initialStock = initialStock;
		this.securityStock = securityStock;
		this.productCode = productCode;
		this.setProductName(productName);
		this.costArticle = costArticle;
		this.preparationCost = preparationCost;
		this.maintenanceCost = maintenanceCost;
		this.periodicity = periodicity;
		this.TPeriodOFSupply = TPeriodOFSupply;
		
		lotSizingMethods = new LotSizingMethods();
		
		bruteRequirements = new ArrayList<Integer>();
		scheduledReceptions = new ArrayList<Integer>();
		scheduledAvailableStock = new ArrayList<Integer>();
		netRequirements = new ArrayList<Integer>();
		planOrders = new ArrayList<Integer>();
		planOrdersLxL = new ArrayList<>();
		releasedPlanOrders = new ArrayList<Integer>();
	}
	
	public void reset() {
		scheduledAvailableStock = new ArrayList<Integer>();
		netRequirements = new ArrayList<Integer>();
	}
	
	public void addBruteRequirement(int toBeAdded) {
		bruteRequirements.add(toBeAdded);
	}
	
	public void addScheduleReception(int toBeAdded) {
		scheduledReceptions.add(toBeAdded);
	}
	
	public void calculatePlanOrders() {
		switch(lotSizingMethod){
		case(ECONOMIC_ORDER_QUANTITY):/*No*/
			planOrders = lotSizingMethods.systemEconomicOrderQuantiy(periodicity, bruteRequirements, planOrdersLxL, costArticle, preparationCost, maintenanceCost);
			break;
		case(PERIODS_OF_SUPPLY):/*No*/
			planOrders = lotSizingMethods.systemPeriodsOfSupply(TPeriodOFSupply, bruteRequirements);
			break;
		case(PERIOD_ORDER_QUANTITY):/*Sí*/
			planOrders = lotSizingMethods.systemPeriodOrderQuantity(periodicity, bruteRequirements, planOrdersLxL, costArticle, preparationCost, maintenanceCost);
			break;
		case(LEAST_UNIT_COST):/*Sí*/
			planOrders = lotSizingMethods.systemLeastUnitCost(planOrdersLxL, preparationCost, maintenanceCost, costArticle);
			break;
		case(LEAST_TOTAL_COST):/*Sí*/
			planOrders = lotSizingMethods.systemLeastTotalCost(planOrdersLxL, preparationCost, maintenanceCost, costArticle);
			break;
		}
	}
	
	//Ordenar inventario de seguridad si quedó menor
	
	//Quiza cambiar nombre de planOrder por lxl y usar un nuevo plan orders
	public void makeLXLMPS() {
		int actualNetReq = bruteRequirements.get(0) + securityStock - initialStock - scheduledReceptions.get(0);
		int actualStockAvailable = 0;
		if(actualNetReq > 0) {
			netRequirements.add(actualNetReq);
			planOrdersLxL.add(netRequirements.get(0));
		}else {
			planOrdersLxL.add(0);
			netRequirements.add(0);
		}
		actualStockAvailable = planOrdersLxL.get(0) + initialStock + scheduledReceptions.get(0) - bruteRequirements.get(0);
		scheduledAvailableStock.add(actualStockAvailable);
		for(int i = 1; i < bruteRequirements.size(); i++) {
			actualNetReq = bruteRequirements.get(i) + securityStock - scheduledAvailableStock.get(i-1) - scheduledReceptions.get(i);
			if(actualNetReq > 0) {
				netRequirements.add(actualNetReq);
				planOrdersLxL.add(netRequirements.get(i));
			}else {
				planOrdersLxL.add(0);
				netRequirements.add(0);
			}
			actualStockAvailable = planOrdersLxL.get(i) + 
					scheduledAvailableStock.get(i-1) + 
					scheduledReceptions.get(i) - 
					bruteRequirements.get(i);
			scheduledAvailableStock.add(actualStockAvailable);
		}
	}
	
	public void calculateReleasedPlanOrders() {
		for(int i = leadTime; i < planOrders.size(); i++) {
			releasedPlanOrders.add(planOrders.get(i));
		}
		for(int i = 0; i < leadTime; i++) {
			releasedPlanOrders.add(0);
		}
	}
	
	public void createMPS() {
		if(!lotSizingMethod.equals(LOTXLOT)) {
			if(!lotSizingMethod.equals(PERIODS_OF_SUPPLY)) {
				makeLXLMPS();
				reset();
			}
			calculatePlanOrders();
		}else {
			makeLXLMPS();
			planOrders = planOrdersLxL;
			calculateReleasedPlanOrders();
			return;
		}
		int actualNetReq = bruteRequirements.get(0) + securityStock - initialStock - scheduledReceptions.get(0);
		int actualStockAvailable = 0;
		if(actualNetReq > 0) {
			netRequirements.add(actualNetReq);
		}else {
			netRequirements.add(0);
		}
		actualStockAvailable = planOrders.get(0) + initialStock + scheduledReceptions.get(0) - bruteRequirements.get(0);
		scheduledAvailableStock.add(actualStockAvailable);
		for(int i = 1; i < bruteRequirements.size(); i++) {
			actualNetReq = bruteRequirements.get(i) + securityStock - scheduledAvailableStock.get(i-1) - scheduledReceptions.get(i);
			if(actualNetReq > 0) {
				netRequirements.add(actualNetReq);
			}else {
				netRequirements.add(0);
			}
			actualStockAvailable = planOrders.get(i) + 
					scheduledAvailableStock.get(i-1) + 
					scheduledReceptions.get(i) - 
					bruteRequirements.get(i);
			scheduledAvailableStock.add(actualStockAvailable);
		}
		calculateReleasedPlanOrders();
		
	}

	public List<Integer> getScheduledAvailableStock() {
		return scheduledAvailableStock;
	}

	public void setScheduledAvailableStock(List<Integer> scheduledAvailableStock) {
		this.scheduledAvailableStock = scheduledAvailableStock;
	}

	public List<Integer> getNetRequirements() {
		return netRequirements;
	}

	public void setNetRequirements(List<Integer> netRequirements) {
		this.netRequirements = netRequirements;
	}

	public List<Integer> getPlanOrders() {
		return planOrders;
	}

	public void setPlanOrders(List<Integer> planOrders) {
		this.planOrders = planOrders;
	}

	public int getTPeriodOFSupply() {
		return TPeriodOFSupply;
	}

	public void setTPeriodOFSupply(int tPeriodOFSupply) {
		TPeriodOFSupply = tPeriodOFSupply;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getLotSizingMethod() {
		return lotSizingMethod;
	}

	public void setLotSizingMethod(String lotSizingMethod) {
		this.lotSizingMethod = lotSizingMethod;
	}

	public int getLeadTime() {
		return leadTime;
	}

	public void setLeadTime(int leadTime) {
		this.leadTime = leadTime;
	}

	public int getInitialStock() {
		return initialStock;
	}

	public void setInitialStock(int initialStock) {
		this.initialStock = initialStock;
	}

	public int getSecurityStock() {
		return securityStock;
	}

	public void setSecurityStock(int securityStock) {
		this.securityStock = securityStock;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public double getCostArticle() {
		return costArticle;
	}

	public void setCostArticle(double costArticle) {
		this.costArticle = costArticle;
	}

	public double getPreparationCost() {
		return preparationCost;
	}

	public void setPreparationCost(double preparationCost) {
		this.preparationCost = preparationCost;
	}

	public double getMaintenanceCost() {
		return maintenanceCost;
	}

	public void setMaintenanceCost(double maintenanceCost) {
		this.maintenanceCost = maintenanceCost;
	}

	public String getPeriodicity() {
		return periodicity;
	}

	public void setPeriodicity(String periodicity) {
		this.periodicity = periodicity;
	}

	public List<Integer> getBruteRequirements() {
		return bruteRequirements;
	}

	public void setBruteRequirements(List<Integer> bruteRequirements) {
		this.bruteRequirements = bruteRequirements;
	}

	public List<Integer> getScheduledReceptions() {
		return scheduledReceptions;
	}

	public void setScheduledReceptions(List<Integer> scheduledReceptions) {
		this.scheduledReceptions = scheduledReceptions;
	}

	public List<Integer> getReleasedPlanOrders() {
		return releasedPlanOrders;
	}

	public void setReleasedPlanOrders(List<Integer> releasedPlanOrders) {
		this.releasedPlanOrders = releasedPlanOrders;
	}

	public List<Integer> getPlanOrdersLxL() {
		return planOrdersLxL;
	}

	public void setPlanOrdersLxL(List<Integer> planOrdersLxL) {
		this.planOrdersLxL = planOrdersLxL;
	}
	
	
}