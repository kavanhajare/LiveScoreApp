package in.vs2.navjeevanadmin.test;

import com.robotium.solo.*;
import android.test.ActivityInstrumentationTestCase2;


@SuppressWarnings("rawtypes")
public class AdminTestRobotium extends ActivityInstrumentationTestCase2 {
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
    public AdminTestRobotium() throws ClassNotFoundException {
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
        //Wait for activity: 'in.vs2.navjeevanadmin.LoginActivity'
		solo.waitForActivity("LoginActivity", 2000);
        //Set default small timeout to 55495 milliseconds
		Timeout.setSmallTimeout(55495);
        //Click on ImageView
		solo.clickOnView(solo.getView(android.widget.ImageButton.class, 0));
        //Click on All Pending Orders
		solo.clickOnText(java.util.regex.Pattern.quote("All Pending Orders"));
        //Take screenshot
        solo.takeScreenshot();
        //Take screenshot
        solo.takeScreenshot();
        //Set default small timeout to 166045 milliseconds
		Timeout.setSmallTimeout(166045);
        //Click on ImageView
		solo.clickOnView(solo.getView(android.widget.ImageButton.class, 3));
        //Click on Offers
		solo.clickOnText(java.util.regex.Pattern.quote("Offers"));
        //Click on ImageView
		solo.clickOnView(solo.getView("fab_add"));
        //Click on Empty Text View
		solo.clickOnView(solo.getView("edit_title"));
        //Enter the text: 'Garam Tadka'
		solo.clearEditText((android.widget.EditText) solo.getView("edit_title"));
		solo.enterText((android.widget.EditText) solo.getView("edit_title"), "Garam Tadka");
        //Click on Empty Text View
		solo.clickOnView(solo.getView("edit_description"));
        //Enter the text: 'Get 150 off on any bill amount of more than 1200'
		solo.clearEditText((android.widget.EditText) solo.getView("edit_description"));
		solo.enterText((android.widget.EditText) solo.getView("edit_description"), "Get 150 off on any bill amount of more than 1200");
        //Click on Start date
		solo.clickOnView(solo.getView("button_from_date"));
        //Click on OK
		solo.clickOnView(solo.getView(android.R.id.button1));
        //Click on End date
		solo.clickOnView(solo.getView("button_to_date"));
        //Scroll View to the right side
		solo.scrollViewToSide(solo.getView("day_picker_view_pager"), Solo.RIGHT);
        //Click on OK
		solo.clickOnView(solo.getView(android.R.id.button1));
        //Click on Select Offer Type
		solo.clickOnView(solo.getView("spinner_offer_type"));
        //Click on Take Away Discount
		solo.clickOnView(solo.getView(android.R.id.text1, 3));
        //Click on Get 150 off on any bill amount of more than 1200
		solo.clickOnView(solo.getView("edit_description"));
        //Enter the text: 'Get 150 off on any bill amount of more than 1200.'
		solo.clearEditText((android.widget.EditText) solo.getView("edit_description"));
		solo.enterText((android.widget.EditText) solo.getView("edit_description"), "Get 150 off on any bill amount of more than 1200.");
        //Click on Empty Text View
		solo.clickOnView(solo.getView("edit_discount_rate"));
        //Click on Empty Text View
		solo.clickOnView(solo.getView("edit_min_amount"));
        //Enter the text: '1200'
		solo.clearEditText((android.widget.EditText) solo.getView("edit_min_amount"));
		solo.enterText((android.widget.EditText) solo.getView("edit_min_amount"), "1200");
        //Click on Empty Text View
		solo.clickOnView(solo.getView("edit_discount_rate"));
        //Take screenshot
        solo.takeScreenshot();
        //Enter the text: '10'
		solo.clearEditText((android.widget.EditText) solo.getView("edit_discount_rate"));
		solo.enterText((android.widget.EditText) solo.getView("edit_discount_rate"), "10");
        //Click on 10
		solo.clickOnView(solo.getView("edit_discount_rate"));
        //Enter the text: '12'
		solo.clearEditText((android.widget.EditText) solo.getView("edit_discount_rate"));
		solo.enterText((android.widget.EditText) solo.getView("edit_discount_rate"), "12");
        //Click on Save
		solo.clickOnView(solo.getView("button_save"));
        //Scroll to ImageView
		android.widget.ListView listView0 = (android.widget.ListView) solo.getView(android.widget.ListView.class, 0);
		solo.scrollListToLine(listView0, 0);
        //Click on ImageView
		solo.clickOnView(solo.getView(android.widget.ImageButton.class, 0));
        //Click on Menu
		solo.clickOnText(java.util.regex.Pattern.quote("Menu"));
        //Click on ImageView
		solo.clickOnView(solo.getView("fab_add"));
        //Click on Empty Text View
		solo.clickOnView(solo.getView("edit_title"));
        //Click on Cancel
		solo.clickOnView(solo.getView("button_cancel"));
        //Click on ImageView
		solo.clickOnView(solo.getView("fab_add"));
        //Click on Empty Text View
		solo.clickOnView(solo.getView("edit_title"));
        //Enter the text: 'Thai'
		solo.clearEditText((android.widget.EditText) solo.getView("edit_title"));
		solo.enterText((android.widget.EditText) solo.getView("edit_title"), "Thai");
        //Click on Empty Text View
		solo.clickOnView(solo.getView("edit_description"));
        //Enter the text: 'Special Thai food all the way from thailand.'
		solo.clearEditText((android.widget.EditText) solo.getView("edit_description"));
		solo.enterText((android.widget.EditText) solo.getView("edit_description"), "Special Thai food all the way from thailand.");
        //Click on Save
		solo.clickOnView(solo.getView("button_save"));
        //Click on ImageView
		solo.clickOnView(solo.getView("fab_add"));
        //Click on Empty Text View
		solo.clickOnView(solo.getView("edit_title"));
        //Enter the text: 'Soup'
		solo.clearEditText((android.widget.EditText) solo.getView("edit_title"));
		solo.enterText((android.widget.EditText) solo.getView("edit_title"), "Soup");
        //Click on Empty Text View
		solo.clickOnView(solo.getView("edit_description"));
        //Enter the text: 'sous'
		solo.clearEditText((android.widget.EditText) solo.getView("edit_description"));
		solo.enterText((android.widget.EditText) solo.getView("edit_description"), "sous");
        //Click on Soup
		solo.clickOnView(solo.getView("edit_title"));
        //Enter the text: 'Soups'
		solo.clearEditText((android.widget.EditText) solo.getView("edit_title"));
		solo.enterText((android.widget.EditText) solo.getView("edit_title"), "Soups");
        //Click on sous
		solo.clickOnView(solo.getView("edit_description"));
        //Enter the text: 'soups '
		solo.clearEditText((android.widget.EditText) solo.getView("edit_description"));
		solo.enterText((android.widget.EditText) solo.getView("edit_description"), "soups");
        //Click on Save
		solo.clickOnView(solo.getView("button_save"));
        //Click on testing
		solo.clickInRecyclerView(0, 0);
        //Wait for activity: 'in.vs2.navjeevanadmin.FoodItemActivity'
		assertTrue("FoodItemActivity is not found!", solo.waitForActivity("FoodItemActivity"));
        //Press menu back key
		solo.goBack();
        //Click on Jain Food
		solo.clickInRecyclerView(1, 0);
        //Wait for activity: 'in.vs2.navjeevanadmin.FoodItemActivity'
		assertTrue("FoodItemActivity is not found!", solo.waitForActivity("FoodItemActivity"));
        //Click on ImageView
		solo.clickOnView(solo.getView("fab_add", 1));
        //Click on Empty Text View
		solo.clickOnView(solo.getView("edit_title"));
        //Enter the text: 'Jain samosa'
		solo.clearEditText((android.widget.EditText) solo.getView("edit_title"));
		solo.enterText((android.widget.EditText) solo.getView("edit_title"), "Jain samosa");
        //Click on Empty Text View
		solo.clickOnView(solo.getView("edit_price"));
        //Enter the text: '50'
		solo.clearEditText((android.widget.EditText) solo.getView("edit_price"));
		solo.enterText((android.widget.EditText) solo.getView("edit_price"), "50");
        //Click on Empty Text View
		solo.clickOnView(solo.getView("edit_description"));
        //Enter the text: 'Tasty jain samosa'
		solo.clearEditText((android.widget.EditText) solo.getView("edit_description"));
		solo.enterText((android.widget.EditText) solo.getView("edit_description"), "Tasty jain samosa");
        //Take screenshot
        solo.takeScreenshot();
        //Take screenshot
        solo.takeScreenshot();
        //Click on Save
		solo.clickOnView(solo.getView("button_save"));
        //Take screenshot
        solo.takeScreenshot();
        //Click on ImageView
		solo.clickOnView(solo.getView(android.widget.ImageButton.class, 0));
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
        //Take screenshot
        solo.takeScreenshot();
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
        //Click on ImageView
		solo.clickOnView(solo.getView(android.widget.ImageButton.class, 0));
	}
}
