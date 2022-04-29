package turkcell.rentacar1.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import turkcell.rentacar1.business.abstracts.PaymentService;
import turkcell.rentacar1.business.dtos.GetListPaymentDto;
import turkcell.rentacar1.business.dtos.ListPaymentDto;
import turkcell.rentacar1.business.requests.creates.CreatePaymentExtraRequest;
import turkcell.rentacar1.business.requests.creates.CreatePaymentRequest;
import turkcell.rentacar1.core.utilities.results.DataResult;
import turkcell.rentacar1.core.utilities.results.Result;

@RestController
@RequestMapping("/api/payments")
public class PaymentsController {
	
	PaymentService paymentService;
	
	@Autowired
	public PaymentsController(PaymentService paymentService) {
		this.paymentService = paymentService;
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreatePaymentRequest createPaymentRequest) {
		return this.paymentService.add(createPaymentRequest);
	}
	
	@PostMapping("/addextra")
	public Result addExtra(@RequestBody @Valid CreatePaymentExtraRequest createPaymentExtraRequest) {
		return this.paymentService.addForExtra(createPaymentExtraRequest);
	}
	
	@GetMapping("/getall")
	public DataResult<List<ListPaymentDto>> getAll(){
		return this.paymentService.getAll();
	}
	
	@GetMapping("/getbyid")
	public DataResult<GetListPaymentDto> getById(@RequestParam int paymentId){
		return this.paymentService.getById(paymentId);
	}

}
