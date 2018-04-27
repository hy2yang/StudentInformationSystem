package serviceImp;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import DAO.BillDAO;

@Path("billing")
public class BillingResource {
	
	@Path("/{studentID}")
	@GET
    @Produces(MediaType.TEXT_PLAIN)
	public String generateBill(@PathParam("studentID") String sID){	
		return BillDAO.generateBill(sID);
	}
	

}
