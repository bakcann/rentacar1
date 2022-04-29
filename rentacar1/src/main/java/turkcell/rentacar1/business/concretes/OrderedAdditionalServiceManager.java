package turkcell.rentacar1.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import turkcell.rentacar1.business.abstracts.AdditionalServiceService;
import turkcell.rentacar1.business.abstracts.OrderedAdditionalServiceService;
import turkcell.rentacar1.business.abstracts.RentalService;
import turkcell.rentacar1.business.constants.BusinessMessages;
import turkcell.rentacar1.business.dtos.GetListOrderedAdditionalServiceDto;
import turkcell.rentacar1.business.dtos.ListOrderedAdditionalServiceDto;
import turkcell.rentacar1.business.requests.creates.CreateOrderedAdditionalServiceRequest;
import turkcell.rentacar1.business.requests.deletes.DeleteOrderedAdditionalServiceRequest;
import turkcell.rentacar1.business.requests.updates.UpdateOrderedAdditionalServiceRequest;
import turkcell.rentacar1.core.concretes.BusinessException;
import turkcell.rentacar1.core.utilities.mapping.ModelMapperService;
import turkcell.rentacar1.core.utilities.results.DataResult;
import turkcell.rentacar1.core.utilities.results.Result;
import turkcell.rentacar1.core.utilities.results.SuccessDataResult;
import turkcell.rentacar1.core.utilities.results.SuccessResult;
import turkcell.rentacar1.dataAccess.abstracts.OrderedAdditionalServiceDao;
import turkcell.rentacar1.entities.concretes.OrderedAdditionalService;


@Service
public class OrderedAdditionalServiceManager implements OrderedAdditionalServiceService {
	
	private ModelMapperService modelMapperService;
	private OrderedAdditionalServiceDao orderedAdditionalServiceDao;
	private RentalService rentalService;
	private AdditionalServiceService additionalServiceService;

	@Autowired
	public OrderedAdditionalServiceManager(ModelMapperService modelMapperService,
			OrderedAdditionalServiceDao orderedAdditionalServiceDao, RentalService rentalService,
			AdditionalServiceService additionalServiceService ) {
		this.modelMapperService = modelMapperService;
		this.orderedAdditionalServiceDao = orderedAdditionalServiceDao;
		this.additionalServiceService = additionalServiceService;
		this.rentalService = rentalService;
	}

	@Override
	public Result add(CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest) {
		
	checkIfExistsRentalByRentalId(createOrderedAdditionalServiceRequest.getRentId());
	checkIfExistsAdditionalServiceId(createOrderedAdditionalServiceRequest.getAdditionalServiceId());
	checkIfExistsAdditionalServiceInRent(createOrderedAdditionalServiceRequest.getRentId(), createOrderedAdditionalServiceRequest.getAdditionalServiceId());
	
	OrderedAdditionalService orderedAdditionalService = this.modelMapperService.forRequest().map(createOrderedAdditionalServiceRequest, OrderedAdditionalService.class);
	
	this.orderedAdditionalServiceDao.save(orderedAdditionalService);
	
		return new SuccessResult(BusinessMessages.ORDEREDADDITIONALSERVICEADDED);
	}

	@Override
	public Result delete(DeleteOrderedAdditionalServiceRequest deleteOrderedAdditionalServiceRequest) {
		checkIfExistsOrderedAdditionalServiceId(deleteOrderedAdditionalServiceRequest.getOrderId());
		
		this.orderedAdditionalServiceDao.deleteById(deleteOrderedAdditionalServiceRequest.getOrderId());
		
		return new SuccessResult(BusinessMessages.ORDEREDADDITIONALSERVICEDELETED);
		
		
	}

	@Override
	public Result update(UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest) {
		
		checkIfExistsOrderedAdditionalServiceId(updateOrderedAdditionalServiceRequest.getOrderedId());
		checkIfExistsRentalByRentalId(updateOrderedAdditionalServiceRequest.getRentId());
		checkIfExistsAdditionalServiceId(updateOrderedAdditionalServiceRequest.getAdditionalServiceId());
		checkIfNotExistsAdditionalServiceInRent(this.orderedAdditionalServiceDao.getByOrderedId(updateOrderedAdditionalServiceRequest.getOrderedId()).getRental().getRentId(), updateOrderedAdditionalServiceRequest.getAdditionalServiceId());
		
		OrderedAdditionalService orderedAdditionalService = this.orderedAdditionalServiceDao.getById(updateOrderedAdditionalServiceRequest.getOrderedId());
		orderedAdditionalService.setAdditionalService(this.additionalServiceService.additionalServiceForOrder(updateOrderedAdditionalServiceRequest.getAdditionalServiceId()));
	
		
		return new SuccessResult(BusinessMessages.ORDEREDADDITIONALSERVICEUPDATED);
	}
	
	@Override
	public DataResult<List<ListOrderedAdditionalServiceDto>> getAll() {
		var result = this.orderedAdditionalServiceDao.findAll();
		 
		List<ListOrderedAdditionalServiceDto> response =result.stream()
											.map(orderedAdditionalService -> this.modelMapperService.forDto()
														.map(orderedAdditionalService, ListOrderedAdditionalServiceDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<ListOrderedAdditionalServiceDto>>(response,BusinessMessages.SUCCESS);
	}

	@Override
	public DataResult<GetListOrderedAdditionalServiceDto> getById(int orderedId) {
		
		checkIfExistsOrderedAdditionalServiceId(orderedId);
		
		var result = this.orderedAdditionalServiceDao.getByOrderedId(orderedId);
		
		GetListOrderedAdditionalServiceDto response = this.modelMapperService.forDto().map(result, GetListOrderedAdditionalServiceDto.class);
		
		return new SuccessDataResult<GetListOrderedAdditionalServiceDto>(response,BusinessMessages.SUCCESS);
	}
	
	@Override
	public boolean checkIfExistsRentalByRentalId(int rentId) {
		this.rentalService.checkIfExistsRentalId(rentId);
		return true;
	}
	
	private boolean checkIfNotExistsAdditionalServiceInRent(int rentId, int additionalServiceId) {
		var result = this.orderedAdditionalServiceDao.getByRental_RentIdAndAdditionalService_AdditionalServiceId(rentId, additionalServiceId);
		if(result ==null) {
			throw new BusinessException(BusinessMessages.ORDEREDADDITIONALSERVICENOTEXISTSADDITIONALSERVICEINRENT);
		}
		return true;
	}
	
	private boolean checkIfExistsAdditionalServiceInRent(int rentId, int additionalServiceId ) {
		var result = this.orderedAdditionalServiceDao.getByRental_RentIdAndAdditionalService_AdditionalServiceId(rentId, additionalServiceId);
		
		if(result==null) {
			return true;
		}
		throw new BusinessException(BusinessMessages.ORDEREDADDITIONALSERVICEEXISTSADDITIONALSERVICEINRENT);
	}
	
	private boolean checkIfExistsAdditionalServiceId(int additionalServiceId) {
		this.additionalServiceService.checkIfExistByAdditionalServiceId(additionalServiceId);
		return true;
		
	}
	
	private boolean checkIfExistsOrderedAdditionalServiceId(int orderedId) {
		 var result = this.orderedAdditionalServiceDao.getByOrderedId(orderedId);
		 if(result== null) {
			 throw new BusinessException(BusinessMessages.ORDEREDADDITIONALSERVICENOTFOUND);
		 }
		 return true;
	}
	
	
	
	

}
