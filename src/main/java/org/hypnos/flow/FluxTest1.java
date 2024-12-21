package org.hypnos.flow;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * @author: yuanrui
 */
public class FluxTest1 {


    static class IllegalLetterException extends RuntimeException {

        public IllegalLetterException() {
            super("Can't be F, no F");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Flux<String> letters = Flux
                .just("A", "B", "C", "D", "E", "F")
                .concatMap(letter -> {
                    if (letter.equals("F")) {
                        return Mono.error(new IllegalLetterException());
                    } else return Mono.just(letter);
                })
//                .delayElements(Duration.ofSeconds(1));
;
        letters.subscribe(System.out::println);

        //Thread.sleep(5 * 1000);
    }
}
