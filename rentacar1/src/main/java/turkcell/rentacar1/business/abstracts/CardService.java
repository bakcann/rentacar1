package turkcell.rentacar1.business.abstracts;

import java.util.List;

import turkcell.rentacar1.business.dtos.GetListCardDto;
import turkcell.rentacar1.business.dtos.ListCardDto;
import turkcell.rentacar1.business.requests.creates.CreateCardRequest;
import turkcell.rentacar1.business.requests.deletes.DeleteCardRequest;
import turkcell.rentacar1.core.utilities.results.DataResult;
import turkcell.rentacar1.core.utilities.results.Result;

public interface CardService {
	
	Result add(CreateCardRequest createCardRequest);
	Result delete(DeleteCardRequest deleteCardRequest);
	
	DataResult<List<ListCardDto>> getAll();
	DataResult<GetListCardDto> getByCardId(int cardId);
	DataResult<GetListCardDto> getCardByCustomerId(int customerId);

}
