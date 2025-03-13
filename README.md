### Dropwizard reference app with Clerk API Authentication

This is a reference app that demonstrates how to use Clerk API to authenticate users in Dropwizard application.

To run this application, follow the steps:

---
1. Ensure `CLERK_API_SECRET_KEY` environment variable is set with correct secret key.
2. Run `mvn clean install` to build your application
3. Start application with `java -jar target/dropwizard-example-1.0-SNAPSHOT.jar server config.yml`
4. To check that your application is running enter url `http://localhost:8080`
5. From a Clerk frontend, use the useSession hook to retrieve the getToken() function:
```
const { getToken } = useSession();
```
6. Then send a request to backend server:
```
await fetch("http://localhost:8080/clerk_jwt", {
       headers: {
          "Authorization": `Bearer ${token}`
       }
   });
```

## Important Note:
This project is not optimized for production and do not address all best practices that should be configured in a production app (401 error handling and HTTPs for example).
These projects serve as a design template and should be given appropriate consideration before being used in production.