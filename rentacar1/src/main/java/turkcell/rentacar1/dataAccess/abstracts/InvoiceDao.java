package turkcell.rentacar1.dataAccess.abstracts;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import turkcell.rentacar1.entities.concretes.Invoice;

@Repository
public interface InvoiceDao extends JpaRepository<Invoice, Integer>{
	
	public Invoice getByInvoiceId(int invoiceId);
	public Invoice getByInvoiceNo(String invoiceNo);
	List<Invoice> getByRental_rentId(int rentId);
	
	List<Invoice> getByCustomer_customerId(int customerId);
	List<Invoice> findByCreateDateBetween(LocalDate startDate , LocalDate endDate);

}
