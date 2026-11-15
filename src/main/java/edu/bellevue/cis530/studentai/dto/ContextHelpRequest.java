package edu.bellevue.cis530.studentai.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContextHelpRequest {
	@NotBlank(message = "question is required")
	private String question;
}
