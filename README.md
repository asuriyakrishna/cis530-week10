# Student AI Assistant

A Spring Boot REST API demonstrating AI question answering, structured study-plan generation, context help from course notes, and retrieval-augmented generation (RAG) with local documents.

## Requirements

- JDK 17
- IntelliJ IDEA or Maven
- An OpenAI API key for AI requests

The project targets Java 17 in `pom.xml`.

## Run in IntelliJ IDEA

1. Open the project folder containing `pom.xml`.
2. Set the Project SDK, Maven importer JDK, and Maven runner JRE to JDK 17.
3. If IntelliJ cannot use the Maven wrapper, choose **Bundled (Maven 3)** for Maven home, then reload the Maven project.
4. Open **Run → Edit Configurations** and create or select an Application configuration:
   - Main class: `edu.bellevue.cis530.studentai.StudentAiAssistantApplication`
   - Working directory: the project root
   - Environment variable: `OPENAI_API_KEY=<your-key>`
5. Run `StudentAiAssistantApplication`. The server listens on port 8080.

Do not commit your API key or include it in shared files.

## Run from PowerShell

Set the key in the same PowerShell session used to launch the app:

```powershell
$env:OPENAI_API_KEY = "your-key"
.\mvnw.cmd spring-boot:run
```

Run the tests with:

```powershell
.\mvnw.cmd test
```

## API Endpoints

All endpoints accept and return JSON. Set the request header to `Content-Type: application/json`.

| Method | Path | Request body |
| --- | --- | --- |
| POST | `/api/ai/ask` | `{"question":"Explain Spring AI prompts."}` |
| POST | `/api/ai/context-help` | `{"question":"What is prompt stuffing?"}` |
| POST | `/api/ai/rag-help` | `{"question":"What is RAG?"}` |
| POST | `/api/ai/study-plan` | `{"studentName":"Alex","topic":"Spring AI","problem":"I find RAG confusing","goal":"Understand the RAG flow"}` |

The text endpoints return an `answer` and `provider`. The study-plan endpoint returns `summary`, `actionItems`, `importantTerms`, and `encouragement`.

## RAG Documents

At startup, the application loads these classpath resources into an in-memory vector store:

- `src/main/resources/rag/doc1-spring-ai-overview.txt`
- `src/main/resources/rag/doc2-output-parsers.txt`
- `src/main/resources/rag/doc3-rag-basics.txt`

The context-help endpoint uses `src/main/resources/docs/week10_notes.txt`. If `OPENAI_API_KEY` is missing, the application skips loading the RAG documents; AI endpoints require a valid key.
