package DAO;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entity.Course;
import entity.Student;
import utilitises.DynamoDBClient;
import utilitises.JSONConverter;

public class CrossDAO {
	
	private static DynamoDBMapper mapper = DynamoDBClient.getMapper();	
	
	public static void studentEnrollCourse(String sID, String cID) {
		Student s = StudentDAO.getStudentByID(sID);
		Course c = CourseDAO.getCourseByID(cID);
		s.enrollCourse(cID);
		c.enrollStudent(sID);
		mapper.save(s);
		mapper.save(c);	
		callStepFunctions(s);
	}
	
	public static void studentDropCourse(String sID, String cID) {		
		Student s = StudentDAO.getStudentByID(sID);
		Course c = CourseDAO.getCourseByID(cID);
		s.dropCourse(cID);
		c.dropStudent(sID);
		mapper.save(s);
		mapper.save(c);
		callStepFunctions(s);
	}
	
	private static void callStepFunctions(Student s) {
		String postBody = getJsonString(s);
		try {
			URL url = new URL("https://72munuzpk7.execute-api.us-west-2.amazonaws.com/beta/handlecourseregister");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(postBody);
			wr.flush();
			wr.close();	
			
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			System.out.println(response.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static String getJsonString(Student s) {	
		String stateMachineArn = "arn:aws:states:us-west-2:056025374430:stateMachine:afterCourseRegister";
		
		ObjectMapper mapper = JSONConverter.getMapper();
		ObjectNode root = mapper.createObjectNode();
		root.put("stateMachineArn", stateMachineArn);
		
		ObjectNode input = mapper.createObjectNode();
		input.set("student", mapper.valueToTree(s));
		input.put("numCourses", s.getCourseIDs().size());
		
		root.put("input", input.toString().toString().replaceAll("\"", "\\\""));
		return root.toString();
	}
	
}
