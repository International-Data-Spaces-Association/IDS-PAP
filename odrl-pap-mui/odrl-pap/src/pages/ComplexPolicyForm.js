import React, { useState } from "react";
import { Grid, MenuItem, Menu, Button } from "@material-ui/core";
import { useStyle } from "../components/Style";
import Input from "../components/controls/Input";
import ItemPicker from "../components/controls/ItemPicker";
import { useHistory } from "react-router-dom";
import {
  event_list,
  purpose_list,
  sale_rent_list,
  system_list,
  time_units,
} from "../components/controls/InitialFieldListValues";
import Date from "../components/controls/Date";
import PageHeader from "../components/PageHeader";
import PostAddIcon from "@material-ui/icons/PostAdd";
import Form from "../components/controls/Form";
import IdentifyPolicy from "../components/controls/IdentifyPolicy";
import { OdrlPolicy } from "../components/backend/OdrlPolicy";
import Submit from "../components/backend/Submit";
import Remove from "../components/controls/Remove";
import Title from "../components/controls/Title";
import DeleteIcon from "@material-ui/icons/Delete";
const selected_components = {
  location: false,
  system: false,
  event: false,
  interval: false,
  payment: false,
  counter: false,
  purpose: false,
  restrictTimeDuration: false,
  specifyBeginTime: false,
};

export default function ComplexPolicyForm() {
  const classes = useStyle();
  const [values, setValues] = useState(OdrlPolicy);
  const [errors, setErrors] = useState({});
  const history = useHistory();
  const [selectedComponents, setSelectedComponents] = useState(
    selected_components
  );
  const [anchorEl, setAnchorEl] = useState(null);

  const handleInputChange = (e) => {
    setValues({ ...values, [e.target.name]: e.target.value });
  };

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleSubmit = (e) => {
    Submit(
      "/policy/ComplexPolicyForm",
      values,
      selectedComponents,
      setErrors,
      history,
      e
    );
  };

  const resetStates = () => {
    setValues({...OdrlPolicy, ["consumer"]: values["consumer"]});
    setSelectedComponents({  location: false,
      system: false,
      event: false,
      interval: false,
      payment: false,
      counter: false,
      purpose: false,
      restrictTimeDuration: false,
      specifyBeginTime: false,});
    
  };

  const removeComponent = (id) => {
    setSelectedComponents({ ...selectedComponents, [id]: false });
  };

  const removeEnteredData = (id1, id2) => {
    setValues({ ...values, [id1]: "", [id2]: "" });
  };

  return (
    <>
      <div className={classes.page}>
        <Form onSubmit={handleSubmit}>
          <PageHeader
            title="This policy gives permission to a specified IDS data consumer to use your data."
            icon={<PostAddIcon />}
          />
          <Grid container>
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
                    placeholder="e.g. https://wikidata.org/wiki/Q136218 (ZIP)"
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
              </>
            ) : null}

            {selectedComponents.system ? (
              <>
                <Grid container>
                  <Title label="Restrict System" />
                  <ItemPicker
                    name="system"
                    defaultValue=""
                    ItemList={system_list}
                    onChange={handleInputChange}
                    error={errors.system}
                  />
                  {/*
                  <Input
                    name="system"
                    value={values.system}
                    placeholder="e.g. https://wikidata.org/wiki/Q136218 (ZIP)"
                    onChange={handleInputChange}
                    error={errors.system}
                  />
                  */}
                  <Remove
                    onClick={() => {
                      removeComponent("system");
                      removeEnteredData("system");
                    }}
                  />
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
                  <Remove
                    onClick={() => {
                      removeComponent("purpose");
                      removeEnteredData("purpose");
                    }}
                  />
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
              </>
            ) : null}

            {selectedComponents.counter ? (
              <>
                <Grid container>
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
              </>
            ) : null}

            {selectedComponents.specifyBeginTime ? (
              <>
                <Grid container>
                  <Title label="Specify a begin time" />
                  <Date
                    name="specifyBeginTime"
                    value={values.specifyBeginTime}
                    onChange={handleInputChange}
                    error={errors.specifyBeginTime}
                  />
                  <Remove
                    onClick={() => {
                      removeEnteredData("specifyBeginTime");
                      removeComponent("specifyBeginTime");
                    }}
                  />
                </Grid>
              </>
            ) : null}

            {selectedComponents.restrictTimeDuration ? (
              <>
                <Grid container>
                  <Title label="Restrict Time Duration" />
                  <Input
                    name="restrictTimeDuration"
                    label="Duration Value*"
                    value={values.restrictTimeDuration}
                    placeholder="e.g. 10"
                    onChange={handleInputChange}
                    error={errors.restrictTimeDuration}
                    xs={11}
                    sm={5}
                  />
                  <Grid item sm={1} />

                  <ItemPicker
                    name="restrictTimeDurationUnit"
                    label="Unit*"
                    defaultValue=""
                    ItemList={time_units}
                    onChange={handleInputChange}
                    error={errors.restrictTimeDurationUnit}
                    xs={11}
                    sm={5}
                  />

                  <Remove
                    onClick={() => {
                      removeEnteredData(
                        "restrictTimeDuration",
                        "restrictTimeDurationUnit"
                      );
                      removeComponent("restrictTimeDuration");
                    }}
                  />
                </Grid>
              </>
            ) : null}

            {Object.values(selectedComponents).some((x) => x === false) ? (
              <>
                <Grid item xs={12} container justify="center">
                  <Grid item xs={2}>
                    <Button
                      color="primary"
                      aria-controls="simple-menu"
                      aria-haspopup="true"
                      onClick={handleClick}
                      className={classes.addBtn}
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
                    {!selectedComponents.location ? (
                      <MenuItem
                        onClick={() => {
                          selectedComponents.location = true;
                          setAnchorEl(null);
                        }}
                        id="location"
                      >
                        Restrict Location
                      </MenuItem>
                    ) : null}

                    {!selectedComponents.system ? (
                      <MenuItem
                        onClick={() => {
                          selectedComponents.system = true;
                          setAnchorEl(null);
                        }}
                        id="system"
                      >
                        Restrict System
                      </MenuItem>
                    ) : null}
                    {!selectedComponents.purpose ? (
                      <MenuItem
                        onClick={() => {
                          selectedComponents.purpose = true;
                          setAnchorEl(null);
                        }}
                        id="purpose"
                      >
                        Restrict Purpose
                      </MenuItem>
                    ) : null}
                    {!selectedComponents.event ? (
                      <MenuItem
                        onClick={() => {
                          selectedComponents.event = true;
                          setAnchorEl(null);
                        }}
                        id="event"
                      >
                        Restrict Event
                      </MenuItem>
                    ) : null}
                    {!selectedComponents.interval ? (
                      <MenuItem
                        onClick={() => {
                          selectedComponents.interval = true;
                          setAnchorEl(null);
                        }}
                        id="time"
                      >
                        Restrict Time Interval
                      </MenuItem>
                    ) : null}

                    {!selectedComponents.payment ? (
                      <MenuItem
                        onClick={() => {
                          selectedComponents.payment = true;
                          setAnchorEl(null);
                        }}
                        id="payment"
                      >
                        Restrict Payment
                      </MenuItem>
                    ) : null}

                    {!selectedComponents.counter ? (
                      <MenuItem
                        onClick={() => {
                          selectedComponents.counter = true;
                          setAnchorEl(null);
                        }}
                        id="umberOfUsage"
                      >
                        Restrict Number of Usage
                      </MenuItem>
                    ) : null}

                    {!selectedComponents.restrictTimeDuration ? (
                      <MenuItem
                        onClick={() => {
                          selectedComponents.restrictTimeDuration = true;
                          setAnchorEl(null);
                        }}
                        id="restrictTimeDuration"
                      >
                        Restrict Time Duration
                      </MenuItem>
                    ) : null}

                    {!selectedComponents.specifyBeginTime ? (
                      <MenuItem
                        onClick={() => {
                          selectedComponents.specifyBeginTime = true;
                          setAnchorEl(null);
                        }}
                        id="restrictTimeDuration"
                      >
                        Specify a begin time
                      </MenuItem>
                    ) : null}
                    <MenuItem
                      onClick={() => {
                        selectedComponents.location = true;
                        selectedComponents.system = true;
                        selectedComponents.event = true;
                        selectedComponents.interval = true;
                        selectedComponents.payment = true;
                        selectedComponents.counter = true;
                        selectedComponents.purpose = true;
                        selectedComponents.restrictTimeDuration = true;
                        selectedComponents.specifyBeginTime = true;
                        
                        setAnchorEl(null);
                      }}
                      id="all"
                    >
                      All
                    </MenuItem>
                  </Menu>
                </Grid>
              </>
            ) : null}

            <Grid container>
              <Grid item xs={2} xm={1}>
                <Button
                  variant="contained"
                  color="primary"
                  className={classes.saveBtn}
                  type="submit"
                >
                  Save
                </Button>
              </Grid>
              <Grid item xs={7} xm={9} />

              <Grid item xs={2} xm={1}>
                <Button
                  variant="contained"
                  color="secondary"
                  className={classes.saveBtn}
                  onClick={resetStates}
                >
                  <DeleteIcon />
                </Button>
              </Grid>
            </Grid>
          </Grid>
        </Form>
      </div>
    </>
  );
}
