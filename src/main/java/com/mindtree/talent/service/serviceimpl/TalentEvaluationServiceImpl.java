package com.mindtree.talent.service.serviceimpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.mindtree.talent.entity.Testsuite;
import com.mindtree.talent.service.TalentEvaluationService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TalentEvaluationServiceImpl implements TalentEvaluationService {

	@Override
	public void generateCandidateReport(String candidateProjectLocation) throws Exception {
		deleteExcelFileResult();
		runTestCasesOfCandidateProject(candidateProjectLocation);

	}

	private void deleteExcelFileResult() {

		File fileName = new File("C:\\\\Users\\\\M1064549\\\\Desktop\\\\Project\\\\test.xls");

		/* File fileName = new File("/mydata/test.xls"); */
		if (fileName.delete()) {
			log.info("Previous Report Deleted");
		}

	}

	private void runTestCasesOfCandidateProject(String candidateProjectLocation) throws Exception {
		String[] command = { "CMD", "/C", "mvn site" };
		/* String[] command = { "bash", "-c", "mvn site" }; */
		ProcessBuilder builder = new ProcessBuilder(command);
		builder = builder.directory(new File(candidateProjectLocation));
		builder.redirectErrorStream(true);
		Process process = builder.start();
		checkForBuildSuccess(candidateProjectLocation, process);
	}

	private void checkForBuildSuccess(String candidateProjectLocation, Process process) throws Exception {
		log.info("Going To generate the checkstyle report and Test Cases Report");
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line;
		while (true) {
			line = bufferedReader.readLine();
			if (line == null) {
				break;
			}
			if (line.contains("BUILD SUCCESS")) {
				log.info("Generating Report is in Progress");
				reportTransferToReportPublishService(candidateProjectLocation);
				calculateCandidateMarks(candidateProjectLocation);
			}
		}

	}

	private void reportTransferToReportPublishService(String candidateProjectLocation) {

		LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

		/*
		 * FileSystemResource value = new FileSystemResource(new
		 * File("/mydata/test.xls"));
		 */

		log.info("Transferred the report to publish service");
		FileSystemResource value = new FileSystemResource(
				new File(candidateProjectLocation + "/target/site/surefire-report.html"));
		FileSystemResource value1 = new FileSystemResource(
				new File(candidateProjectLocation + "/target/site/checkstyle.html"));
		map.add("file", value);
		map.add("file", value1);
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.MULTIPART_FORM_DATA);
		HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, header);
		RestTemplate template = new RestTemplate();
		template.exchange("http://localhost:8082/save", HttpMethod.POST, requestEntity, String.class);
		/*
		 * template.exchange("http://localhost:8082/save", HttpMethod.POST,
		 * requestEntity, String.class);
		 */
	}

	private void calculateCandidateMarks(String candidateProjectLocation) throws Exception {
		/*
		 * File file = new File(candidateProjectLocation +
		 * "\\target\\surefire-reports\\TEST-com.mindtree.candidate.controller.CandidateControllerTest.xml"
		 * );
		 */
		log.info("Marks Evaluation of the particular Candidate is going on...");
		File file = new File(candidateProjectLocation
				+ "/target/surefire-reports/TEST-com.mindtree.candidate.controller.CandidateControllerTest.xml");
		JAXBContext jaxbContext = JAXBContext.newInstance(Testsuite.class);
		Unmarshaller unmarsh = jaxbContext.createUnmarshaller();
		Testsuite testCaseReport = (Testsuite) unmarsh.unmarshal(file);
		
		testCaseResultToExcel(testCaseReport);
	}

	private void testCaseResultToExcel(Testsuite testCaseReport) throws IOException {
		int index = 0, result = 0;
		String fileName = "C:\\Users\\M1064549\\Desktop\\Project\\test.xls";
		/* String fileName = "/mydata/test.xls"; */

		FileInputStream marksFile = new FileInputStream("C:\\Users\\M1064549\\Documents\\workspace-spring-tool-suite-4-4.8.0.RELEASE\\TalentEvoluation\\marks.properties");

		/* FileInputStream marksFile = new FileInputStream("marks.properties"); */
		Properties pro = new Properties();
		pro.load(marksFile);
		/* static String fileName="/mydata/test.xls"; */
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		HSSFRow row = sheet.createRow(0);
		row.createCell(0).setCellValue("TestCaseName");
		row.createCell(1).setCellValue("Class Name");
		row.createCell(2).setCellValue("Status");
		row.createCell(3).setCellValue("Marks");
		for (index = 0; index < testCaseReport.getTestcase().size(); index++) {
			row = sheet.createRow(index + 1);
			row.createCell(0).setCellValue(testCaseReport.getTestcase().get(index).getName());
			row.createCell(1).setCellValue(testCaseReport.getTestcase().get(index).getClassname());
			if (testCaseReport.getTestcase().get(index).getFailure() == null) {
				row.createCell(2).setCellValue("Passed");
				result = result + Integer.parseInt(pro.getProperty(testCaseReport.getTestcase().get(index).getName()));
				row.createCell(3).setCellValue(pro.getProperty(testCaseReport.getTestcase().get(index).getName()));
			} else {
				row.createCell(2).setCellValue(testCaseReport.getTestcase().get(index).getFailure());
				row.createCell(3).setCellValue("0");
			}

		}
		row = sheet.createRow(index + 1);
		row.createCell(0).setCellValue("Totale Score:- " + result);
		FileOutputStream out = new FileOutputStream(fileName);
		workbook.write(out);
		out.close();
		log.info("Reports Generated you can checkout the report");

	}

}
