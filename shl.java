package Assignment;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
public class Project {
	    static class Assessment {
	        String name;
	        List<String> skills;
	        String level;
	        Assessment(String name, List<String> skills, String level) {
	            this.name = name;
	            this.skills = skills;
	            this.level = level;
	        }
	    }
	    static int recommendScore(
	            Assessment assessment,
	            List<String> jobSkills,
	            String experienceLevel
	    ) {
	        int score = 0;

	        for (String skill : jobSkills) {
	            if (assessment.skills.contains(skill)) {
	                score += 10;
	            }
	        }

	        if (assessment.level.equalsIgnoreCase(experienceLevel)
	                || assessment.level.equalsIgnoreCase("All")) {
	            score += 20;
	        }

	        return score;
	    }

	    public static void main(String[] args) {

	        List<String> jobSkills = Arrays.asList("Java", "SQL");
	        String experienceLevel = "Mid";

	        List<Assessment> shlCatalogue = Arrays.asList(
	            new Assessment("Java Programming Test",
	                    Arrays.asList("Java", "OOP"), "Mid"),

	            new Assessment("SQL Skills Test",
	                    Arrays.asList("SQL", "Database"), "Entry"),

	            new Assessment("Logical Reasoning Test",
	                    Arrays.asList("Logic", "Problem Solving"), "All"),

	            new Assessment("Advanced Java Assessment",
	                    Arrays.asList("Java", "Spring"), "Senior")
	        );

	        Map<String, Integer> result = new HashMap<>();

	        for (Assessment a : shlCatalogue) {
	            result.put(a.name, recommendScore(a, jobSkills, experienceLevel));
	        }
	        result.entrySet().stream()
	              .sorted((a, b) -> b.getValue() - a.getValue())
	              .forEach(e ->
	                  System.out.println(e.getKey() + " -> Score: " + e.getValue())
	              );
	    }
	}