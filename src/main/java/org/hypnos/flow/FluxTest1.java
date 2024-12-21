package org.hypnos.flow;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Random;
import java.util.function.Consumer;

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
        Random random = new Random();
        Flux<String> letters = Flux
                .just("A", "B", "C", "D", "E")
                // 使用flatMap可以帮助理解其异步的特征
                .concatMap(letter -> {
                    if (letter.equals("F")) {
                        return Mono.error(new IllegalLetterException());
                    } else return Mono.just(letter).delayElement(Duration.ofMillis(random.nextInt(1000)));
                })
                //.delayElements(Duration.ofSeconds(1), Schedulers.boundedElastic());
;
        Consumer<String> consumer = letter -> {
            try {
                Thread.sleep(random.nextInt(500));
                System.out.println(letter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        letters.subscribe(System.out::println);

        Thread.sleep(5 * 1000);
    }



}
