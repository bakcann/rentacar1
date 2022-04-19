package turkcell.rentacar1.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import turkcell.rentacar1.entities.concretes.IndividualCustomer;

@Repository
public interface IndividualCustomerDao extends JpaRepository<IndividualCustomer, Integer>{
	
	public IndividualCustomer getByCustomerId(int customerId);
	public IndividualCustomer getByEmail(String email);
	public IndividualCustomer getByIdentityNumber(String identityNumber);

}
