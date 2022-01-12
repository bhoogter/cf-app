package com.wincdspro.app.test

import android.view.View
import android.widget.EditText
import android.widget.TextView
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

class CustomMatchers {

    companion object {
        @JvmStatic
        fun hasValueEqualTo(content: String): Matcher<View?>? {
            return object : TypeSafeMatcher<View?>() {
                override fun describeTo(description: Description) {
                    description.appendText("Has EditText/TextView the value:  $content")
                }

                override fun matchesSafely(view: View?): Boolean {
                    if (view !is TextView && view !is EditText) {
                        return false
                    }
                    if (view != null) {
                        val text: String
                        text = if (view is TextView) {
                            (view as TextView).text.toString()
                        } else {
                            (view as EditText).text.toString()
                        }
                        return text.equals(content, ignoreCase = true)
                    }
                    return false
                }
            }
        }

        @JvmStatic
        fun containsStringIgnoringCase(content: String): Matcher<View?>? {
            return object : TypeSafeMatcher<View?>() {
                override fun describeTo(description: Description) {
                    description.appendText("Has EditText/TextView the value:  $content")
                }

                override fun matchesSafely(view: View?): Boolean {
                    if (view !is TextView && view !is EditText) {
                        return false
                    }
                    if (view != null) {
                        val text: String
                        text = if (view is TextView) {
                            (view as TextView).text.toString()
                        } else {
                            (view as EditText).text.toString()
                        }
                        return text.contains(content, ignoreCase = true)
                    }
                    return false
                }
            }
        }
    }
}
