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
} from "../components/controls/InitialFieldListValues";
import Form from "../components/controls/Form";
import IdentifyPolicy from "../components/controls/IdentifyPolicy";
import { OdrlPolicy } from "../components/backend/OdrlPolicy";
import Submit from "../components/backend/Submit";
import Remove from "../components/controls/Remove";
import Date from "../components/controls/Date";
import { time_units } from "../components/controls/InitialFieldListValues";
const selected_components = {
  location: false,
  system: false,
  purpose: false,
  event: false,
  interval: false,
  payment: false,
};

export default function ProvideAccess() {
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
      system: false,
      purpose: false,
      event: false,
      interval: false,
      payment: false,
    });
    values.location = "";
    values.system = "";
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
        <Grid container spacing={3}>
          <IdentifyPolicy
            values={values}
            handleInputChange={handleInputChange}
            errors={errors}
          />

          {selectedComponents.location ? (
            <>
              <Grid item xs={11}>
                <Input
                  name="location"
                  label="Location"
                  value={values.location}
                  placeholder="e.g. http://ontologi.es/place/DE"
                  onChange={handleInputChange}
                  error={errors.location}
                />
              </Grid>
              <Remove onClick={resetStates} />
            </>
          ) : null}

          {selectedComponents.system ? (
            <>
              <Grid item xs={11}>
                <Input
                  name="system"
                  label="System"
                  value={values.system}
                  placeholder="e.g. http://ontologi.es/place/DE"
                  onChange={handleInputChange}
                  error={errors.system}
                />
              </Grid>
              <Remove onClick={resetStates} />
            </>
          ) : null}

          {selectedComponents.purpose ? (
            <>
              <Grid item xs={11}>
                <ItemPicker
                  name="purpose"
                  label="Restrict the usage of your data to specific applications*"
                  defaultValue="Marketing"
                  ItemList={purpose_list}
                  onChange={handleInputChange}
                  error={errors.purpose}
                />
              </Grid>
              <Remove onClick={resetStates} />
            </>
          ) : null}

          {selectedComponents.event ? (
            <>
              <Grid item xs={11}>
                <Input
                  name="event"
                  label="Event*"
                  value={values.event}
                  placeholder="e.g. http://ontologi.es/place/DE"
                  onChange={handleInputChange}
                  error={errors.event}
                />
              </Grid>
              <Remove onClick={resetStates} />
            </>
          ) : null}

          {selectedComponents.restrictTimeDuration ? (
            <>
              <Grid item xs={11}>
                <Date
                  name="specifyBeginTime"
                  label="Begin Time (Optional)"
                  value={values.specifyBeginTime}
                  onChange={handleInputChange}
                  error={errors.specifyBeginTime}
                />
              </Grid>
              <Grid item xs={11} sm={5}>
                <Input
                  name="restrictTimeDuration"
                  label="Duration Value (Optional)"
                  value={values.restrictTimeDuration}
                  placeholder="e.g. 10"
                  onChange={handleInputChange}
                  error={errors.restrictTimeDuration}
                />
              </Grid>
              <Grid item xs={11} sm={1} />
              <Grid item xs={11} sm={5}>
                <ItemPicker
                  name="restrictTimeDurationUnit"
                  label="Unit"
                  defaultValue=""
                  ItemList={time_units}
                  onChange={handleInputChange}
                  error={errors.restrictTimeDurationUnit}
                />
              </Grid>
              <Grid item xs={1}>
                <Remove onClick={resetStates} />
              </Grid>
            </>
          ) : null}

          {selectedComponents.interval ? (
            <>
              <Grid item xs={11} sm={5}>
                <Date
                  name="restrictTimeIntervalStart"
                  label="Start Time*"
                  value={values.restrictTimeIntervalStart}
                  onChange={handleInputChange}
                  error={errors.restrictTimeIntervalStart}
                />
              </Grid>
              <Grid item xs={11} sm={1} />
              <Grid item xs={11} sm={5}>
                <Date
                  name="restrictTimeIntervalEnd"
                  label="End Time*"
                  value={values.restrictTimeIntervalEnd}
                  onChange={handleInputChange}
                  error={errors.restrictTimeIntervalEnd}
                />
              </Grid>
              <Grid item xs={1}>
                <Remove onClick={resetStates} />
              </Grid>
            </>
          ) : null}

          {selectedComponents.payment ? (
            <>
              <Grid item xs={11} sm={5}>
                <Input
                  name="price"
                  label="Payment (Euro)*"
                  value={values.price}
                  placeholder="150"
                  onChange={handleInputChange}
                  error={errors.price}
                />
              </Grid>
              <Grid item xs={11} sm={1} />
              <Grid item xs={11} sm={5}>
                <ItemPicker
                  name="payment"
                  label="For Sale or Rend?*"
                  defaultValue="Rent"
                  ItemList={sale_rent_list}
                  onChange={handleInputChange}
                  error={errors.payment}
                />
              </Grid>
              <Remove onClick={resetStates} />
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
                <MenuItem onClick={handleSelectedClose} id="system">
                  System
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
            >
              Save
            </Button>
          </Grid>
        </Grid>
      </Form>
    </div>
  );
}
