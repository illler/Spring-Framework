package org.RestAPI.Client;

import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Client {
    static RestTemplate restTemplate = new RestTemplate();
    static String URL = "http://localhost:8080/";

    public static void main(String[] args) {

        Random random = new Random();
        String name = "Sensor" + random.nextInt()*100;
        registrateSensor(name);
        System.out.println("Сенсор зарегистрирован");
        for (int i = 0; i < 1000; i++) {
            send1000measurements(random.nextDouble()*50, random.nextBoolean(), name);
        }
        get1000measurements();

    }

    public static void registrateSensor(String name){
        Map<String, Object> send = new HashMap<>();
        send.put("name", name);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(send);
        restTemplate.postForObject(URL + "sensor/registration", entity, String.class);
    }

    public static void get1000measurements(){
        System.out.println(restTemplate.getForObject(URL + "measurements", String.class));
    }

    public static void send1000measurements(Double value, boolean raining, String name){
        Map<String, Object> send = new HashMap<>();
        send.put("value", value);
        send.put("raining", raining);
        send.put("sensor", Map.of("name",name));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(send);
        restTemplate.postForObject(URL + "measurements/add", request, String.class);

    }
}
