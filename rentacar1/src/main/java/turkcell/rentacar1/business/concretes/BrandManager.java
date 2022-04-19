package turkcell.rentacar1.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import turkcell.rentacar1.business.abstracts.BrandService;
import turkcell.rentacar1.business.dtos.GetListBrandDto;
import turkcell.rentacar1.business.dtos.ListBrandDto;
import turkcell.rentacar1.business.requests.creates.CreateBrandRequest;
import turkcell.rentacar1.business.requests.deletes.DeleteBrandRequest;
import turkcell.rentacar1.business.requests.updates.UpdateBrandRequest;
import turkcell.rentacar1.core.concretes.BusinessException;
import turkcell.rentacar1.core.utilities.mapping.ModelMapperService;
import turkcell.rentacar1.core.utilities.results.DataResult;
import turkcell.rentacar1.core.utilities.results.Result;
import turkcell.rentacar1.core.utilities.results.SuccessDataResult;
import turkcell.rentacar1.core.utilities.results.SuccessResult;
import turkcell.rentacar1.dataAccess.abstracts.BrandDao;
import turkcell.rentacar1.entities.concretes.Brand;

@Service
public class BrandManager implements BrandService {
	
	private BrandDao brandDao;
	private ModelMapperService modelMapperService;
	
	@Autowired
	public BrandManager(BrandDao brandDao, ModelMapperService modelMapperService) {
		
		this.brandDao = brandDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<ListBrandDto>> getAll() {
		
		var result = this.brandDao.findAll();
		
		List<ListBrandDto> response = result.stream()
				.map(brand->this.modelMapperService.forDto().map(brand, ListBrandDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListBrandDto>>(response);
	}

	
	//500 hatasını kullanıcı görmemeli.
	@Override
	public Result add(CreateBrandRequest createBrandRequest)  {
		
		checkIfExistByBrandName(createBrandRequest.getBrandName());
		Brand brand = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);
		
			this.brandDao.save(brand);
			return new SuccessResult("Marka eklendi."); //brand.added
		
	}

	@Override
	public DataResult<GetListBrandDto> getByBrandId(int brandId)   {
		checkIfExistByBrandId(brandId);
		
		var result = this.brandDao.getByBrandId(brandId);
		
		GetListBrandDto response = this.modelMapperService.forDto().map(result, GetListBrandDto.class);
			
		return new SuccessDataResult<GetListBrandDto>(response);
		
		
	}

	@Override
	public Result delete(DeleteBrandRequest deleteBrandRequest) {
		
		checkIfExistByBrandId(deleteBrandRequest.getBrandId());
		
			this.brandDao.deleteById(deleteBrandRequest.getBrandId());
			
			return new SuccessResult("Marka silindi."); //BRAND.DELETED
		
		
	}

	@Override
	public Result update(UpdateBrandRequest updateBrandRequest) {
		
		checkIfExistByBrandId(updateBrandRequest.getBrandId());
		checkIfExistByBrandName(updateBrandRequest.getBrandName());
		
		Brand brand = this.modelMapperService.forRequest().map(updateBrandRequest, Brand.class);
		
			this.brandDao.save(brand);
			return new SuccessResult("Marka güncellendi."); //BRAND.UPDATED
		
	}
	
	private boolean checkIfExistByBrandName(String brandName)  {
		if(this.brandDao.getByBrandName(brandName)!=null) {
			throw new BusinessException("Bu marka daha önce eklenmiştir.");
		}
		return true;
	}
	
	private boolean checkIfExistByBrandId(int brandId){
		if(this.brandDao.getByBrandId(brandId)==null) {
			throw new BusinessException("Böyle bir id mevcut değil.");
		}
		return true;
		
		
		
	}
	
	
	}

