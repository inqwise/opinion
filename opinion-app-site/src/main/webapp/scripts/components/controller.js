

// alert("OK");


function layoutControler() { return this.constructor(); }
layoutControler.prototype = {
	
	win:null,
	defaultWidth: 740,
	defaultHeight: 500,
	add : function() {
		var width = 500;
		var height = 580;
		this.win = window.open('http://localhost:8080/opinion-opinion-1.0.1/modules/add_layout_element.jsp', 'name', 'height=' + height + ', width=' + width + ', top=' + 20 + ', left=' + 20 + ', toolbar=no, status=yes, resizable=yes');	
		if (window.focus) { this.win.focus(); }
	},
	edit: function(opinionId, elementId) {
		// if(this.win != null) { this.win.close(); this.win = null; }
		
		this.win = window.open('http://localhost:8080/opinion-opinion-1.0.1/modules/edit_layout_element.jsp?opinion_id=' + opinionId + '&element_id=' + elementId, 'name', 'height=' + this.defaultHeight + ', width=' + this.defaultWidth + ', top=' + (screen.height / 2 - this.defaultHeight / 2) + ', left=' + (screen.width / 2 - this.defaultWidth / 2) + ', toolbar=no, status=yes, resizable=yes');	
		if (window.focus) { this.win.focus(); }
	},
	
	insert: function(elementTitle) {
		var panel = $("<div class=\"layout-dragable-panel\"></div>");
		var title = $("<span class=\"layout-title\">" + elementTitle + "</span>");
		var deleteButton = $("<a class=\"layout-delete-button\" title=\"Delete\">Delete</a>");
		$(deleteButton).click(function(el) {
			$(deleteButton).parent().remove();
		});
		var editButton = $("<a class=\"layout-action-button\" title=\"Edit\">Edit</a>");
		$(panel).append($(title)).append($(deleteButton)).append(editButton);
		$($('.layout-box').children()[$('.layout-box').children().length - 3]).after($(panel));
	},
	constructor: function() {}
};



var layoutElement = new layoutControler();






