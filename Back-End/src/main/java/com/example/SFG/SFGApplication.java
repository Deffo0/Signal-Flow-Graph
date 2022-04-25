package com.example.SFG;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SFGApplication {

	public static void main(String[] args) {
		ForwardPathsAndLoopsGetter getter = new ForwardPathsAndLoopsGetter();
		Tester2 test2 = new Tester2(getter);
		test2.test();
		SpringApplication.run(SFGApplication.class, args);
	}

}
