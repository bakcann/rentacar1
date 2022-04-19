package turkcell.rentacar1.business.requests.creates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import turkcell.rentacar1.business.requests.updates.UpdateRentalRequest;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentExtraRequest {
	
	private CreateOrderedAdditionalServiceRequestForTransactional createOrderedAdditionalServiceRequestForTransactional;
	
	private UpdateRentalRequest updateRentalRequest;
	
	private CreateCardRequest createCard;
	
	private CreateInvoiceRequest createInvoiceRequest;
	
	private boolean saveCard;

}
