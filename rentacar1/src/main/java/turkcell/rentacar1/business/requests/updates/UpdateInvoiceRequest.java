package turkcell.rentacar1.business.requests.updates;

import java.time.LocalDate;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateInvoiceRequest {
	
	@NotNull
	@Positive
	private int invoiceId;
	
	@NotNull
	@FutureOrPresent
	private LocalDate createDate;
	
	
	

}
