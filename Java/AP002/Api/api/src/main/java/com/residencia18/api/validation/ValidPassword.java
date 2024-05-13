package com.residencia18.api.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = PasswordConstraintValidator.class)
@Target({ ElementType.METHOD,
        ElementType.FIELD,
        ElementType.ANNOTATION_TYPE,
        ElementType.CONSTRUCTOR,
        ElementType.PARAMETER,
        ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {
    // Mensagem padrão exibida quando a validação falha
    String message() default "Senha invalida";
    
    // Define os grupos de restrições de validação que a anotação pertence
    Class<?>[] groups() default {};
    
    // Define os payloads que podem ser anexados à anotação
    Class<? extends Payload>[] payload() default {};
}
