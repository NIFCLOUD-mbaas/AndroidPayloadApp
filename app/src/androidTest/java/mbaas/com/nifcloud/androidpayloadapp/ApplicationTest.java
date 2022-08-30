package mbaas.com.nifcloud.androidpayloadapp;

import android.content.Context;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.Until;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.*;
import static mbaas.com.nifcloud.androidpayloadapp.Utils.*;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4ClassRunner.class)
public class ApplicationTest {
    private final static int TIMEOUT = 150000;
    private UiDevice device;

    @Rule
    public final ActivityTestRule<MainActivity> main = new ActivityTestRule<>(MainActivity.class, true);

    @Before
    public void init() {
        // Initialize UiDevice instance
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    @Test
    public void useAppContext() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Assert.assertEquals("mbaas.com.nifcloud.androidpayloadapp", appContext.getPackageName());
    }

    @Test
    public void validateDisplayScreen() {
        Utils.allowPermissionsIfNeeded();
        onView(withText("AndroidPayloadPush")).check(matches(isDisplayed()));
        onView(withText("com.nifcloud.mbaas.pushId")).check(matches(isDisplayed()));
        onView(withText("com.nifcloud.mbaas.richUrl")).check(matches(isDisplayed()));
        onView(withText("data (JSON)")).check(matches(isDisplayed()));
        onView(withId(R.id.txtPushid)).check(matches(withText("")));
        onView(withId(R.id.txtRichurl)).check(matches(withText("")));
        onData(allOf(is(instanceOf(String.class)), is(""))).inAdapterView(withId(R.id.lsJson));
    }

    @Test
    public void clickOnSendNotification() throws JSONException {
        Utils.allowPermissionsIfNeeded();
        Utils utils = new Utils();
        utils.sendPushWithSearchCondition();
        device.openNotification();
        device.wait(Until.hasObject(By.text(NOTIFICATION_TITLE)), TIMEOUT);
        UiObject2 title = device.findObject(By.text(NOTIFICATION_TITLE));
        UiObject2 text = device.findObject(By.text(NOTIFICATION_TEXT));
        assertEquals(NOTIFICATION_TITLE, title.getText());
        assertEquals(NOTIFICATION_TEXT, text.getText());
        title.click();
    }
}