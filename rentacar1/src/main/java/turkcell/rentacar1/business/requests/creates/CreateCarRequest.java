package turkcell.rentacar1.business.requests.creates;



import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarRequest {
	
	@NotNull
	private double dailyPrice;
	
	@NotNull
	@Positive
	private int modelYear;
	
	@NotNull
	private String description;
	
	@NotNull
	private double carKilometer;
	
	@NotNull
	@Positive
	private int brandId;
	
	@NotNull
	@Positive
	private int colorId;
	
	

}
