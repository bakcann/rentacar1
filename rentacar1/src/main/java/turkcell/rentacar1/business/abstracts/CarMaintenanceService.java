package turkcell.rentacar1.business.abstracts;

import java.util.List;

import turkcell.rentacar1.business.dtos.GetListCarMaintenanceDto;
import turkcell.rentacar1.business.dtos.ListCarMaintenanceDto;
import turkcell.rentacar1.business.requests.creates.CreateCarMaintenanceRequest;
import turkcell.rentacar1.business.requests.deletes.DeleteCarMaintenanceRequest;
import turkcell.rentacar1.business.requests.updates.UpdateCarMaintenanceRequest;
import turkcell.rentacar1.core.utilities.results.DataResult;
import turkcell.rentacar1.core.utilities.results.Result;

public interface CarMaintenanceService {
	
	Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest);
	
	Result delete(DeleteCarMaintenanceRequest deleteCarMaintenanceRequest);
	
	Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest);
	
	DataResult<List<ListCarMaintenanceDto>> getAll();
	
	DataResult<GetListCarMaintenanceDto> getByCarMaintenanceId(int maintenanceId);

	DataResult<List<ListCarMaintenanceDto>> getCarMaintenanceByCarId(int carId);
	
	public boolean checkIfCarNotInMaintenance(int carId);
	

}
