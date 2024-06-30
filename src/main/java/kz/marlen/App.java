package kz.marlen;

public class App {
    public static void main(String[] args) {
        try {
            String createTableSQL = HibernateUtil.generateCreateTableSQL(User.class);
            System.out.println(createTableSQL);

            User user = new User();
            user.setName("Bob");
            user.setAge(30);

            String insertSQL = HibernateUtil.generateInsertSQL(user);
            System.out.println(insertSQL);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
