package turkcell.rentacar1.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import turkcell.rentacar1.business.abstracts.CardService;
import turkcell.rentacar1.business.abstracts.CustomerService;
import turkcell.rentacar1.business.dtos.GetListCardDto;
import turkcell.rentacar1.business.dtos.ListCardDto;
import turkcell.rentacar1.business.requests.creates.CreateCardRequest;
import turkcell.rentacar1.business.requests.deletes.DeleteCardRequest;
import turkcell.rentacar1.core.concretes.BusinessException;
import turkcell.rentacar1.core.utilities.mapping.ModelMapperService;
import turkcell.rentacar1.core.utilities.results.DataResult;
import turkcell.rentacar1.core.utilities.results.Result;
import turkcell.rentacar1.core.utilities.results.SuccessDataResult;
import turkcell.rentacar1.core.utilities.results.SuccessResult;
import turkcell.rentacar1.dataAccess.abstracts.CardDao;
import turkcell.rentacar1.entities.concretes.Card;

@Service
public class CardManager implements CardService{
	
	private CardDao cardDao;
	private ModelMapperService modelMapperService;
	private CustomerService customerService;
	
	@Autowired
	public CardManager(CardDao cardDao, ModelMapperService modelMapperService, CustomerService customerService) {
		this.cardDao = cardDao;
		this.modelMapperService = modelMapperService;
		this.customerService = customerService;
	}

	@Override
	public Result add(CreateCardRequest createCardRequest) {
		
		Card card = this.modelMapperService.forRequest().map(createCardRequest, Card.class);
		card.setCustomer(this.customerService.getById(createCardRequest.getCustomerId()));
		card.setCardId(0);
		
		this.cardDao.save(card);
		
		return new SuccessResult("Kredi kartı eklendi.");
	}

	@Override
	public Result delete(DeleteCardRequest deleteCardRequest) {
		
		this.cardDao.deleteById(deleteCardRequest.getCardId());
		
		return new SuccessResult("Kredi kartı silindi");
	}

	@Override
	public DataResult<List<ListCardDto>> getAll() {
		
		var  result = this.cardDao.findAll();
		
		List<ListCardDto> response = result.stream().map(card -> this.modelMapperService.forDto().map(card, ListCardDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListCardDto>>(response);
	}

	@Override
	public DataResult<GetListCardDto> getByCardId(int cardId) {
		
		checkIfExistCardId(cardId);
		
		var result = this.cardDao.getByCardId(cardId);
		
		GetListCardDto response = this.modelMapperService.forDto().map(result, GetListCardDto.class);
		
		return new SuccessDataResult<GetListCardDto>(response);
	}

	@Override
	public DataResult<GetListCardDto> getCardByCustomerId(int customerId) {
		
		var result = this.cardDao.getByCustomer_CustomerId(customerId);
		
		GetListCardDto response = this.modelMapperService.forDto().map(result, GetListCardDto.class);
		
		return new SuccessDataResult<GetListCardDto>(response);
	}
	
	private boolean checkIfExistCardId(int cardId) {
		var result = this.cardDao.getByCardId(cardId);
		if(result== null) {
			throw new BusinessException("Böyle bir kredi kartı mevcut değil.");
		}
		return true;
	}
	

}
