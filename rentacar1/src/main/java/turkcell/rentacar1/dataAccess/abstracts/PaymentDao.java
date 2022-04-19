package turkcell.rentacar1.dataAccess.abstracts;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import turkcell.rentacar1.entities.concretes.Payment;

@Repository 
@Transactional(rollbackFor = Exception.class)
public interface PaymentDao extends JpaRepository<Payment, Integer>{
	
	public Payment getByPaymentId(int paymentId);
	//public Payment getByInvoice_invoiceId(int invoiceId);
	//public Payment getByOrderedAdditionalService_orderedId(int orderedId);

}
