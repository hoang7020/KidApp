package vn.edu.fpt.kidapp.JsonModel;

public class CapturePicture {
    private int id;
    private String name;
    private String eng1;
    private String eng2;
    private String eng3;
    private String vie1;
    private String vie2;
    private String vie3;
    private double timeshoot;


    public CapturePicture() {
    }

    public CapturePicture(int id, String name, String eng1, String eng2, String eng3, String vie1, String vie2, String vie3, double timeshoot) {
        this.id = id;
        this.name = name;
        this.eng1 = eng1;
        this.eng2 = eng2;
        this.eng3 = eng3;
        this.vie1 = vie1;
        this.vie2 = vie2;
        this.vie3 = vie3;
        this.timeshoot = timeshoot;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEng1() {
        return eng1;
    }

    public void setEng1(String eng1) {
        this.eng1 = eng1;
    }

    public String getEng2() {
        return eng2;
    }

    public void setEng2(String eng2) {
        this.eng2 = eng2;
    }

    public String getEng3() {
        return eng3;
    }

    public void setEng3(String eng3) {
        this.eng3 = eng3;
    }

    public String getVie1() {
        return vie1;
    }

    public void setVie1(String vie1) {
        this.vie1 = vie1;
    }

    public String getVie2() {
        return vie2;
    }

    public void setVie2(String vie2) {
        this.vie2 = vie2;
    }

    public String getVie3() {
        return vie3;
    }

    public void setVie3(String vie3) {
        this.vie3 = vie3;
    }

    public double getTimeshoot() {
        return timeshoot;
    }

    public void setTimeshoot(double timeshoot) {
        this.timeshoot = timeshoot;
    }
}
