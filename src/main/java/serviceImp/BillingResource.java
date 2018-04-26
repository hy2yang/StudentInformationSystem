package serviceImp;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import DAO.BillDAO;

@Path("fee")
public class BillingResource {
	
	@Path("/billing")
	@POST
    @Produces(MediaType.TEXT_PLAIN)
	public String generateBill(@FormParam("studentID") String sID){	
		return BillDAO.generateBill(sID);
	}
	

}
