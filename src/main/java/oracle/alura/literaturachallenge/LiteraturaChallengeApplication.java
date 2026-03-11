package oracle.alura.literaturachallenge;

import com.fasterxml.jackson.databind.ObjectMapper;
import oracle.alura.literaturachallenge.view.ConsoleMenu;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LiteraturaChallengeApplication implements CommandLineRunner {

    private final ConsoleMenu consoleMenu;

    public LiteraturaChallengeApplication(ConsoleMenu consoleMenu) {
        this.consoleMenu = consoleMenu;
    }

    public static void main(String[] args) {
        SpringApplication.run(LiteraturaChallengeApplication.class, args);
    }

    @Override
    public void run(String... args) {
        consoleMenu.start();
    }
}
