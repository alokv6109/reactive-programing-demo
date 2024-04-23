package com.alokcodesback.ReactivePrograming;

import com.alokcodesback.ReactivePrograming.service.FluxLearnService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
public class FluxTesting {

    @Autowired
    private FluxLearnService fluxLearnService;

    @Test
    void testing(){
        fluxLearnService.fluxTestingService();
    }

    @Test
    void simpleFluxTest(){
        fluxLearnService.getFlux().subscribe(data->{
            System.out.println(data);
        });
        System.out.println("----------------------------------");
        fluxLearnService.fruitsFlux().subscribe(data->{
            System.out.println(data);
        });
        System.out.println("------------------------------------");
        fluxLearnService.mapExampleMEthod().subscribe(e->{
            System.out.println(e);
        });
        System.out.println("------------------------------------");
    }

    @Test
    void mapTest(){
        Flux<String> stringFlux = fluxLearnService.mapExampleMEthod();
        StepVerifier.create(stringFlux).expectNextCount(5).verifyComplete();
    }

    @Test
    void filterTest(){
        Flux<String> stringFlux = fluxLearnService.filterExampleFlux();
        StepVerifier.create(stringFlux)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void flatMapTest(){
        Flux<String> stringFlux = fluxLearnService.flatMApExample();
        StepVerifier.create(stringFlux).expectNextCount(28).verifyComplete();
    }

    @Test
    void transformExample(){
        Flux<String> stringFlux = fluxLearnService.transformExample();
        StepVerifier.create(stringFlux).expectNextCount(5).verifyComplete();
    }

    @Test
    void ifExampleTest(){
        Flux<String> stringFlux = fluxLearnService.ifExample(14);
        StepVerifier.create(stringFlux).expectNextCount(3).verifyComplete();
    }

}
