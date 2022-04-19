package turkcell.rentacar1.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import turkcell.rentacar1.business.abstracts.ColorService;
import turkcell.rentacar1.business.dtos.ListColorDto;
import turkcell.rentacar1.business.requests.creates.CreateColorRequest;
import turkcell.rentacar1.business.requests.deletes.DeleteColorRequest;
import turkcell.rentacar1.business.requests.updates.UpdateColorRequest;
import turkcell.rentacar1.core.concretes.BusinessException;
import turkcell.rentacar1.core.utilities.results.DataResult;
import turkcell.rentacar1.core.utilities.results.Result;

@RestController
@RequestMapping("/api/colors")
public class ColorsController {
	
	private ColorService colorService;

	@Autowired
	public ColorsController(ColorService colorService) {
		
		this.colorService = colorService;
	}
	
	@GetMapping("/getall")
	public DataResult<List<ListColorDto>> getAll(){
		return colorService.getAll();
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody CreateColorRequest createColorRequest) throws BusinessException{
		return this.colorService.add(createColorRequest);
	}
	
	@GetMapping("/getcolorid")
	public DataResult<ListColorDto> getByColorId(int colorId) throws BusinessException{
		return this.colorService.getByColorId(colorId);
	}
	
	@PostMapping("/delete")
	public Result delete(@RequestBody DeleteColorRequest deleteColorRequest ) throws BusinessException{
		 return this.colorService.delete(deleteColorRequest);
	}
	
	@PostMapping("/update")
	public Result update(@RequestBody UpdateColorRequest updateColorRequest)  throws BusinessException{
		return this.colorService.update(updateColorRequest);
	}

	

}
