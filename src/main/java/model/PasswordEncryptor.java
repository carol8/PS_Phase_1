package model;

import org.javatuples.Pair;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class PasswordEncryptor {
    private final int sareLenght;
    private final String sareAcceptedChars;
    private final SecureRandom random;
    private final SecretKeyFactory factory;

    public PasswordEncryptor(int sareLenght, String sareAcceptedChars, SecureRandom random, SecretKeyFactory factory) {
        this.sareLenght = sareLenght;
        this.sareAcceptedChars = sareAcceptedChars;
        this.random = random;
        this.factory = factory;
    }

    public Pair<String, String> encryptPassword(String password) {
        String salt = random.ints(sareLenght, 0, sareAcceptedChars.length())
                .mapToObj(sareAcceptedChars::charAt)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 128);
        byte[] passwordHash;
        try {
            passwordHash = factory.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        return new Pair<>(salt, new String(passwordHash, StandardCharsets.UTF_8));
    }

    public String encryptPassword(String password, String salt) {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 128);
        byte[] passwordHash;
        try {
            passwordHash = factory.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        return new String(passwordHash, StandardCharsets.UTF_8);
    }
}
