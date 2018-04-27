package lambdas;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.SubscribeRequest;

public class SubscribeActiveStudentToCourse implements RequestHandler<String, String>{
	
	private static DynamoDB ddb = new DynamoDB(
			AmazonDynamoDBClientBuilder
			.standard()
			.withCredentials(new EnvironmentVariableCredentialsProvider())
			.withRegion("us-west-2")
			.build()
			);	

	private static AmazonSNS SNS_CLIENT = AmazonSNSClientBuilder.standard()
			.withRegion(Regions.US_WEST_2).build();
	
	@Override
	public String handleRequest(String json, Context context) {
		Item student = Item.fromJSON(json);
		String cID = student.getStringSet("courseIDs").iterator().next();
		String topicARN = subscribe(student, cID);
    	SNS_CLIENT.publish(new PublishRequest(topicARN, generateNotifBody(cID)));
		return json;
	}
	
	public String subscribe(Item student, String cID) {
		String topicName = cID+"CourseEnrollNotification";
		String topicARN = SNS_CLIENT.createTopic(new CreateTopicRequest(topicName)).getTopicArn();
		SNS_CLIENT.subscribe(new SubscribeRequest(topicARN, "email", student.getString("email")));	
		return topicARN;
	}
	
	public String generateNotifBody(String cID) {
		StringBuilder sb = new StringBuilder();
		Item course = ddb.getTable("Courses").getItem("courseID", cID);
		Item prof = ddb.getTable("Professors").getItem("professorID", course.getString("professorID"));
		sb.append("You have registered for course ")
		.append(course.getString("name")).append("! Professor of the course is ")
		.append(prof.getString("name")).append(" whose email is").append(prof.getString("email"));
		
		return sb.toString();
	}

}
