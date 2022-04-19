package turkcell.rentacar1.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import turkcell.rentacar1.business.abstracts.CorporateCustomerService;
import turkcell.rentacar1.business.dtos.ListCorporateCustomerDto;
import turkcell.rentacar1.business.requests.creates.CreateCorporateCustomerRequest;
import turkcell.rentacar1.business.requests.deletes.DeleteCorporateCustomerRequest;
import turkcell.rentacar1.business.requests.updates.UpdateCorporateCustomerRequest;
import turkcell.rentacar1.core.concretes.BusinessException;
import turkcell.rentacar1.core.utilities.mapping.ModelMapperService;
import turkcell.rentacar1.core.utilities.results.DataResult;
import turkcell.rentacar1.core.utilities.results.Result;
import turkcell.rentacar1.core.utilities.results.SuccessDataResult;
import turkcell.rentacar1.core.utilities.results.SuccessResult;
import turkcell.rentacar1.dataAccess.abstracts.CorporateCustomerDao;
import turkcell.rentacar1.entities.concretes.CorporateCustomer;

@Service
public class CorporateCustomerManager implements CorporateCustomerService {
	
	private CorporateCustomerDao corporateCustomerDao;
	private ModelMapperService modelMapperService;
	
	@Autowired
	public CorporateCustomerManager(CorporateCustomerDao corporateCustomerDao, ModelMapperService modelMapperService) {
		this.corporateCustomerDao = corporateCustomerDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) {
		
		checkIfExistByEmail(createCorporateCustomerRequest.getEmail());
		checkIfExistByTaxNumber(createCorporateCustomerRequest.getTaxNumber());
		CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(createCorporateCustomerRequest, CorporateCustomer.class);
		
		this.corporateCustomerDao.save(corporateCustomer);
		return new SuccessResult("CorporateCustomer eklendi");
	}

	@Override
	public Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) {
		
		checkIfExistByEmail(deleteCorporateCustomerRequest.getEmail());
		
		this.corporateCustomerDao.delete(this.corporateCustomerDao.getByEmail(deleteCorporateCustomerRequest.getEmail()));
		
		return new SuccessResult("CorporateCustomer silindi");
	}

	@Override
	public Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) {
		
		checkIfExistByEmail(updateCorporateCustomerRequest.getEmail());
		checkIfExistByTaxNumber(updateCorporateCustomerRequest.getTaxNumber());
		
		CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(updateCorporateCustomerRequest, CorporateCustomer.class);
		corporateCustomer.setUserId(this.corporateCustomerDao.getByTaxNumber(updateCorporateCustomerRequest.getTaxNumber()).getUserId());
		
		this.corporateCustomerDao.save(corporateCustomer);
		
		return new SuccessResult("CorporateCustomer güncellendi.");
	}

	@Override
	public DataResult<List<ListCorporateCustomerDto>> getAll() {
		
		var result = this.corporateCustomerDao.findAll();
		
		List<ListCorporateCustomerDto> response = result.stream().map(corporateCustomer -> this.modelMapperService.forDto().map(corporateCustomer, ListCorporateCustomerDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListCorporateCustomerDto>>(response, "Success");
	}

	
	
	private boolean checkIfExistByEmail(String email) {
		
		var result = this.corporateCustomerDao.getByEmail(email);
		
		if(result==null) {
			throw new BusinessException("Bu maile sahip Corporate Custoomer bulunamadı");
		}
		return true;
	}
	
	private boolean checkIfExistByTaxNumber(String taxNumber) {
		
		var result = this.corporateCustomerDao.getByTaxNumber(taxNumber);
		
		if(result== null) {
			throw new BusinessException("Bu tax numbera sahip Corporate customer bulunamadı.");
		}
		return true;
	}
	
}
