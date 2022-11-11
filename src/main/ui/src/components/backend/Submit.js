/**
 * @file Manages the connection with the backend when new policies are created
 * @author Tom Kollmer
 */

import { Language } from "@material-ui/icons";
import axios from "axios";
import negotiateTestResponse from "../negotiateExample.json";
import { ids_policy_types, odrl_policy_types } from "../controls/InitialFieldListValues";
/**
 * Main function that is called when a new policy is created. First the inputs are validated then
 * transformed and finally send to the backend.
 * @param {string} url that is used to send the data to the backend
 * @param {object} values that contains all user input
 * @param {object} states that determine all selected components
 * @param {func} setErrors function to show error messages on the UI
 * @param {object} history that controls the currently shown page
 * @param {object} e sender to determine the origin [Not used at the moment]
 */
export default function Submit(url, values, states, setErrors, history, e) {
  const replacer = (key, value) =>
    value instanceof Object && !(value instanceof Array)
      ? Object.keys(value)
          .sort()
          .reduce((sorted, key) => {
            sorted[key] = value[key];
            return sorted;
          }, {})
      : value;

  if (validation(values, states, setErrors)) {
    for (var key in states) {
      states[key] = false;
    }
    var isoValues = convertDateToIso(values, states);
    //setPolicyType(isoValues)
    console.log(isoValues)
    if (values.is_template) {
      axios.post("/policy/template", isoValues).then();
    } else {
      axios.post(url, isoValues).then(
        (response) => {
          axios
            .post("/policy/initialTechnologyDependentPolicy", response.data)
            .then(
              (responseTDP) => {
                var dict = {
                  jsonPolicy: JSON.stringify(response.data, replacer, 2),
                  dtPolicy: responseTDP.data,
                };
                history.push({
                  pathname: "/ODRLCreator",
                  state: dict,
                });
              },
              (error) => {
                console.log(error);
              }
            );
        },
        (error) => {
          console.log(error);
        }
      );
    }
  }
}

/**
 * Connects with the backend to translate the user input to a valid policy.
 * @param {string} url that is used to send the data to the backend
 * @param {object} values that contains all user input
 * @param {func} setPolicy function to set policy
 */
export function jsonOdrlPolicy(url, values, setPolicy) {
  axios
    .post(url, values, {
      headers: {
        // Overwrite Axios's automatically set Content-Type
        "Content-Type": "application/json",
      },
    })
    .then(
      (response) => {
        setPolicy(response.data);
      },
      (error) => {
        console.log(error);
      }
    );
}

/**
 * [Not used!] Tries to access the negotiated policy and retries if the policy is still stuck in the negotiation process.
 * @param {object} axios object
 * @param {object} options json object with retry_time and retry_status_code
 */
const retryWrapper = (axios, options) => {
  const max_time = options.retry_time;
  const retry_status_code = options.retry_status_code;
  let counter = 0;
  axios.interceptors.response.use(null, (error) => {
    const config = error.config;
    if (counter < max_time && error.response.status === retry_status_code) {
      counter++;
      return new Promise((resolve) => {
        resolve(axios(config));
      });
    }
    // ===== this is mock final one is a successful request, you could delete one in usage.
    if (counter === max_time && error.response.status === retry_status_code) {
      config.url = "https://api.ipify.org?format=json";
      return new Promise((resolve) => {
        resolve(axios(config));
      });
    }
    return Promise.reject(error);
  });
};

/**
 * Tries to get the uuid of the negotiated policy after it is exported.
 * @param {string} url that is used to send the data to the backend
 * @returns {string} the uuid of the negotiated policy
 */
export async function negotiatePolicyGetUUID(url) {
  let uuid = await axios.post(
    `${url}/contract-negotiation`,
    negotiateTestResponse,
    {
      headers: {
        "Content-Type": "application/json",
      },
    }
  );
  return uuid.data;
}

/**
 * Tries to get the negotiated policy with the given uuid
 * @param {string} url that is used to send the data to the backend
 * @param {string} uuid of the negotiated policy
 * @returns {object} negotiated policy
 */
export async function negotiatePolicyGetResponse(url, uuid) {
  let policy = await axios.get(`${url}/${uuid}`);
  return policy;
}


/**
 * Sets policy type according to selected language
 * @param {object} values object with all the user input
 */
 function setPolicyType(values) {
  if (values.language === "IDS") {
    for (const type of ids_policy_types) { 
      if (type.id === values.policyType) {
        values["policyType"] = type.value
      }    
    }
  } else if (values.language === "ODRL"){
    for (const type of odrl_policy_types) { 
      if (type.id === values.policyType) {
        values["policyType"] = type.value
      }    
    }  }
 }
 
/**
 * Converts the date to iso standard
 * @param {object} values object with all the user input
 * @param {object} states object with all the selected states
 * @returns {string} the date in iso format
 */
function convertDateToIso(values, states) {
  var isoValues = { ...values };
  isoValues.specifyBeginTime = addDateSuffix(isoValues.specifyBeginTime);
  isoValues.restrictEndTime = addDateSuffix(isoValues.restrictEndTime);
  isoValues.restrictStartTime = addDateSuffix(isoValues.restrictStartTime);
  isoValues.timeDate = addDateSuffix(isoValues.timeAndDate);
  return isoValues;
}

/**
 * Adds a suffix to the date string
 * @param {String} date that should be converted
 * @returns {string} converted date
 */
function addDateSuffix(date) {
  if (date === "" || date === undefined) {
    return "";
  }
  return date + ":00Z";
}

/**
 * Used to validate the user input and checks if all requirements are satisfied.
 * @param {object} values containing the user input
 * @param {object} states that determine all selected components
 * @param {func} setErrors function to show error messages on the UI
 * @returns {boolean} true when all requirements are satisfied
 */
function validation(values, states, setErrors) {
  console.log(states, states.page);
  let error_list = {};

  checkHeader(error_list, values);
  switch (states.page) {
    case "CreatePolicy":
      checkComplexPolicyFields(values, states, error_list);
      break;

    case "ProvideAccess":
      checkComplexPolicyFields(values, states, error_list);
      break;

    case "CountAccess":
      error_list.counter = isValidInt(values.counter);
      break;

    case "DeleteDataAfter":
      if (states.postduties_duration) {
        error_list.postduties_durationDay = isIntOrEmpty(
          values.postduties_durationDay
        );
        error_list.postduties_durationHour = isIntOrEmpty(
          values.postduties_durationHour
        );
        error_list.postduties_durationMonth = isIntOrEmpty(
          values.postduties_durationMonth
        );
        error_list.postduties_durationYear = isIntOrEmpty(
          values.postduties_durationYear
        );
      }
      if (states.postduties_timeDate) {
        error_list.postduties_timeAndDate = isDateOrEmpty(
          values.postduties_timeAndDate
        );
        error_list.postduties_timeAndDate = isNotEmpty(
          values.postduties_timeAndDate
        );
      }
      break;

    case "AnonymizeInRest":
      break;

    case "AnonymizeInTransit":
      error_list["preduties_modifier"] = isNotEmpty(
        values["preduties_modifier"]
      );
      error_list["preduties_fieldToChange"] = isNotEmpty(
        values["preduties_fieldToChange"]
      );
      if (values.preduties_modifier === "idsc:REPLACE") {
        error_list["preduties_valueToChange"] = isNotEmpty(
          values["preduties_valueToChange"]
        );
      }
      break;

    case "LogAccess":
      error_list["postduties_systemDevice"] = isValidUrl(
        values["postduties_systemDevice"]
      );
      error_list["postduties_logLevel"] = isNotEmpty(
        values["postduties_logLevel"]
      );

      break;

    case "InformParty":
      error_list["postduties_notificationLevel"] = isNotEmpty(
        values["postduties_notificationLevel"]
      );
      error_list["postduties_informedParty"] = isNotEmpty(
        values["postduties_informedParty"]
      );
      break;

    case "DistributeData":
      error_list.artifactState = isNotEmpty(values["artifactState"]);
      error_list.policy = isValidUrl(values["policy"]);
      break;

    default:
      break;
  }
  error_list.odrlLanguageError = languageError(values.language, states, error_list)
  console.log(error_list);
  setErrors({
    ...error_list,
  });
  return Object.values(error_list).every((x) => x === "");
}


/**
 * Checks if the given input is comatible to the selected language
 * @param {*} language selected language
 * @param {*} states selected fields
 * @param {object} error_list that contains all error messages
 */
function languageError(language, states, error_list) {
  var languageError = ""
  var errors = ""
  // ODRL 
  if (language === "ODRL"){
    const notAllowedStatesODRL = [ ];
    for (const [key, value] of Object.entries(states)) {
      if (value && notAllowedStatesODRL.includes(key)) {
        errors += `${key}, `
        //const res = Object.keys(error_list).filter(v => v.startsWith(key));
       //res.forEach(e => 
        //  error_list[e] = "Not Allowed in ODRL"
         // );
      }
    }
    if (errors !== ""){
      languageError = `This input combination is not allowed in ODRL: [${errors}]`
    }
  }
  // IDS
  console.log("language", error_list)
  return languageError
}

/**
 * Checks the user input of the complex policy page
 * @param {object} values containing the user input
 * @param {object} states that determine all selected components
 * @param {object} error_list that contains all error messages
 */
function checkComplexPolicyFields(values, states, error_list) {
  //Restrict Application
  checkMultiInputField("application", isValidUrl, values, states, error_list);

  //Restrict Connector
  checkMultiInputField("connector", isValidUrl, values, states, error_list);

  // Restrict Number of Usage
  if (states["counter"]) {
    error_list.counter = isValidInt(values.counter);
  }

  //Restrict End Time
  if (states["endTime"]) {
       error_list.restrictEndTime = isValidDate(values.restrictEndTime);
     }

  //Restrict Start Time
  if (states["startTime"]) {
    error_list.restrictStartTime = isValidDate(values.restrictStartTime);
  }

  // Restrict Payment
  if (states.payment) {
    error_list.payment = isNotEmpty(values.payment);
    error_list.price = isValidFloat(values.price);
  }

  // Restrict Time Duration
  if (values.durationDay !== "")
    error_list.durationDay = isIntOrEmpty(values.durationDay);
  if (values.durationHour !== "")
    error_list.durationHour = isIntOrEmpty(values.durationHour);
  if (values.durationMonth !== "")
    error_list.durationMonth = isIntOrEmpty(values.durationMonth);
  if (values.durationYear !== "")
    error_list.durationYear = isIntOrEmpty(values.durationYear);
  if (values.specifyBeginTime !== "")
    error_list.specifyBeginTime = isDateOrEmpty(values.specifyBeginTime);

  // Restrict Time Interval
  if (states["interval"]) {
    error_list.restrictStartTime = isValidDate(
      values.restrictStartTime
    );
    error_list.restrictEndTime = isValidDateInterval(
      values.restrictStartTime,
      values.restrictEndTime
    );
  }

  // Restrict Event
  checkMultiInputField("event", isValidUrl, values, states, error_list);

  // Restrict Location
  checkMultiInputField("location", isValidUrl, values, states, error_list);

  // Restrict Purpose
  checkMultiInputField("purpose", isNotEmpty, values, states, error_list);

  // Restrict Role
  checkMultiInputField("role", isNotEmpty, values, states, error_list);

  // Restrict Security Level
  checkMultiInputField("securityLevel", isNotEmpty, values, states, error_list);

  // Restrict State
  checkMultiInputField("state", isNotEmpty, values, states, error_list);

  // Distribute Data
  if (states["distribute"]) {
    error_list.artifactState = isNotEmpty(values["artifactState"]);
    error_list.policy = isValidUrl(values["policy"]);
  }

  // Anonymize in Transit
  if (states["anonymizeTransit"]) {
    error_list["preduties_modifier"] = isNotEmpty(values["preduties_modifier"]);
    error_list["preduties_fieldToChange"] = isNotEmpty(
      values["preduties_fieldToChange"]
    );
    if (values.preduties_modifier === "idsc:REPLACE") {
      error_list["preduties_valueToChange"] = isNotEmpty(
        values["preduties_valueToChange"]
      );
    }
  }

  // Delete Data After
  if (states.postduties_duration) {
    error_list.postduties_durationDay = isIntOrEmpty(
      values.postduties_durationDay
    );
    error_list.postduties_durationHour = isIntOrEmpty(
      values.postduties_durationHour
    );
    error_list.postduties_durationMonth = isIntOrEmpty(
      values.postduties_durationMonth
    );
    error_list.postduties_durationYear = isIntOrEmpty(
      values.postduties_durationYear
    );
  }
  if (states.postduties_timeDate) {
    error_list.postduties_timeAndDate = isDateOrEmpty(
      values.postduties_timeAndDate
    );
    error_list.postduties_timeAndDate = isNotEmpty(
      values.postduties_timeAndDate
    );
  }

  // Log Data Usage
  if (states["log"]) {
    error_list["postduties_systemDevice"] = isValidUrl(
      values["postduties_systemDevice"]
    );
    error_list["postduties_logLevel"] = isNotEmpty(
      values["postduties_logLevel"]
    );
  }

  // Inform Party
  if (states["inform"]) {
    error_list["postduties_notificationLevel"] = isNotEmpty(
      values["postduties_notificationLevel"]
    );
    error_list["postduties_informedParty"] = isNotEmpty(
      values["postduties_informedParty"]
    );
  }
}

/**
 * Checks if the user input to the header of the policy is correct
 * @param {object} error_list that contains all error messages
 * @param {object} values containing the user input
 */
function checkHeader(error_list, values) {
  error_list.policyType = isNotEmpty(values.policyType);
  error_list.target = isValidUrl(values.target);
  if (values.policyType === "Agreement" || values.policyType === "Offer") {
    error_list.provider = isNotEmpty(values.provider);
  }
  if (values.policyType === "Agreement" || values.policyType === "Request") {
    error_list.consumer = isNotEmpty(values.consumer);
  }
}

/**
 * Checks if the input of a multi input field is valid
 * @param {string} key
 * @param {func} func
 * @param {object} values containing the user input
 * @param {object} states that determine all selected components
 * @param {object} error_list that contains all error messages
 */
function checkMultiInputField(key, func, values, states, error_list) {
  if (states[key]) {
    if (1 < values[key + "_input"].length) {
      error_list[key + "_op"] = isNotEmpty(values[key + "_op"]);
    }
    for (let id = 0; id < values[key + "_input"].length; ++id) {
      error_list[key + "_input_" + id] = func(values[key + "_input"][id]);
    }
  }
}

/**
 * Checks if the parameter is a valid url
 * @param {string} string that should be checked if it is a valid url
 * @returns {string} error message if the input is incorrect
 */
function isValidUrl(string) {
  const error = isNotEmpty(string);
  if (error !== "") return error;

  try {
    new URL(string);
  } catch (_) {
    return "This is not a valid URI";
  }
  return "";
}

/**
 * Checks if the parameter is a valid date and in the future
 * @param {string} date to be checked if it is valid
 * @returns {string} error message if the input is incorrect
 */
function isValidDate(date) {
  const error = isNotEmpty(date);
  if (error !== "") return error;

  try {
    var today = new Date();
    today.setHours(0, 0, 0, 0);
    if (today <= new Date(date)) {
      return "";
    } else {
      return "The entered date is in the past";
    }
  } catch (_) {
    return "This is not a valid date";
  }
}

/**
 *  Checks if the input is a valid date and not empty
 * @param {string} date that should be checked
 * @returns {string} error message if the input is incorrect
 */
function isDateOrEmpty(date) {
  if (date === "") return "";
  try {
    var today = new Date();
    today.setHours(0, 0, 0, 0);
    if (today <= new Date(date)) {
      return "";
    } else {
      return "The entered date is in the past";
    }
  } catch (_) {
    return "This is not a valid date";
  }
}

/**
 * Checks if both dates define a valid interval
 * @param {string} date1 start date
 * @param {string} date2 end date
 * @returns {string} error message if the input is incorrect
 */
function isValidDateInterval(date1, date2) {
  const error = isNotEmpty(date2);
  if (error !== "") return error;
  if (date1 === "") {
    return "The start date should not be empty";
  }
  if (new Date(date1) <= new Date(date2)) {
    return "";
  } else {
    return "The end date should be after the start date";
  }
}

/**
 * Checks if the input is a valid price
 * @param {number} price that should be checked
 * @returns {string} error message if the input is incorrect
 */
function isValidFloat(price) {
  const error = isNotEmpty(price);
  if (error !== "") return error;

  if (parseFloat(price) < 0) {
    return "Price should be positive";
  } else if (parseFloat(price) >= 0) {
    return "";
  } else {
    return "Not a valid number";
  }
}

/**
 * Checks if the input is empty or a valid int
 * @param {number} n that should be checked
 * @returns {string} error message if the input is incorrect
 */
function isIntOrEmpty(n) {
  if (n === "") return "";
  if (parseInt(n) < 0) {
    return "Count should be positive";
  } else if (parseFloat(n) >= 0) {
    return "";
  } else {
    return "Not a valid number";
  }
}

/**
 * Checks if the input is an int and not empty
 * @param {number} count that should be checked
 * @returns {string} error message if the input is incorrect
 */
function isValidInt(count) {
  const error = isNotEmpty(count);
  if (error !== "") return error;

  if (parseInt(count) < 0) {
    return "Count should be positive";
  } else if (parseFloat(count) >= 0) {
    return "";
  } else {
    return "Not a valid number";
  }
}

/**
 * Checks if the string is not empty
 * @param {string} str
 * @returns {string} error message if the input is incorrect
 */
function isNotEmpty(str) {
  if (str === "") {
    return "The field should not be empty";
  } else {
    return "";
  }
}
