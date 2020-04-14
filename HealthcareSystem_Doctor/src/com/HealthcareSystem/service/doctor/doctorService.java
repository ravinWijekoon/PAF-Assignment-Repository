package com.HealthcareSystem.service.doctor;

//For REST Service
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//For XML
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import com.HealthcareSystem.model.Doctor;
//For JSON
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Path("/Doctors")
public class doctorService {

	Doctor Obj = new Doctor();

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertDoctor(@FormParam("doctor_name") String doctor_name,
			@FormParam("specialization") String specialization, @FormParam("regNo") String regNo,
			@FormParam("address") String address, @FormParam("phone") String phone) {
		String output = Obj.insertDoctor(doctor_name, specialization, regNo, address, phone);
		return output;
	}

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readDoctors() {
		return Obj.readDoctors();
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateDoctorDetails(String doctorData) {
		// Convert the input string to a JSON object
		JsonObject itemObject = new JsonParser().parse(doctorData).getAsJsonObject();
		// Read the values from the JSON object
		String doctor_id = itemObject.get("doctor_id").getAsString();
		String name = itemObject.get("doctor_name").getAsString();
		String spec = itemObject.get("specialization").getAsString();
		String reg = itemObject.get("regNo").getAsString();
		String addr = itemObject.get("address").getAsString();
		String phn = itemObject.get("phone").getAsString();

		String output = Obj.updateDoctorDetails(doctor_id, name, spec, reg, addr, phn);
		return output;
	}

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteDoctorDetails(String doctorData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(doctorData, "", Parser.xmlParser());

		// Read the value from the element <userID>
		String doctor_id = doc.select("doctor_id").text();
		String output = Obj.deleteDoctorDetails(doctor_id);
		return output;
	}

}
