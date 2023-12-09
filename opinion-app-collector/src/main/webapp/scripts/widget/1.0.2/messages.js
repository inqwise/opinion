var messages = {
	message_ThankYouForTakingTheSurvey : "Thank you for taking the survey.",
	message_SurveyHasAlreadyBeenCompleted : "Survey has already been completed",
	message_SurveyNotFound : "Survey not found",
	message_SurveyClosed : "Survey Closed",
	month : "Month",
	day : "Day",
	year : "Year",
	hours : "Hours",
	minutes : "Minutes",
	timeZone : "Time Zone",
	defaultNoneSelectedOption : "Please choose one of the following",
	other : "Other, (please specify)",
	defaultAdditionalDetails : "Additional details or comments",
	days : new function() {
		var days = [];
		for(var i = 0; i <= 31; i++) {
			var obj = {
				value: i,
				caption: (i == 0 ? "" : i)
			};
			days.push(obj);
		}
		return days;
	},
	months : [
		{value: 0, caption: ""}, 
        {value: 1, caption: "Jan"},
        {value: 2, caption: "Feb"},
        {value: 3, caption: "Mar"},
        {value: 4, caption: "Apr"},
        {value: 5, caption: "May"},
        {value: 6, caption: "Jun"},
        {value: 7, caption: "Jul"},
        {value: 8, caption: "Aug"},
        {value: 9, caption: "Sep"},
        {value: 10, caption: "Oct"},
        {value: 11, caption: "Nov"},
        {value: 12, caption: "Dec"}
    ],
    hours : new function() {
		var hours = [];
		for(var i = 0; i <= 24; i++) {
			var obj = {
				value: (i > 0 ? jQuery.pad((i - 1), 2) : i),
				caption: (i == 0 ? "" : jQuery.pad((i - 1), 2))
			};
			hours.push(obj);
		}
		return hours;
	},
    minutes : new function() {
		var minutes = [];
		for(var i = 0; i <= 60; i++) {
			var obj = {
				value: (i > 0 ? jQuery.pad((i - 1), 2) : i),
				caption: (i == 0 ? "" : jQuery.pad((i - 1), 2))
			};
			minutes.push(obj);
		}
		return minutes;
	},
    timeZones : [
    	{value: 0, caption: ""},
    	{value: "-12.0", caption: "(GMT -12:00) Eniwetok, Kwajalein"}, 
       	{value: "-11.0", caption: "(GMT -11:00) Midway Island, Samoa"}, 
       	{value: "-10.0", caption: "(GMT -10:00) Hawaii"}, 
       	{value: "-9.0", caption: "(GMT -9:00) Alaska"}, 
       	{value: "-8.0", caption: "(GMT -8:00) Pacific Time (US & Canada)"}, 
       	{value: "-7.0", caption: "(GMT -7:00) Mountain Time (US & Canada)"}, 
       	{value: "-6.0", caption: "(GMT -6:00) Central Time (US & Canada), Mexico City"}, 
       	{value: "-5.0", caption: "(GMT -5:00) Eastern Time (US & Canada), Bogota, Lima"}, 
       	{value: "-4.0", caption: "(GMT -4:00) Atlantic Time (Canada), Caracas, La Paz"}, 
       	{value: "-3.5", caption: "(GMT -3:30) Newfoundland"}, 
       	{value: "-3.0", caption: "(GMT -3:00) Brazil, Buenos Aires, Georgetown"}, 
       	{value: "-2.0", caption: "(GMT -2:00) Mid-Atlantic"},
       	{value: "-1.0", caption: "(GMT -1:00 hour) Azores, Cape Verde Islands"},
       	{value: "0.0", caption: "(GMT) Western Europe Time, London, Lisbon, Casablanca"},
       	{value: "1.0", caption: "(GMT +1:00 hour) Brussels, Copenhagen, Madrid, Paris"},
       	{value: "2.0", caption: "(GMT +2:00) Kaliningrad, South Africa"},
       	{value: "3.0", caption: "(GMT +3:00) Baghdad, Riyadh, Moscow, St. Petersburg"},
       	{value: "3.5", caption: "(GMT +3:30) Tehran"},
       	{value: "4.0", caption: "(GMT +4:00) Abu Dhabi, Muscat, Baku, Tbilisi"},
       	{value: "4.5", caption: "(GMT +4:30) Kabul"},
       	{value: "5.0", caption: "(GMT +5:00) Ekaterinburg, Islamabad, Karachi, Tashkent"},
       	{value: "5.5", caption: "(GMT +5:30) Bombay, Calcutta, Madras, New Delhi"},
       	{value: "5.75", caption: "(GMT +5:45) Kathmandu"},
       	{value: "6.0", caption: "(GMT +6:00) Almaty, Dhaka, Colombo"},
       	{value: "7.0", caption: "(GMT +7:00) Bangkok, Hanoi, Jakarta"},
       	{value: "8.0", caption: "(GMT +8:00) Beijing, Perth, Singapore, Hong Kong"},
       	{value: "9.0", caption: "(GMT +9:00) Tokyo, Seoul, Osaka, Sapporo, Yakutsk"},
       	{value: "9.5", caption: "(GMT +9:30) Adelaide, Darwin"},
       	{value: "10.0", caption: "(GMT +10:00) Eastern Australia, Guam, Vladivostok"},
       	{value: "11.0", caption: "(GMT +11:00) Magadan, Solomon Islands, New Caledonia"},
       	{value: "12.0", caption: "(GMT +12:00) Auckland, Wellington, Fiji, Kamchatka"}
	]
};