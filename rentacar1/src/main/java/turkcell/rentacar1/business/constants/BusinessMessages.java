package turkcell.rentacar1.business.constants;

public class BusinessMessages {
	
	public static final String SUCCESS = "Success";
	
	public static final String ADDITIONALSERVICEADDED = "AddittionalService.Added";
	public static final String ADDITINALSERVICEDELETED = "AdditionalService.Deleted";
	public static final String ADDITIONALSERVICEUPDATED = "AdditionalService.Updated";
	public static final String ADDITIONALSERVICENOTFOUND = "No additional service with this id was found";
	public static final String ADDITIONALSERVICEEXISTSNAME = "Additional service with this name is available";
	
	public static final String BRANDADDED = "Brand.Added";
	public static final String BRANDDELETED = "Brand.Deleted";
	public static final String BRANDUPDATED = "Brand.Updated";
	public static final String BRANDNOTFOUND = "No brand with this id was found";
	public static final String BRANDEXISTSNAME = "Brand  with this name is available";
	
	public static final String CARACCIDENTADDED = "CarAccident.Added";
	public static final String CARACCIDENTDELETED = "CarAccident.Deleted";
	public static final String CARACCIDENTUPDATED = "CarAccident.Updated";
	public static final String CARACCIDENTNOTFOUND = "No car accident with this id was found";
	public static final String CARACCIDENTNOTFOUNDCAR = "No car with this id was found";
	
	public static final String CARDADDED = "Card.Added";
	public static final String CARDDELETED = "Card.Deleted";
	public static final String CARDNOTFOUND = "No card with this id was found";
	
	public static final String CARMAINTENANCEADDED = "CarMaintenance.Added";
	public static final String CARMAINTENANCEDELETED = "CarMaintenance.Deleted";
	public static final String CARMAINTENANCEUPDATED = "CarMaintenance.Updated";
	public static final String CARMAINTENANCENOTFOUND = "No var maintenance with this id was found";
	public static final String CARMAINTENANCEEXISTSCAR = "The car is not avaible because it is under maintenance";
	public static final String CARMAINTENANCENOTEXISTSCAR = "The car is not in maintenance";
	
	public static final String CARADDED = "Car.Added";
	public static final String CARDELETED = "Car.Deleted";
	public static final String CARUPDATED = "Car.Updated";
	public static final String CARNOTFOUND = "No car with this id was found";
	public static final String CAREXISTSBRAND = "No brand with this id was found";
	public static final String CAREXISTSCOLOR = "No color with this is was found";
	
	public static final String CITYADDED = "City.Added";
	public static final String CITYDELETED = "City.Deleted";
	public static final String CITYNOTFOUND = "No city with this id was found";
	public static final String CITYEXISTSNAME = "City with this name is available";
	
	public static final String COLORADDED = "Color.Added";
	public static final String COLORDELETED = "Color.Deleted";
	public static final String COLORUPDATED = "Color.Updated";
	public static final String COLORNOTFOUND = "No color with this id was found";
	public static final String COLOREXISTSNAME = "Color service with this name is available";
	
	public static final String CORPORATECUSTOMERADDED = "CorporateCustomer.Added";
	public static final String CORPORATECUSTOMERDELETED = "CorporateCustomer.Deleted";
	public static final String CORPORATECUSTOMERUPDATED = "CorporateCustomer.Updated";
	public static final String CORPORATECUSTOMEREXISTSEMAIL = "No corporate customer with this email was found";
	public static final String CORPORATECUSTOMEREXISTSTAXNUMBER = "No corporate customer with this tax number was found";
	public static final String CORPORATECUSTOMERNOTEXISTSEMAIL = "Corporate customer with this email is available";
	public static final String CORPORATECUSTOMERNOTEXISTSTAXNUMBER = "Corporate customer with this tax number is available";
	
	public static final String INDIVIDUALCUSTOMERADDED = "IndividualCustomer.Added";
	public static final String INDIVIDUALCUSTOMERDELETED = "IndividualCustomer.Deleted";
	public static final String INDIVIDUALCUSTOMERUPDATED = "IndividualCustomer.Updated";
	public static final String INDIVIDUALCUSTOMEREXISTSEMAIL = "No corporate customer with this email was found";
	public static final String INDIVIDUALCUSTOMEREXISTSIDENTITYNUMBER = "No corporate customer with this identity number was found";
	public static final String INDIVIDUALCUSTOMERNOTEXISTSEMAIL = "Corporate customer with this email is available";
	public static final String INDIVIDUALCUSTOMERNOTEXISTSIDENTITYNUMBER = "Corporate customer with this identity number is available";
			
	public static final String CUSTOMERNOTFOUND = "No customer with this id was found";
	
	public static final String INVOICEADDED = "Invoice.Added";
	public static final String INVOICEDELETED = "Invoice.Deleted";
	public static final String INVOICEUPDATED = "Invoice.Updated";
	public static final String INVOICEADDERROR = "Invoice not created";
	public static final String INVOICENOTFOUND = "No invoice with this id was found";
	public static final String INVOICEEXISTSRENTAL = "Invoice has already been created";
	public static final String INVOICENOTEXISTSINVOICENO = "Invoice  with this invoiceNo is available";
	
	public static final String ORDEREDADDITIONALSERVICEADDED = "OrderedAdditionalService.Added";
	public static final String ORDEREDADDITIONALSERVICEDELETED = "OrderedAdditionalService.Deleted";
	public static final String ORDEREDADDITIONALSERVICEUPDATED = "OrderedAdditionalService.Updated";
	public static final String ORDEREDADDITIONALSERVICENOTFOUND = "No orderedAdditionalService with this id was found";
	public static final String ORDEREDADDITIONALSERVICEEXISTSADDITIONALSERVICEINRENT = "Additional Service has already been added to this lease";
	public static final String ORDEREDADDITIONALSERVICENOTEXISTSADDITIONALSERVICEINRENT = "This rental not found additional service";

	public static final String PAYMENTADDED = "Payment.Added";
	public static final String PAYMENTADDERROR = "Payment.Add.Error";
	public static final String PAYMENTNOTFOUND = "No payment with this id was found";
	
	public static final String RENTALADDED = "Rental.Added";
	public static final String RENTALDELETED = "Rental.Deleted";
	public static final String RENTALUPDATED = "Rental.Updated";
	public static final String RENTALNOTFOUND = "No rental with this id was found";
	public static final String CARAVAILABLEFORRENTAL = "This car has been rented before and is still rented";
	public static final String RENTALRETURNDATEAFTERPLANNEDRETURNDATE = "The return date cannot be earlier than the xcheduled date";
	public static final String RENTALPLANNEDRETURNDATEAFTERRENTDATE ="Return date must be after rental date";
	public static final String RENTALSETCARKILOMETER = "Rent kilometer cannot be less than return kilometer";
}
