package turkcell.rentacar1.business.abstracts;

import java.util.List;

import turkcell.rentacar1.business.dtos.GetListPaymentDto;
import turkcell.rentacar1.business.dtos.ListPaymentDto;
import turkcell.rentacar1.business.requests.creates.CreatePaymentExtraRequest;
import turkcell.rentacar1.business.requests.creates.CreatePaymentRequest;
import turkcell.rentacar1.core.utilities.results.DataResult;
import turkcell.rentacar1.core.utilities.results.Result;

public interface PaymentService {
	
	Result add(CreatePaymentRequest createPaymentRequest);
	Result addForExtra(CreatePaymentExtraRequest createPaymentExtraRequest);
	
	DataResult<List<ListPaymentDto>> getAll();
	DataResult<GetListPaymentDto> getById(int paymentId);
	

}
