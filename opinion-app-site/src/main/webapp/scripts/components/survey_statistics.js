
(function($) {
	surveyStatistics = function(givenOptions) {
		
		var options = $.extend( {
			opinionId : null,
			callback : null
		}, givenOptions);
		
		var lastFromDate = null;
		var lastToDate = null;
		
		function buildChart(data){
			
			var started = [];
			var completed = [];
			var partial = [];
			var disqualified = [];
			
			for(var i = 0; i < data.charts.completed.length; i++) {
				started.push([Date.parse(data.charts.completed[i][0]), data.charts.completed[i][1] + data.charts.partial[i][1]]);
				completed.push([Date.parse(data.charts.completed[i][0]), data.charts.completed[i][1]]);
				partial.push([Date.parse(data.charts.partial[i][0]), data.charts.partial[i][1]]);
				disqualified.push([Date.parse(data.charts.partial[i][0]), 0]);
			}
			
			$('.label-started').text($.addCommas((data.charts.totals.completed + data.charts.totals.partial)));
			$('.label-completed').text($.addCommas(data.charts.totals.completed));
			$('.label-partial').text($.addCommas(data.charts.totals.partial));
			
			if((data.charts.totals.completed + data.charts.totals.partial) != 0) {
				//$('.label-completion-rate').text(Math.round(data.charts.totals.completed / (data.charts.totals.completed + data.charts.totals.partial) * 100) + "%");
				$('.label-completion-rate').text(((data.charts.totals.completed / (data.charts.totals.completed + data.charts.totals.partial)) * 100).toFixed(2) + "%");
			} else {
				$('.label-completion-rate').text("0.00%");
			}
			
			//Date.UTC(+('20' + match[3]), match[1] - 1, +match[2])
			
			createChart(started, completed, partial, disqualified);
		}

		function createChart(started, completed, partial, disqualified) {
			
			var colors = Highcharts.getOptions().colors;
			var options = {
				chart: {
					/*type : 'area',*/
					renderTo: 'chart',
					style: {
						/*fontFamily: "open sans",*/
						/*margin: "auto",*/
						fontFamily: "arial,sans-serif",
						fontSize: "12px"
		           	}
					/*,
		           	events: {
		                load: function(event) {
		                    getChartData();
		                }
		            }
					*/
				},
				credits: {
				      enabled: false
				},
				title : {
					text : null
				},
				subtitle : {
					text : null
				},
				xAxis: {
					type : 'datetime',
					dateTimeLabelFormats: {
						month: '%e. %b',
						year: '%b'
					},
					labels : {
						style : {
							fontFamily: "arial,sans-serif",
							fontSize: "12px"
						},
						y :24
					}
					/*lineColor: 'red',*/
				    /*tickColor: 'red'*/
		            /*tickInterval: 7 * 24 * 3600 * 1000, // one week*/
		            /*
		            tickWidth: 0,
		            gridLineWidth: 1
		            */
				},
				yAxis: [{ 
					title: { 
						text : null
					},
					labels : {
						style : {
							fontFamily: "arial,sans-serif",
							fontSize: "12px"
						},
						formatter: function() {
		                    return Highcharts.numberFormat(this.value, 0);
		                }
					},
					min: 0,
					gridLineColor: '#ccc'
				    /*minorGridLineColor: 'rgba(255,255,255,0.07)'*/
				},{
					linkedTo :0,
					gridLineWidth: 0,
					opposite : true,
					title: {
						text: null
					},
					labels : {
						style : {
							fontFamily: "arial,sans-serif",
							fontSize: "12px"
						},
						align : 'right',
						/*x: -3,*/
						/*y: 16,*/
						formatter: function() {
		                    return Highcharts.numberFormat(this.value, 0);
		                }
					}
				}],
				legend: {
		            enabled: false
		        },
				tooltip: {
					shared: true,
					crosshairs: true,
					style: {
		                /*fontWeight: 'bold',*/
						fontFamily: "arial,sans-serif",
		                fontSize: '12px'
		            },
		            borderRadius: 0
					/*
					formatter: function() {
						return "<b>" + this.series.name + "</b><br/>" + Highcharts.dateFormat('%e. %b', this.x) + ": " + this.y + " m";
					}
					*/
				},
				/*
				plotOptions: {
					series: {
		                fillOpacity: 0.3
		            }
		        },
		        */
				series:[{
					name: "Started",
					type : "area",
					fillOpacity: 0.1,
					data: started,
					color: '#3b5998',
					marker: {
		                symbol: 'circle',
		                radius: 3
		            }
				},{
					name: "Completed",
					type : "area",
		            /*fillColor : '#ebeef4',*/
		            fillOpacity: 0.1,
					data:completed,
					color : '#8bbc21',
					marker: {
		                symbol: 'circle',
		                radius: 3
		            }
				},{
					name: "Partial (Incomplete)",
					type : "area",
					fillOpacity: 0.1,
					data:partial,
					color : "#e78a3b",
					/*color: '#8bbc21', //'#57a610'*/
					marker: {
		                symbol: 'circle',
		                radius: 3
		            }
				},{
					name : "Disqulified",
					type : "area",
					fillOpacity: 0.1,
					data: disqualified,
					color: '#a856a1',
					marker: {
		                symbol: 'circle',
		                radius: 3
		            }
				}]
			};
			
			chart = new Highcharts.Chart(options);
			
			showStatuses($('#button_status').attr('data-value'), $('#button_comparison_status').attr('data-value'));
			
		}

		function showStatuses(status, comparison_status) {
			
			if(typeof chart != 'undefined') {
				var series = chart.series;
				
				// started
				if(status == 1 && (comparison_status == 0 || comparison_status == 1)) {
					if (!series[0].visible) {
						series[0].show();
					}
					series[1].hide();
					series[2].hide();
					series[3].hide();
				} else if(status == 1 && comparison_status == 2) {
					if (!series[0].visible) {
						series[0].show();
					}
					if (!series[1].visible) {
						series[1].show();
					}
					series[2].hide();
					series[3].hide();
				} else if(status == 1 && comparison_status == 3) {
					if (!series[0].visible) {
						series[0].show();
					}
					series[1].hide();
					if (!series[2].visible) {
						series[2].show();
					}
					series[3].hide();
				} else if (status == 1 && comparison_status == 4) {
					if (!series[0].visible) {
						series[0].show();
					}
					series[1].hide();
					series[2].hide();
					if (!series[3].visible) {
						series[3].show();
					}
				}
				
				// completed
				if(status == 2 && (comparison_status == 0 || comparison_status == 2)) {
					series[0].hide();
					if (!series[1].visible) {
						series[1].show();
					}
					series[2].hide();
					series[3].hide();
				} else if (status == 2 && comparison_status == 1) {
					if (!series[0].visible) {
						series[0].show();
					}
					if (!series[1].visible) {
						series[1].show();
					}
					series[2].hide();
					series[3].hide();
				} else if(status == 2 && comparison_status == 3) {
					series[0].hide();
					if (!series[1].visible) {
						series[1].show();
					}
					if (!series[2].visible) {
						series[2].show();
					}
					series[3].hide();
				} else if(status == 2 && comparison_status == 4) {
					series[0].hide();
					if (!series[1].visible) {
						series[1].show();
					}
					series[2].hide();
					if (!series[3].visible) {
						series[3].show();
					}
				}
				
				// partial (incomplete)
				if(status == 3 && (comparison_status == 0 || comparison_status == 3)) {
					series[0].hide();
					series[1].hide();
					if (!series[2].visible) {
						series[2].show();
					}
					series[3].hide();
				} else if(status == 3 && comparison_status == 1) {
					if (!series[0].visible) {
						series[0].show();
					}
					series[1].hide();
					if (!series[2].visible) {
						series[2].show();
					}
					series[3].hide();
				} else if(status == 3 && comparison_status == 2) {
					series[0].hide();
					if (!series[1].visible) {
						series[1].show();
					}
					if (!series[2].visible) {
						series[2].show();
					}
					series[3].hide();
				} else if(status == 3 && comparison_status == 4) {
					series[0].hide();
					series[1].hide();
					if (!series[2].visible) {
						series[2].show();
					}
					if (!series[3].visible) {
						series[3].show();
					}
				}
				
				
				// disqualified
				if(status == 4 && (comparison_status == 0 || comparison_status == 4)) {
					series[0].hide();
					series[1].hide();
					series[2].hide();
					if (!series[3].visible) {
						series[3].show();
					}
				} else if(status == 4 && comparison_status == 1) { // started
					if (!series[0].visible) {
						series[0].show();
					}
					series[1].hide();
					series[2].hide();
					if (!series[3].visible) {
						series[3].show();
					}
				} else if(status == 4 && comparison_status == 2) { // completed
					series[0].hide();
					if (!series[1].visible) {
						series[1].show();
					}
					series[2].hide();
					if (!series[3].visible) {
						series[3].show();
					}
				} else if(status == 4 && comparison_status == 3) { // partial
					series[0].hide();
					series[1].hide();
					if (!series[2].visible) {
						series[2].show();
					}
					if (!series[3].visible) {
						series[3].show();
					}
				}
				
			}
			
		};
		
		var countries = [];
		var tableCountries = null;
		var renderTableCountries = function() {
			
			$('#table_countries').empty();
			tableCountries = $('#table_countries').dataTable({
				tableColumns : [
					{ key : 'countryName', label : 'Country', sortable : true, formatter: function(cell, value, record, source) {
						return (record.iso2 == "oo" ? "N/A" : "<span><i class=\"flag-icon flag flag-" + record.iso2 + "\"></i><span class=\"flag-icon-text\">" + record.countryName + "</span></span>");
					}},
					{ key: 'started', label: 'Responses', sortable: true, sortBy : { key : 'started', dataType: "number" }, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } }, formatter : function(cell, value, record, source) {
						return $.addCommas(record.started);
					}},
					{ key: 'completed', label: 'Completed', help : "A Completed responses indicates that the respondents clicked Finish on the last page of the survey.", sortable: true, sortBy : { key : 'completed', dataType: "number" }, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } }, formatter : function(cell, value, record, source) {
						return $.addCommas(record.completed);
					}},
					{ key : 'completionRate', label : 'Completion Rate', help : "A Completion Rate is the number of completed responses divided by the number of total responses (completed + partial + disqualified) and expressed in percentage.<br/><br/><table><tr><td rowspan=\"2\">Completion Rate =&nbsp;</td><td align=\"center\" style=\"border-bottom: 1px solid #000\">Completed responses</td></tr><tr><td align=\"center\">(Completed + Partial + Disqualified)</td></tr></table><br/><strong>Example:</strong> If you have five completed responses from 1000 responses, then your Completion Rate is 0.5%.", sortable : true, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } }, formatter: function(cell, value, record, source) {
						var completionRate;
						if(record.started != 0) {
							completionRate = ((record.completed / record.started) * 100).toFixed(2) + "%";
						} else {
							completionRate = "0.00%";
						}
						return completionRate;
		            }},
					{ key: 'partial', label: 'Partial', help: "A Partial responses indicates that the respondents entered at least one answer, but they did not click Finish on the last page of the survey.", sortable: true, sortBy : { key : 'partial', dataType: "number" }, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } }, formatter : function(cell, value, record, source) {
						return $.addCommas(record.partial);
					}},
					{ key: 'disqualified', label: 'Disqualified', sortable: true, sortBy : { key : 'partial', dataType: "number" }, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } }, formatter : function(cell, value, record, source) {
						var disqualified = 0;
						return $.addCommas(disqualified);
					}},
		            { key: 'timeTaken', label: 'Avg. Time Taken', sortable: false, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } }, formatter : function(cell, value, record, source) {
		            	if(record.timeTaken != undefined) {
		            		var timeTaken = (record.timeTaken.days != 0 ? record.timeTaken.days + (record.timeTaken.days > 1 ? " days, " : " day, ") : "") +
		            		(record.timeTaken.hours != 0 ? record.timeTaken.hours + (record.timeTaken.hours > 1 ? " hours, " : " hour, ") : "") + 
		            		(record.timeTaken.minutes != 0 ? record.timeTaken.minutes + (record.timeTaken.minutes > 1 ? " mins, " : " min, ") : "") +
		            		(record.timeTaken.seconds != 0 ? record.timeTaken.seconds + (record.timeTaken.seconds > 1 ? " secs" : " sec") : "");
		            		
		            		if(record.timeTaken.days == 0 
		   						&& record.timeTaken.hours == 0
		   						&& record.timeTaken.minutes == 0
		   						&& record.timeTaken.seconds == 0) {
		               			timeTaken = "Less than sec";
		               		}
		            		
		            		return timeTaken;
		            	}
		            }}
				],
				totals : [
				   [
				    	{ key : "countryName", calculate : false, formatter : function(totals) {
				    		return "Total - all countries";
				    	}},
				    	{ key : "started", formatter : function(totals) {
				    		return $.addCommas(totals.started);
				    	}},
				    	{ key : "completed", formatter : function(totals) {
				    		return $.addCommas(totals.completed);
				    	}},
				    	{ key : "completionRate", formatter : function(totals) { 
				    		var completionRate;
				    		if((totals.completed + totals.partial) != 0) {
				    			completionRate = ((totals.completed / totals.started) * 100).toFixed(2) + "%";
				    		} else {
				    			completionRate = "0.00%";
				    		}
				    		return completionRate; 
				    	}},
				    	{ key : "partial", formatter: function(totals) {
				    		return $.addCommas(totals.partial);	
				    	}},
				    	{ key : "disqualified", calculate : false, formatter : function(totals) {
				    		return "0";
				    	}},
				    	{ key : "timeTaken", calculate : false, formatter : function(totals) {
				    		return "--";
				    	}}
				   ]
				],
				dataSource : countries, 
				pagingStart : 10,
				show : [5, 10, 25, 50, 100]
			});
			
		};
		
		var getActivity = function(fromDate, toDate) {
			
			// set last
			lastFromDate = fromDate;
			lastToDate = toDate;
			
			getActivityChart({
				accountId : accountId,
				opinionId : options.opinionId,
				fromDate : fromDate,
				toDate : toDate,
				graphBy : 3,
				success : function(data) {
					buildChart(data);
				},
				error: function() {
					// alert("ERR")
				}
			});
			
			
			var mapValues = {};
			
			getCountriesStatistics({
				accountId : accountId,
				opinionId : options.opinionId,
				fromDate : fromDate,
				toDate : toDate,
				success : function(data) {
					
					countries = data.list;
					
					for(var i = 0; i < data.list.length; i++) {
						if(data.list[i].iso2 != "oo") {
							mapValues[data.list[i].iso2] = data.list[i].completed;
						}
					}
					
					// countries
					renderTableCountries();
					
					var min = 0;
					var max = 0;
					if(countries.length != 0) {
						
						min = (countries.length > 1 ? _.min(countries, function(country){ return country.started; }).started : 0);
						max = _.max(countries, function(country){ return country.started; }).started;
						
						$('.demographic-min').text(min);
						$('.demographic-max').text(max);
						
					} else {
						$('.demographic-min').text(min);
						$('.demographic-max').text(max);
					}
					
					jQuery('#vmap').empty();
					jQuery('#vmap').vectorMap({
					    map: 'world_en',
					    backgroundColor: null,
					    borderColor: '#666',
					    color: '#f8f8f8',
					    hoverOpacity: 0.7,
					    selectedColor: '#666666',
					    enableZoom: true, // true
					    showTooltip: true,
					    values: mapValues,
					    scaleColors: ['#ebeef4', '#3B5998'], // ['#C8EEFF', '#006491'], // ['#C8EEFF', '#006491'],
					    normalizeFunction: 'polynomial'
					});
					
				},
				error: function(error) {
					// alert(JSON.stringify(error));	
				}
			});
			
		};
		
		var init = function() {
			
			// status
	        $('#button_status').dropdownButton({
	        	actions : [
       			    {
       			    	label : "Started",
       			    	legend : '#3b5998',
       			    	value : 1,
       			    	active : true,
       					click : function(button) {
       						//
       					}
       			    },
       			    {
       			    	label : "Completed",
       			    	legend : '#8bbc21',
       			    	value : 2,
       					click : function(button) {
       						//
       					}
       			    },
       			    {
       			    	label : "Partial (Incomplete)",
       			    	legend : "#e78a3b",
       			    	value : 3,
       					click : function(button) {
       						//
       					}
       			    },
       			    {
       			    	label : "Disqualified",
       			    	legend : '#a856a1',
       			    	value : 4,
       			    	click : function(button) {
       						//
       					}
       			    }
       			],
				change : function(value) {
					showStatuses($('#button_status').attr('data-value'), $('#button_comparison_status').attr('data-value'));
				}
	        });
	        
	        // comparison_status
	        $('#button_comparison_status').dropdownButton({
	        	actions : [
       			    {
       			    	label : "None",
       			    	value : 0,
       			    	active : true,
       					click : function(button) {
       						//
       					}
       			    },
       			    {
       			    	label : "Started",
       			    	legend : '#3b5998',
       			    	value : 1,
       					click : function(button) {
       						//
       					}
       			    },
       			    {
       			    	label : "Completed",
       			    	legend : '#8bbc21',
       			    	value : 2,
       					click : function(button) {
       						//
       					}
       			    },
       			    {
       			    	label : "Partial (Incomplete)",
       			    	legend : "#e78a3b",
       			    	value : 3,
       					click : function(button) {
       						//
       					}
       			    },
       			    {
       			    	label : "Disqualified",
       			    	legend : '#a856a1',
       			    	value : 4,
       			    	click : function(button) {
       						//
       					}
       			    }
       			],
				change : function() {
					showStatuses($('#button_status').attr('data-value'), $('#button_comparison_status').attr('data-value'));
				}
	        });	
			
		};
		
		init();
		
		return {
			get : function(params) {
				getActivity(params.fromDate, params.toDate);
			}
		};
	};
})(jQuery);