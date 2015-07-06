# business-insight-api-samples-java

This is an example of an application written in java that authorises an user through the the authorization code flow.
The sample application acts as an client, that communicates with the authorization server at nedap-bi.com.
For this example the OAuth2 library com.google.api-client is used.


## Requirements
- Java 1.8
- Maven
- Eclipse or your favorite editor

## Project dependencies
- [Spark framework](http://sparkjava.com/)
- [Google OAuth2 library](https://developers.google.com/api-client-library/java/google-api-java-client/oauth2)


## Configuration

Enter your API_KEY and API_SECRET in the file `OAuth2ClientSettings.java`

## Running the application

- Make sure the requirements are met.
- `git clone git@github.com:nedap/business-insight-api-samples-java.git`
- `cd business-insight-api-samples-java/bi_server_authorization_code_example`
- `mvn compile`
- `mvn exec:java`
-  navigate in your browser to `http://localhost:4567`

If you are having issues make sure you use java 8 and have no other socket listening to 4567.


