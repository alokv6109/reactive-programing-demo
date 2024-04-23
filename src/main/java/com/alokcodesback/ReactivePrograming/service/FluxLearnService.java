package com.alokcodesback.ReactivePrograming.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

@Service
public class FluxLearnService {

    public void fluxTestingService(){
        System.out.println("flux learning....");
    }

    public Flux<String> getFlux(){
        Flux<String> namedFlux = Flux.just("alok", "aniket", "amit", "ankit", "fdffsdfsf").log();
        return namedFlux;
    }

    public Flux<String> fruitsFlux(){
        List<String> fruitNAme= List.of("mango", "apple", "banana");
        return Flux.fromIterable(fruitNAme).log();
//        Flux<String> namedFlux = Flux.just("alok", "aniket", "amit", "ankit", "fdffsdfsf").log();
//        return namedFlux;
    }

    public Flux<Void> getBlankFlux(){
        return Flux.empty();
    }

    public Flux<String> mapExampleMEthod(){
        Flux<String> capsFlux = getFlux().map(name -> name.toUpperCase());
        return capsFlux.log();
    }

    public Flux<String> filterExampleFlux(){
        return getFlux().filter(name->
            name.length()>4).log();
    }

    public Flux<String> flatMApExample(){
        return getFlux().flatMap(name->
            Flux.just(name.split(""))
        ).delayElements(Duration.ofSeconds(2)).log();
        //with delay you'll get to know that this is anych job as the parallel executing threads are doing th ejob
        /*
        2023-12-10T12:01:45.578+05:30  INFO 10392 --- [           main] reactor.Flux.Array.1                     : | onNext(amit)
2023-12-10T12:01:45.578+05:30  INFO 10392 --- [           main] reactor.Flux.Array.1                     : | onNext(ankit)
2023-12-10T12:01:45.578+05:30  INFO 10392 --- [           main] reactor.Flux.Array.1                     : | onNext(fdffsdfsf)
2023-12-10T12:01:45.578+05:30  INFO 10392 --- [           main] reactor.Flux.Array.1                     : | onComplete()
2023-12-10T12:01:47.585+05:30  INFO 10392 --- [     parallel-1] reactor.Flux.ConcatMapNoPrefetch.2       : onNext(a)
2023-12-10T12:01:49.586+05:30  INFO 10392 --- [     parallel-2] reactor.Flux.ConcatMapNoPrefetch.2       : onNext(l)
2023-12-10T12:01:51.588+05:30  INFO 10392 --- [     parallel-3] reactor.Flux.ConcatMapNoPrefetch.2       : onNext(o)
2023-12-10T12:01:53.596+05:30  INFO 10392 --- [     parallel-4] reactor.Flux.ConcatMapNoPrefetch.2       : onNext(k)
2023-12-10T12:01:55.599+05:30  INFO 10392 --- [     parallel-5] reactor.Flux.ConcatMapNoPrefetch.2       : onNext(a)
2023-12-10T12:01:57.609+05:30  INFO
         Here main is the main thead and the other threads are the paralllel threads
                 */
    }

    //using the fucntional Interface
    public Flux<String>  transformExample(){
        Function<Flux<String>, Flux<String>> functionInterface = (name)->name.map(t->t.toUpperCase());
        return getFlux().transform(functionInterface).log();
    }

    //defaultIfEmpty
//    switchifWmpty
    public Flux<String> ifExample(int length){
//        return  getFlux().filter(name-> name.length()>length).defaultIfEmpty("lkalaalla").log();

        return  getFlux().filter(name-> name.length()>length).switchIfEmpty(fruitsFlux()).log();
    }

    /*

    for joining 2 fluxes concat - Static mathod returns FLux - sync way Flux.concat(f1, f2)- even of delay sync manner only
    log() methos is very necessary
    concatWith -sync way

    for async way - merge and mergeWith, merge is static and mergeWith is non static , istance method ie for the object of lfux
    for checking async properly , delay is omething you can use - ordr is not preserved due to aync thing
    mergeSequential thing is also one method
    zip and zip with - can be done with Flux<Tuple2<String,Integer>>  or anything else is the return type  - sama joining hi hai
    bacsically, [aniket, 1], [alok, 2], [amit, 3] basically ye ek tuple karke milte ahi
    ye joining kind of a thing hai

     */


}
