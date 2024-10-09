package com.example.ec_order.config;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

import reactor.core.publisher.Flux;

@Configuration
public class ProductConfigurations {

    private final EurekaClient discoveryClient;

    public ProductConfigurations(EurekaClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @Bean
    @Primary
    ServiceInstanceListSupplier serviceInstanceListSupplier() {
        return new DemoServiceInstanceListSupplier("ec-products", discoveryClient);
    }

    class DemoServiceInstanceListSupplier implements ServiceInstanceListSupplier {
        private final String serviceId;
        private final EurekaClient eurekaClient;

        DemoServiceInstanceListSupplier(String serviceId, EurekaClient eurekaClient) {
            this.serviceId = serviceId;
            this.eurekaClient = eurekaClient;
        }

        @Override
        public String getServiceId() {
            return serviceId;
        }

        public EurekaClient getEurekaClient() {
            return eurekaClient;
        }

        @Override
        public Flux<List<ServiceInstance>> get() {
            return Flux.defer(() -> {
                List<InstanceInfo> instanceInfos = eurekaClient.getInstancesByVipAddressAndAppName("ec-products", serviceId, false);

                List<ServiceInstance> serviceInstances = instanceInfos.stream()
                        .map(instanceInfo -> new DefaultServiceInstance(
                                instanceInfo.getInstanceId(),
                                instanceInfo.getAppName(),
                                instanceInfo.getHostName(),
                                instanceInfo.getPort(),
                                false))
                        .collect(Collectors.toList());

                return Flux.just(serviceInstances);
            });
        }

    }
}