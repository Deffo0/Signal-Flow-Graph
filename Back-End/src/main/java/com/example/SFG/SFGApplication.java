package com.example.SFG;

import com.example.SFG.services.ForwardPathsAndLoopsGetter;
/*
import com.example.SFG.services.Tester4;
*/
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SFGApplication {

	public static void main(String[] args) {
		/*ForwardPathsAndLoopsGetter getter = new ForwardPathsAndLoopsGetter();
		Tester4 test4 = new Tester4(getter);
		test4.test();*/
		SpringApplication.run(SFGApplication.class, args);
	}

}
