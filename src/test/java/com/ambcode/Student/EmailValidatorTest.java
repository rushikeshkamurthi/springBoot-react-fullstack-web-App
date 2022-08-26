package com.ambcode.Student;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class EmailValidatorTest {

    private final EmailValidator underTest = new EmailValidator();
    @Test
    void isShouldValidateCorrectEmail() {
        assertThat(underTest.test("h.ello@gmail.com")).isTrue();
    }

    @Test
    void isShouldValidateAnIncorrectEmail() {
        assertThat(underTest.test("hellogmail.com")).isFalse();
    }

    @Test
    void isShouldValidateAnIncorrectEmailWithoutDotAtEnd() {
        assertThat(underTest.test("hellogmail")).isFalse();
    }

}