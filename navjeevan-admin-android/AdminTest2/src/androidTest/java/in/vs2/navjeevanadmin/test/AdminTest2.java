package in.vs2.navjeevanadmin.test;

import com.robotium.solo.*;
import android.test.ActivityInstrumentationTestCase2;


@SuppressWarnings("rawtypes")
public class AdminTest2 extends ActivityInstrumentationTestCase2 {
  	private Solo solo;
  	
  	private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME = "in.vs2.navjeevanadmin.LoginActivity";

    private static Class<?> launcherActivityClass;
    static{
        try {
            launcherActivityClass = Class.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME);
        } catch (ClassNotFoundException e) {
           throw new RuntimeException(e);
        }
    }
  	
  	@SuppressWarnings("unchecked")
    public AdminTest2() throws ClassNotFoundException {
        super(launcherActivityClass);
    }

  	public void setUp() throws Exception {
        super.setUp();
		solo = new Solo(getInstrumentation());
		getActivity();
  	}
  
   	@Override
   	public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
  	}
  
	public void testRun() {
        //Take screenshot
        solo.takeScreenshot();
        //Take screenshot
        solo.takeScreenshot();
        //Take screenshot
        solo.takeScreenshot();
        //Take screenshot
        solo.takeScreenshot();
        //Take screenshot
        solo.takeScreenshot();
        //Take screenshot
        solo.takeScreenshot();
        //Take screenshot
        solo.takeScreenshot();
        //Take screenshot
        solo.takeScreenshot();
        //Take screenshot
        solo.takeScreenshot();
        //Take screenshot
        solo.takeScreenshot();
        //Take screenshot
        solo.takeScreenshot();
        //Take screenshot
        solo.takeScreenshot();
        //Take screenshot
        solo.takeScreenshot();
        //Take screenshot
        solo.takeScreenshot();
        //Take screenshot
        solo.takeScreenshot();
        //Take screenshot
        solo.takeScreenshot();
        //Take screenshot
        solo.takeScreenshot();
        //Take screenshot
        solo.takeScreenshot();
        //Take screenshot
        solo.takeScreenshot();
        //Take screenshot
        solo.takeScreenshot();
        //Take screenshot
        solo.takeScreenshot();
        //Take screenshot
        solo.takeScreenshot();
        //Take screenshot
        solo.takeScreenshot();
        //Take screenshot
        solo.takeScreenshot();
        //Take screenshot
        solo.takeScreenshot();
        //Take screenshot
        solo.takeScreenshot();
        //Wait for activity: 'in.vs2.navjeevanadmin.LoginActivity'
		solo.waitForActivity("LoginActivity", 2000);
        //Set default small timeout to 16490 milliseconds
		Timeout.setSmallTimeout(16490);
        //Click on PENDING Brad delson 8160629836 04/28/2017 03:02:27   600.00 Order #165 Home
		solo.clickInRecyclerView(0, 0);
        //Wait for activity: 'in.vs2.navjeevanadmin.OrderDetailActivity'
		assertTrue("OrderDetailActivity is not found!", solo.waitForActivity("OrderDetailActivity"));
        //Scroll to CANCEL
		android.widget.ListView listView0 = (android.widget.ListView) solo.getView(android.widget.ListView.class, 0);
		solo.scrollListToLine(listView0, 1);
        //Click on CANCEL
		solo.clickOnView(solo.getView("button_cancel_order"));
        //Click on ImageView
		solo.clickOnView(solo.getView(android.widget.ImageButton.class, 2));
        //Click on All Pending Orders
		solo.clickOnText(java.util.regex.Pattern.quote("All Pending Orders"));
        //Click on LinearLayout mitesh 8866344895 10/10/2015 04:10:13   320.00 Order #142 Home
		solo.clickInRecyclerView(1, 0);
        //Wait for activity: 'in.vs2.navjeevanadmin.OrderDetailActivity'
		assertTrue("OrderDetailActivity is not found!", solo.waitForActivity("OrderDetailActivity"));
        //Click on ImageView
		solo.clickOnView(solo.getView(android.widget.ImageButton.class, 1));
        //Click on ImageView
		solo.clickOnView(solo.getView(android.widget.ImageButton.class, 3));
        //Click on Offers
		solo.clickOnText(java.util.regex.Pattern.quote("Offers"));
        //Scroll to Garam Tadka (Item Free) * Get 150 off on any bill amount of more than 1200.
		android.widget.ListView listView1 = (android.widget.ListView) solo.getView(android.widget.ListView.class, 0);
		solo.scrollListToLine(listView1, 0);
        //Click on Garam Tadka (Item Free) * Get 150 off on any bill amount of more than 1200.
		solo.clickInList(2, 0);
        //Click on Delete
		solo.clickOnView(solo.getView(android.R.id.button2));
        //Click on Yes
		solo.clickOnView(solo.getView(android.R.id.button1));
        //Click on ImageView
		solo.clickOnView(solo.getView(android.widget.ImageButton.class, 0));
        //Click on Menu
		solo.clickOnText(java.util.regex.Pattern.quote("Menu"));
        //Click on ImageView
		solo.clickOnView(solo.getView("image_edit"));
        //Click on Noodles
		solo.clickOnView(solo.getView("edit_title"));
        //Enter the text: 'Noodl'
		solo.clearEditText((android.widget.EditText) solo.getView("edit_title"));
		solo.enterText((android.widget.EditText) solo.getView("edit_title"), "Noodl");
        //Click on Noodl
		solo.clickOnView(solo.getView("edit_title"));
        //Enter the text: 'Japanese Noodl'
		solo.clearEditText((android.widget.EditText) solo.getView("edit_title"));
		solo.enterText((android.widget.EditText) solo.getView("edit_title"), "Japanese Noodl");
        //Click on Japanese Noodl
		solo.clickOnView(solo.getView("edit_title"));
        //Enter the text: 'Japanese Noodl es'
		solo.clearEditText((android.widget.EditText) solo.getView("edit_title"));
		solo.enterText((android.widget.EditText) solo.getView("edit_title"), "Japanese Noodl es");
        //Click on 21
		solo.clickOnView(solo.getView("edit_description"));
        //Enter the text: '21'
		solo.clearEditText((android.widget.EditText) solo.getView("edit_description"));
		solo.enterText((android.widget.EditText) solo.getView("edit_description"), "21");
        //Click on Update
		solo.clickOnView(solo.getView("button_save"));
        //Click on ImageView
		solo.clickOnView(solo.getView(android.widget.ImageButton.class, 0));
        //Click on Daily Report
		solo.clickOnText(java.util.regex.Pattern.quote("Daily Report"));
        //Click on 28 / 4 / 2017
		solo.clickOnView(solo.getView("button_date"));
        //Click on OK
		solo.clickOnView(solo.getView(android.R.id.button1));
        //Click on Show
		solo.clickOnView(solo.getView("button_show"));
        //Click on ImageView
		solo.clickOnView(solo.getView(android.widget.ImageButton.class, 0));
        //Click on Monthly Report
		solo.clickOnText(java.util.regex.Pattern.quote("Monthly Report"));
        //Click on ImageView
		solo.clickOnView(solo.getView(android.widget.ImageButton.class, 0));
        //Click on Setting
		solo.clickOnText(java.util.regex.Pattern.quote("Setting"));
        //Wait for activity: 'in.vs2.navjeevanadmin.SettingsActivity'
		assertTrue("SettingsActivity is not found!", solo.waitForActivity("SettingsActivity"));
        //Click on ImageView
		solo.clickOnView(solo.getView(android.widget.ImageButton.class, 0));
	}
}
