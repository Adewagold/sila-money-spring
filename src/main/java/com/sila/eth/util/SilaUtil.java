package com.sila.eth.util;
import java.io.*;
import java.math.BigInteger;
import java.security.*;

import com.oracle.javafx.jmx.json.JSONException;
import com.plaid.client.PlaidClient;
import com.plaid.client.request.ItemPublicTokenExchangeRequest;
import com.plaid.client.response.ItemPublicTokenExchangeResponse;
import org.bouncycastle.util.encoders.HexEncoder;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.web3j.crypto.*;
import org.web3j.crypto.Sign.SignatureData;
import org.web3j.utils.Numeric;
import org.web3j.utils.Strings;
import retrofit2.Response;

/**
 * *  Created by Adewale Adeleye on 2019-09-21
 **/
@Component
public class SilaUtil {
    static final int PRIVATE_KEY_SIZE = 32;
    static final int PUBLIC_KEY_SIZE = 64;

    private static final int ADDRESS_SIZE = 160;
    private static final int ADDRESS_LENGTH_IN_HEX = ADDRESS_SIZE >> 2;
    static final int PUBLIC_KEY_LENGTH_IN_HEX = PUBLIC_KEY_SIZE << 1;
    private static final int PRIVATE_KEY_LENGTH_IN_HEX = PRIVATE_KEY_SIZE << 1;
    private static final Logger LOGGER = LoggerFactory.getLogger(SilaUtil.class);

    private static String privateKey="08740E6DBE4AF8C9A4C95CA9222E43D038D1518364654827E8DA9EDE477170";
    private static String userPrivateKey="1acb8eaaad101ffa923f87f41bb9d0a1092ed8f726c8b5236667675e86f289c1a";
    private String userAddress ="0xe599393449bd537c10ed1c83536aaa80a5979322";

    public static String signMessage(String message, ECKeyPair keyPair) {
        if (message == null) {
            System.out.println("null message was signed");
        }
        if (keyPair == null) {
            System.out.println("null keypair received");
        }

        Sign.SignatureData signatureData = Sign.signMessage(message.getBytes(), keyPair);

        String r = Numeric.toHexString(signatureData.getR());
        String s = Numeric.toHexString(signatureData.getS());
        byte[] temp = new byte[1];
        temp[0] = signatureData.getV();
        String v = Numeric.toHexString(temp);

        r = r.replaceAll("0x", "");
        s = s.replaceAll("0x", "");
        v = v.replaceAll("0x", "");

        /*
         * System.out.println("R: " + r); System.out.println("S: " + s);
         * System.out.println("V: " + v);
         */

        // Numeric.hexStringToByteArray(strInHex);
        return r + s + v;
    }


    /**
     * Recover public key which signed a message. Assume signature is R, S, V byte[]
     * concatenation from SignatureData object
     *
     * @param originalMessage
     * @return
     */
    public static String decodePublicKey(String originalMessage, String signedMessage) {
        // log.info("now decoding signed message..." + signedMessage);
        // log.info("message length = " + signedMessage.length());
        byte[] R = Numeric.hexStringToByteArray(signedMessage.substring(0, 64));
        byte[] S = Numeric.hexStringToByteArray(signedMessage.substring(64, 128));
        byte[] V = Numeric.hexStringToByteArray(signedMessage.substring(128, signedMessage.length()));

        // recreate client-side signatureData
        SignatureData newSignature = new SignatureData(V[0], R, S);

        // log.info("newSignature created okay");
        try {
            BigInteger x = Sign.signedMessageToKey(originalMessage.getBytes(), newSignature);
            String publicKeyNoPrefix = Numeric.cleanHexPrefix(x.toString(16));
            // log.info("publicKeyNoPrefix created okay" + publicKeyNoPrefix);

            if (publicKeyNoPrefix.length() < PUBLIC_KEY_LENGTH_IN_HEX) {
                publicKeyNoPrefix = Strings.zeros(PUBLIC_KEY_LENGTH_IN_HEX - publicKeyNoPrefix.length())
                        + publicKeyNoPrefix;
            }
            String hash = Hash.sha3(publicKeyNoPrefix);
            // log.info("hashed okay" + hash);
            return "0x" + hash.substring(hash.length() - ADDRESS_LENGTH_IN_HEX); // right most 160 bits
        } catch (SignatureException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Generate ethereum public key from private key
     *
     * @param privateKeyInHex
     * @return
     */
    public static String generatePublicKeyInHex(String privateKeyInHex) {

        BigInteger privateKey = new BigInteger(privateKeyInHex, 16);
        ECKeyPair aPair = ECKeyPair.create(privateKey);
        BigInteger publicKey = aPair.getPublicKey();
        String sPublickeyInHex = publicKey.toString(16);

        return sPublickeyInHex;

    }

    /**
     * Generate ethereum public key from private key
     *
     * @param privateKey
     * @return
     */
    public String generatePublicKey(String privateKey) {

        BigInteger privateKey2 = new BigInteger(privateKey, 16);
        ECKeyPair aPair = ECKeyPair.create(privateKey2);
        BigInteger publicKey = aPair.getPublicKey();
        String sPublickey = publicKey.toString();

        return sPublickey;

    }

    public static ECKeyPair getKeyPair(){
        BigInteger privateKey2 = new BigInteger(privateKey, 16);
        ECKeyPair aPair = ECKeyPair.create(privateKey2);
        return aPair;
    }

    public static ECKeyPair getNewKeyPair(){
        try {
            ECKeyPair keyPair = Keys.createEcKeyPair();
            return keyPair;
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getSignature(String message){
        return SilaUtil.signMessage(message,getKeyPair());
    }

    public static String getUserSignature(String message){
        return SilaUtil.signMessage(message, getUserKeyPair(userPrivateKey));

    }

    private static ECKeyPair getUserKeyPair(String userKey){
        BigInteger privateKey2 = new BigInteger(userKey, 16);
        ECKeyPair aPair = ECKeyPair.create(privateKey2);
        return aPair;
    }


    public static String accessToken() throws IOException {
        String accessToken = null ;
        // Use builder to create a client
        PlaidClient plaidClient = PlaidClient.newBuilder()
                .clientIdAndSecret("5d92595a1489d00016334a8c", "f5ac13ff69cb8e1ecdbc3849a93e5f")
                .publicKey("9666a15bf97523ecc9f94713b80adc") // optional. only needed to call endpoints that require a public key
                .sandboxBaseUrl() // or equivalent, depending on which environment you're calling into
                .build();

        // Synchronously exchange a Link public_token for an API access_token
        // Required request parameters are always Request object constructor arguments
        Response<ItemPublicTokenExchangeResponse> response = plaidClient.service()
                .itemPublicTokenExchange(new ItemPublicTokenExchangeRequest("fa9dd19eb40982275785b09760ab79")).execute();

        if (response.isSuccessful()) {
            accessToken = response.body().getAccessToken();
        }
        return accessToken;
    }

    public static void main(String[] args) throws IOException {
//        accessToken();
        ECKeyPair kp = getNewKeyPair();
        System.out.println(kp.getPublicKey());
        System.out.println(kp.getPrivateKey());
        ECKeyPair kp2 = getNewKeyPair();
        System.out.println(kp2.getPublicKey());
        System.out.println(kp2.getPublicKey().toString(16).length());
        System.out.println(kp2.getPrivateKey());
        System.out.println(kp2.getPrivateKey().toString(16).length());
        System.out.println("This is the new generated string "+kp.getPrivateKey().toString(16));
        System.out.println("This is the new generated public string "+generatePublicKeyInHex(kp.getPrivateKey().toString(16)));
        // Web3j
        Credentials credentials = Credentials.create(kp.getPrivateKey().toString(16));
        System.out.println("Generated from CRED: "+credentials.getAddress());
        System.out.println(credentials.getAddress().length());

        Credentials credentials2 = Credentials.create(kp2.getPrivateKey().toString(16));
        System.out.println(credentials2.getAddress());

        JSONObject jsonObject =SilaUtil.processKeyAddress("adewale", kp);
        try {
            String address = jsonObject.get("address").toString();
            System.out.println("Generated from JSON: "+address);
        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * Generate the etherum Address from user;
     * @param seed
     * @return
     */
    private static JSONObject processKeyAddress(String seed, ECKeyPair keyPair){

        JSONObject processJson = new JSONObject();

        try {
            ECKeyPair ecKeyPair = keyPair;
            BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();

            String sPrivatekeyInHex = privateKeyInDec.toString(16);

            WalletFile aWallet = Wallet.createLight(seed, ecKeyPair);
            String sAddress = aWallet.getAddress();


            processJson.put("address", "0x" + sAddress);
            processJson.put("privatekey", sPrivatekeyInHex);


        } catch (CipherException e) {
            //
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }

        return processJson;
    }
}
