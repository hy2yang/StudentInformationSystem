package lambdas;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.SubscribeRequest;

public class SubscribeInactiveStudentNotif implements RequestHandler<String, String> {	
	
	private static AmazonSNS SNS_CLIENT = AmazonSNSClientBuilder.standard()
			.withRegion(Regions.US_WEST_2).build();
	
	private static String topicARN = "arn:aws:sns:us-west-2:056025374430:InactiveStudentNotif";

	@Override
	public String handleRequest(String json, Context context) {
		String email = Item.fromJSON(json).getString("email");
		SNS_CLIENT.subscribe(new SubscribeRequest(topicARN, "email", email));
		return json;
	}

}
