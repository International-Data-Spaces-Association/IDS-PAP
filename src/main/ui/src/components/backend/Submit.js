import axios from "axios";
import negotiateTestResponse from "../negotiateExample.json";
import axiosRetry from "axios-retry";

export default function Submit(url, values, states, setErrors, history, e) {
  console.log(values);
  //e.preventDefault();
  if (validation(values, states, setErrors)) {
    for (var key in states) {
      states[key] = false;
    }
    const isoValues = convertDateToIso(values, states);
    if (values.is_template) {
      axios.post("/policy/template", isoValues).then();
    } else {
      axios.post(url, isoValues).then(
        (response) => {
          axios
            .post("/policy/initialTechnologyDependentPolicy", response.data)
            .then(
              (responseTDP) => {
                console.log(response.data);
                var dict = {
                  jsonPolicy: JSON.stringify(response.data, null, 2),
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
const retryWrapper = (axios, options) => {
  const max_time = options.retry_time;
  const retry_status_code = options.retry_status_code;
  let counter = 0;
  axios.interceptors.response.use(null, (error) => {
    /** @type {import("axios").AxiosRequestConfig} */
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

export async function negotiatePolicyGetResponse(url, uuid) {
  let policy = await axios.get(`${url}/${uuid}`);
  return policy;
}

function convertDateToIso(values, states) {
  var isoValues = { ...values };
  isoValues.specifyBeginTime = addDateSuffix(isoValues.specifyBeginTime);
  isoValues.restrictEndTime = addDateSuffix(isoValues.restrictEndTime);
  isoValues.restrictStartTime = addDateSuffix(isoValues.restrictStartTime);
  isoValues.restrictTimeIntervalStart = addDateSuffix(
    isoValues.restrictTimeIntervalStart
  );
  isoValues.restrictTimeIntervalEnd = addDateSuffix(
    isoValues.restrictTimeIntervalEnd
  );
  isoValues.timeDate = addDateSuffix(isoValues.timeAndDate);
  return isoValues;
}

function addDateSuffix(date) {
  if (date === "") {
    return "";
  }
  return date + ":00Z";
}

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
        error_list.postduties_durationDay = isValidInt(
          values.postduties_durationDay
        );
        error_list.postduties_durationHour = isValidInt(
          values.postduties_durationHour
        );
        error_list.postduties_durationMonth = isValidInt(
          values.postduties_durationMonth
        );
        error_list.postduties_durationYear = isValidInt(
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
  console.log(error_list);
  setErrors({
    ...error_list,
  });
  return Object.values(error_list).every((x) => x === "");
}

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
    error_list.restrictTimeIntervalStart = isValidDate(
      values.restrictTimeIntervalStart
    );
    error_list.restrictTimeIntervalEnd = isValidDateInterval(
      values.restrictTimeIntervalStart,
      values.restrictTimeIntervalEnd
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
    error_list.postduties_durationDay = isValidInt(
      values.postduties_durationDay
    );
    error_list.postduties_durationHour = isValidInt(
      values.postduties_durationHour
    );
    error_list.postduties_durationMonth = isValidInt(
      values.postduties_durationMonth
    );
    error_list.postduties_durationYear = isValidInt(
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
function isNotEmpty(str) {
  if (str === "") {
    return "The field should not be empty";
  } else {
    return "";
  }
}
