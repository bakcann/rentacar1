package turkcell.rentacar1.business.abstracts;

import java.util.List;

import turkcell.rentacar1.business.dtos.ListCorporateCustomerDto;
import turkcell.rentacar1.business.requests.creates.CreateCorporateCustomerRequest;
import turkcell.rentacar1.business.requests.deletes.DeleteCorporateCustomerRequest;
import turkcell.rentacar1.business.requests.updates.UpdateCorporateCustomerRequest;
import turkcell.rentacar1.core.utilities.results.DataResult;
import turkcell.rentacar1.core.utilities.results.Result;

public interface CorporateCustomerService {
	
	Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest);
	Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest);
	Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest);
	
	DataResult<List<ListCorporateCustomerDto>> getAll();

}
