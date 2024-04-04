package com.ecom.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ecom.model.AddressModel;
import com.ecom.model.UserModel;
import com.ecom.utils.QueryUtils;
import com.ecom.utils.StringUtils;

public class DatabaseController {
    public static Connection getConn() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = ("jdbc:mysql://localhost:3306/gadget");
        String username = "root";
        String password = "";
        System.out.println("Connected to database");
        return DriverManager.getConnection(url, username, password);
    }

    public int addUser(UserModel userModel) {
        try (Connection conn = DatabaseController.getConn()) {
            // Insert user data
            String insertUserQuery = QueryUtils.insertUserQuery;
            PreparedStatement userStatement = conn.prepareStatement(insertUserQuery);

            userStatement.setString(1, userModel.getUserName());
            userStatement.setString(2, userModel.getFirstName());
            userStatement.setString(3, userModel.getLastName());
            userStatement.setString(4, userModel.getEmail());
            userStatement.setString(5, userModel.getPhoneNumber());
            userStatement.setString(6, userModel.getPassword());
            
            userStatement.setDate(7, java.sql.Date.valueOf(userModel.getDob())); 
            userStatement.setString(8, userModel.getGender());

            int userInserted = userStatement.executeUpdate();
            userStatement.close();

            return userInserted; // Return the result of user insertion

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return -1; // or handle the exception as needed
        }
    }

    public int addAddress(AddressModel addressModel) {
        try (Connection conn = DatabaseController.getConn()) {
            // Insert address data
            String insertAddressQuery = QueryUtils.insertAddressQuery;
            PreparedStatement addressStatement = conn.prepareStatement(insertAddressQuery);

            addressStatement.setInt(1, addressModel.getUserId());
            addressStatement.setString(2, addressModel.getCity());
            addressStatement.setString(3, addressModel.getProvince());
            addressStatement.setString(4, addressModel.getCountry());
            addressStatement.setString(5, addressModel.getPostalCode());

            int addressInserted = addressStatement.executeUpdate();
            addressStatement.close();

            return addressInserted; // Return the result of address insertion

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return -1; // or handle the exception as needed
        }
    }
    
    
    public String getHashedPassword(String userName) {
		try (Connection con = getConn()) {
			PreparedStatement st = con.prepareStatement(QueryUtils.GET_HASHED_PASSWORD);
			st.setString(1, userName);
			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				return rs.getString("password");
			} else {
				// Username not found
				return null;
			}
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			return null;
		}
	}
    
    
    
  //user login info
  	public int getUserLoginInfo(String userName, String password) {
  		try (Connection con = getConn()) {
  			PreparedStatement ps = con.prepareStatement(QueryUtils.GET_LOGIN_USER_INFOS);
  			ps.setString(1, userName);
  			ps.setString(2, password);
  			ResultSet rs = ps.executeQuery();

  			if (rs.next()) {
  				// User name and password match in the database
  				return 1;
  			} else {
  				// No matching record found
  				return 0;
  			}
  		} catch (SQLException | ClassNotFoundException ex) {
  			ex.printStackTrace(); // Log the exception for debugging
  			return -1;

  		}
  	}

 // admin login info
 	public int getAdminLoginInfo(String userName, String password) {
 		try (Connection con = getConn()) {
 			PreparedStatement ps = con.prepareStatement(QueryUtils.GET_LOGIN_ADMIN_INFOS);
 			ps.setString(1, userName);
 			ps.setString(2, password);
 			ResultSet rs = ps.executeQuery();

 			if (rs.next()) {
 				// User name and password match in the database
 				return 1;
 			} else {
 				// No matching record found
 				return 0;
 			}
 		} catch (SQLException | ClassNotFoundException ex) {
 			ex.printStackTrace(); // Log the exception for debugging
 			return -1;

 		}
 	}

}
