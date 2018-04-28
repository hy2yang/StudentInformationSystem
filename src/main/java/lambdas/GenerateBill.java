package lambdas;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GenerateBill implements RequestHandler<Map<String, Object>, Map<String, Object>>{

	@Override
	public Map<String, Object> handleRequest(Map<String, Object> json, Context context) {
		String sID = (String) json.get("studentID");
		
		try {
			URL url = new URL("http://default-environment.9zgzmiickn.us-east-2.elasticbeanstalk.com/webapi/billing/"+sID);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.getResponseMessage();
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return json;
	}

}
