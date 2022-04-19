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

import turkcell.rentacar1.business.abstracts.OrderedAdditionalServiceService;
import turkcell.rentacar1.business.dtos.GetListOrderedAdditionalServiceDto;
import turkcell.rentacar1.business.dtos.ListOrderedAdditionalServiceDto;
import turkcell.rentacar1.business.requests.creates.CreateOrderedAdditionalServiceRequest;
import turkcell.rentacar1.business.requests.deletes.DeleteOrderedAdditionalServiceRequest;
import turkcell.rentacar1.business.requests.updates.UpdateOrderedAdditionalServiceRequest;
import turkcell.rentacar1.core.utilities.results.DataResult;
import turkcell.rentacar1.core.utilities.results.Result;

@RestController
@RequestMapping("/api/orderedAdditionalServiceControllers")
public class OrderedAdditionalServicesController {
	
	private OrderedAdditionalServiceService orderedAdditionalServiceService;

	public OrderedAdditionalServicesController(OrderedAdditionalServiceService orderedAdditionalServiceService) {
		this.orderedAdditionalServiceService = orderedAdditionalServiceService;
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest) {
		return this.orderedAdditionalServiceService.add(createOrderedAdditionalServiceRequest);
	}
	
	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteOrderedAdditionalServiceRequest deleteOrderedAdditionalServiceRequest) {
		return this.orderedAdditionalServiceService.delete(deleteOrderedAdditionalServiceRequest);
	}
	
	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest) {
		return this.orderedAdditionalServiceService.update(updateOrderedAdditionalServiceRequest);
	}
	
	@GetMapping("/getAll")
	public DataResult<List<ListOrderedAdditionalServiceDto>> getAll(){
		return this.orderedAdditionalServiceService.getAll();
	}
	
	@GetMapping("/getbyid")
	public DataResult<GetListOrderedAdditionalServiceDto> getById(int orderedId){
		return this.orderedAdditionalServiceService.getById(orderedId);
	}

}
