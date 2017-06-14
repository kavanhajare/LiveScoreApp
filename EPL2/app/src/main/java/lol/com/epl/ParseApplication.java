package lol.com.epl;

/**
 * Created by arjun on 7/11/15.
 */
import com.parse.Parse;
        import com.parse.ParseACL;

        import com.parse.ParseUser;

        import android.app.Application;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Add your initialization code here
        Parse.initialize(this, "SMKrDrEdBfyYTPTqcWWQapapT6wnGtaZjEvgCvtt", "QG6AkfDmVyPp8JEVE5a14fcxrIhwc7Ld81Brglt5");
        ParseUser.enableRevocableSessionInBackground();
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();

        // If you would like all objects to be private by default, remove this
        // line.
        defaultACL.setPublicReadAccess(true);

        ParseACL.setDefaultACL(defaultACL, true);
    }

}
