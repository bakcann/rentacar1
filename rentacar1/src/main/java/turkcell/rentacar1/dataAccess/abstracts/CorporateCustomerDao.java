package turkcell.rentacar1.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import turkcell.rentacar1.entities.concretes.CorporateCustomer;

@Repository	
public interface CorporateCustomerDao extends JpaRepository<CorporateCustomer, Integer> {
	
	public CorporateCustomer getByCustomerId(int customerId);
	public CorporateCustomer getByEmail(String email);
	public CorporateCustomer getByTaxNumber(String taxNumber);

}
