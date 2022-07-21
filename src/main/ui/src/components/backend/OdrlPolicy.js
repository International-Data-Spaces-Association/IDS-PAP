/**
 * @file Manages the construction and recovery of the initial object through a given 
 * template policy, which later contains all user input
 * @author Tom Kollmer 
 */

/**
 * Creates an object that manages all user input
 * @returns {object} with all field names and values.
 */
function OdrlPolicy() {
  return {
    id: -1,
    policyType: "Agreement",
    target: "",
    provider: "",
    consumer: "",

    location_input: [""],
    location_op: "",
    application_input: [""],
    application_op: "",
    connector_input: [""],
    connector_op: "",
    role_input: [""],
    role_op: "",
    purpose_input: [""],
    purpose_op: "",
    event_input: [""],
    event_op: "",
    state_input: [""],
    state_op: "",
    securityLevel_input: [""],
    securityLevel_op: "",

    preduties_anomInRest: "",
    preduties_modifier: "",
    preduties_valueToChange: "",
    preduties_fieldToChange: "",

    postduties_anomInRest: "",
    postduties_logLevel: "",
    postduties_durationYear: "",
    postduties_durationMonth: "",
    postduties_durationDay: "",
    postduties_durationHour: "",
    postduties_timeAndDate: "",
    postduties_notificationLevel: "",
    postduties_informedParty: "",

    system: "",
    interval: "",
    payment: "",
    price: "",
    counter: "",
    encoding: "",
    policy: "",
    time: "",
    timeUnit: "",
    timeAndDate: "",
    durationHour: "",
    durationDay: "",
    durationMonth: "",
    durationYear: "",
    restrictTimeIntervalStart: "",
    restrictTimeIntervalEnd: "",
    restrictEndTime: "",
    specifyBeginTime: "",
    artifactState: "",

    is_template: false,
    name: "",
    comment:"",
  };
}

/**
 * Used to recreate the selectedComponents object that controls all selected form fields. 
 * This function is mainly used for editing a saved policy. 
 * @param {object} stateLocal local state object that contains the template policy.
 * @param {object} initialValues initial field values 
 * @param {object} selected_components initial selected component values
 * @returns {object} selected components with added values if stateLocal is not undefined.
 */
function recreateSelectedCompFromJson(stateLocal, initialValues, selected_components) {
  if (stateLocal !== undefined) {
    initialValues = stateLocal;
    var order = [];
    if (initialValues.application_input[0] !== "") {
      order.push("application");
    }
    if (initialValues.connector_input[0] !== "") {
      order.push("connector");
    }
    if (initialValues.counter !== "") {
      order.push("counter");
    }
    if (initialValues.durationYear !== "" || initialValues.timeAndDate !== "") {
      order.push("duration");
    }
    if (initialValues.restrictEndTime !== "") {
      order.push("endTime");
    }
    if (initialValues.event_input[0] !== "") {
      order.push("event");
    }
    if (initialValues.restrictTimeIntervalStart !== "") {
      order.push("interval");
    }
    if (initialValues.location_input[0] !== "") {
      order.push("location");
    }
    if (initialValues.payment !== "") {
      order.push("payment");
    }
    if (initialValues.purpose_input[0] !== "") {
      order.push("purpose");
    }
    if (initialValues.role_input[0] !== "") {
      order.push("role");
    }
    if (initialValues.securityLevel_input[0] !== "") {
      order.push("securityLevel");
    }
    if (initialValues.state_input[0] !== "") {
      order.push("state");
    }
    selected_components.order = order;
    order.forEach((key) =>
      selected_components.availableComponents.forEach((dict) => {
        if (key === dict.id) {
          dict.isVisible = true;
        }
      })
    );
  }
  return initialValues;
}

/**
 * Used to recreate the selectedDistributeComponents object that controls the distribute form fields. 
 * This function is mainly used for editing a saved policy. 
 * @param {object} stateLocal local state object that contains the template policy.
 * @param {object} initialValues initial field values 
 * @param {object} selected_distribute_components initial selected distribute component values
 * @returns {object} selected distribute components with added values if stateLocal is not undefined.
 */
function recreateSelectedDistriCompFromJson(stateLocal, initialValues, selected_distribute_components) {
  if (stateLocal !== undefined) {
    initialValues = stateLocal;
    var order = [];
    if (initialValues.artifactState !== "") {
      order.push("distribute");
    }
    selected_distribute_components.order = order;
    order.forEach((key) =>
    selected_distribute_components.availableComponents.forEach((dict) => {
        if (key === dict.id) {
          dict.isVisible = true;
        }
      })
    );
  }
  return initialValues;
}

/**
 * Used to recreate the selectedPredutyComponents object that controls the preduty form fields. 
 * This function is mainly used for editing a saved policy. 
 * @param {object} stateLocal local state object that contains the template policy.
 * @param {object} initialValues initial field values 
 * @param {object} selected_preduties_components initial selected preduty component values
 * @returns {object} selected preduties components with added values if stateLocal is not undefined.
 */
function recreateSelectedPreduCompFromJson(stateLocal, initialValues, selected_preduties_components) {
  if (stateLocal !== undefined) {
    initialValues = stateLocal;
    var order = [];
    if (initialValues.preduties_modifier !== "") {
      order.push("anonymizeTransit");
    }
    if (initialValues.preduties_anomInRest !== "") {
      order.push("anonymizeInRest");
    }
    selected_preduties_components.order = order;
    order.forEach((key) =>
    selected_preduties_components.availableComponents.forEach((dict) => {
        if (key === dict.id) {
          dict.isVisible = true;
        }
      })
    );
  }
  return initialValues;
}

/**
 * Used to recreate the selectedPostdutyComponents object that controls the postdury form fields. 
 * This function is mainly used for editing a saved policy. 
 * @param {object} stateLocal local state object that contains the template policy.
 * @param {object} initialValues initial field values 
 * @param {object} selected_postduties_components initial selected postduty component values
 * @param {object} selected_delete_data_components initial selected delete data component values
 * @returns {object} selected postduty and delete data components with added values if stateLocal is not undefined.
 */
function recreateSelectedPostduCompFromJson(stateLocal, initialValues, selected_postduties_components, selected_delete_data_components) {
  if (stateLocal !== undefined) {
    initialValues = stateLocal;
    var order = [];
    if (initialValues.postduties_durationYear !== "") {
      selected_delete_data_components.postduties_duration = true
      order.push("delete");
    }
    if (initialValues.postduties_timeAndDate !== "") {
      selected_delete_data_components.postduties_timeDate = true
      order.push("delete");
    }
    if (initialValues.postduties_logLevel !== "") {
      order.push("log");
    }
    if (initialValues.postduties_notificationLevel !== "") {
      order.push("inform");
    }
    selected_postduties_components.order = order;
    order.forEach((key) =>
    selected_postduties_components.availableComponents.forEach((dict) => {
        if (key === dict.id) {
          dict.isVisible = true;
        }
      })
    );
  }
  return initialValues;
}

export { OdrlPolicy, recreateSelectedCompFromJson, recreateSelectedDistriCompFromJson, recreateSelectedPreduCompFromJson, recreateSelectedPostduCompFromJson};

//export const OdrlPolicy = JSON.parse(JSON.stringify({OdrlPolicy_template}))
