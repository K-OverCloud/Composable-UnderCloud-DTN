package com.netmng.com.validate;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ArrayStrValidator implements ConstraintValidator<ArrayStrValid, String[]> {
	
	//@Autowired
	//@Qualifier("regexp")
	String pattern;
	
	@Override
	public void initialize(ArrayStrValid constraintAnnotation) {
		// TODO Auto-generated method stub
		this.pattern = constraintAnnotation.regexp();
	}

	@Override
	public boolean isValid(String[] value, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		Pattern ARRAY_PATTERN = Pattern.compile(this.pattern);
		if(value != null && value.length != 0) {
			int cnt = value.length;
			boolean result = false;
			for(int i=0; i<cnt; i++) {
				result = ARRAY_PATTERN.matcher(value[i]).matches();
				if(!result) break;
			}
			return result;
		} else {
			return true;
		}
	}
}
