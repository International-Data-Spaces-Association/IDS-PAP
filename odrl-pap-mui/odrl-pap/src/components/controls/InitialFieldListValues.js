export const time_units = [
  { id: "", value: "" },
  { id: "Hours", value: "HOURS" },
  { id: "Days", value: "DAYS" },
  { id: "Months", value: "MONTH" },
  { id: "Years", value: "YEARS" },
];

export const purpose_list = [
  { id: "", value: "" },
  { id: "Marketing", value: "http://example.com/ids-purpose:Marketing" },
  {
    id: "Risk Management",
    value: "Risk http://example.com/ids-purpose:Risk_Management",
  },
];
export const sale_rent_list = [
  { id: "", value: "" },
  { id: "Rent", value: "http://dbpedia.org/page/Rent" },
  { id: "Sale", value: "http://dbpedia.org/page/Sale" },
];

export const modificator_list = [
  { id: "", value: "" },
  {
    id: "Replace modification method",
    value: "http://example.com/anonymize/replace",
  },
  {
    id: "Delete modification method",
    value: "http://example.com/anonymize/delete",
  },
];

export const policy_types = [
  { id: "Agreement", value: "Agreement" },
  { id: "Offer", value: "Offer" },
  { id: "Request", value: "Request" },
];

export const application_list = [
  { id: "", value: "" },
  { id: "http://example.com/ids-app/data_app", value: "http://example.com/ids-app/data-app" },
];

export const event_list = [
  { id: "", value: "" },
  { id: "http://example.com/ids-event:exhibition", value: "http://example.com/ids-event:exhibition" },
];

export const log_level_list = [
  { id: "ON_DENY", value: "ON_DENY" },
  { id: "ON_ALLOW", value: "ON_ALLOW" },
  { id: "ON_DUTY_EXERCISED", value: "ON_DUTY_EXERCISED" },
  { id: "ON_ACTION_OPERATED", value: "ON_ACTION_OPERATED" },
];

export const artifact_state_list = [
  { id: "ANONYMIZED", value: "ANONYMIZED" },
  { id: "PSEUDONYMIZED", value: "PSEUDONYMIZED" },
  { id: "ENCRYPTED", value: "ENCRYPTED" },
  { id: "COMBINED", value: "COMBINED" },
];

export const data_consumers = [
  { id: "", value: "" },
  { id: "Consumer Party", value: "http://example.com/party/consumer-party" },
];
