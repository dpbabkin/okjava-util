package okjava.util.string;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 1/20/2019
 * 18:58.
 */
public class StringFormatTest {


    public void test001() {

        Structure structure = new Structure(12, "test", ImmutableList.of("aaa", "bbb", "ccc", "ddd"));


    }

    private static class Structure {
        private final int intField;
        private final String stringField;
        private final List<String> listOfStringsField;

        private Structure(int intField, String stringField, List<String> listOfStringsField) {
            this.intField = intField;
            this.stringField = stringField;
            this.listOfStringsField = listOfStringsField;
        }

        public int getIntField() {
            return intField;
        }

        public String getStringField() {
            return stringField;
        }

        public List<String> getListOfStringsField() {
            return listOfStringsField;
        }
    }
}
