export const time_units = [
  { id: "", value: "" },
  { id: "Hours", value: "HOURS" },
  { id: "Days", value: "DAYS" },
  { id: "Months", value: "MONTHS" },
  { id: "Years", value: "YEARS" },
];

export const purpose_list = [
  { id: "", value: "" },
  { id: "Marketing", value: "http://example.com/ids/purpose/Marketing" },
  {
    id: "Risk Management",
    value: "http://example.com/ids/purpose/Risk_Management",
  },
];
export const sale_rent_list = [
  { id: "", value: "" },
  { id: "Rent", value: "http://dbpedia.org/page/Rent" },
  { id: "Sale", value: "http://dbpedia.org/page/Sale" },
];

export const modifier_list = [
  { id: "", value: "" },
  {
    id: "Replace modification method",
    value: "idsc:REPLACE",
  },
  {
    id: "Delete modification method",
    value: "idsc:DELETE",
  },
];

export const policy_types = [
  { id: "Agreement", value: "Agreement" },
  { id: "Offer", value: "Offer" },
  { id: "Request", value: "Request" },
];

export const application_list = [
  { id: "", value: "" },
  { id: "http://example.com/ids/application/data_app", value: "http://example.com/ids/application/data_app" },
];

export const connector_list = [
  { id: "", value: "" },
  { id: "http://example.com/ids/connector/connector1", value: "http://example.com/ids/connector/connector1" },
];

export const event_list = [
  { id: "", value: "" },
  { id: "http://example.com/ids/event/exhibition", value: "http://example.com/ids/event/exhibition" },
];

export const role_list = [
  { id: "Admin", value: "idsc:ADMIN" },
  { id: "Developer", value: "idsc:DEVELOPER" },
  { id: "End User", value: "idsc:END_USER" },
];

export const security_level_list = [
  { id: "Base", value: "idsc:BASE_SECURITY_PROFILE" },
  { id: "Trust", value: "idsc:TRUST_SECURITY_PROFILE" },
  { id: "Trust Plus", value: "idsc:TRUST_PLUS_SECURITY_PROFILE" },
];

export const log_level_list = [
  { id: "ON_DENY", value: "idsc:ON_DENY" },
  { id: "ON_ALLOW", value: "idsc:ON_ALLOW" },
  { id: "ON_DUTY_EXERCISED", value: "idsc:ON_DUTY_EXERCISED" },
  { id: "ON_ACTION_OPERATED", value: "idsc:ON_ACTION_OPERATED" },
];

export const state_list = [
  { id: "Contract not Terminated", value: "CONTRACTNOTTERMINATED" },
  { id: "Firewall Activated", value: "FIREWALLACTIVATED" },
];

export const artifact_state_list = [
  { id: "ANONYMIZED", value: "idsc:ANONYMIZED" },
  { id: "PSEUDONYMIZED", value: "idsc:PSEUDONYMIZED" },
  { id: "ENCRYPTED", value: "idsc:ENCRYPTED" },
  { id: "COMBINED", value: "idsc:COMBINED" },
];

export const data_consumers = [
  { id: "", value: "" },
  { id: "Consumer Party", value: "http://example.com/ids/party/consumer-party" },
];

export const operator_list = [
{ id: "", value: "" },
{id: "IN", value: "IN"},
];
