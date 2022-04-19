package turkcell.rentacar1.business.adapters;

import org.springframework.stereotype.Service;

import turkcell.rentacar1.business.abstracts.PosService;
import turkcell.rentacar1.business.outServices.FakeYapiBankManager;

@Service
public class FakeYapiBankServiceAdapter implements PosService{

	@Override
	public boolean payments( String cardNumber,String cardOwnerName, int cardCvvNumber) {
		
		FakeYapiBankManager fakeYapiBankManager = new FakeYapiBankManager();
		
		fakeYapiBankManager.makePayment(cardNumber, cardOwnerName, cardCvvNumber);
		
		return true;
	}
	

}
