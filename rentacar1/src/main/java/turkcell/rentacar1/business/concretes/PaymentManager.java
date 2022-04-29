package turkcell.rentacar1.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import turkcell.rentacar1.business.abstracts.CardService;
import turkcell.rentacar1.business.abstracts.InvoiceService;
import turkcell.rentacar1.business.abstracts.OrderedAdditionalServiceService;
import turkcell.rentacar1.business.abstracts.PaymentService;
import turkcell.rentacar1.business.abstracts.PosService;
import turkcell.rentacar1.business.abstracts.RentalService;
import turkcell.rentacar1.business.constants.BusinessMessages;
import turkcell.rentacar1.business.dtos.GetListPaymentDto;
import turkcell.rentacar1.business.dtos.ListPaymentDto;
import turkcell.rentacar1.business.requests.creates.CreateCardRequest;
import turkcell.rentacar1.business.requests.creates.CreateInvoiceRequest;
import turkcell.rentacar1.business.requests.creates.CreateOrderedAdditionalServiceRequest;
import turkcell.rentacar1.business.requests.creates.CreateOrderedAdditionalServiceRequestForTransactional;
import turkcell.rentacar1.business.requests.creates.CreatePaymentExtraRequest;
import turkcell.rentacar1.business.requests.creates.CreatePaymentRequest;
import turkcell.rentacar1.business.requests.creates.CreateRentalRequest;
import turkcell.rentacar1.business.requests.updates.UpdateRentalRequest;
import turkcell.rentacar1.core.concretes.BusinessException;
import turkcell.rentacar1.core.utilities.mapping.ModelMapperService;
import turkcell.rentacar1.core.utilities.results.DataResult;
import turkcell.rentacar1.core.utilities.results.ErrorResult;
import turkcell.rentacar1.core.utilities.results.Result;
import turkcell.rentacar1.core.utilities.results.SuccessDataResult;
import turkcell.rentacar1.core.utilities.results.SuccessResult;
import turkcell.rentacar1.dataAccess.abstracts.PaymentDao;
import turkcell.rentacar1.entities.concretes.Invoice;
import turkcell.rentacar1.entities.concretes.Payment;
import turkcell.rentacar1.entities.concretes.Rental;

@Service
public class PaymentManager implements PaymentService  {
	
	private PaymentDao paymentDao;
	private ModelMapperService modelMapperService;
	private RentalService rentalService;
	private OrderedAdditionalServiceService orderedAdditionalServiceService;
	private InvoiceService invoiceService;
	private PosService posService;
	private CardService cardService;
	
	public PaymentManager(PaymentDao paymentDao, ModelMapperService modelMapperService, RentalService rentalService,
			OrderedAdditionalServiceService orderedAdditionalServiceService,
			InvoiceService invoiceService,@Lazy PosService posService, CardService cardService) {
		this.paymentDao = paymentDao;
		this.modelMapperService = modelMapperService;
		this.rentalService = rentalService;
		this.orderedAdditionalServiceService = orderedAdditionalServiceService;
		this.invoiceService = invoiceService;
		this.posService = posService;
		this.cardService = cardService;
	}

	@Override
	@Transactional(rollbackFor = BusinessException.class)
	public Result add(CreatePaymentRequest createPaymentRequest) {
		Rental rental = addToRental(createPaymentRequest.getCreateRentalRequest());
		
		addToOrderedAdditionalService(rental.getRentId(),createPaymentRequest.getCreateOrderedAdditionalServiceRequestForTransactional());
		
		Invoice invoice = addToInvoice(rental, createPaymentRequest.getCreateInvoiceRequest());
		
		addToCard(createPaymentRequest.isSaveCard(), invoice.getCustomer().getCustomerId(), createPaymentRequest.getCreateCardRequest().getCardOwnerName(),
								createPaymentRequest.getCreateCardRequest().getCardNumber(),createPaymentRequest.getCreateCardRequest().getCardCvvNumber());
		
		toSendPosService(createPaymentRequest.getCreateCardRequest().getCardOwnerName(), createPaymentRequest.getCreateCardRequest().getCardNumber(),
								createPaymentRequest.getCreateCardRequest().getCardCvvNumber());
		
		Payment payment = toSetPayment(rental, invoice);
		this.paymentDao.save(payment);
		return new SuccessResult(BusinessMessages.PAYMENTADDED);
	}

	@Override
	@Transactional(rollbackFor = BusinessException.class)
	public Result addForExtra(CreatePaymentExtraRequest createPaymentExtraRequest) {
		Rental rental = updateToRental(createPaymentExtraRequest.getUpdateRentalRequest());
		
		addToOrderedAdditionalService(rental.getRentId(), createPaymentExtraRequest.getCreateOrderedAdditionalServiceRequestForTransactional());
		
		Invoice invoice = addToInvoice(rental, createPaymentExtraRequest.getCreateInvoiceRequest());
		
		if(invoice==null) {
			return new ErrorResult(BusinessMessages.PAYMENTADDERROR);
		}
		
		addToCard(createPaymentExtraRequest.isSaveCard(), invoice.getCustomer().getCustomerId(), createPaymentExtraRequest.getCreateCard().getCardOwnerName(), 
								createPaymentExtraRequest.getCreateCard().getCardNumber(), createPaymentExtraRequest.getCreateCard().getCardCvvNumber());
		
		toSendPosService(createPaymentExtraRequest.getCreateCard().getCardOwnerName(), createPaymentExtraRequest.getCreateCard().getCardNumber(),
								createPaymentExtraRequest.getCreateCard().getCardCvvNumber());
		
		Payment payment =toSetPayment(rental, invoice);
		this.paymentDao.save(payment);
		return new SuccessResult(BusinessMessages.PAYMENTADDED);
	}

	@Override
	public DataResult<List<ListPaymentDto>> getAll() {
		
		var result = this.paymentDao.findAll();
		
		List<ListPaymentDto> response = result.stream().map(payment -> this.modelMapperService.forDto().map(payment, ListPaymentDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<ListPaymentDto>>(response,BusinessMessages.SUCCESS);
	}

	@Override
	public DataResult<GetListPaymentDto> getById(int paymentId) {
		checkIfPaymentExists(paymentId);
		
		var result = this.paymentDao.getById(paymentId);
		
		GetListPaymentDto response = this.modelMapperService.forDto().map(result, GetListPaymentDto.class);
		
		return new SuccessDataResult<GetListPaymentDto>(response,BusinessMessages.SUCCESS);
	}
	private Payment checkIfPaymentExists(int paymentId) {
		
		var result = this.paymentDao.getByPaymentId(paymentId);
		
		if(result== null) {
			
			throw new BusinessException(BusinessMessages.PAYMENTNOTFOUND);
		}
		return result;
	}
	
	private void toSendPosService(String cardOwnerName, String cardNumber, int cardCvvNumber) {
		
		this.posService.payments(cardOwnerName, cardNumber, cardCvvNumber);
	}
	
	private Rental addToRental(CreateRentalRequest createRentalRequest) {
		
		return this.rentalService.add(createRentalRequest).getData();
	}
	
	private Rental updateToRental(UpdateRentalRequest updateRentalRequest) {
		
		return this.rentalService.update(updateRentalRequest).getData();
	}
	
	private Invoice addToInvoice(Rental rental, CreateInvoiceRequest createInvoiceRequest) {
		
		this.modelMapperService.forRequest().map(createInvoiceRequest, CreateInvoiceRequest.class);
		
		createInvoiceRequest.setRental(createInvoiceRequest.getRental());
		
		return this.invoiceService.add(createInvoiceRequest).getData();
	}
	
	private void addToOrderedAdditionalService(int rentId, CreateOrderedAdditionalServiceRequestForTransactional createOrderedAdditionalServiceRequestForTransactional) {
		
		CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest = new CreateOrderedAdditionalServiceRequest();
		
		createOrderedAdditionalServiceRequest.setRentId(rentId);
		
		for(int i=0; i<createOrderedAdditionalServiceRequestForTransactional.getAdditionalServiceIds().size(); i++) {
			
			createOrderedAdditionalServiceRequest.setAdditionalServiceId(createOrderedAdditionalServiceRequestForTransactional.getAdditionalServiceIds().get(i));	
			
			this.orderedAdditionalServiceService.add(createOrderedAdditionalServiceRequest);
		}
	}
	
	private void addToCard(boolean saveCard, int customerId, String cardOwnerName, String cardNumber, int cardCvvNumber) {
		if(saveCard) {
			CreateCardRequest createCardRequest = new CreateCardRequest();
			createCardRequest.setCardNumber(cardNumber);			
			createCardRequest.setCardOwnerName(cardOwnerName);
			createCardRequest.setCardCvvNumber(cardCvvNumber);
			createCardRequest.setCustomerId(customerId);
			this.cardService.add(createCardRequest);			}
	}
	
	private Payment toSetPayment(Rental rental, Invoice invoice) {
		
		Payment payment = new Payment();
		
		payment.setInvoice(invoice);
		
		payment.setRental(rental);
		
		return payment;
	}

}
