package Labs.lab2.abiturient;

public class Abiturient {
    private int id;
    private String lastName;
    private String name;
    private String patronymic;
    private String phoneNumber;
    private double averageScore;

    public Abiturient(int id, String lastName, String name, String patronymic, String phoneNumber, double averageScore) {
        this.id = id;
        this.lastName = lastName;
        this.name = name;
        this.patronymic = patronymic;
        this.phoneNumber = phoneNumber;
        this.averageScore = averageScore;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    public String toString() {
        return "Information about abiturient with id: " + getId()
                + "\n\tLast name: " + getLastName()
                + "\n\tName: " + getName()
                + "\n\tPatronymic: " + getPatronymic()
                + "\n\tPhone number: " + getPhoneNumber()
                + "\n\tAverage score: " + getAverageScore();
    }
}
