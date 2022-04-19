package turkcell.rentacar1.business.requests.creates;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAdditionalServiceRequest {
	
	@NotNull
	private String additionalServiceName;
	
	@NotNull
	@Positive
	private double additionalPrice;

}
