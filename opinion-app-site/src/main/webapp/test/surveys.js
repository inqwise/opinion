var getList = function(params) {
    	
	var obj = {
		opinions : {
			getList : {
				from : params.from,
				size : params.size,
				order_by : params.order_by,
				order_by_direction : params.order_by_direction,
				filter : params.filter,
				from_date : params.fromDate,
				to_date : params.toDate,
				accountId : params.accountId,
				opinionTypeId : 1 /*survey*/
			}
		}
	};

	$.ajax({
		/*
		type: "POST",
		*/
        url: servletUrl,
        data: { 
        	rq : JSON.stringify(obj),
        	timestamp : $.getTimestamp()  
        },
        dataType: "json",
        /*
        processData: false,
        xhrFields : {
            withCredentials: true
        },
        */
        success: function (data, status) {
        	
        	console.log(data);
        	
        	if(data.opinions.getList != undefined) {
	        	if(data.opinions.getList.error != undefined) {
					
	        		/*
	        		errorHandler({
						error : data.opinions.getList.error	
					});
	        		*/
	        		
	        		if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.opinions.getList);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.opinions.getList);
					}
				}
        	} else {
        		if(params.error != undefined 
						&& typeof params.error == 'function') {
					params.error();
				}
        	}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
};


var texte = "";

var defaultState = {
	fromDate : null,
	toDate : null
};

// default columns set
var customizedColumns = {
	metrics : [
		{
			label : "Attributes",
			key : "attributes"
		}
	],
	columns : [
		{ key : "opinion_id", label : "Survey Id", sortable : true, metric : "attributes", fixed : true },
		{ key : "title", label : "Title", sortable: true, metric : "attributes", fixed : true, formatter : function(cell, value, record) { return "<a href=\"#" + record.opinion_id + "\">" + record.title + "</a>" } },
		{ key : "cnt_started_opinions", label: "Responses", metric : "attributes", added : true, sortable: true, sortBy : { key : 'cnt_started_opinions', dataType: "number" }, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } }, formatter : function(cell, value, record, source) {
			return record.cnt_started_opinions;
		}},
		{ key : "cnt_finished_opinions", label: "Completed", metric : "attributes", added : true, help : "A Completed responses indicates that the respondents clicked Finish on the last page of the survey.", sortable: true, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } }, formatter : function(cell, value, record, source) {
			return $.addCommas(record.cnt_finished_opinions);
		}},
		{ key : "completion_rate", label : "Completion Rate", metric : "attributes", added : true, sortable : true, help : "A Completion Rate is the number of completed responses divided by the number of total responses and expressed in percentage.<br/><br/><table><tr><td rowspan=\"2\">Completion Rate =&nbsp;</td><td align=\"center\" style=\"border-bottom: 1px solid #000\">Completed responses</td></tr><tr><td align=\"center\">Responses</td></tr></table><br/><strong>Example:</strong> If you have five Completed responses from 1000 total responses, then your Completion Rate is 0.5%.", style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } }, formatter: function(cell, value, record, source) {
			return (value != undefined ? value.toFixed(2) : 0) + "%";
        }},
		{ key : "cnt_partial_opinions", label: "Partial", metric : "attributes", added : true, sortable : true, help: "A Partial responses indicates that the respondents entered at least one answer, but they did not click Finish on the last page of the survey.", style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } }, formatter : function(cell, value, record, source) {
			return record.cnt_partial_opinions != undefined ? $.addCommas(record.cnt_partial_opinions) : 0;
		}},
		{ key : "cnt_disqualified_opinions", label: "Disqualified", sortable: true, metric : "attributes", style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } }, formatter : function(cell, value, record, source) {
			return record.cnt_disqualified_opinions != undefined ? $.addCommas(record.cnt_disqualified_opinions) : 0;
		}},
		{ key : "avg_time_taken_sec", label: "Avg. Time Taken", metric : "attributes", sortable: true, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } }, formatter : function(cell, value, record, source) {
        	if(record.avg_time_taken_sec != undefined) {
        		var timeTaken = (record.avg_time_taken_sec.days != 0 ? record.avg_time_taken_sec.days + (record.avg_time_taken_sec.days > 1 ? " days, " : " day, ") : "") +
        		(record.avg_time_taken_sec.hours != 0 ? record.avg_time_taken_sec.hours + (record.avg_time_taken_sec.hours > 1 ? " hours, " : " hour, ") : "") + 
        		(record.avg_time_taken_sec.minutes != 0 ? record.avg_time_taken_sec.minutes + (record.avg_time_taken_sec.minutes > 1 ? " mins, " : " min, ") : "") +
        		(record.avg_time_taken_sec.seconds != 0 ? record.avg_time_taken_sec.seconds + (record.avg_time_taken_sec.seconds > 1 ? " secs" : " sec") : "");
        		
        		if(record.avg_time_taken_sec.days == 0 
					&& record.avg_time_taken_sec.hours == 0
					&& record.avg_time_taken_sec.minutes == 0
					&& record.avg_time_taken_sec.seconds == 0) {
           			timeTaken = "Less than sec";
           		}
        		
        		return timeTaken;
        	}
        }},
        { key : "last_start_date", label : "Last Response Date", metric : "attributes", added: true, sortable : true, formatter: function(cell, value, record, source) {
			if(record.last_start_date) {
				var left = record.last_start_date.substring(record.last_start_date.lastIndexOf(" "), " ");
				var right = record.last_start_date.replace(left, "");
				return left + "<b class=\"hours\">" + right + "</b>";
			} 
        }},
        { key : "modify_date", label : "Modify Date", metric : "attributes", added: true, sortable: true, formatter: function(cell, value, record, source) {
			if(record.modify_date) {
				var left = record.modify_date.substring(record.modify_date.lastIndexOf(" "), " ");
				var right = record.modify_date.replace(left, "");
				return left + "<b class=\"hours\">" + right + "</b>";
			} 
        }}
	]
};

var getFilterOperatorsByKey = function(key) {
	var operators = [];
	switch(key) {
		case 'opinion_id' :
			operators = [
			    { label : "is equal to", name : "equalTo", fieldType : "text" }, // =
			    { label : "is not equal to", name : "notEqualTo", fieldType : "text" } // !=
			];
			break;
		case 'title' :
			operators = [
				{ label : "is equal to", name : "equalTo", fieldType : "text" }, // = is
				{ label : "is not equal to", name : "notEqualTo", fieldType : "text" }, // != is not
				{ label : "includes", name : "includes", fieldType : "text" }, // like - contains
				{ label : "not includes", name : "notIncludes", fieldType : "text" } // not like - does not contains
			];
			break;
		case 'cnt_started_opinions' :
			operators = [
				{ label : "is equal to", name : "equalTo", fieldType : "text" }, // =
			    { label : "is not equal to", name : "notEqualTo", fieldType : "text" }, // !=
			    { label : "greater than", name : "greaterThan", fieldType : "text" }, // >
			    { label : "less than", name : "lessThan", fieldType : "text" }, // <
			    { label : "less than or equal to", name : "lessThanOrEqualTo", fieldType : "text" }, // <=
			    { label : "greater than or equal to", name : "greaterThanOrEqualTo", fieldType : "text" } // >=
			];
			break;
	}
	return operators;
};

var customFilters = [
	{
		name : "My filter",
		type : "AND", // OR
		conditions : [
			{ key : "opinion_id", operator : "equalTo", value : "173" },
			{ key : "cnt_started_opinions", operator : "greaterThan", value : "30" }
		]
	}
];

function getOperatorSelect() {
    var select = $("<select class=\"operator\"/>");
    select.change(onOperatorSelectChange);
    return select;
}

function onOperatorSelectChange() {
    var $this = $(this);
    var option = $this.find("> :selected");
    var container = $this.parents("li");
    var fieldSelect = container.find(".field");
    var currentValue = container.find(".value");
    var val = currentValue.val();

    console.log(option.data("fieldType"));
    console.log(option.data("value"));
    console.log("DO AFTER");

    switch(option.data("fieldType")) {
        case "text":
            var fieldValue = $("<input type=\"text\" class=\"value\" />");
            if(option.data("value") != undefined) {
                fieldValue.val(option.data("value"));
            }
            $this.after(fieldValue);
            break;
    }

    currentValue.remove();

}

var listFilterConditions = $('ul.list-filter-conditions');
var addCondition = function(condition) {

    var filterOperators = null;
    var temporaryCondition = {
        key : null,
        operator : null,
        value : null
    };

	var listItem = $("<li/>");

    // select, select, input

	// conditions
	var fieldSelect = $("<select class=\"field\"/>");
    _.each(customizedColumns.columns, function(column) {
        var option = $("<option value=\"" + column.key + "\">" + column.label + "</option>");
        if(condition != undefined) {
            console.log(column.key + "______" + condition.key);
            if (column.key == condition.key) {
                option.prop('selected', true);
            }
        }
        option.appendTo(fieldSelect);
    });

    var operatorSelect = getOperatorSelect();


    fieldSelect.appendTo(listItem);
    fieldSelect.change(function() {
        console.log("FIELD CHANGED");

        var key = $(this).val();

        temporaryCondition.key = key;

        operatorSelect.empty();
        //var _operator = null;
        filterOperators = getFilterOperatorsByKey(key);
        _.each(filterOperators, function(operator) {
            console.log(operator.fieldType);
            var option = $("<option value=\"" + operator.name + "\">" + operator.label + "</option>").data("fieldType", operator.fieldType);
            if(condition != undefined) {
                if(operator.name == condition.operator) {
                    option.prop('selected', true).data("value", condition.value);

                    //_operator = operator;
                }
            }
            option.appendTo(operatorSelect);
        });
        operatorSelect.change();

    });

    operatorSelect.appendTo(listItem);


    fieldSelect.change();


    /*
	// operators with values
	console.log(condition);
	console.log(getFilterOperatorsByKey(condition.key));
	console.log(condition.operator);
	console.log(condition.value);
	*/

    // remove condition
    var removeButton = $("<a href=\"#\">Remove</a>");
    removeButton.click(function(e) {
        e.preventDefault();

        console.log("REMOVE THIS CONDITION");
        listItem.remove();

    });
    removeButton.appendTo(listItem);

	
	listItem.appendTo(listFilterConditions);
};

var filter = null;
function buildFilter() {
	filter = null;
	filter = {
		type : "AND",
		conditions : [
			/* { key : "opinion_id", operator : "equalTo", value : "1128" } */
		]
	};
	
	$('ul.list-filter-conditions li').each(function(i, el) {
		var element = $(el);
		filter.conditions.push({
			key : element.find('.field').val(),
			operator : element.find('.operator').val(),
			value : element.find('.value').val()
		});
		
	});
	
	console.log(JSON.stringify(filter));
};

var getTableCustomFilters = function(filter) {

    listFilterConditions.empty();
	
	_.each(customFilters, function(item) {
		// set conditions
		_.each(item.conditions, function(condition) {
			//console.log(condition);
			addCondition(condition);
		});
		
	});
	
};

/*
// saved columns set's
var customizedColumnsSets = [
	{
		name : "My Columns 1",
		columns : [
			{ key : "referrer" },
			{ key : "ip_address" },
			{ key : "start_date" }
		]
	},
	{
		name : "Test 2",
		columns : [
			{ key : "os" },
			{ key : "browser" }
		]
	}
];
*/

var currentColumns = [];
var currentFields = [];

var getTableColumns = function() {
	
	var columns = [];
	var fields = [];
	
	/*
	for(var f = 0; f < fixedColumns.length; f++) {
		columns.push(fixedColumns[f]);
	}
	*/
	
	if(currentColumns.length != 0) {
		for(var f = 0; f < customizedColumns.columns.length; f++) {
			if(customizedColumns.columns[f].fixed != undefined && customizedColumns.columns[f].fixed == true) {
				columns.push(customizedColumns.columns[f]);
				fields.push(customizedColumns.columns[f].key);
			}
		}
		for(var c = 0; c < currentColumns.length; c++) {
			columns.push(currentColumns[c]);
			fields.push(currentColumns[c].key);
		}
	} else {
		
		// currentColumns - empty
		
		for(var f = 0; f < customizedColumns.columns.length; f++) {
			if(customizedColumns.columns[f].fixed != undefined && customizedColumns.columns[f].fixed == true) {
				columns.push(customizedColumns.columns[f]);
				fields.push(customizedColumns.columns[f].key);
			}
			if(customizedColumns.columns[f].added != undefined && customizedColumns.columns[f].added == true) {
				columns.push(customizedColumns.columns[f]);
				fields.push(customizedColumns.columns[f].key);
			}
		}
		
	}
	
	return {
		columns : columns,
		fields : fields
	}
};


var table = null;
var renderTable = function() {
	
	var columns = getTableColumns().columns;
	
	// total : total, // new
	
	$('#table_surveys').empty();
	table = $('#table_surveys').dataTable({ 
		tableColumns : columns,
		messages: {
            processing: "Processing...",
            loading: "Loading...",
            noRecords: "No matching records found.",
            noData: "You don't have surveys. Click \"+ Create Survey\" above to create one.",
            noGroupData : "No records found.",
            of : "of",
            showRows : "Show rows:"
        },
		/*
		dataSource : responses,
		total : total,
		*/
		change : function(params, callback) {
			
			console.log(params.sort != undefined);
			
			
			//loader.show();
			
			getList({
        		accountId : accountId,
        		from : params.from,
        		size: params.size,
        		order_by : params.sort != undefined ? params.sort.key : 'opinion_id',
        		order_by_direction : params.sort != undefined ? (params.sort.order == 'asc' ? true : false) : false, // ASC || DESC
        		//filter : texte, // text_search 
        		fromDate : defaultState.fromDate,
				toDate : defaultState.toDate,
        		success : function(data) {
        			
        			//loader.hide();
        			
        			console.log(data);
        			
        			var total = 100;
        			
        			callback(data.list, total);
        			
        		},
        		error : function(error) {
        			
        			//loader.hide();
        			
        			console.log(error);
        		}
        	});
			
		},
		filtered : [
		    { key : 'title', formatter : function() {
	    		return "Total - all filtered surveys"; // #fff6c3 - yellow
	    	}},
	    	{ key : 'cnt_started_opinions' },
	    	{ key : 'cnt_finished_opinions' },
	    	{ key : 'completion_rate' },
	    	{ key : 'cnt_partial_opinions' },
	    	{ key : 'cnt_disqualified_opinions' }
		],
		totals : [
		   [
		    	{ key : 'title', formatter : function() {
		    		return "Total - all filtered surveys";
		    	}}
		   ],
		   [
		    	{ key : 'title', formatter : function() {
		    		return "Total - all surveys" 
		    	}},
		    	{ key : 'cnt_started_opinions' },
		    	{ key : 'cnt_finished_opinions' },
		    	{ key : 'completion_rate' },
		    	{ key : 'cnt_partial_opinions' },
		    	{ key : 'cnt_disqualified_opinions' }
		   ]       
		],
		/*
		totals : [
		   [
				{ key : "id", calculate : false, formatter : function(totals) {
					return "Total - all responses";
				}},
				{ key : "status" },
		    	{ key : "time_taken", calculate : false, formatter : function(totals) {
		    		return "--";
		    	}}
		   ]
		],
		*/
		pagingStart: 5,
		selectable :  true,
		actions : [
			{ 
				label : "Delete",
				disabled : true,
				fire : function(records, source) {
					if(records.length > 0) {
						
						var list = [];
						$(records).each(function(index) {
							list.push(records[index].id);
						});
						
						console.log("delete ->> " + list);
						
					}
				}
			}
		]
	});
};

var loader = null;


$(document)
.ready(function() {
	
	loader = new lightLoader({
		message : "Loading..."
	});	
	
	/*
	var formGraph = $('#form_graph');
	$('#button_show_hide_graph').click(function() {
		if(!formGraph.is(":visible")) {
			formGraph.show();
		} else {
			formGraph.hide();
		}
	});
	*/
	
	// text search
	texte = $('#text_search').val();
	$('#text_search').keydown(function(e) {
		var code;
        if (!e) var e = window.event;
        if (e.keyCode) code = e.keyCode;
        else if (e.which) code = e.which;

     	// enter
        if(code == 13) {
        	if($('#text_search').is(':focus')) {
        		$('#button_search').trigger('click');
        	}
        }
	});
	$('#button_search').click(function(e) {
		texte = $('#text_search').val();
		//search();
		e.preventDefault();
	});
	
	// daterange picker
	$("#daterange").dateRange({
		ranges : [
		    { description: "Custom", value : { from : 0, to : 0 }, isCustom : true },
			{ description: "Today", value : { from : 0 }, isDefault : true },
			{ description: "Yesterday", value: { from : -1 } },
			{ description: "Last 7 days", value : { from : -7, to : 0 } },
			{ description: "Last 14 days", value : { from : -14, to : 0 } },
			{ description: "Last 30 days", value: { from : -30, to : 0 } },
			{ description: "All time", value : { from: -730, to : 0 } } // from : -364
		],
		change : function(data) {
			
			defaultState.fromDate = (data.fromDate != null ? data.fromDate.format(dateFormat.masks.isoDate) + " 00:00" /*"T00:00:00.000Z"*/ : undefined);
			defaultState.toDate = data.toDate.format(dateFormat.masks.isoDate) + "  23:59"; //"T23:59:59.000Z";
			
			search();
			
		},
		ready : function(data) {
			
			defaultState.fromDate = (data.fromDate != null ? data.fromDate.format(dateFormat.masks.isoDate) + " 00:00" /*"T00:00:00.000Z"*/ : undefined);
			defaultState.toDate = data.toDate.format(dateFormat.masks.isoDate) + " 23:59"; // "T23:59:59.000Z";
		
		}
	});
	
	// filter
	var formFilter = $("#form_filter");
	$("#button_filter")
	.menuButton({
		label : "Filter",
		disabled : false,
		actions : [
		    {
		    	label : "Create filter",
		    	value : 1,
				click : function(button) {
					//
					if(!formFilter.is(":visible")) {

                        getTableCustomFilters();

						formFilter.show();
					} else {
						formFilter.hide();
					}
					
				}
		    }
		],
		change : function(value) {
			//
		}
    });
	
	// add another
	$('#button_filter_add_another').click(function(e){
		e.preventDefault();
		
		console.log("add another");
		
		addCondition();
		
	});
	
	$("#button_filter_apply").click(function(e) {
		e.preventDefault();
		
		buildFilter();
		
		
		// TODO:
		//formFilter.hide();
		
		
	});
	$("#button_filter_cancel").click(function(e) {
		e.preventDefault();
		formFilter.hide();
	});
	
	
	// columns
	var formCustomizeColumns = $("#form_customize_colums");
	$("#button_columns")
	.menuButton({
		label : "Columns",
		disabled : false,
		actions : [
		    {
		    	label : "Customize columns",
		    	value : 1,
				click : function(button) {
					//
					if(!formCustomizeColumns.is(":visible")) {
						formCustomizeColumns.show();
						
						//console.log("remove...... -->> " + $(this).attr("data-value"));
						// update value added to false
						// refresh -> columns -?
						// after click on Apply -> close customize columns and refresh datatable
						
					} else {
						formCustomizeColumns.hide();
					}
				}
		    }
		],
		change : function(value) {
			//
		}
    });
	
	$("#button_customize_columns_apply").click(function(e) {
		e.preventDefault();
		
		//console.log(showColumns);
		
		currentColumns = showColumns;

		// rebuild table
		renderTable();
		
		formCustomizeColumns.hide();
		
	});
	
	$("#button_customize_columns_cancel").click(function(e) {
		e.preventDefault();
		formCustomizeColumns.hide();
	});
	
	function removeFromArray(array, from, to) {
        var rest = array.slice((to || from) + 1 || array.length);
        array.length = from < 0 ? array.length + from : from;
        return array.push.apply(array, rest);
	};
	
	function removeFromShowColumns(key, callback) {
		
		for(var d = 0; d < showColumns.length; d++) {
			if(showColumns[d].key == key) {
				
				removeFromArray(showColumns, d);
				
				$("ul.list-metric-columns li").each(function(i, el) {
					
					if($(el).attr("data-value") == key) {
						$(el).removeClass("added");
					}
					//console.log($(el).attr("data-value") + "________" + key);
				
				});
				
				//lastMetric.trigger("click");
				
				if(callback != undefined 
						&& typeof callback == 'function')
					callback();
				
				break;
			}
		}
		
	};
	
	function addToShowColumns(key) {
		
		for(var u = 0; u < customizedColumns.columns.length; u++) {
			if(customizedColumns.columns[u].key == key) {
				
				showColumns.push(customizedColumns.columns[u]);
				
				$("ul.list-metric-columns li").each(function(i, el) {
					if($(el).attr("data-value") == key) {
						$(el).addClass("added");
					}
				});
				
				// ->>
				var custCol = $("<li data-value=\"" + customizedColumns.columns[u].key + "\">" + customizedColumns.columns[u].label + "<a href=\"#\" title=\"Remove\">Remove</a></li>").appendTo("ul.list-customize-columns");
				custCol.find("a").click(function(e) {
					e.preventDefault();
					
					var _this = $(this).closest("li");
					removeFromShowColumns(_this.attr("data-value"), function() {
						_this.remove();
					});
					
				});
				
				break;
			}
		}
		
	};
	
	function getColumnsByMetric(metric) {
		
		$("ul.list-metric-columns").empty();
		
		var isAdded = function(key) {
			var found = false;
			for(var n = 0; n < showColumns.length; n++) {
				if(showColumns[n].key == key) {
					found = true;
					break;
				}
			}
			return found;
		};
		
		for(var c = 0; c < customizedColumns.columns.length; c++) {
			if(customizedColumns.columns[c].metric == metric) {
				
				if(customizedColumns.columns[c].fixed == undefined) {
					var metricColumn = $("<li " + (isAdded(customizedColumns.columns[c].key) ? "class=\"added\"" : "") + " data-value=\"" + customizedColumns.columns[c].key + "\">" + customizedColumns.columns[c].label + "<span>Added</span><a href=\"#\" title=\"Add\">Add</a></li>").appendTo("ul.list-metric-columns");
					metricColumn.find("a").click(function(e) {
						
						e.preventDefault();
						
						//console.log("to add ->>" + $(this).closest("li").attr("data-value"));
						
						addToShowColumns($(this).closest("li").attr("data-value"));
						
					});
				}
			}
		}
	};
	
	// fixed
	for(var h = 0; h < customizedColumns.columns.length; h++) {
		if(customizedColumns.columns[h].fixed != undefined && customizedColumns.columns[h].fixed == true) {
			var fixedColumn = $("<li>" + customizedColumns.columns[h].label + "</li>").appendTo("ul.list-fixed-columns");
		}
	}
	
	
	
	var showColumns = [];
	function setColumns(columns) {
		
		
		showColumns = [];
		$("ul.list-customize-columns").empty();
		
		var getColumn = function(key) {
			var col = null;
			for(var x = 0; x < customizedColumns.columns.length; x++) {
				if(customizedColumns.columns[x].key == key) {
					col = customizedColumns.columns[x];
					break;
				}
			}
			return col;
		};
		
		if(columns != undefined) {
			// merge saved columns with defaultColumns
			for(var a = 0; a < columns.length; a++) {
				var col = getColumn(columns[a].key);
				if(col != null) {
					showColumns.push(col);
				}
			}
		} else {
			for(var x = 0; x < customizedColumns.columns.length; x++) {
				if(customizedColumns.columns[x].added != undefined && customizedColumns.columns[x].added == true) {
					showColumns.push(customizedColumns.columns[x]);
				}
			}
		}
		
		
		// show columns
		if(showColumns.length != 0) {
			for(var j = 0; j < showColumns.length; j++) {
				var custCol = $("<li data-value=\"" + showColumns[j].key + "\">" + showColumns[j].label + "<a href=\"#\" title=\"Remove\">Remove</a></li>").appendTo("ul.list-customize-columns");
				custCol.find("a").click(function(e) {
					e.preventDefault();
					
					var _this = $(this).closest("li");
					removeFromShowColumns(_this.attr("data-value"), function() {
						_this.remove();
					});
					
				});
			}
		}
		
		
		
		var reorderColumns = function(key) {
			
			for(var u = 0; u < customizedColumns.columns.length; u++) {
				if(customizedColumns.columns[u].key == key) {
					showColumns.push(customizedColumns.columns[u]);
					break;
				}
			}
			
		};
		
		// sortable
		$("ul.list-customize-columns").sortable({
			placeholder: "list-customize-columns-drag",
			axis: "y",
    		cursor: "pointer",
    		opacity: 0.7,
    		stop : function(event, ui) {
    			
    			showColumns = [];
    			
    			$("ul.list-customize-columns li").each(function(i, el) {
    				reorderColumns($(el).attr("data-value"));
    			});
    			
    		}
		});
		
		
		// draw metrics
		drawMetrics();
		
	};
	
	
	// default columns
	setColumns();
	//setColumns(customizedColumnsSets[0].columns);
	
	
	// metrics
	function drawMetrics() {
		
		var lastMetric = null;
		for(var g = 0; g < customizedColumns.metrics.length; g++) {
			
			// metrics
			var itemMetric = $("<li data-value=\"" + customizedColumns.metrics[g].key + "\"><a href=\"#\" title=\"" + customizedColumns.metrics[g].label + "\">" + customizedColumns.metrics[g].label + "</a></li>").appendTo("ul.list-metrics");
			itemMetric.click(function(e) {
				e.preventDefault();
				
				if(lastMetric != null && lastMetric != $(this)) {
					lastMetric.removeClass("active");
				}
				
				$(this).addClass("active");
				lastMetric = $(this);
				
				console.log("change metric to -> " + $(this).attr("data-value"));
				getColumnsByMetric($(this).attr("data-value"));
			});
			
		}
		
		// select first
		$("ul.list-metrics li:first").trigger("click");
	
	}
	
	
	renderTable();
	
	function search() {
		
		
		// fields
		var fields = getTableColumns().fields;
		
		console.log(fields);
		
		//renderTable();
		
		// trigger change event
		table.change();
		
	}
	
});