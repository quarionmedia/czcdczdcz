package com.dooo.android.list;

public class MostSearchedList {
    private int ID;
    private int Type;
    private String Title;
    private String Year;
    private String Thumbnail;
    private int contentType;
    private String custom_tag;
    private String custom_tag_background_color;
    private String custom_tag_text_color;

    public MostSearchedList(int ID, int type, String title, String year, String thumbnail, int contentType, String custom_tag, String custom_tag_background_color, String custom_tag_text_color) {
        this.ID = ID;
        Type = type;
        Title = title;
        Year = year;
        Thumbnail = thumbnail;
        this.contentType = contentType;
        this.custom_tag = custom_tag;
        this.custom_tag_background_color = custom_tag_background_color;
        this.custom_tag_text_color = custom_tag_text_color;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        Thumbnail = thumbnail;
    }

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    public String getCustom_tag() {
        return custom_tag;
    }

    public void setCustom_tag(String custom_tag) {
        this.custom_tag = custom_tag;
    }

    public String getCustom_tag_background_color() {
        return custom_tag_background_color;
    }

    public void setCustom_tag_background_color(String custom_tag_background_color) {
        this.custom_tag_background_color = custom_tag_background_color;
    }

    public String getCustom_tag_text_color() {
        return custom_tag_text_color;
    }

    public void setCustom_tag_text_color(String custom_tag_text_color) {
        this.custom_tag_text_color = custom_tag_text_color;
    }
}
