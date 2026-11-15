package edu.bellevue.cis530.studentai.controller;

import edu.bellevue.cis530.studentai.dto.AiQuestionRequest;
import edu.bellevue.cis530.studentai.dto.AiTextResponse;
import edu.bellevue.cis530.studentai.dto.ContextHelpRequest;
import edu.bellevue.cis530.studentai.dto.RagHelpRequest;
import edu.bellevue.cis530.studentai.dto.StudyPlanRequest;
import edu.bellevue.cis530.studentai.dto.StudyPlanResponse;
import edu.bellevue.cis530.studentai.service.AiService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
public class AiController {
	private final AiService aiAssistantService;

	public AiController(AiService aiAssistantService) {
		this.aiAssistantService = aiAssistantService;
	}

	@PostMapping("/ask")
	public ResponseEntity<AiTextResponse> ask(@Valid @RequestBody AiQuestionRequest request) {
		return ResponseEntity.ok(aiAssistantService.askQuestion(request));
	}

	@PostMapping("/study-plan")
	public ResponseEntity<StudyPlanResponse> studyPlan(@Valid @RequestBody StudyPlanRequest request) {
		return ResponseEntity.ok(aiAssistantService.createStudyPlan(request));
	}

	@PostMapping("/context-help")
	public ResponseEntity<AiTextResponse> contextHelp(@Valid @RequestBody ContextHelpRequest request) {
		return ResponseEntity.ok(aiAssistantService.contextHelp(request));
	}

	@PostMapping("/rag-help")
	public ResponseEntity<AiTextResponse> ragHelp(@Valid @RequestBody RagHelpRequest request) {
		return ResponseEntity.ok(aiAssistantService.ragHelp(request));
	}
}
