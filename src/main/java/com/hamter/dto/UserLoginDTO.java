package com.hamter.dto;

import org.antlr.v4.runtime.misc.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {
	@JsonProperty("email")
	@NotBlank(message = "Email is not required")
	private String email;
	
	@NotBlank(message = "Password cannot be blank")
	private String password;
}
