import axios from 'axios';
const BASE_URL = 'http://localhost:9090';

export default function Submit(url, values, states, setErrors, history ,e) {
  console.log(values)
  e.preventDefault();
  if (validation(values, states, setErrors)) {
    for (var key in states) {
      states[key] = false;
  }
    const isoValues = convertDateToIso(values, states)
    axios.post(BASE_URL + url, isoValues)
    .then((response) => {
      axios.post(BASE_URL + "/policy/initialTDP", response.data)
      .then((responseTDP) => {
              var dict = {
                jsonPolicy: JSON.stringify(response.data, null, 2),
                dtPolicy: responseTDP.data,
              }
      history.push({
        pathname: '/ODRLCreator',
        state: dict
      })}, (error) => {
        console.log(error);
      })    
    }, (error) => {
      console.log(error);
    })    
  }
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

function convertDateToIso(values, states) {
  var isoValues = {...values}
  isoValues.specifyBeginTime = addDateSuffix(isoValues.specifyBeginTime)
  isoValues.restrictEndTime = addDateSuffix(isoValues.restrictEndTime)
  isoValues.restrictTimeIntervalStart = addDateSuffix(isoValues.restrictTimeIntervalStart)
  isoValues.restrictTimeIntervalEnd = addDateSuffix(isoValues.restrictTimeIntervalEnd)
  isoValues.timeDate = addDateSuffix(isoValues.timeAndDate)
  return isoValues
}

function addDateSuffix(date) {
  if (date === "") {
    return ""
  }
  return date+":00Z"
}

function validation(values, states, setErrors) {
    let temp = {};
    checkHeader(temp, values)
    //temp.location = states.location ? isValidUrl(values.location) : "";
    //temp.system = states.system ? isValidUrl(values.system) : "";
    //temp.application = states.application ? isValidUrl(values.application) : "";
    //temp.connector = states.connector ? isValidUrl(values.connector) : "";
    //temp.role = states.role ? notEmpty(values.role) : "";
    //temp.state = states.state ? notEmpty(values.state) : "";
    //temp.securityLevel = states.securityLevel ? notEmpty(values.securityLevel) : "";
    //temp.event = states.event ? isValidUrl(values.event) : "";
    //temp.purpose = states.purpose ? notEmpty(values.purpose) : "";
    temp.restrictTimeIntervalStart = states.interval ? isValidDate(values.restrictTimeIntervalStart): "";
    temp.restrictTimeIntervalEnd = states.interval ? isValidDateInterval(values.restrictTimeIntervalStart, values.restrictTimeIntervalEnd): "";
    temp.payment = states.payment ? notEmpty(values.payment) : "";
    temp.price = states.payment ? isValidFloat(values.price): "";
    temp.counter = states.counter ? isValidInt(values.counter): "";

    //temp.durationDay = states.duration ? isIntOrEmpty(values.durationDay): "";
    //temp.durationHour = states.duration ? isIntOrEmpty(values.durationHour): "";
    //temp.durationMonth = states.duration ? isIntOrEmpty(values.durationMonth): "";
    //temp.durationYear = states.duration ? isIntOrEmpty(values.durationYear): "";
    temp.specifyBeginTime = states.duration ? isDateOrEmpty(values.specifyBeginTime): "";

    temp.time = states.time ? isValidInt(values.time):"";
    temp.timeUnit = states.time ? notEmpty(values.timeUnit):"";
    //temp.modifier = states.anonymizeInTransit? notEmpty(values.modifier):"";
    //temp.valueToChange = states.anonymizeInTransit? isValidUrl(values.valueToChange):"";
    //temp.fieldToChange = states.anonymizeInTransit? notEmpty(values.fieldToChange):"";
    //temp.systemDevice = states.logAccess? isValidUrl(values.systemDevice):"";
    temp.getPostduties_informedParty = states.getPostduties_informedParty? notEmpty(values.getPostduties_informedParty):"";
    temp.encoding = states.encoding? isValidUrl(values.encoding):"";
    temp.policy = states.policy? isValidUrl(values.policy):"";
    //temp.timeAndDate = states.timeDate ? isValidDate(values.timeAndDate):"";
    temp.specifyBeginTime = states.specifyBeginTime ? isValidDate(values.specifyBeginTime): "";
    //temp.logLevel = states.logLevel ? notEmpty(values.logLevel):"";
    //temp.notificationLevel = states.notificationLevel ? notEmpty(values.notificationLevel):"";
    temp.artifactState = states.artifactState ? notEmpty(values.artifactState):"";
    temp.restrictEndTime = states.endTime ? isValidDate(values.restrictEndTime): "";
    setErrors({
      ...temp,
    });
    console.log(temp)
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
    console.log(date)
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

  function isDateOrEmpty(date) {
    if (date === "") return ""
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

  function isIntOrEmpty(n) {
    if (n === "") return ""
    if (parseInt(n) < 0) {
      return "Count should be positive";
    } else if (parseFloat(n) >= 0) {
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