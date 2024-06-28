package com.sirkaue.screensound;

import com.sirkaue.screensound.menu.Menu;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreensoundApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ScreensoundApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Menu menu = new Menu();
        menu.exibeMenu();
    }
}
