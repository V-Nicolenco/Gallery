package mother.hackers.gallery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class ApiStarter extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ApiStarter.class, args);
    }
}
