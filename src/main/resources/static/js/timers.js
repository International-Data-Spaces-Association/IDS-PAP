function showTimer(timerId) {
	window.location = '/timer/' + timerId+"/";
}

function getParameterByName(name, url) {
	if (!url) {
		url = window.location.href;
	}
	name = name.replace(/[\[\]]/g, "\\$&");
	var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
	results = regex.exec(url);
	if (!results) return null;
	if (!results[2]) return '';
	console.log(results[2]);
	return decodeURIComponent(results[2].replace(/\+/g, " "));
}

var pendingList = new Set([]);

function toggleDeployTimer(timerId, checkbox) {
	if(timerId === null || !(timerId) || timerId== "null" || timerId.endsWith("null")){
		timerId = extractTimerIdFromTimer();
	}
	if (pendingList.has(timerId)) {
		// since "delete" is a reserved word, we use this way
		pendingList['delete'](timerId);
		return;
	}
	var state = checkbox.checked;
	$.ajax({
		type : "GET",
		url : !state ? "/timer/" + timerId + "/revoke" : "/timer/" + timerId + "/deploy",
				contentType : "application/json",
				async : true,
				complete : function(data) {
					if (data.status == 200) {
						var filter = getParameterByName('filter');
						if (filter == 'deployed' || filter == 'undeployed') {
							var row = document.getElementById(timerId + '_row');
							row.parentNode.removeChild(row);
						}
						
						filter = getParameterByName('tab');
						if (filter == 'deployed' || filter == 'undeployed') {
							var row = document.getElementById(timerId + '_row');
							row.parentNode.removeChild(row);
						}


						if (!state) {
							deployed -= 1;
							undeployed += 1;
							try{document.getElementById(timerId+"_delete").setAttribute("onclick", "deleteTimer('"+timerId+"', false)"); 
							}catch(e){console.log("could not update the delete button");}
							try{document.getElementById(timerId).value = "false";
							}catch(e){console.log("could not update the toggle button");}
						} else {
							deployed += 1;
							undeployed -= 1;
							try{document.getElementById(timerId).value = "true";
							}catch(e){console.log("could not update the toggle button");}
							try{document.getElementById(timerId+"_delete").setAttribute("onclick", "deleteTimer('"+timerId+"', true)"); 
							}catch(e){console.log("could not update the delete button");}
							if(deployed<=0)
								location.reload(true);
						}
						document.getElementById("link_all").innerHTML = "All (" + (deployed +  undeployed) + ")";
						document.getElementById("link_deployed").innerHTML = "Deployed (" + deployed + ")";
						document.getElementById("link_undeployed").innerHTML = "Not Deployed (" + undeployed + ")";
						//location.reload(false);
						bootbox.alert("Timer was " + (state ? "deployed" : "revoked"));
					} else {
						pendingList.add(timerId);
						var name = timerId + '_toggle';
						document.getElementById(name).click();

						if (data.status == 401 || data.status == 403) {
							bootbox.alert("Timer was not " + (state ? "deployed" : "revoked") + ". You do not have the privilege!");
						}else {
							bootbox.alert("Timer was not " + (state ? "deployed" : "revoked"));
						}
					}
				}
	
	});
}

function deleteTimer(timerId, depolyedSate) {
	if(timerId === null || !(timerId) || timerId== "null" || timerId.endsWith("null")){
		timerId = extractTimerIdFromTimer();
	}
	if (depolyedSate) {
		new PNotify({
			title : "Can not delete Timer",
			type : "error",
			text : "As " + timerId
			+ " timer is deployed, it can not be deleted",
			styling : 'bootstrap3'
		});
	} else {
		bootbox
		.confirm({
			message : 'Do you want to delete timer with id '
				+ timerId + ' ?',
				buttons : {
					confirm : {
						label : 'Delete',
						className : 'btn-danger'
					},
					cancel : {
						label : 'No',
						className : 'btn-default'
					}
				},
				callback : function(result) {
					if (result) {
						$
						.ajax({
							type : "DELETE",
							url : "/timer/"
								+ timerId+"/"
								+ "?reason=Please add functionality to ask reason",
								contentType : "application/json",
								async : true,
								complete : function(data) {
									if (data.status == 200) {
										window.location = '/timers/';
									} else if (data.status == 401) {
										location.reload(true);
									} else {
										new PNotify(
												{
													title : "Can not delete Timer",
													type : "error",
													text : "Unable to delete "
														+ timerId
														+ ".",
														styling : 'bootstrap3'
												});
									}
								}
						});
					}
				}
		});
	}
}

function refreshTimer(timerId) {
	new PNotify({
		title : "Implement functionality to refresh timer!",
		type : "info",
		styling : 'bootstrap3'
	});
}

function bulkDelete(tabId) {
	var elementsIds = getCheckedElementsIds(tabId);
	var elementsValues = getCheckedElementsValues(tabId);
	var count = elementsIds.length;

	var msg;
	if (count == 0) {
		return;
	} else {
		msg = "Do you really want to delete these <b> " + count
		+ " </b>timers?";
	}
	bootbox
	.confirm({
		message : msg,
		buttons : {
			confirm : {
				label : 'Delete',
				className : 'btn-danger'
			},
			cancel : {
				label : 'No',
				className : 'btn-default'
			}
		},
		callback : function(result) {
			if (result) {
				var failedIds=[];
				var deployedIds=[];
				var todelete=[];
				for (var i = 0; i < elementsIds.length; i++) {
					var timerId = elementsIds[i];
					var isDeployed = elementsValues.includes(timerId);
					//console.log(timerId+", "+isDeployed);
					if (isDeployed) {
						deployedIds.push(timerId);
						} else {
							$.ajax({
								type : "DELETE",
								url : "/timer/"
									+ timerId+"/"
									+ "?reason=Please add functionality to ask reason",
									contentType : "application/json",
									async : false,
									complete : function(data) {
										//console.log(data.status);
										if (data.status == 500) {
											failedIds.push(timerId);
										}
										else{
											todelete.push(timerId+"_row");
										}
									}
							});
						}
				}
		//		console.log(deployedIds);
		//		console.log(failedIds);
				if(todelete.length>0){
					for(var j=0;j<todelete.length;j++){
						 var row = document.getElementById(todelete[j]);
						    row.parentNode.removeChild(row);
						    var aText=jQuery("#link_undeployed").text();
						    var nb = parseInt(aText.split("(")[1].split(")")[0]);
						    var txt = aText.split("(")[0];
						    nb--;
						    jQuery("#link_undeployed").text(txt+" ("+nb+")");
						    aText=jQuery("#link_all").text();
						    nb = parseInt(aText.split("(")[1].split(")")[0]);
						    txt = aText.split("(")[0];
						    nb--;
						    jQuery("#link_all").text(txt+" ("+nb+")");
						    
					}
				}
				if(failedIds.length>0){
					for (var j = 0; j < failedIds.length; j++) {	
						new PNotify(
								{
									title : "Failed to delete Timer",
									type : "error",
									text : "Timer with id "
										+ failedIds[j]
								+ " deletion failed",
								styling : 'bootstrap3'
								});
					}
				}
				if(deployedIds.length>0){
					for (var j = 0; j < deployedIds.length; j++) {
					new PNotify(
							{
								title : "Can not delete Timer",
								type : "error",
								text : "As "
									+ deployedIds[j]
									+ " timer is deployed, it can not be deleted",
									styling : 'bootstrap3'
							});
					}
				}
			}
		}
	});

}

function extractTimerIdFromTimer() {
	var xmlDoc = jQuery.parseXML(Xonomy.harvest());
	var x = xmlDoc.getElementsByTagName('timer');
	return x[0].getAttribute('id');
}


function exportTimer(timerId) {
	if(timerId === null || !(timerId) || timerId== "null" || timerId.endsWith("null")){
		timerId = extractTimerIdFromTimer();
	}
	$.ajax({
		type : "GET",
		url : "/timer/" + timerId + "/export",
		contentType : "application/json",
		async : false,
		complete : function(data) {
			if (data.status == 200 && data.responseText.length>0) {
				var response = JSON.parse(data.responseText);
				var timer = response.xml;
				var blob = new Blob([prettyXml(timer, 3) ], {
					type : "text/xml;charset=utf-8"
				});
				saveAs(blob, timerId + ".xml",true);
				new PNotify({
					title : "Timer saved",
					type : "info",
					text : "Timer with id " + timerId
					+ " was saved on your system.",
					styling : 'bootstrap3'
				});
			}else if (data.status == 200 && data.responseText.length==0) {
			//timer not saved yet
				var harvestedXml = Xonomy.harvest();
				var blob = new Blob([prettyXml(harvestedXml, 3) ], {
					type : "text/xml;charset=utf-8"
				});
				saveAs(blob, timerId + ".xml",true);
				new PNotify({
					title : "Timer saved",
					type : "info",
					text : "Timer with id " + timerId
					+ " was saved but might not be valid.",
					styling : 'bootstrap3'
				});
			} else if (data.status == 401) {
				location.reload(true);
			} else {
				new PNotify({
					title : "Failed to export Timer",
					type : "error",
					text : "Timer with id " + timerId
					+ " could not be exported.",
					styling : 'bootstrap3'
				});
			}
		}
	});
}

function prettyXml(text, step) {

	var ar = text.replace(/>\s{0,}</g, "><").replace(/</g, "~::~<").replace(
			/\s*xmlns\:/g, "~::~xmlns:").replace(/\s*xmlns\=/g, "~::~xmlns=")
			.split('~::~'), len = ar.length, inComment = false, deep = 0, str = '', ix = 0, shift = step ? createShiftArr(step)
					: this.shift;

			for (ix = 0; ix < len; ix++) {
				// start comment or <![CDATA[...]]> or <!DOCTYPE //
				if (ar[ix].search(/<!/) > -1) {
					str += shift[deep] + ar[ix];
					inComment = true;
					// end comment or <![CDATA[...]]> //
					if (ar[ix].search(/-->/) > -1 || ar[ix].search(/\]>/) > -1
							|| ar[ix].search(/!DOCTYPE/) > -1) {
						inComment = false;
					}
				} else
					// end comment or <![CDATA[...]]> //
					if (ar[ix].search(/-->/) > -1 || ar[ix].search(/\]>/) > -1) {
						str += ar[ix];
						inComment = false;
					} else
						// <elm></elm> //
						if (/^<\w/.exec(ar[ix - 1])
								&& /^<\/\w/.exec(ar[ix])
								&& /^<[\w:\-\.\,]+/.exec(ar[ix - 1]) == /^<\/[\w:\-\.\,]+/
								.exec(ar[ix])[0].replace('/', '')) {
							str += ar[ix];
							if (!inComment)
								deep--;
						} else
							// <elm> //
							if (ar[ix].search(/<\w/) > -1 && ar[ix].search(/<\//) == -1
									&& ar[ix].search(/\/>/) == -1) {
								str = !inComment ? str += shift[deep++] + ar[ix] : str += ar[ix];
							} else
								// <elm>...</elm> //
								if (ar[ix].search(/<\w/) > -1 && ar[ix].search(/<\//) > -1) {
									str = !inComment ? str += shift[deep] + ar[ix] : str += ar[ix];
								} else
									// </elm> //
									if (ar[ix].search(/<\//) > -1) {
										str = !inComment ? str += shift[--deep] + ar[ix] : str += ar[ix];
									} else
										// <elm/> //
										if (ar[ix].search(/\/>/) > -1) {
											str = !inComment ? str += shift[deep] + ar[ix] : str += ar[ix];
										} else
											// <? xml ... ?> //
											if (ar[ix].search(/<\?/) > -1) {
												str += shift[deep] + ar[ix];
											} else
												// xmlns //
												if (ar[ix].search(/xmlns\:/) > -1 || ar[ix].search(/xmlns\=/) > -1) {
													str += shift[deep] + ar[ix];
												}

												else {
													str += ar[ix];
												}
			}

			return (str[0] == '\n') ? str.slice(1) : str;
}

function createShiftArr(step) {

	var space = '    ';

	if (isNaN(parseInt(step))) { // argument is string
		space = step;
	} else { // argument is integer
		switch (step) {
		case 1:
			space = ' ';
			break;
		case 2:
			space = '  ';
			break;
		case 3:
			space = '   ';
			break;
		case 4:
			space = '    ';
			break;
		case 5:
			space = '     ';
			break;
		case 6:
			space = '      ';
			break;
		case 7:
			space = '       ';
			break;
		case 8:
			space = '        ';
			break;
		case 9:
			space = '         ';
			break;
		case 10:
			space = '          ';
			break;
		case 11:
			space = '           ';
			break;
		case 12:
			space = '            ';
			break;
		}
	}

	var shift = [ '\n' ]; // array of shifts
	for (ix = 0; ix < 100; ix++) {
		shift.push(shift[ix] + space);
	}
	return shift;
}
function getCheckedElementsIds(id){
	var selected = [];
	var tr;
	var timerId;
	$("#"+id+" input:checked").filter(".dt-checkboxes").each(function() {
		try{
		td =$(this).closest("td");
		timerId = td.siblings().children("span").filter(".policy_id").text();
		if(timerId !=null && timerId.length>1)
			selected.push(timerId);
		}catch(e){}
	});
	return selected;
}

function getCheckedElementsValues(id){
	var selected = [];
	var deployedPolicy;
	$("#"+id+" input:checked").filter(".js-switch").each(function() {
		console.log($(this));
		deployedPolicy = $(this).attr("id").split("_toggle")[0];
		selected.push(deployedPolicy);
	});
	return selected;
}


function bulkExport(divId) {
	var elements = getCheckedElementsIds(divId);
	var zipFile = new JSZip();
	for (var i = 0; i < elements.length; i++) {
			var timerId = elements[i];
			$.ajax({
				type : "GET",
				url : "/timer/" + timerId + "/export",
				contentType : "application/json",
				async : false, // async is false bcz zipFile cant be
				// synchronized.
				complete : function(data) {
					if (data.status == 200) {
						var response = JSON.parse(data.responseText);
						var timerName = timerId.replace(/:/g, '_');
						zipFile.file(timerName + '.xml', prettyXml(response.xml, 3));
					} else if (data.status == 401) {
						location.reload(true);
					} else {
						new PNotify(
								{
									title : 'Fetched timer failed',
									type : "Info",
									text : "Timer with id "
										+ timerId
										+ " is not added to zip file as it can not be fetched",
										styling : 'bootstrap3'
								});
					}
				}
			});
	}
	zipFile.generateAsync({
		type : "blob"
	}).then(function(content) {
		var fileName = 'ind2uce-timers-' + Date.now() + '.zip'
		saveAs(content, fileName);
		new PNotify({
			title : 'Timers exported',
			type : "Info",
			text : 'All selected timers are saved in file ' + fileName,
			styling : 'bootstrap3'
		});
	});
}

var saveAs = saveAs
|| (function(view) {
	"use strict";
	// IE <10 is explicitly unsupported
	if (typeof view === "undefined" || typeof navigator !== "undefined"
		&& /MSIE [1-9]\./.test(navigator.userAgent)) {
		return;
	}
	var doc = view.document
	// only get URL when necessary in case Blob.js hasn't overridden it
	// yet
	, get_URL = function() {
		return view.URL || view.webkitURL || view;
	}, save_link = doc.createElementNS("http://www.w3.org/1999/xhtml",
	"a"), can_use_save_link = "download" in save_link, click = function(
			node) {
		var event = new MouseEvent("click");
		node.dispatchEvent(event);
	}, is_safari = /constructor/i.test(view.HTMLElement) || view.safari, is_chrome_ios = /CriOS\/[\d]+/
	.test(navigator.userAgent), throw_outside = function(ex) {
		(view.setImmediate || view.setTimeout)(function() {
			throw ex;
		}, 0);
	}, force_saveable_type = "application/octet-stream"
		// the Blob API is fundamentally broken as there is no
		// "downloadfinished" event to subscribe to
		, arbitrary_revoke_timeout = 1000 * 40 // in ms
		, revoke = function(file) {
		var revoker = function() {
			if (typeof file === "string") { // file is an object URL
				get_URL().revokeObjectURL(file);
			} else { // file is a File
				file.remove();
			}
		};
		setTimeout(revoker, arbitrary_revoke_timeout);
	}, dispatch = function(filesaver, event_types, event) {
		event_types = [].concat(event_types);
		var i = event_types.length;
		while (i--) {
			var listener = filesaver["on" + event_types[i]];
			if (typeof listener === "function") {
				try {
					listener.call(filesaver, event || filesaver);
				} catch (ex) {
					throw_outside(ex);
				}
			}
		}
	}, auto_bom = function(blob) {
		// prepend BOM for UTF-8 XML and text/* types (including HTML)
		// note: your browser will automatically convert UTF-16 U+FEFF
		// to EF BB BF
		if (/^\s*(?:text\/\S*|application\/xml|\S*\/\S*\+xml)\s*;.*charset\s*=\s*utf-8/i
		.test(blob.type)) {
			return new Blob([ String.fromCharCode(0xFEFF), blob ], {
				type : blob.type
			});
		}
		return blob;
	}, FileSaver = function(blob, name, no_auto_bom) {
		if (!no_auto_bom) {
			blob = auto_bom(blob);
		}
		// First try a.download, then web filesystem, then object URLs
		var filesaver = this, type = blob.type, force = type === force_saveable_type, object_url, dispatch_all = function() {
			dispatch(filesaver, "writestart progress write writeend"
					.split(" "));
		}
		// on any filesys errors revert to saving with object URLs
		, fs_error = function() {
			if ((is_chrome_ios || (force && is_safari))
					&& view.FileReader) {
				// Safari doesn't allow downloading of blob urls
				var reader = new FileReader();
				reader.onloadend = function() {
					var url = is_chrome_ios ? reader.result
							: reader.result.replace(/^data:[^;]*;/,
							'data:attachment/file;');
					var popup = view.open(url, '_blank');
					if (!popup)
						view.location.href = url;
					url = undefined; // release reference before
					// dispatching
					filesaver.readyState = filesaver.DONE;
					dispatch_all();
				};
				reader.readAsDataURL(blob);
				filesaver.readyState = filesaver.INIT;
				return;
			}
			// don't create more object URLs than needed
			if (!object_url) {
				object_url = get_URL().createObjectURL(blob);
			}
			if (force) {
				view.location.href = object_url;
			} else {
				var opened = view.open(object_url, "_blank");
				if (!opened) {
					// Apple does not allow window.open, see
					// https://developer.apple.com/library/safari/documentation/Tools/Conceptual/SafariExtensionGuide/WorkingwithWindowsandTabs/WorkingwithWindowsandTabs.html
					view.location.href = object_url;
				}
			}
			filesaver.readyState = filesaver.DONE;
			dispatch_all();
			revoke(object_url);
		};
		filesaver.readyState = filesaver.INIT;

		if (can_use_save_link) {
			object_url = get_URL().createObjectURL(blob);
			setTimeout(function() {
				save_link.href = object_url;
				save_link.download = name;
				click(save_link);
				dispatch_all();
				revoke(object_url);
				filesaver.readyState = filesaver.DONE;
			});
			return;
		}

		fs_error();
	}, FS_proto = FileSaver.prototype, saveAs = function(blob, name,
			no_auto_bom) {
		return new FileSaver(blob, name || blob.name || "download",
				no_auto_bom);
	};
	// IE 10+ (native saveAs)
	if (typeof navigator !== "undefined" && navigator.msSaveOrOpenBlob) {
		return function(blob, name, no_auto_bom) {
			name = name || blob.name || "download";

			if (!no_auto_bom) {
				blob = auto_bom(blob);
			}
			return navigator.msSaveOrOpenBlob(blob, name);
		};
	}

	FS_proto.abort = function() {
	};
	FS_proto.readyState = FS_proto.INIT = 0;
	FS_proto.WRITING = 1;
	FS_proto.DONE = 2;

	FS_proto.error = FS_proto.onwritestart = FS_proto.onprogress = FS_proto.onwrite = FS_proto.onabort = FS_proto.onerror = FS_proto.onwriteend = null;

	return saveAs;
}(typeof self !== "undefined" && self || typeof window !== "undefined"
	&& window || this.content));
