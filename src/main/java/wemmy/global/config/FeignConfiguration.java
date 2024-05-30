package wemmy.global.config;

import feign.Logger;
import feign.codec.ErrorDecoder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import wemmy.WemmyApplication;
import wemmy.global.config.error.FeingClientExceptionErrorDecoder;

@Configuration
@EnableFeignClients (basePackageClasses = WemmyApplication.class)
@Import(FeignClientsConfiguration.class)
public class FeignConfiguration {

    @Bean
    Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
        // NONE, BASIC, HEADERS
    }

    @Bean
    public ErrorDecoder errorDecoder(){
        return new FeingClientExceptionErrorDecoder();
    }
}
