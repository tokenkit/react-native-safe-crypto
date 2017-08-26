
package co.airbitz.fastcrypto;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.lambdaworks.crypto.SCrypt;
import android.util.Base64;


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

  @ReactMethod
  public void scrypt(String passwd,
                     String salt,
                     Integer N,
                     Integer r,
                     Integer p,
                     Integer size,
                     Promise promise) {
     try {
       byte[] rawPassword = Base64.decode(passwd, Base64.DEFAULT);
       byte[] rawSalt     = Base64.decode(salt, Base64.DEFAULT);
       byte[] result = SCrypt.scrypt(rawPassword, rawSalt, N, r, p, size);

       String resultStr = new String(Base64.encode(result, Base64.NO_WRAP));
       promise.resolve(resultStr);
     } catch(Exception e) {
       promise.reject(e);
     }
  }
}
