/**
 * @file This file contains the add restrictions components
 * @author Tom Kollmer 
 */
import React, { useState } from "react";
import {
  Grid,
  MenuItem,
  Menu,
  Button,
  Typography,
} from "@material-ui/core";
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
import MultiSelectInputField from "../components/controls/MultiselectInputField";
import MenuItems from "../components/controls/MenuItems";

/**
 * Components for the add restrictions component
 * @component
 * @param {object} selectedComponents contains all selected components
 * @param {object} valueHook access to the user input
 * @param {object} errors contains all error messages
 * @param {func} removeComponent is called to remove components
 * @param {func} removeEnteredData is called to remove entered data
 * @param {object} classes contains the css definitions
 * @returns component
 */
export default function AddRestrictions(props) {
  const {
    selectedComponents,
    valueHook,
    errors,
    removeComponent,
    removeEnteredData,
    classes,
  } = props;

  const [anchorEl, setAnchorEl] = useState(null);
  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const addAll = () => {
    const dict = selectedComponents.availableComponents;
    dict.forEach(function (item) {
      if (!item.isVisible) {
        item.isVisible = true;
        selectedComponents.order.push(item.id);
      }
    });
  };
  const components = selectedComponents.order.map((c) => {
    switch (c) {
      case "location":
        return () => (
          <Grid container key={"location"}>
            <Title label="Restrict Location" />
            <MultiSelectInputField
              name="location"
              valueHook={valueHook}
              placeholder="e.g. https://ontologi.es/place/DE"
              errors={errors}
            />
            <Remove
              onClick={() => {
                removeComponent("restrictions", "location");
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
              valueHook={valueHook}
              placeholder="e.g. http://example.com/ids/application/smart-app"             
              errors={errors}
            />
            <Remove
              onClick={() => {
                removeComponent("restrictions", "application");
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
              valueHook={valueHook}
              placeholder="e.g. http://example.com/ids/connector/connector1"             
              errors={errors}
            />
            {}
            <Remove
              onClick={() => {
                removeComponent("restrictions", "connector");
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
              valueHook={valueHook}
              placeholder=""             
              errors={errors}
              inputType={"itempicker"}
              itemList={security_level_list}
            />
            {}
            <Remove
              onClick={() => {
                removeComponent("restrictions", "securityLevel");
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
              valueHook={valueHook}
              placeholder=""             
              errors={errors}
              inputType={"itempicker"}
              itemList={state_list}
            />
            {}
            <Remove
              onClick={() => {
                removeComponent("restrictions", "state");
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
              valueHook={valueHook}
              placeholder=""             
              errors={errors}
              inputType={"itempicker"}
              itemList={role_list}
            />

            {}
            <Remove
              onClick={() => {
                removeComponent("restrictions", "role");
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
              valueHook={valueHook}
              placeholder=""            
              errors={errors}
              inputType={"itempicker"}
              itemList={purpose_list}
            />
            <Remove
              onClick={() => {
                removeComponent("restrictions", "purpose");
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
              valueHook={valueHook}
              placeholder="e.g. http://example.com/ids/event/exhibition"            
              errors={errors}
            />
            <Remove
              onClick={() => {
                removeComponent("restrictions", "event");
                removeEnteredData(["event_input", "event_op"]);
              }}
            />
          </Grid>
        );
      case "interval":
        return () => (
          <Grid container key={"interval"}>
            <Title label="Restrict Time Interval" />
            <Grid container xs={11} spacing={2}>
              <Date
                name="restrictTimeIntervalStart"
                label="Start Time*"
                valueHook={valueHook}
                errors={errors}
                sm={11}
                md={4}
              />
              <Date
                name="restrictTimeIntervalEnd"
                label="End Time*"
                valueHook={valueHook}
                errors={errors}
                sm={11}
                md={4}
              />
            </Grid>
            <Remove
              onClick={() => {
                removeEnteredData([
                  "restrictTimeIntervalEnd",
                  "restrictTimeIntervalStart",
                ]);
                removeComponent("restrictions", "interval");
              }}
            />
          </Grid>
        );
      case "payment":
        return () => (
          <Grid container key={"payment"}>
            <Title label="Restrict Payment" />
            <Grid container spacing={2} xs={11}>
              <Input
                name="price"
                label="Payment (Euro)*"
                placeholder="e.g. 10"
                valueHook={valueHook}
                errors={errors}
                sm={11}
                md={3}
              />
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
            </Grid>
            <Remove
              onClick={() => {
                removeEnteredData(["price", "payment"]);
                removeComponent("restrictions", "payment");
              }}
            />
          </Grid>
        );

      case "counter":
        return () => (
          <Grid container key={"counter"}>
            <Title label="Restrict Number of Usage" />
            <Grid container xs={11} spacing={2}>
              <Input
                name="counter"
                placeholder="e.g. 10"
                valueHook={valueHook}
                errors={errors}
                xs={3}
                sm={3}
                md={3}
              />
            </Grid>
            <Remove
              onClick={() => {
                removeComponent("restrictions", "counter");
                removeEnteredData(["counter"]);
              }}
            />
          </Grid>
        );

        case "startTime":
          return () => (
            <Grid container key={"startTime"}>
              <Title label="Restrict Start Time" />
              <Grid container xs={11} spacing={2}>
                <Date
                  name="restrictStartTime"
                  label="Start Time"
                  valueHook={valueHook}
                  errors={errors}
                  sm={11}
                  md={5}
                />
              </Grid>
              <Remove
                onClick={() => {
                  removeEnteredData(["restrictStartTime"]);
                  removeComponent("restrictions", "startTime");
                }}
              />
            </Grid>
          );

      case "endTime":
        return () => (
          <Grid container key={"endTime"}>
            <Title label="Restrict End Time" />
            <Grid container xs={11} spacing={2}>
              <Date
                name="restrictEndTime"
                label="End Time"
                valueHook={valueHook}
                errors={errors}
                sm={11}
                md={5}
              />
            </Grid>
            <Remove
              onClick={() => {
                removeEnteredData(["restrictEndTime"]);
                removeComponent("restrictions", "endTime");
              }}
            />
          </Grid>
        );

      case "duration":
        return () => (
          <Grid container key={"duration"}>
            <Title label="Restrict Time Duration" />
            <Grid container sm={11} spacing={2}>
              <Input
                name="durationYear"
                label="Year (Optional)"
                placeholder="e.g. 2021"
                valueHook={valueHook}
                errors={errors}
                sm={11}
                md={3}
              />
              <Input
                name="durationMonth"
                label="Month (Optional)"
                placeholder="e.g. 01"
                valueHook={valueHook}
                errors={errors}
                sm={11}
                md={3}
              />

              <Input
                name="durationDay"
                label="Day (Optional)"
                placeholder="e.g. 01"
                valueHook={valueHook}
                errors={errors}
                sm={11}
                md={3}
              />
              <Input
                name="durationHour"
                label="Hour (Optional)"
                placeholder="e.g. 12"
                valueHook={valueHook}
                errors={errors}
                sm={11}
                md={3}
              />
              <Date
                name="specifyBeginTime"
                label="Begin Time (Optional)"
                valueHook={valueHook}
                errors={errors}
                sm={11}
                md={5}
              />
            </Grid>
            <Remove
              onClick={() => {
                removeEnteredData([
                  "durationYear",
                  "durationMonth",
                  "specifyBeginTime",
                  "durationDay",
                  "durationHour",
                ]);
                removeComponent("restrictions", "duration");
              }}
            />
          </Grid>
        );
      default:
        return null;
    }
  });
  return (
    <>
      {selectedComponents.order.length > 0 ? (
        <>
          <Typography
            variant="h5"
            component="div"
            id="subTitle1"
            className={classes.subtitle}
          >
            Restrictions
          </Typography>

          {components.map((c) => c())}

          {selectedComponents.order.length <= 12 ? (
            <Grid item xs={12} container justifyContent="center">
              <Grid item xs={5}>
                <Button
                  color="primary"
                  aria-controls="simple-menu"
                  aria-haspopup="true"
                  onClick={handleClick}
                  className={classes.addBtn}
                  id="Add Component"
                >
                  Add Restriction
                </Button>
              </Grid>
              <Menu
                id="simple-menu"
                anchorEl={anchorEl}
                keepMounted
                open={Boolean(anchorEl)}
                onClose={() => setAnchorEl(null)}
              >
                <MenuItems
                  selectedComponents={selectedComponents}
                  setAnchorEl={setAnchorEl}
                />

                <MenuItem
                  onClick={() => {
                    addAll();
                    setAnchorEl(null);
                  }}
                  id="all"
                >
                  All
                </MenuItem>
              </Menu>
            </Grid>
          ) : null}
        </>
      ) : (
        <Grid item xs={12} container justifyContent="center">
          <Grid item xs={5}>
            <Button
              color="primary"
              aria-controls="simple-menu"
              aria-haspopup="true"
              onClick={handleClick}
              className={classes.addBtn}
              id="Add Component"
            >
              Add Restriction
            </Button>
          </Grid>
          <Menu
            id="simple-menu"
            anchorEl={anchorEl}
            keepMounted
            open={Boolean(anchorEl)}
            onClose={() => setAnchorEl(null)}
          >
            <MenuItems
              selectedComponents={selectedComponents}
              setAnchorEl={setAnchorEl}
            />

            <MenuItem
              onClick={() => {
                addAll();
                setAnchorEl(null);
              }}
              id="all"
            >
              All
            </MenuItem>
          </Menu>
        </Grid>
      )}
    </>
  );
}
