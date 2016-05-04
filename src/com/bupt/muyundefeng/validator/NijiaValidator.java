package com.bupt.muyundefeng.validator;

import java.util.Date;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bupt.muyundefng.model.Nijia;

public class NijiaValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return Nijia.class.isAssignableFrom(arg0);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		Nijia nijia=(Nijia)target;
		ValidationUtils.rejectIfEmpty(errors, "name", "Nijianame.required");
		ValidationUtils.rejectIfEmpty(errors, "birthDate", "NijiabirthDate.required");
		ValidationUtils.rejectIfEmpty(errors, "category", "Nijiacategory.required");
		Date birthDate=nijia.getBirthDate();
		if(birthDate!=null)
		{
			if(birthDate.after(new Date()))
			{
				System.out.println("error birthdate");
				errors.rejectValue("birthdate", "invalidate.birthdate");
			}
		}
	}

	
}
