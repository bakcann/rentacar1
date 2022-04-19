package turkcell.rentacar1.business.requests.updates;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRentalRequest {
	
	@NotNull
	@Positive
	private int rentalId;
	
	@NotNull
	private LocalDate rentReturnDate;
	
	@NotNull
	@Positive
	private int returnCityId;
	
	@NotNull
	
	private double endKilometer;
}
