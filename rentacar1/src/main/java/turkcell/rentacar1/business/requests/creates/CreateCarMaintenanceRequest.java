package turkcell.rentacar1.business.requests.creates;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarMaintenanceRequest {
	
	@NotNull
	@Size(min=1, max=250)
	private String description;
	
	
	@NotNull
	@Positive
	private int carId;

}
