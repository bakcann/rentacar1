package turkcell.rentacar1.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetListOrderedAdditionalServiceDto {
	
	private int orderedId;
	
	private int additionalServiceId;
	
	private String additionalServiceName;
	
	private int rentId;

}
