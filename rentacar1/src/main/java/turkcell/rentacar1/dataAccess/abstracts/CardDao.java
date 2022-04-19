package turkcell.rentacar1.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import turkcell.rentacar1.entities.concretes.Card;

@Repository
public interface CardDao extends JpaRepository<Card, Integer>{
	
	public Card getByCardId(int cardId);
	
	public List<Card> getByCustomer_CustomerId(int customerId);

}
