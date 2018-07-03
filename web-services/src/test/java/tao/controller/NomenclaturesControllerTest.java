package tao.controller;

import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class NomenclaturesControllerTest {
    @Test
    public void should_return_optional_empty_when_input_is_null() {
        final Optional result = Optional.ofNullable(null);
        assertThat(result).isEqualTo(Optional.empty());
    }
}