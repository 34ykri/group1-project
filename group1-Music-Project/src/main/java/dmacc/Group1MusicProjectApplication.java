package dmacc;

import dmacc.beans.Product;
import dmacc.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class Group1MusicProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(Group1MusicProjectApplication.class, args);
	}



//	Imput example items into database for testing out product viewer
	@Bean
	CommandLineRunner init (ProductRepository prodRepo) {
		Product cello = new Product("Fiddlermen", "Cello", "Strings", "800.00", "This is a cello that has strings on it and is made of really good wood. It sounds really nice when you know what you're doing");

		Product drums = new Product("Drum Masters", "Drum Set Kit 9000", "Percussion", "599.99", "Beginner drum set kit for newer players young and old. This drum set kit comes with high quality drum heads and incredible sounding cymbals.");

		Product piano = new Product("Yamaha", "Baby Grand Piano", "Percussion", "1500.00","A baby grand piano for all your baby grand piano playing needs. Similar to a grand piano except it is not as grand.");

		return args -> {
			prodRepo.save(cello);
			prodRepo.save(drums);
			prodRepo.save(piano);
		};
	}
}
