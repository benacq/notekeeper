package com.acq.notekeeper;

import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.NavigationViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;
import org.junit.Rule;
import org.junit.Test;
import java.util.List;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.*;

public class NextThroughNotesTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void NextThroughNotes() {
        //Open the side navigation drawer
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        //Navigate to the specified page
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_notes));
        // Get the first item in the RecyclerView and click on it
        onView(withId(R.id.list_items)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        //Get the actual notes from DataManager for comparison
        List<NoteInfo> notes = DataManager.getInstance().getNotes();

        //Loop through all the data and check if they match the actual data in DataManager
        for (int index = 0; index < notes.size(); index++) {
        NoteInfo note = notes.get(index);
        onView(withId(R.id.spinner_courses)).check(ViewAssertions.matches(withSpinnerText(note.getCourse().getTitle())));
        onView(withId(R.id.text_note_title)).check(ViewAssertions.matches(withText(note.getTitle())));
        onView(withId(R.id.text_note_text)).check(ViewAssertions.matches(withText(note.getText())));

        //Click on the next button to check the next note
        if (index < notes.size() - 1)
            onView(allOf(withId(R.id.action_next), isEnabled())).perform(click());
    }

        //Check if next button gets disabled after the last note is checked
        onView(withId(R.id.action_next)).check(ViewAssertions.matches(not(isEnabled())));
        //Go back
        pressBack();
    }

}