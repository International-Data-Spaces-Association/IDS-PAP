import React, { useState } from "react";
import { Grid, MenuItem, Menu, Button } from "@material-ui/core";
import { useStyle } from "../components/Style";
import Input from "../components/controls/Input";
import ItemPicker from "../components/controls/ItemPicker";
import { useHistory } from "react-router-dom";
import {
  purpose_list,
  sale_rent_list,
  state_list,
  security_level_list,
  role_list,
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


export default function ComplexPolicyForm() {
  const selected_components = {
    location: false,
    application: false,
    connector:false,
    state: false,
    securityLevel: false,
    role: false,
    event: false,
    interval: false,
    payment: false,
    counter: false,
    purpose: false,
    duration: false,
    endTime: false,
  };

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
    setValues({ ...OdrlPolicy});
    setSelectedComponents({...selected_components});
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
              </>
            ) : null}

            {selectedComponents.application ? (
              <>
                <Grid container>
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
              </>
            ) : null}

            {selectedComponents.connector ? (
              <>
                <Grid container>
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
              </>
            ) : null}

            {selectedComponents.securityLevel ? (
              <>
                <Grid container>
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
              </>
            ) : null}

            {selectedComponents.state ? (
              <>
                <Grid container>
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
              </>
            ) : null}

            {selectedComponents.role ? (
              <>
                <Grid container>
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

            {selectedComponents.endTime ? (
              <>
                <Grid container>
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
              </>
            ) : null}

            {selectedComponents.duration ? (
              <>
                <Grid container>
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
                      removeEnteredData("durationYear", "durationMonth", "specifyBeginTime", "durationDay", "durationHour");
                      removeComponent("duration");
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

                   {!selectedComponents.application ? (
                     <MenuItem
                       onClick={() => {
                         selectedComponents.application = true;
                         setAnchorEl(null);
                       }}
                       id="application"
                     >
                       Restrict Application
                     </MenuItem>
                   ) : null}

                   {!selectedComponents.role ? (
                     <MenuItem
                       onClick={() => {
                         selectedComponents.role = true;
                         setAnchorEl(null);
                       }}
                       id="role"
                     >
                       Restrict User Role
                     </MenuItem>
                   ) : null}

                   {!selectedComponents.connector ? (
                     <MenuItem
                       onClick={() => {
                         selectedComponents.connector = true;
                         setAnchorEl(null);
                       }}
                       id="connector"
                     >
                       Restrict Connector
                     </MenuItem>
                   ) : null}

                   {!selectedComponents.securityLevel ? (
                     <MenuItem
                       onClick={() => {
                         selectedComponents.securityLevel = true;
                         setAnchorEl(null);
                       }}
                       id="securityLevel"
                     >
                       Restrict Security Level
                     </MenuItem>
                   ) : null}

                   {!selectedComponents.state ? (
                     <MenuItem
                       onClick={() => {
                         selectedComponents.state = true;
                         setAnchorEl(null);
                       }}
                       id="state"
                     >
                       Restrict State
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

                    {!selectedComponents.duration ? (
                      <MenuItem
                        onClick={() => {
                          selectedComponents.duration = true;
                          setAnchorEl(null);
                        }}
                        id="duration"
                      >
                        Restrict Duration
                      </MenuItem>
                    ) : null}

                    {!selectedComponents.endTime ? (
                      <MenuItem
                        onClick={() => {
                          selectedComponents.endTime = true;
                          setAnchorEl(null);
                        }}
                        id="endTime"
                      >
                        Restrict End Time
                      </MenuItem>
                    ) : null}

                    <MenuItem
                      onClick={() => {
                        selectedComponents.location = true;
                        selectedComponents.application = true;
                        selectedComponents.connector = true;
                        selectedComponents.securityLevel = true;
                        selectedComponents.state = true;
                        selectedComponents.role = true;
                        selectedComponents.event = true;
                        selectedComponents.interval = true;
                        selectedComponents.payment = true;
                        selectedComponents.counter = true;
                        selectedComponents.purpose = true;
                        selectedComponents.duration = true;
                        selectedComponents.endTime = true;

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
                  color="secondary"
                  className={classes.saveBtn}
                  type="submit"
                  id="Save"
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
