package turkcell.rentacar1.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListCarAccidentDto {
	
	private int carAccidentId;
	private String carAccidentDescription;
	
	private int carId;

}
