package com.sila.eth;

import com.sila.eth.util.BaseUtil;
import com.sila.eth.util.SilaUtil;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@SpringBootTest
public class SilaUtilTests {

    @Test
    public void testEpochGenerator(){
        Long epoch = generateEpoch()-100;
        String epochStringValue  = String.valueOf(epoch);
        Assert.assertEquals(epochStringValue, BaseUtil.getEpoch());
    }

    @Test
    public void whenEpochValueHasExpired(){
        String epochStringValue  = String.valueOf(generateEpoch());
        Assert.assertNotEquals(epochStringValue, BaseUtil.getEpoch());
    }

    private Long generateEpoch(){
        ZoneId zoneId = ZoneId.of("GMT+1");
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime,zoneId);
        Long epoch = zonedDateTime.toInstant().getEpochSecond();
        return epoch;
    }

    @Test
    public void generateECKeyPair(){
        ECKeyPair kp = generateECKP();
        BigInteger publicKey = kp.getPublicKey();
        BigInteger privateKey = kp.getPrivateKey();
        Assert.assertNotNull(kp);
        Assert.assertNotNull(publicKey);
        Assert.assertNotNull(privateKey);
        Assert.assertEquals(128,publicKey.toString(16).length());
        Assert.assertEquals(64,privateKey.toString(16).length());
        Credentials credentials = Credentials.create(privateKey.toString(16));
        Assert.assertNotNull(credentials);
        Assert.assertEquals(40,credentials.getAddress().length());
    }

    @Test
    public void testSignatureGeneration(){
        ECKeyPair kp = SilaUtil.getNewKeyPair();
        String signature = SilaUtil.generatePublicKeyInHex(kp.getPrivateKey().toString(16));
        Assert.assertNotNull(signature);
    }


    private ECKeyPair generateECKP(){
        return SilaUtil.getNewKeyPair();
    }
}
