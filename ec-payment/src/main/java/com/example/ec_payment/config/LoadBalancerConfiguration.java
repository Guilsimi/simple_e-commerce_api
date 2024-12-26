package com.example.ec_payment.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.netflix.discovery.EurekaClient;

import reactor.core.publisher.Flux;

@Configuration
public class LoadBalancerConfiguration {

    @Autowired
    private EurekaClient discoveryClient;

    private List<String> servicesList = new ArrayList<>();

    public static String publicServiceId;

    @Bean
    @Primary
    ServiceInstanceListSupplier serviceInstanceListSupplier() {
        servicesList.addAll(Arrays.asList("ec-oauth", "ec-orders"));
        return new DemoServiceInstanceListSupplier(servicesList, discoveryClient);
    }

    class DemoServiceInstanceListSupplier implements ServiceInstanceListSupplier {
        private final List<String> allServices;
        private final EurekaClient eurekaClient;

        DemoServiceInstanceListSupplier(List<String> allServices, EurekaClient eurekaClient) {
            this.allServices = allServices;
            this.eurekaClient = eurekaClient;
        }

        @Override
        public String getServiceId() {
            String serviceId = FeignClientConfiguration.serviceIdThreadLocal.get();
            return serviceId != null ? serviceId : "default-service";
        }

        @Override
        public Flux<List<ServiceInstance>> get() {
            List<ServiceInstance> serviceInstancesList = new ArrayList<>();

            for (String services : allServices) {
                if (services.equals(getServiceId())) {
                    serviceInstancesList.addAll(
                        eurekaClient
                                .getInstancesByVipAddressAndAppName(services, services, false)
                                .stream()
                                .map(instanceInfo -> new DefaultServiceInstance(
                                        instanceInfo.getInstanceId(),
                                        instanceInfo.getAppName(),
                                        instanceInfo.getHostName(),
                                        instanceInfo.getPort(),
                                        false))
                                .collect(Collectors.toList()));
                }
            }

            return Flux.just(serviceInstancesList);
        }

    }
}
