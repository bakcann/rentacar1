package turkcell.rentacar1.business.abstracts;

import java.time.LocalDate;
import java.util.List;

import turkcell.rentacar1.business.dtos.GetListInvoiceDto;
import turkcell.rentacar1.business.dtos.ListInvoiceDto;
import turkcell.rentacar1.business.requests.creates.CreateInvoiceRequest;
import turkcell.rentacar1.business.requests.deletes.DeleteInvoiceRequest;
import turkcell.rentacar1.business.requests.updates.UpdateInvoiceRequest;
import turkcell.rentacar1.core.utilities.results.DataResult;
import turkcell.rentacar1.core.utilities.results.Result;
import turkcell.rentacar1.entities.concretes.Invoice;

public interface InvoiceService {
	
	DataResult<Invoice> add(CreateInvoiceRequest createInvoiceRequest);
	Result delete(DeleteInvoiceRequest deleteInvoiceRequest);
	Result update(UpdateInvoiceRequest updateInvoiceRequest);
	
	DataResult<List<ListInvoiceDto>> getAll();
	DataResult<GetListInvoiceDto> getById(int invoiceId);
	
	DataResult<List<ListInvoiceDto>> getByCreateDateBetween(LocalDate startDate , LocalDate endDate);
	DataResult<List<ListInvoiceDto>> getInvoiceByCustomer(int customerId);

}
