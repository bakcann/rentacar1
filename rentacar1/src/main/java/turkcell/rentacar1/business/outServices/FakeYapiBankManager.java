package turkcell.rentacar1.business.outServices;

import org.springframework.stereotype.Service;

@Service
public class FakeYapiBankManager {
	
	public boolean makePayment(String cardNumber, String cardOwnerName, int cardCvvNumber) {
		
		System.out.println("Yapı bank ile ödenmiştir.");
		
		return true;
	}

}
