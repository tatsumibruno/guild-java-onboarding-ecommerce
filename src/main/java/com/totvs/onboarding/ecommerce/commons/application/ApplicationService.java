package com.totvs.onboarding.ecommerce.commons.application;

import com.totvs.tjf.core.validation.ValidatorService;

import javax.validation.ConstraintViolationException;

public class ApplicationService {

    private final ValidatorService validatorService;

    public ApplicationService(ValidatorService validatorService) {
        this.validatorService = validatorService;
    }

    protected <T> void validate(T object) {
        validatorService.validate(object)
                .ifPresent(violations -> {
                    throw new ConstraintViolationException(violations);
                });
    }
}
