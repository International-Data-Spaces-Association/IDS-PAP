import React from "react";
import { Grid} from "@material-ui/core";
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

export default function FormComponents(props) {
  const {
    selectedComponents,
    values,
    errors,
    handleInputChange,
    removeComponent,
    removeEnteredData,
  } = props;

  const components = selectedComponents.order.map((c) => {
    switch (c) {
      case "location":
        return () => (
          <Grid container key={"location"}>
            <Title label="Restrict Location" />
            <Input
              name="location"
              value={values.location}
              placeholder="e.g. https://ontologi.es/place/DE"
              onChange={handleInputChange}
              error={errors.location}
            />
            <Remove
              onClick={() => {
                removeComponent("location");
                removeEnteredData("location");
              }}
            />
          </Grid>
        );
      case "application":
        return () => (
          <Grid container key={"application"}>
            <Title label="Restrict Application" />
            <Input
              name="application"
              value={values.application}
              placeholder="e.g.  http://example.com/ids-app/data-app"
              onChange={handleInputChange}
              error={errors.application}
            />
            {/*
              <Input
                name="application"
                value={values.application}
                placeholder="e.g. https://wikidata.org/wiki/Q136218 (ZIP)"
                onChange={handleInputChange}
                error={errors.application}
              />
              */}
            <Remove
              onClick={() => {
                removeComponent("application");
                removeEnteredData("application");
              }}
            />
          </Grid>
        );
      case "connector":
        return () => (
          <Grid container key={"connector"}>
            <Title label="Restrict Connector" />
            <Input
              name="connector"
              value={values.connector}
              placeholder="e.g. http://example.com/ids-connector/connector1"
              onChange={handleInputChange}
              error={errors.connector}
            />
            {}
            <Remove
              onClick={() => {
                removeComponent("connector");
                removeEnteredData("connector");
              }}
            />
          </Grid>
        );
      case "securityLevel":
        return () => (
          <Grid container key={"securityLevel"}>
            <Title label="Restrict Security Level" />
            <ItemPicker
              name="securityLevel"
              defaultValue=""
              ItemList={security_level_list}
              onChange={handleInputChange}
              error={errors.securityLevel}
            />
            {}
            <Remove
              onClick={() => {
                removeComponent("securityLevel");
                removeEnteredData("securityLevel");
              }}
            />
          </Grid>
        );
      case "state":
        return () => (
          <Grid container key={"state"}>
            <Title label="Restrict State" />
            <ItemPicker
              name="state"
              defaultValue=""
              ItemList={state_list}
              onChange={handleInputChange}
              error={errors.state}
            />
            {}
            <Remove
              onClick={() => {
                removeComponent("state");
                removeEnteredData("state");
              }}
            />
          </Grid>
        );
      case "role":
        return () => (
          <Grid container key={"role"}>
            <Title label="Restrict User Role" />
            <ItemPicker
              name="role"
              defaultValue=""
              ItemList={role_list}
              onChange={handleInputChange}
              error={errors.role}
            />
            {}
            <Remove
              onClick={() => {
                removeComponent("role");
                removeEnteredData("role");
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
            <ItemPicker
              name="purpose"
              defaultValue=""
              ItemList={purpose_list}
              onChange={handleInputChange}
              error={errors.purpose}
            />
            <Remove
              onClick={() => {
                removeComponent("purpose");
                removeEnteredData("purpose");
              }}
            />
          </Grid>
        );
      case "event":
        return () => (
          <Grid container key={"event"}>
            <Title label="Restrict Event" />

            <Input
              name="event"
              value={values.event}
              placeholder="e.g. http://example.com/ids-event:exhibition"
              onChange={handleInputChange}
              error={errors.event}
            />
            {/*
                                      <Input
                                        name="event"
                                        value={values.event}
                                        placeholder="e.g. https://wikidata.org/wiki/Q136218 (ZIP)"
                                        onChange={handleInputChange}
                                        error={errors.event}
                                      />
                                      */}

            <Remove
              onClick={() => {
                removeComponent("event");
                removeEnteredData("event");
              }}
            />
          </Grid>
        );
      case "interval":
        return () => (
          <Grid container key={"interval"}>
            <Title label="Restrict Time Interval" />
            <Date
              name="restrictTimeIntervalStart"
              label="Start Time*"
              value={values.restrictTimeIntervalStart}
              onChange={handleInputChange}
              error={errors.restrictTimeIntervalStart}
              xs={11}
              sm={5}
            />
            <Grid item sm={1} />
            <Date
              name="restrictTimeIntervalEnd"
              label="End Time*"
              value={values.restrictTimeIntervalEnd}
              onChange={handleInputChange}
              error={errors.restrictTimeIntervalEnd}
              xs={11}
              sm={5}
            />
            <Remove
              onClick={() => {
                removeEnteredData(
                  "restrictTimeIntervalEnd",
                  "restrictTimeIntervalStart"
                );
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
              value={values.price}
              placeholder="e.g. 10"
              onChange={handleInputChange}
              error={errors.price}
              xs={11}
              sm={5}
            />
            <Grid item sm={1} />
            <ItemPicker
              name="payment"
              label="For Sale or Rent*"
              defaultValue=""
              ItemList={sale_rent_list}
              onChange={handleInputChange}
              error={errors.payment}
              xs={11}
              sm={5}
            />
            <Remove
              onClick={() => {
                removeEnteredData("price", "payment");
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
              value={values.counter}
              placeholder="e.g. 10"
              onChange={handleInputChange}
              error={errors.counter}
            />
            <Remove
              onClick={() => {
                removeComponent("counter");
                removeEnteredData("counter");
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
              value={values.restrictEndTime}
              onChange={handleInputChange}
              error={errors.restrictEndTime}
              sm={11}
              md={3}
            />
            <Remove
              onClick={() => {
                removeEnteredData("restrictEndTime");
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
              value={values.durationYear}
              placeholder="e.g. 3"
              onChange={handleInputChange}
              error={errors.durationYear}
              sm={11}
              md={3}
            />
            <Grid item sm={1} />
            <Input
              name="durationMonth"
              label="Month (Optional)"
              value={values.durationMonth}
              placeholder="e.g. 3"
              onChange={handleInputChange}
              error={errors.durationMonth}
              sm={11}
              md={3}
            />
            <Grid item sm={1} />
            <Date
              name="specifyBeginTime"
              label="Begin Time (Optional)"
              value={values.specifyBeginTime}
              onChange={handleInputChange}
              error={errors.specifyBeginTime}
              sm={11}
              md={3}
            />
            <Grid item sm={1} />
            <Input
              name="durationDay"
              label="Day (Optional)"
              value={values.durationDay}
              placeholder="e.g. 3"
              onChange={handleInputChange}
              error={errors.durationDay}
              sm={11}
              md={3}
            />
            <Grid item sm={1} />
            <Input
              name="durationHour"
              label="Hour (Optional)"
              value={values.durationHour}
              placeholder="e.g. 3"
              onChange={handleInputChange}
              error={errors.durationHour}
              sm={11}
              md={3}
            />
            <Remove
              onClick={() => {
                removeEnteredData(
                  "durationYear",
                  "durationMonth",
                  "specifyBeginTime",
                  "durationDay",
                  "durationHour"
                );
                removeComponent("duration");
              }}
            />
          </Grid>
        );
        default:
            return null
    }
  });
  return <>{components.map((c) => c())}</>;
}
