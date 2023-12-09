
//pad a string with zeroes to a certain length
var padNumber = function( n, len ) {
    var str = '' + n;
    while (str.length < len) {
        str = '0' + str;
    }
    return str;
};

//takes in a hex color and a percentage and returns a lighter or darker shade of the color
var percentColor = function( color, percent ) {
    var color_arr = color.split( "" );

    var red = parseInt( ( color_arr[1] + color_arr[2] ), 16 );
    var green = parseInt( ( color_arr[3] + color_arr[4] ), 16 );
    var blue = parseInt( ( color_arr[5] + color_arr[6] ), 16 );
    if( percent > 1 ) {
        if( red == 0 ) {
            red = 60;
        }
        if( green == 0 ) {
            green = 60;
        }
        if( blue == 0 ) {
            blue = 60;
        }
    }
    if( red * percent < 255 && red * percent > 0 ) {
        red = padNumber( Math.floor( red * percent ).toString( 16 ), 2 );
    } else {
        if( red * percent >= 255 ) {
            red = "FF";
        } else {
            red = "00";
        }
    }
    if( green * percent < 255 && green * percent > 0 ) {
        green = padNumber( Math.floor( green * percent ).toString( 16 ), 2 );
    } else {
        if( green * percent >= 255 ) {
            green = "FF";
        } else {
            green = "00";
        }
    }
    if( blue * percent < 255 && blue * percent > 0 ) {
        blue = padNumber( Math.floor( blue * percent ).toString( 16 ), 2 );
    } else {
        if( blue * percent >= 255 ) {
            blue = "FF";
        } else {
            blue = "00";
        }
    }
    
    return "#" + red + "" + green + "" + blue + "";
};

		
//pass in a color and a slider value and get back a tuple with start and end colors
//of a gradient - the slider value is a number between 0 and 100
//greater than 50 -> convex gradient, less than 50 -> concave
var computeGradient = function( color, slider_value ) {
    var color_arr = color.split( "" );

    var red = parseInt( (color_arr[1] + color_arr[2]), 16 );
    var green = parseInt( (color_arr[3] + color_arr[4]), 16 );
    var blue = parseInt( (color_arr[5] + color_arr[6]), 16 );

    var convex, red_start, green_start, blue_start, percent;
        
    if( slider_value >= 50 ) {
        convex = 1;
        percent = 1 + ( slider_value - 50 ) / 100;
    } else {
        convex = 0;
        percent = 1 + ( 50 - slider_value ) / 100;
    }
    if( percent * red > 255 ) {
        red_start = "FF";
    } else {
        red_start = padNumber( Math.floor(percent * red).toString( 16 ), 2 );
    }
    if( percent * green > 255 ) {
        green_start = "FF";
    } else {
        green_start = padNumber( Math.floor(percent * green).toString( 16 ), 2 );
    }
    if( percent * blue > 255 ) {
        blue_start = "FF";
    } else {
        blue_start = padNumber( Math.floor(percent * blue).toString( 16 ), 2 );
    }

    if( convex ) {
        percent = ( 100 - (slider_value - 50) ) / 100;
    } else {
        percent = ( 100 - (50 - slider_value) ) / 100;
    }

    var red_end = padNumber( Math.floor(percent * red).toString( 16 ), 2 );
    var green_end = padNumber( Math.floor(percent * green).toString( 16 ), 2 );
    var blue_end = padNumber( Math.floor(percent * blue).toString( 16 ), 2 );

    var start, end;
    if( convex ) {
        start = "#" + red_start + "" + green_start + "" + blue_start + "";
        end = "#" + red_end + "" + green_end + "" + blue_end + "";
    } else {
        start = "#" + red_end + "" + green_end + "" + blue_end + "";
        end = "#" + red_start + "" + green_start + "" + blue_start + "";
    }
    return [start, end];
}





var colors = [];
var hexDigits = new Array("0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f");

// function to convert hex format to a rgba color
var rgbatohex = function(rgba) {
    rgba = rgba.match(/^rgba\((\d+),\s*(\d+),\s*(\d+),\s*([\d.]+)\)$/);
    return "#" + hex(rgba[1]) + hex(rgba[2]) + hex(rgba[3]);
}

var rgbtohex = function(rgb) {
	/*
	rgb = rgb.match(/^rgb\((\d+),\s*(\d+),\s*(\d+)\)$/);
	return "#" + hex(rgb[1]) + hex(rgb[2]) + hex(rgb[3]);
	*/
	
	if (rgb.search("rgb") == -1){
        return rgb;
	} else {
        rgb = rgb.match(/^rgba?\((\d+),\s*(\d+),\s*(\d+)(?:,\s*(\d+))?\)$/);
        function hex(x) {
             return ("0" + parseInt(x).toString(16)).slice(-2);
        }
        return "#" + hex(rgb[1]) + hex(rgb[2]) + hex(rgb[3]); 
	}

}

// function to get opacity from rgba
var rgbaOpacity = function(rgba) {
    rgba = rgba.match(/^rgba\((\d+),\s*(\d+),\s*(\d+),\s*([\d.]+)\)$/);
    return rgba[4];
}

function hex(x) {
	return isNaN(x) ? "00" : hexDigits[(x - x % 16) / 16] + hexDigits[x % 16];
}


//takes a hex color and computes the grayscale value
var grayValue = function( color ) {
    var color_arr = color.split( "" );
    
    var red = parseInt( ( color_arr[1] + color_arr[2] ), 16 );
    var green = parseInt( ( color_arr[3] + color_arr[4] ), 16 );
    var blue = parseInt( ( color_arr[5] + color_arr[6] ), 16 );

    return ( red + green + blue ) / 3;
};





// "global", 
var alpha = [ "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" ];
// "global": 0, 
var num = { "a": 1, "b": 2, "c": 3, "d": 4, "e": 5, "f": 6, "g": 7, "h": 8, "i": 9, "j": 10, "k": 11, "l": 12, "m": 13, "n": 14, "o": 15, "p": 16, "q": 17, "r": 18, "s": 19, "t": 20, "u": 21, "v": 22, "w": 23, "x": 24, "y": 25, "z": 26 };



var isIFrameReady = false;

var timerId = 0;
var movingColor = 0;
var showStartEnd = [];


var iframe = null;
var styleBlock = null;
var tokens = [];
var styleArray = [];
var undoLog = [];
var redoLog = []; 









(function(jQuery) {
	themeEditor = function(givenOptions) {
		
		
		var options = $.extend( {
			complete : null
		}, givenOptions);
		
		
		
		
		
		
		// on undo, backup CSS before the undo and push onto the redoLog
		// apply previous CSS from undoLog and reinit
		var undo = function() {
		    var style = undoLog.pop();
		    if( style ) {
		        redoLog.push( styleBlock.text() );
		        styleBlock.text( style );
		        initStyleArray();
		        
		        // TR.correctNumberOfSwatches();
		        updateFormValues();
		        
		    }
		};

		// on redo, backup CSS before the redo and push onto the undoLog
		// apply last action's CSS from redoLog and reinit
		var redo = function() {
		    var style = redoLog.pop();
		    if( style ) {
		        undoLog.push( styleBlock.text() );
		        styleBlock.text( style );
		        initStyleArray();
		        
		        // TR.correctNumberOfSwatches();
		        updateFormValues();
		        
		    }
		};
		
		
		// work horse of the app
		// this function runs through the token array and pushes out any changes that may have been made
		var updateAllCSS = function( skip_log ) {
			
		    var skip_log = skip_log || false,
		        new_style = [];
		    
		    if( !skip_log ) {
		    	
		        undoLog.push( styleBlock.text() );
		        redoLog = [];
		    }
		    
		    for( var i in tokens ) {
		        var t = tokens[i];
		        if( t.type == "placeholder" ) {
		            new_style.push( " " + styleArray[t.ref] + " " + "/*{" + t.ref + "}*/" );
		        } else {
		            new_style.push( t.value );
		        }
		    }
		    new_style = new_style.join( "" );
		    
		    styleBlock.text(new_style);
		    
		};
		
		var travelTo = function( version, importing ) {
			
			// merge( target, version, importing );
			
			// after merge do pass
			// passTheme( version );
			
			
			/*
			$.ajax({
				url: "jqm/" + version + "/jqm.starter.theme.css",
				dataType: "text",
				success: function( target ) {
					merge( target, version, importing );
					if ( version != TR.version ) {
						passTheme( version );
					} else {
						// $( "#upload" ).dialog( "close" );
					}
				}
			});
			*/
			
		};

		var passTheme = function( version ) {
			var form = $( '<form style="display: none" action="?\
				ver=' + version + '" method="post"><input name="style" value="' + encodeURI( styleBlock.text() ) + '" /></form>' );
			$( "body" ).append( form );
			form.submit();
		};

		// takes current theme and updates styleDict, takes target CSS file (version we are traveling to)
		// and merges the two and writes out to styleBlock, ready for travel
		var merge = function( css, version, importing ) {
			
			if( importing ) {	
				//takes in the imported CSS and puts values into styleArray
				styleBlock.text($('#load-css').val());
				initStyleArray();
				// correctNumberOfSwatches();
			}
			
			//reads the target CSS file, adds/subtracts appropriate number of swatches and tokenizes it
			var swatchCount = getNumberOfSwatches();
			styleBlock.text( fixNumberOfSwatches( css, swatchCount ) );
			initStyleArray( "refresh" );
			
			//styleArray has correct values and tokens array has the appropriate stylesheet
			//so we write out
			updateAllCSS( true );
		};

		// matches the target CSS file to have the right number of swatches in the current theme
		var fixNumberOfSwatches = function( style, count ) {
			var diff = count - 1;
			if( diff > 0 ) {
				var start = style.search( /\/\* A.*\n-*\*\// ),
		        	end = style.search( /\/\* Structure /),
		 			swatch_a = style.substring( start, end );
				
				for( var i = 0; i < diff; i++) {
					var letter = String.fromCharCode( i + 98 );
			
					var temp_style_template = swatch_a.replace( /-a,/g, "-" + letter + "," ).replace( /-a\s/g, "-" + letter + " " )
						.replace( /-a\:/g, "-" + letter + ":" ).replace( /{a-/g, "{" + letter + "-" ).replace( /\/\*\sA/, "/* " + letter.toUpperCase() );
					
					style = style.replace( /\/\*\sStructure\s/, temp_style_template + "\n\n/* Structure " );
				}
			}
			return style;
		};

		// gets number of swatches in the current theme by enumerating dictionary keys in styleDict
		var getNumberOfSwatches = function() {
			var count = 1;
			for( var i in styleArray ) {
				var letter = i.split("-")[0];
				if( letter.length == 1 ) {
					var num = letter.charCodeAt(0) - 96;
					if ( num > count ) {
						count = num;
					}
				}
			}
			return count;
		};
		
		//initStyleArray is used to initialize the array of tokens and the TR.styleArray
		//if the refresh flag is passed it refreshes the token array and does not initialize the TR.styleArray
		var initStyleArray = function( refresh ) {
			
		    tokens = [];
		    refresh = refresh || "fresh";

		    //if we're not refreshing the styleArray
		    //we start from scratch
		    if( refresh != "refresh" ) {
		        styleArray = [];
		    }

		    var style = styleBlock.text();
		    
		    
		    escaped_style = style.replace( /\n/g, "%0A" );
		    escaped_style = escaped_style.replace( /\t/g, "%09" );
		    
		    var reg = new RegExp( "(?:font-family:)[^/\\*]+/\\*{[^\\*/]*}\\*/|\\s*\\S*\\s*/\\*{[^\\*/]*}\\*/" ),
		        reference = "",
		        length = 0,
		        index = -1,
		        i = 0;
		    
		    while( style.length > 0 ) {
		        var temp = reg.exec( style ) + "";
		        
		        length = temp.length;
		        reference = /{.*}/.exec( temp ) + "";
		        reference = reference.substr( 1,reference.length-2 );
		        index = style.search( reg );
		        
		        if( index != -1 ) {
		            tokens[i++] = {
		                value: style.substr( 0, index ), 
		                type: "string"
		            };
		            tokens[i++] = {
		                value: style.substr( index, length ).trim(), 
		                type: "placeholder", 
		                ref: reference
		            };
		            
		            // update styleArray
		            if( refresh != "refresh" ) {
		                styleArray[reference] = tokens[i-1].value.replace( /\/\*.*\*\//, "" ).trim();
		            }
		            //cut off string and continue
		            style = style.substring( index+length ); 
		        } else {
		            tokens[i++] = {
		                value: style,
		                type: "string"
		            };
		            style = "";
		        }
		        
		    }
		};
		
		//updates all form values from the style array
		var updateFormValues = function( $this ) {
			
		    //check to make sure form values are up to date
		    var tab_num = 1; // parseInt($this.attr("id").replace(/[^0-9]/g, ""));
		    var swatch = alpha[ tab_num - 1 ];

		    for( var i in styleArray ) {
		        var key = i.split( "-" )[0];
		        if ( key == swatch ) {
		        	
		            var field = /*$this*/ $('#list_theme_properties').find( "input[data-name=" + i + "]" ),
		            	select = /*$this*/ $('#list_theme_properties').find( "select[data-name=" + i + "]" ),
		                slider = /*$this*/ $('#list_theme_properties').find( ".slider[data-name=" + i + "]" ),
		                value = styleArray[i].trim(),
		                colorwell = field.hasClass("colorwell") ? 1 : 0;

		            if( i.indexOf("font-family") != -1 ) {
		                field.val( styleArray[i].replace(/font-family:\s*/, "") );
		            
					/*
					else if( i.indexOf("global-icon") != -1 ) {
		                if( i == "global-icon-set" ) {
		                    field = $this.find( "select[data-name=global-icon-set]" );
		                    if( value.indexOf("black") != -1 ) {
		                        field.val( "black" );
		                    } else {
		                        field.val( "white" );
		                    }
		                } else {
		                    if( i != "global-icon-color" && i != "global-icon-shadow" ) {
		                        var with_disc = $( "select[data-name=global-icon-disc]" ),
		                            disc_color = $this.find( "[data-name=global-icon-disc].colorwell" ),
		                            disc_opacity = $this.find( "[data-name=global-icon-disc]:not(.colorwell)" );

		                        if( value.indexOf( "transparent" ) != -1 ) {
		                            with_disc.val( "without_disc" );
		                        } else {
		                            var hex = rgbatohex( value ),
		                                opac = rgbaOpacity( value );
		                            disc_color.val( hex ).css( "background-color", hex );
		                            disc_opacity.val( parseFloat(opac) * 100 );
		                            with_disc.val( "with_disc" );
		                            if( grayValue(hex) < 127 ) {
		                                disc_color.css( "color", "#ffffff" );
		                            } else {
		                                disc_color.css( "color", "#000000" );
		                            }
		                        }
		                    }
		                }
					*/
		            
		            } else if(i.indexOf("font-weight") != -1 
		            		|| i.indexOf("border-style") != -1) {
					
		            	// TODO: 
		            	select.val(value);
		            	
		            } else if( i.indexOf("box-shadow") != -1) {
		            	
		                if( i.indexOf( "-size" ) == -1 ) {
		                	
	                		var shadow_color = /*$this*/ $('#list_theme_properties').find( "[data-name=" + i + "].colorwell" ),
		                        shadow_opacity = /*$this*/ $('#list_theme_properties').find( "[data-name=" + i + "]:not(.colorwell)" ),
		                        hex = rgbatohex( value ),
		                        opac = rgbaOpacity( value );
		                    
		                    shadow_color.val( hex ).css( "background-color", hex );
		                    shadow_opacity.val( parseFloat(opac) * 100 );
		                    
		                    if( grayValue(hex) < 127 ) {
		                        shadow_color.css( "color", "#ffffff" );
		                    } else {
		                        shadow_color.css( "color", "#000000" );
		                    }
		                } else {
		                    field.val( value );
		                }
		                
		            } else {
		            	
		                field.val( value );
		                
		                if( colorwell ) {
		                	
		                    if( grayValue(value) < 127 ) {
		                        field.css( "color", "#ffffff" );
		                    } else {
		                        field.css( "color", "#000000" );
		                    }
		                    field.css( "background-color", value );
		                    
		                    if( slider.html() ) {
		                        //if there is some property on slider object, then there exists a slider
		                        slider.find( "a" ).css({
		                            "background": value
		                            /*"border-color": value*/
		                        });
		                    }
		                }
		                
		            }
		            
		            
		        }
		    }
		};
		
		//updates the newest recent color. Used when adding a color with the color picker
		var updateMostRecent = function( newColor ) {
		    var found = 0,
		        most_recent = $( "#most-recent-colors .color-drag:first" );

		    if( most_recent.length ) {
		        most_recent.css( "background-color", newColor );
		    }
		};
		
		// adds a most-recent-color to the five right-most draggables in the quickswatch panel
		var addMostRecent = function( color ) {
		    var found = 0,
		        most_recents = $( "#most-recent-colors .color-drag" );
		    
		    most_recents.each( function() {
		        if( color == $(this).css("background-color") ) {
		            found = 1;
		        }
		    });
		    
		    if( !found ) {
		        var last = null;
		        most_recents.each(function() {
		            var $this = $( this ),
		                temp = $this.css( "background-color" )
		                last = last ? last : color;
		            $this.css( "background-color", last );
		            if( last != temp ) {
		                $this.draggable( "enable" );
		                $this.removeClass( "disabled" );
		            }
		            last = temp;
		        });
		    }
		};
		
		
		//When a color is dropped this function applies a color to the element
		//automatically detecting things like 
		var applyColor = function( color, prefix ) {
		    var color_arr = color.split( "" ),
		        red = parseInt( (color_arr[1] + color_arr[2]), 16 ),
		        green = parseInt( (color_arr[3] + color_arr[4]), 16 ),
		        blue = parseInt( (color_arr[5] + color_arr[6]), 16 ),
		        gray = grayValue( color ),
		        swatch = prefix.substr( 0, 1 ),
		        element = prefix.substr( 2, prefix.length - 2 );
		        
		    element = prefix.split( "-" );
		    element[0] = "";
		    element = element.join( "" );
		    
		    
		    
		    
		    
		    
		    
		    //if we're on button down then the gradient gets flipped
		    var start, end;
		    if( element != "bdown" ) {
		        start = computeGradient(color, 50)[0];
		        end = computeGradient(color, 50)[1];
		    } else {
		        /*
		    	start = TR.computeGradient(color, 50)[1];
		        end = TR.computeGradient(color, 50)[0];
		        */
		    }
		    
		    if( element != "link" ) {
		    	
		        //anything but a link has gradients and text color
		        $( "input[data-name=" + prefix + "-background-color]" ).val( color ).css( "background-color", color );
		        $( "input[data-name=" + prefix + "-background-start]" ).val( start ).css( "background-color", start );
		        $( "input[data-name=" + prefix + "-background-end]" ).val( end ).css( "background-color", start );
		        
		        $( ".slider[data-name=" + prefix + "-background-color]" ).slider( {value: 50} ).find( "a" ).css({"background": color /*, "border-color": color*/ }); // value : 50
		        
		        styleArray[prefix + "-background-start"] = start;
		        styleArray[prefix + "-background-end"] = end;
		        styleArray[prefix + "-background-color"] = color;

		        
		        //special border for content body elements
		        if( element != "body" ) {
		            $( "input[data-name=" + prefix + "-border]" ).val( color ).css( "background-color", color );
		            styleArray[prefix + "-border"] = color;
		        } else {
		            var border;
		            if( gray > 90 ) {
		                border = percentColor( color, 0.55 );
		            } else {
		                border = percentColor( color, 1.4 );
		            }
		            $( "input[data-name=" + prefix + "-border]" ).val( border ).css( "background-color", border );
		            styleArray[prefix + "-border"] = border;
		        }
				
		        
		        /*
		        // contrast calculation for text color
		        if( gray > 150 ) {
		            $( "input[data-name=" + prefix + "-color]" ).val( "#000000" ).css( "background-color", "#000000" );
		            $( "input[data-name=" + prefix + "-shadow-color]" ).val( "#eeeeee" ).css( "background-color", "#eeeeee" );
		            styleArray[prefix + "-color"] = "#000000";
		            styleArray[prefix + "-shadow-color"] = "#eeeeee";
		        } else {
		        	
		            $( "input[data-name=" + prefix + "-color]" ).val( "#ffffff" ).css( "background-color", "#ffffff" );
		            $( "input[data-name=" + prefix + "-shadow-color]" ).val( "#444444" ).css( "background-color", "#444444" );
		            styleArray[prefix + "-color"] = "#ffffff";
		            styleArray[prefix + "-shadow-color"] = "#444444";
		        }
		       	*/
		    }
		    
		    
		    
		    // we've updated styleArray now update the CSS
		    // if it's a button, we don't call updateAllCSS until all three are updated in the styleArray
		    if( element != "bdown" && element != "bhover" ) {
		        updateAllCSS();
		    }
		    
		    
		};
		
		//function used to open a specific accordion
		//the element passed in is one selected by the incpector or when dropping a color
		var selectElement = function( element ) {
		    var data_theme = element.attr( "data-theme" );
		    var form = element.attr( "data-form" );
		    
		    if( !form ) {
		        return;
		    }
		    
		    if( !data_theme ) {
		        data_theme = form.split( "-" );
		        data_theme = data_theme[data_theme.length - 1];
		    }

		    /*
		    if( element.hasClass("ui-btn-active") ) {
		        data_theme = "global";
		        form = "ui-btn-active";
		    }
		    if( form == "ui-link" ) {
		        data_theme = "global";
		    }
		    if( form == "ui-icon" ) {
		        data_theme = "global";
		    }
			*/
			
		    
		    
		    /*
		    $( "#tabs" ).tabs( "select", num[data_theme] );  
		    */
		    
		    
		    
		    
		    
		    
		    
		    
		    setTimeout(function() {
		        
		    	$( ".accordion" ).each(function() {
		    		if( $(this).attr("data-form") == form ) {
		                if( $(this).accordion("option", "active" ) === false) {
		                    $( this ).accordion( "activate", 0 );
		                }
		            } else if( $(this).accordion("option", "active") !== false ) {
		                $( this ).accordion( "activate", false );
		            }
		    	});
		    	
		    	/*
		    	$( "#tab" + (num[data_theme]+1) ).find( ".accordion" ).each(function() {
		            if( $(this).attr("data-form") == form ) {
		                if( $(this).accordion("option", "active" ) === false) {
		                    $( this ).accordion( "activate", 0 );
		                }
		            } else if( $(this).accordion("option", "active") !== false ) {
		                $( this ).accordion( "activate", false );
		            }
		        });
		        */
		    	
		    }, 200);
		    
		    
		};
		
		// intialize mouseover and click events for Inspector
		var initInspector = function() {
			
		    //click on an element with inspector-on and select element in panel
		    iframe.find( "[data-form]" ).unbind("mouseup").mouseup(function(e) {
		        if( $("#inspector-button").hasClass("active") ) {
		            e.stopPropagation();
		            selectElement( $(this) );        
		            iframe.find( "#highlight" ).show();
		        }
		    });
		    
		    //highlight moves as you mouseover things
		    iframe.find( "[data-form]" ).unbind("mouseover").bind( "mouseover", function() {
		        var $this = $( this );
		        if( $("#inspector-button").hasClass("active") ) {
		            var left = $this.offset().left, top = $this.offset().top,
		                width = $this.outerWidth(), height = $this.outerHeight();
		            
		            var parent = this;
		            
		            iframe.find( "#highlight" ).unbind("mousemove").mousemove(function(e) { 
		                var highlight = $( this );
		                $( "[data-form]", parent ).each(function() {
		                    var $form = $( this ),
		                        left = $form.offset().left, top = $form.offset().top,
		                        width = $form.outerWidth(), height = $form.outerHeight(),
		                        right = left + width, bottom = top + height;

		                    if( e.pageX <= right && e.pageX >= left && e.pageY <= bottom && e.pageY >= top ) {
		                        highlight.css({
		                            "z-index": 20, 
		                            "position": "absolute", 
		                            "top": ( top - 3 ) + "px", 
		                            "left": ( left - 3 ) + "px", 
		                            "width": width + "px", 
		                            "height": height + "px" 
		                            /*"border": "1px solid #52a4f7"*/
		                        })
		                        .show()
		                        .find(".inspector-highlight-wrap").css({ "top" : (top > 30 ? "-20px" : "0px") })
		                        .find(".inspector-highlight-label").text($form.attr("data-form"));
		                        
		                    }
		                });
		            });
		            
		            
		            
		            $( "iframe" ).contents().find( "#highlight" ).css({
		                "z-index": 20, 
		                "position": "absolute", 
		                "top": (top-3) + "px", 
		                "left": (left-3) + "px", 
		                "width": width + "px", 
		                "height": + height + "px"
		                /*"border": "1px solid #52a4f7"*/
		            })
		            .show()
		            .find(".inspector-highlight-wrap").css({ "top" : (top > 30 ? "-20px" : "0px") })
		            .find(".inspector-highlight-label").text($this.attr("data-form"));
		            
		            
		            
		            
		        }
		    });
		    
		    iframe.find( "#highlight" ).unbind("mousedown").mousedown(function() {
		        if( $("#inspector-button").hasClass("active") ) {
		            $( this ).css( "z-index", -1 );
		        }
		    });
		    
		    iframe.unbind("mouseleave").bind( "mouseleave", function() {
		        iframe.find( "#highlight" ).hide();
		        iframe.find( "#highlight" ).unbind( "mousemove" );
		    });
		};
		
		
		//large mouseup event detects if the user is dragging a color
		//if so it runs throught the dom to see if the mouse position is above
		//an acceptable element, if so it calls applyColor on that element
		$(document).mouseup(function(e) {
			
			var classtokey = {
				".ui-body-" : "-body",
				".ui-wrapper-" : "-wrapper",
				".ui-header-" : "-header",
				".ui-content-" : "-content",
				".ui-button-" : "-button",
				".ui-progress-bar-" : "-progress-bar",
				".ui-survey-heading-" : "-survey-heading",
				".ui-page-heading-" : "-page-heading",
				".ui-page-" : "-page",
				".ui-question-heading-" : "-question-heading",
				".ui-question-content" : "-question-content",
				".ui-question-" : "-question",
				".ui-footer-" : "-footer",
				".ui-btn-up-": "-bup",
				".ui-btn-down-": "-bdown",
				".ui-link": "-link",
				".ui-btn-active": "-active"
			}

			//different alpha array - no global
			var alphabet = [ "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" ];
			
			var droppables = [".ui-survey-heading-", ".ui-page-heading-", ".ui-question-content-", ".ui-question-heading-", ".ui-question-", ".ui-header-", ".ui-button-", ".ui-progress-bar-", ".ui-page-", ".ui-content-", ".ui-footer-", ".ui-wrapper-", ".ui-body-" ]; // [ ".ui-btn-up-", ".ui-btn-down-", ".ui-bar-", ".ui-body-" ];
			
			if(movingColor) {
				movingColor = 0;
				var frame_offset = $( "iframe" ).offset();
				// var el_offset = iframe.find( ".ui-bar-a" ).offset();
				
				var element = null;
				var el_class = "";
				//loop through possible classes and if mouse is above one,
				//end the loop (order is based on precedence)
				for( var i = 0; i < droppables.length; i++ ) {
	                for( var j = 0; j < alphabet.length; j++ ) {
	                    iframe.find( droppables[i] + alphabet[j] ).each(function() {
	                        var $this = $( this );
	                        var top = frame_offset.top + $this.offset().top - iframe.scrollTop(), 
	                        	bottom = top + $this.outerHeight(),
	                            left = frame_offset.left + $this.offset().left, 
	                            right = left + $this.outerWidth();
	                        if( e.pageX <= right && e.pageX >= left && e.pageY <= bottom && e.pageY >= top ) {
	                            el_class = droppables[i] + alphabet[j];
	                            element = $this;
	                            return false;
	                        }
	                    });
	                    if( element ) {
	                        break;
	                    }
	                }
	                if( element ) {
	                    break;
	                }
	            }
				
				/*
				//another loop to check if over a link or the active state
				TR.iframe.find( ".ui-link, .ui-btn-active" ).each(function() {
					var $this = $( this );
					var top = frame_offset.top + $this.offset().top - TR.iframe.scrollTop(), bottom = top + $this.outerHeight(),
						left = frame_offset.left + $this.offset().left, right = left + $this.outerWidth();
					if( e.pageX <= right && e.pageX >= left && e.pageY <= bottom && e.pageY >= top ) {
						if( $this.hasClass("ui-btn-active") ) {
							el_class = ".ui-btn-active";
						} else {
							el_class = ".ui-link";
						}
						element = $this;
						return false;
					}
				});
				*/
				
				if( !element ) {
					//if no acceptable element was found, drop the color
					$( ".ui-draggable .ui-draggable-dragging" ).trigger( "drop" );
				} else {
					
					
					//alert(element + "______" + el_class);
					
					
					//else apply the color and select the element in the panel
					var swatch = element.attr( "data-theme" );
					
					/*
					if( el_class == ".ui-btn-active" ) {
						swatch = "global";
					}
					if( el_class.indexOf(".ui-bar") != -1 ) {
						swatch = element.attr( "data-swatch" );
					}
					*/
					
					
					var color = $( ".color-drag.ui-draggable-dragging" ).css( "background-color" );
					if( color != "transparent" ) {
						color = rgbtohex( color );
					}
				
					for( var i in classtokey ) {
						if( el_class.indexOf(i) != -1 ) {
							applyColor( color, swatch + classtokey[i] );
							break;
						}
					}
				
					
					selectElement(element);

					
					setTimeout(function() {
						//store color in most recent colors
						var color = element.css("background-color");
						addMostRecent( color );
					}, 100);
					
					
					
					
				}
				
			}
			
		});
		
		var initControls = function() {
			
			
			
			iframe = $( "iframe" ).contents();

			//#style is where the initial CSS file is put
			//copy it to #styleblock so its in the scope of the iframe
			styleBlock = iframe.find( "#styleblock" );
			styleBlock.text( $("#style").text() );
			
			
			
			// tabs
			var lastTab = null;
			$('#tabs li').each(function(i, el) {
				if(i == 0) {
					lastTab = $(el);
					lastTab.addClass("active");
					$(lastTab.find("a").attr("href")).show();
				}
				$(el).click(function(event) {
					if(lastTab != null && lastTab != $(this)) {
						lastTab.removeClass("active");
						$(lastTab.find("a").attr("href")).hide();
					}
					lastTab = $(this);
					lastTab.addClass("active");
					$(lastTab.find("a").attr("href")).show();
					
					if(lastTab.find("a").attr("href") == "#form_edit_theme") {
						$("#panel_colors").show();
						
					} else {
					
						$("#panel_colors").hide();
						
						if($( "#inspector-button" ).hasClass("active")) {
							$( "#inspector-button" ).trigger("click");
						}
						
						$("#themes li[theme=" + selectedThemeId + "]").trigger("click");
						
					}
					
					event.preventDefault();
				});
			});

			// branding
			var quickswatch = $( "#quickswatch" );
		    quickswatch.find( ".color-drag:not(.disabled)" ).each(function() {
		    	colors.push( rgbtohex($(this).css("background-color")) );
			});
			
		    // accordion
			$( ".accordion" ).accordion({ 
				header: "h3", 
				active: false,
				animated: false,
				clearStyle: true, 
				collapsible: true 
			});
			
			/*
			collapsible: true,
				active: false,
				autoheight : false,
				animated: false,
				alwaysOpen: false,
				clearStyle: true,
			 */
			
		    
		    // slider
			$( ".slider" ).slider({ 
				max : 80, 
				value: 50 
			});

			// radius sliders has different range of values
			$( ".slider[data-type=radius]" ).slider("option", {
				max: 2,
				step: .1
			});
			
			// undo
		    $( "#undo" ).click(function() {
		        undo();
		    });
		    
		    // redo
		    $( "#redo" ).click(function() {
		        redo();
		    });
		    
		  	// lightness and saturation sliders
			$( "#lightness_slider" ).slider({
				width: 100,
				max: 50,
				min: -40
			});
			
			$( "#saturation_slider" ).slider({
				width: 100,
				value: 0,
				min: -100,
				max: 100
			});
			
			$( "#saturation_slider" ).bind("slide", function() {
				var sat_val = $( this ).slider( "value" );
				var sat_percent = sat_val / 100;	
				if( sat_percent >= 0 ) {
					var sat_str = "+=";
				} else {
					var sat_str = "-=";
					sat_percent = sat_percent - (2 * sat_percent);
				}
				
				var lit_val = $( "#lightness_slider" ).slider( "value" );
				var lit_percent = lit_val / 100;	
				if( lit_percent >= 0 ) {
					var lit_str = "+=";
				} else {
					var lit_str = "-=";
					lit_percent = lit_percent - (2 * lit_percent);
				}
				
				
				for( var i = 1; i< colors.length; i++ ) {
					var orig = $.Color( colors[i] );
					quickswatch.find( ".color-drag:nth-child(" + (i + 1) + ")" )
						.css("background-color", orig.saturation(sat_str + sat_percent).lightness(lit_str + lit_percent) );
				}
			});
			
			$( "#lightness_slider" ).bind("slide", function() {
				var sat_val = $( "#saturation_slider" ).slider( "value" );
				var sat_percent = sat_val / 100;	
				if( sat_percent >= 0 ) {
					var sat_str = "+=";
				} else {
					var sat_str = "-=";
					sat_percent = sat_percent - (2 * sat_percent);
				}
				
				var lit_val = $( this ).slider( "value" );
				var lit_percent = lit_val / 100;	
				if( lit_percent >= 0 ) {
					var lit_str = "+=";
				} else {
					var lit_str = "-=";
					lit_percent = lit_percent - (2 * lit_percent);
				}
				
				for( var i = 1; i < colors.length; i++ ) {
					var orig = $.Color( colors[i] );
					quickswatch.find( ".color-drag:nth-child(" + (i + 1) + ")" )
						.css("background-color", orig.saturation(sat_str + sat_percent).lightness(lit_str + lit_percent) );
				}
			});
			
			//draggable colors
		    $( ".color-drag" ).draggable({
		        appendTo: "body",
		        revert: true,
		        revertDuration: 200,
		        opacity: 1,
		        containment: "document",
		        cursor: "move",
		        helper: "clone",
		        zIndex: 3000,
		        iframeFix: true,
		        drag: function() {
		            movingColor = 1;
		        }
		    });
			
		    $( ".color-drag.disabled" ).draggable( "disable" );
			
		    //droppable for colorwell
		    $( ".colorwell" ).droppable({
		        accept: ".color-drag",
		        hoverClass: "hover",
		        drop: function() {
		            var $this = $( this );
		            var color = $(".ui-draggable-dragging").css("background-color");
		            if( color != "transparent" ) {
		                color = rgbtohex( color );
		            }
		            $( ".ui-draggable .ui-draggable-dragging" ).trigger( "drop" );
		            $this.val( color ).css( "background-color", color );
		            $this.trigger( "change" );
		            
		        }
		    });
		    
		    $('input.colorwell').each( function() {
		    	
		    	$(this).minicolors({
		    		change : function(hex, opacity) {
		    			
		    			//text = hex ? hex : 'transparent';
	                    //if( opacity ) text += ', ' + opacity;
	                    //text += ' / ' + $(this).minicolors('rgbaString');
	                    
	                    
		    			//console.log(hex + ' - ' + opacity);
		    			
		    			
	    				if( grayValue(hex) < 127 ) {
			            	$(this).css( "color", "#ffffff" );
			            } else {
			            	$(this).css( "color", "#000000" );
			            }
	    				
	    				
	    				// trigger
	    				$(this).trigger( "change" );
		    			
		    			
		    		}
		    	});
		    	
		    });
		    
		    // box shadow
		    $( "[data-type=box-shadow]" ).bind( "blur change keyup", function() {
		    	var name = $(this).attr("data-name");
		    	var color_el = $( "[data-type=box-shadow][data-name=" + name + "]:first" ),
			    	opac_el = $( "[data-type=box-shadow][data-name=" + name + "]:eq(1)" ),
			    	color_arr = color_el.val().split( "" ),
			    	red = parseInt( (color_arr[1] + color_arr[2]), 16 ),
			    	green = parseInt( (color_arr[3] + color_arr[4]), 16 ),
			    	blue = parseInt( (color_arr[5] + color_arr[6]), 16 ),
			    	opacity = parseFloat( opac_el.val() ) / 100;
			    
			    if ( !opacity ) {
			    	opacity = 0;
			    }
			    
			    styleArray[name] = "rgba(" + red + "," + green + "," + blue + "," + opacity + ")";
			    updateAllCSS();
		    });
		    
		    // box shadow size
		    $( "[data-type=box-shadow][data-alternative-name=box-shadow-size]" ).bind( "blur change keyup", function() {
		    	var name = $(this).attr("data-name");
		    	
		    	styleArray[name] = $(this).val();
		    	updateAllCSS();
		    });
		    
		    
		    
		    // initialize colors of sliders
		    $( "input[data-type=background]" ).each(function() {
		        var $this = $( this );
		        $( ".slider[data-type=background][data-name=" + $this.attr("data-name") + "] a" ).css({
		            "background": $this.val() 
		            /*"border-color": $this.val()*/
		        });
		    });
		    
		    // font family
		    $("[data-type=font-family]").bind("blur change keyup", function() {
		        var name = $(this).attr("data-name");
		        styleArray[name] = "font-family: " + this.value;
		        updateAllCSS();
		    });
		    
		    
		    
		    
		    
		    // link, text color, text shadow, border color, font-size
		    $( "[data-type=color], [data-type=text-shadow], [data-type=border], [data-type=border-radius], [data-type=border-width], [data-type=border-style], [data-type=border-color], [data-type=font-size], [data-type=padding], [data-type=margin], [data-type=font-weight], [data-type=line-height], [data-type=width], [data-type=height], [data-type=min-height]" )
		        .bind( "blur change keyup", function(){
		        	
		        	styleArray[$(this).attr("data-name")] = this.value;
		        	updateAllCSS();
		        	
		    });
		    
		    
		    // background colors
		    $( "[data-type=background]" ).bind( "blur slide mouseup change keyup", function(event, slider) {
		        if( !timerId ) {
		            timerId = setTimeout(function() {
		                timerId = 0;
		            }, 100);

		            var index = $( this ).attr( "data-name" ) + "";
		            
		            index = index.split( "-" );
		            
		            var prefix = ""; // prefix = index[0] + "-" + index[1];
		            
		            /* fix for double class names */
		            if(index.length == 4) {
		            	prefix = index[0] + "-" + index[1];
		            } else if(index.length == 5) {
		            	prefix = index[0] + "-" + index[1] + "-" + index[2];
		            }
		            
		            var color = $( "input[data-type=background][data-name|=" + prefix + "]" ).val();
		            var slider_value = $( ".slider[data-type=background][data-name|=" + prefix + "]" ).slider( "value" );
		            
		            var start_end = computeGradient(color, slider_value);
		            var start = start_end[0];
		            var end = start_end[1];

		            $( "[data-type=start][data-name=" + prefix + "-background-start]" ).val( start ).css( "background-color", start );
		            $( "[data-type=end][data-name=" + prefix + "-background-end]" ).val( end ).css( "background-color", end );
		            styleArray[prefix + "-background-color"] = color;
		            styleArray[prefix + "-background-start"] = start;
		            styleArray[prefix + "-background-end"] = end;
		            updateAllCSS();
		        }
		    });
		    
		    // start and end colors
		    $( "[data-type=start] , [data-type=end]" ).bind( "blur mouseup change keyup", function() {
		        var index = $( this ).attr( "data-name" ) + "";
		        index = index.split( "-" );
		        
		        
		        
		        // var prefix = index[0] + "-" + index[1];
		        
		        /* fix for double class names */
	            if(index.length == 4) {
	            	prefix = index[0] + "-" + index[1];
	            } else if(index.length == 5) {
	            	prefix = index[0] + "-" + index[1] + "-" + index[2];
	            }
	            
		        var start = $( "[data-type=start][data-name=" + prefix + "-background-start]" ).val();
		        var end = $( "[data-type=end][data-name=" + prefix + "-background-end]" ).val();

		        styleArray[prefix + "-background-start"] = start;
		        styleArray[prefix + "-background-end"] = end;
		        updateAllCSS();
		    });
		    
		    
		    // global more dictionary keeps track of which start-end fields are showing
		    // i.e. more["a-boby-background"] = "showing" means that the next click of that .more should
		    // hide that set of start-end fields
		    $( ".more" ).click(function(e) {
		        e.preventDefault();
		        var index = $( this ).attr( "data-name" );
		        
		        if( showStartEnd[index] ) {
		            if( showStartEnd[index] == "showing" ) {
		                $( ".start-end[data-name=" + index + "]" ).hide();
		                
		                $( this ).text( "+" );
		                showStartEnd[index] = "hiding";
		            } else {
		                $( ".start-end[data-name=" + index + "]" ).show();
		                
		                $( this ).text( "-" );
		                showStartEnd[index] = "showing";
		            }
		        } else {
		            $( ".start-end[data-name=" + index + "]" ).show();
		            
		            $( this ).text( "-" );
		            showStartEnd[index] = "showing";
		        }
		        
		        return false;
		    });
		    
		    
		    
		    
		    // inspector radio behavior
			$( "#inspector-button" ).click(function() {
				var $this = $( this ),
					active = $this.hasClass( "active" );
					
				if( !active ) {
					$this.addClass( "active" ); //.find( "img" ).attr( "src", "images/inspector-active.png" );
					$this.find( "strong" ).text( "on" );
				} else {
					$this.removeClass( "active" ); //.find( "img" ).attr( "src", "images/inspector.png" );
					$this.find( "strong" ).text( "off" );
				}
			});
		    
		    
		    
		    
		    
		    
		    // fix iframe container width
		    var frameBlock = $( "#frame_block" );
		    
		    
		    /*
		    //frameBlock.width( ($(window).width() - 10) - frameBlock.offset().left );
		    frameBlock.width($(window).width() - frameBlock.offset().left);
		    //frameBlock.height(($(window).height() - frameBlock.offset().top) - 10);
		    frameBlock.height($(window).height() - frameBlock.offset().top);
		    
		    $(window).resize( resize );
		    //sizing content to the right of the TR panel
			function resize() {
				//frameBlock.width( ($(window).width() - 10) - frameBlock.offset().left );
				//frameBlock.height(($(window).height() - frameBlock.offset().top) - 10);
				
				frameBlock.width($(window).width() - frameBlock.offset().left);
				frameBlock.height($(window).height() - frameBlock.offset().top);
				
			}
			*/
		    
		    
		    var frameEdit = $('.frame-edit');
		    var framePreview = $(".frame-preview");
		    var blockPreview = $(".block-preview");
		    var innerBlockPreview = $("#frame_block");
			
			
			
			var offsetLeft = framePreview.offset().left;
			
			/*
			// listen for collapsible events
			if(typeof collapsible != 'undefined') {
				
				// listen to event expand and collapse
				collapsible.onExpand = function() {
					
					if(expanded) {
			    		framePreview.css({
			    			'width' : (($(window).width() - 690) - 20)
			    		});
					    blockPreview.css({
				    		'width' : (($(window).width() - 690) - 20) - 2
				    	});
					} else {
			    		framePreview.css({
			    			'width' : (($(window).width() - 480) - 20)
			    		});
					    blockPreview.css({
				    		'width' : (($(window).width() - 480) - 20) - 2
				    	});
					}
					
				};
				
			}
			*/
		   
		    
		    
		    
		    
		    
		    // set width
		    framePreview.css({
		    	'width' : (($(window).width() - offsetLeft) - 20)
		    });
		    blockPreview.css({
	    		'width' : (($(window).width() - offsetLeft) - 20) - 2
	    	});
			
		    //blockPreview.height(($(window).height() - framePreview.offset().top) < 300 ? 300 : ($(window).height() - framePreview.offset().top) - 2);
		    blockPreview.height(300);
		    
		    //innerBlockPreview.height(($(window).height() - framePreview.offset().top) < 300 ? 300 : ($(window).height() - framePreview.offset().top) - 2);
		    innerBlockPreview.height(300);
		    
		    frameEdit.css({ "min-height" : ($(window).height() - framePreview.offset().top) < 300 ? 300 : ($(window).height() - framePreview.offset().top) });
		    
		    
		    
		    
		    
		    // sticky
		    var sidebarHeight = $('.sidebar').height();
			var e = $('.col-3').position().top;
			var h = $('.wrapper-footer').offset().top - 36;
			
			
			$('.frame-edit').resize(function() {
				h = $('.wrapper-footer').offset().top - 36;
			});
			
			scrollTop = $(window).scrollTop(), z = Math.max(0, e - scrollTop);
			
			z = Math.min(z, (h - scrollTop) - sidebarHeight);
			
			$('.sidebar').css({
				'top' : z/*,
				'height' : $(window).height() - z*/
			});
			
			$(window).resize(function() {
				
				/*
				if(expanded) {
			    	framePreview.css({
			    		'width' : (($(window).width() - 690) - 20)
			    	});
				    blockPreview.css({
				   		'width' : (($(window).width() - 690) - 20) - 2
				   	});
				} else {
			    	framePreview.css({
			    		'width' : (($(window).width() - 480) - 20)
			    	});
				    blockPreview.css({
				   		'width' : (($(window).width() - 480) - 20) - 2
				   	});
				}
				*/
				
				scrollTop = $(window).scrollTop(), y = Math.max(0, e - scrollTop);
				
				//y = Math.min(y, (h - scrollTop) - sidebarHeight);
				
				$('.sidebar').css({
			        'top': y/*,
			        'height' : $(window).height() - y*/
				});
				
			}).scroll(function (a) {
				
				scrollTop = $(window).scrollTop(), i = Math.max(0, e - scrollTop); //, i = Math.min(i, ($(window).height() - scrollTop) - 80);
				
				i = Math.min(i, (h - scrollTop) - sidebarHeight);
				
				$('.sidebar').css({
			        'top': i/*,
			        'height' : $(window).height() - i*/
				});
				
			});
			
			
			
			
			
		    
			// save for exists
			$("#button_save").click(function(event) {
				
				// do save for exist
				updateTheme({
					accountId : accountId,
					themeId : selectedThemeId, // if themeId is null create new or update
					opinionTypeId : opinionTypeId,
					styleBlock : styleBlock.text(),
					success : function(data) {
						
						var modal = new lightFace({
							title : "Changes saved.",
							message : "Your changes were successfully saved.",
							actions : [
							    { 
							    	label : "OK", 
							    	fire : function() { 
							    		 modal.close(); 
							    	}, 
							    	color: "blue" 
							    }
							],
							overlayAll : true
						});
						
						
						selectedTheme = null;
    					selectedThemeId = data.themeId;
						
						// reload themes
						reloadThemes();
						
					},
					error: function(error) {
						
						alert(JSON.stringify(error));
						
					}
				});
				
				// prevent default event
				event.preventDefault();
			});
			
			
			
			
			// save as...
			$('#button_save_as').click(function(event) {
				
				var isApply = false;
				
				var v = null;
				var M = $("<div>" +
					"<div style=\"padding: 0 0 12px 0\">Enter a new theme name.</div>" +
					"<div class=\"row\">" +
						"<div class=\"cell\">" +
							"<div><input type=\"text\" id=\"text_theme_name\" name=\"theme_name\" maxlength=\"100\" autocomplete=\"off\" style=\"width: 323px;\" /></div>" +
							"<div><label id=\"status_theme_name\"></label></div>" +
						"</div>" +
					"</div>" +
				"</div>");
				var I = M.find('#text_theme_name');
				var B = M.find('#status_theme_name');
				
				var modal = new lightFace({
					title : "Saving theme.",
					message : M,
					actions : [
						{ 
							label : "Save", 
							fire : function() {
								
								isApply = false;
								
								// save -> create or update
								v.validate();
								
							}, 
							color: "blue" 
						},
						{ 
							label : "Save and Apply", 
							fire : function() {
								
								isApply = true;
								
								// save and apply
								v.validate();
								
							}, 
							color: "blue" 
						},
						{ 
							label : "Cancel", 
							fire : function() {
								
								modal.close(); 
							}, 
							color: "white" 
						}
					],
					overlayAll : true,
					complete : function() {
						
						// set default values
						I.val((selectedThemeName != null ? selectedThemeName : "Theme #1"));
						
						// initialize validator on input
						v = new validator({
							elements : [
								{
									element : I,
									status : B,
									rules : [
										{ method : 'required', message : 'This field is required.' },
										{ method : 'rangelength', pattern : [3,100] }
									]
								}
							],
							submitElement : null,
							messages : null,
							accept : function () {
								
								if(isApply) {
									
									// save and apply
									//alert("SAVE AND APPLY -->> themeId: " + selectedThemeId + "______name: " + I.val() + "______opinionTypeId: " + opinionTypeId + "\nstyleBlock: " + styleBlock.text());
									// (selectedIsTemplate ? null : selectedThemeId)
									
									updateTheme({
										accountId : accountId,
										//themeId : selectedThemeId, // if themeId is null create new or update
										name : I.val(),
										opinionId : opinionId, // for apply has opinionId
										opinionTypeId : opinionTypeId,
										styleBlock : styleBlock.text(),
										success : function(data) {
											
											
											selectedTheme = null;
					    					selectedThemeId = data.themeId;
											
											// reload themes
											reloadThemes(true);
											
											
											modal.close();
											
										},
										error: function(error) {
											
											alert(JSON.stringify(error));
											
											modal.close();
										}
									});
								
								} else {
									
									// save
									//alert("SAVE -->> themeId: " + selectedThemeId + "______name: " + I.val() + "______opinionId: " + opinionId + "______opinionTypeId: " + opinionTypeId + "\nstyleBlock: " + styleBlock.text());
									// (selectedIsTemplate ? null : selectedThemeId)
									
									updateTheme({
										accountId : accountId,
										//themeId : selectedThemeId, // if themeId is null create new or update
										name : I.val(),
										opinionTypeId : opinionTypeId,
										styleBlock : styleBlock.text(),
										success : function(data) {
											
											
											selectedTheme = null;
					    					selectedThemeId = data.themeId;
					    					
											// reload themes
											reloadThemes();
											
											
											modal.close();
											
										},
										error: function(error) {
											alert(JSON.stringify(error));
											
											modal.close();
										}
									});
									
								}
								
								
								
								
								
								
								/*
								
								*/
								
								// save or update
								
								// modal.close();
								
							},
							error: function() {
								// error
							}
						});
					}
				});
				
				
				
				event.preventDefault();
				
			});
			
			// download
			$("#button_download").click(function(event) {
				
				// Download theme
				
				/*
				var theme_name = $( "input", this ).val();
                if( theme_name && theme_name.indexOf(" ") == -1 ) {
                    
                    $.ajax({
                        url: "./zip.php",
                        type: "POST",
                        data: "ver=" + TR.version + "&theme_name=" + $( "input", this ).val() + "&file=" + encodeURIComponent(TR.styleBlock.text()),
                        dataType: "text",
                        mimeType: "text/plain",
                        beforeSend: function() {
                            //loading gif here
                        },
                        success: function(response) {
                            window.location = response;
                            $( "#download" ).dialog( "close" );
                        }
                    });
                    
                } else {
                    alert( "Invalid theme name" );
                }
				*/
				
				event.preventDefault();
				
			});
			
			
			// import
			$("#button_import").click(function(event) {
				
				// Import theme
				
				/*
				 <select id="upgrade-to-version"><option value="1.0.1">1.0.1</option><option value="1.1.0">1.1.0</option><option value="1.1.1">1.1.1</option><option value="1.1.2">1.1.2</option><option value="1.2.0">1.2.0</option><option value="1.2.1">1.2.1</option><option value="1.3.0">1.3.0</option></select>
				 */
				
				/*
				 travelTo( $( "#upgrade-to-version" ).val(), true );
				 */
				
				
				event.preventDefault();
				
			});
			
		    
		    
		    
		    
		    
		    
			
			
			
		    // start here
		    initStyleArray();
		    
		    updateFormValues();
		    
		    
		    
		    
		    if(options.complete != null 
		    		&& typeof options.complete == "function") {
		    	options.complete();
		    }
		    
		    
		    
	    
		};
		
		// init controls
		initControls();
		
		
		
		
		
		
		
		
		return {
			initStyleArray : function(v) {
				initStyleArray(v);
			},
			updateAllCSS : function() {
				updateAllCSS();
			},
			updateFormValues : function() {
				updateFormValues();
			},
			initInspector : function() {
				initInspector();
			}
		}
		
	}
})(jQuery);