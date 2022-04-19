package turkcell.rentacar1.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import turkcell.rentacar1.entities.concretes.CarAccident;

@Repository
public interface CarAccidentDao extends JpaRepository<CarAccident, Integer>{
	
	public CarAccident getByCarAccidentId(int carAccidentId);
	
	public List<CarAccident> getByCar_CarId(int carId);

}
