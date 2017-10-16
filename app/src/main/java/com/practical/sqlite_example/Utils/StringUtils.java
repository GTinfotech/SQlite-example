package com.practical.sqlite_example.Utils;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

/**
 * CommonUtils class
 * <p/>
 * <p>
 * This is util class for String
 * </p>
 *
 * @author Sumeet Bhut
 * @version 1.0
 * @since 2016-10-15
 */
public class StringUtils {

    public static boolean isEmpty(String string)
    {
        return string==null || string.length()<=0;
    }
    public static boolean isServerUrl(String url){return !StringUtils.isEmpty(url) && url.startsWith("http:");}

    public final static boolean isEmailInValid(CharSequence target) {
        return !(!TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    //space contetnt cheking
    public static boolean isContentSpace(EditText mEditText) {
        return mEditText.getText().toString().contains(" ");

    }

    public final static boolean isPasswordInValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() < 6;
    }

    public static boolean isValidMobile(String mEditText) {
        String mobile = mEditText;
        int length = mobile.length();
        if (length > 7 && length < 14)
            return true;
        return false;
    }

    public static String getQuantityString(int count, String word) {
        switch(count) {
            case 0:
                return "No "+word+"s";
            case 1:
                return "1 "+word;
            default:
                return count+" "+word+"s";
        }
    }

    public static SpannableString getKeyValueSpan(String key,String value)
    {
        if(!key.substring(key.length()-2).equals(": ")) {
            key += ": ";
        }
        SpannableString span=new SpannableString(key+value);
        span.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),0,key.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        return span;
    }

    public static String formatDouble(double num)
    {
        return String.format(Locale.ENGLISH,"%.2f",num);
    }

    public static String format1PDouble(double num)
    {
        return String.format(Locale.ENGLISH,"%.1f",num);
    }

    public static void doUnderline(TextView tv) {
        SpannableString span = new SpannableString(tv.getText().toString());
        span.setSpan(new UnderlineSpan(), 0, tv.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv.setText(span);
    }
}
