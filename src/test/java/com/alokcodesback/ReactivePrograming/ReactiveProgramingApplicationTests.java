package com.alokcodesback.ReactivePrograming;

import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuple3;

import java.time.Duration;
import java.util.Objects;
import java.util.function.Consumer;

@SpringBootTest
class ReactiveProgramingApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void workingWithMono(){
		System.out.println("testing");
	}

	@Test
	public void workingWithMono2() throws InterruptedException {
		//mon-->publisher->can return 0 or 1 elements
		Mono<String> m1 =   Mono.just("alok codes back")
				.log();
		//subscribing is basiclly done by the subscriber on the publishr
		//so its basically telling the publisher that someone wants to conaume the publisher
//			varius methods of doing this

		m1.subscribe(new Consumer<String>() {
			@Override
			public void accept(String s) {
				System.out.println("data is " + s);
			}
		});
		//or it can be done using the lambda
//		second method od subscribing to the publisher
		m1.subscribe(data->{
			System.out.println("the data is " + data);
		});

		Mono<Long> m2 = Mono.just(321l).log();
		m2.subscribe(data->{
			System.out.println("the data is " + data);
		});

		//error mono is published
		Mono<Object> errorMono = Mono.error(new RuntimeException("error !!")).log();
		errorMono.subscribe(error->{
			System.out.println(error);
		});

		//now if you would want to loopd various monos togetther
//		so then you would want to use then() chaining method
//		lets do this woth the errorMono

		Mono<String> m3 = Mono.just("dasddasd").then(m1).log();
		m3.subscribe(data->{
			System.out.println("the data is " + data);
		});

//		withZip() and the ther methos zip()
		Mono<String> m4 = Mono.just("kala kala").log();

		Mono<Tuple2<String, Long>> combinedMono =  Mono.zip(m1, m2);
		combinedMono.subscribe(data->{
			System.out.println(data.getT1()); //gives the array of data
		});

		Mono<Tuple3<String, Long, String>> zip = Mono.zip(m1, m2, m4);
		zip.subscribe(data->{
			System.out.println(data);
		});

		Mono<Tuple2<String, Long>> tuple2Mono = m1.zipWith(m2);
		tuple2Mono.subscribe(data->{
			System.out.println(data);
		});
//map will return a mono only - usinf tge synch function
		Mono<String> resultMono = m1.map(item -> item.toUpperCase());
		resultMono.subscribe(System.out::println);

//		flatMAp() transform the value emotted by cureent mono async, returning the value emittednby another mono
		Mono<String[]> mono = m1.flatMap(valueM1 -> Mono.just(valueM1.split(" ")));
		mono.subscribe(data->{
			for(String s: data){
				System.out.println(s);
			}
//			System.out.println(data);
		});
		System.out.println("-----------------------------------------------");

		Flux<String> stringFlux = m1.flatMapMany(val -> Flux.just(val.split(" "))).log();
		stringFlux.subscribe(data->{
			System.out.println(data);
		});
		System.out.println("------------------------------------------------------------------");
		System.out.println(Thread.currentThread().getName());
		Flux<String> stringFlux1 = m1.concatWith(m4).
				delayElements(Duration.ofMillis(2000)).
				log();

		stringFlux1.subscribe((data)->{
			System.out.println(Thread.currentThread().getName());
			System.out.println(data);
		});
		Thread.sleep(4000);
		System.out.println("terminated the main thread");
	}
}
