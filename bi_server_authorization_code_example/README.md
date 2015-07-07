# business-insight-api-samples-java

This is an example of an application written in Java that authorises to access the data from an user through [OAuth's](https://developer.nedap-bi.com/oauth/) [authorization code](https://developer.nedap-bi.com/oauth/grants/authorization_code) flow.
The sample application acts as an client, that communicates with the authorization server at nedap-bi.com.
For this example the OAuth2 library com.google.api-client is used.


## Requirements
- Java 1.8
- Maven
- Eclipse or your favorite editor

## Project dependencies
- [Spark framework](http://sparkjava.com/)
- [Google OAuth2 library](https://developers.google.com/api-client-library/java/google-api-java-client/oauth2)
- [Mustache Template Engine](https://mustache.github.io/)

## Configuration

Enter your API_KEY and API_SECRET in the file `OAuth2ClientSettings.java`

## Running the application

- Make sure the requirements are met.
- `git clone git@github.com:nedap/business-insight-api-samples-java.git`
- `cd business-insight-api-samples-java/bi_server_authorization_code_example`
-  Set your api_token and api_key in `/src/main/java/com/nedap_bi/examples/server/authorizationcode/OAuth2ClientSettings.java`
- `mvn compile`
- `mvn exec:java`
-  navigate in your browser to `http://localhost:4567`

If you are having issues make sure you use Java 8 and have no other socket listening to 4567.

## Getting started and project structure

This section covers how to read the code to get a quick start.

  |-- src  
  |   |-- main  
  |       `-- java  
  |           `-- com  
  |               `-- nedap_bi  
  |                   `-- examples  
  |                       `-- server  
  |                           `-- authorizationcode  
  |                               |-- controllers  
  |                               |-- helpers  
  |                               `-- models  
  |  
  |-- resources  
  |   `-- templates  
  


/src/main/java/com/nedap_bi/examples/server/authorizationcode/  
The Application main can be found in `App.java`, this is your starting endpoint.  
`OAuth2ClientSettings.java` is where your app can be configured.  

/src/main/java/com/nedap_bi/examples/server/authorizationcode/controllers  
OAuth authorization logic exists in `MyAppController.java`. Start here if you want to see how authorization works.  
The authentication logic exists in `HomeController.java`.

/src/main/java/com/nedap_bi/examples/server/authorizationcode/helper  
This map is ment for all sort of helper methods used by the application.
`OAuthHelper.java` contains all OAuth logic.  

/src/main/java/com/nedap_bi/examples/server/authorizationcode/models  
This map is ment for all kind of dummy models used by the application.  

/resources/templates/  
Contains all html templates. 
