package vn.edu.fpt.kidapp.interfaces;

public interface Subject {

    public final int PICTURE_PREDICT = 1;
    public final int ENGLISH_TRANSLATE = 2;

    public void registerObserver(Observer observer);
    public void removeObserver(Observer observer);
    public void sendNotification(int type, String rs1, String rs2, String rs3);
}
