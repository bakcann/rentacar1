package turkcell.rentacar1.entities.concretes;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="rentals")
public class Rental {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="rental_id")
	private int rentId;
	
	@Column(name="rental_date")
	private LocalDate rentDate;
	
	@Column(name="rental_return_date")
	private LocalDate rentReturnDate;
	
	@Column(name="total_price")
	private double totalPrice;
	
	@Column(name="first_kilometer")
	private double firstKilometer;
	
	@Column(name="end_kilometer")
	private double endKilometer;
	
	@ManyToOne
	@JoinColumn(name="rent_city_id")
	private City rentCity;
	
	@ManyToOne
	@JoinColumn(name="return_city_id")
	private City returnCity;;
	
	@ManyToOne
	@JoinColumn(name="car_id")
	private Car car;
	
	@ManyToOne
	@JoinColumn(name="customer_id")
	private Customer customer;
	
	@OneToMany(mappedBy="rental")
	private List<OrderedAdditionalService> orderedAdditionalServices;
	
	@OneToMany(mappedBy="rental")
	private List<Invoice> invoices;
	
	@Column(name="planned_return_date")
	private LocalDate plannedReturnDate;
	
	
	
	
	

}
