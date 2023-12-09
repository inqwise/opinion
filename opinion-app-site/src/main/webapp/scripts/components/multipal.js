
/*
 * Multipal control
 * 
 * 
 */

jQuery.fn.multipal = function() {
	
	if (arguments.length == 0) return [];
	var args = arguments[0] || {};
	
	var elements = $(this);
	var availableOptions, selectedOptions = null;
	var leftList, rightList = [];
	var addButton, removeButton, upButton, downButton = null;
	
	availableOptions = $(elements[0])[0];
	selectedOptions = $(elements[1])[0];
	
	leftList = args.leftList != undefined ? args.leftList : [];
	rightList = args.rightList != undefined ? args.rightList : [];
	
	// Add button
	if(args.addButton != undefined && args.addButton != null) {
		addButton = args.addButton;
		addButton.click(function() {
			add();
		});
	}
	
	// Remove button
	if(args.removeButton != undefined && args.removeButton != null) {
		removeButton = args.removeButton;
		removeButton.click(function() {
			remove();
		});
	}
	
	// Up button
	if(args.upButton != undefined && args.upButton != null) {
		upButton = args.upButton;
		upButton.click(function() {
			up();
		});
	}
	
	// Down button
	if(args.downButton != undefined && args.downButton != null) {
		downButton = args.downButton;
		downButton.click(function() {
			down();
		});
	}
	
	// Fill available
	if(leftList.length != 0) {
		for(var i = 0; i < leftList.length; i++) {
			var optionElem = new Option(leftList[i][1], leftList[i][0]);
			availableOptions.add(optionElem, null);
		}
	}
	
	
	// Fill selected
	if(rightList.length != 0) {
		for(var i = 0; i < rightList.length; i++) {
			var optionElem = new Option(rightList[i][1], rightList[i][0]);
			selectedOptions.add(optionElem, null);
		}
	}
	
	var toRight = [];
	var add = function() {
		
		var clearSelected = function() {
			var i =0;
			for (i = availableOptions.length - 1; i>=0; i--) {
			    if (availableOptions.options[i].selected) {
			    	availableOptions.remove(i);
			    	leftList.splice(i, 1);
			    }
			}
			
			if(toRight.length != 0) {
				for(var i = 0; i < toRight.length; i++) {
					var optionElem = new Option(toRight[i][1], toRight[i][0]);
					selectedOptions.add(optionElem, null);
				}
			}
			
			toRight = [];
			
		};
		
		for(var i = 0; i < availableOptions.length; i++) {
			if(availableOptions.options[i].selected) {
				toRight.push([availableOptions.options[i].value, availableOptions.options[i].text, i]);
				// temp
				rightList.push([availableOptions.options[i].value, availableOptions.options[i].text, i]);
			}
		}
		
		clearSelected();
		
	};
	
	var toLeft = [];
	var remove = function() {
		
		var clearSelected = function() {
			var i =0;
			for (i = selectedOptions.length - 1; i>=0; i--) {
			    if (selectedOptions.options[i].selected) {
			    	selectedOptions.remove(i);
			    	rightList.splice(i, 1);
			    }
			}
			
			if(toLeft.length != 0) {
				for(var i = 0; i < toLeft.length; i++) {
					var optionElem = new Option(toLeft[i][1], toLeft[i][0]);
					availableOptions.add(optionElem, null);
				}
			}
			
			toLeft = [];
			
		};
		
		for(var i = 0; i < selectedOptions.length; i++) {
			if(selectedOptions.options[i].selected) {
				toLeft.push([selectedOptions.options[i].value, selectedOptions.options[i].text, i]);
				// temp
				leftList.push([selectedOptions.options[i].value, selectedOptions.options[i].text, i]);
			}
		}
		
		clearSelected();
		
	};
	
	var up = function() {
		
		var tempList = selectedOptions.getElementsByTagName('option');
		for(var i = 1; i < tempList.length; i++) {
			var opt = tempList[i];
			if(opt.selected) {
				selectedOptions.removeChild(opt);
				selectedOptions.insertBefore(opt, tempList[i - 1]);
			}
		}
		
	};
	
	var down = function() {
		
		var tempList = selectedOptions.getElementsByTagName('option');
		for(var i = tempList.length - 2; i >= 0; i--) {
			var opt = tempList[i];
			if(opt.selected) {
				var nextOpt = tempList[i + 1];
				opt = selectedOptions.removeChild(opt);
				nextOpt = selectedOptions.replaceChild(opt, nextOpt);
				selectedOptions.insertBefore(nextOpt, opt);
			}
		}
		
	};
	
};



