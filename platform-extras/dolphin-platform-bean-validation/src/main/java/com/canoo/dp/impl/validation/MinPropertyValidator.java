package com.canoo.dp.impl.validation;

import org.apiguardian.api.API;

import javax.validation.ValidationException;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.math.BigInteger;

import static org.apiguardian.api.API.Status.INTERNAL;

/**
 * Validator that adds Dolphin Platform property support for the {@link Min} annotation.
 */
@API(since = "0.19.0", status = INTERNAL)
public final class MinPropertyValidator extends AbstractNumberValidator<Min> {

    private long minValue;

    @Override
    public void initialize(final Min minValue) {
        this.minValue = minValue.value();
    }

    @Override
    protected boolean checkValidLong(final Long value) {
        return value >= minValue;
    }

    @Override
    protected boolean checkValidCharSequence(final CharSequence value) {
        throw new ValidationException("Max constraint does not support CharSequence validation: " + value);
    }

    @Override
    protected boolean checkValidBigInteger(final BigInteger value) {
        return value.compareTo(BigInteger.valueOf(minValue) ) != -1;
    }

    @Override
    protected boolean checkValidBigDecimal(final BigDecimal value) {
        return value.compareTo(BigDecimal.valueOf(minValue) ) != -1;
    }
}
