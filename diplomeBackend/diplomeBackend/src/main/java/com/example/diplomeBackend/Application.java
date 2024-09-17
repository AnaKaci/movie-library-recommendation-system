package com.example.diplomeBackend;

//import com.example.diplomeBackend.services.TmdbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.diplomeBackend.repositories")
public class Application {

//	@Autowired
//	private TmdbService tmdbService;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		tmdbService.fetchPopularMovies();
//	}
}