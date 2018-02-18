package serviceImp;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;

import DAO.CourseDAO;
import DAO.CrossDAO;
import entity.Course;
import entity.Lecture;
import entity.Student;
import utilitises.JSONConverter;

@Path("courses")
public class CourseResource {
	
	@GET
    @Produces(MediaType.TEXT_PLAIN)
	public String getAllCourses(){
		Collection<Course> list=CourseDAO.getAllCourses();		
		try {
			return JSONConverter.object2JSON(list);
		} catch (JsonProcessingException e) {			
			e.printStackTrace();
			return list.toString();
		}
	}
	
	@POST
    @Produces(MediaType.TEXT_PLAIN)
	public String addCourse( @FormParam("courseID") String cID){		
		CourseDAO.addCourse(new Course(cID));
		return getAllCourses();
	}
	
	@Path("/{courseID}")
	@DELETE
    @Produces(MediaType.TEXT_PLAIN)
	public String deleteCourse(@PathParam("courseID") String cID) {
		CourseDAO.deleteCourseByID(cID);
		return getAllCourses();
	}
	
	@Path("/{courseID}")
	@GET
    @Produces(MediaType.TEXT_PLAIN)
	public String getCourseByID(@PathParam("courseID") String cID) {
		Course res=CourseDAO.getCourseByID(cID);
		try {
			return JSONConverter.object2JSON(res);
		} catch (JsonProcessingException e) {			
			e.printStackTrace();
			return res.toString();
		}
	}
	
	
	
	@Path("/{courseID}/board")
	@GET
    @Produces(MediaType.TEXT_PLAIN)
	public String getBoardOfCourse(@PathParam("courseID") String cID) {
		Object res=CourseDAO.getCourseByID(cID).getBoard();
		try {
			return JSONConverter.object2JSON(res);
		} catch (JsonProcessingException e) {			
			e.printStackTrace();
			return res.toString();
		}
	}
	
	@Path("/{courseID}/board")
	@PUT
    @Produces(MediaType.TEXT_PLAIN)
	public String setBoardOfCourse(@PathParam("courseID") String cID, @FormParam("newBoard") Object b) {
		CourseDAO.getCourseByID(cID).setBoard(b);
		return getBoardOfCourse(cID);
	}
	
	@Path("/{courseID}/board")
	@DELETE
    @Produces(MediaType.TEXT_PLAIN)
	public String deleteBoardOfCourse(@PathParam("courseID") String cID) {
		CourseDAO.getCourseByID(cID).deleteBoard();
		return getBoardOfCourse(cID);
	}
	
	@Path("/{courseID}/roster")
	@GET
    @Produces(MediaType.TEXT_PLAIN)
	public String getRosterOfCourse(@PathParam("courseID") String cID) {
		Object res=CourseDAO.getCourseByID(cID).getRoster();
		try {
			return JSONConverter.object2JSON(res);
		} catch (JsonProcessingException e) {			
			e.printStackTrace();
			return res.toString();
		}
	}
	
	@Path("/{courseID}/roster")
	@PUT
    @Produces(MediaType.TEXT_PLAIN)
	public String setRosterOfCourse(@PathParam("courseID") String cID, @FormParam("newRoster") Object b) {
		CourseDAO.getCourseByID(cID).setRoster(b);
		return getRosterOfCourse(cID);
	}
	
	@Path("/{courseID}/roster")
	@DELETE
    @Produces(MediaType.TEXT_PLAIN)
	public String deleteRosterOfCourse(@PathParam("courseID") String cID) {
		CourseDAO.getCourseByID(cID).deleteRoster();
		return getRosterOfCourse(cID);
	}
	
	
	@Path("/{courseID}/enrolledStudents")
	@GET
    @Produces(MediaType.TEXT_PLAIN)
	public String getStudentsOfCourse(@PathParam("courseID") String cID) {
		Set<Student> res=CourseDAO.getAllStudentsOfCourse(cID);
		try {
			return JSONConverter.object2JSON(res);
		} catch (JsonProcessingException e) {			
			e.printStackTrace();
			return res.toString();
		}
	}
	
	@Path("/{courseID}/enrolledStudents")
	@POST
    @Produces(MediaType.TEXT_PLAIN)
	public String enrollStudent(@PathParam("courseID") String cID, @FormParam("studentID") int sID) {
		CrossDAO.studentEnrollCourse(sID, cID);
		return getStudentsOfCourse(cID);
	}
	
	@Path("/{courseID}/enrolledStudents/{studentID}")
	@DELETE
    @Produces(MediaType.TEXT_PLAIN)
	public String dropStudent(@PathParam("courseID") String cID, @PathParam("studentID") int sID) {
		CrossDAO.studentDropCourse(sID, cID);
		return getStudentsOfCourse(cID);
	}
	
	
	@Path("/{courseID}/lectures")
	@GET
    @Produces(MediaType.TEXT_PLAIN)
	public String getLecturesOfCourse(@PathParam("courseID") String cID) {
		List<Lecture> res=CourseDAO.getCourseByID(cID).getAllLectures();
		try {
			return JSONConverter.object2JSON(res);
		} catch (JsonProcessingException e) {			
			e.printStackTrace();
			return res.toString();
		}
	}
	
	@Path("/{courseID}/lectures")
	@POST
    @Produces(MediaType.TEXT_PLAIN)
	public String addLectureToCourse(@PathParam("courseID") String cID,
										@FormParam("lectureName") String name) {
		CourseDAO.getCourseByID(cID).addLecture( new Lecture(name));
		return getLecturesOfCourse(cID);
		
	}
	
	@Path("/{courseID}/lectures/{lectureIndex}")
	@DELETE
    @Produces(MediaType.TEXT_PLAIN)
	public String deleteLecture(@PathParam("courseID") String cID,
										@PathParam("lectureIndex") int index) {
		CourseDAO.getCourseByID(cID).deleteLecture(index);
		return getLecturesOfCourse(cID);		
	}
	
	@Path("/{courseID}/lectures/{lectureIndex}")
	@GET
    @Produces(MediaType.TEXT_PLAIN)
	public String getLecture(@PathParam("courseID") String cID,
			@PathParam("lectureIndex") int index) {
		Lecture l = CourseDAO.getCourseByID(cID).getLecture(index);		
		try {
			return JSONConverter.object2JSON(l);
		} catch (JsonProcessingException e) {			
			e.printStackTrace();
			return l.toString();
		}		
	}
	
	@Path("/{courseID}/lectures/{lectureIndex}/notes")
	@GET
    @Produces(MediaType.TEXT_PLAIN)
	public String getnotes(@PathParam("courseID") String cID,
			@PathParam("lectureIndex") int index) {
		List<String> res = CourseDAO.getCourseByID(cID).getLecture(index).getNotes();		
		try {
			return JSONConverter.object2JSON(res);
		} catch (JsonProcessingException e) {			
			e.printStackTrace();
			return res.toString();
		}		
	}
	
	@Path("/{courseID}/lectures/{lectureIndex}/notes")
	@POST
    @Produces(MediaType.TEXT_PLAIN)
	public String addNote(@PathParam("courseID") String cID,
			@PathParam("lectureIndex") int index, @FormParam("note") String note ) {
		CourseDAO.getCourseByID(cID).getLecture(index).addNote(note);	
		return getnotes(cID, index);
	}
	
	@Path("/{courseID}/lectures/{lectureIndex}/notes/{noteIndex}")
	@DELETE
    @Produces(MediaType.TEXT_PLAIN)
	public String deleteNote(@PathParam("courseID") String cID,
			@PathParam("lectureIndex") int lIndex, @PathParam("noteIndex") int nIndex ) {
		CourseDAO.getCourseByID(cID).getLecture(lIndex).deleteNote(nIndex);
		return getnotes(cID, lIndex);
	}
	
	@Path("/{courseID}/lectures/{lectureIndex}/notes/{noteIndex}")
	@PUT
    @Produces(MediaType.TEXT_PLAIN)
	public String updateNote(@PathParam("courseID") String cID, @PathParam("lectureIndex") int lIndex, 
			@PathParam("noteIndex") int nIndex,  @FormParam("note") String note) {
		CourseDAO.getCourseByID(cID).getLecture(lIndex).updateNote(nIndex, note);
		return getnotes(cID, lIndex);
	}
	
	@Path("/{courseID}/lectures/{lectureIndex}/materials")
	@GET
    @Produces(MediaType.TEXT_PLAIN)
	public String getMaterials(@PathParam("courseID") String cID,
			@PathParam("lectureIndex") int index) {
		Map<String, Object> res = CourseDAO.getCourseByID(cID).getLecture(index).getAllMaterails();	
		try {
			return JSONConverter.object2JSON(res);
		} catch (JsonProcessingException e) {			
			e.printStackTrace();
			return res.toString();
		}		
	}
	
	@Path("/{courseID}/lectures/{lectureIndex}/materials")
	@POST
    @Produces(MediaType.TEXT_PLAIN)
	public String addMaterial(@PathParam("courseID") String cID, @PathParam("lectureIndex") int index,
			@FormParam("materialName") String name , @FormParam("material") Object m) {
		CourseDAO.getCourseByID(cID).getLecture(index).addMaterial(name, m);	
		return getMaterials(cID, index);	
	}
	
	@Path("/{courseID}/lectures/{lectureIndex}/materials/{materialName}")
	@DELETE
    @Produces(MediaType.TEXT_PLAIN)
	public String deleteMaterial(@PathParam("courseID") String cID, @PathParam("lectureIndex") int index,
			@PathParam("materialName") String name) {
		CourseDAO.getCourseByID(cID).getLecture(index).deleteMaterial(name);	
		return getMaterials(cID, index);	
	}
	
	@Path("/{courseID}/lectures/{lectureIndex}/materials/{materialName}")
	@PUT
    @Produces(MediaType.TEXT_PLAIN)
	public String updateMaterial(@PathParam("courseID") String cID, @PathParam("lectureIndex") int index,
			@PathParam("materialName") String name , @FormParam("material") Object m) {
		CourseDAO.getCourseByID(cID).getLecture(index).addMaterial(name, m);	
		return getMaterials(cID, index);	
	}

}
