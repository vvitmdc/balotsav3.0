package app.balotsav.com.vvitbalotsav.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chandu on 21/9/18.
 */

public class Schools implements Parcelable {
    private String School_Name;
    private String School_Code;
    private String Board;
    private String Password;
    private String HeadMaster_Name;
    private String HeadMaster_Email;
    private String HeadMaster_PhNo;
    private String Coordinator_Name;
    private String Coordinator_PhNo;
    private String address;
    private String Town;
    private String District;
    private String State;
    private String PinCode;
    private String studentCount;
    private String boysCount;
    private String girlsCount;
    private String studentHostelCount;
    private String boysHostelCount;
    private String girlsHostelCount;
    private String date1;
    private String date2;
    private String date3;
    private boolean hostel;
    private int sj;
    private int j;
    private int s;
    private int total;
    private int juniorCount;
    private int subJuniorCount;
    private int seniorCount;
    private String boardingPlace;



    public Schools() {
    }


    protected Schools(Parcel in) {
        School_Name = in.readString();
        School_Code = in.readString();
        Board = in.readString();
        Password = in.readString();
        HeadMaster_Name = in.readString();
        HeadMaster_Email = in.readString();
        HeadMaster_PhNo = in.readString();
        Coordinator_Name = in.readString();
        Coordinator_PhNo = in.readString();
        address = in.readString();
        Town = in.readString();
        District = in.readString();
        State = in.readString();
        PinCode = in.readString();
        studentCount = in.readString();
        boysCount = in.readString();
        girlsCount = in.readString();
        studentHostelCount = in.readString();
        boysHostelCount = in.readString();
        girlsHostelCount = in.readString();
        date1 = in.readString();
        date2 = in.readString();
        date3 = in.readString();
        hostel = in.readByte() != 0;
        sj = in.readInt();
        j = in.readInt();
        s = in.readInt();
        total = in.readInt();
        juniorCount = in.readInt();
        subJuniorCount = in.readInt();
        seniorCount = in.readInt();
        boardingPlace = in.readString();
    }

    public static final Creator<Schools> CREATOR = new Creator<Schools>() {
        @Override
        public Schools createFromParcel(Parcel in) {
            return new Schools(in);
        }

        @Override
        public Schools[] newArray(int size) {
            return new Schools[size];
        }
    };

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getJuniorCount() {
        return juniorCount;
    }
    public void setJuniorCount(int juniorCount) {
        this.juniorCount = juniorCount;

    }

    public int getSubJuniorCount() {

        return subJuniorCount;
    }

    public void setSubJuniorCount(int subJuniorCount) {

        this.subJuniorCount = subJuniorCount;
    }

    public int getSeniorCount() {
        return seniorCount;
    }

    public void setSeniorCount(int seniorCount) {
        this.seniorCount = seniorCount;
    }




    public boolean getHostel() {
        return hostel;
    }

    public void setHostel(boolean hostel) {
        this.hostel = hostel;
    }

    public int getSj() {
        return sj;
    }

    public void setSj(int sj) {
        this.sj = sj;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public int getS() {
        return s;
    }

    public void setS(int s) {
        this.s = s;
    }


    public String getStudentHostelCount() {
        return studentHostelCount;
    }

    public void setStudentHostelCount(String studentHostelCount) {
        this.studentHostelCount = studentHostelCount;
    }

    public String getBoysHostelCount() {
        return boysHostelCount;
    }

    public void setBoysHostelCount(String boysHostelCount) {
        this.boysHostelCount = boysHostelCount;
    }

    public String getGirlsHostelCount() {
        return girlsHostelCount;
    }

    public void setGirlsHostelCount(String girlsHostelCount) {
        this.girlsHostelCount = girlsHostelCount;
    }



    public String getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(String studentCount) {
        this.studentCount = studentCount;
    }

    public String getBoysCount() {
        return boysCount;
    }

    public void setBoysCount(String boysCount) {
        this.boysCount = boysCount;
    }

    public String getGirlsCount() {
        return girlsCount;
    }

    public void setGirlsCount(String girlsCount) {
        this.girlsCount = girlsCount;
    }

    public String getDate1() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public String getDate2() {
        return date2;
    }

    public void setDate2(String date2) {
        this.date2 = date2;
    }

    public String getDate3() {
        return date3;
    }

    public void setDate3(String date3) {
        this.date3 = date3;
    }


    public String getSchool_Name() {
        return School_Name;
    }

    public void setSchool_Name(String school_Name) {
        School_Name = school_Name;
    }

    public String getSchool_Code() {
        return School_Code;
    }

    public void setSchool_Code(String school_Code) {
        School_Code = school_Code;
    }

    public String getBoard() {
        return Board;
    }

    public void setBoard(String board) {
        Board = board;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getHeadMaster_Name() {
        return HeadMaster_Name;
    }

    public void setHeadMaster_Name(String headMaster_Name) {
        HeadMaster_Name = headMaster_Name;
    }

    public String getHeadMaster_Email() {
        return HeadMaster_Email;
    }

    public void setHeadMaster_Email(String headMaster_Email) {
        HeadMaster_Email = headMaster_Email;
    }

    public String getHeadMaster_PhNo() {
        return HeadMaster_PhNo;
    }

    public void setHeadMaster_PhNo(String headMaster_PhNo) {
        HeadMaster_PhNo = headMaster_PhNo;
    }

    public String getCoordinator_Name() {
        return Coordinator_Name;
    }

    public void setCoordinator_Name(String coordinator_Name) {
        Coordinator_Name = coordinator_Name;
    }

    public String getCoordinator_PhNo() {
        return Coordinator_PhNo;
    }

    public void setCoordinator_PhNo(String coordinator_PhNo) {
        Coordinator_PhNo = coordinator_PhNo;
    }

    public String getTown() {
        return Town;
    }

    public void setTown(String town) {
        Town = town;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getPinCode() {
        return PinCode;
    }

    public void setPinCode(String pinCode) {
        PinCode = pinCode;
    }


    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getBoardingPlace() {
        return boardingPlace;
    }

    public void setBoardingPlace(String boardingPlace) {
        this.boardingPlace = boardingPlace;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(School_Name);
        dest.writeString(School_Code);
        dest.writeString(Board);
        dest.writeString(Password);
        dest.writeString(HeadMaster_Name);
        dest.writeString(HeadMaster_Email);
        dest.writeString(HeadMaster_PhNo);
        dest.writeString(Coordinator_Name);
        dest.writeString(Coordinator_PhNo);
        dest.writeString(address);
        dest.writeString(Town);
        dest.writeString(District);
        dest.writeString(State);
        dest.writeString(PinCode);
        dest.writeString(studentCount);
        dest.writeString(boysCount);
        dest.writeString(girlsCount);
        dest.writeString(studentHostelCount);
        dest.writeString(boysHostelCount);
        dest.writeString(girlsHostelCount);
        dest.writeString(date1);
        dest.writeString(date2);
        dest.writeString(date3);
        dest.writeByte((byte) (hostel ? 1 : 0));
        dest.writeInt(sj);
        dest.writeInt(j);
        dest.writeInt(s);
        dest.writeInt(total);
        dest.writeInt(juniorCount);
        dest.writeInt(subJuniorCount);
        dest.writeInt(seniorCount);
        dest.writeString(boardingPlace);
    }
}