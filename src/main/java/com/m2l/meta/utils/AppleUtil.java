//package com.m2l.meta.utils;
//
//import com.google.api.client.util.PemReader;
//import com.nimbusds.jose.JOSEException;
//import com.nimbusds.jose.JWSAlgorithm;
//import com.nimbusds.jose.JWSHeader;
//import com.nimbusds.jose.JWSSigner;
//import com.nimbusds.jose.crypto.ECDSASigner;
//import com.nimbusds.jwt.JWTClaimsSet;
//import com.nimbusds.jwt.SignedJWT;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.http.HttpEntity;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.util.EntityUtils;
//import org.aspectj.apache.bcel.classfile.annotation.NameValuePair;
//import org.codehaus.jackson.map.ObjectMapper;
//import org.springframework.boot.configurationprocessor.json.JSONObject;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.Resource;
//import org.springframework.stereotype.Component;
//
//import java.io.FileReader;
//import java.io.IOException;
//import java.security.InvalidKeyException;
//import java.security.interfaces.ECPrivateKey;
//import java.text.ParseException;
//import java.util.*;
//
//@Slf4j
//@Component
//public class AppleUtil {
//    private static ObjectMapper objectMapper = new ObjectMapper();
//
//    public String createClientSecret(String teamId, String clientId, String keyId, String keyPath, String authUrl) throws ParseException {
//        Date now = new Date();
//        Map<String,Object> map = new HashMap<>();
//        map.put("issuer",teamId);
//        map.put("issueTime",now);
//        map.put("expirationTime",new Date(now.getTime() + 3600000));
//        map.put("audience",authUrl);
//        map.put("subject",clientId);
//        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.ES256).keyID(keyId).build();
//        JWTClaimsSet claimsSet = JWTClaimsSet.parse(map);
//
//        SignedJWT jwt = new SignedJWT(header, claimsSet);
//
//        try {
//            ECPrivateKey ecPrivateKey = new ECPri(readPrivateKey(keyPath));
//            JWSSigner jwsSigner = new ECDSASigner(ecPrivateKey.getS());
//
//            jwt.sign(jwsSigner);
//
//        } catch (InvalidKeyException | JOSEException e) {
//            e.printStackTrace();
//        }
//
//        return jwt.serialize();
//    }
//
//
//    private byte[] readPrivateKey(String keyPath) {
//
//        Resource resource = new ClassPathResource(keyPath);
//        byte[] content = null;
//
//        try (FileReader keyReader = new FileReader(resource.getFile());
//             PemReader pemReader = new PemReader(keyReader)) {
//            {
//                PemObject pemObject = pemReader.readPemObject();
//                content = pemObject.getContent();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return content;
//    }
//
//    public String doPost(String url, Map<String, String> param) {
//        String result = null;
//        CloseableHttpClient httpclient = null;
//        CloseableHttpResponse response = null;
//        Integer statusCode = null;
//        String reasonPhrase = null;
//        try {
//            httpclient = HttpClients.createDefault();
//            HttpPost httpPost = new HttpPost(url);
//            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
//            List<NameValuePair> nvps = new ArrayList<>();
//            Set<Map.Entry<String, String>> entrySet = param.entrySet();
//            for (Map.Entry<String, String> entry : entrySet) {
//                String fieldName = entry.getKey();
//                String fieldValue = entry.getValue();
//                nvps.add(new BasicNameValuePair(fieldName, fieldValue));
//            }
//            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nvps);
//            httpPost.setEntity(formEntity);
//            response = httpclient.execute(httpPost);
//            statusCode = response.getStatusLine().getStatusCode();
//            reasonPhrase = response.getStatusLine().getReasonPhrase();
//            HttpEntity entity = response.getEntity();
//            result = EntityUtils.toString(entity, "UTF-8");
//
//            if (statusCode != 200) {
//                log.error("[error] : " + result);
//            }
//            EntityUtils.consume(entity);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (response != null) {
//                    response.close();
//                }
//                if (httpclient != null) {
//                    httpclient.close();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return result;
//    }
//
//    public JSONObject decodeFromIdToken(String id_token) {
//
//        try {
//            SignedJWT signedJWT = SignedJWT.parse(id_token);
//            ReadOnlyJWTClaimsSet getPayload = signedJWT.getJWTClaimsSet();
//            ObjectMapper objectMapper = new ObjectMapper();
//            JSONObject payload = objectMapper.readValue(getPayload.toJSONObject().toJSONString(), JSONObject.class);
//            if (payload != null) {
//                return payload;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//}
