package lambdas;

import java.util.Map;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class MarkActive implements RequestHandler<Map<String, Object>, Map<String, Object>> {

	private static Table t = new DynamoDB(
			AmazonDynamoDBClientBuilder
			.standard()
			.withCredentials(new EnvironmentVariableCredentialsProvider())
			.withRegion("us-west-2")
			.build()
			)
			.getTable("Students");

	@Override
	public Map<String, Object> handleRequest(Map<String, Object> json, Context context) {
		String sID = (String) json.get("studentID");
		t.updateItem(
				new UpdateItemSpec()
				.withPrimaryKey("studentID", sID)
				.withUpdateExpression("set active = :a")
				.withValueMap(new ValueMap().withNumber(":a", 1))
				);
		return t.getItem("studentID", sID).asMap();
	}

}