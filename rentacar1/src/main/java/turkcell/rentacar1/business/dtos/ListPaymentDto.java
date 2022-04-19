package turkcell.rentacar1.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListPaymentDto {
	
	private int paymentId;
	
	private int invoiceId;
	private int orderedId;
	private int cardId;

}
