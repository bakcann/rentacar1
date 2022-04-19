package turkcell.rentacar1.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetListCardDto {
	
	private String cardOwnerName;
	private String cardNumber;
	private int cardCvvNumber;
	
	private int customerId;

}
