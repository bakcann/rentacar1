package turkcell.rentacar1.business.abstracts;

import java.util.List;

import turkcell.rentacar1.business.dtos.GetListRentalDto;
import turkcell.rentacar1.business.dtos.ListRentalDto;
import turkcell.rentacar1.business.requests.creates.CreateRentalRequest;
import turkcell.rentacar1.business.requests.deletes.DeleteRentalRequest;
import turkcell.rentacar1.business.requests.updates.UpdateRentalRequest;
import turkcell.rentacar1.core.utilities.results.DataResult;
import turkcell.rentacar1.core.utilities.results.Result;
import turkcell.rentacar1.entities.concretes.Rental;

public interface RentalService {
	
	DataResult<Rental> add(CreateRentalRequest createRentalRequest);
	
	Result delete(DeleteRentalRequest deleteRentalRequest);
	
	DataResult<Rental> update(UpdateRentalRequest updateRentalRequest);
	
	DataResult<List<ListRentalDto>> getAll();
	
	DataResult<GetListRentalDto> getByRentalId(int rentId);
	
	DataResult<List<GetListRentalDto>> getByCar_CarId(int carId);
	
	public boolean checkIfExistsRentalId(int rentId);
	public boolean checkIfCarNotInRent(int carId);
	public void totalPriceIncludingAdditionalService(int rentId);
	public Rental getByRentalIdForOtherServices(int rentId);
	
	
	
	
	
	
	

}
