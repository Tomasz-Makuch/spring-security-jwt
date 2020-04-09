# spring-security-jwt
Spring Boot demo project that uses Spring Security with JWT (Json Web Token). In order to test application you need to use POSTMAN:
1) send your post request http://localhost:8080/login with user credential in Body / JSON (3 users are stored in H2 database e.g. 
{
	"username" : "admin",
	"password" : "admin"
}
Application will generate the JWT token and it will be send in response.

2) In order to access resources, you don't have to use login and password again. 
Use received JWT token (without "Bearer ", pay attention to !space!). Selec AUTORIZATION -> TYPE: "Bearer Token", paste your token and send new (GET) request. You have 3 endpoints:
      http://localhost:8080/permitAll
      http://localhost:8080/permitUser
      http://localhost:8080/permitAdmin
If you used in 1) admin credentials, you have ADMIN_ROLE and access to http://localhost:8080/permitAdmin.
If you used in 1) user credentials (e.g. login:user pass:user), you have USER_ROLE and access to http://localhost:8080/permitUser.
To http://localhost:8080/permitAll you have access without token
