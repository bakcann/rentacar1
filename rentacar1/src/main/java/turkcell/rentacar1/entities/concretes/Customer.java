package turkcell.rentacar1.entities.concretes;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="customers")
@PrimaryKeyJoinColumn(name="customer_id",referencedColumnName = "user_id")
public class Customer extends User {
	
	
	@Column(name="customer_id", insertable = false, updatable = false)
	private int customerId;
	
	@OneToMany(mappedBy="customer")
	private List<Rental> rentals;
	
	@OneToMany(mappedBy="customer")
	private List<Invoice> invoices;

}
