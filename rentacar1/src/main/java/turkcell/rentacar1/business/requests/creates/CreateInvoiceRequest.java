package turkcell.rentacar1.business.requests.creates;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import turkcell.rentacar1.entities.concretes.Rental;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateInvoiceRequest {
	
	@NotNull
	private String invoiceNo;
	
	@NotNull
	@Positive
	private int customerId;
	
	
	private Rental rental;

}
