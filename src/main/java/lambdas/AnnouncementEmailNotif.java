package lambdas;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent.DynamodbStreamRecord;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;



public class AnnouncementEmailNotif implements RequestHandler<DynamodbEvent, String> {
	
	private AmazonSNS SNS_CLIENT = AmazonSNSClientBuilder.standard()
			.withRegion(Regions.US_WEST_2).build();
	
	private static String ARN_TOPIC = "arn:aws:sns:us-west-2:056025374430:testNotif";
	
	@Override
    public String handleRequest(DynamodbEvent input, Context context) {
    	
		String output ="";
		
    	for (DynamodbStreamRecord record : input.getRecords()) {
    		if (record == null) continue;
    		
    		String inputString =record.toString();
        	
            context.getLogger().log("Input: " + input);
            output = "Hello, "+inputString+"!";
            String outputBody = output+" a new fruit you might like is published!";
            sendEmailNotif(output, outputBody);
    	}    	
    	
        return output;
    } 
    
    private void sendEmailNotif( final String subject, final String message) {
    	PublishRequest pr = new PublishRequest(ARN_TOPIC, message);
    	SNS_CLIENT.publish(pr);
    }
}
