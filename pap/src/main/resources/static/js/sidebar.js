/**
 * Make all items visible. This is for selecting another solution.
 * 
 * @returns
 */
function showAllSolutions() {
	$('li.solution').css('display', 'block');
	$('li.arrow').css('display', 'none');
}

/**
 * Changes the visibility of the  solutions items so that only the 
 * selected solution is visible.
 * 
 * @returns
 */
function showSelectedSolutionOnly() {
	$('li.solution').css('display', 'none');
	$('li.solution.current').css('display', 'block');
}



/**
 * Submits a selected solution to the server. On successful response,
 * a callback is getting invoked.
 * 
 * @param id
 * @param callback
 * @returns
 */
function submitSelectedSolution(id, callback) {
	$.ajax({
		type : "POST",
		url : "/solution/select/"+id,
		async : true,
		contentType : "application/json",
		complete : function(data) {
			//console.log(data.status);
			if (data.status == 200) {
				callback();
			} else {
				setTimeout(function () {
                    self.$("#closeSolutionSwitch").trigger("click");
                }, 1200);
				bootbox.alert("Unable to select the solution "+id+", something has changed.<br/> Please refresh the solution list.");
			}
		}
	});
	
	
}

/**
 * Eventhandler for clicking a solution.
 * 
 * @param e
 * @returns
 */
function onSolutionSelected(solutionId) {
	submitSelectedSolution(solutionId, function() {location.reload(true);});
}

/**
 * Eventhandler for clicking the arrow below the selected 
 * solution.
 * 
 * @returns
 */
function onSolutionListClicked() {
	if (document.allSolutions) {
		showSelectedSolutionOnly();
		document.allSolutions = false;
	} else {
		showAllSolutions();
		document.allSolutions = true;
	}
	
}

