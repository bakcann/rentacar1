package turkcell.rentacar1.business.abstracts;

import java.util.List;

import turkcell.rentacar1.business.dtos.GetListAdditionalServiceDto;
import turkcell.rentacar1.business.dtos.ListAdditionalServiceDto;
import turkcell.rentacar1.business.requests.creates.CreateAdditionalServiceRequest;
import turkcell.rentacar1.business.requests.deletes.DeleteAdditionalServiceRequest;
import turkcell.rentacar1.business.requests.updates.UpdateAdditionalServiceRequest;
import turkcell.rentacar1.core.utilities.results.DataResult;
import turkcell.rentacar1.core.utilities.results.Result;
import turkcell.rentacar1.entities.concretes.AdditionalService;

public interface AdditionalServiceService {
	
	Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest);
	
	Result delete(DeleteAdditionalServiceRequest deleteAdditionalServiceRequest);
	
	Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest);
	
	DataResult<List<ListAdditionalServiceDto>> getAll();
	
	DataResult<GetListAdditionalServiceDto> getById(int additionalServiceId);
	
	public boolean checkIfExistByAdditionalServiceId(int additionalServiceId);
	
	public AdditionalService additionalServiceForOrder(int additionalServiceId);
	

}
