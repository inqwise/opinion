


var videoSizes = {};
function isVideoSizesMatch() {
	var isMatch = false;
	
	videoSizes = {};
	
	// fill sizes from videos
	for(var item in videoResources) {
		videoSizes[videoResources[item].width] = {
			height : videoResources[item].height
		}
	}
	
	// validate
	var checkSize = function(element) {			
		if(sizes[element.width] != undefined) {
			if(sizes[element.width].height == element.height) {
				return true;
			}
			return false;
		}
		return false;
	};
	
	var total = 0;
	var existCount = 0;
	for(var item in videoResources) {
		if(checkSize(videoResources[item])) {
			existCount += 1;
		}
		total += 1;
	}
	
	if(total == existCount) {
		isMatch = true;
	}

	return isMatch;
}