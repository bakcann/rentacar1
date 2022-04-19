package turkcell.rentacar1.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import turkcell.rentacar1.entities.concretes.OrderedAdditionalService;

@Repository
public interface OrderedAdditionalServiceDao extends JpaRepository<OrderedAdditionalService, Integer> {

		public OrderedAdditionalService getByOrderedId(int orderedId);
		
		public List<OrderedAdditionalService> getByRental_rentId(int rentId);
		
		public OrderedAdditionalService getByRental_RentIdAndAdditionalService_AdditionalServiceId(int rentId, int additionalServiceId);
		
}
