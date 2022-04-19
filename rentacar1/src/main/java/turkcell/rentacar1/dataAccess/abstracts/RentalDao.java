package turkcell.rentacar1.dataAccess.abstracts;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import turkcell.rentacar1.entities.concretes.Rental;

@Repository
public interface RentalDao extends JpaRepository<Rental, Integer> {
	
	Rental getByRentalId(int rentId);
	Rental getByReturnDateAndCar_carId(LocalDate localDate, int carId);
	
	List<Rental> getByCar_carId(int carId);
	
	
	

}
