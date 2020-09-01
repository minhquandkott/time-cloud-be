package com.ces.intern.apitimecloud;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApitimecloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApitimecloudApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper (){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    @Bean
    public ApplicationContext applicationContext(){
        return new ApplicationContext();
    }

}
