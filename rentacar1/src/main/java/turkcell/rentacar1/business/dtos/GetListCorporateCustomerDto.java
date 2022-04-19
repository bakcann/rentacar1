package turkcell.rentacar1.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetListCorporateCustomerDto {
	
	private int customerId;
	
	private String email;
	
	private String companyName;
	
	private String taxNumber;

}
