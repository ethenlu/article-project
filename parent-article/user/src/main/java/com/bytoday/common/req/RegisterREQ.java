package com.bytoday.common.req;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterREQ {
	private String name;
	private String password;
	private String checkPass;
	private String birthday;
	private boolean delivery;
	private String email;
	private String verifyCode;
}
