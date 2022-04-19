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

import turkcell.rentacar1.business.abstracts.AdditionalServiceService;
import turkcell.rentacar1.business.dtos.GetListAdditionalServiceDto;
import turkcell.rentacar1.business.dtos.ListAdditionalServiceDto;
import turkcell.rentacar1.business.requests.creates.CreateAdditionalServiceRequest;
import turkcell.rentacar1.business.requests.deletes.DeleteAdditionalServiceRequest;
import turkcell.rentacar1.business.requests.updates.UpdateAdditionalServiceRequest;
import turkcell.rentacar1.core.concretes.BusinessException;
import turkcell.rentacar1.core.utilities.results.DataResult;
import turkcell.rentacar1.core.utilities.results.Result;

@RestController
@RequestMapping("/api/additionalServiceController")
public class AdditionalServicesController {
	
	private AdditionalServiceService additionalServiceService;
	
	@Autowired
	public AdditionalServicesController(AdditionalServiceService additionalServiceService) {
		this.additionalServiceService = additionalServiceService;
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateAdditionalServiceRequest createAdditionalServiceRequest){					
		return this.additionalServiceService.add(createAdditionalServiceRequest);
	}
	
	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteAdditionalServiceRequest deleteAdditionalServiceRequest) {
		return this.additionalServiceService.delete(deleteAdditionalServiceRequest);
	}
	
	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateAdditionalServiceRequest updateAdditionalServiceRequest ){
		return this.additionalServiceService.update(updateAdditionalServiceRequest);
	}
	
	@GetMapping("/getAll")
	public DataResult<List<ListAdditionalServiceDto>> getAll() {
		return this.additionalServiceService.getAll();
	}

	@GetMapping("/getAdditionalServiceId")
	public DataResult<GetListAdditionalServiceDto> getById(int additionalServiceId) throws BusinessException{
		return this.additionalServiceService.getById(additionalServiceId);
	}

}
