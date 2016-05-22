package at.ac.tuwien.big.we16.ue3.model;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;

/* This class is used as a Javabean to show dynamic error messages in
 * the registration.jsp. It also performs the validation of the registration form.
 */
public class RegistrationForm {
	
	private String salutation = "";	
	private boolean salutationError = false;
	private String salutationErrMsg = "";
	
	private String firstname = "";
	private boolean firstnameError = false;
	private String firstnameErrMsg = ""; 
	
	private String lastname = "";
	private boolean lastnameError = false;
	private String lastnameErrMsg = "";
	
	private String dateofbirth = "";
	private boolean dateofbirthError = false;
	private String dateofbirthErrMsg = "";
	
	private String email = "";
	private boolean emailError = false;
	private String emailErrMsg = "";
	
	private String password = "";
	private boolean passwordError = false;
	private String passwordErrMsg = "";
	
	private String streetAndNumber = "";
	
	private String postcodeAndCity = "";
	
	private String country = "";

	private boolean formHasError = false;
	
	
	
	
	
	
	
	
	
	
	
	public boolean isSalutationError() {
		return salutationError;
	}














	public String getSalutationErrMsg() {
		return salutationErrMsg;
	}














	public String getFirstname() {
		return firstname;
	}














	public boolean isFirstnameError() {
		return firstnameError;
	}














	public String getFirstnameErrMsg() {
		return firstnameErrMsg;
	}














	public String getLastname() {
		return lastname;
	}














	public boolean isLastnameError() {
		return lastnameError;
	}














	public String getLastnameErrMsg() {
		return lastnameErrMsg;
	}














	public String getDateofbirth() {
		return dateofbirth;
	}














	public boolean isDateofbirthError() {
		return dateofbirthError;
	}














	public String getDateofbirthErrMsg() {
		return dateofbirthErrMsg;
	}














	public String getEmail() {
		return email;
	}














	public boolean isEmailError() {
		return emailError;
	}














	public String getEmailErrMsg() {
		return emailErrMsg;
	}














	public String getPassword() {
		return password;
	}














	public boolean isPasswordError() {
		return passwordError;
	}














	public String getPasswordErrMsg() {
		return passwordErrMsg;
	}














	public String getStreetAndNumber() {
		return streetAndNumber;
	}














	public String getPostcodeAndCity() {
		return postcodeAndCity;
	}














	public boolean isFormHasError() {
		return formHasError;
	}














	public boolean setAndValidate(String salutation,
							String firstname,
							String lastname,
							String dateofbirth,
							String email,
							String password,
							String streetAndNumber,
							String postcodeAndCity,
							String country) {
		
		this.salutation = salutation;
		this.firstname = firstname;
		this.lastname = lastname;
		this.dateofbirth = dateofbirth;
		this.email = email;
		this.password = password;
		this.streetAndNumber = streetAndNumber;
		this.postcodeAndCity = postcodeAndCity;
		this.country = country;
		
		/* validating the salutation */
		if(salutation.equals("mr") || salutation.equals("ms")) {
			this.salutationError = false;
		}
		else {
			this.salutationError = true;
			this.formHasError = true;
			this.salutationErrMsg = "Ungültige Anrede.";
		}
		
		
		/* validating firstname */
		if(firstname.length() < 1) {
			this.firstnameError = true;
			this.formHasError = true;
			this.firstnameErrMsg = "Bitte geben Sie einen Vornamen ein.";
		}
		else {
			this.firstnameError = false;
		}
		
		/* validating lastname */
		if(lastname.length() < 1) {
			this.lastnameError = true;
			this.formHasError = true;
			this.lastnameErrMsg = "Bitte geben Sie einen Nachnamen ein.";
		}
		else {
			this.lastnameError = false;
		}
		
		/* validating dateofbirth */
		String normDate = this.getNormalizedDateString(dateofbirth);
		SimpleDateFormat dateformat = new SimpleDateFormat("dd.MM.yyyy");
		Date datum = new Date();
		try {
			datum = dateformat.parse(normDate);
			if(datum == null) {
				this.dateofbirthError = true;
				this.formHasError = true;
				this.dateofbirthErrMsg = "Bitte geben Sie ein gültiges Datum der Form tt.mm.jjjj ein.";
			}
			else {
				Calendar calendar = GregorianCalendar.getInstance();
				calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 18);
				if(calendar.getTime().after(datum)){
					this.dateofbirthError = false;
				}
				else {
					this.dateofbirthError = true;
					this.formHasError = true;
					this.dateofbirthErrMsg = "Das Geburtsdatum muss 18 Jahre "
							+ "in der Vergangenheit liegen.";
				}
				
			}
		}
		catch(ParseException e) {
			this.dateofbirthError = true;
			this.formHasError = true;
			this.dateofbirthErrMsg = "Bitte geben Sie ein gültiges Datum der Form tt.mm.jjjj ein.";
		}
		
		/* validating email */
		if(Pattern.matches("^\\S+@\\S+\\.\\S+$", email)) {
			this.emailError = false;
		}
		else {
			this.emailError = true;
			this.formHasError = true;
			this.emailErrMsg = "Bitte geben Sie eine gültige Email-Adresse ein.";
		}
		
		
		/* validating password */
		if((password.length() < 4) || (password.length() > 8)) {
			this.passwordError = true;
			this.formHasError = true;
			this.passwordErrMsg = "Das Passwort muss zwischen 4 und 8 Zeichen lang sein.";
		}
		else {
			this.passwordError = false;
		}
		
		/* validating streetAndNumber */
		// no limitations
		
		
		/* validating postcodeAndCity */
		// no limitations
		
		/* validating country */
		// no limitations
		
		if(this.formHasError) {
			return false;
		}
		else {
			return true;
		}
	}	
	
	
	
	
	
	
	
	
	
	/* This method is a translation of the function with
	 * the same name in framework.js */
	private String getNormalizedDateString(String datestring) {
		if(datestring == null) {
			return "";
		}
		
		String[] input = { "" };
		input[0] = datestring;
		String[] delimeters = {"/","[\\\\]", "-"};		
				
		for(int i=0; i<delimeters.length; i++) {
			input = input[0].split(delimeters[i]);
			
			List<String> list = Arrays.asList(input);
			input[0] = String.join(".", list);
		}
		
		//check if date might be reversed i.e yyyy.mm.dd
		if(Pattern.matches("^\\d{4}\\.\\d{1,2}\\.\\d{1,2}$", input[0])) {
			input = input[0].split("\\.");
			input[0] = input[2]+"."+input[1]+"."+input[0];
		}
		
		//check if valid date string dd.mm.yyyy  
		if(Pattern.matches("^\\d{1,2}\\.\\d{1,2}\\.\\d{4}$", input[0])) {
			return input[0];
		}
		return datestring;
	}
}

















