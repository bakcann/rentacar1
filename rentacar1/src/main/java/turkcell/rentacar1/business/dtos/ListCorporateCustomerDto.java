package turkcell.rentacar1.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListCorporateCustomerDto {
	
	private int customerId;
	
	private String email;
	
	private String password;
	
	private String companyName;
	
	private String taxNumber;

}
