package andycheung.baseproject.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.android.volley.VolleyError;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Andy Cheung on 4/5/15.
 */
public class DataUtil {
    public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth,
                                                     int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        // ***
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);

        // *** Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res,
                                                         int resId, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        // ***
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        // ***
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and
            // keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static Bitmap getBitmapFromAsset(Context context, String strName) {
        AssetManager assetManager = context.getAssets();
        Bitmap bitmap = null;
        InputStream istr = null;
        try {
            istr = assetManager.open(strName);
            bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * File
     *
     * @throws IOException
     */
    public static boolean deleteFile(String path) {
        if (isEmpty(path)) {
            LogUtil.log("[DataUtil] deleteFile>> EMPTY PATH");
            return false;
        } else {
            File dir = new File(path);
            if (dir.exists()) {
                if (dir.isDirectory()) {
                    // is a folder
                    for (File childrenFile : dir.listFiles()) {
                        deleteFile(childrenFile.getPath());
                    }
                } else {
                    // just a file
                }

				/* delete it */
                boolean deleteFile = dir.delete();
                LogUtil.log("[DataUtil] deleteFile>> " + deleteFile + ">"
                        + path);
                return deleteFile;
            } else {
                LogUtil.log("[DataUtil] deleteFile>> NOT EXISTS>" + path);
                return false;
            }
        }
    }

    public static void copyFile(File fromFile, File toFile) throws IOException {
        if (fromFile != null && toFile != null) {
            deleteFile(toFile.getPath());

            InputStream in = new FileInputStream(fromFile);
            OutputStream out = new FileOutputStream(toFile);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        }
    }

    public static void writeStringToStorage(String data, String path) {
        DataUtil.deleteFile(path);

        try {
            InputStream is = convertStringToInputStream(data);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            FileOutputStream fos = new FileOutputStream(new File(path));
            fos.write(buffer);
            fos.close();

            LogUtil.log("DataUtil >>> writeToFile success: " + path);
        } catch (Exception e) {
            LogUtil.log("DataUtil >>> writeToFile fail: " + path);
            e.printStackTrace();
        }
    }

    public static boolean writeByteArrayToStorage(byte[] bs, String path) {
        deleteFile(path);

        try {
            FileOutputStream fos = new FileOutputStream(path);
            fos.write(bs);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static String getStringFromFile(String filePath) throws IOException {
        File fl = new File(filePath);
        FileInputStream fin = new FileInputStream(fl);
        String ret = convertStreamToString(fin);
        // Make sure you close all streams.
        fin.close();
        return ret;
    }

    public static String readableFileSize(long size) {
        if (size <= 0)
            return "0";
        final String[] units = new String[]{"B", "kB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size
                / Math.pow(1024, digitGroups))
                + " " + units[digitGroups];
    }

    /**
     * convert
     *
     * @throws IOException
     */
    public static String convertStreamToString(InputStream is)
            throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    public static byte[] convertFileToByteArray(File file) {
        byte[] byteArray = null;
        try {
            InputStream inputStream = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024 * 8];
            int bytesRead = 0;

            while ((bytesRead = inputStream.read(b)) != -1) {
                bos.write(b, 0, bytesRead);
            }
            inputStream.close();
            byteArray = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArray;
    }

    public static String convertAssetsFileToString(Context context,
                                                   String fileName) {
        byte[] buffer = null;
        String str = null;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            buffer = new byte[size];
            is.read(buffer);
            is.close();

            str = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return str;
    }

    public static InputStream convertStringToInputStream(String data) {
        if (data != null) {
            try {
                return new ByteArrayInputStream(data.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String encryptMD5(String str) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            result = toHexString(md.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String toHexString(byte[] in) {
        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < in.length; i++) {
            String hex = Integer.toHexString(0xFF & in[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static byte[] SHA1(String text) throws NoSuchAlgorithmException,
            UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        byte[] sha1hash = md.digest();
        return sha1hash;
    }

    /**
     * Api
     */

    public static String checkApiResponseFormat(String response) {
        if (!isEmpty(response)) {
            int startChact = response.indexOf("<");
            if (startChact >= 0) {
                response = response.substring(startChact);
            }
        }
        return response;
    }

    public static String checkHtmlUrl(String url) {
        if (url != null) {
            final String httpTag = "http://";
            final String httpsTag = "https://";

            Locale locale = LanguagesUtil.getCurrentLanguageLocale();
            if (!url.toLowerCase(locale).startsWith(httpTag)
                    && !url.toLowerCase(locale).startsWith(httpsTag)) {
                url = httpTag + url;
            }
        }
        return url;
    }

    /**
     * BigDecimal Formatter
     */

    public static String bigDecimalFormatter(BigDecimal bigDecimal,
                                             int decimalPlace) {
        if (bigDecimal != null) {
            DecimalFormat moneyDecimalFormat = new DecimalFormat();
            moneyDecimalFormat.setMinimumFractionDigits(decimalPlace);
            moneyDecimalFormat.setMaximumFractionDigits(decimalPlace);
            return moneyDecimalFormat.format(bigDecimal);
        } else {
            return "";
        }
    }

    public static String bigDecimalFormatter(BigDecimal bigDecimal,
                                             int minimumFractionDigits, int maximumFractionDigits) {
        if (bigDecimal != null) {
            DecimalFormat moneyDecimalFormat = new DecimalFormat();
            moneyDecimalFormat.setMinimumFractionDigits(minimumFractionDigits);
            moneyDecimalFormat.setMaximumFractionDigits(maximumFractionDigits);
            return moneyDecimalFormat.format(bigDecimal);
        } else {
            return "";
        }
    }

    /**
     * Money Format
     */
    public static String moneyFormatter(BigDecimal money,
                                        int minimumFractionDigits, int maximumFractionDigits) {
        if (money != null) {
            DecimalFormat moneyDecimalFormat = new DecimalFormat();
            moneyDecimalFormat.setGroupingSize(3);
            moneyDecimalFormat.setGroupingUsed(true);
            moneyDecimalFormat.setMinimumFractionDigits(minimumFractionDigits);
            moneyDecimalFormat.setMaximumFractionDigits(maximumFractionDigits);
            return moneyDecimalFormat.format(money);
        } else {
            return "";
        }
    }

    public static String moneyFormatter(String money,
                                        int minimumFractionDigits, int maximumFractionDigits) {
        money = isEmpty(money) ? null : money.trim().replace(",", "");
        if (isDigitString(money)) {
            return moneyFormatter(new BigDecimal(money), minimumFractionDigits,
                    maximumFractionDigits);
        } else {
            return "";
        }
    }

    public static String moneyFormatter(Integer money,
                                        int minimumFractionDigits, int maximumFractionDigits) {
        if (money != null) {
            return moneyFormatter(new BigDecimal(money), minimumFractionDigits,
                    maximumFractionDigits);
        } else {
            return "";
        }
    }

    public static String moneyFormatter(Long money, int minimumFractionDigits,
                                        int maximumFractionDigits) {
        if (money != null) {
            return moneyFormatter(new BigDecimal(money), minimumFractionDigits,
                    maximumFractionDigits);
        } else {
            return "";
        }
    }

    public static String moneyFormatter(Double money,
                                        int minimumFractionDigits, int maximumFractionDigits) {
        if (money != null) {
            return moneyFormatter(new BigDecimal(money), minimumFractionDigits,
                    maximumFractionDigits);
        } else {
            return "";
        }
    }

    public static String moneyFormatter(Float money, int minimumFractionDigits,
                                        int maximumFractionDigits) {
        if (money != null) {
            return moneyFormatter(new BigDecimal(money), minimumFractionDigits,
                    maximumFractionDigits);
        } else {
            return "";
        }
    }

    //
    public static String moneyFormatter(BigDecimal bigDecimal, int decimalPlace) {
        if (bigDecimal != null) {
            DecimalFormat moneyDecimalFormat = new DecimalFormat();
            moneyDecimalFormat.setGroupingSize(3);
            moneyDecimalFormat.setGroupingUsed(true);
            //JIRA DPIAS-919
            moneyDecimalFormat.setMinimumFractionDigits(0);
            moneyDecimalFormat.setMaximumFractionDigits(decimalPlace);
            return moneyDecimalFormat.format(bigDecimal);
        } else {
            return "";
        }
    }

    public static String moneyFormatter(String string, int decimalPlace) {
        string = isEmpty(string) ? null : string.trim().replace(",", "");
        if (isDigitString(string)) {
            return moneyFormatter(new BigDecimal(string), decimalPlace);
        } else {
            return "";
        }
    }

    public static String moneyFormatter(Integer money, int decimalPlace) {
        if (money != null) {
            return moneyFormatter(BigDecimal.valueOf(money), decimalPlace);
        } else {
            return "";
        }
    }

    public static String moneyFormatter(Long money, int decimalPlace) {
        if (money != null) {
            return moneyFormatter(BigDecimal.valueOf(money), decimalPlace);
        } else {
            return "";
        }
    }

    public static String moneyFormatter(Double money, int decimalPlace) {
        if (money != null) {
            return moneyFormatter(BigDecimal.valueOf(money), decimalPlace);
        } else {
            return "";
        }
    }

    public static String moneyFormatter(Float money, int decimalPlace) {
        if (money != null) {
            return moneyFormatter(BigDecimal.valueOf(money), decimalPlace);
        } else {
            return "";
        }
    }

    /**
     * parse
     */

    // date
    public static Date parseDate(String date, String dateFormat) {
        if (!isEmpty(date)) {
            try {
                return new SimpleDateFormat(dateFormat,
                        LanguagesUtil.getCurrentLanguageLocale()).parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Date parseDate(TextView dateTextView, String dateFormat) {
        String date = dateTextView.getText().toString();
        return parseDate(date, dateFormat);
    }

    // string
    public static String parseStringFromArray(String separator,
                                              List<String> rowItems) {
        StringBuilder resultBuilder = new StringBuilder();
        String tempString;
        int size = rowItems.size();
        for (int i = 0; i < size; i++) {
            tempString = rowItems.get(i);
            if (!DataUtil.isEmpty(tempString)) {
                resultBuilder.append(tempString);
            }
            if (i < size - 1) {
                resultBuilder.append(separator);
            }
        }
        return resultBuilder.toString();
    }

    public static String parseStringFromArray(String separator,
                                              String... rowItems) {
        List<String> rowsList = new ArrayList<String>();
        for (String string : rowItems) {
            rowsList.add(string);
        }
        return parseStringFromArray(separator, rowsList);
    }

    public static String parseString(Date date, String dateFormat) {
        SimpleDateFormat newFormat = new SimpleDateFormat(dateFormat,
                LanguagesUtil.getCurrentLanguageLocale());
        return newFormat.format(date);
    }

    public static String parseString(Object... objects) {
        String string = "";
        if (!isEmpty(objects)) {
            for (Object object : objects) {
                string += object == null ? "" : object + "";
            }
        }
        return isEmpty(string) ? null : string.trim();
    }

    // number
    public static Integer parseInt(String num) {
        if (isEmpty(num)) {
            return null;
        } else {
            num = num.replace(",", "");
        }
        if (isDigitString(num)) {
            return Integer.parseInt(num);
        } else {
            return null;
        }
    }

    public static Long parseLong(String num) {
        if (isEmpty(num)) {
            return null;
        } else {
            num = num.replace(",", "");
        }
        if (isDigitString(num)) {
            return Long.parseLong(num);
        } else {
            return null;
        }
    }

    public static Float parseFloat(String num) {
        if (isEmpty(num)) {
            return null;
        } else {
            num = num.replace(",", "");
        }
        if (isDigitString(num)) {
            return Float.parseFloat(num);
        } else {
            return null;
        }
    }

    public static Double parseDouble(String num) {
        if (isEmpty(num)) {
            return null;
        } else {
            num = num.replace(",", "");
        }
        if (num.length() > 0 && num.charAt(num.length() - 1) == '.') {
            num = num.substring(0, num.length() - 1);
        }
        if (isDigitString(num)) {
            return Double.parseDouble(num);
        } else {
            return null;
        }
    }

    public static Integer parseInt(TextView view) {
        if (view != null) {
            return parseInt(view.getText().toString().trim());
        }
        return null;
    }

    public static Long parseLong(TextView view) {
        if (view != null) {
            return parseLong(view.getText().toString().trim());
        }
        return null;
    }

    public static Float parseFloat(TextView view) {
        if (view != null) {
            return parseFloat(view.getText().toString().trim());
        }
        return null;
    }

    public static Double parseDouble(TextView view) {
        if (view != null) {
            return parseDouble(view.getText().toString().trim());
        }
        return null;
    }

    /**
     * check Empty
     */

    public static <T> boolean isEmpty(T[] array) {
        if (array != null) {
            return array.length == 0;
        }
        return true;
    }

    public static <K, V> boolean isEmpty(Map<K, V> map) {
        if (map != null) {
            return map.isEmpty();
        }
        return true;
    }

    public static <E> boolean isEmpty(List<E> list) {
        if (list != null) {
            return list.isEmpty();
        }
        return true;
    }

    public static <E> boolean isEmpty(Collection<E> list) {
        if (list != null) {
            return list.isEmpty();
        }
        return true;
    }

    public static boolean isEmpty(String string) {
        if (string != null) {
            return string.isEmpty();
        }
        return true;
    }

    /**
     * Checking
     */

    public static boolean isDigitString(String string) {
        if (string != null) {
            return string.matches("-?\\d+(\\.\\d+)?");
        } else {
            return false;
        }
    }

    public static <A, E> boolean isArrayContains(A[] arrays, E object) {
        if (!isEmpty(arrays)) {
            for (A a : arrays) {
                if (a.equals(object)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * View
     */

    public static void setStringToTextView(TextView textView, String string) {
        if (textView != null) {
            if (!DataUtil.isEmpty(string)) {
                textView.setVisibility(View.VISIBLE);
                textView.setText(string);
            } else {
                textView.setVisibility(View.GONE);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void setAlpha(View view, float alphaValue) {
        if (view != null) {
            if (Build.VERSION.SDK_INT >= 11) {
                view.setAlpha(alphaValue);
            } else {
                AlphaAnimation alpha = new AlphaAnimation(
                        alphaValue == 1 ? 0.5F : 1F, alphaValue);
                alpha.setDuration(0);
                alpha.setFillAfter(true);
                view.startAnimation(alpha);
            }
        }
    }

    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void removeOnGlobalLayoutListener(View view,
                                                    ViewTreeObserver.OnGlobalLayoutListener victim) {
        if (view != null) {
            if (Build.VERSION.SDK_INT >= 16) {
                view.getViewTreeObserver().removeOnGlobalLayoutListener(victim);
            } else {
                view.getViewTreeObserver().removeGlobalOnLayoutListener(victim);
            }
        }
    }

    public static void setEnableViewGroup(boolean enable, View view) {
        view.setEnabled(enable);
        if (view instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) view;
            for (int i = 0; i < vg.getChildCount(); i++) {
                View child = vg.getChildAt(i);
                setEnableViewGroup(enable, child);
            }
        }
    }

    public static void setNumberToTextView(TextView view, Double value) {
        if (view != null) {
            if (value == null) {
                view.setText(null);
            } else {
                view.setText(value + "");
            }
        }
    }

    public static void setNumberToTextView(TextView view, Integer value) {
        if (view != null) {
            if (value == null) {
                view.setText(null);
            } else {
                view.setText(value + "");
            }
        }
    }

    public static void setNumberToTextView(TextView view, Float value) {
        if (view != null) {
            if (value == null) {
                view.setText(null);
            } else {
                view.setText(String.format("%.2f", value));
            }
        }
    }

    public static void setDateToTextView(TextView view, Date date,
                                         String dateFormat) {
        if (view != null) {
            if (date == null) {
                view.setText(null);
            } else {
                view.setText(parseString(date, dateFormat));
            }
        }
    }

    /**
     * Volley
     */
    public static String getVolleyErrorMessage(VolleyError error) {
        String errorMsg = null;
        if (error != null) {
            errorMsg = "VolleyError>>\n";
            if (!DataUtil.isEmpty(error.getMessage())) {
                errorMsg = "VolleyError.message>>\n" + error.getMessage();
            } else if (error.networkResponse != null
                    && error.networkResponse.data != null) {
                errorMsg = "VolleyError.networkResponse.data>>\n"
                        + new String(error.networkResponse.data);
            }
        }
        return errorMsg;
    }

    /**
     * Soft keyboard
     */
    public static void hideSoftKeyboard(Activity activity) {
        if (activity != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity
                    .getSystemService(Activity.INPUT_METHOD_SERVICE);
            if (activity.getCurrentFocus() != null) {
                inputMethodManager.hideSoftInputFromWindow(activity
                        .getCurrentFocus().getWindowToken(), 0);
            }
        }
    }

}
