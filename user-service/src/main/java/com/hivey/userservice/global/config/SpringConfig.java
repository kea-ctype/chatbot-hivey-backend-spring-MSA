package com.hivey.userservice.global.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    /**
     * - 반복되는 객체 간 변환을 간단하게 줄이는 데 사용하는 ModelMapper
     * - 반복적으로 여러 로직에 사용되므로 @Bean으로 등록한다.
     * - 여기서 .setFieldAccessLevel()을 PRIVATE으로 해줘야 Setter를 사용하지 않고도 ModelMapper를 사용할 수 있다.
     */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true);

        return modelMapper;
    }
}
