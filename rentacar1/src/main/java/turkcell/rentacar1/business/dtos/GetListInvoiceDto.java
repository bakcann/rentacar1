package turkcell.rentacar1.business.dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetListInvoiceDto {
	
	private int invoiceId;
	private String invoiceNo;
	private LocalDate createDate;
	private LocalDate rentDate;
	private LocalDate returnDate;
	private long totalDay;
	private double rentTotalPrice;
	
	private int customerId;
	private int rentId;
	private int paymentId;

}
