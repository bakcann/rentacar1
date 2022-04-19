package turkcell.rentacar1.entities.concretes;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="ordered_additional_services")
public class OrderedAdditionalService {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ordered_id")
	private int orderedId;
	
	
	@ManyToOne
	@JoinColumn(name="rental_id")
    private Rental rental;
	
	@ManyToOne
	@JoinColumn(name="additional_service_id")
	private AdditionalService additionalService;
	
	/*@OneToOne(mappedBy="orderedAdditionalService")
	private Payment payment;*/

}
