package com.corbandalas.reaper.springreaper;

import com.corbandalas.reaper.springreaper.dto.TxResponse;
import lombok.SneakyThrows;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@SpringBootApplication
public class SpringReaperApplication {

    public static Integer count = 0;

    public static void main(String[] args) {
        SpringApplication.run(SpringReaperApplication.class, args);

//        for (int i = 0; i < 100; ++i) {
//            var thread = new MyThread();
//
//            thread.start();
//        }


//        List<String> list = new ArrayList<>(List.of("A", "B", "C"));
//
//        list.stream().forEach( x-> {
//            if (x.equals("C")) {
//                list.remove(x);
//            }
//        });


//        test(Set.of("A", "B", "C"));


    }


//    public static void test(Set<?> set) {
//        set.forEach(System.out::println);
//    }


    @Bean
    @Profile("!test")
    public CommandLineRunner CommandLineRunnerBean() {
        return (args) -> {

//            RestTemplate restTemplate = new RestTemplate();
//
//            List<CompletableFuture<TxResponse>> futures = new ArrayList<>();
//
//            for (int i = 0; i < 10; ++i) {
//                CompletableFuture<TxResponse> future = CompletableFuture.supplyAsync(() -> invokeWithdraw(restTemplate, 100L, "USD", 1L));
//
//                futures.add(future);
//            }
//
//            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        };



    }

    @SneakyThrows
    public TxResponse invokeDeposit(RestTemplate restTemplate, Long amount, String currency, Long accountTo) {

        try {
            var url = "http://localhost:8080/transaction/deposit";


            var headers  = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            var transactionJsonObject = new JSONObject();
            transactionJsonObject.put("amount", 100);
            transactionJsonObject.put("currency", "USD");
            transactionJsonObject.put("accountTo", "1");

            HttpEntity<String> request =
                    new HttpEntity<>(transactionJsonObject.toString(), headers);

            TxResponse transactionDTO =
                    restTemplate.postForObject(url, request, TxResponse.class);



            System.out.println("transactionDTO: " + transactionDTO);

            return transactionDTO;
        } catch (Exception e) {
            e.printStackTrace();
        }

       return null;

    }

    @SneakyThrows
    public TxResponse invokeWithdraw(RestTemplate restTemplate, Long amount, String currency, Long accountTo) {

       try {
           var url = "http://localhost:8080/transaction/withdraw";


           var headers  = new HttpHeaders();
           headers.setContentType(MediaType.APPLICATION_JSON);
           var transactionJsonObject = new JSONObject();
           transactionJsonObject.put("amount", 100);
           transactionJsonObject.put("currency", "USD");
           transactionJsonObject.put("accountFrom", "1");

           HttpEntity<String> request =
                   new HttpEntity<>(transactionJsonObject.toString(), headers);

           TxResponse transactionDTO =
                   restTemplate.postForObject(url, request, TxResponse.class);



           System.out.println("transactionDTO: " + transactionDTO);

           return transactionDTO;
       } catch (Exception e) {
           e.printStackTrace();

       }

       return null;


    }

}
