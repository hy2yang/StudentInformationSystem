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

import DAO.AnnouncementDAO;
import DAO.CourseDAO;
import DAO.CrossDAO;
import DAO.LectureDAO;
import DAO.StudentDAO;
import entity.Announcement;
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
	public String addCourse( @FormParam("courseID") String cID
			,@FormParam("name") String name ,@FormParam("professorID") String pID){		
		CourseDAO.addCourse(new Course(cID, name, pID));
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
	
	
	
	@Path("/{courseID}/announcements")
	@GET
    @Produces(MediaType.TEXT_PLAIN)
	public String getAllAnnouncementsOfCourse(@PathParam("courseID") String cID) {
		List<Announcement> res=AnnouncementDAO.getAnnouncementsOfCourse(cID);
		try {
			return JSONConverter.object2JSON(res);
		} catch (JsonProcessingException e) {			
			e.printStackTrace();
			return res.toString();
		}
	}
	
	@Path("/{courseID}/announcements")
	@POST
    @Produces(MediaType.TEXT_PLAIN)
	public String setBoardOfCourse(@PathParam("courseID") String cID,
			@FormParam("professorID") String pID, @FormParam("header") String header,
			@FormParam("body") String body) {
		AnnouncementDAO.postAnnouncement(new Announcement(cID,pID,header,body));
		return getAllAnnouncementsOfCourse(cID);
	}
	
	@Path("/{courseID}/announcements/{announcementIndex}")
	@GET
    @Produces(MediaType.TEXT_PLAIN)
	public String deleteBoardOfCourse(@PathParam("courseID") String cID, 
			@PathParam("announcementIndex") int index) {
		String id = CourseDAO.getCourseByID(cID).getAnnouncementIDs().get(index);
		Announcement res= AnnouncementDAO.getAnnouncementByID(id);
		try {
			return JSONConverter.object2JSON(res);
		} catch (JsonProcessingException e) {			
			e.printStackTrace();
			return res.toString();
		}
	}
	
	
	
	
	@Path("/{courseID}/enrolledStudents")
	@GET
    @Produces(MediaType.TEXT_PLAIN)
	public String getStudentsOfCourse(@PathParam("courseID") String cID) {
		Set<String> ids=CourseDAO.getCourseByID(cID).getStudentIDs();
		List<Student> res =  StudentDAO.getStudentByID(ids);
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
	public String enrollStudent(@PathParam("courseID") String cID, @FormParam("studentID") String sID) {
		CrossDAO.studentEnrollCourse(sID, cID);
		return getStudentsOfCourse(cID);
	}
	
	@Path("/{courseID}/enrolledStudents/{studentID}")
	@DELETE
    @Produces(MediaType.TEXT_PLAIN)
	public String dropStudent(@PathParam("courseID") String cID, @PathParam("studentID") String sID) {
		CrossDAO.studentDropCourse(sID, cID);
		return getStudentsOfCourse(cID);
	}
	
	
	@Path("/{courseID}/lectures")
	@GET
    @Produces(MediaType.TEXT_PLAIN)
	public String getLecturesOfCourse(@PathParam("courseID") String cID) {
		List<String> ids=CourseDAO.getCourseByID(cID).getLectureIDs();
		List<Lecture> res = LectureDAO.getLectureByID(ids);
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
										@FormParam("lectureID") String id) {
		LectureDAO.addLecture(new Lecture(id));
		CourseDAO.getCourseByID(cID).addLecture(id);
		return getLecturesOfCourse(cID);
		
	}
	
	@Path("/{courseID}/lectures/{lectureIndex}")
	@DELETE
    @Produces(MediaType.TEXT_PLAIN)
	public String deleteLecture(@PathParam("courseID") String cID,
										@PathParam("lectureIndex") int index) {
		CourseDAO.getCourseByID(cID).deleteLectureByIndex(index);
		return getLecturesOfCourse(cID);		
	}
	
	@Path("/{courseID}/lectures/{lectureIndex}")
	@GET
    @Produces(MediaType.TEXT_PLAIN)
	public String getLecture(@PathParam("courseID") String cID,
			@PathParam("lectureIndex") int index) {
		String id = CourseDAO.getCourseByID(cID).getLectureIDByIndex(index);
		Lecture l = LectureDAO.getLectureByID(id);
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
		
		String id = CourseDAO.getCourseByID(cID).getLectureIDByIndex(index);
		List<String> res = LectureDAO.getLectureByID(id).getNotes();		
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
		String id = CourseDAO.getCourseByID(cID).getLectureIDByIndex(index);
		LectureDAO.getLectureByID(id).addNote(note);	
		return getnotes(cID, index);
	}
	
	@Path("/{courseID}/lectures/{lectureIndex}/notes/{noteIndex}")
	@DELETE
    @Produces(MediaType.TEXT_PLAIN)
	public String deleteNote(@PathParam("courseID") String cID,
			@PathParam("lectureIndex") int lIndex, @PathParam("noteIndex") int nIndex ) {
		String id = CourseDAO.getCourseByID(cID).getLectureIDByIndex(lIndex);
		LectureDAO.getLectureByID(id).deleteNote(nIndex);
		return getnotes(cID, lIndex);
	}
	
	@Path("/{courseID}/lectures/{lectureIndex}/notes/{noteIndex}")
	@PUT
    @Produces(MediaType.TEXT_PLAIN)
	public String updateNote(@PathParam("courseID") String cID, @PathParam("lectureIndex") int lIndex, 
			@PathParam("noteIndex") int nIndex,  @FormParam("note") String note) {
		String id = CourseDAO.getCourseByID(cID).getLectureIDByIndex(lIndex);
		LectureDAO.getLectureByID(id).updateNote(nIndex, note);
		return getnotes(cID, lIndex);
	}
	
	@Path("/{courseID}/lectures/{lectureIndex}/materials")
	@GET
    @Produces(MediaType.TEXT_PLAIN)
	public String getMaterials(@PathParam("courseID") String cID,
			@PathParam("lectureIndex") int index) {
		String id = CourseDAO.getCourseByID(cID).getLectureIDByIndex(index);
		Map<String, String> res = LectureDAO.getLectureByID(id).getAllMaterails();	
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
			@FormParam("materialName") String name , @FormParam("materialURL") String URL) {
		String id = CourseDAO.getCourseByID(cID).getLectureIDByIndex(index);
		LectureDAO.getLectureByID(id).putMaterial(name, URL);	
		return getMaterials(cID, index);	
	}
	
	@Path("/{courseID}/lectures/{lectureIndex}/materials/{materialName}")
	@DELETE
    @Produces(MediaType.TEXT_PLAIN)
	public String deleteMaterial(@PathParam("courseID") String cID, @PathParam("lectureIndex") int index,
			@PathParam("materialName") String name) {
		String id = CourseDAO.getCourseByID(cID).getLectureIDByIndex(index);
		LectureDAO.getLectureByID(id).deleteMaterial(name);	
		return getMaterials(cID, index);	
	}
	
	@Path("/{courseID}/lectures/{lectureIndex}/materials/{materialName}")
	@PUT
    @Produces(MediaType.TEXT_PLAIN)
	public String updateMaterial(@PathParam("courseID") String cID, @PathParam("lectureIndex") int index,
			@PathParam("materialName") String name , @FormParam("materialURL") String URL) {
		String id = CourseDAO.getCourseByID(cID).getLectureIDByIndex(index);
		LectureDAO.getLectureByID(id).putMaterial(name, URL);	
		return getMaterials(cID, index);	
	}

}
