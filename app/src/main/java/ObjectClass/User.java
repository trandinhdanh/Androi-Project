package ObjectClass;

public class User {
    private String name;
    private Date birthDay;
    private String numberPhone;
    private String gender;
    private String address;

    public User(String name, Date birthDay, String numberPhone, String gender, String address) {
        this.name = name;
        this.birthDay = birthDay;
        this.numberPhone = numberPhone;
        this.gender = gender;
        this.address = address;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
