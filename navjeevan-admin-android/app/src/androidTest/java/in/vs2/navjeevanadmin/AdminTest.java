package in.vs2.navjeevanadmin;


import android.app.Activity;
import android.content.Context;
import android.support.test.annotation.UiThreadTest;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;


import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AdminTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);
    @UiThreadTest
    public void adminTest() {

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.edit_code), isDisplayed()));
        appCompatEditText.perform(click());
        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.edit_code), isDisplayed()));
        appCompatEditText2.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.edit_code), isDisplayed()));
        appCompatEditText3.perform(click());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.edit_code), isDisplayed()));
        appCompatEditText4.perform(click());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.edit_code), isDisplayed()));
        appCompatEditText5.perform(click());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.edit_code), isDisplayed()));
        appCompatEditText6.perform(click());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.edit_code), isDisplayed()));
        appCompatEditText7.perform(replaceText("123456"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withText("Login"), isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction imageButton = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        withParent(allOf(withId(R.id.action_bar),
                                withParent(withId(R.id.action_bar_container)))),
                        isDisplayed()));
        imageButton.perform(click());

        ViewInteraction navigationMenuItemView = onView(
                allOf(withText("All Pending Orders"), isDisplayed()));
        navigationMenuItemView.perform(click());

        ViewInteraction imageButton2 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        withParent(allOf(withId(R.id.action_bar),
                                withParent(withId(R.id.action_bar_container)))),
                        isDisplayed()));
        imageButton2.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.list),
                        withParent(allOf(withId(R.id.main_content),
                                withParent(withId(R.id.container)))),
                        isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.spin_reason),
                        withParent(withId(R.id.layout_reason)),
                        isDisplayed()));
        appCompatSpinner.perform(click());

        ViewInteraction imageButton3 = onView(
                allOf(withContentDescription("Navigate up"),
                        withParent(allOf(withId(R.id.action_bar),
                                withParent(withId(R.id.action_bar_container)))),
                        isDisplayed()));
        imageButton3.perform(click());

        ViewInteraction navigationMenuItemView2 = onView(
                allOf(withText("Offers"), isDisplayed()));
        navigationMenuItemView2.perform(click());

        ViewInteraction coordinatorLayout = onView(
                allOf(withId(R.id.main_content),
                        childAtPosition(
                                allOf(withId(R.id.list),
                                        withParent(withId(R.id.main_content))),
                                0),
                        isDisplayed()));
        coordinatorLayout.perform(click());

        ViewInteraction coordinatorLayout2 = onView(
                allOf(withId(R.id.main_content),
                        childAtPosition(
                                allOf(withId(R.id.list),
                                        withParent(withId(R.id.main_content))),
                                0),
                        isDisplayed()));
        coordinatorLayout2.perform(click());

        ViewInteraction coordinatorLayout3 = onView(
                allOf(withId(R.id.main_content),
                        childAtPosition(
                                allOf(withId(R.id.list),
                                        withParent(withId(R.id.main_content))),
                                0),
                        isDisplayed()));
        coordinatorLayout3.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(android.R.id.button1), withText("Update"),
                        withParent(allOf(withId(R.id.buttonPanel),
                                withParent(withId(R.id.parentPanel)))),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.edit_min_amount), withText("1000")));
        appCompatEditText8.perform(scrollTo(), replaceText("1005"), closeSoftKeyboard());

        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.edit_min_amount), withText("1005")));
        appCompatEditText9.perform(pressImeActionButton());

        ViewInteraction appCompatEditText10 = onView(
                allOf(withId(R.id.edit_discount_rate), withText("10"), isDisplayed()));
        appCompatEditText10.perform(pressImeActionButton());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.button_save), withText("Update")));
        appCompatButton3.perform(scrollTo(), click());

        ViewInteraction coordinatorLayout4 = onView(
                allOf(withId(R.id.main_content),
                        childAtPosition(
                                allOf(withId(R.id.list),
                                        withParent(withId(R.id.main_content))),
                                0),
                        isDisplayed()));
        coordinatorLayout4.perform(click());

        ViewInteraction imageButton4 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        withParent(allOf(withId(R.id.action_bar),
                                withParent(withId(R.id.action_bar_container)))),
                        isDisplayed()));
        imageButton4.perform(click());

        ViewInteraction navigationMenuItemView3 = onView(
                allOf(withText("Menu"), isDisplayed()));
        navigationMenuItemView3.perform(click());

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.list),
                        withParent(allOf(withId(R.id.main_content),
                                withParent(withId(R.id.container)))),
                        isDisplayed()));
        recyclerView2.perform(actionOnItemAtPosition(1, click()));

        ViewInteraction imageButton5 = onView(
                allOf(withContentDescription("Navigate up"),
                        withParent(allOf(withId(R.id.action_bar),
                                withParent(withId(R.id.action_bar_container)))),
                        isDisplayed()));
        imageButton5.perform(click());

        ViewInteraction imageButton6 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        withParent(allOf(withId(R.id.action_bar),
                                withParent(withId(R.id.action_bar_container)))),
                        isDisplayed()));
        imageButton6.perform(click());

        ViewInteraction navigationMenuItemView4 = onView(
                allOf(withText("Daily Report"), isDisplayed()));
        navigationMenuItemView4.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.button_date), withText("28 / 4 / 2017"), isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        withParent(allOf(withClassName(is("com.android.internal.widget.ButtonBarLayout")),
                                withParent(withClassName(is("android.widget.LinearLayout"))))),
                        isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.button_show), withText("Show"), isDisplayed()));
        appCompatButton6.perform(click());

        ViewInteraction imageButton7 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        withParent(allOf(withId(R.id.action_bar),
                                withParent(withId(R.id.action_bar_container)))),
                        isDisplayed()));
        imageButton7.perform(click());

        ViewInteraction navigationMenuItemView5 = onView(
                allOf(withText("Setting"), isDisplayed()));
        navigationMenuItemView5.perform(click());

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

}
