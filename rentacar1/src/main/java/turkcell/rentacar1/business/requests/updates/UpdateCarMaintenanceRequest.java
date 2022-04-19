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
public class UpdateCarMaintenanceRequest {
	
	@NotNull
	@Positive
	private int maintenanceId;
	
	@NotNull
	private String description;
	
	@NotNull
	private LocalDate returnDate;
	
	@NotNull
	@Positive
	private int carId;
	
	

}
