package com.progex.tracker.uttility;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author indunil
 */
@Component
public class DataMapper {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
