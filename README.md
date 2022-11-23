# Supermarket Checkout

Implementation of a supermarket checkout API.
It allows the addition of new products and offers.
The offers can be reused among different products.
Products are scanned at the checkout. Once the checkout
is completed all offers are applied to the basket and
the final price computed and provided by the API.

## Building Project
It's a Maven project so once Maven is installed to be used in the command line then in the command line at the project
route use the `mvn clean install` command to compile the project. You can also use the Maven plugin functionality in the IDE do this.

## Running Project
Install Java 8 JDK and run in the IDE using the CheckoutApplication class as it's a Spring Boot project.
Alternatively, you can do a Maven build to produce an artifact and run the artifact (jar for example) 
in the command line using the JDK.

## API Documentation
Once the application is running - the documentation and API payload examples
can be found here:
[Link to Documentation](http://localhost:8080/swagger-ui.html)