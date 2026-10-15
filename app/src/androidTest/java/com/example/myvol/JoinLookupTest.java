package com.example.myvol;

import android.database.sqlite.SQLiteDatabase;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.example.myvol.bean.ActivityBean;
import com.example.myvol.bean.TeamBean;
import com.example.myvol.ui.DatabaseHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

/** Association IDs must not be confused with join rows' auto-increment IDs. */
@RunWith(AndroidJUnit4.class)
public class JoinLookupTest {
    private SQLiteDatabase memory;
    private DatabaseHelper helper;

    @Before
    public void setUp() {
        memory = SQLiteDatabase.create(null);
        helper = new DatabaseHelper(InstrumentationRegistry.getInstrumentation().getTargetContext()) {
            @Override public SQLiteDatabase getWritableDatabase() { return memory; }
            @Override public SQLiteDatabase getReadableDatabase() { return memory; }
        };
        helper.onCreate(memory);
    }

    @After
    public void tearDown() {
        helper.close();
        memory.close();
    }

    @Test
    public void activityLookupUsesActivityIdAndUsername() {
        ActivityBean activity = new ActivityBean();
        activity.setId(42);
        activity.setTitle("test activity");
        assertTrue(helper.insertMyActivity(activity, "alice"));
        assertEquals(1, helper.isjoinActivity("42", "alice").size());
        assertTrue(helper.isjoinActivity("1", "alice").isEmpty());
        assertTrue(helper.isjoinActivity("42", "bob").isEmpty());
        activity.setId(99);
        assertTrue(helper.insertMyActivity(activity, "bob"));
        assertEquals(1, helper.isjoinActivity("99", "bob").size());
        assertTrue(helper.isjoinActivity("99", "alice").isEmpty());
    }

    @Test
    public void teamLookupUsesTeamIdAndUsername() {
        TeamBean team = new TeamBean();
        team.setId(42);
        team.setTitle("test team");
        assertTrue(helper.insertMyTeam(team, "alice"));
        assertEquals(1, helper.isjoinTeam("42", "alice").size());
        assertTrue(helper.isjoinTeam("1", "alice").isEmpty());
        assertTrue(helper.isjoinTeam("42", "bob").isEmpty());
        team.setId(99);
        assertTrue(helper.insertMyTeam(team, "bob"));
        assertEquals(1, helper.isjoinTeam("99", "bob").size());
        assertTrue(helper.isjoinTeam("99", "alice").isEmpty());
    }
}
