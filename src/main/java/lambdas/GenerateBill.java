package lambdas;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return json;
	}

}
