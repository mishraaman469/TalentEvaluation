package com.mindtree.candidate.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CandidateControllerTest {

	@Autowired
	CandidateController candidateController;
	
	@Test
	void oneTest() {
		assertEquals("working", candidateController.getMessage());
	}
	
	@Test
	void twoTest() {
		assertEquals("Not Working", candidateController.getMessage());
	}
	
	@Test
	void threeTest() {
		assertEquals("working", candidateController.getMessage());
	}
	
	

}
