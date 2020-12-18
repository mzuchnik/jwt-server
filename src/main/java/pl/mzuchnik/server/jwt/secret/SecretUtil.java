package pl.mzuchnik.server.jwt.secret;

import org.apache.tomcat.util.codec.binary.Base64;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class SecretUtil {

    public static RSAPublicKey getPublicKey(File file) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {

        String key = new String(Files.readAllBytes(file.toPath()), Charset.defaultCharset());

        String publicKeyPEM = key
                .replace("-----BEGIN PUBLIC KEY-----","")
                .replaceAll(System.lineSeparator(),"")
                .replace("-----END PUBLIC KEY-----","");

        byte[] publicKeyBytes = Base64.decodeBase64(publicKeyPEM);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }

    public static RSAPrivateKey getPrivateKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        File file = new File("src/main/resources/keys/private_key.pem");

        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));

        byte[] bytes = bis.readAllBytes();

        X509EncodedKeySpec x509 = new X509EncodedKeySpec(bytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = kf.generatePrivate(x509);
        return (RSAPrivateKey) privateKey;
    }


}
