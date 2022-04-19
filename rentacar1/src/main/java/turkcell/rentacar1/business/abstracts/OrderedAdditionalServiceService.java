package turkcell.rentacar1.business.abstracts;

import java.util.List;

import turkcell.rentacar1.business.dtos.GetListOrderedAdditionalServiceDto;
import turkcell.rentacar1.business.dtos.ListOrderedAdditionalServiceDto;
import turkcell.rentacar1.business.requests.creates.CreateOrderedAdditionalServiceRequest;
import turkcell.rentacar1.business.requests.deletes.DeleteOrderedAdditionalServiceRequest;
import turkcell.rentacar1.business.requests.updates.UpdateOrderedAdditionalServiceRequest;
import turkcell.rentacar1.core.utilities.results.DataResult;
import turkcell.rentacar1.core.utilities.results.Result;

public interface OrderedAdditionalServiceService {
	
	Result add(CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest);
	Result delete(DeleteOrderedAdditionalServiceRequest deleteOrderedAdditionalServiceRequest);
	Result update(UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest);
	
	DataResult<List<ListOrderedAdditionalServiceDto>> getAll();
	DataResult<GetListOrderedAdditionalServiceDto> getById(int orderedId);
	public boolean checkIfExistRentalByRentalId(int rentId);
	

}
