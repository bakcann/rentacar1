package turkcell.rentacar1.business.abstracts;

import java.util.List;

import turkcell.rentacar1.business.dtos.GetListBrandDto;
import turkcell.rentacar1.business.dtos.ListBrandDto;
import turkcell.rentacar1.business.requests.creates.CreateBrandRequest;
import turkcell.rentacar1.business.requests.deletes.DeleteBrandRequest;
import turkcell.rentacar1.business.requests.updates.UpdateBrandRequest;
import turkcell.rentacar1.core.utilities.results.DataResult;
import turkcell.rentacar1.core.utilities.results.Result;

public interface BrandService {
	
		Result add(CreateBrandRequest createBrandRequest) ;
		
		Result delete(DeleteBrandRequest deleteBrandRequest) ;
		
		Result update(UpdateBrandRequest updateBrandRequest) ;
		
		DataResult<List<ListBrandDto>> getAll();
		
		DataResult<GetListBrandDto> getByBrandId(int brandId);
		
		

}
