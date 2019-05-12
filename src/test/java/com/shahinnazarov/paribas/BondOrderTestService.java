package com.shahinnazarov.paribas;

import com.shahinnazarov.paribas.service.BondOrderService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class BondOrderTestService {

    @Bean
    @Primary
    public BondOrderService orderService(){
        return Mockito.mock(BondOrderService.class);
    }
}
