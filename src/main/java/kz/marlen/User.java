package kz.marlen;

@Entity(tableName = "Пользователи")
public class User {
    @Column(name = "Имя")
    private String name;

    @Column(name = "Возраст")
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
