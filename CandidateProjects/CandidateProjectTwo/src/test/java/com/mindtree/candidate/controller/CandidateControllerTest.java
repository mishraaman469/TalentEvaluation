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
		assertEquals("Working", candidateController.getMessageInfo());
	}
	
	@Test
	void twoTest() {
		assertEquals("Working", candidateController.getMessageInfo());
	}
	
	@Test
	void threeTest() {
		assertEquals("Not Working", candidateController.getMessageInfo());
	}

}
