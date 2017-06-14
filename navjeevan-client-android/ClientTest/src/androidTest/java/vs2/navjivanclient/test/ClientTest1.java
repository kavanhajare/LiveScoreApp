package vs2.navjivanclient.test;

import com.robotium.solo.*;
import android.test.ActivityInstrumentationTestCase2;


@SuppressWarnings("rawtypes")
public class ClientTest1 extends ActivityInstrumentationTestCase2 {
  	private Solo solo;
  	
  	private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME = "vs2.navjivanclient.SplashScreenActivity";

    private static Class<?> launcherActivityClass;
    static{
        try {
            launcherActivityClass = Class.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME);
        } catch (ClassNotFoundException e) {
           throw new RuntimeException(e);
        }
    }
  	
  	@SuppressWarnings("unchecked")
    public ClientTest1() throws ClassNotFoundException {
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
        //Wait for activity: 'vs2.navjivanclient.SplashScreenActivity'
		solo.waitForActivity("SplashScreenActivity", 2000);
        //Set default small timeout to 10435 milliseconds
		Timeout.setSmallTimeout(10435);
        //Click on Jain Food
		solo.clickInRecyclerView(0, 0);
        //Wait for activity: 'vs2.navjivanclient.FoodItemActivity'
		assertTrue("FoodItemActivity is not found!", solo.waitForActivity("FoodItemActivity"));
        //Set default small timeout to 16060 milliseconds
		Timeout.setSmallTimeout(16060);
        //Click on ImageView
		solo.clickOnView(solo.getView("button_add"));
        //Click on ImageView
		solo.clickOnView(solo.getView(android.widget.ImageButton.class, 0));
        //Click on action_cart
		solo.clickOnView(solo.getView("action_cart"));
        //Wait for activity: 'vs2.navjivanclient.CartActivity'
		assertTrue("CartActivity is not found!", solo.waitForActivity("CartActivity"));
        //Click on ImageView
		solo.clickOnView(solo.getView("button_add"));
        //Click on Spl. Sangai Roll(jain)   160.00 X 2   320.00 2
		solo.clickInList(1, 0);
        //Click on CHANGE
		solo.clickOnView(solo.getView("button_change_address"));
        //Click on Pick from existing addresses
		solo.clickOnView(solo.getView(android.R.id.text1, 1));
        //Wait for activity: 'vs2.navjivanclient.MyAddressActivity'
		assertTrue("MyAddressActivity is not found!", solo.waitForActivity("MyAddressActivity"));
        //Click on 809, Trump Tower, Upsana school, Vapi Gunjan
		solo.clickInList(2, 0);
        //Click on CHANGE
		solo.clickOnView(solo.getView("button_change_address"));
        //Click on New Address
		solo.clickOnView(solo.getView(android.R.id.text1));
        //Wait for activity: 'vs2.navjivanclient.AddAddress'
		assertTrue("AddAddress is not found!", solo.waitForActivity("AddAddress"));
        //Click on ImageView
		solo.clickOnView(solo.getView(android.widget.ImageButton.class, 0));
        //Click on ImageView
		solo.clickOnView(solo.getView(android.widget.ImageButton.class, 0));
        //Click on ImageView
		solo.clickOnView(solo.getView(android.widget.ImageButton.class, 0));
        //Click on My Orders
		solo.clickOnText(java.util.regex.Pattern.quote("My Orders"));
        //Click on   ACCEPTED Home Delivery Chilly Paratha Dry(2), Veg. Potly (8pcs)(2) Today
		solo.clickInRecyclerView(0, 0);
        //Wait for activity: 'vs2.navjivanclient.OrderDetailActivity'
		assertTrue("OrderDetailActivity is not found!", solo.waitForActivity("OrderDetailActivity"));
        //Click on ImageView
		solo.clickOnView(solo.getView(android.widget.ImageButton.class, 0));
        //Click on ImageView
		solo.clickOnView(solo.getView(android.widget.ImageButton.class, 0));
        //Click on Offers
		solo.clickOnText(java.util.regex.Pattern.quote("Offers"));
        //Wait for activity: 'vs2.navjivanclient.OfferListActivity'
		assertTrue("OfferListActivity is not found!", solo.waitForActivity("OfferListActivity"));
        //Click on Summer Bonanza Item Free This summer get chilled! Get 2 cold-drinks of 60 m
		solo.clickInList(1, 0);
        //Wait for activity: 'vs2.navjivanclient.OfferDetailActivity'
		assertTrue("OfferDetailActivity is not found!", solo.waitForActivity("OfferDetailActivity"));
        //Click on ImageView
		solo.clickOnView(solo.getView(android.widget.ImageButton.class, 0));
        //Click on ImageView
		solo.clickOnView(solo.getView(android.widget.ImageButton.class, 0));
        //Click on ImageView
		solo.clickOnView(solo.getView(android.widget.ImageButton.class, 0));
        //Click on Profile
		solo.clickOnText(java.util.regex.Pattern.quote("Profile"));
        //Click on ImageView
		solo.clickOnView(solo.getView(android.widget.ImageButton.class, 0));
	}
}
