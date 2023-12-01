package com.wallet_service.domain.model;

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

/**
 * Class JWT access token.
 */
@Getter
@Setter
@NoArgsConstructor
public class Jwt {

    /**
     * Encryption algorithm.
     */
    private static final String HEADER_ALG;
    /**
     * Encryption algorithm.
     */
    private static final String ALGORITHM;
    /**
     * Type of token.
     */
    private static final String HEADER_TYP = "JWT";
    /**
     * Secret.
     */
    private static final String SECRET;
    /**
     * Subject (whom the token refers to).
     */
    private String payloadName;
    /**
     * Subject role (whom the token refers to).
     */
    private String payloadRoles;
    /**
     * Header part of JWT.
     */
    private String header;
    /**
     * payload part of JWT.
     */
    private String payload;
    /**
     * Verify signature part of JWT.
     */
    private String verifySignature;
    /**
     * Encoded token.
     */
    private String accessToken;
    /**
     * Web-marker JWT in header of HTTP request.
     */
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
        HEADER_ALG = ALGORITHM.equals("HmacSHA256") ? "HS256" : "RS256";
    }

    /**
     * Method for creating an access token.
     * Information about the algorithm and secret is taken from the configuration file.
     *
     * @param login user login for create а token
     */
    public void createJWT(String login) {
        StringBuilder jwtEncoded = new StringBuilder();
        payloadName = login;
        payloadRoles = login.equals("admin") ? "admin" : "user";
        header = String.format("{\"alg\":\"%s\",\"typ\":\"%s\"}", HEADER_ALG, HEADER_TYP);
        payload = String.format("{\"name\":\"%s\",\"roles\":\"%s\"}", payloadName, payloadRoles);

        String headerBase64 = Base64.encodeBase64String(header.getBytes(StandardCharsets.UTF_8));
        String payloadBase64 = Base64.encodeBase64String(payload.getBytes(StandardCharsets.UTF_8));

        jwtEncoded.append(headerBase64)
                .append(".")
                .append(payloadBase64);

        String hmac = new HmacUtils(ALGORITHM, SECRET).hmacHex(jwtEncoded.toString());
        byte[] decodedHex;
        byte[] encodedHexB64;
        try {
            decodedHex = Hex.decodeHex(hmac);
            encodedHexB64 = Base64.encodeBase64(decodedHex);
            verifySignature = new String(encodedHexB64, StandardCharsets.UTF_8);
        } catch (DecoderException e) {
            throw new RuntimeException(e);
        }
        accessToken = jwtEncoded.append(".").append(verifySignature).toString();
    }
}
