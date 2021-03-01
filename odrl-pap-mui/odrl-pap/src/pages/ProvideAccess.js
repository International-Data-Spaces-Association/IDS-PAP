import React, { useState } from "react";
import { Grid, Menu, MenuItem, Button } from "@material-ui/core";
import PageHeader from "../components/PageHeader";
import { useStyle } from "../components/Style";
import LockOpenIcon from "@material-ui/icons/LockOpen";
import Input from "../components/controls/Input";
import ItemPicker from "../components/controls/ItemPicker";
import { useHistory } from "react-router-dom";
import {
  purpose_list,
  sale_rent_list,
  application_list,
  event_list,
} from "../components/controls/InitialFieldListValues";
import Form from "../components/controls/Form";
import IdentifyPolicy from "../components/controls/IdentifyPolicy";
import { OdrlPolicy } from "../components/backend/OdrlPolicy";
import Submit from "../components/backend/Submit";
import Remove from "../components/controls/Remove";
import Date from "../components/controls/Date";
import Title from "../components/controls/Title";
import { time_units } from "../components/controls/InitialFieldListValues";

const selected_components = {
  location: false,
  /*system: false,*/
  application: false,
  purpose: false,
  event: false,
  interval: false,
  payment: false,
};

export default function ProvideAccess() {
  document.title = 'Provide Access'

  const classes = useStyle();
  const [values, setValues] = useState(OdrlPolicy);
  const [errors, setErrors] = useState({});
  const history = useHistory();
  const [selectedComponents, setSelectedComponents] = useState(
    selected_components
  );
  const [anchorEl, setAnchorEl] = useState(null);

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };
  const resetStates = () => {
    setSelectedComponents({
      location: false,
      application: false,
      purpose: false,
      event: false,
      interval: false,
      payment: false,
    });
    values.location = "";
    values.application = "";
    values.purpose = "";
    values.event = "";
    values.restrictTimeIntervalStart = "";
    values.restrictTimeIntervalEnd = "";
    values.payment = "";
    values.price = "";
    values.specifyBeginTime = "";
    values.restrictTimeDuration = "";
    values.restrictTimeDurationUnit = "";
  };
  const handleSelectedClose = (e) => {
    selectedComponents[e.target.id] = true;
    setAnchorEl(null);
  };
  const handleInputChange = (e) => {
    setValues({ ...values, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    Submit(
      "/policy/ProvideAccess",
      values,
      selectedComponents,
      setErrors,
      history,
      e
    );
  };

  return (
    <div className={classes.page}>


      <Form onSubmit={handleSubmit}>
        <PageHeader
          title="This policy gives permission to a specified IDS data consumer to use your data."
          icon={<LockOpenIcon />}
        />
        <Grid container spacing={1}>
          <IdentifyPolicy
            values={values}
            handleInputChange={handleInputChange}
            errors={errors}
          />

          {selectedComponents.location ? (
            <>
              <Grid container>
                <Title label="Restrict Location" />
                <Input
                  name="location"
                  value={values.location}
                  placeholder="e.g. http://ontologi.es/place/DE"
                  onChange={handleInputChange}
                  error={errors.location}
                />
                <Remove onClick={resetStates} />
              </Grid>
            </>
          ) : null}

          {selectedComponents.application ? (
              <>
                <Grid container>
                  <Title label="Restrict Application" />
                  <ItemPicker
                    name="application"
                    defaultValue=""
                    ItemList={application_list}
                    onChange={handleInputChange}
                    error={errors.application}
                  />
                  <Remove onClick={resetStates} />
                </Grid>
              </>
            ) : null}

          {selectedComponents.purpose ? (
            <>
              <Grid container>
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
                <Remove onClick={resetStates} />
              </Grid>
            </>
          ) : null}

          {selectedComponents.event ? (
            <>
              <Grid container>
                <Title label="Restrict Event" />

                <ItemPicker
                  name="event"
                  defaultValue=""
                  ItemList={event_list}
                  onChange={handleInputChange}
                  error={errors.event}
                />
                <Remove onClick={resetStates} />
              </Grid>
            </>
          ) : null}

          {selectedComponents.restrictTimeDuration ? (
            <>
              <Grid container>
                <Title label="Restrict Time Duration" />
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
                  name="restrictTimeDuration"
                  label="Duration Value (Optional)"
                  value={values.restrictTimeDuration}
                  placeholder="e.g. 10"
                  onChange={handleInputChange}
                  error={errors.restrictTimeDuration}
                  sm={11}
                  md={3}
                />
                <Grid item sm={1} />
                <ItemPicker
                  name="restrictTimeDurationUnit"
                  label="Unit"
                  defaultValue=""
                  ItemList={time_units}
                  onChange={handleInputChange}
                  error={errors.restrictTimeDurationUnit}
                  sm={11}
                  md={3}
                />
                <Remove onClick={resetStates} />
              </Grid>
            </>
          ) : null}

          {selectedComponents.interval ? (
            <>
              <Grid container>
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
                <Remove onClick={resetStates} />
              </Grid>
            </>
          ) : null}

          {selectedComponents.payment ? (
            <>
              <Grid container>
                <Title label="Restrict Time Interval" />
                <Input
                  name="price"
                  label="Payment (Euro)*"
                  value={values.price}
                  placeholder="150"
                  onChange={handleInputChange}
                  error={errors.price}
                  xs={11}
                  sm={5}
                />
                <Grid item sm={1} />
                <ItemPicker
                  name="payment"
                  label="For Sale or Rend?*"
                  defaultValue="Rent"
                  ItemList={sale_rent_list}
                  onChange={handleInputChange}
                  error={errors.payment}
                  xs={11}
                  sm={5}
                />
                <Remove onClick={resetStates} />
              </Grid>
            </>
          ) : null}

          {Object.values(selectedComponents).every((x) => x === false) ? (
            <Grid item xs={12} container justify="center">
              <Grid item xs={2}>
                <Button
                  color="primary"
                  aria-controls="simple-menu"
                  aria-haspopup="true"
                  onClick={handleClick}
                  id="Add Component"
                  className={classes.addBtn}
                >
                  Add Component
                </Button>
              </Grid>
              <Menu
                id="simple-menu"
                anchorEl={anchorEl}
                keepMounted
                open={Boolean(anchorEl)}
                onClose={handleClose}
              >
                <MenuItem onClick={handleSelectedClose} id="location">
                  Location
                </MenuItem>
                <MenuItem onClick={handleSelectedClose} id="application">
                  Application
                </MenuItem>
                <MenuItem onClick={handleSelectedClose} id="purpose">
                  Purpose
                </MenuItem>
                <MenuItem onClick={handleSelectedClose} id="event">
                  Event
                </MenuItem>
                <MenuItem onClick={handleSelectedClose} id="interval">
                  Interval
                </MenuItem>
                <MenuItem onClick={handleSelectedClose} id="payment">
                  Payment
                </MenuItem>
                <MenuItem
                  onClick={handleSelectedClose}
                  id="restrictTimeDuration"
                >
                  Specify a begin time
                </MenuItem>
              </Menu>
            </Grid>
          ) : null}

          <Grid item xs={2}>
            <Button
              variant="contained"
              color="primary"
              className={classes.saveBtn}
              type="submit"
              id="Save"
            >
              Save
            </Button>
          </Grid>
        </Grid>
      </Form>
    </div>
  );
}
