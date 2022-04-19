package turkcell.rentacar1.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import turkcell.rentacar1.business.abstracts.CityService;
import turkcell.rentacar1.business.dtos.ListCityDto;
import turkcell.rentacar1.business.requests.creates.CreateCityRequest;
import turkcell.rentacar1.business.requests.deletes.DeleteCityRequest;
import turkcell.rentacar1.core.utilities.results.DataResult;
import turkcell.rentacar1.core.utilities.results.Result;

@RestController
@RequestMapping("/api/cities")
public class CitiesController {
	
	CityService cityService;
	
	@Autowired
	public CitiesController(CityService cityService) {
		this.cityService = cityService;
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateCityRequest createCityRequest) {
		return this.cityService.add(createCityRequest);
	}
	
	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteCityRequest deleteCityRequest) {
		return this.cityService.delete(deleteCityRequest);
	}
	
	@GetMapping("/getall")
		public DataResult<List<ListCityDto>> getAll(){
			return this.cityService.getAll();
		}
	

}
