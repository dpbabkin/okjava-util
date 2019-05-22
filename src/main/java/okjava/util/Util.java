package okjava.util;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * Date: 3/23/14
 * Time: 10:29 PM
 */
public final class Util {

    private Util() {
    }


    public static String[] deatachFirstSymbol(String string) {
        String first;
        String second;
        if (string.length() > 0) {
            first = String.valueOf(string.charAt(0));
            second = string.substring(1, string.length());
        } else {
            first = "";
            second = "";
        }
        return new String[]{first, second};
    }

    public static String formatNumber(long l) {
        return formatNumber(l, ' ');
    }

    public static String formatNumber(long l, char separator) {
        char[] n = String.valueOf(l).toCharArray();
        StringBuilder sb = new StringBuilder(n.length + n.length / 3);
        for (int i = 0; i < n.length; i++) {
            sb.append(n[i]);
            if ((n.length - i - 1) % 3 == 0) {
                sb.append(separator);
            }
        }
        return sb.toString();
    }

    public static String i2s(int[] arr) {
        return i2s(arr, ", ");
    }

    public static String i2s(int[] arr, String separator) {
        if (arr == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        for (int i : arr) {
            sb.append(i).append(separator);
        }
        return sb.toString();
    }

    public static Map<String, String> getEnumFields(Class<? extends Enum<?>> en) {
        Map<String, String> fields = new HashMap<>();
        for (Enum<?> field : en.getEnumConstants()) {
            fields.put(field.name(), field.name());
        }
        return ImmutableMap.copyOf(fields);
    }

    public static int hashCode(int a, int b) {
        return 31 * a + b;
    }


    public static int hashCode(int result, Object... objects) {
        for (Object o : objects) {
            result += 31 * result + o.hashCode();
        }
        return result;
    }

    public static int hashCodexx(Object... objects) {
        return hashCode(31, objects);
    }
}
