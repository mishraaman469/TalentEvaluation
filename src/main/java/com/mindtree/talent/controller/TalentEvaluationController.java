package com.mindtree.talent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mindtree.talent.service.TalentEvaluationService;

@RestController
public class TalentEvaluationController {
	
		@Autowired
		TalentEvaluationService talentEvaluationService;
	
		@GetMapping("/getResult/{projectName}")
		public String getCandidateResult(@PathVariable String projectName) throws Exception {
			
			  String candidateProjectLocation=
			  "C:\\Users\\M1064549\\Documents\\workspace-spring-tool-suite-4-4.8.0.RELEASE\\TalentEvoluation\\CandidateProjects\\"
			  +projectName;
			 
			/*
			 * String candidateProjectLocation="/CandidateProjects/" + projectName + "/";;
			 */
			talentEvaluationService.generateCandidateReport(candidateProjectLocation);
			return "Working "+projectName;
		}
		
} 
