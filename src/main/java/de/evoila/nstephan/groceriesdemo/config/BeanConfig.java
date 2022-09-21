package de.evoila.nstephan.groceriesdemo.config;

import de.evoila.nstephan.groceriesdemo.mapping.GroceryItemMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public GroceryItemMapper groceryItemMapper() {
        return Mappers.getMapper(GroceryItemMapper.class);
    }
}
