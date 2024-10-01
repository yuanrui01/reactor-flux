//package org.hypnos.flow;
//
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.Flow;
//import java.util.concurrent.SubmissionPublisher;
//
///**
// * @author: yuanrui
// */
//public class PubSubTest {
//
//    public static void main(String[] args) throws InterruptedException {
//        // 创建一个 CountDownLatch，以便主线程可以等待数据处理完成
//        CountDownLatch latch = new CountDownLatch(1);
//
//        // 创建一个发布者
//        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
//
//        // 创建一个订阅者
//        Flow.Subscriber<String> subscriber = new Flow.Subscriber<>() {
//            @Override
//            public void onSubscribe(Flow.Subscription subscription) {
//                subscription.request(Long.MAX_VALUE); // 请求所有数据
//            }
//
//            @Override
//            public void onNext(String item) {
//                System.out.println("Received: " + item);
//            }
//
//            @Override
//            public void onError(Throwable throwable) {
//                throwable.printStackTrace();
//            }
//
//            @Override
//            public void onComplete() {
//                System.out.println("Completed");
//                // 完成后减小计数器，允许主线程结束
//                latch.countDown();
//            }
//        };
//
//        // 将订阅者注册到发布者
//        publisher.subscribe(subscriber);
//
//        // 发布数据
//        publisher.submit("Hello");
//        publisher.submit("World");
//
//        // 关闭发布者
//        publisher.close();
//
//        // 等待直到所有数据都被处理完成
//        latch.await();
//    }
//}
