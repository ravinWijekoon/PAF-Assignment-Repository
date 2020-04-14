package com.HealthcareSystem.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Doctor {

	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/paf?useTimeZone=true&serverTimezone=UTC",
					"root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertDoctor(String name, String spec, String reg, String addr, String phn) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into doctor(`doctor_name`,`specialization`,`regNo`,`address`,`phone`)"
					+ " values (?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			// preparedStmt.setInt(1, 0);
			preparedStmt.setString(1, name);
			preparedStmt.setString(2, spec);
			preparedStmt.setString(3, reg);
			preparedStmt.setString(4, addr);
			preparedStmt.setString(5, phn);

			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Doctor Information Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting Doctor information.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readDoctors() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Doctor Name</th><th>Specialization</th><th>Registration No.</th><th>Address</th><th>Phone</th></tr>";
			String query = "select * from doctor";
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				// String DoctorID = Integer.toString(rs.getInt("doctor_id "));
				String Name = rs.getString("doctor_name");
				String specialization = rs.getString("specialization");
				String regNo = rs.getString("regNo");
				String address = rs.getString("address");
				String phone = rs.getString("phone");
				// Add into the html table
				output += "<tr><td>" + Name + "</td>";
				output += "<td>" + specialization + "</td>";
				output += "<td>" + regNo + "</td>";
				output += "<td>" + address + "</td>";
				output += "<td>" + phone + "</td>";

				// buttons
				/*
				 * output +=
				 * "<td><input name=\"btnUpdate\" type=\"button\"value=\"Update\" class=\"btn btn-secondary\"></td>"
				 * + "<td><form method=\"post\" action=\"items.jsp\">" +
				 * "<input name=\"btnRemove\" type=\"submit\" value=\"Remove\"class=\"btn btn-danger\">"
				 * + "<input name=\"userID\" type=\"hidden\" value=\"" + userID + "\">" +
				 * "</form></td></tr>";
				 */
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the doctor information.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateDoctorDetails(String ID, String name, String spec, String reg, String addr, String phn) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE doctor SET doctor_name=?,specialization=?,regNo=?,address=?,phone=?WHERE doctor_id =?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, name);
			preparedStmt.setString(2, spec);
			preparedStmt.setString(3, reg);
			preparedStmt.setString(4, addr);
			preparedStmt.setString(5, phn);
			preparedStmt.setInt(6, Integer.parseInt(ID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the doctor Information.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deleteDoctorDetails(String doctor_id) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from doctor where doctor_id=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(doctor_id));
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the doctor information.";
			System.err.println(e.getMessage());
		}
		return output;
	}

}
