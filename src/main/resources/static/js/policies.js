function showPolicy(policyId) {
	window.location = '/policy/' + policyId+"/";
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

function bulkRevoke(tabId) {
	var elementsIds = getCheckedElementsIds(tabId);
	var elementsValues=getCheckedElementsValues(tabId);
	var count = elementsIds.length;

	
	var msg;
	if (count == 0) {
		return;
	} else {
		if(count==1)
			msg = "Do you really want to revoke this policy?";
		else
		msg = "Do you really want to revoke these <b> " + count
		+ " </b>policies?";
	}
	bootbox
	.confirm({
		message : msg,
		buttons : {
			confirm : {
				label : 'Revoke',
				className : 'btn-danger'
			},
			cancel : {
				label : 'Keep deployed',
				className : 'btn-default'
			}
		},
		callback : function(result) {
			if (result) {
				var failedIds=[];
				var deployedIds=[];
				
				for (var i = 0; i < elementsIds.length; i++) {
						var policyId = elementsIds[i];
						var isDeployed = elementsValues.includes(policyId);
						if (isDeployed) {
							deployedIds.push(policyId);
							$.ajax({
								type : "GET",
								url : "/policy/"
									+ policyId+"/revoke",
									contentType : "application/json",
									async : false,
									complete : function(data) {
//										console.log(data.status);
										if (data.status == 500) {
											failedIds.push(policyId);
										}
										else{
											var filter = getParameterByName('filter');
											if (filter == 'deployed' || filter == 'undeployed') {
												var row = document.getElementById(policyId + '_row');
												row.parentNode.removeChild(row);
											}
											
											filter = getParameterByName('tab');
											if (filter == 'deployed' || filter == 'undeployed') {
												var row = document.getElementById(policyId + '_row');
												row.parentNode.removeChild(row);
											}
						
											deployed -= 1;
											undeployed += 1;
											document.getElementById(policyId+"_delete").setAttribute("onclick", "deletePolicy('"+policyId+"', false)"); 
											document.getElementById(policyId+"_toggle").setAttribute("checked",false);
											document.getElementById("link_all").innerHTML = "All (" + (deployed +  undeployed) + ")";
											document.getElementById("link_deployed").innerHTML = "Deployed (" + deployed + ")";
											document.getElementById("link_undeployed").innerHTML = "Not Deployed (" + undeployed + ")";
										}
									}
							});
						}
				}

				
				if(failedIds.length>0){
					for (var j = 0; j < failedIds.length; j++) {	
						new PNotify(
								{
									title : "Failed to revoke Policy",
									type : "error",
									text : "Policy with id "
										+ failedIds[j]
								+ " revoke failed",
								styling : 'bootstrap3'
								});
					}
				}
				else{
					location.reload(false);
				}
				
			}
		}
	});

}

function bulkDeploy(tabId) {
	var elementsIds = getCheckedElementsIds(tabId);
	var elementsValues=getCheckedElementsValues(tabId);
	var count = elementsIds.length;
	
	var msg;
	if (count == 0) {
		return;
	}
	
	var failedIds=[];
	var deployedIds=[];
	for (var i = 0; i < elementsIds.length; i++) {
			var policyId =elementsIds[i];
			var isDeployed = elementsValues.includes(policyId);
			if (!isDeployed) {
				deployedIds.push(policyId);
				$.ajax({
					type : "GET",
					url : "/policy/"
						+ policyId+"/deploy",
						contentType : "application/json",
						async : false,
						complete : function(data) {
//							alert(data.status+", "+policyId);
							if (data.status == 500) {
								failedIds.push(policyId);
							}
							else{
								var filter = getParameterByName('filter');
								if (filter == 'deployed' || filter == 'undeployed') {
									var row = document.getElementById(policyId + '_row');
									row.parentNode.removeChild(row);
									
								}
								
								filter = getParameterByName('tab');
								if (filter == 'deployed' || filter == 'undeployed') {
									var row = document.getElementById(policyId + '_row');
									row.parentNode.removeChild(row);
								}
			
								deployed += 1;
								undeployed -= 1;
								document.getElementById(policyId+"_toggle").setAttribute("checked","checked");
								document.getElementById(policyId+"_delete").setAttribute("onclick", "deletePolicy('"+policyId+"', true)"); 
								
								document.getElementById("link_all").innerHTML = "All (" + (deployed +  undeployed) + ")";
								document.getElementById("link_deployed").innerHTML = "Deployed (" + deployed + ")";
								document.getElementById("link_undeployed").innerHTML = "Not Deployed (" + undeployed + ")";
							}
						}
				});
			}
	}

		
	if(failedIds.length>0){
		for (var j = 0; j < failedIds.length; j++) {	
			new PNotify(
					{
						title : "Failed to deploy Policy",
						type : "error",
						text : "Policy with id "
							+ failedIds[j]
					+ " deploy failed",
					styling : 'bootstrap3'
					});
		}
	}
	else{
	   location.reload(false);
	}

}
function toggleDeployPolicy(policyId, checkbox) {
	if(policyId === null || !(policyId) || policyId== "null" || policyId.endsWith("null")){
		policyId = extractPolicyIdFromPolicy();
	}
	if (pendingList.has(policyId)) {
		// since "delete" is a reserved word, we use this way
		pendingList['delete'](policyId);
		return;
	}
	var state = checkbox.checked;
	$.ajax({
		type : "GET",
		url : !state ? "/policy/" + policyId + "/revoke" : "/policy/" + policyId + "/deploy",
				contentType : "application/json",
				async : true,
				complete : function(data) {
					if (data.status == 200 && data.responseText == "true") {
						//var filter = getParameterByName('filter');
						//if (filter == 'deployed' || filter == 'undeployed') {
						//	var row = document.getElementById(policyId + '_row');
						//	row.parentNode.removeChild(row);
						//}
						
						var filter = getParameterByName('tab');
						if (filter == 'deployed' || filter == 'undeployed') {
							var row = document.getElementById(policyId + '_row');
							row.parentNode.removeChild(row);
						}


						if (!state) {
							deployed -= 1;
							undeployed += 1;
							try{
							document.getElementById(policyId+"_delete").setAttribute("onclick", "deletePolicy('"+policyId+"', false)"); 
							}catch(e){console.log("could not update the delete button");}
							try{document.getElementById(policyId+"_toggle").setAttribute("checked",false);
							}catch(e){console.log("could not update the toggle button");}
							if(deployed<=0)
								location.reload(true);
						} else {
							deployed += 1;
							undeployed -= 1;
							try{document.getElementById(policyId+"_toggle").setAttribute("checked","checked");
							}catch(e){console.log("could not update the toggle button");}
							try{
							document.getElementById(policyId+"_delete").setAttribute("onclick", "deletePolicy('"+policyId+"', true)"); 
							}catch(e){console.log("could not update the delete button");}
							if(deployed<=0)
								location.reload(true);
						}
						document.getElementById("link_all").innerHTML = "All (" + (deployed +  undeployed) + ")";
						document.getElementById("link_deployed").innerHTML = "Deployed (" + deployed + ")";
						document.getElementById("link_undeployed").innerHTML = "Not Deployed (" + undeployed + ")";
						//location.reload(false);
					} else {
						pendingList.add(policyId);
						var name = policyId + '_toggle';
						document.getElementById(name).click();

						if (data.status == 401 || data.status == 403) {
							bootbox.alert("Policy was not " + (state ? "deployed" : "revoked") + ". You do not have the privilege!");
						}else {
							bootbox.alert("Policy was not " + (state ? "deployed" : "revoked"));
						}
					}
				}
	
	});
}

function deletePolicy(policyId, depolyedSate) {
	if(policyId === null || !(policyId) || policyId== "null" || policyId.endsWith("null")){
		policyId = extractPolicyIdFromPolicy();
	}
	if (depolyedSate) {
		new PNotify({
			title : "Can not delete Policy",
			type : "error",
			text : "As " + policyId
			+ " policy is deployed, it can not be deleted",
			styling : 'bootstrap3'
		});
	} else {
		bootbox
		.confirm({
			message : 'Do you want to delete policy with id '
				+ policyId + '?',
				buttons : {
					confirm : {
						label : 'Delete',
						className : 'btn-danger'
					},
					cancel : {
						label : 'Keep',
						className : 'btn-default'
					}
				},
				callback : function(result) {
					if (result) {
						$
						.ajax({
							type : "DELETE",
							url : "/policy/"
								+ policyId+"/"
								+ "?reason=Please add functionality to ask reason",
								contentType : "application/json",
								async : true,
								complete : function(data) {
									if (data.status == 200) {
										window.location = '/policies/';
									} else if (data.status == 401) {
										location.reload(true);
									} else {
										new PNotify(
												{
													title : "Can not delete Policy",
													type : "error",
													text : "Unable to delete "
														+ policyId
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

function refreshPolicy(policyId) {
	new PNotify({
		title : "Implement functionality to refresh policy!",
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
		if(count==1)
			msg = "Do you really want to delete this policy?";
		else
		msg = "Do you really want to delete these <b> " + count
		+ " </b>policies?";
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
				label : 'Keep',
				className : 'btn'
			}
		},
		callback : function(result) {
			if (result) {
				var failedIds=[];
				var deployedIds=[];
				var todelete=[];
				for (var i = 0; i < elementsIds.length; i++) {
						var policyId = elementsIds[i];
						var isDeployed = elementsValues.includes(policyId);
						//console.log(policyId+", "+isDeployed);
						if (isDeployed) {
							deployedIds.push(policyId);
						} else {
							$.ajax({
								type : "DELETE",
								url : "/policy/"
									+ policyId+"/"
									+ "?reason=Please add functionality to ask reason",
									contentType : "application/json",
									async : false,
									complete : function(data) {
										//console.log(data.status);
										if (data.status == 500) {
											failedIds.push(policyId);
										}
										else{
											todelete.push(policyId+"_row");
										}
									}
							});
						}
				}
//				console.log(deployedIds);
//				console.log(failedIds);
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
						    
						    // remove the policies from datatable and redraw the table
							$('#policiesTable').DataTable().row(row).remove().draw();
						    
					}
				}
				if(failedIds.length>0){
					for (var j = 0; j < failedIds.length; j++) {	
						new PNotify(
								{
									title : "Failed to delete Policy",
									type : "error",
									text : "Policy with id "
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
								title : "Can not delete Policy",
								type : "error",
								text : "As "
									+ deployedIds[j]
									+ " policy is deployed, it can not be deleted",
									styling : 'bootstrap3'
							});
					}
				}
			}
		}
	});

}

function extractPolicyIdFromPolicy() {
	var xmlDoc = jQuery.parseXML(Xonomy.harvest());
	var x = xmlDoc.getElementsByTagName('policy');
	return x[0].getAttribute('id');
}


function exportPolicy(policyId) {
	if(policyId === null || !(policyId) || policyId== "null" || policyId.endsWith("null")){
		policyId = extractPolicyIdFromPolicy();
	}
	$.ajax({
		type : "GET",
		url : "/policy/" + policyId + "/export",
		contentType : "application/json",
		async : true,
		complete : function(data) {
			
			if (data.status == 200 && data.responseText.length>0) {
				var response = JSON.parse(data.responseText);
				var policy = response.policy;
				var blob = new Blob([prettyXml(policy, 3) ], {
					type : "text/xml;charset=utf-8"
				});
				saveAs(blob, policyId + ".xml",true);
				new PNotify({
					title : "Policy saved",
					type : "info",
					text : "Policy with id " + policyId
					+ " was saved on your system.",
					styling : 'bootstrap3'
				});
			}else if (data.status == 200 && data.responseText.length==0) {
			//policy not saved yet
				var harvestedXml = Xonomy.harvest();
				var blob = new Blob([prettyXml(harvestedXml, 3) ], {
					type : "text/xml;charset=utf-8"
				});
				saveAs(blob, policyId + ".xml",true);
				new PNotify({
					title : "Policy saved",
					type : "info",
					text : "Policy with id " + policyId
					+ " was saved but might not be valid.",
					styling : 'bootstrap3'
				});
			} else if (data.status == 401) {
				location.reload(true);
			} else {
				new PNotify({
					title : "Failed to export Policy",
					type : "error",
					text : "Policy with id " + policyId
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
	var policyid;
	$("#"+id+" input:checked").filter(".dt-checkboxes").each(function() {
		try{
			tr =$(this).closest("tr");
			policyid = tr.attr("id").split("_row")[0];
		if(policyid !=null && policyid.length>1)
			selected.push(policyid);
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
	console.log(divId);
	var elements = getCheckedElementsIds(divId);
	console.log(elements.length);
	var zipFile = new JSZip();
	for (var i = 0; i < elements.length; i++) {
			var policyId = elements[i];
			$.ajax({
				type : "GET",
				url : "/policy/" + policyId + "/export",
				contentType : "application/json",
				async : false, // async is false bcz zipFile cant be
				// synchronized.
				complete : function(data) {
					if (data.status == 200) {
						var response = JSON.parse(data.responseText);
						var policyName = policyId.replace(/:/g, '_');
						zipFile.file(policyName + '.xml', prettyXml(response.policy, 3));
					} else if (data.status == 401) {
						location.reload(true);
					} else {
						new PNotify(
								{
									title : 'Fetched policy failed',
									type : "Info",
									text : "Policy with id "
										+ policyId
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
		var fileName = 'ind2uce-policies-' + Date.now() + '.zip'
		saveAs(content, fileName,true);
		new PNotify({
			title : 'Policies exported',
			type : "Info",
			text : 'All selected policies are saved in file ' + fileName,
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
