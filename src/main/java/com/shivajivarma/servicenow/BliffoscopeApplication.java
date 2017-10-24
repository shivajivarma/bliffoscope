package com.shivajivarma.servicenow;

import com.shivajivarma.servicenow.analyzer.service.AnalyzerService;
import com.shivajivarma.servicenow.bliffoscope.model.Target;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class BliffoscopeApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BliffoscopeApplication.class, args);
	}

	@Autowired
    private AnalyzerService analyzerService;

	@Override
	public void run(String... args) throws Exception {
		try {
            ArrayList<String> targetFiles = new ArrayList<String>();
            targetFiles.add("SlimeTorpedo.blf");
            targetFiles.add("Starship.blf");
            List<Target> targetList = analyzerService.findTargets("TestData.blf", targetFiles,70);
            System.out.println("TARGETS :: ");
            for (Target target: targetList){
                System.out.println(target);
            }

            System.out.println("\n\n Please open http://localhost:8090 \n\n");
        } catch (Exception e){
            System.out.println("EXCEPTION CAUGHT ---->");
			System.out.println("MESSAGE :: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
