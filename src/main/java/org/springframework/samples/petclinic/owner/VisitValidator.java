package org.springframework.samples.petclinic.owner;

import org.springframework.samples.petclinic.visit.Visit;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class VisitValidator  implements Validator {

    private static final String REQUIRED = "required";
    private static final String WEEKDAY = "weekday";
    private static final String BUSINESSHOURS = "businesshours";
    private static final LocalTime bod = LocalTime.of(8,0,0,0);
    private static final LocalTime eod = LocalTime.of(17,0,0,0);

    @Override
    public void validate(Object obj, Errors errors) {
        Visit pet = (Visit) obj;
        LocalDateTime date = pet.getDate();
        // date validation
        if (null == date) {
            errors.rejectValue("date", REQUIRED, REQUIRED);
        }
        if(date.getDayOfWeek()== DayOfWeek.SATURDAY || date.getDayOfWeek()== DayOfWeek.SUNDAY){
            errors.rejectValue("date", WEEKDAY, WEEKDAY);
        }

        if(date.toLocalTime().isBefore(bod) || date.toLocalTime().isAfter(eod) ) {
            errors.rejectValue("date", BUSINESSHOURS, BUSINESSHOURS);
        }
    }

    /**
     * This Validator validates *just* Visit instances
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return Visit.class.isAssignableFrom(clazz);
    }

}
