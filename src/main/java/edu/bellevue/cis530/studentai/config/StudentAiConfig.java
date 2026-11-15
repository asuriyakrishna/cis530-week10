package edu.bellevue.cis530.studentai.config;

import edu.bellevue.cis530.studentai.service.DocumentLoaderService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentAiConfig {

	@Bean
	public ChatClient chatClient(ChatClient.Builder builder) {
		return builder
				.defaultSystem("You are a helpful student assistant. Explain concepts clearly and support your answers with relevant context.")
				.build();
	}

	@Bean
	public VectorStore vectorStore(EmbeddingModel embeddingModel) {
		return SimpleVectorStore.builder(embeddingModel).build();
	}

	@Bean
	public ApplicationRunner loadVectorStore(
			VectorStore vectorStore,
			DocumentLoaderService documentLoaderService,
			@Value("${spring.ai.openai.api-key:}") String apiKey) {
		return args -> {
			if (apiKey == null || apiKey.isBlank() || apiKey.contains("${")) {
				System.out.println("Skipping vector-store loading because the OpenAI API key is missing or unresolved.");
				return;
			}
			vectorStore.add(documentLoaderService.loadRagDocuments());
			System.out.println("RAG documents loaded into the vector store.");
		};
	}
}
