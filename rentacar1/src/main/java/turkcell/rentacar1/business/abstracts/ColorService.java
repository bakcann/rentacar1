package turkcell.rentacar1.business.abstracts;

import java.util.List;

import turkcell.rentacar1.business.dtos.ListColorDto;
import turkcell.rentacar1.business.requests.creates.CreateColorRequest;
import turkcell.rentacar1.business.requests.deletes.DeleteColorRequest;
import turkcell.rentacar1.business.requests.updates.UpdateColorRequest;
import turkcell.rentacar1.core.concretes.BusinessException;
import turkcell.rentacar1.core.utilities.results.DataResult;
import turkcell.rentacar1.core.utilities.results.Result;

public interface ColorService {
	
		Result add(CreateColorRequest createColorRequest) throws BusinessException;
	
		Result delete(DeleteColorRequest deleteColorRequest) throws BusinessException;
		
		Result update(UpdateColorRequest updateColorRequest) throws BusinessException;
		
		DataResult<List<ListColorDto>> getAll();
		
		DataResult<ListColorDto> getByColorId(int colorId) ;

}
