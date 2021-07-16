package test;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

public class Github_Example {
	
	RequestSpecification  reqSpec;
	String sshKey;
	int responsesshKeyID;
	
  @BeforeClass
  public void setUp() {
	  
	  //create request specification
	  reqSpec = new RequestSpecBuilder()
			  .setContentType(ContentType.JSON)
			  .setBaseUri("https://api.github.com")
			  .addHeader("Authorization", "token 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")
			  .build();
	  
	  sshKey="ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQDP3yVN/7PA23IwJ4XOAOPCqLwNWU9sZbvnwY/KPm0bvYudxrruJLTTmnf4WL+3oZj5UrVNM1on0mP6ENj48ZBkyFQqQpaKr1H4g0MzKSCXnQVYUe17QhEQMMssi4aJ7ZrYd8UyLnzA/EvoWpqc81hsnTXT5EqiskKmwwGkKuDv4DM2x6l5hLuObef5LoKHORbigHMJRxTr7sZU5Rig8SxUCFqpSJPPIDg6IP5m6+Y1WcVODnW0nHo/9sgozhsQlPRpt/MADPyuQoFbKtARSfVn9+4QFoy5YYMdEUmqE0uiJ/EUefD73QbVrO8/jbIwk9QZPz+9UQbFzcQdbKRjfA31";
  }
  
  @Test(priority=1)
  public void addKeyPostRequest() {

	  String reqBody="{\"title\": \"TestAPIKey\", \"key\": \""+sshKey +"\"}";


	  Response response = given().spec(reqSpec).when().body(reqBody).post("/user/keys");
	  System.out.println(response.asPrettyString());
	  
	  responsesshKeyID= response.then().extract().path("id");
	  System.out.println(responsesshKeyID);
	  
	  response.then().statusCode(201);
  }
  
  @Test(priority=2)
  public void getKeyGetRequest() {


	  Response response = given().spec(reqSpec).when().get("/user/keys/"+responsesshKeyID);
	  System.out.println(response.asPrettyString());
	  
	  String sshKeyID= response.then().extract().path("[0].id");
	  System.out.println(sshKeyID);
	  
	  response.then().statusCode(200);
  }
  
  @Test(priority=3)
  public void deleteKeyDeleteRequest() {


	  Response response = given().spec(reqSpec).when().delete("/user/keys/"+responsesshKeyID);
	  
	 
	  response.then().statusCode(204);
  }
}
