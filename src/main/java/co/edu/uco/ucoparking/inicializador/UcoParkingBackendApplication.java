package co.edu.uco.ucoparking.inicializador;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "co.edu.uco.ucoparking")
public class UcoParkingBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(UcoParkingBackendApplication.class, args);
	}

}
