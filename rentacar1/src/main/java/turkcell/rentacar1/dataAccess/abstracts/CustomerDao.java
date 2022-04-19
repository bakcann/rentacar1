package turkcell.rentacar1.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import turkcell.rentacar1.entities.concretes.Customer;

@Repository
public interface CustomerDao extends JpaRepository<Customer, Integer>{
	
	public Customer getByCustomerId(int customerId);

}
