package serviceImp;

import java.util.Collection;
import java.util.Set;

import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;

import DAO.CrossDAO;
import DAO.StudentDAO;
import entity.Course;
import entity.Student;
import utilitises.JSONConverter;

@Path("students")
public class StudentResource {
	
	@GET
    @Produces(MediaType.TEXT_PLAIN)
	public String getAllStudents(){
		Collection<Student> list=StudentDAO.getAllStudents();		
		try {
			return JSONConverter.object2JSON(list);
		} catch (JsonProcessingException e) {			
			e.printStackTrace();
			return list.toString();
		}
	}
	
	@POST
    @Produces(MediaType.TEXT_PLAIN)
	public String addStudent( @FormParam("studentID") int sID, @FormParam("studentName") String name, @FormParam("programID") int pID){		
		StudentDAO.addStudent( new Student(sID, name, pID));
		return getAllStudents();
	}
	
	@Path("/{studentID}")
	@DELETE
    @Produces(MediaType.TEXT_PLAIN)
	public String deleteStudent(@PathParam("studentID") int sID) {
		StudentDAO.deleteStudentByID(sID);
		return getAllStudents();
	}
	
	@Path("/{studentID}")
	@GET
    @Produces(MediaType.TEXT_PLAIN)
	public String getStudentByID( @PathParam("studentID") int sID) {
		Student res = StudentDAO.getStudentByID(sID);
		try {
			return JSONConverter.object2JSON(res);
		} catch (JsonProcessingException e) {			
			e.printStackTrace();
			return res.toString();
		}
	}
	
	@Path("/{studentID}")
	@POST
    @Produces(MediaType.TEXT_PLAIN)
	public String updateStudentInfo( @PathParam("studentID") int sID, @FormParam("studentName") String name, 
			@FormParam("programID") @DefaultValue("-1") int pID) {
		Student s=StudentDAO.getStudentByID(sID);
		if (name!=null) s.setName(name);
		if (pID!=-1) s.setProgramID(pID);
		return getAllStudents();
	}
	

	@Path("/{studentID}/courses")
	@GET
    @Produces(MediaType.TEXT_PLAIN)
	public String getCourses (@PathParam("studentID") int sID) {
		Set<Course> courses = StudentDAO.getAllCoursesOfStudent(sID);
		try {
			return JSONConverter.object2JSON(courses);
		} catch (JsonProcessingException e) {			
			e.printStackTrace();
			return courses.toString();
		}
	}
	
	@Path("/{studentID}/courses")
	@POST
    @Produces(MediaType.TEXT_PLAIN)
	public String enrollCourse (@PathParam("studentID") int sID, @FormParam("courseID") String cID) {
		CrossDAO.studentEnrollCourse(sID, cID);
		return getCourses(sID);
	}
	
	@Path("/{studentID}/courses/{courseID}")
	@DELETE
    @Produces(MediaType.TEXT_PLAIN)
	public String dropCourse (@PathParam("studentID") int sID, @PathParam("courseID") String cID) {
		CrossDAO.studentDropCourse(sID, cID);
		return getCourses(sID);
	}
	
}
