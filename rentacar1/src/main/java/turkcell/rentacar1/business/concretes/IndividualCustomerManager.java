package turkcell.rentacar1.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import turkcell.rentacar1.business.abstracts.IndividualCustomerService;
import turkcell.rentacar1.business.dtos.ListIndividualCustomerDto;
import turkcell.rentacar1.business.requests.creates.CreateIndividualCustomerRequest;
import turkcell.rentacar1.business.requests.deletes.DeleteIndividualCustomerRequest;
import turkcell.rentacar1.business.requests.updates.UpdateIndividualCustomerRequest;
import turkcell.rentacar1.core.concretes.BusinessException;
import turkcell.rentacar1.core.utilities.mapping.ModelMapperService;
import turkcell.rentacar1.core.utilities.results.DataResult;
import turkcell.rentacar1.core.utilities.results.Result;
import turkcell.rentacar1.core.utilities.results.SuccessDataResult;
import turkcell.rentacar1.core.utilities.results.SuccessResult;
import turkcell.rentacar1.dataAccess.abstracts.IndividualCustomerDao;
import turkcell.rentacar1.entities.concretes.IndividualCustomer;

@Service
public class IndividualCustomerManager implements IndividualCustomerService{
	
	private IndividualCustomerDao individualCustomerDao;
	private ModelMapperService modelMapperService;
	
	@Autowired
	public IndividualCustomerManager(IndividualCustomerDao individualCustomerDao,
			ModelMapperService modelMapperService) {
		this.individualCustomerDao = individualCustomerDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) {
		
		checkIfExistByEmail(createIndividualCustomerRequest.getEmail());
		checkIfExistByIdentityNumber(createIndividualCustomerRequest.getIdentityNumber());
		
		IndividualCustomer individualCustomer = this.modelMapperService.forRequest().map(createIndividualCustomerRequest, IndividualCustomer.class);
		this.individualCustomerDao.save(individualCustomer);
		
		return new SuccessResult("IndividualCustomer eklendi");
	}

	@Override
	public Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) {
		checkIfExistByEmail(deleteIndividualCustomerRequest.getEmail());
		
		this.individualCustomerDao.delete(this.individualCustomerDao.getByEmail(deleteIndividualCustomerRequest.getEmail()));
		
		return new SuccessResult("IndividualCustomer silindi");
	}

	@Override
	public Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) {
		
		checkIfExistByEmail(updateIndividualCustomerRequest.getEmail());
		checkIfExistByIdentityNumber(updateIndividualCustomerRequest.getIdentityNumber());
		
		IndividualCustomer individualCustomer= this.modelMapperService.forRequest().map(updateIndividualCustomerRequest, IndividualCustomer.class);
		individualCustomer.setUserId(this.individualCustomerDao.getByEmail(updateIndividualCustomerRequest.getEmail()).getUserId());
		
		this.individualCustomerDao.save(individualCustomer);
		
		return new SuccessResult("IndividualCustomer.update");
	}

	@Override
	public DataResult<List<ListIndividualCustomerDto>> getAll() {
		
		var result = this.individualCustomerDao.findAll();
		List<ListIndividualCustomerDto> response = result.stream().map(individualCustomer -> this.modelMapperService.forDto().map(individualCustomer, ListIndividualCustomerDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListIndividualCustomerDto>>(response);
	}

	private boolean checkIfExistByEmail(String email) {
		var result = this.individualCustomerDao.getByEmail(email);
		if(result==null) {
			throw new BusinessException("Bu maile sahip IndividualCustomer bulunamadı");
		}
		return true;
	}
	private boolean checkIfExistByIdentityNumber(String identityNumber) {
		var result = this.individualCustomerDao.getByIdentityNumber(identityNumber);
		if(result==null) {
			throw new BusinessException("Bu identityNumberda bir individualCustomer bulunamadı");
		}
		return true;
	}
	

}
