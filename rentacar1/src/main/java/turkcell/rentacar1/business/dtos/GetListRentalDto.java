package turkcell.rentacar1.business.dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetListRentalDto {
	
	private int rentId;
	private LocalDate rentDate;
	private LocalDate rentReturnDate;
	private double totalDailyPrice;
	private double firstKilometer;
	private double endKilometer;
	private int rentCityId;
	private int returnCityId;
	
	private int carId;
	private int customerId;
	private int invoiceId;

}
