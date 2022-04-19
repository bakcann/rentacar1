package turkcell.rentacar1.business.abstracts;

import java.util.List;

import turkcell.rentacar1.business.dtos.ListIndividualCustomerDto;
import turkcell.rentacar1.business.requests.creates.CreateIndividualCustomerRequest;
import turkcell.rentacar1.business.requests.deletes.DeleteIndividualCustomerRequest;
import turkcell.rentacar1.business.requests.updates.UpdateIndividualCustomerRequest;
import turkcell.rentacar1.core.utilities.results.DataResult;
import turkcell.rentacar1.core.utilities.results.Result;

public interface IndividualCustomerService {
	
	Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest);
	Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest);
	Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest);
	
	DataResult<List<ListIndividualCustomerDto>> getAll();


}
