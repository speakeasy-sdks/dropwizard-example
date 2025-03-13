### Dropwizard reference app with Clerk API Authentication

This is a reference app that demonstrates how to use Clerk API to authenticate users in Dropwizard application.

To run this application, follow the steps:

---
1. Ensure `CLERK_API_SECRET_KEY` environment variable is set with correct secret key.
2. Run `mvn clean install` to build your application
3. Start application with `java -jar target/dropwizard-example-1.0-SNAPSHOT.jar server config.yml`
4. To check that your application is running enter url `http://localhost:8080`
