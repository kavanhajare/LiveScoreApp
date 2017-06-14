package lol.com.epl;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SignUp extends ActionBarActivity {
    Button signup;
    String usernametxt;
    String passwordtxt;
    String emailtxt;
    EditText password;
    EditText username;
    EditText email;
    Spinner sp;
    Button b;
    List<String> team;
    String store;
    EditText fav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        sp = (Spinner)findViewById(R.id.spinner2);
        team=new ArrayList<String>();














        final HashMap<String,String> hm=new HashMap<String,String>();

        fav=(EditText)findViewById(R.id.fav);

        team.add("Manchester United FC");
        team.add("Tottenham Hotspur FC");
        team.add("AFC Bournemouth");
        team.add("Aston Villa FC");
        team.add("Everton FC");
        team.add("Watford FC");
        team.add("Leicester City FC");
        team.add("Sunderland AFC");
        team.add("Norwich City FC");
        team.add("Crystal Palace FC");
        team.add("Chelsea FC");
        team.add("Swansea City FC");
        team.add("Newcastle United FC");
        team.add("Southampton FC");
        team.add("Arsenal FC");
        team.add("West Ham United FC");
        team.add("Stoke City FC");
        team.add("Liverpool FC");
        team.add("West Bromwich Albion FC");
        team.add("Manchester City FC");

        team.add("RC Deportivo La Coruna");
        team.add("Real Sociedad de Fútbol");
        team.add("RCD Espanyol");
        team.add("Getafe CF");
        team.add("Club Atlético de Madrid");
        team.add("UD Las Palmas");
        team.add("Rayo Vallecano de Madrid");
        team.add("Valencia CF");
        //  nowShowing.add("RCD Espanyol");
        team.add("Málaga CF");
        team.add("Sevilla FC");
        team.add("Athletic Club");
        team.add("FC Barcelona");
        team.add("Sporting Gijón");
        team.add("Real Madrid CF");
        team.add("Levante UD");
        team.add("RC Celta de Vigo");
        team.add("Real Betis");
        team.add("Villarreal CF");
        team.add("Granada CF");
        team.add("SD Eibar");


        team.add("FC Bayern München");
        team.add("Hamburger SV");
        team.add("FC Augsburg");
        team.add("Hertha BSC");
        team.add("Bayer Leverkusen");
        team.add("TSG 1899 Hoffenheim");
        team.add("SV Darmstadt 98");
        team.add("Hannover 96");
        team.add("1. FSV Mainz 05");
        team.add("FC Ingolstadt 04");
        team.add("Werder Bremen");
        team.add("FC Schalke 04");
        team.add("Borussia Dortmund");
        team.add("HBor. Mönchengladbach");
        team.add("VfL Wolfsburg");
        team.add("Eintracht Frankfurt");
        team.add("VfB Stuttgart");
        team.add("1. FC Köln");


        hm.put("Manchester United FC", "http://api.football-data.org/alpha/teams/66/fixtures");
        hm.put("Tottenham Hotspur FC","http://api.football-data.org/alpha/teams/73/fixtures");
        hm.put("AFC Bournemouth","http://api.football-data.org/alpha/teams/1044/fixtures");
        hm.put("Aston Villa FC","http://api.football-data.org/alpha/teams/58/fixtures");
       hm.put("Everton FC","http://api.football-data.org/alpha/teams/62/fixtures");
        hm.put("Watford FC","http://api.football-data.org/alpha/teams/346/fixtures");
        hm.put("Leicester City FC","http://api.football-data.org/alpha/teams/338/fixtures");

        ArrayAdapter<String> aa=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,team);
        sp.setAdapter(aa);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos,
                                       long id) {
              store =sp.getItemAtPosition(pos).toString();
              fav.setText(store);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });







        ParseUser currentUser = ParseUser.getCurrentUser();
        currentUser.logOut();
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.email);


        b=(Button)findViewById(R.id.signup);
        b.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // Retrieve the text entered from the EditText
                usernametxt = username.getText().toString();
                passwordtxt = password.getText().toString();

                // Force user to fill up the form
                if (usernametxt.equals("") && passwordtxt.equals("")) {
                    Toast.makeText(getApplicationContext(),
                            "Please complete the sign up form",
                            Toast.LENGTH_LONG).show();

                } else {
                    // Save new user data into Parse.com Data Storage
                    ParseUser user = new ParseUser();
                    user.setUsername(usernametxt);
                    user.setPassword(passwordtxt);
                    user.put("team",fav.getText().toString());
                    user.signUpInBackground(new SignUpCallback() {
                        public void done(ParseException e) {
                            if (e == null) {
                                // Show a simple Toast message upon successful registration
                                Toast.makeText(getApplicationContext(),
                                        "Successfully Signed up, please log in.",
                                        Toast.LENGTH_LONG).show();
                                String val=(String)hm.get(fav.getText().toString());
                                Alert a = new Alert(fav.getText().toString(),val);
                                a.show(getFragmentManager(), "error");
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        e.toString(), Toast.LENGTH_LONG)
                                        .show();
                            }
                        }
                    });
                }

            }
        });
        // Force user to fill up the form


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
