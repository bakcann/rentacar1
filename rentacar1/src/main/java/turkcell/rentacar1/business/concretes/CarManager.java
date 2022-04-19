package turkcell.rentacar1.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import turkcell.rentacar1.business.abstracts.BrandService;
import turkcell.rentacar1.business.abstracts.CarService;
import turkcell.rentacar1.business.abstracts.ColorService;
import turkcell.rentacar1.business.dtos.GetListCarDto;
import turkcell.rentacar1.business.dtos.ListCarDto;
import turkcell.rentacar1.business.requests.creates.CreateCarRequest;
import turkcell.rentacar1.business.requests.deletes.DeleteCarRequest;
import turkcell.rentacar1.business.requests.updates.UpdateCarRequest;
import turkcell.rentacar1.core.concretes.BusinessException;
import turkcell.rentacar1.core.utilities.mapping.ModelMapperService;
import turkcell.rentacar1.core.utilities.results.DataResult;
import turkcell.rentacar1.core.utilities.results.ErrorDataResult;
import turkcell.rentacar1.core.utilities.results.Result;
import turkcell.rentacar1.core.utilities.results.SuccessDataResult;
import turkcell.rentacar1.core.utilities.results.SuccessResult;
import turkcell.rentacar1.dataAccess.abstracts.CarDao;
import turkcell.rentacar1.entities.concretes.Car;

@Service
public class CarManager implements CarService{

		private CarDao carDao;
		private ModelMapperService modelMapperService;
		private ColorService colorService;
		private BrandService brandService;
		
	@Autowired
	public CarManager(CarDao carDao, ModelMapperService modelMapperService, ColorService colorService, BrandService brandService) {
			super();
			this.carDao = carDao;
			this.modelMapperService = modelMapperService;
			this.colorService = colorService;
			this.brandService = brandService;
			
		}

	@Override
	public Result add(CreateCarRequest createCarRequest) throws BusinessException {
		Car car = this.modelMapperService.forRequest().map(createCarRequest, Car.class);
		
		checkIfExistByBrandId(car.getBrand().getBrandId());
		checkIfExistByColorId(car.getColor().getColorId());
		
			this.carDao.save(car);
			return new SuccessResult("Araba eklendi");
	}

	@Override
	public Result delete(DeleteCarRequest deleteCarRequest) throws BusinessException {
		
		    checkIfExistByCarId(deleteCarRequest.getCarId());
		
			this.carDao.deleteById(deleteCarRequest.getCarId());
			return new SuccessResult("Araba silindi");
	}

	@Override
	public Result update( UpdateCarRequest updateCarRequest) throws BusinessException {
		
		checkIfExistByCarId(updateCarRequest.getCarId());
		
		Car car = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);
		
		checkIfExistByBrandId(car.getBrand().getBrandId());
		checkIfExistByColorId(car.getColor().getColorId());
		
		this.carDao.save(car);
		
		return new SuccessResult("Araba güncellendi.");
	}
	
	@Override
	public DataResult<List<ListCarDto>> getAll() {
		
		var result = this.carDao.findAll();
		
		List<ListCarDto> response = result.stream()
				.map(car->this.modelMapperService.forDto()
						.map(car, ListCarDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<ListCarDto>>(response,"Success");
	}
	
	@Override
	public DataResult<GetListCarDto> getByCarId(int carId)  {
		
		var result = this.carDao.getByCarId(carId);
		if(result!=null) {
			GetListCarDto response = this.modelMapperService.forDto().map(result, GetListCarDto.class);
		 return new SuccessDataResult<GetListCarDto>(response);
		}
		return new ErrorDataResult<GetListCarDto>("Bu id kullanılmamaktadır.");	
	}
	
	@Override
	public DataResult<List<ListCarDto>> getAllPaged(int pageNo, int pageSize) {
		Pageable pageable =PageRequest.of(pageNo-1, pageSize);
		
		List<Car> result = this.carDao.findAll(pageable).getContent(); 
		
		List<ListCarDto> response = result.stream()
				.map(car->this.modelMapperService.forDto()
						.map(car, ListCarDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListCarDto>>(response);
	}

	@Override
	public DataResult<List<ListCarDto>> getAllSorted(Sort.Direction direction) {
		Sort sort = Sort.by(direction, "dailyPrice" );
		
		List<Car> result = this.carDao.findAll(sort); 
		
		List<ListCarDto> response = result.stream()
				.map(car->this.modelMapperService.forDto()
						.map(car, ListCarDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListCarDto>>(response);
	}

	@Override
	public DataResult<List<ListCarDto>> getByDailyPriceLessThanEqual(double dailyPrice) {
		
		List<Car> result =this.carDao.getByDailyPriceLessThanEqual(dailyPrice);
		
		List<ListCarDto> response = result.stream()
				.map(car->this.modelMapperService.forDto()
						.map(car, ListCarDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListCarDto>>(response);
	}
	
	@Override
	public boolean checkIfExistByCarId(int carId) {
		
		Car car = this.carDao.getByCarId(carId);
		
		if(car==null) {
			throw new BusinessException("Böyle bir araba mevcut değil.");
		}
		return true;
	}
	
	private boolean checkIfExistByBrandId(int brandId) {
		if(this.brandService.getByBrandId(brandId)==null) {
			throw new BusinessException("Böyle bir marka mevcut değil.");
		}
		return true;
	}
	
	private boolean checkIfExistByColorId(int colorId) {
		if(this.colorService.getByColorId(colorId)== null) {
			throw new BusinessException("Böyle bir renk mevcut değil.");
		}
		return true;
	}
	
	public void toSetCarKilometer(int carId, double carKilometer) {
		Car car = this.carDao.getByCarId(carId);
		car.setCarKilometer(carKilometer);
	}
	
	@Override
	public Car getByIdAllServices(int carId) {
		return this.carDao.getByCarId(carId);
	}

	
	
	
	
	
	
	
	
	
	
	

}
