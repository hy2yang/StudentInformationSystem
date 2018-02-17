package serviceImp;

import java.util.Collection;
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

import DAO.ProgramDAO;
import entity.Program;
import utilitises.JSONConverter;


@Path("programs")
public class ProgramResource {	
	
	@GET
    @Produces(MediaType.TEXT_PLAIN)
	public String getAllPrograms(){
		Collection<Program> list=ProgramDAO.getAllPrograms();		
		try {
			return JSONConverter.object2JSON(list);
		} catch (JsonProcessingException e) {			
			e.printStackTrace();
			return list.toString();
		}
	}
	
	@POST
    @Produces(MediaType.TEXT_PLAIN)
	public String addProgram( @FormParam("programID") int pID, @FormParam("programName") String name){		
		ProgramDAO.addProgram( new Program(pID, name));
		return getAllPrograms();
	}
	
	
	@DELETE
    @Produces(MediaType.TEXT_PLAIN)
	public String deleteProgram( @FormParam("programID") int pID){
		ProgramDAO.deleteProgramByID(pID);
		return getAllPrograms();
	}
	

	@Path("/{programID}")
	@GET
    @Produces(MediaType.TEXT_PLAIN)
	public String getProgramByID( @PathParam("programID") int pID){
		Program res=ProgramDAO.getProgramByID(pID);
		try {
			return JSONConverter.object2JSON(res);
		} catch (JsonProcessingException e) {			
			e.printStackTrace();
			return res.toString();
		}
		
	}
	
	@Path("/{programID}/curriculum")
	@GET
    @Produces(MediaType.TEXT_PLAIN)
	public String getProgramCurriculum(@PathParam("programID") int pID){
		Set<String> res=ProgramDAO.getProgramByID(pID).getCurriculum();
		try {
			return JSONConverter.object2JSON(res);
		} catch (JsonProcessingException e) {			
			e.printStackTrace();
			return res.toString();
		}		
	}
	
	@Path("/{programID}/curriculum")
	@POST
    @Produces(MediaType.TEXT_PLAIN)
	public String addCourseToProgram(@PathParam("programID") int pID, @FormParam("courseID") String cID){
		ProgramDAO.getProgramByID(pID).addCourseByID(cID);
		return getProgramCurriculum(pID);
	}
	
	@Path("/{programID}/curriculum/{courseID}")
	@DELETE
    @Produces(MediaType.TEXT_PLAIN)
	public String deleteCourse(@PathParam("programID") int pID, @PathParam("courseID") String cID){
		ProgramDAO.getProgramByID(pID).deleteCourseByID(cID);
		return getProgramCurriculum(pID);
	}
	
}
