package turkcell.rentacar1.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import turkcell.rentacar1.business.abstracts.CarService;
import turkcell.rentacar1.business.dtos.GetListCarDto;
import turkcell.rentacar1.business.dtos.ListCarDto;
import turkcell.rentacar1.business.requests.creates.CreateCarRequest;
import turkcell.rentacar1.business.requests.deletes.DeleteCarRequest;
import turkcell.rentacar1.business.requests.updates.UpdateCarRequest;
import turkcell.rentacar1.core.concretes.BusinessException;
import turkcell.rentacar1.core.utilities.results.DataResult;
import turkcell.rentacar1.core.utilities.results.Result;

@RestController
@RequestMapping("/api/cars")
public class CarsController {
	
	private CarService carService;

	@Autowired
	public CarsController(CarService carService) {
	
		this.carService = carService;
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateCarRequest createCarRequest) throws BusinessException{
		return this.carService.add(createCarRequest);
	}
	
	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteCarRequest deleteCarRequest) throws BusinessException{
		return this.carService.delete(deleteCarRequest);
	}
	
	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateCarRequest updateCarRequest) throws BusinessException{
		return this.carService.update(updateCarRequest);
	}
	
	@GetMapping("/getAll")
	public DataResult<List<ListCarDto>> getAll(){
		return carService.getAll();
	}
	
	@GetMapping("/getbycarid")
	public DataResult<GetListCarDto> getByCarId(@RequestParam @Valid int carId) throws BusinessException{
		return this.carService.getByCarId(carId);
	}
	
	@GetMapping("/getAllPaged")
	public DataResult<List<ListCarDto>> getAllPaged(@RequestParam @Valid int pageNo, int pageSize){
		return this.carService.getAllPaged(pageNo, pageSize);
	}
	@GetMapping("/getAllSorted")
	public DataResult<List<ListCarDto>> getAllSorted(Sort.Direction direction){
		return this.carService.getAllSorted(direction);
	}
	@GetMapping("/getByDailyPrice")
	public DataResult<List<ListCarDto>> getByDaiilyPrice(@RequestParam @Valid double dailyPrice){
		return this.carService.getByDailyPriceLessThanEqual(dailyPrice);
	}

	
	


}