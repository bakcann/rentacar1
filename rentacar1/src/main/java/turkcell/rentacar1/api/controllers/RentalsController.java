package turkcell.rentacar1.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import turkcell.rentacar1.business.abstracts.RentalService;
import turkcell.rentacar1.business.dtos.GetListRentalDto;
import turkcell.rentacar1.business.dtos.ListRentalDto;
import turkcell.rentacar1.business.requests.creates.CreateRentalRequest;
import turkcell.rentacar1.business.requests.deletes.DeleteRentalRequest;
import turkcell.rentacar1.business.requests.updates.UpdateRentalRequest;
import turkcell.rentacar1.core.concretes.BusinessException;
import turkcell.rentacar1.core.utilities.results.DataResult;
import turkcell.rentacar1.core.utilities.results.Result;

@RestController
@RequestMapping("/api/rents")
public class RentalsController {
	
	private RentalService rentalService;
	
	@Autowired
	public RentalsController(RentalService rentalService) {
		this.rentalService = rentalService;
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateRentalRequest createRentalRequest) throws BusinessException{
		return this.rentalService.add(createRentalRequest);
	}
	
	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteRentalRequest deleteRentalRequest) throws BusinessException{
		return this.rentalService.delete(deleteRentalRequest);
	}
	
	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateRentalRequest updateRentalRequest) throws BusinessException{
		return this.rentalService.update(updateRentalRequest);
	}
	
	@GetMapping("/getall")
	public DataResult<List<ListRentalDto>> getAll() {
		return this.rentalService.getAll();
	}
	
	@GetMapping("/getbyrentalid")
	public DataResult<GetListRentalDto> getByCarMaintenanceId(int rentId) throws BusinessException {
		return this.rentalService.getByRentalId(rentId);
	}

	@GetMapping("/getrentalbycarid")
	public DataResult<List<GetListRentalDto>> getByCar_CarId(int carId) throws BusinessException {
		return this.rentalService.getByCar_CarId(carId);
	}
}
