package diff;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Diff {

    private static Map<String, Map<String, String>> diffResult = new HashMap<String, Map<String, String>>();

    private static void diffTreeBetweenTwoObjects(Object o1, Object o2, String prefixFieldName) throws IllegalAccessException, InstantiationException, NoSuchFieldException {

        Class<?> old = o1.getClass();
        Class<?> actual = o2.getClass();

        //TODO: Verificar se os dois são filhos da mesma classe
        Field[] fields = old.getDeclaredFields();

        for(Field field : fields) {
            Field field2 = actual.getDeclaredField(field.getName());
            Class<?> fieldType  = field.getType();

            // TODO: Fazer tratamento para lista de objetos
            if(fieldType.isPrimitive() || fieldType.getPackage().getName().equals("java.lang")) {
                Object value = getValueByField(field, o1);
                Object value2 = getValueByField(field2, o2);

                if (!value.equals(value2)) {
                    String fieldName = "";
                    if(prefixFieldName != null) {
                        fieldName = prefixFieldName+ "." +field.getName();
                    } else {
                        fieldName = field.getName();
                    }
                    diffResult.put(fieldName, getDiff(value,value2));
                }
            } else {
                field.setAccessible(true);
                field2.setAccessible(true);
                diffTreeBetweenTwoObjects(field.get(o1), field2.get(o2), field.getName());
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

    public static Map<String, Map<String, String>> diff(Object o1, Object o2) throws IllegalAccessException, NoSuchFieldException, InstantiationException {
        diffTreeBetweenTwoObjects(o1,o2, null);
        return diffResult;
    }
}
