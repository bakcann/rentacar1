package turkcell.rentacar1.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import turkcell.rentacar1.business.abstracts.CustomerService;
import turkcell.rentacar1.core.concretes.BusinessException;
import turkcell.rentacar1.dataAccess.abstracts.CustomerDao;
import turkcell.rentacar1.entities.concretes.Customer;

@Service
public class CustomerManager implements CustomerService {
	
	private CustomerDao customerDao;

		@Autowired
	public CustomerManager(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

		@Override
		public Customer getById(int customerId) {
		checkIfExistCustomerById(customerId);
		
			return this.customerDao.getByCustomerId(customerId);
		}
		
		@Override
		public boolean checkIfExistCustomerById(int customerId) {
			
			var result= this.customerDao.getByCustomerId(customerId);
			if(result ==null) {
				throw new BusinessException("Bu idye sahip Customer bulunamadı");
			}
			return true;
		}

}
