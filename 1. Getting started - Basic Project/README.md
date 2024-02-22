# Getting Started
 
## Setting up the Environment: 

Setting up the Maven directory:

1. Download the Maven Source Zip Archive from https://maven.apache.org/download.cgi and extract it to the path: `C:\Program Files`.

2. Edit system environment variables by going to your device settings. Under system variables, create a new variable with the name `M2_HOME` and set its value to the path of the Maven folder you extracted/copied to Program Files (e.g., `C:\Program Files\apache-maven-version`).

3. Add the Maven bin directory to the system PATH. Navigate to the 'Path' variable under system variables, click 'Edit,' then 'New,' and paste `%M2_HOME%\bin`. Click OK/Apply and close the corresponding windows.

4. Reopen the command prompt.

5. Verify Maven installation by typing 'mvn -version.' Successful installation will display the Maven version; otherwise, you'll see the message: 'mvn' is not recognized as an internal or external command.

Setting up Java directory:

1. Download and install the latest version of Java JDK from the Oracle website: https://www.oracle.com/java/technologies/downloads.

2. Verify Java installation on your PC by typing 'java -version' in the command prompt.

3. Follow the same steps as Maven if needed.

Setting up VS Code:

1. Download and install VS Code.

2. In VS Code Extensions, search for "Spring Boot Dashboard" and install the extension.

3. Restart VS Code after the extension installation.

4. Visit the Spring Initializr website and configure your project (Project type: Maven, Language: Java, Packaging: Jar, Java version: Recommended). Add the "Spring Reactive Web" dependency.

Generate Zip:

1. Open the unzipped folder on VS Code.

2. Run the project using the Spring Boot Dashboard extension.

3. Access localhost:8080 in a web browser. You should see an error page: Whitelabel Error Page.

Download and Install PostgreSQL:

1. Download and install PostgreSQL from the official website.

2. Locate the PostgreSQL folder in `%Program Files%`, and copy the PostgreSQL Server directory (e.g., `C:\Program Files\PostgreSQL\version`).

3. Edit system environment variables, create a new variable named `POSTGRES_HOME` with the path to the PostgreSQL Server folder.

4. Add PostgreSQL bin directory to the system PATH. In the 'Path' variable under system variables, click 'Edit,' then 'New,' and paste `%POSTGRES_HOME%\bin`.

5. Verify PostgreSQL installation by typing 'psql --version' in the command prompt.


## Setting up the Project to work with @RestController

Spring WebFlux supports two programming models:

1. Annotation-based: Similar to the traditional Spring MVC Framework, this model uses annotations to map requests to handler methods.

Controller class:

        @RestController
        public class WebFluxController {
            @GetMapping("/mono")
            public Mono<String> getMonoResult() {
                return Mono.just("Result from Mono");
            }
            @GetMapping("/flux")
            public Flux<String> getFluxResult() {
                return Flux.just("Result from Flux");
            }
        }

To handle non-blocking requests in Spring, use the @Async annotation to annotate methods that should be executed asynchronously and the TaskExecutor interface to control the execution of asynchronous tasks. Additionally, you can leverage Reactor (Mono, Flux) and Spring WebFlux.

Flux and Mono:

        // Spring WebFlux uses Project Reactor as a reactive library
        // Mono: Returns 0 or 1 element.
        // Flux: Returns 0…N elements.

2. Functional: Functional programming concepts, such as lambda expressions and functional interfaces, are used to define the routing and handling of requests.

Create a controller class:

        @Configuration
        public class GreetingController {
            @Bean
            public RouterFunction<ServerResponse> routeHelloWorld() {
                return route(GET("/hello"),
                        request -> ServerResponse.ok()
                                .contentType(MediaType.TEXT_PLAIN)
                                .body(Mono.just("Hello, Reactive World!"), String.class));
            }
        }

Locate the Starter Class under the source directory src/main/java/your-package-name.

        import org.springframework.boot.SpringApplication;
        import org.springframework.boot.autoconfigure.SpringBootApplication;

        @SpringBootApplication
        public class Application {
            public static void main(String[] args) {
                SpringApplication.run(Application.class, args);
            }
        }
In this directory, create a folder called 'controller' and a controller class for testing.

        import org.springframework.web.bind.annotation.GetMapping;
        import org.springframework.web.bind.annotation.RestController;
        import reactor.core.publisher.Flux;
        import reactor.core.publisher.Mono;

        @RestController
        public class TestController {
            @GetMapping("/mono")
            public Mono<String> getMonoResult() {
                return Mono.just("Result from Mono");
            }
            @GetMapping("/flux")
            public Flux<String> getFluxResult() {
                return Flux.just("Result from Flux");
            }
        }

Rerun the project.

On localhost:8080/mono and localhost:8080/flux, you should get results.

## @Controller annotation - Thymleaf/Web

While ReactJS will be used for our projects to run externally, it's also important to understand how to configure Thymeleaf in case we need some HTML to run internally.

1. Dependencies:

For Thymeleaf to work on our project, use the following dependency:

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>

This dependency must be injected under the dependencies section of the pom.xml file.

1. Thymeleaf Template:

- Create the directory src/main/resources/templates for Thymeleaf templates. Spring recognizes this path by default.

- Create an index.html file and inside the file type ! and then press Enter immediately; HTML code will be populated.

- Add a heading inside the body tag:

        <h1 th:text="${message}"></h1>

- All variables with 'th' are Thymeleaf variables, and server code/variables are written with ${}.

2. Create another controller class called HomeController and add code to redirect to index.html.

        @Controller
        public class HomeController {
            @GetMapping("/")
            public String index(final Model model) {
                model.addAttribute("message", "Hello from TZM");
                return "index";
            }
        }

Notice the difference between @Controller and @RestController. The @Controller is used for rendering HTML files.