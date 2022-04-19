package turkcell.rentacar1.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import turkcell.rentacar1.business.abstracts.CarMaintenanceService;
import turkcell.rentacar1.business.dtos.GetListCarMaintenanceDto;
import turkcell.rentacar1.business.dtos.ListCarMaintenanceDto;
import turkcell.rentacar1.business.requests.creates.CreateCarMaintenanceRequest;
import turkcell.rentacar1.business.requests.deletes.DeleteCarMaintenanceRequest;
import turkcell.rentacar1.business.requests.updates.UpdateCarMaintenanceRequest;
import turkcell.rentacar1.core.concretes.BusinessException;
import turkcell.rentacar1.core.utilities.results.DataResult;
import turkcell.rentacar1.core.utilities.results.Result;

@RestController
@RequestMapping("/api/carMaintenances")
public class CarMaintenancesController {
	
	private CarMaintenanceService carMaintenanceService;

	public CarMaintenancesController(CarMaintenanceService carMaintenanceService) {
		this.carMaintenanceService = carMaintenanceService;
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException{
		return this.carMaintenanceService.add(createCarMaintenanceRequest);
	}
	
	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteCarMaintenanceRequest deleteCarMaintenanceRequest) throws BusinessException{
		return this.carMaintenanceService.delete(deleteCarMaintenanceRequest);
	}
	
	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException {
		return this.carMaintenanceService.update(updateCarMaintenanceRequest);
	}
	
	@GetMapping("/getall")
	public DataResult<List<ListCarMaintenanceDto>> getAll() {
		return this.carMaintenanceService.getAll();
	}
	
	@GetMapping("/getbycarmaintenanceid")
	public DataResult<GetListCarMaintenanceDto> getByMaintenanceId(@RequestParam @Valid int maintenanceId) throws BusinessException {
		return this.carMaintenanceService.getByCarMaintenanceId(maintenanceId);
	}

	@GetMapping("/getcarmaintenancebycarid")
	public DataResult<List<ListCarMaintenanceDto>> getByCar_CarId(@RequestParam @Valid int carId) throws BusinessException {
		return this.carMaintenanceService.getCarMaintenanceByCarId(carId);
	}
}
