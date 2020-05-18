package model.entities;
import java.io.Serializable;

public class Company implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String companyName;
	private String ceoName;
	private String phoneNumber;
	private String email;
	private String password;
	private String country;
	private String foundationDate;
	private String website;
	private String industry;
	private String grossSales;
	
	public Company() {
	}

	public Company(Integer id, String companyName, String ceoName, String phoneNumber, String email, String password, String country,
			String foundationDate, String website, String industry, String grossSales) {
		this.id = id;
		this.companyName = companyName;
		this.ceoName = ceoName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.password = password;
		this.country = country;
		this.foundationDate = foundationDate;
		this.website = website;
		this.industry = industry;
		this.grossSales = grossSales;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCeoName() {
		return ceoName;
	}

	public void setCeoName(String ceoName) {
		this.ceoName = ceoName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getFoundationDate() {
		return foundationDate;
	}

	public void setFoundationDate(String foundationDate) {
		this.foundationDate = foundationDate;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getGrossSales() {
		return grossSales;
	}

	public void setGrossSales(String grossSales) {
		this.grossSales = grossSales;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", companyName=" + companyName + ", ceoName=" + ceoName + ", phoneNumber="
				+ phoneNumber + ", email=" + email + ", password=" + password + ", country=" + country
				+ ", foundationDate=" + foundationDate + ", website=" + website + ", industry=" + industry
				+ ", grossSales=" + grossSales + "]";
	}
}
