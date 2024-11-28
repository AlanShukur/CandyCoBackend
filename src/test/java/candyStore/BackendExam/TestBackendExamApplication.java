package candyStore.BackendExam;

import org.springframework.boot.SpringApplication;

public class TestBackendExamApplication {

	public static void main(String[] args) {
		SpringApplication.from(BackendExamApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
