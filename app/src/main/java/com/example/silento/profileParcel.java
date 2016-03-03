package com.example.silento;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 1305166 on 02-10-2015.
 */
public class profileParcel implements Parcelable {

    private int id;
    private int status;
    private String startHour;
    private String startMinute;
    private String endHour;
    private String endMinute;
    private String profileName;
    private String start_profileType;
    private String end_profileType;
    private int sun;
    private int mon;
    private int tue;
    private int wed;
    private int thur;
    private int fri;
    private int sat;

    public profileParcel() {
        super();
    }

    private profileParcel(Parcel in) {
        super();
        this.id = in.readInt();
        this.status = in.readInt();
        this.startHour = in.readString();
        this.startMinute = in.readString();
        this.endHour = in.readString();
        this.endMinute = in.readString();
        this.profileName = in.readString();
        this.start_profileType = in.readString();
        this.end_profileType = in.readString();
        this.sun = in.readInt();
        this.mon = in.readInt();
        this.tue = in.readInt();
        this.wed = in.readInt();
        this.thur = in.readInt();
        this.fri = in.readInt();
        this.sat = in.readInt();
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }


    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }


    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getStartHour() {
        return startHour;
    }


    public void setStartMinute(String startMinute) {
        this.startMinute = startMinute;
    }

    public String getStartMinute() {
        return startMinute;
    }


    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }

    public String getEndHour() {
        return endHour;
    }


    public void setEndMinute(String endMinute) {
        this.endMinute = endMinute;
    }

    public String getEndMinute() {
        return endMinute;
    }


    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setstart_profileType(String start_profileType) {
        this.start_profileType = start_profileType;
    }

    public String getstart_profileType() {
        return start_profileType;
    }


    public void setend_profileType(String end_profileType) {
        this.end_profileType = end_profileType;
    }

    public String getend_profileType() {
        return end_profileType;
    }


    public void setSun(int sun) {
        this.sun = sun;
    }

    public int getSun() {
        return sun;
    }

    public void setMon(int mon) {
        this.mon = mon;
    }

    public int getMon() {
        return mon;
    }

    public void setTue(int tue) {
        this.tue = tue;
    }

    public int getTue() {
        return tue;
    }


    public void setWed(int wed) {
        this.wed = wed;
    }

    public int getWed() {
        return wed;
    }


    public void setThur(int thur) {
        this.thur = thur;
    }

    public int getThur() {
        return thur;
    }


    public void setFri(int fri) {
        this.fri = fri;
    }

    public int getFri() {
        return fri;
    }


    public void setSat(int sat) {
        this.sat = sat;
    }

    public int getSat() {
        return sat;
    }


    @Override
    public String toString() {
        return "Profile [id=" + id + ", status=" + status
                + ", startHour=" + startHour + ", startMinute=" + startMinute
                + ", endHour=" + endHour
                + ", endMinute=" + endMinute
                + ", profileName=" + profileName + ", start_profileType=" + start_profileType + ", sun=" + sun + ", mon=" + mon + ", tue=" + tue + ", wed=" + wed + ", thur=" + thur + ", fri=" + fri + ", sat=" + sat + ", end_profileType=" + end_profileType + "]";
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        profileParcel other = (profileParcel) obj;
        if (id != other.id)
            return false;
        return true;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(getId());
        dest.writeInt(getStatus());
        dest.writeString(getStartHour());
        dest.writeString(getStartMinute());
        dest.writeString(getEndHour());
        dest.writeString(getEndMinute());
        dest.writeString(getProfileName());
        dest.writeString(getstart_profileType());
        dest.writeInt(getSun());
        dest.writeInt(getMon());
        dest.writeInt(getTue());
        dest.writeInt(getWed());
        dest.writeInt(getThur());
        dest.writeInt(getFri());
        dest.writeInt(getSat());
        dest.writeString(getend_profileType());


    }


    public static final Parcelable.Creator<profileParcel> CREATOR = new Parcelable.Creator<profileParcel>() {
        public profileParcel createFromParcel(Parcel in) {
            return new profileParcel(in);
        }

        public profileParcel[] newArray(int size) {
            return new profileParcel[size];

        }
    };


}
