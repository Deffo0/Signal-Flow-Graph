package com.example.sgf;

import com.example.sgf.service.ForwardPathsGetter;
import com.example.sgf.service.Tester;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SgfApplication {

	public static void main(String[] args) {
//		ForwardPathsGetter getter = new ForwardPathsGetter();
//		Tester tester = new Tester(getter);
//		tester.test();
		SpringApplication.run(SgfApplication.class, args);
	}

}
