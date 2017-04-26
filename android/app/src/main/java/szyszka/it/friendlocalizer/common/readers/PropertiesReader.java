package szyszka.it.friendlocalizer.common.readers;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Squier on 26.04.2017.
 */

public class PropertiesReader {

    public static final String TAG = PropertiesReader.class.getSimpleName();

    private Context context;
    private Properties myProperties;

    public PropertiesReader(Context context, Properties myProperties) {
        this.context = context;
        this.myProperties = myProperties;
    }

    public Properties readMyProperties(String fileName) {
        AssetManager assetManager = context.getAssets();
        InputStream inputStream;
        try {
            inputStream = assetManager.open(fileName);
            myProperties.load(inputStream);
            Log.i(TAG, "Successfully loaded \"" + fileName + "\".");
        } catch (IOException e) {
            Log.e(TAG, "Failed to load \"" + fileName + "\"!");
        }
        return myProperties;
    }

}
