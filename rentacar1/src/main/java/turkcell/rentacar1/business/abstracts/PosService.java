package turkcell.rentacar1.business.abstracts;

public interface PosService {

	public boolean payments( String cardNumber,String cardOwnerName, int cardCvvNumber);

}
