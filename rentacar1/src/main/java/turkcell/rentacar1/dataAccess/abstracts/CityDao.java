package turkcell.rentacar1.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import turkcell.rentacar1.entities.concretes.City;

@Repository	
public interface CityDao extends JpaRepository<City, Integer> {
	
	public City getByCityId(int cityId);
	
	public City getByCityName(String cityName);

}
