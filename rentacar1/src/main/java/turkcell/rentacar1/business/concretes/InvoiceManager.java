package turkcell.rentacar1.business.concretes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import turkcell.rentacar1.business.abstracts.CustomerService;
import turkcell.rentacar1.business.abstracts.InvoiceService;
import turkcell.rentacar1.business.abstracts.RentalService;
import turkcell.rentacar1.business.constants.BusinessMessages;
import turkcell.rentacar1.business.dtos.GetListInvoiceDto;
import turkcell.rentacar1.business.dtos.ListInvoiceDto;
import turkcell.rentacar1.business.requests.creates.CreateInvoiceRequest;
import turkcell.rentacar1.business.requests.deletes.DeleteInvoiceRequest;
import turkcell.rentacar1.business.requests.updates.UpdateInvoiceRequest;
import turkcell.rentacar1.core.concretes.BusinessException;
import turkcell.rentacar1.core.utilities.mapping.ModelMapperService;
import turkcell.rentacar1.core.utilities.results.DataResult;
import turkcell.rentacar1.core.utilities.results.ErrorDataResult;
import turkcell.rentacar1.core.utilities.results.Result;
import turkcell.rentacar1.core.utilities.results.SuccessDataResult;
import turkcell.rentacar1.core.utilities.results.SuccessResult;
import turkcell.rentacar1.dataAccess.abstracts.InvoiceDao;
import turkcell.rentacar1.entities.concretes.Invoice;

@Service
public class InvoiceManager implements InvoiceService{
	
	private InvoiceDao invoiceDao;
	private ModelMapperService modelMapperService;
	private CustomerService customerService;
	private RentalService rentalService;
	
	@Autowired
	public InvoiceManager(InvoiceDao invoiceDao, ModelMapperService modelMapperService, CustomerService customerService,
			RentalService rentalService) {
		this.invoiceDao = invoiceDao;
		this.modelMapperService = modelMapperService;
		this.customerService = customerService;
		this.rentalService = rentalService;
	}

	@Override
	public DataResult<Invoice> add(CreateInvoiceRequest createInvoiceRequest) {
		checkIfNotExistsByInvoiceNo(createInvoiceRequest.getInvoiceNo());
		
		checkIfExistCustomer(createInvoiceRequest.getCustomerId());
		checkIfExistRental(createInvoiceRequest.getRental().getRentId());
		checkIfInvoiceExistForRental(createInvoiceRequest.getRental().getRentId());
		
		if(checkIfRentalCar_ReturnDateEqualPlannedReturnDate(createInvoiceRequest.getRental().getRentId())) {
			return new ErrorDataResult<Invoice>(null, BusinessMessages.INVOICEADDERROR);
		}
		Invoice invoice = setForAddMethod(createInvoiceRequest);
		this.invoiceDao.save(invoice);
		
		return new SuccessDataResult<Invoice>(invoice, BusinessMessages.INVOICEADDED);
	}

	@Override
	public Result delete(DeleteInvoiceRequest deleteInvoiceRequest) {
		
		checkIfExistByInvoiceId(deleteInvoiceRequest.getInvoiceId());
		
		this.invoiceDao.deleteById(deleteInvoiceRequest.getInvoiceId());
		
		return new SuccessResult(BusinessMessages.INVOICEDELETED);
	}

	@Override
	public Result update(UpdateInvoiceRequest updateInvoiceRequest) {
		
		checkIfExistByInvoiceId(updateInvoiceRequest.getInvoiceId());
		
		Invoice invoice = this.invoiceDao.getByInvoiceId(updateInvoiceRequest.getInvoiceId());
		invoice.setCreateDate(updateInvoiceRequest.getCreateDate());
		
		this.invoiceDao.save(invoice);
		
		return new SuccessResult(BusinessMessages.INVOICEUPDATED);
	}

	@Override
	public DataResult<List<ListInvoiceDto>> getAll() {
		
		var result = this.invoiceDao.findAll();
		
		List<ListInvoiceDto> response = result.stream().map(invoice -> this.modelMapperService.forDto().map(invoice, ListInvoiceDto.class)).collect(Collectors.toList());
		response = toSetReturnDateForGetAll(result, response);
		
		return new SuccessDataResult<List<ListInvoiceDto>>(response,BusinessMessages.SUCCESS);
	}

	@Override
	public DataResult<GetListInvoiceDto> getById(int invoiceId) {
		
		checkIfExistByInvoiceId(invoiceId);
		
		var result = this.invoiceDao.getByInvoiceId(invoiceId);
		
		GetListInvoiceDto response = this.modelMapperService.forDto().map(result, GetListInvoiceDto.class);
		
		return new SuccessDataResult<GetListInvoiceDto>(response, BusinessMessages.SUCCESS);
	}

	@Override
	public DataResult<List<ListInvoiceDto>> getByCreateDateBetween(LocalDate startDate, LocalDate endDate) {
		
		var result = this.invoiceDao.findByCreateDateBetween(startDate, endDate);
		
		List<ListInvoiceDto> response = result.stream().map(invoice -> this.modelMapperService.forDto().map(invoice, ListInvoiceDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListInvoiceDto>>(response,BusinessMessages.SUCCESS);
	}

	@Override
	public DataResult<List<ListInvoiceDto>> getInvoiceByCustomer(int customerId) {
		
		checkIfExistCustomer(customerId);
		
		var result = this.invoiceDao.getByCustomer_customerId(customerId);
		List<ListInvoiceDto> response = result.stream().map(invoice -> this.modelMapperService.forDto().map(invoice, ListInvoiceDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListInvoiceDto>>(response,BusinessMessages.SUCCESS);
	}
	
	
	private List<ListInvoiceDto> toSetReturnDateForGetAll(List<Invoice> result, List<ListInvoiceDto> response){
		for(int i= 0; i<response.size(); i++) {
			response.get(i).setReturnDate(result.get(i).getReturnDate());
			response.get(i).setRentId(result.get(i).getRental().getRentId());
			
		}
		return response ;
	}
	
	
	private boolean checkIfInvoiceExistForRental(int rentId) {
		var result= this.invoiceDao.getByRental_rentId(rentId);
		if(result!=null) {
			if(result.size()== 1 && this.rentalService.getByRentalId(rentId).getData().getRentReturnDate()== null || result.size() == 2) {
				throw new BusinessException(BusinessMessages.INVOICEEXISTSRENTAL);
			}
		}
		return true;
	}
	
	private boolean checkIfExistByInvoiceId(int invoiceId) {
		var result = this.invoiceDao.getByInvoiceId(invoiceId);
		if(result==null) {
			throw new BusinessException(BusinessMessages.INVOICENOTFOUND);
		}
		return true;
	}
	
	private boolean checkIfNotExistsByInvoiceNo(String invoiceNo) {
		var result = this.invoiceDao.getByInvoiceNo(invoiceNo);
		if(result== null) {
			return true;
		}
		throw new BusinessException(BusinessMessages.INVOICENOTEXISTSINVOICENO);
	}
	
	private boolean checkIfExistRental(int rentId) {
		this.rentalService.checkIfExistsRentalId(rentId);
		return true;
	}
	
	private boolean checkIfExistCustomer(int customerId) {
		this.customerService.checkIfExistCustomerById(customerId);
		return true;
	}
	
	private boolean checkIfRentalCar_ReturnDateEqualPlannedReturnDate(int rentId) {
		
		if(this.rentalService.getByRentalIdForOtherServices(rentId).getRentReturnDate()!= null && 
				this.rentalService.getByRentalIdForOtherServices(rentId)
				.getPlannedReturnDate().isEqual(this.rentalService.getByRentalIdForOtherServices(rentId).getRentReturnDate())){
			
					return false;
				}
				return true;
			}
	
	private Invoice setForAddMethod(CreateInvoiceRequest createInvoiceRequest) {
		
		var customer = this.customerService.getById(createInvoiceRequest.getCustomerId());
		
		Invoice invoice = new Invoice();
		
		invoice.setCustomer(customer);
		invoice.setInvoiceNo(createInvoiceRequest.getInvoiceNo());
		invoice.setRental(createInvoiceRequest.getRental());
		
		if(invoice.getRental().getRentReturnDate()== null) {
			
			invoice.setRentDate(createInvoiceRequest.getRental().getRentDate());
			invoice.setCreateDate(createInvoiceRequest.getRental().getRentDate());
			invoice.setReturnDate(createInvoiceRequest.getRental().getPlannedReturnDate());
			invoice.setTotalDay(ChronoUnit.DAYS.between(createInvoiceRequest.getRental().getPlannedReturnDate(), createInvoiceRequest.getRental().getRentReturnDate()));
			invoice.setRentTotalPrice(createInvoiceRequest.getRental().getTotalPrice());
		}
		else { double extraInvoicePrice = createInvoiceRequest.getRental().getTotalPrice() - this.invoiceDao.getByRental_rentId(createInvoiceRequest.getRental().getRentId()).get(0).getRentTotalPrice();
		
		invoice.setRentDate(createInvoiceRequest.getRental().getPlannedReturnDate());
		invoice.setCreateDate(createInvoiceRequest.getRental().getRentReturnDate());
		invoice.setReturnDate(createInvoiceRequest.getRental().getRentReturnDate());
		invoice.setTotalDay(ChronoUnit.DAYS.between(createInvoiceRequest.getRental().getPlannedReturnDate(), createInvoiceRequest.getRental().getRentReturnDate()));
		invoice.setRentTotalPrice(extraInvoicePrice);
		}
		return invoice;
	}
	}







