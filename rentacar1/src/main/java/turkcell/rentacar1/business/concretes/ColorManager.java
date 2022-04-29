package turkcell.rentacar1.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import turkcell.rentacar1.business.abstracts.ColorService;
import turkcell.rentacar1.business.constants.BusinessMessages;
import turkcell.rentacar1.business.dtos.ListColorDto;
import turkcell.rentacar1.business.requests.creates.CreateColorRequest;
import turkcell.rentacar1.business.requests.deletes.DeleteColorRequest;
import turkcell.rentacar1.business.requests.updates.UpdateColorRequest;
import turkcell.rentacar1.core.concretes.BusinessException;
import turkcell.rentacar1.core.utilities.mapping.ModelMapperService;
import turkcell.rentacar1.core.utilities.results.DataResult;
import turkcell.rentacar1.core.utilities.results.Result;
import turkcell.rentacar1.core.utilities.results.SuccessDataResult;
import turkcell.rentacar1.core.utilities.results.SuccessResult;
import turkcell.rentacar1.dataAccess.abstracts.ColorDao;
import turkcell.rentacar1.entities.concretes.Color;

@Service
public class ColorManager implements ColorService{
	
	private ColorDao colorDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public ColorManager(ColorDao colorDao, ModelMapperService modelMapperService) {
		
		this.colorDao = colorDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateColorRequest createColorRequest) {
		checkIfColorName(createColorRequest.getColorName());
		Color color = this.modelMapperService.forRequest().map(createColorRequest, Color.class);
	
			this.colorDao.save(color);
			return new SuccessResult(BusinessMessages.COLORADDED);
		}
	
	@Override
	public Result delete(DeleteColorRequest deleteColorRequest) {
		
		checkIfColorId(deleteColorRequest.getColorId());
		
		Color color=this.modelMapperService.forRequest().map(deleteColorRequest, Color.class);
		
			this.colorDao.deleteById(color.getColorId());
			return new SuccessResult(BusinessMessages.COLORDELETED);
	}

	@Override
	public Result update(UpdateColorRequest updateColorRequest) {
		
		checkIfColorId(updateColorRequest.getColorId());
		checkIfColorName(updateColorRequest.getColorName());
		
		Color color = this.modelMapperService.forRequest().map(updateColorRequest, Color.class);
		
		
			this.colorDao.save(color);
			return new SuccessResult(BusinessMessages.COLORUPDATED);
		
	}
	
	@Override
	public DataResult<List<ListColorDto>> getAll() {
		var result = this.colorDao.findAll();
		List<ListColorDto> response =result.stream()
				.map(color->this.modelMapperService.forDto()
						.map(color, ListColorDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<ListColorDto>>(response, BusinessMessages.SUCCESS);
	}
	
	@Override
	public DataResult<ListColorDto> getByColorId(int colorId)  {
		
		checkIfColorId(colorId);
		var result = this.colorDao.getByColorId(colorId);
		
			ListColorDto response = this.modelMapperService.forDto().map(result, ListColorDto.class);
		return new SuccessDataResult<ListColorDto>(response,BusinessMessages.SUCCESS);
		
	}
		
	
	private boolean checkIfColorName(String colorName) throws BusinessException {
		if(this.colorDao.getByColorName(colorName)==null) {
			return true;
		}
		throw new BusinessException(BusinessMessages.COLOREXISTSNAME);
	}
	
	private boolean checkIfColorId(int colorId)throws BusinessException{
		if(this.colorDao.getByColorId(colorId)!=null) {
			return true;
		}
		throw new BusinessException(BusinessMessages.COLORNOTFOUND);
		
		
	}
}
