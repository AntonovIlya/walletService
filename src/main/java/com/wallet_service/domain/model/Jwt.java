package com.wallet_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.HmacUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Jwt {

    //todo добавить документацию
    private static final String SECRET;
    private static final String ALGORITHM;
    private String accessToken;
    private String tokenType = "Bearer";

    static {
        File file = new File("C:\\Users\\antonov_ii\\Desktop\\walletService\\.env");

        Properties properties = new Properties();
        try {
            properties.load(new FileReader(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SECRET = properties.getProperty("SECRET");
        ALGORITHM = properties.getProperty("ALGORITHM");
    }

    public void createJWT(String login) {
        StringBuilder jwt = new StringBuilder();
        String header = String.format("{\"alg\":\"%s\",\"typ\":\"JWT\"}", ALGORITHM.equals("HmacSHA256") ? "HS256" : "RS256");
        String payload = String.format("{\"name\":\"%s\",\"roles\":\"%s\"}", login, login.equals("admin") ? "admin" : "user");

        String headerBase64 = Base64.encodeBase64String(header.getBytes(StandardCharsets.UTF_8));
        String payloadBase64 = Base64.encodeBase64String(payload.getBytes(StandardCharsets.UTF_8));

        jwt.append(headerBase64)
                .append(".")
                .append(payloadBase64);

        String hmac = new HmacUtils(ALGORITHM, SECRET).hmacHex(jwt.toString());
        byte[] decodedHex;
        byte[] encodedHexB64;
        String verifySignature;
        try {
            decodedHex = Hex.decodeHex(hmac);
            encodedHexB64 = Base64.encodeBase64(decodedHex);
            verifySignature = new String(encodedHexB64, StandardCharsets.UTF_8);
        } catch (DecoderException e) {
            throw new RuntimeException(e);
        }
        accessToken = jwt.append(".").append(verifySignature).toString();
    }
}
