package org.hypnos.flow;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author: yuanrui
 */
public class ExceptionTest {

    public static void main(String[] args) throws InterruptedException {

        getInts()
                .flatMap(integer -> {
                    try {
                        return i2s(integer);
                    } catch (Exception e) {
                        return Mono.error(new RuntimeException("发生了大事了！！！！"));
                    }
                })
                .onErrorContinue((e, value) -> {
                    System.out.println("error message: " + e.getMessage());
                })
                .doOnError(e -> System.out.println("axiba" + e.getMessage()))
                .subscribe(ExceptionTest::printEle);

        // Thread.sleep(100000);
        System.out.println("main ending");
    }


    public static Flux<Integer> getInts() {
        List<Integer> integers = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8));

        return Flux.fromIterable(integers);
    }

    public static Mono<String> i2s(Integer i) {
        if (i == 5)
            throw new RuntimeException();
        return Mono.just(String.format("Random:%s, Integer:%s", UUID.randomUUID(), i));
    }

    private static void printEle(String s) {
        System.out.println(s);
    }
}
