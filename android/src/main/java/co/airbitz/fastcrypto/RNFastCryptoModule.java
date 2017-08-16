
package co.airbitz.fastcrypto;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.lambdaworks.crypto.SCrypt;


public class RNFastCryptoModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  public RNFastCryptoModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNFastCrypto";
  }

  private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
  public static String bytesToHex(byte[] bytes) {
    char[] hexChars = new char[bytes.length * 2];
    for ( int j = 0; j < bytes.length; j++ ) {
      int v = bytes[j] & 0xFF;
      hexChars[j * 2] = hexArray[v >>> 4];
      hexChars[j * 2 + 1] = hexArray[v & 0x0F];
    }
    return new String(hexChars);
  }

  @ReactMethod
  public void scrypt(String passwd,
                     String salt,
                     Integer N,
                     Integer r,
                     Integer p,
                     Integer size,
                     Promise promise) {
    try {
      byte[] result = SCrypt.scrypt(passwd.getBytes(), salt.getBytes(), 1024, 8, 1, 32);
      promise.resolve(bytesToHex(result));
    } catch(Exception e) {
      promise.reject(e);
    }
  }
}
