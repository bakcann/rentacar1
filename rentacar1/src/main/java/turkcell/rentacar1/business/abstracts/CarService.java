package turkcell.rentacar1.business.abstracts;

import java.util.List;

import org.springframework.data.domain.Sort;

import turkcell.rentacar1.business.dtos.GetListCarDto;
import turkcell.rentacar1.business.dtos.ListCarDto;
import turkcell.rentacar1.business.requests.creates.CreateCarRequest;
import turkcell.rentacar1.business.requests.deletes.DeleteCarRequest;
import turkcell.rentacar1.business.requests.updates.UpdateCarRequest;
import turkcell.rentacar1.core.concretes.BusinessException;
import turkcell.rentacar1.core.utilities.results.DataResult;
import turkcell.rentacar1.core.utilities.results.Result;
import turkcell.rentacar1.entities.concretes.Car;

public interface CarService {

	Result add(CreateCarRequest createCarRequest) throws BusinessException;
	
	Result delete(DeleteCarRequest deleteCarRequest) throws BusinessException;
	
	Result update(UpdateCarRequest updateCarRequest) throws BusinessException;
	
	DataResult<List<ListCarDto>> getAll() ;
	
	DataResult<GetListCarDto> getByCarId(int carId);
	
	DataResult<List<ListCarDto>> getAllPaged(int pageNo, int pageSize);
	
	DataResult<List<ListCarDto>> getAllSorted(Sort.Direction direction);
	
	DataResult<List<ListCarDto>> getByDailyPriceLessThanEqual(double dailyPrice);
	
	public boolean checkIfExistByCarId(int carId);
	
	void toSetCarKilometer(int carId, double carKilometer);
	
	public Car getByIdAllServices(int carId);
	
	
}
