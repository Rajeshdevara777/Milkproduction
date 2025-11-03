package com.study.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.SecureRandom;

@SpringBootApplication
public class MilkProductionApplication {

	public static void main(String[] args) {
		warmUpSecureRandom();
		SpringApplication.run(MilkProductionApplication.class, args);}

		private static void warmUpSecureRandom () {
			try {
				SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
				byte[] seed = new byte[16];
				sr.nextBytes(seed); // triggers seeding now
			} catch (Exception e) {
				// ignore: best-effort warmup
			}
		}
	}
