package turkcell.rentacar1.business.requests.creates;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderedAdditionalServiceRequestForTransactional {
	private List<Integer> additionalServiceIds;

}
