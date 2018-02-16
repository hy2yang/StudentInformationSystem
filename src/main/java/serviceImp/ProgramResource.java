package serviceImp;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import DAO.ProgramDAO;
import entity.Program;


@Path("programs")
public class ProgramResource {	
	
	@GET
    @Produces(MediaType.TEXT_PLAIN)
	public String getAllPrograms(){
		return ProgramDAO.getAllPrograms().toString();
	}
	
	@Path("/{programID}")
	@GET
    @Produces(MediaType.TEXT_PLAIN)
	public String getProgramByName( @PathParam("programID") int i){
		return ProgramDAO.getProgramByID(i).toString();
	}	
	
	
	@PUT
    @Produces(MediaType.TEXT_PLAIN)
	public String addProgram( @FormParam("programID") int i, @FormParam("programName") String n){
		boolean status= ProgramDAO.addProgram(new Program(i,n ));
		String res=status? "added program with id ":"fail to add program with id ";
		return res+i;
	}
	
	
	@DELETE
    @Produces(MediaType.TEXT_PLAIN)
	public String deleteProgram( @FormParam("programID") int i){
		ProgramDAO.deleteProgramByID(i);
		return "deleted program with id "+i;
	}
	

}
