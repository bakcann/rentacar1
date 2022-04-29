package turkcell.rentacar1.business.concretes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import turkcell.rentacar1.business.abstracts.CarMaintenanceService;
import turkcell.rentacar1.business.abstracts.CarService;
import turkcell.rentacar1.business.abstracts.CityService;
import turkcell.rentacar1.business.abstracts.CustomerService;
import turkcell.rentacar1.business.abstracts.OrderedAdditionalServiceService;
import turkcell.rentacar1.business.abstracts.RentalService;
import turkcell.rentacar1.business.constants.BusinessMessages;
import turkcell.rentacar1.business.dtos.GetListRentalDto;
import turkcell.rentacar1.business.dtos.ListRentalDto;
import turkcell.rentacar1.business.requests.creates.CreateRentalRequest;
import turkcell.rentacar1.business.requests.deletes.DeleteRentalRequest;
import turkcell.rentacar1.business.requests.updates.UpdateRentalRequest;
import turkcell.rentacar1.core.concretes.BusinessException;
import turkcell.rentacar1.core.utilities.mapping.ModelMapperService;
import turkcell.rentacar1.core.utilities.results.DataResult;
import turkcell.rentacar1.core.utilities.results.Result;
import turkcell.rentacar1.core.utilities.results.SuccessDataResult;
import turkcell.rentacar1.core.utilities.results.SuccessResult;
import turkcell.rentacar1.dataAccess.abstracts.RentalDao;
import turkcell.rentacar1.entities.concretes.OrderedAdditionalService;
import turkcell.rentacar1.entities.concretes.Rental;

@Service
public class RentalManager implements RentalService{
	
	private RentalDao rentalDao;
	private ModelMapperService modelMapperService;
	private CarService carService;
	private CarMaintenanceService carMaintenanceService;
	private OrderedAdditionalServiceService orderedAdditionalServiceService;
	private CustomerService customerService;
	private CityService cityService;
	

	@Lazy
	@Autowired
	public RentalManager(RentalDao rentalDao, ModelMapperService modelMapperService,@Lazy CarService carService,@Lazy CarMaintenanceService carMaintenanceService,
			@Lazy OrderedAdditionalServiceService orderedAdditionalServiceService,CustomerService customerService,CityService cityService) {
		this.rentalDao = rentalDao;
		this.modelMapperService = modelMapperService;
		this.carService = carService;
		this.carMaintenanceService = carMaintenanceService;
		this.orderedAdditionalServiceService = orderedAdditionalServiceService;
		this.customerService = customerService;
		this.cityService = cityService;
		
	}


	@Override
	public DataResult<Rental> add(CreateRentalRequest createRentalRequest) {
		
		checkIfCarExistByCarId(createRentalRequest.getCarId());
		checkIfCarMaintenanceNotExistByCarId(createRentalRequest.getCarId());
		checkIfCarNotInRent(createRentalRequest.getCarId());
		checkIfExistCityId(createRentalRequest.getRentCityId());
		checkIfExistCityId(createRentalRequest.getReturnCityId());
		checkIfPlanedReturnDateAfterRentDate(createRentalRequest.getRentDate(), createRentalRequest.getPlannedReturnDate());
		
		Rental rental =toAllAdd(createRentalRequest);
		this.rentalDao.save(rental);
		
		return new SuccessDataResult<Rental>(rental,BusinessMessages.RENTALADDED);
	}


	@Override
	public Result delete(DeleteRentalRequest deleteRentalRequest) {
		
		checkIfExistsRentalId(deleteRentalRequest.getRentId());
		
		this.rentalDao.deleteById(deleteRentalRequest.getRentId());
		
		return new SuccessResult(BusinessMessages.RENTALDELETED);
	}


	@Override
	public DataResult<Rental> update(UpdateRentalRequest updateRentalRequest) {
		
		checkIfExistsRentalId(updateRentalRequest.getRentalId());
		checkIfExistCityId(updateRentalRequest.getReturnCityId());
		checkIfReturnDateAfterPlannedReturnDate(this.rentalDao.getByRentId(updateRentalRequest.getRentalId()).getPlannedReturnDate(), updateRentalRequest.getRentReturnDate());
		
		Rental rental = toAllUpdate(updateRentalRequest);
		rentalDao.save(rental);
		
		return new SuccessDataResult<Rental>(rental, BusinessMessages.RENTALUPDATED);
	}


	@Override
	public DataResult<List<ListRentalDto>> getAll() {
		
		var result = this.rentalDao.findAll();
		List<ListRentalDto> response = result.stream().map(rental -> this.modelMapperService.forDto().map(rental, ListRentalDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListRentalDto>>(response,BusinessMessages.SUCCESS);
	}


	@Override
	public DataResult<GetListRentalDto> getByRentalId(int rentId) {
		checkIfExistsRentalId(rentId);
		
		var result= this.rentalDao.getByRentId(rentId);
		GetListRentalDto response = this.modelMapperService.forDto().map(result, GetListRentalDto.class);
		
		return new SuccessDataResult<GetListRentalDto>(response,BusinessMessages.SUCCESS);
	}


	@Override
	public DataResult<List<GetListRentalDto>> getByCar_CarId(int carId) {
		checkIfCarExistByCarId(carId);
		
		var result = this.rentalDao.getByCar_carId(carId);
		List<GetListRentalDto> response = result.stream().map(rental -> this.modelMapperService.forDto().map(rental, GetListRentalDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<GetListRentalDto>>(response,BusinessMessages.SUCCESS);
	}


	@Override
	public boolean checkIfExistsRentalId(int rentId) {
		if(this.rentalDao.getByRentId(rentId)==null) {
			throw new BusinessException(BusinessMessages.RENTALNOTFOUND);
		}
		return true;
	}


	@Override
	public boolean checkIfCarNotInRent(int carId) {
		if(this.rentalDao.getByRentReturnDateAndCar_carId(null, carId)!=null) {
			throw new BusinessException(BusinessMessages.CARAVAILABLEFORRENTAL);
		}
		return true;
	}
	
	private double calculatorTotalPrice(Rental rental) {
		long dayDifference= 0;
		double rentalTotalPrice = rental.getTotalPrice();
		double totalPrice= 0;
		
		if(rental.getRentReturnDate()==null) {
			dayDifference = ChronoUnit.DAYS.between(rental.getRentDate(), rental.getPlannedReturnDate());
			rentalTotalPrice =0;
		}else {
			dayDifference = ChronoUnit.DAYS.between(rental.getPlannedReturnDate(), rental.getRentReturnDate());
		}
		totalPrice = (double) dayDifference*this.carService.getByCarId(rental.getCar().getCarId()).getData().getDailyPrice();
		
		if(checkIfOrderedAdditionalService(rental.getRentId())) {
			for (OrderedAdditionalService orderedAdditionalService : rental.getOrderedAdditionalServices()) {
				totalPrice += (double) dayDifference*orderedAdditionalService.getAdditionalService().getAdditionalPrice();
			}
		}
		if(!rental.getRentCity().equals(rental.getReturnCity())) {
			totalPrice += 750;
		}
		totalPrice= totalPrice + rentalTotalPrice;
		return totalPrice;
	}

	@Override
	public void totalPriceIncludingAdditionalService(int rentId) {
		Rental rental = this.rentalDao.getByRentId(rentId);
		rental.setTotalPrice(calculatorTotalPrice(rental));
		this.rentalDao.save(rental);
		
	}
	
	private boolean checkIfReturnDateAfterPlannedReturnDate(LocalDate plannedReturnDate, LocalDate rentReturnDate) {
		if(rentReturnDate.isBefore(plannedReturnDate)) {
			throw new BusinessException(BusinessMessages.RENTALRETURNDATEAFTERPLANNEDRETURNDATE);
		}
		return true;
	}
	
	private boolean checkIfPlanedReturnDateAfterRentDate(LocalDate rentDate, LocalDate plannedReturnDate) {
		if(rentDate.isBefore(plannedReturnDate)) {
			return true;
		}
		throw new BusinessException(BusinessMessages.RENTALPLANNEDRETURNDATEAFTERRENTDATE);
	}
	private Rental toAllAdd(CreateRentalRequest createRentalRequest) {
		
		Rental rental = new Rental();
		rental.setRentId(0);
		rental.setRentDate(createRentalRequest.getRentDate());
		rental.setRentReturnDate(null);
		rental.setCar(this.carService.getByIdAllServices(createRentalRequest.getCarId()));
		rental.setCustomer(this.customerService.getById(createRentalRequest.getCustomerId()));
		rental.setRentCity(this.cityService.getByIdAllService(createRentalRequest.getRentCityId()));
		rental.setReturnCity(this.cityService.getByIdAllService(createRentalRequest.getReturnCityId()));
		rental.setFirstKilometer(getCarKilometer(rental.getCar().getCarId()));
		rental.setEndKilometer(0);
		rental.setPlannedReturnDate(createRentalRequest.getPlannedReturnDate());
		rental.setTotalPrice(calculatorTotalPrice(rental));
		
		return rental;
	}
	
	private Rental toAllUpdate(UpdateRentalRequest updateRentalRequest) {
		Rental rental = this.rentalDao.getByRentId(updateRentalRequest.getRentalId());
		
		rental.setReturnCity(this.cityService.getByIdAllService(updateRentalRequest.getReturnCityId()));
		rental.setRentReturnDate(updateRentalRequest.getRentReturnDate());
		rental.setTotalPrice(calculatorTotalPrice(rental));
		rental.setEndKilometer(updateRentalRequest.getEndKilometer());
		setCarKilometer(rental);
		
		return rental;
	}
	
	private double getCarKilometer(int carId) {
		return this.carService.getByCarId(carId).getData().getCarKilometer();
	}
	
	private void setCarKilometer(Rental rental) {
		if(rental.getFirstKilometer()> rental.getEndKilometer()) {
			throw new BusinessException(BusinessMessages.RENTALSETCARKILOMETER);
		}
		this.carService.toSetCarKilometer(rental.getCar().getCarId(), rental.getEndKilometer());
	}

	@Override
	public Rental getByRentalIdForOtherServices(int rentId) {
		
		return this.rentalDao.getByRentId(rentId);
	}
	
	private boolean checkIfCarMaintenanceNotExistByCarId(int carId) {
		 return this.carMaintenanceService.checkIfCarNotInMaintenance(carId);
	}
	
	private boolean checkIfCarExistByCarId(int carId) {
		this.carService.checkIfExistByCarId(carId);
		return true;
	}
	
	private boolean checkIfOrderedAdditionalService(int rentId) {
		if(this.orderedAdditionalServiceService.checkIfExistsRentalByRentalId(rentId)) {
			return true;
		}
		return false;
	}
	
	private boolean checkIfExistCityId(int cityId) {
		this.cityService.checkIfExistByCityId(cityId);
		return true;
		
	}
	

	

}
