/**
 * @file This file contains all key value pairs that used by itempickers
 * @author Tom Kollmer 
 */

/**
 * Different time units and how they should be translated for the backend
 */
export const time_units = [
  { id: "", value: "" },
  { id: "Hours", value: "HOURS" },
  { id: "Days", value: "DAYS" },
  { id: "Months", value: "MONTHS" },
  { id: "Years", value: "YEARS" },
];

/**
  * Different purpose types and how they should be translated for the backend
 */
export const purpose_list = [
  { id: "", value: "" },
  { id: "Marketing", value: "http://example.com/ids/purpose/Marketing" },
  {
    id: "Risk Management",
    value: "http://example.com/ids/purpose/Risk_Management",
  },
];

/**
  * Different sale and rent types and how they should be translated for the backend
 */
export const sale_rent_list = [
  { id: "", value: "" },
  { id: "Rent", value: "http://dbpedia.org/page/Rent" },
  { id: "Sale", value: "http://dbpedia.org/page/Sale" },
];

/**
 * Different modifier types and how they should be translated for the backend
 */
export const modifier_list = [
  { id: "", value: "" },
  {
    id: "Replace modification method",
    value: "idsc:REPLACE",
  },
  {
    id: "Drop modification method",
    value: "idsc:DROP",
  },
];

/**
 * Different policy types and how they should be translated for the backend
 */
 export const policy_types = [
  { id: "Agreement", value: "Agreement" },
  { id: "Offer", value: "Offer" },
  { id: "Request", value: "Request" },
];

export const ids_policy_types = [
  { id: "Agreement", value: "ids:ContractAgreement" },
  { id: "Offer", value: "ids:ContractOffer" },
  { id: "Request", value: "ids:ContractRequest" },
];

export const odrl_policy_types = [
  { id: "Agreement", value: "Agreement" },
  { id: "Offer", value: "Offer" },
  { id: "Request", value: "Request" },
];

/**
 * Different application types and how they should be translated for the backend
 */
export const application_list = [
  { id: "", value: "" },
  { id: "http://example.com/ids/application/data_app", value: "http://example.com/ids/application/data_app" },
];

/**
 * Different connector types and how they should be translated for the backend
 */
export const connector_list = [
  { id: "", value: "" },
  { id: "http://example.com/ids/connector/connector1", value: "http://example.com/ids/connector/connector1" },
];

/**
 * Different event types and how they should be translated for the backend
 */
export const event_list = [
  { id: "", value: "" },
  { id: "http://example.com/ids/event/exhibition", value: "http://example.com/ids/event/exhibition" },
];

/**
 * Different role types and how they should be translated for the backend
 */
export const role_list = [
  { id: "Admin", value: "idsc:ADMIN" },
  { id: "Developer", value: "idsc:DEVELOPER" },
  { id: "End User", value: "idsc:END_USER" },
];

/**
 * Different security types and how they should be translated for the backend
 */
export const security_level_list = [
  { id: "Base", value: "idsc:BASE_SECURITY_PROFILE" },
  { id: "Trust", value: "idsc:TRUST_SECURITY_PROFILE" },
  { id: "Trust Plus", value: "idsc:TRUST_PLUS_SECURITY_PROFILE" },
];

/**
 * Different log level types and how they should be translated for the backend
 */
export const log_level_list = [
  { id: "ON_DENY", value: "idsc:ON_DENY" },
  { id: "ON_ALLOW", value: "idsc:ON_ALLOW" },
  { id: "ON_DUTY_EXERCISED", value: "idsc:ON_DUTY_EXERCISED" },
  { id: "ON_ACTION_OPERATED", value: "idsc:ON_ACTION_OPERATED" },
];

/**
 * Different state types and how they should be translated for the backend
 */
export const state_list = [
  { id: "Contract not Terminated", value: "CONTRACTNOTTERMINATED" },
  { id: "Firewall Activated", value: "FIREWALLACTIVATED" },
];

/**
 * Different artifact types and how they should be translated for the backend
 */
export const artifact_state_list = [
  { id: "ANONYMIZED", value: "idsc:ANONYMIZED" },
  { id: "PSEUDONYMIZED", value: "idsc:PSEUDONYMIZED" },
  { id: "ENCRYPTED", value: "idsc:ENCRYPTED" },
  { id: "COMBINED", value: "idsc:COMBINED" },
];

/**
 * Different data consumer types and how they should be translated for the backend
 */
export const data_consumers = [
  { id: "", value: "" },
  { id: "Consumer Party", value: "http://example.com/ids/party/consumer-party" },
];


export const operator_list = [
{ id: "", value: "", odrl: "", ids:  ""},
{id: "IS ANY OF", value: "IS ANY OF",  odrl: "isAnyOf", ids:  "IN"},
{id: "IS PART OF", value: "IS PART OF",  odrl: "isPartOf", ids:  "IN"},
{id: "IS ALL OF", value: "IS ALL OF", odrl: "isAllOf", ids:  "EQUALS"},
]