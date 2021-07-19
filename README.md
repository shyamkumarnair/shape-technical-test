# Shape-Rest API Application

RestAPI application developed using springboot to expose available shapes and ability to add shapes


## Help Documents
### Open API

http://localhost:8080/shape-api-docs

### Swagger

http://localhost:8080/shape-swagger-help.html

## Tools and Libraries used
	1. Spring Tool Suite as IDE
	2. Springboot version 2.5.2
	3. Java version 1.8
	4. JPA
	5. Lombok
	6. JUnit 5
	7. Mockito 
	8. Swagger/Open API
	9. H2 Database
	10. Springboot Developer tools (for docker image)
	
## How to install Lombok
Lombok is a library that facilitates many tedious tasks and reduces Java source code verbosity.

- Lombok on intellij
Lombok uses annotation processing through APT. So, when the compiler calls it, the library generates new source files based on annotations in the originals.
Annotation processing isn't enabled by default, though.
Therefore, the first thing to do is to enable annotation processing in our project.
Go to the Preferences | Build, Execution, Deployment | Compiler | Annotation Processors and make sure of the following:
Enable annotation processing box is checked
Obtain processors from project classpath option is selected
There is a dedicated plugin that makes IntelliJ aware of the source code to be generated. After installing it, the errors go away and regular features like Find Usages and Navigate To start working.
Go to the Preferences | Plugins, open the Marketplace tab, type “lombok” and choose Lombok Plugin by Michail Plushnikov.
Next, click the Install button on the plugin page.
After the installation, click the Restart IDE button.

- Lombok on Eclipse
For Eclipse IDE, get the Lombok jar first. The latest version is located on Maven Central.
Next,  run the jar via java -jar command, and an installer UI will open. This tries to automatically detect all available Eclipse installations, but it's also possible to specify the location manually.
Once selected the installations, press the Install/Update button
If the installation is successful, exit the installer.
After installing the plugin, please restart the IDE and ensure that Lombok is correctly configured. Please verify this in the About dialog.

## How to build docker image

maven command used ( mvnw spring-boot:build-image)

## How to push the image to docker hub

docker push {docker-userid}/shape-technical-test

## How to run the application
#### 1. As Java application 

    Run com.pupil.handson.test.ShapeApplication as a Java Application.

    Runs on default port of Spring Boot - 8080

    Application uses h2 database to run the tests.

#### 2. As Jar

    The application can be run as a Jar

`   mvn clean install` generate a jar

#### 3. As Docker Image

    The docker image for this application is available on docker hub as well.

    It can be downloaded from https://hub.docker.com/repository/docker/shyamkumarnair/shape-technical-test

    -Launching the application

    docker run -d -p 8080:8080 shyamkumarnair/shape-technical-test:latest

### The Shape application exposes following REST URLs

* [Get all Shapes](http://localhost:8080/shapes) - Get all shapes currently exists in the database

* [Create/Add a new Square (HTTP POST)](http://localhost:8080/shapes/square) - Create or add a new square to shape database
        
    - Validations:
      - name should be unique
      - xBottomLeft is a BigDecimal with max 2 fractional values
      - yBottomLeft is a BigDecimal with max 2 fractional values
      - xTopLeft is a BigDecimal with max 2 fractional values
      - yTopLeft is a BigDecimal with max 2 fractional values
      - xBottomRight is a BigDecimal with max 2 fractional values
      - yBottomRight is a BigDecimal with max 2 fractional values
      - xTopRight is a BigDecimal with max 2 fractional values
      - yTopRight is a BigDecimal with max 2 fractional values

  - Exceptions:
      - InvalidSquareException (The new square is not a square)
      - SquareOverlapException (The new square overlaps with an existing square)

  - Sample json
    - {
        "name": "Drywall Square",
        "type": "SQUARE",
        "description": "Drywall Square",	  
			  "xbottomLeft": 0,
			  "ybottomLeft": 0,
			  "xtopLeft": 0,
			  "ytopLeft": 5,
			  "xbottomRight": 5,
			  "ybottomRight": 0,
			  "xtopRight": 5,
			  "ytopRight": 5 
		  }


### Security 
Security has been disabled by default.
 * To enable it just uncomment the following section in pom.xml
           <dependency>
             <groupId>org.springframework.boot</groupId>
             <artifactId>spring-boot-starter-security</artifactId>
         </dependency>
 * Username and password set are as follows
      - spring.security.user.name=testuser
      - spring.security.user.password=test_password!
      

### Database
Currently used inmemory H2 database. ShapeDB is the database schema name

To access H2 console use
    -  http://localhost:8080/h2-console         
    

The current schema is in a flat structure so that it could be adaptable to sql and nosql databases.

### CACHING

Caching is basic right now, however easily pluggable to ehcache or other equivalent. 


### Tests
    
Tests are in SquareValidatorTest.java. Have tested for the following scenarios,

Note - [ The co-ordinates are in the order of (xBottomLeft, yBottomLeft, xTopLeft, yTopLeft, xBottomRight, yYBottomRight, xTopRight, yTopRight]
		 
#### SUCCESSFUL SCENARIOS
	
	1)  new Square coordinates= ("0.00", "0.00", "0.00", "5.00", "5.00", "0.00", "5.00", "5.00")
	
	2)  existing square co-ordinates= ("0.00", "0.00", "0.00", "5.00", "5.00", "0.00", "5.00", "5.00");
		new square co-ordinates = ("7.00", "0.00", "7.00", "5.00", "12.00", "0.00", "12.00", "5.00");
	
	3)  existing square co-ordinates= ("0.00", "0.00", "0.00", "5.00", "5.00", "0.00", "5.00", "5.00")
		new square co-ordinates = ("5.00", "0.00", "5.00", "5.00", "10.00", "0.00", "10.00", "5.00")

	4)  existing square co-ordinates= ("0.00", "3.00", "0.00", "8.00", "5.00", "3.00", "5.00", "8.00")
		new square co-ordinates = ("7.00", "0.00", "7.00", "5.00", "12.00", "0.00", "12.00", "5.00")
		
	5)	existing square co-ordinates= ("0.00", "3.00", "0.00", "8.00", "5.00", "3.00", "5.00", "8.00")
		new square co-ordinates = ("5.00", "0.00", "5.00", "5.00", "10.00", "0.00", "10.00", "5.00")
		
	6)	existing square co-ordinates= ("0.00", "5.00", "0.00", "9.00", "4.00", "5.00", "4.00", "9.00")
		new square co-ordinates = ("5.00", "0.00", "5.00", "5.00", "10.00", "0.00", "10.00", "5.00")
		
	7)	existing square co-ordinates= ("0.00", "0.00", "0.00", "5.00", "5.00", "0.00", "5.00", "5.00");
		new square co-ordinates = ("7.00", "0.00", "7.00", "5.00", "12.00", "0.00", "12.00", "5.00");


#### INVALID SQUARE EXCEPTION SCENARIOS
	
	1)  new Square coordinates= ("0.00", "0.00", "0.00", "4.00", "5.00", "0.00", "5.00", "4.00")
	2)  new Square coordinates= ("5.00", "5.00", "5.00", "5.00", "5.00", "5.00", "5.00", "5.00")
    3)  new Square coordinates= ("0.00", "0.00", "0.00", "5.00", "5.00", "0.00", "5.00", "5.00")


	
#### SQUARE OVERLAP EXCEPTION SCENARIOS
	
	1)	existing square co-ordinates= ("0.00", "0.00", "0.00", "7.00", "7.00", "0.00", "7.00", "7.00")
		new square co-ordinates = ("6.00", "0.00", "6.00", "7.00", "13.00", "0.00", "13.00", "7.00")

	2)	existing square co-ordinates= ("0.00", "0.00", "0.00", "5.00", "5.00", "0.00", "5.00", "5.00")
		new square co-ordinates = ("0.00", "0.00", "0.00", "5.00", "5.00", "0.00", "5.00", "5.00")

	3)	existing square co-ordinates= ("0.00", "3.00", "0.00", "8.00", "5.00", "3.00", "5.00", "8.00")
		new square co-ordinates = ("3.00", "0.00", "3.00", "5.00", "8.00", "0.00", "8.00", "5.00")

	4)	existing square co-ordinates= ("0.00", "4.00", "0.00", "10.00", "6.00", "4.00", "6.00", "10.00")
		new square co-ordinates = ("5.00", "0.00", "5.00", "5.00", "10.00", "0.00", "10.00", "5.00")

	5)	existing square co-ordinates= ("0.00", "0.00", "0.00", "10.00", "10.00", "0.00", "10.00", "10.00")
		new square co-ordinates = ("3.00", "3.00", "3.00", "8.00", "8.00", "3.00", "8.00", "8.00")
 
