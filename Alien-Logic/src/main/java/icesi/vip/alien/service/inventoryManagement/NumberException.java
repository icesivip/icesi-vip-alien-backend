package icesi.vip.alien.service.inventoryManagement;

import java.text.DecimalFormat;

public class NumberException extends Exception{
	
	private double value;
	private String reason;
	private String param;
	
	public NumberException(String parameter, String complement, double value) {
		param = parameter;
		reason = complement;
		this.value = value;
	}
	
	@Override
	public String getMessage() {
		DecimalFormat df = new DecimalFormat();
		df.setDecimalSeparatorAlwaysShown(false);
		return param + " " + reason + " " + df.format(value);
	}
}
