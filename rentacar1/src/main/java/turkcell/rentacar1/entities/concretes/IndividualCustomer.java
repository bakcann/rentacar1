package turkcell.rentacar1.entities.concretes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="individual_customers")
@PrimaryKeyJoinColumn(name="individual_customer_id", referencedColumnName = "customer_id")
public class IndividualCustomer extends Customer {
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	
	@Column(name="identity_number")
	private String identityNumber;
	

}
