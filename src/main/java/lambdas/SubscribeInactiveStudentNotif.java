package lambdas;

import java.util.Map;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.SubscribeRequest;

public class SubscribeInactiveStudentNotif implements RequestHandler<Map<String, Object>, Map<String, Object>> {	
	
	private static AmazonSNS SNS_CLIENT = AmazonSNSClientBuilder.standard()
			.withRegion(Regions.US_WEST_2).build();
	
	private static String topicARN = "arn:aws:sns:us-west-2:056025374430:InactiveStudentNotif";

	@Override
	public Map<String, Object> handleRequest(Map<String, Object> json, Context context) {
		String email = (String) json.get("email");
		SNS_CLIENT.subscribe(new SubscribeRequest(topicARN, "email", email));
		return json;
	}

}
