package turkcell.rentacar1.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetListAdditionalServiceDto {
	
	private int additionalServiceId;
	
	private String additionalServiceName;
	
	private double additionalPrice;

}
