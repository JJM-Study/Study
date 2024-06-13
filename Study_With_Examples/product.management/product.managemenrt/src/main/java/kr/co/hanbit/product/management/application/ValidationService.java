package kr.co.hanbit.product.management.application;


import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class ValidationService {
    public <T> void checkVaild(@Valid T validationTarget) {
        // do nothing
    }

}
