package kz.marlen;

import java.lang.reflect.Field;

public class HibernateUtil {

    public static String generateCreateTableSQL(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new RuntimeException("Класс не аннотирован с @Entity");
        }

        Entity entity = clazz.getAnnotation(Entity.class);
        String tableName = entity.tableName();
        StringBuilder sql = new StringBuilder("CREATE TABLE " + tableName + " (");

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                String columnName = column.name();
                String columnType = getSQLType(field.getType());
                sql.append(columnName).append(" ").append(columnType).append(", ");
            }
        }

        sql.setLength(sql.length() - 2);
        sql.append(");");

        return sql.toString();
    }

    public static String generateInsertSQL(Object obj) throws IllegalAccessException {
        Class<?> clazz = obj.getClass();
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new RuntimeException("Класс не аннотирован с  @Entity");
        }

        Entity entity = clazz.getAnnotation(Entity.class);
        String tableName = entity.tableName();
        StringBuilder sql = new StringBuilder("INSERT INTO " + tableName + " (");
        StringBuilder values = new StringBuilder("VALUES (");

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                String columnName = column.name();
                field.setAccessible(true);
                Object value = field.get(obj);
                sql.append(columnName).append(", ");
                values.append("'").append(value).append("', ");
            }
        }

        sql.setLength(sql.length() - 2);
        values.setLength(values.length() - 2);
        sql.append(") ").append(values).append(");");

        return sql.toString();
    }

    private static String getSQLType(Class<?> type) {
        if (type == String.class) {
            return "VARCHAR(255)";
        } else if (type == int.class || type == Integer.class) {
            return "INT";
        }

        throw new RuntimeException("Неподдерживаемый тип поля: " + type.getName());
    }
}
