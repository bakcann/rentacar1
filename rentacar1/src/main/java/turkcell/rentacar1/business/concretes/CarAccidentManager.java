package turkcell.rentacar1.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import turkcell.rentacar1.business.abstracts.CarAccidentService;
import turkcell.rentacar1.business.abstracts.CarService;
import turkcell.rentacar1.business.dtos.GetListCarAccidentDto;
import turkcell.rentacar1.business.dtos.ListCarAccidentDto;
import turkcell.rentacar1.business.requests.creates.CreateCarAccidentRequest;
import turkcell.rentacar1.business.requests.deletes.DeleteCarAccidentRequest;
import turkcell.rentacar1.business.requests.updates.UpdateCarAccidentRequest;
import turkcell.rentacar1.core.concretes.BusinessException;
import turkcell.rentacar1.core.utilities.mapping.ModelMapperService;
import turkcell.rentacar1.core.utilities.results.DataResult;
import turkcell.rentacar1.core.utilities.results.Result;
import turkcell.rentacar1.core.utilities.results.SuccessDataResult;
import turkcell.rentacar1.core.utilities.results.SuccessResult;
import turkcell.rentacar1.dataAccess.abstracts.CarAccidentDao;
import turkcell.rentacar1.entities.concretes.CarAccident;

@Service
public class CarAccidentManager implements CarAccidentService {
	
	private CarAccidentDao carAccidentDao;
	private ModelMapperService modelMapperService;
	private CarService carService;
	
	@Autowired
	public CarAccidentManager(CarAccidentDao carAccidentDao, ModelMapperService modelMapperService,
			CarService carService) {
		this.carAccidentDao = carAccidentDao;
		this.modelMapperService = modelMapperService;
		this.carService = carService;
	}

	@Override
	public Result add(CreateCarAccidentRequest createCarAccidentRequest) {
		
		checkIfCarExistByCarId(createCarAccidentRequest.getCarId());
		
		CarAccident carAccident= this.modelMapperService.forRequest().map(createCarAccidentRequest, CarAccident.class);
		
		this.carAccidentDao.save(carAccident);
		return new SuccessResult("CarAccident.added");
	}

	@Override
	public Result delete(DeleteCarAccidentRequest deleteCarAccidentRequest) {
		
		checkIfCarAccidentExist(deleteCarAccidentRequest.getCarAccidentId());
		
		this.carAccidentDao.deleteById(deleteCarAccidentRequest.getCarAccidentId());
		return new SuccessResult("Caraccident.deleted");
	}

	@Override
	public Result update( UpdateCarAccidentRequest updateCarAccidentRequest) {
		
		checkIfCarAccidentExist(updateCarAccidentRequest.getCarAccidentId());
		
		CarAccident carAccident= this.carAccidentDao.getByCarAccidentId(updateCarAccidentRequest.getCarAccidentId());
		carAccident.setCarAccidentDescription(updateCarAccidentRequest.getCarAccidentDesription());
		
		this.carAccidentDao.save(carAccident);
		
		
		return null;
	}

	@Override
	public DataResult<List<ListCarAccidentDto>> getAll() {
		
		var result = this.carAccidentDao.findAll();
		
		List<ListCarAccidentDto> response = result.stream().map(carAccident ->this.modelMapperService.forDto().map(carAccidentDao, ListCarAccidentDto.class)).collect(Collectors.toList());
		
	
		return new SuccessDataResult<List<ListCarAccidentDto>>(response,"Success");
	}

	@Override
	public DataResult<GetListCarAccidentDto> getById(int carAccidentId) {
			
		CarAccident result = checkIfCarAccidentExist(carAccidentId);
		
		GetListCarAccidentDto response = this.modelMapperService.forDto().map(result, GetListCarAccidentDto.class);
		response.setCarId(result.getCar().getCarId());
		return new SuccessDataResult<GetListCarAccidentDto>(response, "Success");
	}
	
	
	
	@Override
	public DataResult<List<ListCarAccidentDto>> getByCarAccidentByCarId(int carId) {
		
		checkIfCarExistByCarId(carId);
		
		List<CarAccident> result = this.carAccidentDao.getByCar_CarId(carId);
		List<ListCarAccidentDto> response = result.stream().map(carAccident -> this.modelMapperService.forDto().map(carAccidentDao, ListCarAccidentDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListCarAccidentDto>>(response,"Success");
	}
	
	private CarAccident checkIfCarAccidentExist(int carAccidentId) {
		CarAccident carAccident = this.carAccidentDao.getByCarAccidentId(carAccidentId);
		if(carAccident == null) {
			throw new BusinessException("CarAccident.NotFound");
		}
		
		return carAccident;
	}
	
	private boolean checkIfCarExistByCarId(int carId) {
		if(this.carService.getByCarId(carId).getData()== null) {
			throw new BusinessException("not found carId");
		}
		return true;
	}
	
	

	
	

}
