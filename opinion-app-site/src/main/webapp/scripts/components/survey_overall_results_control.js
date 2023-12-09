(function ($) {
    $.fn.extend({
        dataChart: function (givenOptions) {
            var element = $(this),
                options = $.extend({
                    chartId : 1,
                    data : []
                }, givenOptions);

            function init() {

                element.empty();
                console.log("render chart" + options.chartId);

                switch(options.chartId) {
                    case 1:

                        // Pie chart

                        // Build the chart
                        // ['#3b5998', '#4e69a2', '#627aad', '#758ab6', '#3899bc1', '#9daccb', '#b1bdd6', '#c4cde0', '#d8deea', '#ebeef4']
                        var colors = getColors(options.data.length);
                        element.highcharts({
                            colors : colors,
                            chart: {
                                plotBackgroundColor: null,
                                plotBorderWidth: null,
                                plotShadow: false,
                                type: 'pie',
                                style: {
                                    /*fontFamily: "open sans",*/
                                    /*margin: "auto",*/
                                    fontFamily: "arial,sans-serif",
                                    fontSize: "12px"
                                }
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
                            tooltip: {
                                /*pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>',*/
                                pointFormat: '<b>{point.percentage:.2f}%</b>',
                                style: {
                                    /*fontWeight: 'bold',*/
                                    fontFamily: "arial,sans-serif",
                                    fontSize: '12px'
                                },
                                borderRadius: 0
                            },
                            plotOptions: {
                                pie: {
                                    /*allowPointSelect: true,*/
                                    cursor: 'pointer',
                                    center: ['50%', '50%'],
                                    size : 130,
                                    dataLabels : {
                                        enabled : false
                                    }
                                    /*showInLegend : true*/
                                    /*
                                     dataLabels: {
                                     enabled: true,
                                     format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                                     style: {
                                     color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                                     }
                                     }
                                     */
                                }
                            },
                            /*
                             legend: {
                             enabled: true,
                             layout: 'vertical',
                             align: 'right',
                             width: 200,
                             verticalAlign: 'middle',
                             useHTML: true,
                             labelFormatter: function() {
                             return '<div style="text-align: left; width:130px;float:left;">' + this.name + '</div><div style="width:40px; float:left;text-align:right;">' + this.y + '%</div>';
                             }
                             },
                             */
                            /*
                             legend: {
                             align: 'right',
                             verticalAlign: 'top',
                             layout: 'vertical',
                             borderRadius :0,
                             borderWidth : 0,
                             //            style: {"fontSize":"20%"},
                             title: { text: 'FRUITS'},
                             itemStyle: {
                             fontWeight: 'normal',
                             fontFamily: "arial,sans-serif",
                             fontSize: '12px'
                             },
                             symbolWidth: 12,
                             symbolRadius: 0
                             },
                             */
                            series: [{
                                name: "Brands",
                                data: options.data
                            }]
                        });

                        break;

                    case 2 :

                        var colors = getColors(options.data.length);
                        element.highcharts({
                            colors : colors,
                            chart: {
                                plotBackgroundColor: null,
                                plotBorderWidth: null,
                                plotShadow: false,
                                type: 'pie',
                                style: {
                                    /*fontFamily: "open sans",*/
                                    /*margin: "auto",*/
                                    fontFamily: "arial,sans-serif",
                                    fontSize: "12px"
                                }
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
                            tooltip: {
                                /*pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>',*/
                                pointFormat: '<b>{point.percentage:.2f}%</b>',
                                style: {
                                    /*fontWeight: 'bold',*/
                                    fontFamily: "arial,sans-serif",
                                    fontSize: '12px'
                                },
                                borderRadius: 0
                            },
                            plotOptions: {
                                pie: {
                                    /*allowPointSelect: true,*/
                                    cursor: 'pointer',
                                    center: ['50%', '50%'],
                                    size : 130,
                                    dataLabels : {
                                        enabled : false
                                    }
                                    /*showInLegend : true*/
                                    /*
                                     dataLabels: {
                                     enabled: true,
                                     format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                                     style: {
                                     color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                                     }
                                     }
                                     */
                                }
                            },
                            /*
                             legend: {
                             enabled: true,
                             layout: 'vertical',
                             align: 'right',
                             width: 200,
                             verticalAlign: 'middle',
                             useHTML: true,
                             labelFormatter: function() {
                             return '<div style="text-align: left; width:130px;float:left;">' + this.name + '</div><div style="width:40px; float:left;text-align:right;">' + this.y + '%</div>';
                             }
                             },
                             */
                            /*
                             legend: {
                             align: 'right',
                             verticalAlign: 'top',
                             layout: 'vertical',
                             borderRadius :0,
                             borderWidth : 0,
                             //            style: {"fontSize":"20%"},
                             title: { text: 'FRUITS'},
                             itemStyle: {
                             fontWeight: 'normal',
                             fontFamily: "arial,sans-serif",
                             fontSize: '12px'
                             },
                             symbolWidth: 12,
                             symbolRadius: 0
                             },
                             */
                            series: [{
                                name: "Brands",
                                innerSize: 70,
                                data: options.data
                            }]
                        });
                        console.log("Donut");

                        break;

                    case 3 :

                        var colors = getColors(options.data.length);
                        element.highcharts({
                            colors : colors,
                            chart: {
                                type: 'column',
                                style: {
                                    /*fontWeight: 'bold',*/
                                    fontFamily: "arial,sans-serif",
                                    fontSize: '12px'
                                }
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
                                type: 'category',
                                labels : {
                                    style: {
                                        fontFamily: "arial,sans-serif",
                                        fontSize: "12px"
                                    }
                                }
                            },
                            yAxis: {
                                title: {
                                    text: null
                                },
                                labels : {
                                    style: {
                                        fontFamily: "arial,sans-serif",
                                        fontSize: "12px"
                                    }
                                }
                            },
                            legend: {
                                enabled: false
                            },
                            plotOptions: {
                                series: {
                                    pointWidth: 30,
                                    pointPadding: 0,
                                    groupPadding: 0,
                                    borderWidth: 0,
                                    dataLabels: {
                                        enabled: true,
                                        format: '{point.y:.2f}%'
                                    }
                                }
                            },
                            tooltip: {
                                pointFormat: '<b>{point.y:.2f}%</b>',
                                style: {
                                    /*fontWeight: 'bold',*/
                                    fontFamily: "arial,sans-serif",
                                    fontSize: '12px'
                                },
                                borderRadius: 0
                                /*
                                headerFormat: '<span>{series.name}</span><br>',
                                pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.2f}%</b> of total<br/>'
                                */
                            },
                            series: [{
                                name: "Brands",
                                colorByPoint: true,
                                data: options.data
                            }]
                        });


                        break;

                    case 4:

                        var colors = getColors(options.data.length);
                        element.highcharts({
                            colors : colors,
                            chart: {
                                type: 'bar',
                                style: {
                                    /*fontWeight: 'bold',*/
                                    fontFamily: "arial,sans-serif",
                                    fontSize: '12px'
                                }
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
                                type: 'category',
                                labels : {
                                    style: {
                                        fontFamily: "arial,sans-serif",
                                        fontSize: "12px"
                                    }
                                }
                            },
                            yAxis: {
                                title: {
                                    text: null
                                },
                                labels : {
                                    style: {
                                        fontFamily: "arial,sans-serif",
                                        fontSize: "12px"
                                    }
                                }
                            },
                            legend: {
                                enabled: false
                            },
                            plotOptions: {
                                series: {
                                    pointWidth: 30,
                                    pointPadding: 0,
                                    groupPadding: 0,
                                    borderWidth: 0,
                                    dataLabels: {
                                        enabled: true,
                                        format: '{point.y:.2f}%'
                                    }
                                }
                            },
                            tooltip: {
                                pointFormat: '<b>{point.y:.2f}%</b>',
                                style: {
                                    /*fontWeight: 'bold',*/
                                    fontFamily: "arial,sans-serif",
                                    fontSize: '12px'
                                },
                                borderRadius: 0
                                /*
                                 headerFormat: '<span>{series.name}</span><br>',
                                 pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.2f}%</b> of total<br/>'
                                 */
                            },
                            series: [{
                                name: "Brands",
                                colorByPoint: true,
                                data: options.data
                            }]
                        });
                        
                        
                        break;
                        
                    case 5:
                    	
                    	var colors = getColors(options.data.length);
                        element.highcharts({
                            colors : colors,
                            chart: {
                                type: 'bar',
                                style: {
                                    /*fontWeight: 'bold',*/
                                    fontFamily: "arial,sans-serif",
                                    fontSize: '12px'
                                }
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
                                type: 'category',
                                labels : {
                                    style: {
                                        fontFamily: "arial,sans-serif",
                                        fontSize: "12px"
                                    }
                                }
                            },
                            yAxis: {
                                title: {
                                    text: null
                                },
                                labels : {
                                    style: {
                                        fontFamily: "arial,sans-serif",
                                        fontSize: "12px"
                                    }
                                }
                            },
                            legend: {
                                enabled: false
                            },
                            plotOptions: {
                                series: {
                                    pointWidth: 30,
                                    pointPadding: 0,
                                    groupPadding: 0,
                                    borderWidth: 0,
                                    dataLabels: {
                                        enabled: true,
                                        format: '{point.y:.2f}%'
                                    },
                                    stacking: 'percent'
                                }
                            },
                            tooltip: {
                                headerFormat: '<span>{series.name}</span><br>',
                                pointFormat: '<b>{point.count} ({point.y:.2f}%)</b>',
                                style: {
                                    /*fontWeight: 'bold',*/
                                    fontFamily: "arial,sans-serif",
                                    fontSize: '12px'
                                },
                                borderRadius: 0
                                /*
                                 headerFormat: '<span>{series.name}</span><br>',
                                 pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.2f}%</b> of total<br/>'
                                 */
                            },
                            series: [{
                                name: 'John',
                                data: [
                                    { y : 67, count : 2 }
                                ]
                            }, {
                                name: 'Jane',
                                data: [
                                    { y : 33 , count : 1 }
                                ]
                            }]
                            /*
                            series: [{
                                name: "Brands",
                                colorByPoint: true,
                                data: options.data
                            }]
                            */
                        });
                    	
                    	break;

                    case 6:

                        var colors = getColors(options.data.length);
                        element.highcharts({
                            colors : colors,
                            chart: {
                                type: 'column',
                                style: {
                                    /*fontWeight: 'bold',*/
                                    fontFamily: "arial,sans-serif",
                                    fontSize: '12px'
                                }
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
                                type: 'category',
                                labels : {
                                    style: {
                                        fontFamily: "arial,sans-serif",
                                        fontSize: "12px"
                                    }
                                }
                            },
                            yAxis: {
                                min: 0,
                                title: {
                                    text: null
                                },
                                labels : {
                                    style: {
                                        fontFamily: "arial,sans-serif",
                                        fontSize: "12px"
                                    }
                                }
                            },
                            legend: {
                                enabled: false
                            },
                            plotOptions: {
                                series: {
                                    pointWidth: 30,
                                    pointPadding: 0,
                                    groupPadding: 0,
                                    borderWidth: 0,
                                    dataLabels: {
                                        enabled: true,
                                        format: '{point.y:.2f}%'
                                    },
                                    stacking: 'percent'
                                }
                            },
                            tooltip: {
                                headerFormat: '<span>{series.name}</span><br>',
                                pointFormat: '<b>{point.count} ({point.y:.2f}%)</b>',
                                style: {
                                    /*fontWeight: 'bold',*/
                                    fontFamily: "arial,sans-serif",
                                    fontSize: '12px'
                                },
                                borderRadius: 0
                                /*
                                 headerFormat: '<span>{series.name}</span><br>',
                                 pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.2f}%</b> of total<br/>'
                                 */
                            },
                            series: [{
                                name: 'John',
                                data: [
                                    { y : 50, count : 1 }
                                ]
                            }, {
                                name: 'Jane',
                                data: [
                                    { y : 50, count : 1 }
                                ]
                            }]
                            /*
                            series: [{
                                name: "Brands",
                                colorByPoint: true,
                                data: options.data
                            }]
                            */
                        });

                        break;
                }

            }

            init();

        }
    })
})(jQuery);

jQuery.fn.resultControl = function () {
    if (arguments.length == 0) return [];
    var I = arguments[0] || {};
    var v = $(this);
    
    var B = function (D) {
    	
    	var EH = $("<div class=\"result-control-container\">" + 
     			"<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">" +
     				"<tr class=\"row-control-header\">" +
     					"<td>" +
     						"<b class=\"header-control-content\"></b>" + 
     					"</td>" +
     				"</tr>" +
     				"<tr>" +
     					"<td>" + 
		     				"<div class=\"container-control-includes\"></div>" +
     					"</td>" +
     				"</tr>" +
     			"</table>" +
     		"</div>");
    	
    	var E = $("<div class=\"result-control-container\">" +
     			"<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">" +
     				"<tr class=\"row-control-header\">" +
     					"<td>" +
     						"<b class=\"label-control-number\"></b><b class=\"label-control-mandatory\" style=\"display: none;\">*&nbsp;</b><b class=\"header-control-content\"></b>" +
                            "<a href=\"#\" title=\"Customize\" class=\"button-white button-control-customize\"><span>Customize</span></a>" +
     					"</td>" +
     				"</tr>" +
                    "<tr>" +
                        "<td>" +
                            "<div class=\"customize-control\" style=\"display: none\">" +
                                "<a class=\"button-horizontal-bar\">Horizontal Bar</a>" +
                                "<a class=\"button-vertical-bar\">Vertical Bar</a>" +
                                "<a class=\"button-stacked-horizontal-bar\">Stacked Horizontal Bar</a>" +
                                "<a class=\"button-stacked-vertical-bar\">Stacked Vertical Bar</a>" +
                                "<a class=\"button-pie-chart\">Pie Chart</a>" +
                                "<a class=\"button-donut-chart\">Donut Chart</a>" +
                                "<a class=\"button-line-graph\">Line Graph</a>" +
                                "<a class=\"button-area-graph\">Area Graph</a>" +
                            "</div>" +
                            "<div>" +
                                "<div class=\"chart-control\" style=\"height: 186px; width: 450px; margin: 0 auto\">CHART HERE</div>" +
                            "</div>" +
                        "</td>" +
                    "</tr>" +
     				"<tr>" +
     					"<td>" + 
		     				"<div class=\"container-control-includes\"></div>" +
		     				"<div class=\"container-control-comment\" style=\"display: none\">" + 
	     					"<div class=\"label-control-comment\"></div>" + 
		     					"<div class=\"row-textarea\">" + 
		     						"<div class=\"cell\">" + 
		     							"<div class=\"tip\">" +
		     								"<div class=\"tip-top\"></div>" +
		     								"<label class=\"tip-control-comment\"></label>" +
		     							"</div>" +
		     						"</div>" + 
		     					"</div>" + 
		     				"</div>" +
     					"</td>" +
     				"</tr>" +
     			"</table>" +
     		"</div>");
    
    	switch (D) {
			case 1 : {
				break;
			}
			case 2 : {
				
				// multiple choice
				
				var c = E;
				var g = c.find('.container-control-includes');
				var F = I.controlId;
				
				var G = function () {
					
					for (var x = 0; x < response.controls.list.length; x++) {
                        if (response.controls.list[x].controlId == F) {
                        	var u = response.controls.list[x];
                        	
                        	c.find('.header-control-content')
                        	 .html(u.content.replace(/\n/g, "<br/>"));
                        	
                        	if (u.isMandatory) {
                                c.find('.label-control-mandatory').show();
                            }
                        	
                        	if (u.options.list.length != 0) {
                        		
                        		var t = $("<table cellpadding=\"0\" cellspacing=\"1\" border=\"0\" style=\"width: 100%\"></table>");
                        		
                        		$("<tr class=\"row-header\">" +
                        				"<td class=\"first-item\"><b>Answer Choices</b></td>" +
                        				"<td></td>" +
                        				"<td><b>Percent</b></td>" +
                        				"<td class=\"last-item\"><b>Count</b></td>" +
                        			"</tr>").appendTo(t);
                        		
                        		var chartData = [];
                        		var P = null;
                        		for (var i = 0; i < u.options.list.length; i++) {
                        			if(u.options.list[i].optionKindId == 0) {
                        				
                        				var percentage, count;
                        				if(u.options.list[i].statistics != null) {
                            				count = u.options.list[i].statistics[u.controlId].count;
                            				percentage = Math.round(count / u.options.list[i].statistics[u.controlId].sum * 100);
                            			} else {
                            				count = 0;
                            				percentage = 0;
                            			}
                        				
                        				//var h = $("<tr class=\"" + (u.options.list[i].selectTypeId ? "highlight-item" : "hide-item") + "\">" +
                        				var h = $("<tr class=\"highlight-item row-result\">" +
                        							"<td>" +
                        								"<div class=\"row-choice " + (u.options.list[i].selectTypeId ? "selected-item" : "") + "\">" + u.options.list[i].text + "</div>" +
                        								"<div class=\"container-additional-details-or-comments\" style=\"display:" + (u.options.list[i].answerComment != undefined ? "block" : "none") + "\">" +
                        									"<div class=\"row-desc\">" + (u.options.list[i].additionalDetailsTitle != undefined ? u.options.list[i].additionalDetailsTitle : "Additional details or comments") + "</div>" +
                        									"<div class=\"row-details\">" +
	                        									"<div class=\"tip\">" +
		                	     									"<div class=\"tip-top\"></div>" +
		                	     									"<label>" + (u.options.list[i].answerComment != undefined ? u.options.list[i].answerComment : "") + "</label>" +
		                	     								"</div>" +
                        									"</div>" +
                        								"</div>" +
                        							"</td>" +
                        							"<td class=\"column-bar\">" +
                        								"<div class=\"outer-bar\"><div class=\"bar\" data-value=\"" + percentage + "\"><div class=\"bar-inner\"></div></div></div>" +
                        							"</td>" +
                        							"<td class=\"column-statistic\">" +
	                    								"<span>" + (percentage != 0 ? (percentage).toFixed(2) : "0.00") + "%</span>" +
	                    							"</td>" +
                        							"<td class=\"last-item column-statistic\">" +
	                    								"<span>" + count + "</span>" +
	                    							"</td>" +
                        						"</tr>").appendTo(t);


                                        chartData.push({
                                            name : u.options.list[i].text,
                                            y : percentage
                                        });
                        				
                        			}
                        			if(u.options.list[i].optionKindId == 1) {
                        				P = u.options.list[i];
                        			}
                        		}
                        		
                        		// other
                        		if(P != null) {
                        			
                        			var percentage, count;
                    				if(P.statistics != null) {
                        				count = P.statistics[u.controlId].count;
                        				percentage = Math.round(count / P.statistics[u.controlId].sum * 100);
                        			} else {
                        				count = 0;
                        				percentage = 0;
                        			}
                    				
                        			var z = $("<tr class=\"highlight-item row-result\">" +
                        				"<td>" +
                        					"<div>" + (count != 0 ? "<a href=\"#\" title=\"" + P.text + "\" class=\"ui-icon-expand expand-responses\">" + P.text + "</a>" : "<span>" + P.text + "</span>") + "</div>" +
                    						"<ul class=\"related-responses\" style=\"display: none\"></ul>" +
                        				"</td>" +
                        				"<td class=\"column-bar\">" +
                        					"<div class=\"outer-bar\"><div class=\"bar\" data-value=\"" + percentage + "\"><div class=\"bar-inner\"></div></div></div>" +
                        				"</td>" +
                        				"<td class=\"column-statistic\">" +
                        					"<span>" + (percentage != 0 ? (percentage).toFixed(2) : "0.00") + "%</span>" +
                        				"</td>" +
                        				"<td class=\"last-item column-statistic\">" +
                        					"<span>" + count + "</span>" +
	                    				"</td>" +
                        			"</tr>").appendTo(t);

                                    chartData.push({
                                        name : P.text,
                                        y : percentage
                                    });
                        			
                        			var related = $("<tr>" +
                                			"<td colspan=\"4\">" +
                                				"<table cellspacing=\"0\" cellpadding=\"0\" style=\"width: 100%\" class=\"table-related-responses\"></table>" +
                                			"</td>" +
                                		"</tr>").appendTo(t);
                        			
                        			var isRelatedResponses = false;
                                	var relatedResponses = related.find(".table-related-responses"); //z.find("ul.related-responses");
                                	z.find('.expand-responses').click(function(e) {
                                		if(!$(this).hasClass("ui-icon-collapse")) {
                                			
                                			$(this)
                                			.addClass("ui-icon-collapse");
                                			
                                			relatedResponses.show();
                                			
                                			// get related responses
                                			if(!isRelatedResponses) {
                                				
                                				getAnswererResultsText({
                                					accountId : accountId,
                                					opinionId : I.opinionId,
                                					/*controlId : u.controlId,*/
                                					optionId : P.optionId,
                                					success : function(data) {
                                						
                                						for(var r = 0; r < data.list.length; r++) {
                                							
                                							$("<tr>" +
                                									"<td><a href=\"responses/" + data.list[r].sessionId + "\" title=\"\">" + data.list[r].text + "</a></td>" +
                                							"</tr>").appendTo(relatedResponses);
                                							
                                						}
                                						
                                						isRelatedResponses = true;
                                						
                                					},
                                					error: function(error) {
                                						//
                                					}
                                				});
                                				
                                			}
                                			
                                		} else {
                                			$(this)
                                			.removeClass("ui-icon-collapse");
                                			
                                			relatedResponses.hide();
                                		}

                                		e.preventDefault();
                                	});
                        			
                        		}



                                // Customize
                                var customize = c.find('.customize-control');
                                c.find('.button-control-customize').click(function(e) {
                                    e.preventDefault();
                                    if(customize.is(':visible')) {
                                        customize.hide();
                                    } else {
                                        customize.show();
                                    }
                                });




                                // Horizontal Bar
                                c.find('.button-horizontal-bar').click(function(e) {
                                    e.preventDefault();
                                    c.find('.chart-control').dataChart({
                                        chartId : 4,
                                        data : chartData
                                    });
                                });

                                // Vertical Bar
                                c.find('.button-vertical-bar').click(function(e) {
                                    e.preventDefault();
                                    c.find('.chart-control').dataChart({
                                        chartId : 3,
                                        data : chartData
                                    });
                                });

                                // Stacked Horizontal Bar
                                c.find('.button-stacked-horizontal-bar').click(function(e) {
                                    e.preventDefault();
                                    c.find('.chart-control').dataChart({
                                        chartId : 5,
                                        data : chartData
                                    });
                                });

                                // Stacked Vertical Bar
                                c.find('.button-stacked-vertical-bar').click(function(e) {
                                    e.preventDefault();
                                    c.find('.chart-control').dataChart({
                                        chartId : 6,
                                        data : chartData
                                    });
                                });

                                // Pie Chart
                                c.find('.button-pie-chart').click(function(e) {
                                    e.preventDefault();
                                    c.find('.chart-control').dataChart({
                                        chartId : 1,
                                        data : chartData
                                    });
                                });

                                // Donut Chart
                                c.find('.button-donut-chart').click(function(e) {
                                    e.preventDefault();
                                    c.find('.chart-control').dataChart({
                                        chartId : 2,
                                        data : chartData
                                    });
                                });



                                // Default chart
                                c.find('.chart-control').dataChart({
                                    chartId : 1,
                                    data : chartData
                                });


                                //console.log(chartData);
                        		
                        		// answered
                        		$("<tr class=\"row-answered\">" +
                        				"<td colspan=\"3\"><b>Answered</b></td>" +
                        				"<td class=\"last-item\"><b class=\"label-answered\">" + u.answered + "</b></td>" +
                        			"</tr>").appendTo(t);
                        		
                        		// skipped
                        		$("<tr class=\"row-skipped\">" +
                        				"<td colspan=\"3\"><b>Skipped</b></td>" +
                        				"<td class=\"last-item\"><b class=\"label-skipped\">" + u.skipped + "</b></td>" +
                        			"</tr>").appendTo(t);
                        		
                        		
                        		// append table to container
                        		t.appendTo(g);
                        		
                        		
                        	}
                        	
                        	c.appendTo(v);
                        	break;
                        }
					}
				};
				G();
				
				break;
			}
			case 3 : {	
				
				// essay
				
				var c = E;
				var g = c.find('.container-control-includes');
				var F = I.controlId;
				var G = function () {
					for (var x = 0; x < response.controls.list.length; x++) {
                        if (response.controls.list[x].controlId == F) {
                        	var u = response.controls.list[x];
                        	
                        	c.find('.header-control-content')
                        	 .html(u.content.replace(/\n/g, "<br/>"));
                        	
                        	if (u.isMandatory) {
                                c.find('.label-control-mandatory').show()
                            }
                        	
                        	var t = $("<table cellpadding=\"0\" cellspacing=\"1\" border=\"0\" style=\"width: 100%\"></table>");
                        	
                        	$("<tr class=\"row-header\">" +
                    				"<td></td>" +
                    				"<td class=\"last-item\"><b>Count</b></td>" +
                    			"</tr>").appendTo(t);
                        	
                        	
                        	var z = $("<tr class=\"highlight-item row-result\">" +
                    				"<td>" +
                    					"<div>" + (u.answered != 0 ? "<a href=\"#\" title=\"Responses\" class=\"ui-icon-expand expand-responses\">Responses</a>" : "") + "</div>" + // <em style=\"color: #999\">The respondent's skipped this question.</em>
                    					"<ul class=\"related-responses\" style=\"display: none\"></ul>" +
                    				"</td>" +
                    				"<td class=\"last-item column-statistic\">" +
                    					"<span>" + u.answered + "</span>" +
                    				"</td>" +
                    			"</tr>").appendTo(t);
                        	
                        	
                        
                        	var related = $("<tr>" +
                        			"<td colspan=\"2\">" +
                        				"<table cellspacing=\"0\" cellpadding=\"0\" style=\"width: 100%\" class=\"table-related-responses\"></table>" +
                        			"</td>" +
                        		"</tr>").appendTo(t);
                        	
                        	/*
                        	var related = $("<tr>" +
                        			"<td colspan=\"2\">" +
                        				"<div class=\"table-related-responses\" id=\"table_responses_for_" + F + "\"></div>" +
                        			"</td>" +
                        		"</tr>").appendTo(t);
                        		
                        	*/
                        	
                        	
                        	
                        	var isRelatedResponses = false;
                        	var relatedResponses = related.find(".table-related-responses"); //z.find("ul.related-responses");
                        	z.find('.expand-responses').click(function(e) {
                        		if(!$(this).hasClass("ui-icon-collapse")) {
                        			
                        			$(this)
                        			/*.text("Collapse responses")
                        			.attr("title", "Collapse responses")*/
                        			.addClass("ui-icon-collapse");
                        			
                        			relatedResponses.show();
                        			
                        			
                        			
                        			
                        			
                        			// get related responses
                        			if(!isRelatedResponses) {
                        				
                        				getAnswererResultsText({
                        					accountId : accountId,
                        					opinionId : I.opinionId,
                        					controlId : u.controlId,
                        					success : function(data) {
                        						
                        						/*
                        						console.log("HERE");
                        						
                        						relatedResponses.dataTable({
                                    				tableColumns : [
            											{ key : 'text', label : "Response", sortable : true, formatter : function(cell, value, record, source) {
            												return $("<a href=\"responses/" + record.sessionId + "\" title=\"" + record.text + "\">" + record.text + "</a>");
            											}},
            											{ key: 'modifyDate', label: 'Response Date', sortable: true, sortBy : { dataType: "date" }, width: 126, formatter: function(cell, value, record, source) {
            												if(record.modifyDate) {
            													var left = record.modifyDate.substring(record.modifyDate.lastIndexOf(" "), " ");
            													var right = record.modifyDate.replace(left, "");
            													return left + "<b class=\"hours\">" + right + "</b>";
            												} 
            											}}
                                    				],
                                    				dataSource : data.list,
                                    				pagingStart : 10,
                                    				show : [5, 10, 25, 50, 100]
                                    			});
                        						*/
                        						
                        						for(var r = 0; r < data.list.length; r++) {
                        							
                        							$("<tr>" +
                        								"<td><a href=\"responses/" + data.list[r].sessionId + "\" title=\"\">" + data.list[r].text + "</a></td>" +
                        							"</tr>").appendTo(relatedResponses);
                        							
                        						}
                        						
                        						isRelatedResponses = true;
                        						
                        					},
                        					error: function(error) {
                        						//
                        					}
                        				});
                        				
                        			}
                        			
                        		} else {
                        			$(this)
                        			/*.text("Expand responses")
                        			.attr("title", "Expand responses")*/
                        			.removeClass("ui-icon-collapse");
                        			
                        			relatedResponses.hide();
                        		}
                        		
                        		
                        		e.preventDefault();
                        	});
                        	
                        	/*
                        	if(u.answerValue != undefined) {
                            	$("<div class=\"field-free-text\">" +
                            			"<div class=\"tip\">" +
	     									"<div class=\"tip-top\"></div>" +
	     									"<label>" + u.answerValue + "</label>" +
	     								"</div>" +
                            		"</div>").appendTo(g);
                            } else {
                            	$("<div class=\"field-free-text\"><em style=\"color: #999\">The respondent skipped this question.</em></div>").appendTo(g);
                            }
                        	*/
                        	
                        	// answered
                        	$("<tr class=\"row-answered\">" +
                    				"<td><b>Answered</b></td>" +
                    				"<td class=\"last-item\"><b class=\"label-answered\">" + u.answered + "</b></td>" +
                    			"</tr>").appendTo(t);
                    		
                    		// skipped
                    		$("<tr class=\"row-skipped\">" +
                    				"<td><b>Skipped</b></td>" +
                    				"<td class=\"last-item\"><b class=\"label-skipped\">" + u.skipped + "</b></td>" +
                    			"</tr>").appendTo(t);
                    		
                    		
                    		// append table to container
                    		t.appendTo(g);
                    		
                        	
                        	c.appendTo(v);
                        	break;
                        }
					}
				};
				G();
				
				
				break;
			}
			case 4 : {
				
				// matrix
				
				var c = E;
				var g = c.find('.container-control-includes');
				var F = I.controlId;
				var G = function () {
					for (var x = 0; x < response.controls.list.length; x++) {
                        if (response.controls.list[x].controlId == F) {
                        	var u = response.controls.list[x];
                        	
                        	c.find('.header-control-content')
                        	 .html(u.content.replace(/\n/g, "<br/>"));
                        	
                        	if (u.isMandatory) {
                                c.find('.label-control-mandatory').show()
                            }
                        	
                        	if (u.controls.list.length != 0) {
                        		
                        		var colspan = 1;
                        		var t = $("<table cellpadding=\"0\" cellspacing=\"1\" border=\"0\" style=\"width: 100%\"></table>");
                        		
                        		
                        		
                        		
                        		t.appendTo(g);
                        		
                        		
                        		var c1 = $("<tr class=\"row-header\"></tr>");
                        	    var d = $("<td>&nbsp;</td>");
                        	    c1.append(d);
                        		
                        	    for (var y = 0; y < u.options.list.length; y++) {
                        	    	$("<td><b>" + u.options.list[y].text + "</b></td>").appendTo(c1);
                        	    	colspan+=1;
                        	    }
                        	    
                        	    var d2 = $("<td class=\"last-item\"><b>Total</b></td>");
                        	    c1.append(d2);
                        		
                        	    c1.appendTo(t);
                        	    
                        	    for (var i = 0; i < u.controls.list.length; i++) {
                        	    	
                        	    	
                        	    	var f = $("<tr class=\"highlight-item row-result\"></tr>");
                        	    	var g1 = $("<td>" + u.controls.list[i].content + "</td>");
                        	        f.append(g1);
                        	        
                        	        var total = 0;
                        	        for (var y = 0; y < u.options.list.length; y++) {
                        	        	var j = null;
                                        var actualOption = u.options.list[y];
                                    	var actualSubControl = u.controls.list[i];
                                    	
                                    	var percentage, count;
                        				if(actualOption.statistics != null) {
                        					if(actualOption.statistics[u.controls.list[i].controlId] != undefined) {
                        						count = actualOption.statistics[u.controls.list[i].controlId].count;
                            					percentage = Math.round(count / actualOption.statistics[u.controls.list[i].controlId].sum * 100);
                        					} else {
                        						count = 0;
                                				percentage = 0;
                        					}
                            			} else {
                            				count = 0;
                            				percentage = 0;
                            			}
                        				
                                    	var g2 = $("<td class=\"statistic\">" +
                                    			"<b>" + percentage + "%</b><br/>" +
                                    			"<span>" + count + "</span>" +
                                    		"</td>");
                                    	
                                    	f.append(g2);
                                    	
                                    	total += count;
                        			}
                        	        
                        	        var g3 = $("<td class=\"last-item column-statistic\">" +
                        	        		"<b>&nbsp;</b><br/>" +
                        	        		"<span>" + total + "</span>" +
                        	        	"</td>");
                                	f.append(g3);
                        	        
                        	        f.appendTo(t);
                        	        
                        	    }
                        	    
                        		
                        		// answered
                        		$("<tr class=\"row-answered\">" +
                        				"<td colspan=\"" + colspan + "\"><b>Answered</b></td>" +
                        				"<td class=\"last-item\"><b class=\"label-answered\">" + u.answered + "</b></td>" +
                        			"</tr>").appendTo(t);
                        		
                        		// skipped
                        		$("<tr class=\"row-skipped\">" +
                        				"<td colspan=\"" + colspan + "\"><b>Skipped</b></td>" +
                        				"<td class=\"last-item\"><b class=\"label-skipped\">" + u.skipped + "</b></td>" +
                        			"</tr>").appendTo(t);
                        		
                        		
                        		
                        	}
                        	
                        	
                        	
                        	c.appendTo(v);
                        	break;
                        }
					}
				};
				G();
				
				break;
			}
			case 6 : {
				
				// date/time
				
				var c = E;
				var g = c.find('.container-control-includes');
				var F = I.controlId;
				var G = function () {
					for (var x = 0; x < response.controls.list.length; x++) {
                        if (response.controls.list[x].controlId == F) {
                        	var u = response.controls.list[x];
                        	
                        	c.find('.header-control-content')
                        	 .html(u.content.replace(/\n/g, "<br/>"));
                        	
                        	if (u.isMandatory) {
                                c.find('.label-control-mandatory').show()
                            }
                        	
                        	var t = $("<table cellpadding=\"0\" cellspacing=\"1\" border=\"0\" style=\"width: 100%\"></table>");
                        	
                        	$("<tr class=\"row-header\">" +
                    				"<td></td>" +
                    				"<td class=\"last-item\"><b>Count</b></td>" +
                    			"</tr>").appendTo(t);
                        	
                        	
                        	var z = $("<tr class=\"highlight-item row-result\">" +
                    				"<td>" +
                    					"<div>" + (u.answered != 0 ? "<a href=\"#\" title=\"Responses\" class=\"ui-icon-expand expand-responses\">Responses</a>" : "") + "</div>" + // <em style=\"color: #999\">The respondent's skipped this question.</em>
                						"<ul class=\"related-responses\" style=\"display: none\"></ul>" +
                    				"</td>" +
                    				"<td class=\"last-item column-statistic\">" +
                    					"<span>" + u.answered + "</span>" +
                    				"</td>" +
                    			"</tr>").appendTo(t);
                        	
                        	var related = $("<tr>" +
                        			"<td colspan=\"2\">" +
                        				"<table cellspacing=\"0\" cellpadding=\"0\" style=\"width: 100%\" class=\"table-related-responses\"></table>" +
                        			"</td>" +
                        		"</tr>").appendTo(t);
                        	
                        	var getMonth = function(value) {
                        		
                        		var months = {
                        				1 : "Jan",
                  				        2 : "Feb",
                  				        3 : "Mar",
                  				        4 : "Apr",
                  				        5 : "May",
                  				        6 : "Jun",
                  				        7 : "Jul",
                  				        8 : "Aug",
                  				        9 : "Sep",
                  				        10 : "Oct",
                  				        11 : "Nov",
                  				        12 : "Dec"
                        		};
                        		
                        		return months[value];
                  				    
                        	};
                        	
                        	var getTimezone = function(value) {
                        		var timeZones = {
             				    	"-12.0" : "(GMT -12:00) Eniwetok, Kwajalein", 
             				       	"-11.0" : "(GMT -11:00) Midway Island, Samoa", 
             				       	"-10.0" : "(GMT -10:00) Hawaii", 
             				       	"-9.0" : "(GMT -9:00) Alaska", 
             				       	"-8.0" : "(GMT -8:00) Pacific Time (US & Canada)", 
             				       	"-7.0" : "(GMT -7:00) Mountain Time (US & Canada)", 
             				       	"-6.0" : "(GMT -6:00) Central Time (US & Canada), Mexico City", 
             				       	"-5.0" : "(GMT -5:00) Eastern Time (US & Canada), Bogota, Lima", 
             				       	"-4.0" : "(GMT -4:00) Atlantic Time (Canada), Caracas, La Paz", 
             				       	"-3.5" : "(GMT -3:30) Newfoundland", 
             				       	"-3.0" : "(GMT -3:00) Brazil, Buenos Aires, Georgetown", 
             				       	"-2.0" : "(GMT -2:00) Mid-Atlantic",
             				       	"-1.0" : "(GMT -1:00 hour) Azores, Cape Verde Islands",
             				       	"0.0" : "(GMT) Western Europe Time, London, Lisbon, Casablanca",
             				       	"1.0" : "(GMT +1:00 hour) Brussels, Copenhagen, Madrid, Paris",
             				       	"2.0" : "(GMT +2:00) Kaliningrad, South Africa",
             				       	"3.0" : "(GMT +3:00) Baghdad, Riyadh, Moscow, St. Petersburg",
             				       	"3.5" : "(GMT +3:30) Tehran",
             				       	"4.0" : "(GMT +4:00) Abu Dhabi, Muscat, Baku, Tbilisi",
             				       	"4.5" : "(GMT +4:30) Kabul",
             				       	"5.0" : "(GMT +5:00) Ekaterinburg, Islamabad, Karachi, Tashkent",
             				       	"5.5" : "(GMT +5:30) Bombay, Calcutta, Madras, New Delhi",
             				       	"5.75" : "(GMT +5:45) Kathmandu",
             				       	"6.0" : "(GMT +6:00) Almaty, Dhaka, Colombo",
             				       	"7.0" : "(GMT +7:00) Bangkok, Hanoi, Jakarta",
             				       	"8.0" : "(GMT +8:00) Beijing, Perth, Singapore, Hong Kong",
             				       	"9.0" : "(GMT +9:00) Tokyo, Seoul, Osaka, Sapporo, Yakutsk",
             				       	"9.5" : "(GMT +9:30) Adelaide, Darwin",
             				       	"10.0" : "(GMT +10:00) Eastern Australia, Guam, Vladivostok",
             				       	"11.0" : "(GMT +11:00) Magadan, Solomon Islands, New Caledonia",
             				       	"12.0" : "(GMT +12:00) Auckland, Wellington, Fiji, Kamchatka"
                        		};
                        		return timeZones[value];
                        	};
                        	
                        	var getDateFormat = function(inputTypeId, value) {
                        		switch (inputTypeId) {
                        			case 0: {
                        				// MMM/DD
		                        		var a = JSON.parse(value);
		                        		return (a.month != undefined ? getMonth(a.month) : "" ) + "/" + (a.day != undefined ? a.day : "");
		                        	}
		                        	case 1: {
		                        		// MMM/DD/YYYY
		                        		var a = JSON.parse(value);
		                        		return (a.month != undefined ? getMonth(a.month) : "") + "/" + (a.day != undefined ? a.day : "") + "/" + (a.year != undefined ? a.year : "");
		                        	}
		                        	case 2: {
		                        		// MMM/DD/YYYY HH:MM
		                        		var a = JSON.parse(value);
		                        		return (a.month != undefined ? getMonth(a.month) : "") + "/" + (a.day != undefined ? a.day : "") + "/" + (a.year != undefined ? a.year : "") + " " + (a.hours != undefined ? a.hours : "") + ":" + (a.minutes != undefined ? a.minutes : "");
		                        	}
		                        	case 3: {
		                        		// DD/MMM
		                        		var a = JSON.parse(value);
		                        		return (a.day != undefined ? a.day : "") + "/" + (a.month != undefined ? getMonth(a.month) : "" );
		                        	}
		                        	case 4: {
		                        		// DD/MMM/YYYY
		                        		var a = JSON.parse(value);
		                        		return (a.day != undefined ? a.day : "") + "/" + (a.month != undefined ? getMonth(a.month) : "") + "/" + (a.year != undefined ? a.year : "");
									}
		                        	case 5: {
		                        		// DD/MMM/YYYY HH:MM
		                        		var a = JSON.parse(value);
		                        		return (a.day != undefined ? a.day : "") + "/" + (a.month != undefined ? getMonth(a.month) : "") + "/" + (a.year != undefined ? a.year : "") + " " + (a.hours != undefined ? a.hours : "") + ":" + (a.minutes != undefined ? a.minutes : "");
									}
		                        	case 6: {
		                        		// MMM
		                        		var a = JSON.parse(value);
		                        		return (a.month != undefined ? getMonth(a.month) : "" );
									}
		                        	case 7: {
		                            	 // DD
		                        		var a = JSON.parse(value);
		                        		return (a.day != undefined ? a.day : "");
		                        	}
		                        	case 8: {
		                            	 // YYYY
		                        		var a = JSON.parse(value);
		                        		return (a.year != undefined ? a.year : "");
		                        	}
		                        	case 9: {
		                            	 // HH:MM
		                        		var a = JSON.parse(value);
		                        		return (a.hours != undefined ? a.hours : "") + ":" + (a.minutes != undefined ? a.minutes : "");
		                        	}
		                        	case 10: {
		                            	 // TIMEZONE
		                        		var a = JSON.parse(value);
		                        		return (a.timeZone != undefined ? getTimezone(a.timeZone) : "");
		                        	}
                        		}
                        	};
                        	
                        	var isRelatedResponses = false;
                        	var relatedResponses = related.find(".table-related-responses"); //z.find("ul.related-responses");
                        	z.find('.expand-responses').click(function(e) {
                        		if(!$(this).hasClass("ui-icon-collapse")) {
                        			
                        			$(this)
                        			/*.text("Collapse responses")
                        			.attr("title", "Collapse responses")*/
                        			.addClass("ui-icon-collapse");
                        			
                        			relatedResponses.show();
                        			
                        			// get related responses
                        			if(!isRelatedResponses) {
                        				
                        				getAnswererResultsText({
                        					accountId : accountId,
                        					opinionId : I.opinionId,
                        					controlId : u.controlId,
                        					success : function(data) {
                        						
                        						for(var r = 0; r < data.list.length; r++) {
                        							
                        							$("<tr>" +
                        								"<td><a href=\"responses/" + data.list[r].sessionId + "\" title=\"\">" + getDateFormat(u.inputTypeId, data.list[r].text) + "</a></td>" +
                        							"</tr>").appendTo(relatedResponses);
                        							
                        						}
                        						
                        						isRelatedResponses = true;
                        						
                        					},
                        					error: function(error) {
                        						//
                        					}
                        				});
                        				
                        			}
                        			
                        		} else {
                        			$(this)
                        			/*.text("Expand responses")
                        			.attr("title", "Expand responses")*/
                        			.removeClass("ui-icon-collapse");
                        			
                        			relatedResponses.hide();
                        		}
                        		
                        		
                        		e.preventDefault();
                        	});
                        	
                        	
                        	// answered
                        	$("<tr class=\"row-answered\">" +
                    				"<td><b>Answered</b></td>" +
                    				"<td class=\"last-item\"><b class=\"label-answered\">" + u.answered + "</b></td>" +
                    			"</tr>").appendTo(t);
                    		
                    		// skipped
                    		$("<tr class=\"row-skipped\">" +
                    				"<td><b>Skipped</b></td>" +
                    				"<td class=\"last-item\"><b class=\"label-skipped\">" + u.skipped + "</b></td>" +
                    			"</tr>").appendTo(t);
                    		
                    		
                    		// append table to container
                    		t.appendTo(g);
                        	
                        	
                        	
                        	
                        	c.appendTo(v);
                        	break;
                        }
					}
				};
				G();
				
				break;
			}
			case 10 : {
				
				// scale
				
				var c = E;
				var g = c.find('.container-control-includes');
				var F = I.controlId;
				var H = [5, 7, 9, 0, 3, 4, 5, 6, 7, 8, 9, 10];
				
				var G = function () {
					for (var x = 0; x < response.controls.list.length; x++) {
                        if (response.controls.list[x].controlId == F) {
                        	var u = response.controls.list[x];
                        	
                        	c.find('.header-control-content')
                        	 .html(u.content.replace(/\n/g, "<br/>"));
                        	
                        	if (u.isMandatory) {
                                c.find('.label-control-mandatory').show()
                            }
                        	
                    		var t = $("<table cellpadding=\"0\" cellspacing=\"1\" border=\"0\" style=\"width: 100%\"></table>");
                    		
                    		$("<tr class=\"row-header\">" +
                    				"<td></td>" +
                    				"<td></td>" +
                    				"<td><b>Percent</b></td>" +
                    				"<td class=\"last-item\"><b>Count</b></td>" +
                    			"</tr>").appendTo(t);
                        	
                    		for (var y = u.fromScale; y < (H[u.toScale] + 1); y++) {
                    			
                    			var percentage, count;
                				if(u.statistics[y] != undefined) {
                				
                					count = u.statistics[y].count;
                    				percentage = Math.round(count / u.statistics.sum * 100);
                					
                    			} else {
                    				count = 0;
                    				percentage = 0;
                    			}
                				
                    			// highlight-item
                    			var h = $("<tr class=\"highlight-item row-result\">" +
            							"<td>" +
            								"<div class=\"row-choice " + (u.answerValue != undefined ? (y == u.answerValue ? "selected-item" : "") : "") + "\">" + y + " " + (y == 1 ? (u.fromScale != null ? (u.fromTitle != null ? u.fromTitle : "") : "") : (y == (H[u.toScale])) ? (u.toScale != null ? (u.toTitle != null ? u.toTitle : "") : "") : "") + "</div>" +
            							"</td>" +
            							"<td class=\"column-bar\">" +
                        					"<div class=\"outer-bar\"><div class=\"bar\" data-value=\"" + percentage + "\"><div class=\"bar-inner\"></div></div></div>" +
                        				"</td>" +
                        				"<td class=\"column-statistic\">" +
                        					"<span>" + percentage + "%</span>" +
                        				"</td>" +
                        				"<td class=\"last-item column-statistic\">" +
                        					"<span>" + count + "</span>" +
	                    				"</td>" +
            						"</tr>").appendTo(t);
                    			
                    		}
                    		
                    		
                    		// answered
                    		$("<tr class=\"row-answered\">" +
                    				"<td colspan=\"3\"><b>Answered</b></td>" +
                    				"<td class=\"last-item\"><b class=\"label-answered\">" + u.answered + "</b></td>" +
                    			"</tr>").appendTo(t);
                    		
                    		// skipped
                    		$("<tr class=\"row-skipped\">" +
                    				"<td colspan=\"3\"><b>Skipped</b></td>" +
                    				"<td class=\"last-item\"><b class=\"label-skipped\">" + u.skipped + "</b></td>" +
                    			"</tr>").appendTo(t);
                    		
                    		t.appendTo(g);
	                    		
                        	
                    		
                    		
                        	c.appendTo(v);
                        	break;
                        }
					}
				};
				G();
				
				break;
	
			}
			case 11: {
				
				// Hidden param
				var c = EH;
				var g = c.find('.container-control-includes');
				var F = I.controlId;
				
				var G = function () {
					for (var x = 0; x < response.controls.list.length; x++) {
                        if (response.controls.list[x].controlId == F) {
                        	var u = response.controls.list[x];
                        	
                        	c.find('.header-control-content')
                        	 .html("[" + (u.key != undefined ? u.key : "undefined") + "]"); // u.content.replace(/\n/g, "<br/>")
                        	
                        	
                        	
                        	var t = $("<table cellpadding=\"0\" cellspacing=\"1\" border=\"0\" style=\"width: 100%\"></table>");
                        	
                        	$("<tr class=\"row-header\">" +
                    				"<td></td>" +
                    				"<td class=\"last-item\"><b>Count</b></td>" +
                    			"</tr>").appendTo(t);
                        	
                        	
                        	var z = $("<tr class=\"highlight-item row-result\">" +
                    				"<td>" +
                    					"<div>" + (u.answered != 0 ? "<a href=\"#\" title=\"Responses\" class=\"ui-icon-expand expand-responses\">Responses</a>" : "") + "</div>" + // <em style=\"color: #999\">The respondent's skipped this question.</em>
                    					"<ul class=\"related-responses\" style=\"display: none\"></ul>" +
                    				"</td>" +
                    				"<td class=\"last-item column-statistic\">" +
                    					"<span>" + u.answered + "</span>" +
                    				"</td>" +
                    			"</tr>").appendTo(t);
                        	
                        	var related = $("<tr>" +
                        			"<td colspan=\"2\">" +
                        				"<table cellspacing=\"0\" cellpadding=\"0\" style=\"width: 100%\" class=\"table-related-responses\"></table>" +
                        			"</td>" +
                        		"</tr>").appendTo(t);
                        	
                        	var isRelatedResponses = false;
                        	var relatedResponses = related.find(".table-related-responses"); //z.find("ul.related-responses");
                        	z.find('.expand-responses').click(function(e) {
                        		if(!$(this).hasClass("ui-icon-collapse")) {
                        			
                        			$(this)
                        			/*.text("Collapse responses")
                        			.attr("title", "Collapse responses")*/
                        			.addClass("ui-icon-collapse");
                        			
                        			relatedResponses.show();
                        			
                        			// get related responses
                        			if(!isRelatedResponses) {
                        				
                        				getAnswererResultsText({
                        					accountId : accountId,
                        					opinionId : I.opinionId,
                        					controlId : u.controlId,
                        					success : function(data) {
                        						
                        						for(var r = 0; r < data.list.length; r++) {
                        							
                        							$("<tr>" +
                        								"<td><a href=\"responses/" + data.list[r].sessionId + "\" title=\"\">" + data.list[r].text + "</a></td>" +
                        							"</tr>").appendTo(relatedResponses);
                        							
                        						}
                        						
                        						isRelatedResponses = true;
                        						
                        					},
                        					error: function(error) {
                        						//
                        					}
                        				});
                        				
                        			}
                        			
                        		} else {
                        			$(this)
                        			/*.text("Expand responses")
                        			.attr("title", "Expand responses")*/
                        			.removeClass("ui-icon-collapse");
                        			
                        			relatedResponses.hide();
                        		}
                        		
                        		
                        		e.preventDefault();
                        	});
                        	
                        	/*
                        	if(u.answerValue != undefined) {
                            	$("<div class=\"field-free-text\">" +
                            			"<div class=\"tip\">" +
	     									"<div class=\"tip-top\"></div>" +
	     									"<label>" + u.answerValue + "</label>" +
	     								"</div>" +
                            		"</div>").appendTo(g);
                            } else {
                            	$("<div class=\"field-free-text\"><em style=\"color: #999\">The respondent skipped this question.</em></div>").appendTo(g);
                            }
                        	*/
                        	
                        	// answered
                        	$("<tr class=\"row-answered\">" +
                    				"<td><b>Answered</b></td>" +
                    				"<td class=\"last-item\"><b class=\"label-answered\">" + u.answered + "</b></td>" +
                    			"</tr>").appendTo(t);
                    		
                    		// skipped
                    		$("<tr class=\"row-skipped\">" +
                    				"<td><b>Skipped</b></td>" +
                    				"<td class=\"last-item\"><b class=\"label-skipped\">" + u.skipped + "</b></td>" +
                    			"</tr>").appendTo(t);
                    		
                    		
                    		// append table to container
                    		t.appendTo(g);
                        	
                        	
                        	
                        	
                        	c.appendTo(v);
                            break;
                        	
                        }
					}
				};
				G();
				break;
			}
			
		}
    	
    	
    	
    	
    };
    
    B(I.controlTypeId);
};