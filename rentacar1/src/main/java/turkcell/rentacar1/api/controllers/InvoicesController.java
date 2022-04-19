package turkcell.rentacar1.api.controllers;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import turkcell.rentacar1.business.abstracts.InvoiceService;
import turkcell.rentacar1.business.dtos.GetListInvoiceDto;
import turkcell.rentacar1.business.dtos.ListInvoiceDto;
import turkcell.rentacar1.business.requests.creates.CreateInvoiceRequest;
import turkcell.rentacar1.business.requests.deletes.DeleteInvoiceRequest;
import turkcell.rentacar1.business.requests.updates.UpdateInvoiceRequest;
import turkcell.rentacar1.core.utilities.results.DataResult;
import turkcell.rentacar1.core.utilities.results.Result;

@RestController
@RequestMapping("/api/invoices")
public class InvoicesController {
	
	InvoiceService invoiceService;
	
	@Autowired
	public InvoicesController(InvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateInvoiceRequest createInvoiceRequest) {
		return this.invoiceService.add(createInvoiceRequest);
	}
	
	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteInvoiceRequest deleteInvoiceRequest) {
		return this.invoiceService.delete(deleteInvoiceRequest);
	}
	
	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateInvoiceRequest updateInvoiceRequest) {
		return this.invoiceService.update(updateInvoiceRequest);
	}
	
	@GetMapping("/getall")
	public DataResult<List<ListInvoiceDto>> getAll(){
		return this.invoiceService.getAll();
	}
	
	@GetMapping("/getbyinvoiceid")
	public DataResult<GetListInvoiceDto> getById(@RequestParam int invoiceId){
		return this.invoiceService.getById(invoiceId);
	}
	
	@GetMapping("/getbydate")
	public DataResult<List<ListInvoiceDto>> getByCreateDateBetween(@RequestParam @DateTimeFormat(pattern ="yyyy-MM-dd")LocalDate starDate, @RequestParam@DateTimeFormat(pattern ="yyyy-MM-dd")LocalDate finisDate){
		return this.invoiceService.getByCreateDateBetween(starDate, finisDate);
	}
	
	@GetMapping("/getbycustomer")
	public DataResult<List<ListInvoiceDto>> getInvoiceByCustomer(@RequestParam int customerId){
		return this.invoiceService.getInvoiceByCustomer(customerId);
	}
	

}
