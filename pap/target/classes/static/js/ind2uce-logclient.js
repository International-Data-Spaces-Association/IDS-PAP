function LogClient(url, customTimezone, logUser, logPassword) {
  /**
   * log client data
   */
  var data = new LogClientData(url, customTimezone);
  
  /**
   * the view
   */
  var view = new LogClientView(data);
  view.init();
  
  /**
   * initiate the log client
   */
  this.init = function() {
    // clear the cache
//    localStorage.removeItem("request");
//    localStorage.removeItem("messagedetails");
	  
//	var visibleColumns = localStorage.getItem("visibleColumns").split(",");
//	if(visibleColumns){
//		visibleColumns.forEach(function(value) {
//			data.view.visibleColumns[value] = true;
//		});
//	}
    
    // set the timezone and update the default from and to date
    moment.tz.setDefault(data.getSelectedTimezone().zoneid);
    data.request.from = moment().subtract(0, 'days').startOf('day').toDate();
    data.request.to = moment().endOf('day').toDate();
    
    // Do an initial search
    updateRequest();
    search();
    
    // test: setInterval function
//    setInterval(updateRequest, 10000);
    
    // init the handlers
    initHandlers();
  };
  
  /**
   * init handlers
   */
  var initHandlers = function() {    
    // handler for the send request button
    $("#logclient-action-send-request").on("click", sendRequest);
    
    // handler for the edit request button
    $("#logclient-query-summary").on("click", "#logclient-action-edit-request", editRequest);
    
    // handler for the timerange quick select toolbar
    $("#logclient-timerange-selector").on("click", ".timerange-quickselect", updateTimerange);
    
    /**
     * handler if the date range picker is shown
     * updates the values
     */
    initDaterangepickerHandlers();
    
    // minimized button for quick select values
    $("#logclient-quickselect-values-minimized").on("click", function() {
      $("#logclient-timerange-summary").val("");
      view.maximizeQuickSelect();
    });
    
    // minimized button for quick select values
    $("#logclient-quickselect-values-minimized").on("click", function() {
      $("#logclient-timerange-summary").val("");
      view.maximizeQuickSelect();
    });
    
    // handler for updating the log category
    $("#logclient-logcategory").on("click", "a", updateLogCategory);
    
    // handler for updating the checkboxes in the request part
    $("#logclient-request").on("change", ".logclient-checkbox", updateCheckbox);
    
    // handler for changing the timezone
    $("#logclient-use-timezone").on("click", "a", updateTimezone);
    
    // handler for visible columns
    $("#logclient-visible-columns").on("click", "a", updateVisibleColumn);
  };
  
  /**
   * handlers for the daterangepicker
   */
  var initDaterangepickerHandlers = function() {
    $("#logclient-timerange-summary").on("show.daterangepicker", function(ev, picker) {
      
      // update the ranges
      picker.ranges = {
        "Today": [
          moment().subtract(0, 'days').startOf('day'),
          moment().endOf('day')
        ],
        "Yesterday": [
          moment().subtract(1, 'days').startOf('day'),
          moment().subtract(1, 'days').endOf('day'),
        ],
        "This week": [
          moment().subtract(0, 'weeks').startOf('week'),
          moment().endOf('day')
        ],
        "This Month": [
            moment().subtract(0, 'month').startOf('month'),
            moment().endOf('day')
        ],
        "Last Month": [
          moment().subtract(1, 'month').startOf('month'),
          moment().subtract(1, 'month').endOf('month'),
        ],
        "Last 3 Months": [
          moment().subtract(3, 'month').startOf('month'),
          moment().endOf('day')
        ]
      };
    });
    
    /*
     * handler for the apply custom time range button
     * this will set the timerange, update the search type to absolute
     * and minimizes the quick select toolbar
     */ 
    $("#logclient-timerange-summary").on("apply.daterangepicker", function(ev, picker) {
      data.request.searchType = "absolute"; // set the search type to absolute
      data.request.from = picker.startDate.toDate();
      data.request.to = picker.endDate.toDate();
      view.minimizeQuickSelect();
      updateRequest();
    });
    
    // cancel button in the daterangepicker
    $("#logclient-timerange-summary").on("cancel.daterangepicker", function(ev, picker) {
      // nothing to do
    });
  }
  
  /**
   * update the checkboxes in the request part
   * the key and value will be stored in the search query object
   */
  var updateCheckbox = function() {
    var checked = $(this).prop("checked");
    var key = $(this).data("key");
    var value = $(this).data("value");
    data.view.field[key][value].checked = checked;
    
    // add to the search query
    if(checked){
      data.request.searchQuery.addField(key, value);
    }
    else {
      data.request.searchQuery.removeField(key, value);
    }
  };
  
  /**
   * handler: update the log category
   */
  var updateLogCategory = function() {
    var logCategory = $(this).data("value");
    data.request.logCategory = logCategory;
    
    // store the log category in the session storage
    localStorage.setItem("logCategory", logCategory);
    
    $(this).parents(".dropdown").find('.btn').html($(this).text() + ' <span class="caret"></span>');
    
    updateRequest();
  };
  
  /**
   * edit the request
   */
  var editRequest = function() {
    // if the request was minimized, maximize it first
//    view.showSummary(false);
  };
  
  /**
   * send request
   */
  var sendRequest = function() {
    // TODO: minimize the request part and show a summary
//    view.showSummary(true);
    
    // remove the no result div if it is there
    view.showNoResults(false);
    
    // get the additional fields
    var additionalTerms = $('#logclient-additional-terms').val();
    
    // search
    search();
  };
  
  /**
   * update the timerange
   */
  var updateTimerange = function() {
    // get the input radio button from inside the label
    // get the value and the keyfrom the data tags
    var element = $(this).find("input");
    var key = element.data("key");
    var value = element.data("value");
    switch(key) {
      case "rel": // relative timerange
        data.request.searchType = "relative";
        data.request.range = value;
        break;
      case "abs": // absolute timerange
        data.request.searchType = "absolute";
        break;
      case "keyword": // timerange by keyword
        switch(value) {
        case "today":
          data.request.from = moment().subtract(0, 'days').startOf('day').toDate();
          data.request.to = moment().endOf('day').toDate();
          break;
        case "thisweek":
          data.request.from = moment().subtract(0, 'weeks').startOf('week').toDate();
          data.request.to = moment().endOf('day').toDate();
          break;
        case "thismonth":
          data.request.from = moment().subtract(0, 'months').startOf('month').toDate();
          data.request.to = moment().endOf('day').toDate();
          break;
        default:
          // don't know
        }
        data.request.searchType = "absolute"; // set the search type to absolute
        break;
      default:
        data.request.searchType = "relative";
        data.request.range = 0;
    }
    updateRequest();
  };
  
  /**
   * update the visible columns in the view
   */
  var updateVisibleColumn = function() {
	  var input = $(this).find("input");
	  var value =  $(this).data("value");
	  var checked = input.prop("checked");
	  
	  if(checked) {
		  input.prop("checked", false);
		  data.view.visibleColumns[value] = false;
	  }
	  else {
		  input.prop("checked", true);
		  data.view.visibleColumns[value] = true;
	  }
	  
	  // update the cache
	  var visibleColumns = [];
	  Object.keys(data.view.visibleColumns).forEach(function(key, index) {
		  if(data.view.visibleColumns[key] == true) {
			  visibleColumns.push(key);
		  }
	  });
	  localStorage.setItem("visibleColumns", visibleColumns);
	  
	  // update the datatable
	  var column = $("#log-table").DataTable().column($(this).data('column'));
	  column.visible(!column.visible());
	  
	  return false;
  }
  
  /**
   * update the request data
   * if the timerange or the log category was changed, update the values
   * in the request part
   */
  var updateRequest = function() {
    console.log("update request");
    var dateFrom = "";
    var dateTo = "";
    var timezone = data.getSelectedTimezone().zoneid;
    
    // switch the search type
    switch (data.request.searchType) {
    case 'absolute':
      searchAbsoluteTerms("level", "", view.updateRequest, "*");
      searchAbsoluteTerms("component", "level", view.updateRequest, "*");
      searchAbsoluteStats("_exists_", "stack_trace", view.updateRequest, "*");
      searchAbsoluteStats("_exists_", "X-B3-TraceId", view.updateRequest, "*");
      dateFrom = moment(data.request.from).tz(timezone).format();
      dateTo = moment(data.request.to).tz(timezone).format();
      $('#logclient-timerange-summary').val(dateFrom + " - " + dateTo);
      break;
    case 'relative':
      searchRelativeTerms("level", "", view.updateRequest, "*");
      searchRelativeTerms("component", "level", view.updateRequest, "*");
      searchRelativeStats("_exists_", "stack_trace", view.updateRequest, "*");
      searchRelativeStats("_exists_", "X-B3-TraceId", view.updateRequest, "*");
      if(data.request.range > 0) {
        dateFrom = moment().subtract(data.request.range, "seconds").tz(timezone).format();
        dateTo = moment.tz(timezone).format();
        $('#logclient-timerange-summary').val(dateFrom + " - " + dateTo);
      }
      else {
        $('#logclient-timerange-summary').val("");
      }
      break;
    default:
      // do nothing
    }
  };
  
  /**
   * update the timezone data
   */
  var updateTimezone = function() {
    var e = $(this);
    data.useTimezone = e.data("value");
    
    // cache the selected timezone
    localStorage.setItem("timezone", e.data("value"));
    
    // update the button text in the dropdown menu
    e.parents(".dropdown").find('.btn').html($(this).text() + ' <span class="caret"></span>');
    
    // update the message table
    // this will recalculate the timestamps
    view.updateMessageTable();
    
    // set the timezone as default
    moment.tz.setDefault(data.getSelectedTimezone().zoneid);
    
    // update the daterangepicker. if the timezone changes, the max date, end date etc have to be changed also
    // note: initialize the daterangepicker a second time with same element will remove the previous instance
    view.initOrUpdateDaterangepicker();
    initDaterangepickerHandlers();
    
    // update the request part -> timestamps are recalculated
    updateRequest();
  }
  
  /**
   * search function
   */
  var search = function() {
    view.showRequestProcessing(true);
    switch (data.request.searchType) {
    case 'absolute':
      searchAbsolute();
      break;
    case 'relative':
      searchRelative();
      break;
    default:
      searchRelative();
    }
  };
  
  
  /**
   * search with an absolute timerange
   */
   var searchAbsolute = function() {
    var requestData = {
        query: data.request.searchQuery.getQueryString(),
        from: data.request.from.toISOString(),
        to: data.request.to.toISOString(),
        limit: data.request.limit,
        fields: data.request.queriedFields.toString()
    };

    getJSON(data.searchPath + "absolute", requestData).complete(function() {
      console.debug("absolute timerange request completed");
      if(result.messages.length > 0){
        data.result = result;
        view.createMessageTable();
      }
      else {
        console.debug("no result");
        view.showNoResults(true);
      }
      view.showRequestProcessing(false);
    });
  };
  
  /**
   * search for relative stats
   */
  var searchAbsoluteStats = function(id, _field, callback, customSearchQuery) {
    var requestData = {
        field: _field,
        from: data.request.from.toISOString(),
        to: data.request.to.toISOString()
    };
    
    // get the search query string
    if(customSearchQuery !== undefined && customSearchQuery !== "") {
      requestData.query = customSearchQuery;
    }
    else {
      requestData.query = data.request.searchQuery.getQueryString();
    }
    
    // get the json data
    getJSON(data.searchPath + "absolute/stats", requestData).complete(function() {
      
        /*
         * hacking around graylog issue
         * if the count of the result is 0, graylog will give you
         * a min number
         */
        if(result.count === -9223372036854776000) {
          result.count = 0;
        }
        data.view.field[id][_field].all = result.count;
      
      /*
       * if there is a given callback function, call it
       */
      if(callback !== undefined && $.isFunction(callback)) {
        callback();
      }
    });
  };
  
  /**
   * search for terms in an absolute timerange
   */
  var searchAbsoluteTerms = function(_field, stackedField, callback, customSearchQuery) {
    /**
     * request data
     */
    var requestData = {
        field: _field,
        from: data.request.from.toISOString(),
        to: data.request.to.toISOString()
    };
    
    // get the search query string
    if(customSearchQuery !== undefined && customSearchQuery !== "") {
      requestData.query = customSearchQuery;
    }
    else {
      requestData.query = data.request.searchQuery.getQueryString();
    }
    
    // add the stacked field if it is available
    if(stackedField) {
      requestData.stacked_fields = stackedField;
    }
    
    // get the json data
    getJSON(data.searchPath + "absolute/terms", requestData).complete(function() {
      data.clearView(_field);
      
      // update values for the view
      if(stackedField) {
        for(var term in result.terms) {
          for(var fieldName in data.view.field[_field]) {
            if(term.startsWith(fieldName + " - ")) {
              var stackedFieldPropertyName = term.split(" - ")[1];
              
              // add every stacked field value to the "all" value
              data.view.field[_field][fieldName].all += result.terms[term];
              
              // update values
              data.view.field[_field][fieldName][stackedField][stackedFieldPropertyName] = result.terms[term];
            }
          }
        }
      }
      else { // no stacked field
        for (var key in data.view.field[_field]) {
          if(result.terms.hasOwnProperty(key)) {
            data.view.field[_field][key].all = result.terms[key];
          }
          else {
            data.view.field[_field][key].all = 0;
          }
        }
      }
      
      /*
       * if there is a given callback function, call it
       */
      if(callback !== undefined && $.isFunction(callback)) {
        callback();
      }
    });
  };
  
  /**
   * do a relative search
   */
  var searchRelative = function() {

    /** request data */
    var requestData = {
        query: data.request.searchQuery.getQueryString(),
        range: data.request.range,
        limit: data.request.limit,
        fields: data.request.queriedFields.toString()
    };   

    
    getJSON(data.searchPath + "relative", requestData).done(function() {
        console.debug("request completed");
        if(result.messages.length > 0){
          data.result = result;
          view.createMessageTable();
        }
        else { // no result
          console.debug("no result");
          view.showNoResults(true);
        }
        view.showRequestProcessing(false);
    });
  };
  
  /**
   * search for relative stats
   * this will update the data.view object
   */
  var searchRelativeStats = function(id, _field, callback, customSearchQuery) {
    var requestData = {
        field: _field,
        range: data.request.range
    };
    
    // get the search query string
    if(customSearchQuery !== undefined && customSearchQuery !== "") {
      requestData.query = customSearchQuery;
    }
    else {
      requestData.query = data.request.searchQuery.getQueryString();
    }
    
    // get json
    getJSON(data.searchPath + "relative/stats", requestData).done(function() {
      /*
       * hacking around graylog issue
       * if the count of the result is 0, graylog will give you
       * a min number
       */
      if(result.count === -9223372036854776000) {
        result.count = 0;
      }
      data.view.field[id][_field].all = result.count;
       
      /*
       * if there is a given callback function, call it
       */
      if(callback !== undefined && $.isFunction(callback)) {
        callback();
      }
    });
  };
  
  /**
   * count field values in a relative timerange
   * this will update the data.view object
   * 
   * @param _field name of the field
   * @param stackedField name of an optional stacked field
   * @param callback optional callback function
   */
  var searchRelativeTerms = function(_field, stackedField, callback, customSearchQuery) {
    
    // getting the request data
    var requestData = {
        field: _field,
        range: data.request.range
    };
    
    // get the search query string
    if(customSearchQuery !== undefined && customSearchQuery !== "") {
      requestData.query = customSearchQuery;
    }
    else {
      requestData.query = data.request.searchQuery.getQueryString();
    }
    
    // add the stacked field if it is available
    if(stackedField) {
      requestData.stacked_fields = stackedField;
    }
    
    // get json
    getJSON(data.searchPath + "relative/terms", requestData).done(function() {
      // clear the view data for the given field
      data.clearView(_field);  

      /*
       * check if there is a stacked field
       * if true the result has to be recalculated
       */
      if(stackedField) {
        for(var term in result.terms) {
          for(var fieldName in data.view.field[_field]) {
            if(term.startsWith(fieldName + " - ")) {
              var stackedFieldPropertyName = term.split(" - ")[1];

              // add every stacked field value to the "all" value
              data.view.field[_field][fieldName].all += result.terms[term];

              // update values
              data.view.field[_field][fieldName][stackedField][stackedFieldPropertyName] = result.terms[term];
            }
          }
        }
      }
      else { // no stacked field
        // get the updated data
        for (var key in data.view.field[_field]) {
          if(result.terms.hasOwnProperty(key)) {
            // this should be used with an "all" property
            data.view.field[_field][key].all = result.terms[key];
          }
          else {
            data.view.field[_field][key].all = 0;
          }
        }
      } 

      /*
       * if there is a given callback function, call it
       */
      if(callback !== undefined && $.isFunction(callback)) {
        callback();
      }
    });
  };
  
  /**
   * get the json data
   * 
   * @params requestData the request data
   * @return jqhxr object
   */
  var getJSON = function(action, _requestData) {
    /**
     * request data
     */
    var requestData = _requestData;

    // add log category to request data
    requestData.category = data.request.logCategory;

    var jqhxr = $.getJSON({
      url: data.url + action,
      data: requestData,
      beforeSend: setHeaders
    })
    .done(function(data) {
      result = data;
    })
    .fail(function() {
      view.showError();
    })
    .always(function() {
    });
    return jqhxr;
  };
  
  /**
   * set headers if required
   */
  var setHeaders = function(xhr) {
    // add auhtorization header only if credentials are provided
    if((logUser !== undefined) && (logPassword !== undefined)) {
      xhr.setRequestHeader("Authorization", "Basic " + btoa(logUser + ":"+ logPassword));
    }
    
    // fix wrong mime type 
    if (xhr.overrideMimeType) {
      xhr.overrideMimeType("application/json");
    }
  };
}

/**
 * the view object
 * 
 * @param _data log client data
 * @returns the view object
 */
function LogClientView(_data) {
  /**
   * logging data
   */
  var data = _data;
  
  /**
   * init the log view
   */
  this.init = function() {
    
    /*
     * init timezone dropdown
     */
    $("#logclient-tz-solution").html(data.getTimezoneDataAsString("solution"));
    $("#logclient-tz-local").html(data.getTimezoneDataAsString("local"));  
    $("#logclient-use-timezone").find('.btn').html(data.getTimezoneDataAsString() + ' <span class="caret"></span>');
    
    /*
     * init log category dropdown
     */
    var text = "";
    switch(data.request.logCategory) {
    case "all":
      text = "All";
      break;
    case "sol":
      text = "Only selected solution";
      break;
    case "int":
      text = "Internal";
      break;
    case "sol_int":
      text = "Selected solution + internal";
      break;
    default:
      text = "Select log category";
    }
    $("#logclient-logcategory").find('.btn').html(text + ' <span class="caret"></span>');
    
    // init the daterangepicker
    this.initOrUpdateDaterangepicker();
  };
  
  /**
   * init or update the daterangepicker
   */
  this.initOrUpdateDaterangepicker = function () {
    $('#logclient-timerange-summary').daterangepicker({
      "timePicker": true,
      "timePicker24Hour": true,
      "timePickerSeconds": true,
      "alwaysShowCalendars": true,
      "ranges": {
          "Today": [
            moment().subtract(0, 'days').startOf('day'),
            moment().endOf('day')
          ],
          "Yesterday": [
            moment().subtract(1, 'days').startOf('day'),
            moment().subtract(1, 'days').endOf('day'),
          ],
          "This week": [
            moment().subtract(0, 'weeks').startOf('week'),
            moment().endOf('day')
          ],
          "This Month": [
              moment().subtract(0, 'month').startOf('month'),
              moment().endOf('day')
          ],
          "Last Month": [
            moment().subtract(1, 'month').startOf('month'),
            moment().subtract(1, 'month').endOf('month'),
          ],
          "Last 3 Months": [
            moment().subtract(3, 'month').startOf('month'),
            moment().endOf('day')
          ]
      },
      "startDate": data.request.from,
      "endDate": data.request.to,
      "showCustomRangeLabel": false,
      "autoUpdateInput": false,
      "opens": "left",
      "buttonClasses": "btn",
      "applyClass": "btn-primary"
    }, function(start, end, label) {
      console.log('New date range selected: ' + start.format() + ' to ' + end.format() + ' (predefined range: ' + label + ')');
    });
  };
  
  /**
   * update the request part
   */
  this.updateRequest = function() {
    renderTemplateFromFile("#logclient-request", "request", data.view);
  };
  
  /**
   * minimize the quickselect toolbar
   */
  this.minimizeQuickSelect = function() {
    $("#logclient-quickselect-values").hide();
    $("#logclient-quickselect-values-minimized").show();
  };
  
  /**
   * maximize the quickselect toolbar
   */
  this.maximizeQuickSelect = function() {
    $("#logclient-quickselect-values").show();
    $("#logclient-quickselect-values-minimized").hide();
  };
  
  /**
   * show a summary of the request
   */
  this.showSummary = function(visible) {
    if(visible !== false) { // minimize the request
        $("#logclient-query").slideUp("slow");
        $("#logclient-query-summary").fadeIn("slow");
        renderTemplateFromFile("#logclient-query-summary", "summary", data);
    }
    else { // maximize the request
      $("#logclient-query-summary").fadeOut("fast");
      $("#logclient-query").slideDown("fast");
    }
  };
  
  /**
   * show while updating the request data on top of the page
   * 
   * @param processing
   */
  this.showUpdateRequestProcessing = function(processing) {
    if(processing) {
      // TODO: show processing for request
    }
    else {
      // TODO: stop showing processing for request
    }
  };
  
  /**
   * show while a request is processed
   * 
   * @param processing
   */
  this.showRequestProcessing = function(processing) {
    if(processing) {
     $("#logclient-action-send-request").button('loading'); 
     $("#logclient-modal-processing-request").modal('show');
     $('.modal-backdrop').appendTo('#logclient-result');
     $('body').removeClass("modal-open");
     $('body').css("padding-right","");
     
    }
    else {
      $("#logclient-action-send-request").button('reset');
      $("#logclient-modal-processing-request").modal("hide");
    }
  };
  
  /**
   * show the specific modal dialog if there is no result
   * 
   * @param visible 
   */
  this.showNoResults = function(visible) {
    if(visible) {
      $('#logclient-modal-no-results').modal("show");
      $('.modal-backdrop').appendTo('#logclient-result');   
      
      //remove the padding right and modal-open class from the body tag which bootstrap adds when a modal is shown
      $('body').removeClass("modal-open");
      $('body').css("padding-right","");
    }
    else {
      $('#logclient-modal-no-results').modal("hide");
    }
  };
  
  /**
   * show an error message if the log system is not available
   */
  this.showError = function() {
    $('#logclient-modal-error').modal("show");
    $("#logclient-action-send-request").button('reset');
    $("#logclient-modal-processing-request").modal("hide");
  };
  
  /**
   * create the resulst message table
   */
  this.createMessageTable = function() {
//    this.clearContent();
    // if there is a datatable, clear it before showing new logs
    if($.fn.dataTable.isDataTable('#log-table')) {
      $('#log-table').DataTable().clear();
      $('#log-table').DataTable().draw();
      $('#log-table').DataTable().destroy();
    }
    
    // init the datatable
    $('#log-table').DataTable({
        tableClass: "table",
        data: data.result.messages,
        dom: "<'row'<'col-sm-3'li><'col-sm-6'p><'col-sm-3'f>>" +
           "<'row'<'col-sm-12'tr>>" +
           "<'row'<'col-sm-3'i><'col-sm-6'p><'col-sm-3'>>",
        "columns": [
          {
            "name": "details",
            "defaultContent": "<i class='fa fa-plus-square'></i>",
            "orderable": false,
            "searching": false,
            "className": ""
          },
          {
            "data": "message.timestamp",
            "className": "text-center text-nowrap",
            "render": renderTimestamp,
          },
          {
            "name": "level",
            "data": "message.level",
            "className": "",
//            "render": renderLevel,
            "visible": data.view.visibleColumns.level
          },
          {
            "name": "logger_name",
            "data": "message.logger_name",
            "className": "text-nowrap",
            "visible": data.view.visibleColumns.logger_name
          },
          {
            "name": "thread_name",
            "data": "message.thread_name",
            "className": "text-nowrap",
            "visible": data.view.visibleColumns.thread
          },
          {
            "name": "X-B3-TraceId",
            "data": "message.X-B3-TraceId",
            "defaultContent": "",
            "visible": data.view.visibleColumns.trace_id
          },
          {
            "name": "component",
            "data": "message.component",
            "defaultContent": "",
            "className": "",
            "visible": data.view.visibleColumns.component
          },
          {
            "name": "solution",
            "data": "message.solution",
            "defaultContent": "",
            "visible": data.view.visibleColumns.solution
          },
          {
            "name": "message",
            "data": "message.message",
            "defaultContent": "",
            "className": "wordbreak fill",
            "orderable": false,
            "render": renderMessage,
            "visible": data.view.visibleColumns.message
          }
        ],
        language: {
          search: "_INPUT_",
          searchPlaceholder: "Filter results..."
        },
        "order": [1, 'desc'],
        "lengthMenu": [ [10, 25, 50, 100, 250, -1], [10, 25, 50, 100, 250, "All"] ],
        "pageLength": 100,
        "processing": true,
        "deferRender": true,
//        "pagingType": "numbers",
        "autoWidth": false,
        mark: true,
        createdRow: function(row, data, index) {
          $(row).addClass("details-control");
          
          // TODO: after creating a row, additional data can be added
          // show warning, error sign etc.
          if(data.message.stack_trace) {
            //
          }
          if(data.message.level === "WARN") {
            // 
          }
        },
        initComplete: function () {
          // show the log table
          $("#log-table").fadeIn();
          
          // this is only used if a column should be searchable
          // via a dropdown menu on top of the column in the table header
          this.api().columns().every( function () {
            var column = this;
            if($(column).hasClass("searchable")) {
              var select = $('<br><select><option value=""></option></select>')
                  .appendTo( $(column.header()) )
                  .on( 'change', function () {
                      var val = $.fn.dataTable.util.escapeRegex(
                          $(this).val()
                      );
  
                      column
                          .search( val ? '^'+val+'$' : '', true, false )
                          .draw();
                  } );
  
              column.data().unique().sort().each( function ( d, j ) {
                  select.append( '<option value="'+d+'">'+d+'</option>' );
              } );
            }
          });
        }
      });
      
      // Remove the processing dialog and minimize the logclient view
      $("#logclient-view-processing").modal('hide');
      
      /*
       * add event listener for opening und closing message details
       */
      $('#log-table tbody').on('click', '.details-control', function() {
//        var tr = $(this).parents('tr'); // only for td
        var tr = $(this); // for tr
        var tdi = tr.find("i.fa");
        var row = $('#log-table').DataTable().row(tr);
        
        if(row.child.isShown()) {
            $('div.slider', row.child()).slideUp("fast", function(){
            row.child.hide();
            tr.removeClass('shown');
            // toggle plus and minus button
            tdi.first().removeClass("fa-minus-square");
            tdi.first().addClass("fa-plus-square");
          });
        }
        else {        
          // add a row child
          row.child("loading...", 'word-break');
 
          // get the td element of the created row
          var td = row.child().find("td");
          
          // render the element
          renderTemplateFromFile(td, "messagedetails", row.data());
          
          // show the child row
          row.child.show();
          
          tr.addClass('shown');
          $('div.slider', row.child(), 'no-padding').slideDown("fast");

          // toggle plus and minus button
          tdi.first().removeClass("fa-plus-square");
          tdi.first().addClass("fa-minus-square");
          
          // Highlight matching text
          // TODO: this is not working atm
//          row.child.highlight( $.trim( table.search().split(/\s+/) ));
//          row.child().mark($("#log-table").DataTable().search());
//          row.mark($("#log-table").DataTable().search());
//          $(row.child()).mark("pmp");
        }
      });
      
      /*
       * render the timestamp
       */
      function renderTimestamp(text, type, row) {
        var timestamp = "";
        timestamp = moment(text).tz(data.getSelectedTimezone().zoneid).format();
        timestamp = timestamp.split("T");
        return "<strong>" + timestamp[0] + "</strong> " + timestamp[1];
      }
      
      /*
       * render the level
       */
      function renderLevel(text, type, row) {
        var cssClass = "";
        
        switch(text) {
          case "DEBUG":
            break;
          case "INFO":
            cssClass = "text-info";
            break;
          case "WARN":
            cssClass = "text-warning";
            break;
          case "ERROR":
            cssClass = "text-danger";
        }
        return '<span class="' + cssClass + '">' + text + '</span>';
      }
      
      /*
       * render the message
       */
      function renderMessage(text, type, row) {
        var msg = text;
        if(msg.length > 200) {
          msg = msg.substring(0, 200) + "...";
        }
        return msg;
      }
  };
  
  /**
   * this will rerender the message table (if available)
   * without changing the data
   */
  this.updateMessageTable = function() {
    if($.fn.dataTable.isDataTable('#log-table')) {
      $('#log-table').DataTable()
      .rows().invalidate('data')
      .draw(false);
    }
  };
  
  /**
   * render a mustache template from file
   * 
   * TEST: the template will be stored in the localStorage
   */
   var renderTemplateFromFile = function(element, templateName, templateData) {

//     const cachedTemplate = localStorage.getItem(templateName);
//     if(cachedTemplate) {
//       var rendered = Mustache.render(cachedTemplate, templateData);
//       console.log(rendered);
//       $(element).html(rendered);
//     }
//     else {
      var pathToTemplate = "/templates/logview/" + templateName + ".html";
      $.get(pathToTemplate, function(template) {
        Mustache.parse(template);
        localStorage.setItem(templateName, template);
        var rendered = Mustache.render(template, templateData);
        $(element).html(rendered);
      }, "html");
//     }
  };
  
  /**
   * render a mustache template from id (inline template)
   */
  var renderTemplateFromId = function(element, templateId, templateData) {
    var template = $("#"+templateId).html();
    Mustache.parse(template);
    var rendered = Mustache.render(template, templateData);
    $(element).html(rendered);
  };
}

/**
 * data model for the log client
 * 
 * @param _url
 * @returns
 */
function LogClientData(_url, customTimezone) {
  /**
   * url to the logging system
   */
  this.url = _url;
  
  /**
   * the div id the content is written to
   */
  this.divId = "";
  
  /**
   * the request data
   */
  this.request = new RequestData();
  
  /**
   * use timezone
   * - from "solution" (should be default)
   * - from "local" machine
   * - "utc"
   */
  if(localStorage.getItem("timezone")) {
    this.useTimezone = localStorage.getItem("timezone");
  }
  else {
    this.useTimezone = "solution";
  }
  
  /**
   * timezone data
   */
  this.timezone = {
      solution: {
        zoneid: "",
        offset: 0,
        offsetAsString: "",
      },
      local: {
        zoneid: moment.tz.guess(),
        offset: moment.tz(moment.tz.guess()).utcOffset(),
        offsetAsString: moment.tz(moment.tz.guess()).format('Z')
      },
      utc: {
        zoneid: "UTC",
        offset: 0,
        offsetAsString: ""
      }
  }
  
  //TODO use custom timezone if available and calculate offset
  if(customTimezone) {
    this.timezone.solution.zoneid = customTimezone;
    this.timezone.solution.offset = moment.tz(customTimezone).utcOffset();
    this.timezone.solution.offsetAsString = moment.tz(customTimezone).format('Z');
  }
  else {
    this.timezone.solution = this.timezone.utc;
  }
  
  /**
   * view data for the ui
   */
  this.view = {
      field: {
        level: {
          "ERROR" : {
            checked: true,
            all: 0,
            component: {}
          },
          "WARN": {
            checked: true,
            all: 0,
            component: {}
          },
          "INFO": {
            checked: false,
            all: 0,
            component: {}
          },
          "DEBUG": {
            checked: false,
            all: 0,
            component: {}
          }
        },
        component: {
          "pdp": {
            checked: true,
            all: 0,
            level: {
              "ERROR": 0,
              "WARN": 0,
              "INFO": 0,
              "DEBUG": 0
            }
          },
          "pmp": {
            checked: true,
            all: 0,
            level: {
              "ERROR": 0,
              "WARN": 0,
              "INFO": 0,
              "DEBUG": 0
            }
          }
        },
        _exists_: {
          "stack_trace": {
            checked: false,
            all: 0
          },
          "X-B3-TraceId": {
        	checked: false,
        	all: 0
          }
        }
      },
      visibleColumns: {
    	  "level": true,
    	  "component": true,
    	  "logger_name": false,
    	  "trace_id": false,
    	  "thread": false,
    	  "message": true,
    	  "solution": false
      }
  };
  
  /**
   * the search path
   */
  this.searchPath = "search/";
  
  this.clearView = function(_field) {
    for(var tmpkey in this.view.field[_field]) {
      // set all messages to 0
      this.view.field[_field][tmpkey].all = 0;
      
      for(var bla in this.view.field[_field][tmpkey]) {
        for(var blubb in this.view.field[_field][tmpkey][bla]) {            
          this.view.field[_field][tmpkey][bla][blubb] = 0;
        }
      }
    }
  };
  
  /**
   * return the timezone data as string
   * 
   * @param tz optional timezone id: solution, local or utc
   */
  this.getTimezoneDataAsString = function(tz) {
    if(tz) {
      return this.timezone[tz].zoneid + " (UTC" + this.timezone[tz].offsetAsString + ")";
    }
    else {
      return this.timezone[this.useTimezone].zoneid + " (UTC" + this.timezone[this.useTimezone].offsetAsString + ")";
    }
  }
  
  /**
   * get the selected timezone object
   */
  this.getSelectedTimezone = function() {
    return this.timezone[this.useTimezone];
  }
}


/**
 * data for the request
 * @returns
 */
function RequestData() {
  
  /**
   * search query
   */
  this.searchQuery = new SearchQuery();
  this.searchQuery.addField("level", "ERROR");
  this.searchQuery.addField("level", "WARN");
  this.searchQuery.addField("component", "pdp");
  this.searchQuery.addField("component", "pmp");
  
  /**
   * log category
   * all, int, sol or sol_int
   */
  if(localStorage.getItem("logCategory")) {
    this.logCategory = localStorage.getItem("logCategory");
  }
  else {
    this.logCategory = "sol";
  }
  
  /**
   * the search type
   * can be absolute or relative
   */
  this.searchType = "absolute";
  
  /**
   * the timerange for a relative search
   */
  this.range = "0";
  
  /**
   * limit the results
   * can be up to 10000
   */
  this.limit = 10000;
  
  /**
   * the from date for an absolute search
   */
  this.from = moment().subtract(0, 'days').startOf('day').toDate();
  
  /**
   * the to date for an absolute search
   */
  this.to = moment().endOf('day').toDate();
  
  /**
   * fields for the search query which are accessible in the log client
   */
  this.queriedFields = [
    "timestamp", "level", "logger_name", "thread_name", "component", "solution", "message", "X-B3-TraceId", "stack_trace"
  ];
  
  /**
   * additional terms
   */
  this.additionalTerms = "";
}

/**
 * Search Query
 * 
 * @returns
 */
function SearchQuery() {
  
  /**
   * the data object
   */
  var data = {};

  /**
   * add a field to the search query
   */
  this.addField = function(field, value) {
    if(!data.hasOwnProperty(field)) {
      data[field] = [];
    }
    data[field].push(value);
  };

  /**
   * check if a field is already in the search query
   */
  this.isInQuery = function(field, value) {
    if(data.hasOwnProperty(field)) {
      if(data[field].indexOf(value) != -1) {
        return true;
      }
    }
    return false;
  };

  /**
   * remove a field from the search query
   */
  this.removeField = function(field, value) {
    if(data.hasOwnProperty(field)) {
      var index = data[field].indexOf(value);
      if(index != -1) {
        data[field].splice(index, 1);
      }
    }
    if(data[field] !== undefined) {
      if(data[field].length === 0) {
        delete data[field];
      }
    }
  };

  /**
   * toggle a field
   * 
   * checks if a field:value is already in the query - if yes -> remove it - if
   * no -> add it to query
   */
  this.toggleField = function(field, value) {
    if(this.isInQuery(field, value)) { // the field is in the query -> remove
                                        // it
      this.removeField(field, value);
    }
    else { // the field is not in the query, add it!
      this.addField(field, value);
    }
  };

  /**
   * clear the query
   */
  this.clear = function() {
    data = {};
  };

  /**
   * return the query as a lucene string
   */
  this.getQueryString = function() {
    var query = "";
    if(Object.keys(data).length === 0) { // the query is empty
      query = "*";
    }
    else { // more than one field
      var x = Object.keys(data).length;
      var i = 0;
      
      // for each key in data
      Object.keys(data).forEach(function(key) {
        if(data[key].length == 1) {
          query += key + ":" + data[key][0];
        }
        else if(data[key].length > 1) {
          query += key + ":(" + data[key][0];
          for(var j = 1; j < data[key].length; j++) {
            query += " OR " + data[key][j];
          }
          query += ") ";
        }
        else {
          query = "*";
        }
        if(i < x-1) {
          query += " AND ";
        }
        i++;
      });
    }
    return query;
  };

  /**
   * returns an array of tags for the tag editor
   */
  this.getTags = function() {
    var result = [];
    if(Object.keys(data).length === 0) { // the query is empty
      return result;
    }
    else {
      Object.keys(data).forEach(function(key) {
        data[key].forEach(function(element) {
          result.push(key+":"+element);
        });
      });
      return result;
    }
  };
}

/**
 * workaround if startsWith is not implemented
 */
if (!String.prototype.startsWith) {
  String.prototype.startsWith = function(searchString, position) {
    position = position || 0;
    return this.indexOf(searchString, position) === position;
  };
}