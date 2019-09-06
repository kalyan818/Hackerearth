package com.example.kickstarter;

/**
 * Created by kalya on 6/5/2018.
 */

public class Upload {
    private String title;
    private String url;
    private String country;
    private String currency;
    private String blurb;
    private String by;
    private String endtime;
    private String location;
    private int percentagefunded;
    private String numbackers;
    private String state;
    private String type;
    private int amtpledged;
    public Upload(){

    }
    public Upload(String title, String url , String country, String currency,String blurb,String by,String endtime,String location,String type,String numbackers,String state,int amtpledged,int percentagefunded){

        this.title = title;
        this.url = url;
        this.country = country;
        this.currency = currency;
        this.blurb = blurb;
        this.by= by;
        this.endtime = endtime;
        this.location =location;
        this.percentagefunded = percentagefunded;
        this.numbackers = numbackers;
        this.state = state;
        this.type = type;
        this.amtpledged=amtpledged;

    }
    public String getTitle()
    {
        return title;
    }
    public void setTitle(String name){
        title = name;

    }



    public String getCurrency()
    {
        return currency;
    }
    public void setCurrency(String price){
        currency = price;
    }




    public String getCountry()
    {
        return country;
    }
    public void setCountry(String count){
        country = count;
    }


    public String getUrl(){
        return url;
    }
    public void setUrl(String imageUrl){
        url = imageUrl;
    }

    public String getBlurb() {
        return blurb;
    }

    public void setBlurb(String blurb) {
        this.blurb = blurb;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getPercentagefunded() {
        return percentagefunded;
    }

    public void setPercentagefunded(int percentagefunded) {
        this.percentagefunded = percentagefunded;
    }

    public String getNumbackers() {
        return numbackers;
    }

    public void setNumbackers(String numbackers) {
        this.numbackers = numbackers;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getAmtpledged() {
        return amtpledged;
    }

    public void setAmtpledged(int amtpledged) {
        this.amtpledged = amtpledged;
    }

    public void setType(String type) {
        this.type = type;
    }
}
