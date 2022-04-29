package turkcell.rentacar1.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import turkcell.rentacar1.business.abstracts.CarMaintenanceService;
import turkcell.rentacar1.business.abstracts.CarService;
import turkcell.rentacar1.business.abstracts.RentalService;
import turkcell.rentacar1.business.constants.BusinessMessages;
import turkcell.rentacar1.business.dtos.GetListCarMaintenanceDto;
import turkcell.rentacar1.business.dtos.ListCarMaintenanceDto;
import turkcell.rentacar1.business.requests.creates.CreateCarMaintenanceRequest;
import turkcell.rentacar1.business.requests.deletes.DeleteCarMaintenanceRequest;
import turkcell.rentacar1.business.requests.updates.UpdateCarMaintenanceRequest;
import turkcell.rentacar1.core.concretes.BusinessException;
import turkcell.rentacar1.core.utilities.mapping.ModelMapperService;
import turkcell.rentacar1.core.utilities.results.DataResult;
import turkcell.rentacar1.core.utilities.results.Result;
import turkcell.rentacar1.core.utilities.results.SuccessDataResult;
import turkcell.rentacar1.core.utilities.results.SuccessResult;
import turkcell.rentacar1.dataAccess.abstracts.CarMaintenanceDao;
import turkcell.rentacar1.entities.concretes.CarMaintenance;

@Service
public class CarMaintenanceManager implements CarMaintenanceService {
	
	private CarMaintenanceDao carMaintenanceDao;
	private ModelMapperService modelMapperService;
	private CarService carService;
	private RentalService rentalService;
	
	
	@Autowired
	public CarMaintenanceManager(CarMaintenanceDao carMaintenanceDao, ModelMapperService modelMapperService,
			@Lazy CarService carService,@Lazy RentalService rentalService) {
		this.carMaintenanceDao = carMaintenanceDao;
		this.modelMapperService = modelMapperService;
		this.carService = carService;
		this.rentalService = rentalService;
	}


	@Override
	public Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest){
		
		CheckIfCarserviceExistByCarId(createCarMaintenanceRequest.getCarId());
		checkIfCarNotInRent(createCarMaintenanceRequest.getCarId());
		checkIfCarNotInMaintenance(createCarMaintenanceRequest.getCarId());
		
		CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(createCarMaintenanceRequest, CarMaintenance.class);
		carMaintenance.setMaintenanceId(0);
		
		this.carMaintenanceDao.save(carMaintenance);
		
		return new SuccessResult(BusinessMessages.CARMAINTENANCEADDED);
	}

	@Override
	public Result delete(DeleteCarMaintenanceRequest deleteCarMaintenanceRequest) {
		
		checkIfCarMaintenanceExists(deleteCarMaintenanceRequest.getMaintenanceId());
		
		this.carMaintenanceDao.deleteById(deleteCarMaintenanceRequest.getMaintenanceId());
		
		return new SuccessResult(BusinessMessages.CARMAINTENANCEDELETED);
	}

	@Override
	public Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) {
		
		CheckIfCarserviceExistByCarId(updateCarMaintenanceRequest.getCarId());
		checkIfCarInMaintenance(updateCarMaintenanceRequest.getCarId());
		
		CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(updateCarMaintenanceRequest, CarMaintenance.class);
		carMaintenance.setMaintenanceId(this.carMaintenanceDao.getByReturnDateAndCar_carId(null, updateCarMaintenanceRequest.getCarId()).getMaintenanceId());
		
		this.carMaintenanceDao.save(carMaintenance);
		
		return new SuccessResult(BusinessMessages.CARMAINTENANCEUPDATED);
	}

	@Override
	public DataResult<List<ListCarMaintenanceDto>> getAll() {
		
		var result = this.carMaintenanceDao.findAll();
		List<ListCarMaintenanceDto> response =result.stream()
				.map(carMaintenance->this.modelMapperService.forDto()
						.map(carMaintenance, ListCarMaintenanceDto.class)).collect(Collectors.toList());
		
		loadCarId(result, response);
		
		return new SuccessDataResult<List<ListCarMaintenanceDto>>(response,BusinessMessages.SUCCESS);
		
	}

	@Override
	public DataResult<GetListCarMaintenanceDto> getByCarMaintenanceId(int maintenanceId){
		
		checkIfCarMaintenanceExists(maintenanceId);
		
		var result = this.carMaintenanceDao.getById(maintenanceId);
		GetListCarMaintenanceDto response = this.modelMapperService.forDto().map(result, GetListCarMaintenanceDto.class);
		
		return new SuccessDataResult<GetListCarMaintenanceDto>(response,BusinessMessages.SUCCESS);
	}
	
	@Override
	public DataResult<List<ListCarMaintenanceDto>> getCarMaintenanceByCarId(int carId) {
		
		List<CarMaintenance> result= this.carMaintenanceDao.getByCar_CarId(carId);
		
		List<ListCarMaintenanceDto> response = result.stream().map(carMaintenance ->this.modelMapperService.forDto().map(carMaintenance, ListCarMaintenanceDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<ListCarMaintenanceDto>>(response,BusinessMessages.SUCCESS);
	}

	
	private boolean checkIfCarMaintenanceExists(int maintenanceId)  {
		var result = this.carMaintenanceDao.getByMaintenanceId(maintenanceId);
		if(result==null) {
			throw new BusinessException(BusinessMessages.CARMAINTENANCENOTFOUND);
			
		}
		return true;
	}
	
	
	
	@Override
	public boolean checkIfCarNotInMaintenance(int carId) {
	if(this.carMaintenanceDao.getByReturnDateAndCar_carId(null, carId)!=null) {
		throw new BusinessException(BusinessMessages.CARMAINTENANCEEXISTSCAR);
	}
	return true;
		}
	
	private boolean checkIfCarInMaintenance(int carId) {
		if(this.carMaintenanceDao.getByReturnDateAndCar_carId(null, carId)==null) {
			throw new BusinessException(BusinessMessages.CARMAINTENANCENOTEXISTSCAR);
		}
		return true;
	}

	private void checkIfCarNotInRent(int carId) {
	this.rentalService.checkIfCarNotInRent(carId);
	}
	
	private void CheckIfCarserviceExistByCarId(int carId) {
		this.carService.checkIfExistByCarId(carId);
	}
	
	private void loadCarId(List<CarMaintenance> result, List<ListCarMaintenanceDto> response) {
		for(int i=0; i<result.size(); i++) {
			response.get(i).setCarId(result.get(i).getCar().getCarId());
		}
	}




	}
