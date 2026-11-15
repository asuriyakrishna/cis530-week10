package edu.bellevue.cis530.studentai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AiTextResponse {
	private String answer;
	private String provider;
}
