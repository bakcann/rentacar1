package turkcell.rentacar1.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import turkcell.rentacar1.business.abstracts.AdditionalServiceService;
import turkcell.rentacar1.business.abstracts.OrderedAdditionalServiceService;
import turkcell.rentacar1.business.abstracts.RentalService;
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
		
	checkIfExistRentalByRentalId(createOrderedAdditionalServiceRequest.getRentId());
	checkIfExistAdditionalServiceId(createOrderedAdditionalServiceRequest.getAdditionalServiceId());
	checkIfExistAdditionalServiceInRent(createOrderedAdditionalServiceRequest.getRentId(), createOrderedAdditionalServiceRequest.getAdditionalServiceId());
	
	OrderedAdditionalService orderedAdditionalService = this.modelMapperService.forRequest().map(createOrderedAdditionalServiceRequest, OrderedAdditionalService.class);
	
	this.orderedAdditionalServiceDao.save(orderedAdditionalService);
	
		return new SuccessResult("Ordered AddtionalService eklendi");
	}

	@Override
	public Result delete(DeleteOrderedAdditionalServiceRequest deleteOrderedAdditionalServiceRequest) {
		checkIfExistOrderedAdditionalServiceId(deleteOrderedAdditionalServiceRequest.getOrderId());
		
		this.orderedAdditionalServiceDao.deleteById(deleteOrderedAdditionalServiceRequest.getOrderId());
		
		return new SuccessResult("Bu OrderedAdditionalService silindi");
		
		
	}

	@Override
	public Result update(UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest) {
		
		checkIfExistOrderedAdditionalServiceId(updateOrderedAdditionalServiceRequest.getOrderedId());
		checkIfExistAdditionalServiceId(updateOrderedAdditionalServiceRequest.getAdditionalServiceId());
		checkIfExistAdditionalServiceInRent(this.orderedAdditionalServiceDao.getByOrderedId(updateOrderedAdditionalServiceRequest.getOrderedId()).getRental().getRentId(), updateOrderedAdditionalServiceRequest.getAdditionalServiceId());
		
		OrderedAdditionalService orderedAdditionalService = this.orderedAdditionalServiceDao.getById(updateOrderedAdditionalServiceRequest.getOrderedId());
		orderedAdditionalService.setAdditionalService(this.additionalServiceService.additionalServiceForOrder(updateOrderedAdditionalServiceRequest.getAdditionalServiceId()));
	
		
		return null;
	}
	
	@Override
	public DataResult<List<ListOrderedAdditionalServiceDto>> getAll() {
		var result = this.orderedAdditionalServiceDao.findAll();
		 
		List<ListOrderedAdditionalServiceDto> response =result.stream()
											.map(orderedAdditionalService -> this.modelMapperService.forDto()
														.map(orderedAdditionalService, ListOrderedAdditionalServiceDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<ListOrderedAdditionalServiceDto>>(response,"Success");
	}

	@Override
	public DataResult<GetListOrderedAdditionalServiceDto> getById(int orderedId) {
		checkIfExistOrderedAdditionalServiceId(orderedId);
		
		var result = this.orderedAdditionalServiceDao.getByOrderedId(orderedId);
		
		GetListOrderedAdditionalServiceDto response = this.modelMapperService.forDto().map(result, GetListOrderedAdditionalServiceDto.class);
		
		return new SuccessDataResult<GetListOrderedAdditionalServiceDto>(response);
	}
	
	@Override
	public boolean checkIfExistRentalByRentalId(int rentId) {
		this.rentalService.checkIfExistRentalId(rentId);
		return true;
	}
	
	private boolean checkIfExistAdditionalServiceInRent(int rentId, int additionalServiceId ) {
		var result = this.orderedAdditionalServiceDao.getByRental_RentIdAndAdditionalService_AdditionalServiceId(rentId, additionalServiceId);
		
		if(result==null) {
			return true;
		}
		throw new BusinessException("Bu kiralamaya additional service daha önceden eklenmiştir.");
	}
	
	private boolean checkIfExistAdditionalServiceId(int additionalServiceId) {
		this.additionalServiceService.checkIfExistByAdditionalServiceId(additionalServiceId);
		return true;
		
	}
	
	private boolean checkIfExistOrderedAdditionalServiceId(int orderedId) {
		 var result = this.orderedAdditionalServiceDao.getByOrderedId(orderedId);
		 if(result== null) {
			 throw new BusinessException("Böyle bir OrderedAdditionalService yok");
		 }
		 return true;
	}
	
	

}
