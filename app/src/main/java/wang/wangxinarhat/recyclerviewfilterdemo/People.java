package wang.wangxinarhat.recyclerviewfilterdemo;

public class People {

    private String name;
    private String address;

    public People() {
    }

    public People(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
