package icesi.vip.alien.service.neosServer;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


@Entity
public class AMPLModel {

	
	@NotEmpty(message="The model definition can't be empty")
	public String model;
	
	public String data;
	
	@NotEmpty(message="The commands definition can't be empty")
	public String commands;
	
	@Email(message="The provided email seems to be in a wrong format.")
	@NotEmpty(message="You must provide a valid email to the solver.")
	public String email;
	
	
}
