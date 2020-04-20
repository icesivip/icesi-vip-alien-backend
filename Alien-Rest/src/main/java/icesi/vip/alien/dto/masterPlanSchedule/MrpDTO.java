package icesi.vip.alien.dto.masterPlanSchedule;

import java.util.List;

public class MrpDTO {
	
	private String periodicity;
	private List<ProductMrpDTO> products;
	
	public MrpDTO() {
	}
	
	public String getPeriodicity() {
		return periodicity;
	}
	public void setPeriodicity(String periodicity) {
		this.periodicity = periodicity;
	}
	public List<ProductMrpDTO> getProducts() {
		return products;
	}
	public void setProduct(List<ProductMrpDTO> products) {
		this.products = products;
	}
	
	
	
}
