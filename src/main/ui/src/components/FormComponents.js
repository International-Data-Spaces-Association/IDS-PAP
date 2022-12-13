/**
 * @file This contains the basic form component for the complex policy page and provide access page
 * @author Tom Kollmer 
 */

import React from "react";
import { Grid } from "@material-ui/core";
import Input from "../components/controls/Input";
import ItemPicker from "../components/controls/ItemPicker";
import {
  purpose_list,
  sale_rent_list,
  state_list,
  security_level_list,
  role_list,
} from "../components/controls/InitialFieldListValues";
import Date from "../components/controls/Date";
import Remove from "../components/controls/Remove";
import Title from "../components/controls/Title";
import MultiSelectInputField from "./controls/MultiselectInputField";

/**
 * The basic form component used by create complex policy page and provide access page
 * @param {object} valueHook access to the user input
 * @param {object} errors contains all error messages
 * @param {object} selectedComponents contains all selected components
 * @param {func} removeComponent is called to remove components
 * @param {func} removeEnteredData is called to remove entered data
 * @returns component
 */
export default function FormComponents(props) {
  const {
    valueHook,
    errors,
    selectedComponents,
    removeComponent,
    removeEnteredData
  } = props;
  
  const components = selectedComponents.order.map((c) => {
    switch (c) {
      case "location":
        return () => (
          <Grid container key={"location"}>
            <Title label="Restrict Location" />
            <MultiSelectInputField
              name="location"
              placeholder="e.g. https://ontologi.es/place/DE"
              valueHook={valueHook}
              errors={errors}
            />
            <Remove
              onClick={() => {
                removeComponent("location");
                removeEnteredData(["location_input", "location_op"]);
              }}
            />
          </Grid>
        );
      case "application":
        return () => (
          <Grid container key={"application"}>
            <Title label="Restrict Application" />
            <MultiSelectInputField
              name="application"
              placeholder="e.g. http://example.com/ids/application/smart-app"
              valueHook={valueHook}
              errors={errors}
            />
            <Remove
              onClick={() => {
                removeComponent("application");
                removeEnteredData(["application_input", "application_op"]);
              }}
            />
          </Grid>
        );
      case "connector":
        return () => (
          <Grid container key={"connector"}>
            <Title label="Restrict Connector" />
            <MultiSelectInputField
              name="connector"
              placeholder="e.g. http://example.com/ids/connector/connector1"
              valueHook={valueHook}
              errors={errors}
            />
            {}
            <Remove
              onClick={() => {
                removeComponent("connector");
                removeEnteredData(["connector_input", "connector_op"]);
              }}
            />
          </Grid>
        );
      case "securityLevel":
        return () => (
          <Grid container key={"securityLevel"}>
            <Title label="Restrict Security Level" />
            <MultiSelectInputField
              name="securityLevel"
              placeholder=""
              inputType={"itempicker"}
              itemList={security_level_list}
              valueHook={valueHook}
              errors={errors}
            />
            {}
            <Remove
              onClick={() => {
                removeComponent("securityLevel");
                removeEnteredData(["securityLevel_input", "securityLevel_op"]);
              }}
            />
          </Grid>
        );
      case "state":
        return () => (
          <Grid container key={"state"}>
            <Title label="Restrict State" />
            <MultiSelectInputField
              name="state"
              placeholder=""
              inputType={"itempicker"}
              itemList={state_list}
              valueHook={valueHook}
              errors={errors}
            />
            {}
            <Remove
              onClick={() => {
                removeComponent("state");
                removeEnteredData(["state_input", "state_op"]);
              }}
            />
          </Grid>
        );
      case "role":
        return () => (
          <Grid container key={"role"}>
            <Title label="Restrict User Role" />
            <MultiSelectInputField
              name="role"
              placeholder=""
              inputType={"itempicker"}
              itemList={role_list}
              valueHook={valueHook}
              errors={errors}
            />

            {}
            <Remove
              onClick={() => {
                removeComponent("role");
                removeEnteredData(["role_input", "role_op"]);
              }}
            />
          </Grid>
        );
      case "purpose":
        return () => (
          <Grid container key={"purpose"}>
            <Title
              label="Restrict Purpose"
              subtitle="Any certified application in the market place uses the data for a specified purpose. \n You can restrict the usage of your data to specific applications by choosing your intended purpose from the list below*:"
            />
            <MultiSelectInputField
              name="purpose"
              placeholder=""
              inputType={"itempicker"}
              itemList={purpose_list}
              valueHook={valueHook}
              errors={errors}
            />
            <Remove
              onClick={() => {
                removeComponent("purpose");
                removeEnteredData(["purpose_input", "purpose_op"]);
              }}
            />
          </Grid>
        );
      case "event":
        return () => (
          <Grid container key={"event"}>
            <Title label="Restrict Event" />
            <MultiSelectInputField
              name="event"
              placeholder="e.g. http://example.com/ids/event/exhibition"
              valueHook={valueHook}
              errors={errors}
            />
            <Remove
              onClick={() => {
                removeComponent("event");
                removeEnteredData(["event_input", "event_op"]);
              }}
            />
          </Grid>
        );
      case "interval":
        return () => (
          <Grid container key={"interval"}>
            <Title label="Restrict Time Interval" />
            <Date
              name="restrictStartTime"
              label="Start Time*"
              valueHook={valueHook}
              errors={errors}
              sm={11}
              md={3}
            />
            <Grid item sm={1} />
            <Date
              name="restrictEndTime"
              label="End Time*"
              valueHook={valueHook}
              errors={errors}
              sm={11}
              md={3}
            />
            <Grid item md={4} />
            <Remove
              onClick={() => {
                removeEnteredData([
                  "restrictEndTime",
                  "restrictStartTime"
                ]);
                removeComponent("interval");
              }}
            />
          </Grid>
        );
      case "payment":
        return () => (
          <Grid container key={"payment"}>
            <Title label="Restrict Payment" />
            <Input
              name="price"
              label="Payment (Euro)*"
              placeholder="e.g. 10"
              valueHook={valueHook}
              errors={errors}
              sm={11}
              md={3}
            />
            <Grid item sm={1} />
            <ItemPicker
              name="payment"
              label="For Sale or Rent*"
              defaultValue=""
              ItemList={sale_rent_list}
              valueHook={valueHook}
              errors={errors}
              sm={11}
              md={3}
            />
            <Grid item md={4} />
            <Remove
              onClick={() => {
                removeEnteredData(["price", "payment"]);
                removeComponent("payment");
              }}
            />
          </Grid>
        );

      case "counter":
        return () => (
          <Grid container key={"counter"}>
            <Title label="Restrict Number of Usage" />
            <Input
              name="counter"
              placeholder="e.g. 10"
              valueHook={valueHook}
              errors={errors}
            />
            <Remove
              onClick={() => {
                removeComponent("counter");
                removeEnteredData(["counter"]);
              }}
            />
          </Grid>
        );

      case "endTime":
        return () => (
          <Grid container key={"endTime"}>
            <Title label="Restrict End Time" />
            <Grid item sm={1} />
            <Date
              name="restrictEndTime"
              label="End Time"
              valueHook={valueHook}
              errors={errors.restrictEndTime}
              sm={11}
              md={3}
            />
            <Grid item md={8} />
            <Remove
              onClick={() => {
                removeEnteredData(["restrictEndTime"]);
                removeComponent("endTime");
              }}
            />
          </Grid>
        );

      case "duration":
        return () => (
          <Grid container key={"duration"}>
            <Title label="Restrict Time Duration" />
            <Grid item sm={1} />
            <Input
              name="durationYear"
              label="Year (Optional)"
              placeholder="e.g. 3"
              valueHook={valueHook}
              errors={errors}
              sm={11}
              md={3}
            />
            <Grid item sm={1} />
            <Input
              name="durationMonth"
              label="Month (Optional)"
              placeholder="e.g. 3"
              valueHook={valueHook}
              errors={errors}
              sm={11}
              md={3}
            />
            <Grid item sm={1} />
            <Input
              name="durationDay"
              label="Day (Optional)"
              placeholder="e.g. 3"
              valueHook={valueHook}
              errors={errors}
              sm={11}
              md={3}
            />
            <Grid item sm={1} />
            <Input
              name="durationHour"
              label="Hour (Optional)"
              placeholder="e.g. 3"
              valueHook={valueHook}
              errors={errors}
              sm={11}
              md={3}
            />
            <Grid item sm={1} />
            <Date
              name="specifyBeginTime"
              label="Begin Time (Optional)"
              valueHook={valueHook}
              errors={errors}
              sm={11}
              md={3}
            />
            <Grid item md={4} />
            <Remove
              onClick={() => {
                removeEnteredData([
                  "durationYear",
                  "durationMonth",
                  "specifyBeginTime",
                  "durationDay",
                  "durationHour"
                ]);
                removeComponent("duration");
              }}
            />
          </Grid>
        );
      default:
        return null;
    }
  });
  return <>{components.map((c) => c())}</>;
}
