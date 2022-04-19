package turkcell.rentacar1.business.abstracts;

import java.util.List;

import turkcell.rentacar1.business.dtos.ListCityDto;
import turkcell.rentacar1.business.requests.creates.CreateCityRequest;
import turkcell.rentacar1.business.requests.deletes.DeleteCityRequest;
import turkcell.rentacar1.core.utilities.results.DataResult;
import turkcell.rentacar1.core.utilities.results.Result;
import turkcell.rentacar1.entities.concretes.City;

public interface CityService {
	
	Result add(CreateCityRequest createCityRequest);
	Result delete(DeleteCityRequest deleteCityRequest);
	
	DataResult<List<ListCityDto>> getAll();
	
	public City getByIdAllService(int cityId);
	

}
