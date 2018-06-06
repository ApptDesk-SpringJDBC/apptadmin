package com.telappoint.apptadmin.model;


import static com.telappoint.apptadmin.utils.TimeUtils.geHourMinMeridian;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ResourceWorkingHrs extends ApptSysConfig implements Serializable{

   private static final long serialVersionUID = 1L;

	private Integer resourceId;

    private String resource_code;
    private Integer location_id;
    private String resource_type;

    private List<Integer> selectedServiceIds;
    private List<Integer> selectedResourceIds;
    private String effective_date;

    @JsonIgnore
    private String sun_start_time;
    @JsonIgnore
    private String sun_end_time;
    @JsonIgnore
    private String sun_break_time_1;
    private Integer sun_break_time_1_mins;
    @JsonIgnore
    private String mon_start_time;
    @JsonIgnore
    private String mon_end_time;
    @JsonIgnore
    private String mon_break_time_1;
    private Integer mon_break_time_1_mins;
    @JsonIgnore
    private String tue_start_time;
    @JsonIgnore
    private String tue_end_time;
    @JsonIgnore
    private String tue_break_time_1;
    private Integer tue_break_time_1_mins;
    @JsonIgnore
    private String wed_start_time;
    @JsonIgnore
    private String wed_end_time;
    @JsonIgnore
    private String wed_break_time_1;
    private Integer wed_break_time_1_mins;
    @JsonIgnore
    private String thu_start_time;
    @JsonIgnore
    private String thu_end_time;
    @JsonIgnore
    private String thu_break_time_1;
    private Integer thu_break_time_1_mins;
    @JsonIgnore
    private String fri_start_time;
    @JsonIgnore
    private String fri_end_time;
    @JsonIgnore
    private String fri_break_time_1;
    private Integer fri_break_time_1_mins;
    @JsonIgnore
    private String sat_start_time;
    @JsonIgnore
    private String sat_end_time;
    @JsonIgnore
    private String sat_break_time_1;
    private Integer sat_break_time_1_mins;
    private String sun_break_time_str;
    private String mon_break_time_str;
    private String tue_break_time_str;
    private String wed_break_time_str;
    private String thu_break_time_str;
    private String fri_break_time_str;
    private String sat_break_time_str;

    private Integer sat_start_min;
    private String sat_start_meridian;
    private Integer sat_end_min;
    private String sat_end_meridian;

    private Integer sun_start_min;
    private Integer sun_end_min;
    private String sun_start_meridian;

    private Integer sun_start_hour;
    private Integer sun_end_hour;
    private String sun_end_meridian;


    private Integer sat_start_hour;
    private Integer sat_end_hour;

    private Integer mon_start_hour;
    private Integer mon_start_min;
    private String mon_start_meridian;
    private Integer mon_end_hour;
    private Integer mon_end_min;
    private String mon_end_meridian;

    private Integer tue_start_hour;
    private Integer tue_start_min;
    private String tue_start_meridian;
    private Integer tue_end_hour;
    private Integer tue_end_min;
    private String tue_end_meridian;
    private Integer wed_start_hour;
    private Integer wed_start_min;
    private String wed_start_meridian;
    private Integer wed_end_hour;
    private Integer wed_end_min;
    private String wed_end_meridian;
    private Integer thu_start_hour;
    private Integer thu_start_min;
    private String thu_start_meridian;
    private Integer thu_end_hour;
    private Integer thu_end_min;
    private String thu_end_meridian;
    private Integer fri_start_hour;
    private Integer fri_start_min;
    private String fri_start_meridian;

    private Integer fri_end_hour;
    private Integer fri_end_min;
    private String fri_end_meridian;

    private String is_sun_open = "N";
    private String is_mon_open = "N";
    private String is_tue_open = "N";
    private String is_wed_open = "N";
    private String is_thu_open = "N";
    private String is_fri_open = "N";
    private String is_sat_open = "N";

    private Integer mon_break_time_hour;
    private Integer mon_break_time_min;
    private String mon_break_time_meridian;
    private Integer tue_break_time_hour;
    private Integer tue_break_time_min;
    private String tue_break_time_meridian;
    private Integer wed_break_time_hour;
    private Integer wed_break_time_min;
    private String wed_break_time_meridian;
    private Integer thu_break_time_hour;
    private Integer thu_break_time_min;
    private String thu_break_time_meridian;
    private Integer fri_break_time_hour;
    private Integer fri_break_time_min;
    private String fri_break_time_meridian;
    private Integer sat_break_time_hour;
    private Integer sat_break_time_min;
    private String sat_break_time_meridian;
    private Integer sun_break_time_hour;
    private Integer sun_break_time_min;
    private String sun_break_time_meridian;
    private String end_date;

    private String is_mon_no_break_time = "Y";
    private String is_tue_no_break_time = "Y";
    private String is_wed_no_break_time = "Y";
    private String is_thu_no_break_time = "Y";
    private String is_fri_no_break_time = "Y";
    private String is_sat_no_break_time = "Y";
    private String is_sun_no_break_time = "Y";

    private String default_is_sun_open;
    private String default_is_mon_open;
    private String default_is_tue_open;
    private String default_is_wed_open;
    private String default_is_thu_open;
    private String default_is_fri_open;
    private String default_is_sat_open;
    private Integer blockTimeInMins;

    public String getResource_code() {
        return resource_code;
    }

    public void setResource_code(String resource_code) {
        this.resource_code = resource_code;
    }

    public Integer getLocation_id() {
        return location_id;
    }

    public void setLocation_id(Integer location_id) {
        this.location_id = location_id;
    }

    public String getResource_type() {
        return resource_type;
    }

    public void setResource_type(String resource_type) {
        this.resource_type = resource_type;
    }

    public List<Integer> getSelectedServiceIds() {
        return selectedServiceIds;
    }

    public void setSelectedServiceIds(List<Integer> selectedServiceIds) {
        this.selectedServiceIds = selectedServiceIds;
    }

    public List<Integer> getSelectedResourceIds() {
        return selectedResourceIds;
    }

    public void setSelectedResourceIds(List<Integer> selectedResourceIds) {
        this.selectedResourceIds = selectedResourceIds;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public String getEffective_date() {
        return effective_date;
    }

    public void setEffective_date(String effective_date) {
        this.effective_date = effective_date;
    }

    public String getSun_start_time() {
        return sun_start_time;
    }

    public void setSun_start_time(String sun_start_time) {
        this.sun_start_time = sun_start_time;
        if (sun_start_time != null) {
            setSun_start_hour((Integer) geHourMinMeridian(sun_start_time.toString()).get("hour"));
            setSun_start_min((Integer) geHourMinMeridian(sun_start_time.toString()).get("min"));
            setSun_start_meridian((String) geHourMinMeridian(sun_start_time.toString()).get("meridian"));
        }
    }

    public String getSun_end_time() {
        return sun_end_time;
    }

    public void setSun_end_time(String sun_end_time) {
        this.sun_end_time = sun_end_time;
        if (sun_end_time != null) {
            setSun_end_hour((Integer) geHourMinMeridian(sun_end_time.toString()).get("hour"));
            setSun_end_min((Integer) geHourMinMeridian(sun_end_time.toString()).get("min"));
            setSun_end_meridian((String) geHourMinMeridian(sun_end_time.toString()).get("meridian"));
        }
    }

    public String getSun_break_time_1() {
        return sun_break_time_1;
    }

    public void setSun_break_time_1(String sun_break_time_1) {
        this.sun_break_time_1 = sun_break_time_1;
        if (sun_break_time_1 != null) {
            setSun_break_time_hour((Integer) geHourMinMeridian(sun_break_time_1.toString()).get("hour"));
            setSun_break_time_min((Integer) geHourMinMeridian(sun_break_time_1.toString()).get("min"));
            setSun_break_time_meridian((String) geHourMinMeridian(sun_break_time_1.toString()).get("meridian"));
            setIs_sun_no_break_time("N");
        }
    }

    public Integer getSun_break_time_1_mins() {
        return sun_break_time_1_mins;
    }

    public void setSun_break_time_1_mins(Integer sun_break_time_1_mins) {
        this.sun_break_time_1_mins = sun_break_time_1_mins;
    }

    public String getMon_start_time() {
        return mon_start_time;
    }

    public void setMon_start_time(String mon_start_time) {
        this.mon_start_time = mon_start_time;

        if (mon_start_time != null){
            setMon_start_hour((Integer) geHourMinMeridian(mon_start_time.toString()).get("hour"));
            setMon_start_min((Integer) geHourMinMeridian(mon_start_time.toString()).get("min"));
            setMon_start_meridian((String) geHourMinMeridian(mon_start_time.toString()).get("meridian"));
        }
    }

    public String getMon_end_time() {
        return mon_end_time;
    }

    public void setMon_end_time(String mon_end_time) {
        this.mon_end_time = mon_end_time;
        if (mon_end_time != null) {
            setMon_end_hour((Integer) geHourMinMeridian(mon_end_time.toString()).get("hour"));
            setMon_end_min((Integer) geHourMinMeridian(mon_end_time.toString()).get("min"));
            setMon_end_meridian((String) geHourMinMeridian(mon_end_time.toString()).get("meridian"));
        }
    }

    public String getMon_break_time_1() {
        return mon_break_time_1;
    }

    public void setMon_break_time_1(String mon_break_time_1) {
        this.mon_break_time_1 = mon_break_time_1;
        if (mon_break_time_1 != null) {
            setMon_break_time_hour((Integer) geHourMinMeridian(mon_break_time_1.toString()).get("hour"));
            setMon_break_time_min((Integer) geHourMinMeridian(mon_break_time_1.toString()).get("min"));
            setMon_break_time_meridian((String) geHourMinMeridian(mon_break_time_1.toString()).get("meridian"));
            setIs_mon_no_break_time("N");

        }
    }

    public Integer getMon_break_time_1_mins() {
        return mon_break_time_1_mins;
    }

    public void setMon_break_time_1_mins(Integer mon_break_time_1_mins) {
        this.mon_break_time_1_mins = mon_break_time_1_mins;
    }

    public String getTue_start_time() {
        return tue_start_time;
    }

    public void setTue_start_time(String tue_start_time) {
        this.tue_start_time = tue_start_time;
        if (tue_start_time != null) {
            setTue_start_hour((Integer) geHourMinMeridian(tue_start_time.toString()).get("hour"));
            setTue_start_min((Integer) geHourMinMeridian(tue_start_time.toString()).get("min"));
            setTue_start_meridian((String) geHourMinMeridian(tue_start_time.toString()).get("meridian"));
        }
    }

    public String getTue_end_time() {
        return tue_end_time;
    }

    public void setTue_end_time(String tue_end_time) {
        this.tue_end_time = tue_end_time;
        if (tue_end_time != null) {
            setTue_end_hour((Integer) geHourMinMeridian(tue_end_time.toString()).get("hour"));
            setTue_end_min((Integer) geHourMinMeridian(tue_end_time.toString()).get("min"));
            setTue_end_meridian((String) geHourMinMeridian(tue_end_time.toString()).get("meridian"));
        }
    }

    public String getTue_break_time_1() {
        return tue_break_time_1;
    }

    public void setTue_break_time_1(String tue_break_time_1) {
        this.tue_break_time_1 = tue_break_time_1;
        if (tue_break_time_1 != null) {
            setTue_break_time_hour((Integer) geHourMinMeridian(tue_break_time_1.toString()).get("hour"));
            setTue_break_time_min((Integer) geHourMinMeridian(tue_break_time_1.toString()).get("min"));
            setTue_break_time_meridian((String) geHourMinMeridian(tue_break_time_1.toString()).get("meridian"));
            setIs_tue_no_break_time("N");

        }
    }

    public Integer getTue_break_time_1_mins() {
        return tue_break_time_1_mins;
    }

    public void setTue_break_time_1_mins(Integer tue_break_time_1_mins) {
        this.tue_break_time_1_mins = tue_break_time_1_mins;
    }

    public String getWed_start_time() {
        return wed_start_time;
    }

    public void setWed_start_time(String wed_start_time) {
        this.wed_start_time = wed_start_time;

        if (wed_start_time != null) {
            setWed_start_hour((Integer) geHourMinMeridian(wed_start_time.toString()).get("hour"));
            setWed_start_min((Integer) geHourMinMeridian(wed_start_time.toString()).get("min"));
            setWed_start_meridian((String) geHourMinMeridian(wed_start_time.toString()).get("meridian"));
        }
    }

    public String getWed_end_time() {
        return wed_end_time;
    }

    public void setWed_end_time(String wed_end_time) {
        this.wed_end_time = wed_end_time;
        if (wed_end_time != null) {
            setWed_end_hour((Integer) geHourMinMeridian(wed_end_time.toString()).get("hour"));
            setWed_end_min((Integer) geHourMinMeridian(wed_end_time.toString()).get("min"));
            setWed_end_meridian((String) geHourMinMeridian(wed_end_time.toString()).get("meridian"));
        }
    }

    public String getWed_break_time_1() {
        return wed_break_time_1;
    }

    public void setWed_break_time_1(String wed_break_time_1) {
        this.wed_break_time_1 = wed_break_time_1;
        if (wed_break_time_1 != null) {
            setWed_break_time_hour((Integer) geHourMinMeridian(wed_break_time_1.toString()).get("hour"));
            setWed_break_time_min((Integer) geHourMinMeridian(wed_break_time_1.toString()).get("min"));
            setWed_break_time_meridian((String) geHourMinMeridian(wed_break_time_1.toString()).get("meridian"));
            setIs_wed_no_break_time("N");

        }
    }

    public Integer getWed_break_time_1_mins() {
        return wed_break_time_1_mins;
    }

    public void setWed_break_time_1_mins(Integer wed_break_time_1_mins) {
        this.wed_break_time_1_mins = wed_break_time_1_mins;
    }

    public String getThu_start_time() {
        return thu_start_time;
    }

    public void setThu_start_time(String thu_start_time) {
        this.thu_start_time = thu_start_time;

        if (thu_start_time != null) {
            setThu_start_hour((Integer) geHourMinMeridian(thu_start_time.toString()).get("hour"));
            setThu_start_min((Integer) geHourMinMeridian(thu_start_time.toString()).get("min"));
            setThu_start_meridian((String) geHourMinMeridian(thu_start_time.toString()).get("meridian"));
        }
    }

    public String getThu_end_time() {
        return thu_end_time;
    }

    public void setThu_end_time(String thu_end_time) {
        this.thu_end_time = thu_end_time;
        if (thu_end_time != null) {
            setThu_end_hour((Integer) geHourMinMeridian(thu_end_time.toString()).get("hour"));
            setThu_end_min((Integer) geHourMinMeridian(thu_end_time.toString()).get("min"));
            setThu_end_meridian((String) geHourMinMeridian(thu_end_time.toString()).get("meridian"));
        }
    }

    public String getThu_break_time_1() {
        return thu_break_time_1;
    }

    public void setThu_break_time_1(String thu_break_time_1) {
        this.thu_break_time_1 = thu_break_time_1;
        if (thu_break_time_1 != null) {
            setThu_break_time_hour((Integer) geHourMinMeridian(thu_break_time_1.toString()).get("hour"));
            setThu_break_time_min((Integer) geHourMinMeridian(thu_break_time_1.toString()).get("min"));
            setThu_break_time_meridian((String) geHourMinMeridian(thu_break_time_1.toString()).get("meridian"));
            setIs_thu_no_break_time("N");

        }
    }

    public Integer getThu_break_time_1_mins() {
        return thu_break_time_1_mins;
    }

    public void setThu_break_time_1_mins(Integer thu_break_time_1_mins) {
        this.thu_break_time_1_mins = thu_break_time_1_mins;
    }

    public String getFri_start_time() {
        return fri_start_time;
    }

    public void setFri_start_time(String fri_start_time) {
        this.fri_start_time = fri_start_time;
        if (fri_start_time != null) {
            setFri_start_hour((Integer) geHourMinMeridian(fri_start_time.toString()).get("hour"));
            setFri_start_min((Integer) geHourMinMeridian(fri_start_time.toString()).get("min"));
            setFri_start_meridian((String) geHourMinMeridian(fri_start_time.toString()).get("meridian"));
        }
    }

    public String getFri_end_time() {
        return fri_end_time;
    }

    public void setFri_end_time(String fri_end_time) {
        this.fri_end_time = fri_end_time;
        if (fri_end_time != null) {
            setFri_end_hour((Integer) geHourMinMeridian(fri_end_time.toString()).get("hour"));
            setFri_end_min((Integer) geHourMinMeridian(fri_end_time.toString()).get("min"));
            setFri_end_meridian((String) geHourMinMeridian(fri_end_time.toString()).get("meridian"));
        }
    }

    public String getFri_break_time_1() {
        return fri_break_time_1;
    }

    public void setFri_break_time_1(String fri_break_time_1) {
        this.fri_break_time_1 = fri_break_time_1;
        if (fri_break_time_1 != null) {
            setFri_break_time_hour((Integer) geHourMinMeridian(fri_break_time_1.toString()).get("hour"));
            setFri_break_time_min((Integer) geHourMinMeridian(fri_break_time_1.toString()).get("min"));
            setFri_break_time_meridian((String) geHourMinMeridian(fri_break_time_1.toString()).get("meridian"));
            setIs_fri_no_break_time("N");

        }
    }

    public Integer getFri_break_time_1_mins() {
        return fri_break_time_1_mins;
    }

    public void setFri_break_time_1_mins(Integer fri_break_time_1_mins) {
        this.fri_break_time_1_mins = fri_break_time_1_mins;
    }

    public String getSat_start_time() {
        return sat_start_time;
    }

    public void setSat_start_time(String sat_start_time) {
        this.sat_start_time = sat_start_time;
        if (sat_start_time != null) {
            setSat_start_hour((Integer) geHourMinMeridian(sat_start_time.toString()).get("hour"));
            setSat_start_min((Integer) geHourMinMeridian(sat_start_time.toString()).get("min"));
            setSat_start_meridian((String) geHourMinMeridian(sat_start_time.toString()).get("meridian"));
        }
    }

    public String getSat_end_time() {
        return sat_end_time;
    }

    public void setSat_end_time(String sat_end_time) {
        this.sat_end_time = sat_end_time;
        if (sat_end_time != null) {
            setSat_end_hour((Integer) geHourMinMeridian(sat_end_time.toString()).get("hour"));
            setSat_end_min((Integer) geHourMinMeridian(sat_end_time.toString()).get("min"));
            setSat_end_meridian((String) geHourMinMeridian(sat_end_time.toString()).get("meridian"));
        }
    }

    public String getSat_break_time_1() {
        return sat_break_time_1;
    }

    public void setSat_break_time_1(String sat_break_time_1) {
        this.sat_break_time_1 = sat_break_time_1;
        if (sat_break_time_1 != null) {
            setSat_break_time_hour((Integer) geHourMinMeridian(sat_break_time_1.toString()).get("hour"));
            setSat_break_time_min((Integer) geHourMinMeridian(sat_break_time_1.toString()).get("min"));
            setSat_break_time_meridian((String) geHourMinMeridian(sat_break_time_1.toString()).get("meridian"));
            setIs_sat_no_break_time("N");

        }
    }

    public Integer getSat_break_time_1_mins() {
        return sat_break_time_1_mins;
    }

    public void setSat_break_time_1_mins(Integer sat_break_time_1_mins) {
        this.sat_break_time_1_mins = sat_break_time_1_mins;
    }

    public String getSun_break_time_str() {
        return sun_break_time_str;
    }

    public void setSun_break_time_str(String sun_break_time_str) {
        this.sun_break_time_str = sun_break_time_str;
    }

    public String getMon_break_time_str() {
        return mon_break_time_str;
    }

    public void setMon_break_time_str(String mon_break_time_str) {
        this.mon_break_time_str = mon_break_time_str;
    }

    public String getTue_break_time_str() {
        return tue_break_time_str;
    }

    public void setTue_break_time_str(String tue_break_time_str) {
        this.tue_break_time_str = tue_break_time_str;
    }

    public String getWed_break_time_str() {
        return wed_break_time_str;
    }

    public void setWed_break_time_str(String wed_break_time_str) {
        this.wed_break_time_str = wed_break_time_str;
    }

    public String getThu_break_time_str() {
        return thu_break_time_str;
    }

    public void setThu_break_time_str(String thu_break_time_str) {
        this.thu_break_time_str = thu_break_time_str;
    }

    public String getFri_break_time_str() {
        return fri_break_time_str;
    }

    public void setFri_break_time_str(String fri_break_time_str) {
        this.fri_break_time_str = fri_break_time_str;
    }

    public String getSat_break_time_str() {
        return sat_break_time_str;
    }

    public void setSat_break_time_str(String sat_break_time_str) {
        this.sat_break_time_str = sat_break_time_str;
    }

    public void setSat_start_min(Integer sat_start_min) {
        this.sat_start_min = sat_start_min;
    }

    public Integer getSat_start_min() {
        return sat_start_min;
    }

    public void setSat_start_meridian(String sat_start_meridian) {
        this.sat_start_meridian = sat_start_meridian;
    }

    public String getSat_start_meridian() {
        return sat_start_meridian;
    }

    public void setSat_end_min(Integer sat_end_min) {
        this.sat_end_min = sat_end_min;
    }

    public Integer getSat_end_min() {
        return sat_end_min;
    }

    public void setSat_end_meridian(String sat_end_meridian) {
        this.sat_end_meridian = sat_end_meridian;
    }

    public String getSat_end_meridian() {
        return sat_end_meridian;
    }

    public void setIs_sun_open(String is_sun_open) {
        this.is_sun_open = is_sun_open;
    }

    public String getIs_sun_open() {
        return is_sun_open;
    }

    public void setSun_start_min(Integer sun_start_min) {
        this.sun_start_min = sun_start_min;
    }

    public Integer getSun_start_min() {
        return sun_start_min;
    }

    public void setSun_start_meridian(String sun_start_meridian) {
        this.sun_start_meridian = sun_start_meridian;
    }

    public String getSun_start_meridian() {
        return sun_start_meridian;
    }

    public void setSun_end_min(Integer sun_end_min) {
        this.sun_end_min = sun_end_min;
    }

    public Integer getSun_end_min() {
        return sun_end_min;
    }

    public void setSun_end_meridian(String sun_end_meridian) {
        this.sun_end_meridian = sun_end_meridian;
    }

    public String getSun_end_meridian() {
        return sun_end_meridian;
    }

    public void setSun_start_hour(Integer sun_start_hour) {
        this.sun_start_hour = sun_start_hour;
    }

    public Integer getSun_start_hour() {
        return sun_start_hour;
    }

    public void setSun_end_hour(Integer sun_end_hour) {
        this.sun_end_hour = sun_end_hour;
    }

    public Integer getSun_end_hour() {
        return sun_end_hour;
    }

    public void setSat_end_hour(Integer sat_end_hour) {
        this.sat_end_hour = sat_end_hour;
    }

    public Integer getSat_end_hour() {
        return sat_end_hour;
    }

    public void setSat_start_hour(Integer sat_start_hour) {
        this.sat_start_hour = sat_start_hour;
    }

    public Integer getSat_start_hour() {
        return sat_start_hour;
    }

    public void setMon_start_hour(Integer mon_start_hour) {
        this.mon_start_hour = mon_start_hour;
    }

    public Integer getMon_start_hour() {
        return mon_start_hour;
    }

    public void setMon_start_min(Integer mon_start_min) {
        this.mon_start_min = mon_start_min;
    }

    public Integer getMon_start_min() {
        return mon_start_min;
    }

    public void setMon_start_meridian(String mon_start_meridian) {
        this.mon_start_meridian = mon_start_meridian;
    }

    public String getMon_start_meridian() {
        return mon_start_meridian;
    }

    public void setMon_end_hour(Integer mon_end_hour) {
        this.mon_end_hour = mon_end_hour;
    }

    public Integer getMon_end_hour() {
        return mon_end_hour;
    }

    public void setMon_end_min(Integer mon_end_min) {
        this.mon_end_min = mon_end_min;
    }

    public Integer getMon_end_min() {
        return mon_end_min;
    }

    public void setMon_end_meridian(String mon_end_meridian) {
        this.mon_end_meridian = mon_end_meridian;
    }

    public String getMon_end_meridian() {
        return mon_end_meridian;
    }

    public void setTue_start_hour(Integer tue_start_hour) {
        this.tue_start_hour = tue_start_hour;
    }

    public Integer getTue_start_hour() {
        return tue_start_hour;
    }

    public void setTue_start_min(Integer tue_start_min) {
        this.tue_start_min = tue_start_min;
    }

    public Integer getTue_start_min() {
        return tue_start_min;
    }

    public void setTue_start_meridian(String tue_start_meridian) {
        this.tue_start_meridian = tue_start_meridian;
    }

    public String getTue_start_meridian() {
        return tue_start_meridian;
    }

    public void setTue_end_hour(Integer tue_end_hour) {
        this.tue_end_hour = tue_end_hour;
    }

    public Integer getTue_end_hour() {
        return tue_end_hour;
    }

    public void setTue_end_min(Integer tue_end_min) {
        this.tue_end_min = tue_end_min;
    }

    public Integer getTue_end_min() {
        return tue_end_min;
    }

    public void setTue_end_meridian(String tue_end_meridian) {
        this.tue_end_meridian = tue_end_meridian;
    }

    public String getTue_end_meridian() {
        return tue_end_meridian;
    }

    public void setWed_start_hour(Integer wed_start_hour) {
        this.wed_start_hour = wed_start_hour;
    }

    public Integer getWed_start_hour() {
        return wed_start_hour;
    }

    public void setWed_start_min(Integer wed_start_min) {
        this.wed_start_min = wed_start_min;
    }

    public Integer getWed_start_min() {
        return wed_start_min;
    }

    public void setWed_start_meridian(String wed_start_meridian) {
        this.wed_start_meridian = wed_start_meridian;
    }

    public String getWed_start_meridian() {
        return wed_start_meridian;
    }

    public void setWed_end_hour(Integer wed_end_hour) {
        this.wed_end_hour = wed_end_hour;
    }

    public Integer getWed_end_hour() {
        return wed_end_hour;
    }

    public void setWed_end_min(Integer wed_end_min) {
        this.wed_end_min = wed_end_min;
    }

    public Integer getWed_end_min() {
        return wed_end_min;
    }

    public void setWed_end_meridian(String wed_end_meridian) {
        this.wed_end_meridian = wed_end_meridian;
    }

    public String getWed_end_meridian() {
        return wed_end_meridian;
    }

    public void setThu_start_hour(Integer thu_start_hour) {
        this.thu_start_hour = thu_start_hour;
    }

    public Integer getThu_start_hour() {
        return thu_start_hour;
    }

    public void setThu_start_min(Integer thu_start_min) {
        this.thu_start_min = thu_start_min;
    }

    public Integer getThu_start_min() {
        return thu_start_min;
    }

    public void setThu_start_meridian(String thu_start_meridian) {
        this.thu_start_meridian = thu_start_meridian;
    }

    public String getThu_start_meridian() {
        return thu_start_meridian;
    }

    public void setThu_end_hour(Integer thu_end_hour) {
        this.thu_end_hour = thu_end_hour;
    }

    public Integer getThu_end_hour() {
        return thu_end_hour;
    }

    public void setThu_end_min(Integer thu_end_min) {
        this.thu_end_min = thu_end_min;
    }

    public Integer getThu_end_min() {
        return thu_end_min;
    }

    public void setThu_end_meridian(String thu_end_meridian) {
        this.thu_end_meridian = thu_end_meridian;
    }

    public String getThu_end_meridian() {
        return thu_end_meridian;
    }

    public void setFri_start_hour(Integer fri_start_hour) {
        this.fri_start_hour = fri_start_hour;
    }

    public Integer getFri_start_hour() {
        return fri_start_hour;
    }

    public void setFri_start_min(Integer fri_start_min) {
        this.fri_start_min = fri_start_min;
    }

    public Integer getFri_start_min() {
        return fri_start_min;
    }

    public void setFri_start_meridian(String fri_start_meridian) {
        this.fri_start_meridian = fri_start_meridian;
    }

    public String getFri_start_meridian() {
        return fri_start_meridian;
    }

    public void setFri_end_hour(Integer fri_end_hour) {
        this.fri_end_hour = fri_end_hour;
    }

    public Integer getFri_end_hour() {
        return fri_end_hour;
    }

    public void setFri_end_min(Integer fri_end_min) {
        this.fri_end_min = fri_end_min;
    }

    public Integer getFri_end_min() {
        return fri_end_min;
    }

    public void setFri_end_meridian(String fri_end_meridian) {
        this.fri_end_meridian = fri_end_meridian;
    }

    public String getFri_end_meridian() {
        return fri_end_meridian;
    }

    public void setIs_mon_open(String is_mon_open) {
        this.is_mon_open = is_mon_open;
    }

    public String getIs_mon_open() {
        return is_mon_open;
    }

    public void setIs_tue_open(String is_tue_open) {
        this.is_tue_open = is_tue_open;
    }

    public String getIs_tue_open() {
        return is_tue_open;
    }

    public void setIs_wed_open(String is_wed_open) {
        this.is_wed_open = is_wed_open;
    }

    public String getIs_wed_open() {
        return is_wed_open;
    }

    public void setIs_thu_open(String is_thu_open) {
        this.is_thu_open = is_thu_open;
    }

    public String getIs_thu_open() {
        return is_thu_open;
    }

    public void setIs_fri_open(String is_fri_open) {
        this.is_fri_open = is_fri_open;
    }

    public String getIs_fri_open() {
        return is_fri_open;
    }

    public void setIs_sat_open(String is_sat_open) {
        this.is_sat_open = is_sat_open;
    }

    public String getIs_sat_open() {
        return is_sat_open;
    }

    public void setMon_break_time_hour(Integer mon_break_time_hour) {
        this.mon_break_time_hour = mon_break_time_hour;
    }

    public Integer getMon_break_time_hour() {
        return mon_break_time_hour;
    }

    public void setMon_break_time_min(Integer mon_break_time_min) {
        this.mon_break_time_min = mon_break_time_min;
    }

    public Integer getMon_break_time_min() {
        return mon_break_time_min;
    }

    public void setMon_break_time_meridian(String mon_break_time_meridian) {
        this.mon_break_time_meridian = mon_break_time_meridian;
    }

    public String getMon_break_time_meridian() {
        return mon_break_time_meridian;
    }

    public void setTue_break_time_hour(Integer tue_break_time_hour) {
        this.tue_break_time_hour = tue_break_time_hour;
    }

    public Integer getTue_break_time_hour() {
        return tue_break_time_hour;
    }

    public void setTue_break_time_min(Integer tue_break_time_min) {
        this.tue_break_time_min = tue_break_time_min;
    }

    public Integer getTue_break_time_min() {
        return tue_break_time_min;
    }

    public void setTue_break_time_meridian(String tue_break_time_meridian) {
        this.tue_break_time_meridian = tue_break_time_meridian;
    }

    public String getTue_break_time_meridian() {
        return tue_break_time_meridian;
    }

    public void setWed_break_time_hour(Integer wed_break_time_hour) {
        this.wed_break_time_hour = wed_break_time_hour;
    }

    public Integer getWed_break_time_hour() {
        return wed_break_time_hour;
    }

    public void setWed_break_time_min(Integer wed_break_time_min) {
        this.wed_break_time_min = wed_break_time_min;
    }

    public Integer getWed_break_time_min() {
        return wed_break_time_min;
    }

    public void setWed_break_time_meridian(String wed_break_time_meridian) {
        this.wed_break_time_meridian = wed_break_time_meridian;
    }

    public String getWed_break_time_meridian() {
        return wed_break_time_meridian;
    }

    public void setThu_break_time_hour(Integer thu_break_time_hour) {
        this.thu_break_time_hour = thu_break_time_hour;
    }

    public Integer getThu_break_time_hour() {
        return thu_break_time_hour;
    }

    public void setThu_break_time_min(Integer thu_break_time_min) {
        this.thu_break_time_min = thu_break_time_min;
    }

    public Integer getThu_break_time_min() {
        return thu_break_time_min;
    }

    public void setThu_break_time_meridian(String thu_break_time_meridian) {
        this.thu_break_time_meridian = thu_break_time_meridian;
    }

    public String getThu_break_time_meridian() {
        return thu_break_time_meridian;
    }

    public void setFri_break_time_hour(Integer fri_break_time_hour) {
        this.fri_break_time_hour = fri_break_time_hour;
    }

    public Integer getFri_break_time_hour() {
        return fri_break_time_hour;
    }

    public void setFri_break_time_min(Integer fri_break_time_min) {
        this.fri_break_time_min = fri_break_time_min;
    }

    public Integer getFri_break_time_min() {
        return fri_break_time_min;
    }

    public void setFri_break_time_meridian(String fri_break_time_meridian) {
        this.fri_break_time_meridian = fri_break_time_meridian;
    }

    public String getFri_break_time_meridian() {
        return fri_break_time_meridian;
    }

    public void setSat_break_time_hour(Integer sat_break_time_hour) {
        this.sat_break_time_hour = sat_break_time_hour;
    }

    public Integer getSat_break_time_hour() {
        return sat_break_time_hour;
    }

    public void setSat_break_time_min(Integer sat_break_time_min) {
        this.sat_break_time_min = sat_break_time_min;
    }

    public Integer getSat_break_time_min() {
        return sat_break_time_min;
    }

    public void setSat_break_time_meridian(String sat_break_time_meridian) {
        this.sat_break_time_meridian = sat_break_time_meridian;
    }

    public String getSat_break_time_meridian() {
        return sat_break_time_meridian;
    }

    public void setSun_break_time_hour(Integer sun_break_time_hour) {
        this.sun_break_time_hour = sun_break_time_hour;
    }

    public Integer getSun_break_time_hour() {
        return sun_break_time_hour;
    }

    public void setSun_break_time_min(Integer sun_break_time_min) {
        this.sun_break_time_min = sun_break_time_min;
    }

    public Integer getSun_break_time_min() {
        return sun_break_time_min;
    }

    public void setSun_break_time_meridian(String sun_break_time_meridian) {
        this.sun_break_time_meridian = sun_break_time_meridian;
    }

    public String getSun_break_time_meridian() {
        return sun_break_time_meridian;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getIs_mon_no_break_time() {
        return is_mon_no_break_time;
    }

    public void setIs_mon_no_break_time(String is_mon_no_break_time) {
        this.is_mon_no_break_time = is_mon_no_break_time;
    }

    public String getIs_tue_no_break_time() {
        return is_tue_no_break_time;
    }

    public void setIs_tue_no_break_time(String is_tue_no_break_time) {
        this.is_tue_no_break_time = is_tue_no_break_time;
    }

    public String getIs_wed_no_break_time() {
        return is_wed_no_break_time;
    }

    public void setIs_wed_no_break_time(String is_wed_no_break_time) {
        this.is_wed_no_break_time = is_wed_no_break_time;
    }

    public String getIs_thu_no_break_time() {
        return is_thu_no_break_time;
    }

    public void setIs_thu_no_break_time(String is_thu_no_break_time) {
        this.is_thu_no_break_time = is_thu_no_break_time;
    }

    public String getIs_fri_no_break_time() {
        return is_fri_no_break_time;
    }

    public void setIs_fri_no_break_time(String is_fri_no_break_time) {
        this.is_fri_no_break_time = is_fri_no_break_time;
    }

    public String getIs_sat_no_break_time() {
        return is_sat_no_break_time;
    }

    public void setIs_sat_no_break_time(String is_sat_no_break_time) {
        this.is_sat_no_break_time = is_sat_no_break_time;
    }

    public void setIs_sun_no_break_time(String is_sun_no_break_time) {
        this.is_sun_no_break_time = is_sun_no_break_time;
    }

    public String getIs_sun_no_break_time() {
        return is_sun_no_break_time;
    }

    public String getDefault_is_sun_open() {
        return default_is_sun_open;
    }

    public void setDefault_is_sun_open(String default_is_sun_open) {
        this.default_is_sun_open = default_is_sun_open;
    }

    public String getDefault_is_mon_open() {
        return default_is_mon_open;
    }

    public void setDefault_is_mon_open(String default_is_mon_open) {
        this.default_is_mon_open = default_is_mon_open;
    }

    public String getDefault_is_tue_open() {
        return default_is_tue_open;
    }

    public void setDefault_is_tue_open(String default_is_tue_open) {
        this.default_is_tue_open = default_is_tue_open;
    }

    public String getDefault_is_wed_open() {
        return default_is_wed_open;
    }

    public void setDefault_is_wed_open(String default_is_wed_open) {
        this.default_is_wed_open = default_is_wed_open;
    }

    public String getDefault_is_thu_open() {
        return default_is_thu_open;
    }

    public void setDefault_is_thu_open(String default_is_thu_open) {
        this.default_is_thu_open = default_is_thu_open;
    }

    public String getDefault_is_fri_open() {
        return default_is_fri_open;
    }

    public void setDefault_is_fri_open(String default_is_fri_open) {
        this.default_is_fri_open = default_is_fri_open;
    }

    public String getDefault_is_sat_open() {
        return default_is_sat_open;
    }

    public void setDefault_is_sat_open(String default_is_sat_open) {
        this.default_is_sat_open = default_is_sat_open;
    }

    public void setBlockTimeInMins(Integer blockTimeInMins) {
        this.blockTimeInMins = blockTimeInMins;
    }

    public Integer getBlockTimeInMins() {
        return blockTimeInMins;
    }
}

	