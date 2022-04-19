package turkcell.rentacar1.business.adapters;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import turkcell.rentacar1.business.abstracts.PosService;
import turkcell.rentacar1.business.outServices.FakeTarimBankManager;

@Service
@Primary
public class FakeTarimBankServiceAdapter implements PosService {

	@Override
	public boolean payments(  String cardNumber,String cardOwnerName, int cardCvvNumber) {
		
		FakeTarimBankManager fakeTarimBankManager =new FakeTarimBankManager();
		
		fakeTarimBankManager.makePayment(cardNumber, cardOwnerName, cardCvvNumber);
		
		return true;
	}

}
