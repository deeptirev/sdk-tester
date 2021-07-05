//package com.deepti.sdktest.controller;
//
//import com.deepti.sdktest.model.DeviceCheckDetailsObject;
//import com.deepti.sdktest.model.PuppyObject;
//import com.deepti.sdktest.service.ServiceValidationService;
//import org.apache.http.conn.ssl.NoopHostnameVerifier;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.web.server.LocalServerPort;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.client.SimpleClientHttpRequestFactory;
//
//import javax.net.ssl.HttpsURLConnection;
//import java.io.IOException;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class ServiceValidationControllerMvcTest {
//
//    @LocalServerPort
//    private int port;
//
////    @Autowired
////    private TestRestTemplate restTemplate;
//
//    @Test
//    public void testController() throws Exception{
//
////        restTemplate.getRestTemplate().setRequestFactory(new SimpleClientHttpRequestFactory() {
////            @Override
////            protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
////                if (connection instanceof HttpsURLConnection) {
////                    ((HttpsURLConnection) connection).setHostnameVerifier(new NoopHostnameVerifier());
////                }
////                super.prepareConnection(connection, httpMethod);
////            }
////        });
//        TestRestTemplate testRestTemplate = new TestRestTemplate(TestRestTemplate.HttpClientOption.SSL);
//
//        List<DeviceCheckDetailsObject> deviceCheckDetailsObjects = new ArrayList<>();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//
//        HttpEntity<List<DeviceCheckDetailsObject>> entity = new HttpEntity<>(deviceCheckDetailsObjects, headers);
//
//        ResponseEntity<String> response = testRestTemplate.postForEntity(
//                new URL("https://localhost:" + port + "/").toString(), entity, String.class);
//        assertEquals("Hello Controller", response.getBody());
//    }
//}
