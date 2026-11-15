package edu.bellevue.cis530.studentai.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.springframework.ai.document.Document;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class DocumentLoaderService {

	public String loadWeek10Notes() {
		return readFile("docs/week10_notes.txt");
	}

	public List<Document> loadRagDocuments() {
		return List.of(
				new Document(readFile("rag/doc1-spring-ai-overview.txt"),
						Map.of("source", "doc1-spring-ai-overview.txt")),
				new Document(readFile("rag/doc2-output-parsers.txt"),
						Map.of("source", "doc2-output-parsers.txt")),
				new Document(readFile("rag/doc3-rag-basics.txt"),
						Map.of("source", "doc3-rag-basics.txt")));
	}

	private String readFile(String path) {
		try {
			ClassPathResource resource = new ClassPathResource(path);
			try (var inputStream = resource.getInputStream()) {
				return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
			}
		} catch (IOException exception) {
			throw new IllegalStateException("Could not read resource file: " + path, exception);
		}
	}
}
