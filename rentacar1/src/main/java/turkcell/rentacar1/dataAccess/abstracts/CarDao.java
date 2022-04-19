package turkcell.rentacar1.dataAccess.abstracts;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import turkcell.rentacar1.entities.concretes.Car;

@Repository
public interface CarDao extends JpaRepository<Car, Integer>{
	
	public Car getByCarId(int carId);
	
	public List<Car> getByDailyPriceLessThanEqual(double dailyPrice);
	

}
