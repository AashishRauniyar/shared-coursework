package com.ecom.model;

public class AddressModel {
	

    private int userId; // Foreign key referencing Users table
    private String city;
    private String province;
    private String country;
    private String postalCode;
	


	public AddressModel(int userId, String city, String province, String country, String postalCode) {
		super();
		this.userId = userId;
		this.city = city;
		this.province = province;
		this.country = country;
		this.postalCode = postalCode;
	}


	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getProvince() {
		return province;
	}


	public void setProvince(String province) {
		this.province = province;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public String getPostalCode() {
		return postalCode;
	}


	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
    
    
    

}
