(function ($) {
    $.fn.extend({
        dataTable: function (o) {
            var j = [];
            var l = 0;
            var n = 0;
            var u = [];
			var groups = [];
            var A = $(this),
                options = $.extend({
                    tableColumns: null,
                    groupColumns : null,
                    totals : [],
                    dataSource: null,
                    total : 0,
                    change : null,
                    groupBy : null,
                    pagingStart: 5,
                    messages: {
                        processing: "Processing...",
                        loading: "Loading...",
                        noRecords: "No matching records found.",
                        noData: "No records found.",
						noGroupData : "No records found."
                    },
                    show: [5, 10, 25, 50, 100],
                    selectable: false,
                    select : null,
                    expandable: false,
                    expand: null,
                    collapse: null,
                    actions: [],
                    disableFiltering: false,
                    filters : []
                }, o);
            
            var B = A.attr('id');
            A.addClass('data-table').append("<div class=\"data-table-header\" id=\"" + B + "_header\">" + "<div class=\"" + (options.selectable ? "container-actions-selectable" : "container-actions")  + " \" id=\"" + B + "_container_actions\"></div><div class=\"container-filters\" id=\"" + B + "_container_filters\"></div>" + "</div>" + "<div class=\"data-table-body\">" + "<table id=\"" + B + "_table_view\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"></table>" + "</div>" + "<div class=\"data-table-footer\">" + "<div class=\"container-paging\" id=\"" + B + "_container_paging\">" + "<a href=\"javascript:;\" id=\"" + B + "_button_previous\" class=\"data-table-button-previous button-white\" title=\"Previous\"><span><i class=\"icon-previous\">&nbsp;</i></span></a><a href=\"javascript:;\" id=\"" + B + "_button_next\" class=\"data-table-button-next button-white\" title=\"Next\"><span><i class=\"icon-next\">&nbsp;</i></span></a>" + "</div>" + "<div class=\"container-results\" id=\"" + B + "_container_results\">" + "<div class=\"cell\">" + "<div id=\"" + B + "_label_results\"></div>" + "</div>" + "</div>" + "<div class=\"container-show-rows\" id=\"" + B + "_container_show_rows\">" + "<div class=\"cell\"><span class=\"label-field\">Show rows:</span></div>" + "<div class=\"cell select-field\">" + "<select id=\"" + B + "_select_show_rows\"></select>" + "</div>" + "</div>" + "<div class=\"container-goto\" id=\"" + B + "_container_goto\">" + "<div class=\"cell button-field\">" + "<a id=\"" + B + "_button_goto_page\" title=\"Go\" class=\"button-white\"><span>Go</span></a>" + "</div>" + "<div class=\"cell\">" + "<input id=\"" + B + "_input_goto_page\" type=\"text\" autocomplete=\"off\" class=\"goto\" />" + "</div>" + "<div class=\"cell\"><span class=\"label-field\">Go to:</span></div>" + "</div>" + "</div>");
            var C = {};
            var D = A.find("#" + B + "_header");
            var E = A.find("#" + B + "_container_actions");
            var containerFilters = A.find("#" + B + "_container_filters");
            
            //var F = A.find("#" + B + "_container_filter");
            
            var G = A.find("#" + B + "_container_results");
            var H = A.find("#" + B + "_container_goto");
            var I = A.find("#" + B + "_container_show_rows");
            var J = A.find("#" + B + "_container_paging");
            var s = A.find("#" + B + "_select_show_rows");
            
            //var M = A.find("#" + B + "_input_filter");
            
            var K = A.find("#" + B + "_button_goto_page");
            var m = A.find("#" + B + "_input_goto_page");
            var N = A.find("#" + B + "_button_previous");
            var O = A.find("#" + B + "_button_next");
            var P = A.find("#" + B + "_table_view");
            var Q = $("<tbody/>");
            
            u = options.dataSource;
            var total = options.total;
            
            var d = 0;
			
			
			
            
            var disabledActions = [];
            
			
            if (options.disableFiltering) {
                A.find('.data-table-footer').hide();
            }
            var R = function () {
                if ($("." + B + "-checkbox-row").filter(':checked').length == d) {
                    $("." + B + "-checkbox-header").prop("checked", true)
                } else {
                    $("." + B + "-checkbox-header").prop("checked", false)
                }
            };
            var S = function () {
                d = 0;
                $("." + B + "-checkbox-header").prop("checked", false);
                
                //console.log("total ------> " + total);
                
                if (total == 0) {
                    $("." + B + "-checkbox-header").prop("disabled", true)
                } else {
                    $("." + B + "-checkbox-header").prop("disabled", false)
                }
                
                if(disabledActions.length != 0) {
                	for(var t = 0; t < disabledActions.length; t++) {
                		disabledActions[t].element.addClass("ui-button-disabled");
                	}
                }
                
            };
            
            
            // last sort state
            var sort = null;
            
            var T = function (c, d, e) {
            	
            	/*
                if (d) {
                    u.sort(function (a, b) {
                        switch (e) {
                        case "string":
                            {
                                return ((a[c] < b[c]) ? -1 : ((a[c] > b[c]) ? 1 : 0));
                                break
                            }
                        case "number":
                            {
                                return ((a[c] ? a[c] : 0) - (b[c] ? b[c] : 0));
                                break
                            }
                        case "date":
                            {
                                var x = Date.parse(a[c]);
                                var y = Date.parse(b[c]);
                                if (isNaN(x) || x === "") {
                                    x = Date.parse("01/01/1970 00:00:00")
                                }
                                if (isNaN(y) || y === "") {
                                    y = Date.parse("01/01/1970 00:00:00")
                                }
                                return x - y;
                                break
                            }
                        default:
                            {
                                return ((a[c] < b[c]) ? -1 : ((a[c] > b[c]) ? 1 : 0));
                                break
                            }
                        }
                    });
                } else {
                    u.sort(function (a, b) {
                        switch (e) {
                        case "string":
                            {
                                return ((b[c] < a[c]) ? -1 : ((b[c] > a[c]) ? 1 : 0));
                                break
                            }
                        case "number":
                            {
                                return ((b[c] ? b[c] : 0) - (a[c] ? a[c] : 0));
                                break
                            }
                        case "date":
                            {
                                var x = Date.parse(a[c]);
                                var y = Date.parse(b[c]);
                                if (isNaN(x) || x === "") {
                                    x = Date.parse("01/01/1970 00:00:00")
                                }
                                if (isNaN(y) || y === "") {
                                    y = Date.parse("01/01/1970 00:00:00")
                                }
                                return y - x;
                                break
                            }
                        default:
                            {
                                return ((b[c] < a[c]) ? -1 : ((b[c] > a[c]) ? 1 : 0));
                                break
                            }
                        }
                    });
                }
                */
            	
            	
            	/*
            	var sort = { 
            		c : { 
            			"order" : (d ? "asc" : "desc") 
            		} 
            	};
                */
            	
            	
            	//var sort = {};
            	
            	sort = {};
            	sort = {
            		"key" : c,
            		"order" : (d ? "asc" : "desc")
            	};
            	/*
            	sort[c] = { 
            		"order" : (d ? "asc" : "desc")
        		};
        		*/
            	
            	
                b(l + 1);
                
            };
            var U = function (a, b, c) {
                if (a && b && c) {} else {}
            };
            var V = function (p) {
                var z = 0;
                if (p != undefined) {
                    z = p - 1
                }
                l = z;
                var e = options.pagingStart * z;
                
                //console.log("eeee -> " + e);
                
                var j = total;
                var h = 0;
                var k = 0;
                var f = total / options.pagingStart;
                for (var w = 0; w < f; w++) {
                    k++
                }
                n = k;
                Q.empty();
                
                
                for (var i = 0; i < u.length; i++) {
                	
                	//console.log(i);
                	
                    if (h < options.pagingStart) {
                    	
                        var r = $("<tr " + (options.selectable ? "class=\"" + B + "-row-" + i + "\"" : "") + " " + (options.groupColumns != null ? "class=\"group\"" : "") + " />").appendTo(Q);
                        
						if(options.groupBy != null) {
                        
							// group columns
							if(options.groupColumns != null) {
								for(var g = 0; g < options.groupColumns.length; g++) {
									var groupColumn = $("<td " + ((g == options.groupColumns.length - 1) ? "class=\"last-item\"" : "") + " " + (options.groupColumns[g].colspan != undefined ? "colspan=\"" + options.groupColumns[g].colspan + "\"" : "") + "/>");
									if (options.groupColumns[g].formatter != undefined) {
										if (typeof options.groupColumns[g].formatter == 'function') {
											groupColumn.append(options.groupColumns[g].formatter(groupColumn, u[i][options.groupColumns[g].key], u[i], u))
										} else {
											groupColumn.html(u[i][options.groupColumns[g].key])
										}
									} else {
										groupColumn.html(u[i][options.groupColumns[g].key])
									}
									if(options.groupColumns[g].style != undefined) {
										if(options.groupColumns[g].style.cell != undefined) {
											groupColumn.css(options.groupColumns[g].style.cell);
										}
									}
									groupColumn.appendTo(r);
								}
								//d++;
                        	}
                        	
							// groupBy
                			if(u[i][options.groupBy] != undefined) {
							
								//groups
								/*
								if(u[i][options.groupBy].length != 0) {
								*/
									for(var s = 0; s < u[i][options.groupBy].length; s++) {
										var groupRow = $("<tr/>").appendTo(Q);
										for(var groupRowCell = 0; groupRowCell < options.tableColumns.length; groupRowCell++) {
											var c = $("<td " + ((groupRowCell == options.tableColumns.length - 1) ? "class=\"last-item\"" : "") + " />");
											if (options.tableColumns[groupRowCell].formatter != undefined) {
												if (typeof options.tableColumns[groupRowCell].formatter == 'function') {
													c.append(options.tableColumns[groupRowCell].formatter(c, u[i][options.groupBy][s][options.tableColumns[groupRowCell].key], u[i][options.groupBy][s], u[i][options.groupBy]))
												} else {
													c.html(u[i][options.groupBy][s][options.tableColumns[groupRowCell].key])
												}
											} else {
												c.html(u[i][options.groupBy][s][options.tableColumns[groupRowCell].key])
											}
											if(options.tableColumns[groupRowCell].style != undefined) {
												if(options.tableColumns[groupRowCell].style.cell != undefined) {
													c.css(options.tableColumns[groupRowCell].style.cell);
												}
											}
											c.appendTo(groupRow);
										}
										
										if(s%2) groupRow.addClass('odd')
										else groupRow.addClass('even')
										
										groupRow.hover(function () {
											$(this).addClass('hover')
										}, function () {
											$(this).removeClass('hover')
										});
										
									}
								/*
								} else {
									var colspan = options.tableColumns.length;
									//$("<tr class=\"nop\">" + "<td colspan=\"" + colspan + "\"><b>" + options.messages.noGroupData + "</b></td>" + "</tr>").appendTo(Q);
									$("<tr>" + "<td colspan=\"" + colspan + "\" class=\"last-item\">" + options.messages.noGroupData + "</td>" + "</tr>").appendTo(Q);
								}
                				*/
								
                			}
                		
                        	
                        } else {
                        	
                        	// standart
                        	if (options.selectable) {
                                var a = $("<td style=\"padding: 4px 6px 2px\" class=\"first-item\"><input type=\"checkbox\" class=\"" + B + "-checkbox-row\" row=\"" + i + "\" /></td>").appendTo(r);
                                a.find('input[type="checkbox"]').change(function () {
                                    var g = $(this);
                                    var a = g.attr('row');
                                    if (g.prop('checked')) {
                                        $('.' + B + '-row-' + a).addClass('selected')
                                    } else {
                                        $('.' + B + '-row-' + a).removeClass('selected')
                                    }
                                    
                                    
                                    R();
                                    
                                    
                                    //console.log(options.dataSource.length);
                                    
                                    /*
                                    if(options.select != null 
                                    		&& typeof options.select == "function") {
                                    */
        	                            if (options.dataSource.length > 0) {
        	                            	var q = [];
        	                                $("." + B + "-checkbox-row").filter(':checked').each(function (i, a) {
        	                                    q.push(u[parseInt($(a).attr('row'))]);
        	                                });
        	                                
        	                                if(options.select != null 
        	                                		&& typeof options.select == "function") {
        	                                	options.select(q, u);
        	                                }
        	                                
        	                                // enable / disable actions
        	                                if(disabledActions.length != 0) {
        	                                	for(var t = 0; t < disabledActions.length; t++) {
    	                                			if(disabledActions[t].condition != undefined) {
    	                                				if(q.length == disabledActions[t].condition) {
    	                                					disabledActions[t].element.removeClass("ui-button-disabled");
    	                                				} else {
    	                                					disabledActions[t].element.addClass("ui-button-disabled");
    	                                				}
    	                                			} else {
    	                                				if(q.length != 0) {
    	                                					disabledActions[t].element.removeClass("ui-button-disabled");
    	                                				} else {
    	                                					disabledActions[t].element.addClass("ui-button-disabled");
    	                                				}
    	                                			}
        	                                	}
        	                                }
        	                                
        	                                
        	                                
        	                            }
                                    /*
                                	}
                                    */
                                    
                                });
                            }
                            
                            if (options.expandable) {
                                var colspan = options.tableColumns.length + ((options.selectable ? 1 : 0) + (options.expandable ? 1 : 0));
                                $("<tr><td colspan=\"" + colspan + "\" class=\"expandable\">&nbsp;</td></tr>").appendTo(Q);
                                var a = $("<td " + (options.selectable != true ? "class=\"first-item\"" : "") + " ><a href=\"javascript:;\" class=\"button-expand\" row=\"" + i + "\"></a></td>").appendTo(r);
                                a.find('a').click(function () {
                                    var g = $(this);
                                    var row = parseInt(g.attr('row'));
                                    var t = g.parent().parent();
                                    var panel = t.next().find('td');
                                    if (!g.hasClass('collapse')) {
                                        panel.show();
                                        g.addClass('collapse');
                                        if (options.expand != null) {
                                            if (typeof options.expand == 'function') {
                                                options.expand(panel, u[row], u);
                                            }
                                        }
                                    } else {
                                        panel.hide();
                                        g.removeClass('collapse');
                                        if (options.collapse != null) {
                                            if (typeof options.collapse == 'function') {
                                                options.collapse(panel, u[row], u);
                                            }
                                        }
                                    }
                                });
                            }
                            
                            for (var x = 0; x < options.tableColumns.length; x++) {
                            	
                            	/*
                            	if(sort != null) {
                            		if(sort[options.tableColumns[x].key] != undefined) {
                            			console.log("sortable -> " + options.tableColumns[x].key);
                            		}
                            	}
                            	*/
                                var c = $("<td " + ((x == options.tableColumns.length - 1) ? "class=\"last-item\"" : "") + ((x == 0) ? (options.selectable ? "" : (options.expandable ? "" : "class=\"first-item\"")) : "") + " />");
                                
                                if(sort != null) {
                            		if(sort[options.tableColumns[x].key] != undefined) {
                            			//console.log("sortable -> " + options.tableColumns[x].key);
                            			c.addClass("sortable");
                            		}
                            	}
                                
                                if (options.tableColumns[x].formatter != undefined) {
                                    if (typeof options.tableColumns[x].formatter == 'function') {
                                        c.append(options.tableColumns[x].formatter(c, u[i][options.tableColumns[x].key], u[i], u))
                                    } else {
                                        c.html(u[i][options.tableColumns[x].key])
                                    }
                                } else {
                                    c.html(u[i][options.tableColumns[x].key])
                                }
                                
                                /*
                                if (options.tableColumns[x].dir != undefined) {
                                    c.css({
                                        'direction': options.tableColumns[x].dir
                                    })
                                }
                                */
                                
                                // style
                                if(options.tableColumns[x].style != undefined) {
                                	if(options.tableColumns[x].style.cell != undefined) {
                                		c.css(options.tableColumns[x].style.cell);
                                	}
                                }
                                
                                c.appendTo(r)
                            }
                            if (h % 2) r.addClass('odd')
                            else r.addClass('even');
                            r.hover(function () {
                                $(this).addClass('hover')
                            }, function () {
                                $(this).removeClass('hover')
                            });
                            d++
                        	
                        	
                        }
                        
                        
                        
                        
                    }
                    h++
                }
                if (z > 0) {
                    //N.show()
                	N.removeClass('ui-button-disabled')
                    .unbind('click')
                    .click(function () {
                        b(p - 1)
                    })
                } else {
                    //N.hide()
                	N.addClass('ui-button-disabled')
                	.unbind('click');
                }
                if (z < k - 1) {
                    //O.show()
                	O.removeClass('ui-button-disabled')
                    .unbind('click')
                    .click(function () {
                        b((p == undefined ? 2 : p + 1))
                    })
                } else {
                    //O.hide()
                	O.addClass('ui-button-disabled')
                	.unbind('click');
                }
                U((e + 1), ((e + options.pagingStart) > j ? j : (e + options.pagingStart)), j);
                A.find("#" + B + "_label_results").html("<b>" + (e + 1) + "</b> - <b>" + ((e + options.pagingStart) > j ? j : (e + options.pagingStart)) + "</b> of <b>" + j + "</b>");
                m.val(l + 1)
            };
            
            
            var retrive = function(p) {
            	
            	
            	
            	//console.log(total);
            	
            	
            	S();
            	
            	if (total > 0) {
                    V(p);
                    if (options.pagingStart > 0 && total >= options.pagingStart) {
                        if (total <= options.pagingStart) {
                        	
                            //F.show();
                            
                            //H.hide();
                            I.show();
                            J.hide()
                        } else {
                            //F.show();
                            //H.show();
                            I.show();
                            J.show()
                        }
                    } else if (options.pagingStart >= total) {
                        //F.show();
                        //H.hide();
                        I.show();
                        J.hide()
                    } else {
                        if (total > 1) {
                            //H.hide();
                            I.hide();
                            J.hide()
                        } else {
                            //F.hide();
                            //H.hide();
                            I.hide();
                            J.hide()
                        }
                    }
                } else {
                    U();
                    Q.empty();
                    var colspan = options.tableColumns.length + ((options.selectable ? 1 : 0) + (options.expandable ? 1 : 0));
                    $("<tr class=\"nop\">" + "<td colspan=\"" + colspan + "\">" + options.messages.noData + "</td>" + "</tr>").appendTo(Q);
                    A.find("#" + B + "_label_results").html("<b>0</b> - <b>0</b> of <b>0</b>");
                    //F.hide();
                    //H.hide();
                    I.hide();
                    J.hide()
                }
            	
            };
            
            var b = function (p) {
            	
            	//console.log("here...........");
                
                if(options.change != undefined 
                	&& typeof options.change == 'function') {
                	
                	var z = 0;
                    if (p != undefined) {
                        z = p - 1
                    }
                    var e = options.pagingStart * z;
                	
                    //console.log("hhhhh -> " + e);
                	
                	options.change({
                		from : e,
                		size : options.pagingStart,
                		sort : sort
                	}, function(data, _total) {
                		
                		//console.log(total + "________" + data);
                		
                		options.dataSource = data;
                		options.total = _total;
                		
                		u = options.dataSource;
                		total = options.total;
                		
                		retrive(p);
                		
                	});
                	
                } else {
                
                	retrive(p);
                
                }
				
            };
            
            
            
            
            var W = null;
            if (options.tableColumns != null) {
                var X = $("<thead/>").appendTo(P);
                var Y = $("<tr/>").appendTo(X);
                if (options.selectable) {
                    
                	D.show();
                    
                    var a = $("<th class=\"checkbox-column\">" + "<input type=\"checkbox\" class=\"" + B + "-checkbox-header\" />" + "</th>").appendTo(Y);
                    /*if (total > 0) {*/
                    
                        a.find('input[type="checkbox"]').change(function () {
                        	
                            if ($(this).prop('checked')) {
                                $("." + B + "-checkbox-row").each(function (i, a) {
                                    var f = $(a);
                                    var b = f.attr("row");
                                    f.prop("checked", true);
                                    $('.' + B + '-row-' + b).addClass('selected')
                                })
                            } else {
                                $("." + B + "-checkbox-row").each(function (i, a) {
                                    var f = $(a);
                                    var b = f.attr("row");
                                    f.prop("checked", false);
                                    $('.' + B + '-row-' + b).removeClass('selected')
                                })
                            }
                            
                            /*
                            if(options.select != null 
                            		&& typeof options.select == "function") {
                            */	
	                            if (options.dataSource.length > 0) {
	                            	var q = [];
	                                $("." + B + "-checkbox-row").filter(':checked').each(function (i, a) {
	                                    q.push(u[parseInt($(a).attr('row'))])
	                                });
	                                
	                                if(options.select != null 
	                                		&& typeof options.select == "function") {
	                                	options.select(q, u);
	                                }
	                                
	                                // enable / disable actions
	                                if(disabledActions.length != 0) {
	                                	for(var t = 0; t < disabledActions.length; t++) {
                                			if(disabledActions[t].condition != undefined) {
                                				if(q.length == disabledActions[t].condition) {
                                					disabledActions[t].element.removeClass("ui-button-disabled");
                                				} else {
                                					disabledActions[t].element.addClass("ui-button-disabled");
                                				}
                                			} else {
                                				if(q.length != 0) {
                                					disabledActions[t].element.removeClass("ui-button-disabled");
                                				} else {
                                					disabledActions[t].element.addClass("ui-button-disabled");
                                				}
                                			}
	                                	}
	                                }
	                                
	                                
	                            }
	                            
	                        /*    
                            }
                            */
                        });
                        
                    /*
                    } else {
                        a.find('input[type="checkbox"]').prop('disabled', true)
                    }
                    */
                }
                if (options.expandable) {
                    var a = $("<th class=\"expandable " + (options.selectable != true ? "first-item" : "") + "\">&nbsp;</th>").appendTo(Y);
                }
                for (var i = 0; i < options.tableColumns.length; i++) {
                    var h = $("<th " + ((i == options.tableColumns.length - 1) ? "class=\"last-item\"" : "") + ((i == 0) ? (options.selectable ? "" : (options.expandable ? "" : "class=\"first-item\"")) : "") + "><div><span>" + options.tableColumns[i].label + "</span>" + (options.tableColumns[i].help != undefined ? "<span><span class=\"help-tooltip\"></span></span>" : "") + "</div></th>").appendTo(Y);
                    
                    if(options.tableColumns[i].help != undefined) {
	                    h.find(".help-tooltip")
	                    .data({ text : options.tableColumns[i].help })
	                    .mouseenter(function() {
	                    	
	                    	var newLeft = $(this).offset().left - 324;
							var newTop = $(this).offset().top;
							
							var z = newTop;
							
	                    	var helpToolTip = $("<div class=\"help-tooltip-box\">" + $(this).data("text") + "</div>");
	                    	helpToolTip.appendTo("body");
	                    	
	                    	var newHeight = helpToolTip.height() + 24;

	                    	var f = 0;
	                    	if($(window).scrollTop() > (z  - newHeight)) {
	                    		f = $(window).scrollTop();
	                    	} else {
	                    		f = z - newHeight;
	                    	}
	                    	
	                    	helpToolTip.css({
	                    		left : newLeft + "px",
								top : f + "px"
	                    	});
	                    	
						})
						.mouseleave(function() {
							$(".help-tooltip-box").remove();
						});
                    }
                    
                    // width
                    if (options.tableColumns[i].width != undefined) {
                        h.css({
                            'width': options.tableColumns[i].width
                        });
                    }
                    // style
                    if (options.tableColumns[i].style != undefined) {
                    	if(options.tableColumns[i].style.header != undefined) {
                    		h.css(options.tableColumns[i].style.header);
                    	}
                    }
                    
					if(options.groupBy != null) {
						// TODO:
					} else {
						if (options.tableColumns[i].sortable) {
							
							/*if (total > 0) {*/
								h.attr({
									"key": options.tableColumns[i].sortBy != undefined ? (options.tableColumns[i].sortBy.key != undefined ? options.tableColumns[i].sortBy.key : options.tableColumns[i].key) : options.tableColumns[i].key,
									"asc": true,
									"data-type": options.tableColumns[i].sortBy != undefined ? (options.tableColumns[i].sortBy.dataType != undefined ? options.tableColumns[i].sortBy.dataType : "string") : "string"
								}).addClass('sortable').click(function () {
									var c = $(this);
									if (W != null && W != c) {
										W.removeClass('asc desc')
									}
									W = c;
									var a = eval(c.attr("asc"));
									var b = c.attr("data-type");
									T(c.attr("key"), a, b);
									if (a) {
										c.attr("asc", false).removeClass('asc').addClass('desc')
									} else {
										c.attr("asc", true).removeClass('desc').addClass('asc')
									}
								});
								
							/*}*/
							
						}
					}
					
                }
                Q.appendTo(P);
                
                
                
                var FOOT = $("<tfoot/>");
                if(options.totals.length != 0) {
                	FOOT.appendTo(P);
                	
                	for(var _i = 0; _i < options.totals.length; _i++) {
                		
                		var FOOT_TR = $("<tr/>");
                		
                		if(_i == (options.totals.length - 1)) {
                			FOOT_TR.addClass("cFOB");
                		}
                		
                		if(options.totals[_i].length != 0) {
                			
                			var _totals = {};
                			
                			for(var _y = 0; _y < options.totals[_i].length; _y++) {
                				
                				if(options.totals[_i][_y].calculate != undefined && options.totals[_i][_y].calculate == false) {
                					
                				} else {
	                				_totals[options.totals[_i][_y].key] = 0;
	                				
	                				/*
	                				for(var _d = 0; _d < options.dataSource.length; _d++) {
	                					if(options.dataSource[_d][options.totals[_i][_y].key] != undefined) {
	                						_totals[options.totals[_i][_y].key] += options.dataSource[_d][options.totals[_i][_y].key];
	                					}
	                				}
	                				*/
	                				
                				}
                			}
                			
                			// if selectable ad 
                			if(options.selectable) {
                				$("<td/>").appendTo(FOOT_TR);
                			}
                			
                			// add td's
                			for (var x = 0; x < options.tableColumns.length; x++) {
                				
                                var c = $("<td " + ((x == options.tableColumns.length - 1) ? "class=\"last-item\"" : "") + ((x == 0) ? (options.selectable ? "" : (options.expandable ? "" : "class=\"first-item\"")) : "") + " />");
                                
                                if(options.tableColumns[x].style != undefined) {
                                	if(options.tableColumns[x].style.cell != undefined) {
                                		c.css(options.tableColumns[x].style.cell);
                                	}
                                }
                                
                                for(var _y = 0; _y < options.totals[_i].length; _y++) {
                                	if(options.tableColumns[x].key == options.totals[_i][_y].key) {
                                		
                        				if(options.totals[_i][_y].formatter != undefined 
                        						&& typeof options.totals[_i][_y].formatter == 'function') {
                        					c.html(options.totals[_i][_y].formatter(_totals));
                        				} else {
                        					c.html(_totals[options.totals[_i][_y].key]);
                        				}
                        				
                        				//console.log(JSON.stringify(_totals) + " ---- " + _y);
                        				
                                    }
                                }
                                
                                c.appendTo(FOOT_TR);
                			}
                			
                			
                			
                			FOOT_TR.appendTo(FOOT);
                			
                		}
                		
                	}
                	
                }
                
                var l = 0;
                var e = s[0].options;
                for (var f = 0; f < options.show.length; f++) {
                    if (options.pagingStart == options.show[f]) {
                        l = f
                    }
                    var k = new Option(options.show[f], options.show[f]);
                    try {
                        e.add(k)
                    } catch (ex) {
                        e.add(k, null)
                    }
                }
                s[0].selectedIndex = l;
                s.change(function () {
                    var a = $(this).find('option:selected');
                    options.pagingStart = parseInt(a.val());
                    b()
                });
                
                var g = function (e) {
                    if (e.keyCode == '13' || e.keyCode == undefined || e.keyCode == 0) {
                        var v = m.val();
                        if (v != null && v != "") {
                            if (!isNaN(parseInt(v))) {
                                var d = parseInt(v);
                                if (d > 0) {
                                    if (n >= d) {
                                        b(d)
                                    } else {
                                        m.val(l + 1)
                                    }
                                } else {
                                    m.val(l + 1)
                                }
                            } else {
                                m.val(l + 1)
                            }
                        }
                    }
                };
                K.click(g);
                m.keyup(function (e) {
                    g(e)
                });
                
                
                /*
                var Z = function (v) {
				
					if(options.groupBy != null) {
						// TODO: filter
					} else {
						var a = [];
						if (v.length > 0) {
							for (var s = 0; s < options.dataSource.length; s++) {
								var d = [];
								var q = [];
								for (var c = 0; c < options.tableColumns.length; c++) {
									d.push(String(options.dataSource[s][options.tableColumns[c].key]).toLowerCase())
								}
								q = $.grep(d, function (n, i) {
									return n.indexOf(v) != -1
								});
								if (q.length > 0) {
									a.push(options.dataSource[s])
								}
							}
						}
						if (a.length > 0) {
							u = [];
							u = a;
							b()
						} else if (v.length > 0) {
							U();
							Q.empty();
							var colspan = options.tableColumns.length + ((options.selectable ? 1 : 0) + (options.expandable ? 1 : 0));
							$("<tr class=\"nom\">" + "<td colspan=\"" + colspan + "\">" + options.messages.noRecords + "</td>" + "</tr>").appendTo(Q);
							A.find("#" + B + "_label_results").html("<b>0</b> - <b>0</b> of <b>0</b>");
							
							S();
							
							//H.hide();
							I.hide();
							J.hide()
						} else {
							u = [];
							u = options.dataSource;
							b()
						}
					}
				
                };
                */
                
                /*
                if (options.dataSource.length > 0) {
                    M.keyup(function (e) {
                        if (e.keyCode == '13') {
                            return
                        }
                        var a = $(this).val().toLowerCase().replace(/\n/g, " ").replace(/\r/g, "");
                        Z(a)
                    })
                }
                */
                
                var L = function (w, h) {
                    for (var x = 0; x < w.length; x++) {
                        if (w[x] == h) {
                            return true;
                            break
                        }
                    }
                    return false;
                };
                
                var lastCombo = null;
                var bb = function () {
                    if (options.actions.length > 0) {
                        for (var t = 0; t < options.actions.length; t++) {
                            
                        	var buttonContainer = $("<div class=\"data-table-inline-button-container\"></div>");
                        	var c = $("<a href=\"javascript:;\" action=\"" + t + "\" title=\"" + (options.actions[t].label ? options.actions[t].label : "Action" + t) + "\" class=\"button-" + (options.actions[t].color != undefined ? options.actions[t].color : "white") + "\"><span>" + (options.actions[t].icon ? "<i class=\"icon-" + options.actions[t].icon + "\">&nbsp;</i>" : "") + (options.actions[t].label ? options.actions[t].label : "Action" + t) + ((options.actions[t].actions != undefined || options.actions[t].pop != undefined) ? "<i class=\"icon-more\">&nbsp;</i>" : "") +"</span></a>").click(function () {
                                var o = $(this);
                                var b = o.attr('action');
                                
                               
                                if(options.actions[b].actions != undefined || options.actions[b].pop != undefined) {
                                	
                                	if(!o.hasClass("ui-button-disabled")) {
                                	
	                                	if(!o.hasClass('ui-button-selected')) {
	                                		if(lastCombo != null && lastCombo != o) {
	                                    		lastCombo.removeClass('ui-button-selected').removeClass('ui-button-active').parent().find('.ui-combo-popup').removeClass('active');
	                                    	}
	                                    	lastCombo = o;
	                    					o.addClass('ui-button-selected').addClass('ui-button-active').parent().find('.ui-combo-popup').addClass('active');
	                    				} else {
	                    					lastCombo.removeClass('ui-button-selected').removeClass('ui-button-active').parent().find('.ui-combo-popup').removeClass('active');
	                    				}
                                	
                                	}
                                	
                                }
                                
                                // fire
                                if (options.actions[b].fire != undefined 
                                		&& typeof options.actions[b].fire == 'function') {
                                	
                                	if(!o.hasClass("ui-button-disabled")) {
                                	
	                                	if(options.selectable) {
		                                    if (options.dataSource.length > 0) {
		                                    	
		                                    	var q = [];
		                                        $("." + B + "-checkbox-row").filter(':checked').each(function (i, a) {
		                                            q.push(u[parseInt($(a).attr('row'))])
		                                        });
		                                        //if (q.length > 0) {
		                                            options.actions[b].fire(q, u)
		                                        //}
		                                        
		                                    } else {
		                                    	
		                                    	options.actions[b].fire();
		                                    	
		                                    }
	                                	} else {
	                                		options.actions[b].fire();
	                                	}
                                	
                                	}
                                    
                                }
                                
                                
                            }).appendTo(buttonContainer);
                            
                            // disabled
                            if(options.actions[t].disabled != undefined) {
                            	if(options.actions[t].disabled) {
                            		c.addClass("ui-button-disabled");
                            		
                            		if(options.selectable) {
	                            		// global
	                                    disabledActions.push({
	                                    	element : c,
	                                    	condition : options.actions[t].condition
	                                    });
                                    
                            		}
                            		
                            	}
                            }
                            
                            // has actions
                            if(options.actions[t].actions != undefined) {
                            	buttonContainer.addClass('ui-button-dropdown');
                            	var z = $("<div class=\"ui-combo-popup\"></div>").appendTo(buttonContainer);
                            	
                            	// actions
                            	if(options.actions[t].actions.length > 0) {
                            		var a = $("<ul class=\"ml\"></ul>").appendTo(z);
                            		for(var d = 0; d < options.actions[t].actions.length; d++) {
                            				
                            			
                            			var G = $("<li " + (options.actions[t].actions[d].active != undefined ? (options.actions[t].actions[d].active == false ? "style=\"display: none\"" : "") : "") + " />");
                            			var v = $("<a parentaction=\"" + t + "\" subaction=\"" + d + "\">" + options.actions[t].actions[d].label + "</a>").click(function() {
                            				var m = $(this);
                            				
                            				var pa = m.attr('parentaction');
                            				var sa = m.attr('subaction');
                                            
                                           
                                            if(options.actions[pa].actions[sa].fire != undefined 
                                            	&& typeof options.actions[pa].actions[sa].fire == 'function') {
                                            	
                                            	if(options.selectable) {
				                                    if (options.dataSource.length > 0) {
				                                    	
				                                    	var q = [];
				                                        $("." + B + "-checkbox-row").filter(':checked').each(function (i, a) {
				                                            q.push(u[parseInt($(a).attr('row'))])
				                                        });
				                                        if (q.length > 0) {
				                                            options.actions[pa].actions[sa].fire(q, u)
				                                        }
				                                        
				                                    }
			                                	} else {
			                                		options.actions[pa].actions[sa].fire();
			                                	}
                                            	
                                            	//options.actions[pa].actions[sa].fire();
                                            	
                                            	// hide combo popup
                                            	lastCombo.removeClass('ui-button-selected').removeClass('ui-button-active').parent().find('.ui-combo-popup').removeClass('active');
                                            }
                                            
                                            
                            			}).appendTo(G);
                            			
                            			G.appendTo(a);
                            			
                            			
                            		}
                            	}
                            	
                            }
                            
                            // has pop
                            if(options.actions[t].pop != undefined 
                        			&& typeof options.actions[t].pop == 'function') {
                            	buttonContainer.addClass('ui-button-dropdown');
                            	var z = $("<div class=\"ui-combo-popup\" style=\"padding: 10px;\"></div>").appendTo(buttonContainer);
                            	z.append(options.actions[t].pop());
                        	}
                            
                        	
                        	buttonContainer.appendTo(E);
                        }
                        
                        D.show();
                        
                        $('.ui-button-dropdown a.button-white, .ui-combo-popup').click(function() { return false; });
                        jQuery(document).click(function(){
            				if(lastCombo != null) {
            					if(lastCombo.hasClass('ui-button-selected')) {
            						lastCombo.removeClass('ui-button-selected').removeClass('ui-button-active').parent().find('.ui-combo-popup').removeClass('active');
            					}
            				}
            			});
                        
                    } else {
                    	E.hide();
                    }
                };
                
                
                // custom filters
                var addFilters = function() {
                	if(options.filters.length > 0) {
                		
                		for (var t = 0; t < options.filters.length; t++) {
                			var w = $("<div class=\"row-filter-select\"><div class=\"cell cell-filter-select\"></div><div class=\"cell\">" + options.filters[t].title + "</div></div>").appendTo(containerFilters);
                			var d = $("<select action=\"" + t + "\"></select>").appendTo(w.find(".cell-filter-select"));
                			d.change(function() {
                				var o = $(this);
                				var b = o.attr("action");
                				if(options.filters[b].change != undefined 
                						&& typeof options.filters[b].change == 'function') {
                					options.filters[b].change(o);
                				}
                			});
                			var q = d[0].options;
                			for(var i = 0; i < options.filters[t].options.length; i++) {
	                			var k = new Option(options.filters[t].options[i].caption, options.filters[t].options[i].value);
								try {
									q.add(k)
								} catch (ex) {
									q.add(k, null)
								}
                			}
                			
                		}
                		
                		D.show();
                	} else {
                		containerFilters.hide();
                	}
                };
                
                addFilters();
                
                
                bb();
                b()
            }
            return {
            	change : function() {
            		b();
            	},
                deleteRecords: function (q) {
                    var v = [];
                    for (var i = 0; i < options.dataSource.length; i++) {
                        if (!L(q, options.dataSource[i])) {
                            v.push(options.dataSource[i])
                        }
                    }
                    options.dataSource = v;
                    u = options.dataSource;
                    M.val("");
                    if (d == 1 && l > 0) {
                        b(l)
                    } else if (d <= options.pagingStart && l > 0) {
                        if (q.length < d) {
                            b(l + 1)
                        } else {
                            b(l)
                        }
                    } else {
                        b(l + 1)
                    }
                },
                addRecords: function (q) {
                    for (var i = 0; i < q.length; i++) {
                        options.dataSource.push(q[i]);
                    }
                    u = options.dataSource;
                    M.val("");
                    b();
                },
                updateRecords: function (q) {
                	options.dataSource = [];
                	for (var i = 0; i < q.length; i++) {
                        options.dataSource.push(q[i]);
                    }
                    u = options.dataSource;
                    M.val("");
                	
                	b();
                },
                getRecordSet: function () {
                    return u
                },
                getSelectedRecords: function() {
                	var q = [];
                	if(options.selectable) {
                        if (options.dataSource.length > 0) {
                            $("." + B + "-checkbox-row").filter(':checked').each(function (i, a) {
                                q.push(u[parseInt($(a).attr('row'))])
                            });
                        }
                	}
                	return q;
                	
                }
            }
        }
    })
})(jQuery);