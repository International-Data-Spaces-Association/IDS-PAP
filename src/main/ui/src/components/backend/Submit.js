import axios from 'axios';
const BASE_URL = 'http://localhost:9090';

export default function Submit(url, values, states, setErrors, history ,e) {
  e.preventDefault();
  if (Validation(values, states, setErrors)) {
    for (var key in states) {
      states[key] = false;
  }
  console.log(values)
    axios.post(BASE_URL + url, values)
    .then((response) => {
      let policies = response.data.split('DTPOLICY:');
      var dict = {
        jsonPolicy: "",
        dtPolicy: "",
      }
      dict.jsonPolicy = policies[0];
      dict.dtPolicy = policies[1];
      history.push({
        pathname: '/ODRLCreator',
        state: dict
      })
    }, (error) => {
      console.log(error);
    })    }
};

export function jsonOdrlPolicy(url, values, setPolicy) {
    axios.post(BASE_URL + url, values, {
      headers: {
        // Overwrite Axios's automatically set Content-Type
        'Content-Type': 'application/json'
      }
    })
    .then((response) => {
      setPolicy(response.data)
    }, (error) => {
      console.log(error);
    })
};

function Validation(values, states, setErrors) {
    let temp = {};
    checkHeader(temp, values)
    temp.location = states.location ? isValidUrl(values.location) : "";
    temp.system = states.system ? isValidUrl(values.system) : "";
    temp.application = states.application ? isValidUrl(values.application) : "";
    temp.connector = states.connector ? isValidUrl(values.connector) : "";
    temp.role = states.role ? notEmpty(values.role) : "";
    temp.state = states.state ? notEmpty(values.state) : "";
    temp.securityLevel = states.securityLevel ? notEmpty(values.securityLevel) : "";
    temp.event = states.event ? isValidUrl(values.event) : "";
    temp.purpose = states.purpose ? notEmpty(values.purpose) : "";
    temp.restrictTimeIntervalStart = states.interval ? isValidDate(values.restrictTimeIntervalStart): "";
    temp.restrictTimeIntervalEnd = states.interval ? isValidDateInterval(values.restrictTimeIntervalStart, values.restrictTimeIntervalEnd): "";
    temp.payment = states.payment ? notEmpty(values.payment) : "";
    temp.price = states.payment ? isValidFloat(values.price): "";
    temp.counter = states.counter ? isValidInt(values.counter): "";
    temp.time = states.time ? isValidInt(values.time):"";
    temp.timeUnit = states.time ? notEmpty(values.timeUnit):"";
    temp.modificator = states.anonymizeInTransit? notEmpty(values.modificator):"";
    //temp.valueToChange = states.anonymizeInTransit? isValidUrl(values.valueToChange):"";
    //temp.fieldToChange = states.anonymizeInTransit? notEmpty(values.fieldToChange):"";
    temp.systemDevice = states.logAccess? isValidUrl(values.systemDevice):"";
    temp.informedParty = states.informedParty? notEmpty(values.informedParty):"";
    temp.encoding = states.encoding? isValidUrl(values.encoding):"";
    temp.policy = states.policy? isValidUrl(values.policy):"";
    temp.timeAndDate = states.timeDate ? isValidDate(values.timeAndDate):"";
    temp.specifyBeginTime = states.specifyBeginTime ? isValidDate(values.specifyBeginTime): "";
    temp.logLevel = states.logLevel ? notEmpty(values.logLevel):"";
    temp.notificationLevel = states.notificationLevel ? notEmpty(values.notificationLevel):"";
    temp.artifactState = states.artifactState ? notEmpty(values.artifactState):"";
    temp.restrictTimeDuration = states.restrictTimeDuration? notEmpty(values.restrictTimeDuration):"";
    temp.restrictTimeDurationUnit = states.restrictTimeDuration? notEmpty(values.restrictTimeDurationUnit):"";
    setErrors({
      ...temp,
    });
    return Object.values(temp).every((x) => x === "");
  };

  function checkHeader(temp, values) {
    temp.policyType = notEmpty(values.policyType)
    temp.target = isValidUrl(values.target)
    //if (values.policyType === "Agreement"|| values.policyType === "Offer") {
    //  temp.provider = isValidUrl(values.provider)
    //}
    if (values.policyType === "Agreement"|| values.policyType === "Request") {
      temp.consumer = notEmpty(values.consumer)
    }
  }

  function isValidUrl(string) {
    const error = notEmpty(string)
    if (error !== "") return error

    try {
      new URL(string);
    } catch (_) {
      return "This is not a valid URI";
    }
    return "";
  }
  function isValidDate(date) {
    const error = notEmpty(date)
    if (error !== "") return error

    try {
      var today = new Date()
      today.setHours(0,0,0,0)
      if (today <= new Date(date)) {
        return ""
      } else {
        return "The entered date is in the past"
      }
    }catch(_) {
      return "This is not a valid date"
    }
  }
  function isValidDateInterval(date1, date2) {
    const error = notEmpty(date2)
    if (error !== "") return error

    if (date1 === "") {
      return "The start date should not be empty"
    }
    if (new Date(date1) <= new Date(date2)) {
      return ""
    } else {
      return "The end date should be after the start date"
    }
  }

  function isValidFloat(price) {
    const error = notEmpty(price)
    if (error !== "") return error

    if (parseFloat(price) < 0) {
      return "Price should be positive";
    } else if (parseFloat(price) >= 0) {
      return ""
    } else {
      return "Not a valid number"
    }
  }
  function isValidInt(count) {
    const error = notEmpty(count)
    if (error !== "") return error

    if (parseInt(count) < 0) {
      return "Count should be positive";
    } else if (parseFloat(count) >= 0) {
      return ""
    } else {
      return "Not a valid number"
    }
  }
  function notEmpty(str) {
    if (str === "") {
      return "The field should not be empty"
    } else {
      return ""
    }
  }