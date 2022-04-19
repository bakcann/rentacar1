package turkcell.rentacar1.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import turkcell.rentacar1.business.abstracts.CityService;

import turkcell.rentacar1.business.dtos.ListCityDto;
import turkcell.rentacar1.business.requests.creates.CreateCityRequest;
import turkcell.rentacar1.business.requests.deletes.DeleteCityRequest;
import turkcell.rentacar1.core.concretes.BusinessException;
import turkcell.rentacar1.core.utilities.mapping.ModelMapperService;
import turkcell.rentacar1.core.utilities.results.DataResult;
import turkcell.rentacar1.core.utilities.results.Result;
import turkcell.rentacar1.core.utilities.results.SuccessDataResult;
import turkcell.rentacar1.core.utilities.results.SuccessResult;
import turkcell.rentacar1.dataAccess.abstracts.CityDao;
import turkcell.rentacar1.entities.concretes.City;

@Service
public class CityManager implements CityService {

		private CityDao cityDao;
		private ModelMapperService modelMapperService;
		
		@Autowired
	public CityManager(CityDao cityDao, ModelMapperService modelMapperService) {
			this.cityDao = cityDao;
			this.modelMapperService = modelMapperService;
		}
		
	@Override
	public Result add(CreateCityRequest createCityRequest) {
			checkIfExitByCityName(createCityRequest.getCityName());
			City city = this.modelMapperService.forRequest().map(createCityRequest, City.class);
			
			this.cityDao.save(city);
			return new SuccessResult("City.added");
	}


	@Override
	public Result delete(DeleteCityRequest deleteCityRequest) {
			
			checkIfExistByCityId(deleteCityRequest.getCityId());
		
			this.cityDao.deleteById(deleteCityRequest.getCityId());
		
			return new SuccessResult("City.deleted");
	}


	@Override
	public DataResult<List<ListCityDto>> getAll() {
		
		var result = this.cityDao.findAll();
		List<ListCityDto> response = result.stream()
				.map(city -> this.modelMapperService.forDto().map(city, ListCityDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<ListCityDto>>(response,"Success");
	}
	@Override
	public City getByIdAllService(int cityId) {
		return cityDao.getByCityId(cityId);
	}
	
	private boolean checkIfExistByCityId(int cityId) {
		if(this.cityDao.getByCityId(cityId)==null) {
			throw new BusinessException("Brand not found");
		}
		return true;
	}
	
	private boolean checkIfExitByCityName(String cityName) {
		City city = this.cityDao.getByCityName(cityName);
		
		if(city != null) {
			throw new BusinessException("City have is name");
		}
		return true;
	}
	


	
	

}
