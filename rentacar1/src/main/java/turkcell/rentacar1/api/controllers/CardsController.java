package turkcell.rentacar1.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import turkcell.rentacar1.business.abstracts.CardService;
import turkcell.rentacar1.business.dtos.GetListCardDto;
import turkcell.rentacar1.business.dtos.ListCardDto;
import turkcell.rentacar1.business.requests.creates.CreateCardRequest;
import turkcell.rentacar1.business.requests.deletes.DeleteCardRequest;
import turkcell.rentacar1.core.utilities.results.DataResult;
import turkcell.rentacar1.core.utilities.results.Result;

@RestController
@RequestMapping("/api/cards")
public class CardsController {
	
	private CardService cardService;
	
	@Autowired
	public CardsController(CardService cardService) {
		this.cardService = cardService;
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateCardRequest createCardRequest) {
		return this.cardService.add(createCardRequest);
	}
	
	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteCardRequest deleteCardRequest) {
		return this.cardService.delete(deleteCardRequest);
	}
	
	@GetMapping("/getall")
	public DataResult<List<ListCardDto>> getAll(){
		return this.cardService.getAll();
	}
	
	@GetMapping("/getbycardid")
	public DataResult<GetListCardDto> getByCardId(@RequestParam @Valid int cardId){
		return this.cardService.getByCardId(cardId);
	}
	
	@GetMapping("/getcardbycustomerid")
	public DataResult<GetListCardDto> getCardByCustomerId(@RequestParam @Valid int customerId){
		return this.cardService.getCardByCustomerId(customerId);
	}

}
