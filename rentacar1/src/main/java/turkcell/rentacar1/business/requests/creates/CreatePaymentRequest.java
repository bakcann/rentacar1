package turkcell.rentacar1.business.requests.creates;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentRequest {
	
	private CreateOrderedAdditionalServiceRequestForTransactional createOrderedAdditionalServiceRequestForTransactional;
	
	private CreateRentalRequest createRentalRequest;
	
	private CreateCardRequest createCardRequest;
	
	private CreateInvoiceRequest createInvoiceRequest;
	
	private CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest;
	
	private boolean saveCard;

}
