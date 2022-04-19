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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import turkcell.rentacar1.business.abstracts.BrandService;
import turkcell.rentacar1.business.dtos.GetListBrandDto;
import turkcell.rentacar1.business.dtos.ListBrandDto;
import turkcell.rentacar1.business.requests.creates.CreateBrandRequest;
import turkcell.rentacar1.business.requests.deletes.DeleteBrandRequest;
import turkcell.rentacar1.business.requests.updates.UpdateBrandRequest;
import turkcell.rentacar1.core.utilities.results.DataResult;
import turkcell.rentacar1.core.utilities.results.Result;

@RestController
@RequestMapping("/api/brands")
public class BrandsController {
	
	private BrandService brandService;

	@Autowired
	public BrandsController(BrandService brandService) {
	
		this.brandService = brandService;
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody CreateBrandRequest createBrandRequest){
		return this.brandService.add(createBrandRequest);
	}
	
	@DeleteMapping("/delete")
	public Result delete(@RequestBody DeleteBrandRequest deleteBrandRequest){
		return this.brandService.delete(deleteBrandRequest);
	}
	
	@PutMapping("/update")
	public Result update(@RequestBody UpdateBrandRequest updateBrandRequest){
		return this.brandService.update(updateBrandRequest);
	}
	
	@GetMapping("/getall")
	public DataResult<List<ListBrandDto>> getAll(){
		return brandService.getAll();
	}
	
	@GetMapping("/getbrandid")
	public DataResult<GetListBrandDto> getByBrandId(@RequestParam @Valid int brandId) {
		return this.brandService.getByBrandId(brandId);
	}


}
