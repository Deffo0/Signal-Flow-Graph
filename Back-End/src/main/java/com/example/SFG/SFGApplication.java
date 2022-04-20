package com.example.SFG;

import com.example.SFG.services.ForwardPathsGetter;
import com.example.SFG.services.Test2;
import com.example.SFG.services.Tester2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SFGApplication {

	public static void main(String[] args) {
		ForwardPathsGetter getter = new ForwardPathsGetter();
		Tester2 test2 = new Tester2(getter);
		test2.test();
		//SpringApplication.run(SFGApplication.class, args);
	}

}
