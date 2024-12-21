package org.hypnos.flow;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.web.reactive.function.client.WebClient;


/**
 * @author: yuanrui
 */
public class FlatMapHttpDemo {

    public static void main(String[] args) throws InterruptedException {
        // 创建 WebClient
        WebClient webClient = WebClient.create("https://jsonplaceholder.typicode.com");

        // 模拟请求的多个 ID
        Flux<Integer> ids = Flux.just(1, 2, 3, 4, 5);

        // 使用 flatMap 并发发送 HTTP 请求
        Flux<String> responses = ids
                .flatMap(id -> sendRequest(webClient, id)) // 并发发送请求
                .doOnNext(response -> System.out.println("Received response: " + response)) // 打印响应
                .doOnError(error -> System.err.println("Error occurred: " + error.getMessage()));

        // 订阅并触发请求
        responses.subscribe();

        // 主线程等待，确保请求完成（仅用于演示，不建议用于生产）
        Thread.sleep(5000);
    }

    private static Mono<String> sendRequest(WebClient webClient, int id) {
        return webClient
                .get()
                .uri("/posts/{id}", id) // 假设请求 URL 为 https://jsonplaceholder.typicode.com/posts/{id}
                .retrieve()
                .bodyToMono(String.class) // 将响应体解析为字符串
                .doOnSubscribe(subscription -> System.out.println("Sending request for ID: " + id)) // 发送前打印日志
                .doOnSuccess(response -> System.out.println("Request for ID " + id + " completed"));
    }
}
