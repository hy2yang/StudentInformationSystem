package hyyang.csye6225.courseService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("test")
public class test {
	
	@GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getTest() {
        return "a GET request was sent to /test";
    }	
	
	@Path("/{p}")
	@GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getTest( @PathParam( "p" ) String n ) {
        return "a GET request was sent to /test/"+n;
    }
	
}
