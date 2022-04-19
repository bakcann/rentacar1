package turkcell.rentacar1.business.abstracts;

import java.util.List;

import turkcell.rentacar1.business.dtos.GetListCarAccidentDto;
import turkcell.rentacar1.business.dtos.ListCarAccidentDto;
import turkcell.rentacar1.business.requests.creates.CreateCarAccidentRequest;
import turkcell.rentacar1.business.requests.deletes.DeleteCarAccidentRequest;
import turkcell.rentacar1.business.requests.updates.UpdateCarAccidentRequest;
import turkcell.rentacar1.core.utilities.results.DataResult;
import turkcell.rentacar1.core.utilities.results.Result;

public interface CarAccidentService {
	
	Result add(CreateCarAccidentRequest createCarAccidentRequest);
	Result delete(DeleteCarAccidentRequest deleteCarAccidentRequest);
	Result update( UpdateCarAccidentRequest updateCarAccidentRequest);
	
	DataResult<List<ListCarAccidentDto>> getAll();
	DataResult<GetListCarAccidentDto> getById(int carAccidentId);
	DataResult<List<ListCarAccidentDto>> getByCarAccidentByCarId(int carId);
	

}
