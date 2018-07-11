package diff;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Diff {

    private static Map<String, Map<String, String>> diffResult = new HashMap<String, Map<String, String>>();

    private static void diffTreeBetweenTwoObjects(Object o1, Object o2, String prefixFieldName)  {

        Class<?> old = o1.getClass();
        Class<?> actual = o2.getClass();

        Field[] fields = old.getDeclaredFields();

        for(Field field1 : fields) {
            try {
                Field field2 = actual.getDeclaredField(field1.getName());
                Class<?> fieldType  = field1.getType();

                if(fieldType.isPrimitive() || fieldType.getPackage().getName().equals("java.lang") || fieldType.isEnum()) {
                    Object value1 = getValueByField(field1, o1);
                    Object value2 = getValueByField(field2, o2);

                    if (value1 != null && !value1.equals(value2)) {
                        String fieldName = "";
                        if(prefixFieldName != null) {
                            fieldName = prefixFieldName+ "." +field1.getName();
                        } else {
                            fieldName = field1.getName();
                        }
                        diffResult.put(fieldName, getDiff(value1,value2));
                    }
                }  else if (fieldType.isInterface()) {
                    List<Object> values1 = (List<Object>) getValueByField(field1, o1);
                    List<Object> values2 = (List<Object>) getValueByField(field1, o2);

                    for(int i=0; i < values1.size();i++) {
                        String filedName = field1.getName() +"["+i+"]";
                        diffTreeBetweenTwoObjects(values1.get(i), values2.get(i),filedName);
                    }

                } else {
                    field1.setAccessible(true);
                    field2.setAccessible(true);
                    diffTreeBetweenTwoObjects(field1.get(o1), field2.get(o2), field1.getName());
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }


    private static Object getValueByField(Field field, Object classType) {
        try {
            field.setAccessible(true);
            return field.get(classType);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Map<String, String> getDiff(Object value1, Object value2) {
        Map<String, String> diff = new HashMap<String, String>();
        diff.put(value1.toString(), value2.toString());
        return diff;
    }

    public static Map<String, Map<String, String>> diff(Object o1, Object o2) {
        diffTreeBetweenTwoObjects(o1,o2, null);
        return diffResult;
    }
}
