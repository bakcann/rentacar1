package turkcell.rentacar1.dataAccess.abstracts;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import turkcell.rentacar1.entities.concretes.CarMaintenance;

@Repository
public interface CarMaintenanceDao extends JpaRepository<CarMaintenance, Integer> {
	
	CarMaintenance getByMaintenanceId(int maintenanceId);
	
	List<CarMaintenance> getByCar_CarId(int carId);
	
	CarMaintenance getByReturnDateAndCar_carId(LocalDate localDate, int carId);

}
