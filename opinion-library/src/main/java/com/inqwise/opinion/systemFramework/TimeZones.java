package com.inqwise.opinion.systemFramework;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TimeZone;

public class TimeZones {
	private static final String TIMEZONE_ID_PREFIXES =
	      "^(Africa|America|Asia|Atlantic|Australia|Europe|Indian|Pacific)/.*";

	   private static List<TimeZone> timeZones = null;

	   public static List<TimeZone> getTimeZones() {
	      if (timeZones == null) {
	         initTimeZones();
	      }
	      return timeZones;
	   }

	   private static void initTimeZones() {
	      timeZones = new ArrayList<TimeZone>();
	      final String[] timeZoneIds = TimeZone.getAvailableIDs();
	      for (final String id : timeZoneIds) {
	         if (id.matches(TIMEZONE_ID_PREFIXES)) {
	            timeZones.add(TimeZone.getTimeZone(id));
	         }
	      }
	      Collections.sort(timeZones, new Comparator<TimeZone>() {
	         public int compare(final TimeZone a, final TimeZone b) {
	            return a.getID().compareTo(b.getID());
	         }
	      });
	   }
}
