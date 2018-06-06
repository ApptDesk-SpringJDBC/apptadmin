package com.telappoint.apptadmin.service.impl;


import com.google.common.collect.Lists;
import com.telappoint.apptadmin.form.CalendarRequest;
import com.telappoint.apptadmin.model.MonthlyCalendar;
import com.telappoint.apptadmin.model.PerDateAppts;
import com.telappoint.apptadmin.model.ResourceData;
import com.telappoint.apptadmin.model.monthlycalendar.MonthlyCalendarData;
import com.telappoint.apptadmin.model.monthlycalendar.MonthlyCalendarResponse;
import com.telappoint.apptadmin.service.MonthlyCalenderService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class MonthlyCalenderServiceImpl implements MonthlyCalenderService {

    @Override
    public MonthlyCalendar getMonthlyCalendar(final MonthlyCalendarResponse response, final CalendarRequest calendarRequest) {
        final MonthlyCalendar monthlyCalendar = new MonthlyCalendar();
        monthlyCalendar.setMonthlyCalendarDataRowsColumns(
                Lists.partition(getMonthlyCalanderDataList(response, monthlyCalendar, calendarRequest), 7));
        monthlyCalendar.setCurrentDate(getCurrentDate());
        monthlyCalendar.setShowAllResourceCount(calendarRequest.isShowAllResourceCount());
        return monthlyCalendar;
    }

    private List<MonthlyCalendarData> getMonthlyCalanderDataList(final MonthlyCalendarResponse response, final MonthlyCalendar monthlyCalendar, final CalendarRequest calendarRequest) {
        final Map<Integer, Integer> doctorColorIdMap = getDoctorColorIdMap(calendarRequest);
        final List<MonthlyCalendarData> monthlyCalendarDataList = response.getPerDateAppts().entrySet()
                .stream()
                .map(perdatatAppt -> {
                    final int calenderDayOfWeek = getCalenderDayOfWeek(perdatatAppt.getKey());
                    final int calendarWeekOfMonth = getCalendarWeekOfMonth(perdatatAppt.getKey());
                    final MonthlyCalendarData monthlyCalendarData = new MonthlyCalendarData();
                    monthlyCalendarData.setCalendarDate(perdatatAppt.getKey());
                    monthlyCalendarData.setNotAvailable(isNotAvailable(perdatatAppt));
                    monthlyCalendarData.setCalendarDayOfWeek(calenderDayOfWeek);
                    monthlyCalendarData.setCalendarWeekOfMonth(calendarWeekOfMonth);
                    monthlyCalendarData.setDay(getDay(perdatatAppt.getKey()));
                    if(calendarRequest.isShowAllResourceCount()){
                        final long bookedApptCount = perdatatAppt.getValue().getResourceDataList()
                                .stream()
                                .mapToLong(resourceData -> resourceData.getNumberOfBookedAppts())
                                .sum();
                        final long openSlots = perdatatAppt.getValue().getResourceDataList()
                                .stream()
                                .mapToLong(resourceData -> resourceData.getNumberOfOpenSlots())
                                .sum();
                        final ResourceData resourceData = new ResourceData();
                        resourceData.setNumberOfBookedAppts(bookedApptCount);
                        resourceData.setNumberOfOpenSlots(openSlots);
                        monthlyCalendarData.setResourceDataList(Arrays.asList(resourceData));
                    }else{
                        monthlyCalendarData.setResourceDataList(perdatatAppt.getValue().getResourceDataList());
                    }
                    setResourceDataListColorId(monthlyCalendarData.getResourceDataList(), doctorColorIdMap, calendarRequest);
                    return monthlyCalendarData;
                }).collect(Collectors.toList());
        monthlyCalendar.setMonthlyCalendarDataList(monthlyCalendarDataList);

        final List<MonthlyCalendarData> monthlyCalendarDatas = new ArrayList<>();
        monthlyCalendarDatas.addAll(fillGapsFirst(monthlyCalendarDataList));
        monthlyCalendarDatas.addAll(monthlyCalendarDataList);
        monthlyCalendarDatas.addAll(fillGapsEnd(monthlyCalendarDataList));
        return monthlyCalendarDatas;
    }

    private void setResourceDataListColorId(final List<ResourceData> resourceDataList,
                                            final Map<Integer, Integer> doctorColorIdMap, final CalendarRequest request){
        if(request.isShowAllResourceCount()){
            resourceDataList.stream().forEach(resourceData -> {
                resourceData.setColorId(10);
            });
        }else{
            resourceDataList.stream().forEach(resourceData -> {
                resourceData.setColorId(doctorColorIdMap.get(resourceData.getResourceId()));
            });
        }
    }

    private List<MonthlyCalendarData> fillGapsFirst(final List<MonthlyCalendarData> monthlyCalendarDatas){
        final int gap = monthlyCalendarDatas.get(0).getCalendarDayOfWeek() != 7 ? monthlyCalendarDatas.get(0).getCalendarDayOfWeek() : 0;
        final List<MonthlyCalendarData> list = new ArrayList<>();
        IntStream.range(0, gap).forEach(i -> {
            final MonthlyCalendarData monthlyCalendarData = new MonthlyCalendarData();
            monthlyCalendarData.setNotAvailable(true);
            monthlyCalendarData.setDisabledField(true);
            monthlyCalendarData.setDay(getPastDay(monthlyCalendarDatas.get(0).getCalendarDate(), gap-i));
            list.add(monthlyCalendarData);
        });
        return list;
    }

    private int getPastDay(final String calendarDate, final int daysToGoBack){
        final LocalDate date = LocalDate.parse(calendarDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return date.minusDays(daysToGoBack).getDayOfMonth();
    }

    private int getFutureDay(final String calendarDate, final int daysToGoBack){
        final LocalDate date = LocalDate.parse(calendarDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return date.plusDays(daysToGoBack).getDayOfMonth();
    }

    private List<MonthlyCalendarData> fillGapsEnd(final List<MonthlyCalendarData> monthlyCalendarDatas){
        final int gap = 7 - monthlyCalendarDatas.get(monthlyCalendarDatas.size() - 1).getCalendarDayOfWeek();
        final List<MonthlyCalendarData> list = new ArrayList<>();
        IntStream.range(1, gap).forEach(i -> {
            final MonthlyCalendarData monthlyCalendarData = new MonthlyCalendarData();
            monthlyCalendarData.setNotAvailable(true);
            monthlyCalendarData.setDisabledField(true);
            monthlyCalendarData.setDay(
                    getFutureDay(monthlyCalendarDatas.get(monthlyCalendarDatas.size() - 1).getCalendarDate(), i));
            list.add(monthlyCalendarData);
        });
        return list;
    }

    private int getDay(final String calendarDate) {
        final LocalDate localDate = LocalDate.parse(calendarDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return localDate.getDayOfMonth();
    }

    private int getCalenderDayOfWeek(final String calendarDate) {
        final LocalDate localDate = LocalDate.parse(calendarDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return localDate.getDayOfWeek().getValue();
    }

    private int getCalendarWeekOfMonth(final String calendarDate) {
        final LocalDate localDate = LocalDate.parse(calendarDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        final WeekFields weekFields = WeekFields.of(Locale.getDefault());
        return localDate.get(weekFields.weekOfMonth());
    }

    private boolean isNotAvailable(final Map.Entry<String, PerDateAppts> perdatatAppt) {
        return "Y".equals(perdatatAppt.getValue().getIsNotAvailable());
    }

    private Map<Integer, Integer> getDoctorColorIdMap(final CalendarRequest request){
        final Map<Integer, Integer> doctorColorMap = new HashMap<>();
        Arrays.asList(request.getColorDoctorMap().split(","))
                .stream()
                .forEach(doctorColorIdMap -> {
                    final String[] splitStr = doctorColorIdMap.split("_");
                    doctorColorMap.put(Integer.valueOf(splitStr[0]), Integer.valueOf(splitStr[1]));
                });
        return doctorColorMap;
    }

    private String getCurrentDate(){
        final LocalDate now = LocalDate.now();
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return now.format(formatter);
    }
}
