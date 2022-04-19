package turkcell.rentacar1.business.requests.updates;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCorporateCustomerRequest {
	
	@NotNull
	@Positive
	private int customerId;
	
	@NotNull
	private String email;
	
	@NotNull
	private String password;
	
	@NotNull
	private String companyName;
	
	@NotNull
	private String taxNumber;

}
