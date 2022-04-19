package turkcell.rentacar1.business.outServices;

import org.springframework.stereotype.Service;

@Service
public class FakeTarimBankManager {
	
	public boolean makePayment(String cardNumber, String cardOwnerName, int cardCvvNumber) {
		
		System.out.println("Tarım bank ile ödenmiştir.");
		
		return true;
	}

}
