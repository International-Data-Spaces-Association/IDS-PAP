var $ = jQuery.noConflict();

function timeOutCountdown(){
	var offSet = calcOffset();
	var SessionTime= calcTimeoutMillis(offSet);
	var tickDuration=1000;
	var endIn = sessionTimeFormat(SessionTime);
	var refreshlimit = calcRefreshlimit(SessionTime);
	jQuery("#sessionCountdown").html(endIn);
	
	var isClicked = false;
	
	jQuery( document ).click(function() {
		if(!isClicked){
			isClicked = true;
		}
	});
	
	var myInterval=setInterval(function(){
	    refreshlimit = (refreshlimit - tickDuration);
	    endIn = sessionTimeFormat(SessionTime);
	    if( refreshlimit <= 999){
	    	refreshlimit = calcRefreshlimit(SessionTime);
	    	refreshSession();
	    }
	    SessionTime=calcTimeoutMillis(offSet);
	    if(SessionTime <= 0){
	    	SessionExpireEvent();
	    }
	    jQuery("#sessionCountdown").html(endIn);
	},tickDuration); 

	
	function refreshSession(){
		if(isClicked){		
			SessionTime=calcTimeoutMillis(offSet);
			jQuery.get("/static/refreshSession");
			isClicked = false;
			endIn = sessionTimeFormat(SessionTime);
			refreshlimit = calcRefreshlimit(SessionTime);
			jQuery("#sessionCountdown").html(endIn);
		}
	}
	
	function calcRefreshlimit(timeMillis){
		if(timeMillis > 3600000){
			return 300000;
		}
		if(timeMillis > 90000){
			return 60000;
		}
		return 1000;
	}

	function SessionExpireEvent()
	{ clearInterval(myInterval);
	window.location.replace("/static/login?expired");
	}
}


//function calcTimeoutMillis(maxTimeSeconds, sessionExpirationDate){
//	var currentMillis = Date.now(); 
//	var sessionEnd = maxTimeSeconds*1000 + currentMillis;
//	if(sessionExpirationDate > 0 && sessionExpirationDate < sessionEnd){
//		sessionEnd = sessionExpirationDate;
//	}
//	return	(sessionEnd - currentMillis);
//}

function calcTimeoutMillis(offSet){
	var currentMillis = Date.now() - offSet; 
	var sessionEnd = getCookie("expirationDate");
	return	(sessionEnd - currentMillis);
}


function sessionTimeFormat(timeMillis){
	var seconds = Math.floor(timeMillis/1000);
	if(seconds < 1){
		return createHtmlFormat("Session expired", true);
	}
	if(seconds < 60){
		return createHtmlFormat(seconds + "s", true);
	}else{
		var mins = Math.floor(seconds/60);
		if(mins < 60){
			return createHtmlFormat(mins + "min", false);
		}
		else{
			return createHtmlFormat(Math.floor(mins/60) + "h", false);
		}
	}
}
	
function createHtmlFormat(value, isless){
	if(isless){
		return '<i class="fa fa-exclamation-triangle countdown danger"></i><span class="countdown danger"> Session Timeout: ' + value + '</span>';
	}
	return '<span class="countdown">  Session Timeout: ' + value + '</span>';
}

//get cookie by name
function getCookie(c_name) {
    var c_value = " " + document.cookie;
    var c_start = c_value.indexOf(" " + c_name + "=");
    if (c_start == -1) {
        c_value = null;
    }
    else {
        c_start = c_value.indexOf("=", c_start) + 1;
        var c_end = c_value.indexOf(";", c_start);
        if (c_end == -1) {
            c_end = c_value.length;
        }
        c_value = unescape(c_value.substring(c_start,c_end));
    }
    return c_value;
}

// calculate offset between client and serverTIme
function calcOffset() {
    var serverTime = getCookie('serverTime');
    serverTime = serverTime==null ? null : Math.abs(serverTime);
    var clientTimeOffset = Date.now() - serverTime;
    return clientTimeOffset;
}



