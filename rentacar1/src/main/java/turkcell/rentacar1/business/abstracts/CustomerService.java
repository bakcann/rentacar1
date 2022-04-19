package turkcell.rentacar1.business.abstracts;

import turkcell.rentacar1.entities.concretes.Customer;

public interface CustomerService {
	
	public Customer getById(int customerId);
	
	public boolean checkIfExistCustomerById(int customerId);

}
