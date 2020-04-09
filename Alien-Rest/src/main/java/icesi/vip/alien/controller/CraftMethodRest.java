package icesi.vip.alien.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import icesi.vip.alien.dto.craftMethod.CraftContainer;
import icesi.vip.alien.dto.craftMethod.CraftResponse;

@RestController
@RequestMapping("/api/craftMethodModule")
@CrossOrigin(origins = "*")
public class CraftMethodRest {
	
	@RequestMapping(value="/craft", method=RequestMethod.POST)
	public CraftResponse craftMethod(@RequestBody CraftContainer container) throws Exception {
		return container.compute();
	}
	
}
