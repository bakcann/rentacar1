package turkcell.rentacar1.entities.concretes;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="cards")
@Entity
public class Card {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="card_id")
	private int cardId;
	
	@Column(name="card_owner_name")
	private String cardOwnerName;
	
	@Column(name="card_number")
	private String cardNumber;
	
	@Column(name="card_cvv_number")
	private int cardCvvNumber;
	
	@ManyToOne
	@JoinColumn(name="customer_id")
	private Customer customer;
	

	
	
	
	

}
