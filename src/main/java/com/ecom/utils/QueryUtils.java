package com.ecom.utils;

public class QueryUtils {

	public static final String insertUserQuery = "INSERT INTO Users (userName, firstName, lastName, email, phoneNumber,password, dob, gender) VALUES (?,?, ?, ?, ?, ?, ?, ?)";
	public static final String insertAddressQuery = "INSERT INTO Address (userId, city, province, country, postalCode) VALUES (?, ?, ?, ?, ?)";
	
	
	// to fetch admin data
	
	public static final String GET_LOGIN_USER_INFOS = "SELECT userName ,password from users where userName = ? And password = ?";

	public static final String GET_USER = "SELECT * from users";

	public static final String GET_HASHED_PASSWORD = "SELECT password FROM users WHERE userName = ?";

	public static final String GET_LOGIN_ADMIN_INFOS = "SELECT password FROM users WHERE userName = ?";
}
