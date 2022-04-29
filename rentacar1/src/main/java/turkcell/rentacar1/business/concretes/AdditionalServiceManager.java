package turkcell.rentacar1.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import turkcell.rentacar1.business.abstracts.AdditionalServiceService;
import turkcell.rentacar1.business.constants.BusinessMessages;
import turkcell.rentacar1.business.dtos.GetListAdditionalServiceDto;
import turkcell.rentacar1.business.dtos.ListAdditionalServiceDto;
import turkcell.rentacar1.business.requests.creates.CreateAdditionalServiceRequest;
import turkcell.rentacar1.business.requests.deletes.DeleteAdditionalServiceRequest;
import turkcell.rentacar1.business.requests.updates.UpdateAdditionalServiceRequest;
import turkcell.rentacar1.core.concretes.BusinessException;
import turkcell.rentacar1.core.utilities.mapping.ModelMapperService;
import turkcell.rentacar1.core.utilities.results.DataResult;
import turkcell.rentacar1.core.utilities.results.Result;
import turkcell.rentacar1.core.utilities.results.SuccessDataResult;
import turkcell.rentacar1.core.utilities.results.SuccessResult;
import turkcell.rentacar1.dataAccess.abstracts.AdditionalServiceDao;
import turkcell.rentacar1.entities.concretes.AdditionalService;

@Service
public class AdditionalServiceManager implements AdditionalServiceService {
	
	private AdditionalServiceDao additionalServiceDao;
	private ModelMapperService modelMapperService;
	
	@Autowired
	public AdditionalServiceManager(AdditionalServiceDao additionalServiceDao, ModelMapperService modelMapperService) {
		this.additionalServiceDao = additionalServiceDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest) throws BusinessException {
		
		checkIfExistByAdditionalServiceName(createAdditionalServiceRequest.getAdditionalServiceName());
		
		AdditionalService additionalService = this.modelMapperService.forRequest()
				.map(createAdditionalServiceRequest, AdditionalService.class);
		this.additionalServiceDao.save(additionalService);
		
		return new SuccessResult(BusinessMessages.ADDITIONALSERVICEADDED);
	}

	@Override
	public Result delete(DeleteAdditionalServiceRequest deleteAdditionalServiceRequest) throws BusinessException {
		checkIfExistByAdditionalServiceId(deleteAdditionalServiceRequest.getAdditionalServiceId());
		
		this.additionalServiceDao.deleteById(deleteAdditionalServiceRequest.getAdditionalServiceId());
		
		return new SuccessResult(BusinessMessages.ADDITINALSERVICEDELETED);
	}

	@Override
	public Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest) throws BusinessException {
		checkIfExistByAdditionalServiceId(updateAdditionalServiceRequest.getAdditionalServiceId());
		checkIfExistByAdditionalServiceName(updateAdditionalServiceRequest.getAdditionalServiceName());
		AdditionalService additionalService = this.modelMapperService.forRequest()
				.map(updateAdditionalServiceRequest, AdditionalService.class);
		this.additionalServiceDao.save(additionalService);
		
		return new SuccessResult(BusinessMessages.ADDITIONALSERVICEUPDATED);
	}

	@Override
	public DataResult<List<ListAdditionalServiceDto>> getAll() {
		 var result = this.additionalServiceDao.findAll();
		List<ListAdditionalServiceDto> response =result.stream().map(additionalService -> this.modelMapperService.forDto()
														.map(additionalService, ListAdditionalServiceDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<ListAdditionalServiceDto>>(response, BusinessMessages.SUCCESS);
	}

	@Override
	public DataResult<GetListAdditionalServiceDto> getById(int additionalServiceId) {
		checkIfExistByAdditionalServiceId(additionalServiceId);
		var result = this.additionalServiceDao.getById(additionalServiceId);
		GetListAdditionalServiceDto response = this.modelMapperService.forDto().map(result,GetListAdditionalServiceDto.class);
		return new SuccessDataResult<GetListAdditionalServiceDto>(response,BusinessMessages.SUCCESS);
	}

	
	public boolean checkIfExistByAdditionalServiceId(int additionalServiceId)  {
		if(this.additionalServiceDao.getById(additionalServiceId)==null) {
			throw new BusinessException(BusinessMessages.ADDITIONALSERVICENOTFOUND);  
		}
		return true;
	}
	
	public boolean checkIfExistByAdditionalServiceName(String additionalServiceName) {
		if(this.additionalServiceDao.getByAdditionalServiceName(additionalServiceName)!=null) {
			throw new BusinessException(BusinessMessages.ADDITIONALSERVICEEXISTSNAME); 
		}
		return true;
		
	}
	public AdditionalService additionalServiceForOrder(int additionalServiceId) {
		return this.additionalServiceDao.getByAdditionalServiceId(additionalServiceId);
	}

}
