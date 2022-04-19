package turkcell.rentacar1.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import turkcell.rentacar1.business.abstracts.CarAccidentService;
import turkcell.rentacar1.business.dtos.ListCarAccidentDto;
import turkcell.rentacar1.business.requests.creates.CreateCarAccidentRequest;
import turkcell.rentacar1.business.requests.deletes.DeleteCarAccidentRequest;
import turkcell.rentacar1.business.requests.updates.UpdateCarAccidentRequest;
import turkcell.rentacar1.core.utilities.results.DataResult;
import turkcell.rentacar1.core.utilities.results.Result;

@RestController
@RequestMapping("/api/carAccidents")
public class CarAccidentsController {
	
	private CarAccidentService carAccidentService;
	
	public CarAccidentsController(CarAccidentService carAccidentService) {
		
		this.carAccidentService = carAccidentService;
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateCarAccidentRequest createCarAccidentRequest) {
		return this.carAccidentService.add(createCarAccidentRequest);
	}
	
	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteCarAccidentRequest deleteCarAccidentRequest) {
		return this.carAccidentService.delete(deleteCarAccidentRequest);
	}
	
	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateCarAccidentRequest updateCarAccidentRequest) {
		return this.carAccidentService.update( updateCarAccidentRequest);
	}
	
	@GetMapping("/getAll")
	public DataResult<List<ListCarAccidentDto>> getAll(){
		return this.carAccidentService.getAll();
	}

}
