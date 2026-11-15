package edu.bellevue.cis530.studentai.service.impl;

import edu.bellevue.cis530.studentai.dto.AiQuestionRequest;
import edu.bellevue.cis530.studentai.dto.AiTextResponse;
import edu.bellevue.cis530.studentai.dto.ContextHelpRequest;
import edu.bellevue.cis530.studentai.dto.RagHelpRequest;
import edu.bellevue.cis530.studentai.dto.StudyPlanRequest;
import edu.bellevue.cis530.studentai.dto.StudyPlanResponse;
import edu.bellevue.cis530.studentai.service.AiService;
import edu.bellevue.cis530.studentai.service.DocumentLoaderService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

@Service
public class AiAssistantService implements AiService {
	private final ChatClient chatClient;
	private final VectorStore vectorStore;
	private final DocumentLoaderService documentLoaderService;

	public AiAssistantService(
			ChatClient chatClient,
			VectorStore vectorStore,
			DocumentLoaderService documentLoaderService) {
		this.chatClient = chatClient;
		this.vectorStore = vectorStore;
		this.documentLoaderService = documentLoaderService;
	}

	@Override
	public AiTextResponse askQuestion(AiQuestionRequest request) {
		String answer = chatClient.prompt()
				.user(request.getQuestion())
				.call()
				.content();
		return new AiTextResponse(answer, "openai");
	}

	@Override
	public StudyPlanResponse createStudyPlan(StudyPlanRequest request) {
		return chatClient.prompt()
				.system("""
						Return a study plan as a Java-friendly structured response.
						Include these fields only: summary, actionItems, importantTerms, encouragement.
						actionItems should contain exactly 3 items.
						importantTerms should contain 4 or 5 short terms.
						""")
				.user(user -> user.text("""
						Student name: {studentName}
						Topic: {topic}
						Problem: {problem}
						Goal: {goal}
						Create a short study plan for this student.
						""")
						.param("studentName", request.getStudentName())
						.param("topic", request.getTopic())
						.param("problem", request.getProblem())
						.param("goal", request.getGoal()))
				.call()
				.entity(StudyPlanResponse.class);
	}

	@Override
	public AiTextResponse contextHelp(ContextHelpRequest request) {
		String notes = documentLoaderService.loadWeek10Notes();
		String answer = chatClient.prompt()
				.system("Answer using the provided course notes first. If the notes are not enough, say that clearly.")
				.user(user -> user.text("""
						Course notes:
						{notes}
						Student question:
						{question}
						""")
						.param("notes", notes)
						.param("question", request.getQuestion()))
				.call()
				.content();
		return new AiTextResponse(answer, "openai");
	}

	@Override
	public AiTextResponse ragHelp(RagHelpRequest request) {
		String answer = chatClient.prompt()
				.advisors(QuestionAnswerAdvisor.builder(vectorStore).build())
				.user(request.getQuestion())
				.call()
				.content();
		return new AiTextResponse(answer, "openai");
	}
}
