package com.smartmssa.bank_discount;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MegaOfferController {
	
	@Autowired
	private DroolConfig droolConfig;

	@PostMapping("/order")
	public Order orderNow(@RequestBody Order order) {
		return droolConfig.orderDiscount(order);
	}
	
	@PostMapping("/creditercompte")
	public CrediterCompte creditercompte(@RequestBody CrediterCompte credit) {
		return droolConfig.compteDiscount(credit);
	}

}
