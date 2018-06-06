package com.telappoint.apptadmin.utils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.telappoint.apptadmin.model.CalendarData;

@Component
public class CalendarTimeSlotsComponent {

	/**
	 * 
	 * Open time slots - Open time slots prepared based on blocks.
	 * 
	 * @param calendarDataList
	 * @param locationId
	 * @param resourceId
	 * @param serviceId
	 * @param blocks
	 * @param blocksInMins
	 * @param date
	 * @return Map<String,String> = key - resourceId|date|time, value = Open
	 *         time status- open or ""
	 */
	//OpenTimeSlots --> Values = "open","","closed"
	public Map<String, String> getOpenTimeSlots(List<CalendarData> calendarDataList, int locationId, int resourceId, int serviceId, int blocks, int blocksInMins, String date) throws Exception {
		Map<String, String> openTimeSlotsMap = new LinkedHashMap<String, String>();
		boolean isFirstLoop = true;
		int blockCount = blocks;
		int prevTimeSlotsInMins = 0;
		int timeSlotInMins = 0;
		String uptoDateKey = resourceId + "|" + date;
		String fullKey = "";
		boolean consecutiveDiffer = false;
		int size = calendarDataList.size();
		for (int i = 0; i < size; i++) {
			CalendarData calendarData = calendarDataList.get(i);
			String timestr = DateUtils.convert12To24HoursHHMMFormat(calendarData.getTime());
			String apptStatus=calendarData.getApptStatus();
			
			String[] timeStrArr = timestr.split(":");
			//String minutes = timestr.substring(timestr.indexOf(':') + 1).trim();
			String minutes = timeStrArr[1].trim();
			//String hours = timestr.substring(0, 2).trim();
			String hours = timeStrArr[0].trim();
			
			timeSlotInMins = Integer.valueOf(hours) * 60 + Integer.valueOf(minutes);
			fullKey = uptoDateKey + "|" + timestr;

			if ("Open".equalsIgnoreCase(apptStatus)) {
				if (isFirstLoop) {
					openTimeSlotsMap.put(fullKey, "Open");
					blockCount--;
				} else {
					if ((timeSlotInMins - prevTimeSlotsInMins) == blocksInMins) {
						if (blockCount == 0) {
							openTimeSlotsMap.put(fullKey, "Open");
							blockCount = blocks - 1;
						} else {
							openTimeSlotsMap.put(fullKey, "");
							blockCount--;
						}
						consecutiveDiffer = false;
					} else {
						if (consecutiveDiffer) {
							isFirstLoop = true;
							for (int j = 0; j < (blocks - blockCount); j++) {
								blockCount++;
								timeSlotInMins = timeSlotInMins - blocksInMins;
								String key = getSlotKey(timeSlotInMins, uptoDateKey);
								openTimeSlotsMap.put(key, "");
							}
							blockCount = blocks;
						}
						consecutiveDiffer = true;
						openTimeSlotsMap.put(fullKey, "Open");
						blockCount = blocks - 1;
					}
				}
				prevTimeSlotsInMins = timeSlotInMins;
				isFirstLoop = false;
			} else {
				consecutiveDiffer = false;
				if ("NA".equalsIgnoreCase(apptStatus)) {
					openTimeSlotsMap.put(fullKey, "closed");
				}
				isFirstLoop = true;
				if (blockCount > 0)
					for (int j = 0; j < (blocks - blockCount); j++) {
						// blockCount++;
						timeSlotInMins = timeSlotInMins - blocksInMins;
						String key = getSlotKey(timeSlotInMins, uptoDateKey);
						openTimeSlotsMap.put(key, "");
					}
				blockCount = blocks;
			}
		}

		if (blockCount > 0) {
			for (int j = 0; j < (blocks - blockCount); j++) {
				// blockCount++;
				String key = getSlotKey(timeSlotInMins, uptoDateKey);
				timeSlotInMins = timeSlotInMins - blocksInMins;
				openTimeSlotsMap.put(key, "");
			}
		}
		return openTimeSlotsMap;
	}

	/**
	 * Grey time slots for admin to override the the bookings.
	 * 
	 * @param calendarDataList
	 * @param locationId
	 * @param resourceId
	 * @param serviceId
	 * @param blocks
	 * @param blocksInMins
	 * @param date
	 * @return Map<String,String> = key - resourceId|date|time, value = Grey
	 *         time status- open or skip
	 */
	//GreyTimeSlots --> Values = "skip","yellow","Open"
	public Map<String, String> getGreyTimeSlots(List<CalendarData> calendarDataList, Integer locationId, Integer resourceId, Integer serviceId, int blocks, int blocksInMins,
			String date) throws Exception {

		Map<String, String> greyTimeSlotsMap = new LinkedHashMap<String, String>();
		boolean isFirstLoop = true;

		int consecutive = 0;
		boolean consecutiveCheck = false;

		int prevTimeSlotsInMins = 0;
		int timeSlotInMins = 0;
		String uptoDateKey = resourceId + "|" + date;
		String fullKey = "";
		int size = calendarDataList.size();
		for (int i=0;i < size;i++) {
			CalendarData calendarData = calendarDataList.get(i);
			String timestr = DateUtils.convert12To24HoursHHMMFormat(calendarData.getTime());
			String apptStatus=calendarData.getApptStatus();
			
			String[] timeStrArr = timestr.split(":");
			//String minutes = timestr.substring(timestr.indexOf(':') + 1).trim();
			String minutes = timeStrArr[1].trim();
			//String hours = timestr.substring(0, 2).trim();
			String hours = timeStrArr[0].trim();
			
			timeSlotInMins = Integer.valueOf(hours) * 60 + Integer.valueOf(minutes);
			fullKey = uptoDateKey + "|" + timestr;

			if ("Open".equalsIgnoreCase(apptStatus)) {
				if (isFirstLoop) {
					greyTimeSlotsMap.put(fullKey, "skip");
					consecutive++;
					consecutiveCheck = true;
				} else {
					if ((timeSlotInMins - prevTimeSlotsInMins) == blocksInMins) {
						consecutive++;
						consecutiveCheck = true;
					} else {
						consecutiveCheck = false;
						consecutive = 0;
					}

					if (consecutive == blocks + 1 && consecutiveCheck) {
						consecutive--;
						int prevGreyTimeSlot = timeSlotInMins;
						String yellowKey = getSlotKey(prevGreyTimeSlot, uptoDateKey);
						greyTimeSlotsMap.put(yellowKey, "yellow");
						for (int j = 0; j < blocks; j++) {
							prevGreyTimeSlot = prevGreyTimeSlot - blocksInMins;

							// To fill the yellow blocks
							yellowKey = getSlotKey(prevGreyTimeSlot, uptoDateKey);
							greyTimeSlotsMap.put(yellowKey, "yellow");
						}
						String key = getSlotKey(prevGreyTimeSlot, uptoDateKey);
						greyTimeSlotsMap.put(key, "Open");
						if (i + 1 == size) {
							prevGreyTimeSlot = prevGreyTimeSlot + blocksInMins;
							key = getSlotKey(prevGreyTimeSlot, uptoDateKey);
							greyTimeSlotsMap.put(key, "Open");
						}
					}

					// Final slot, if consecutive, we have to fill the yellow
					// blocks.
					int prevGreyTimeSlot = timeSlotInMins;
					if (consecutiveCheck && consecutive == blocks && i + 1 == size) {
						String yellowKey = getSlotKey(prevGreyTimeSlot, uptoDateKey);
						greyTimeSlotsMap.put(yellowKey, "yellow");
						for (int j = 2; j < blocks; j++) {
							prevGreyTimeSlot = prevGreyTimeSlot - blocksInMins;
							yellowKey = getSlotKey(prevGreyTimeSlot, uptoDateKey);
							greyTimeSlotsMap.put(yellowKey, "yellow");
						}
					}
				}
				prevTimeSlotsInMins = timeSlotInMins;
				isFirstLoop = false;
			} else {
				int prevGreyTimeSlot = timeSlotInMins;
				if (consecutiveCheck && consecutive == blocks) {
					String yellowKey = getSlotKey(prevGreyTimeSlot, uptoDateKey);
					greyTimeSlotsMap.put(yellowKey, "yellow");
					for (int j = 0; j < blocks; j++) {
						prevGreyTimeSlot = prevGreyTimeSlot - blocksInMins;
						yellowKey = getSlotKey(prevGreyTimeSlot, uptoDateKey);
						greyTimeSlotsMap.put(yellowKey, "yellow");
					}
					String key = getSlotKey(prevGreyTimeSlot, uptoDateKey);
					greyTimeSlotsMap.put(key, "Open");
				}
				consecutiveCheck = false;
				consecutive = 0;
				isFirstLoop = true;
			}
		}
		return greyTimeSlotsMap;
	}

	private String getSlotKey(int timeSlotInMins, String uptoDateKey) {
		String hrs = String.valueOf(timeSlotInMins / 60);
		String mins = String.valueOf(timeSlotInMins % 60);
		hrs = hrs.length() == 0 ? "00" : hrs.length() == 1 ? "0" + hrs : hrs;
		mins = mins.length() == 0 ? "00" : mins.length() == 1 ? "0" + mins : mins;
		String key = uptoDateKey + "|" + hrs + ":" + mins;
		return key;
	}
	
}
