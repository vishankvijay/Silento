package my.awesome.silento;

/**
 * Created by vishank on 2/3/16.
 */
public class ProfilesList
{
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


    public ProfilesList(int id, int status , String startHour , String startMinute , String endHour,
                        String endMinute , String profileName , String start_profileType , String end_profileType
                        , int sun, int mon , int tue , int wed , int thur , int fri , int sat)
    {
        this.id = id;
        this.status = status;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
        this.profileName = profileName;
        this.start_profileType = start_profileType;
        this.end_profileType = end_profileType;
        this.sun = sun;
        this.mon = mon;
        this.tue = tue;
        this.wed = wed;
        this.thur = thur;
        this.fri = fri;
        this.sat = sat;
    }


    public ProfilesList()
    {

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
}
