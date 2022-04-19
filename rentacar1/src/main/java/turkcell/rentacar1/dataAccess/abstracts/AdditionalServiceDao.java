package turkcell.rentacar1.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import turkcell.rentacar1.entities.concretes.AdditionalService;

@Repository
public interface AdditionalServiceDao extends JpaRepository<AdditionalService, Integer> {
	
	AdditionalService getByAdditionalServiceId(int additionalServiceId);
	AdditionalService getByAdditionalServiceName(String additionalServiceName);

}
