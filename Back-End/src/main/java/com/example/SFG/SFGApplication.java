package com.example.SFG;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SFGApplication {

	public static void main(String[] args) {
		ForwardPathsAndLoopsGetter getter = new ForwardPathsAndLoopsGetter();
		Tester3 test3 = new Tester3(getter);
		test3.test();
		SpringApplication.run(SFGApplication.class, args);
	}

}
