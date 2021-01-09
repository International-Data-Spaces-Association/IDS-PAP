import React, { useState } from "react";
import { Grid, MenuItem, Menu, Button } from "@material-ui/core";
import { useStyle } from "../components/Style";
import Input from "../components/controls/Input";
import ItemPicker from "../components/controls/ItemPicker";
import { useHistory } from "react-router-dom";
import {
  purpose_list,
  sale_rent_list,
} from "../components/controls/InitialFieldListValues";
import Date from "../components/controls/Date";
import PageHeader from "../components/PageHeader";
import PostAddIcon from "@material-ui/icons/PostAdd";
import Form from "../components/controls/Form";
import IdentifyPolicy from "../components/controls/IdentifyPolicy";
import { OdrlPolicy } from "../components/backend/OdrlPolicy";
import Submit from "../components/backend/Submit";
import Remove from "../components/controls/Remove";

const selected_components = {
  location: false,
  system: false,
  event: false,
  interval: false,
  payment: false,
  purpose: false,
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

  const removeComponent = (id) => {
    setSelectedComponents({ ...selectedComponents, [id]: false });
  };

  const removeEnteredData = (id1, id2) => {
    setValues({ ...values, [id1]:"", [id2]:"" });
  };

  return (
    <>
      <div className={classes.page}>
        <Form onSubmit={handleSubmit}>
          <PageHeader
            title="This policy gives permission to a specified IDS data consumer to use your data."
            icon={<PostAddIcon />}
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
                    label="Location*"
                    value={values.location}
                    placeholder="e.g. https://wikidata.org/wiki/Q136218 (ZIP)"
                    onChange={handleInputChange}
                    error={errors.location}
                  />
                </Grid>
                <Grid item xs={1}>
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
                <Grid item xs={11}>
                  <Input
                    name="system"
                    label="System*"
                    value={values.system}
                    placeholder="e.g. https://wikidata.org/wiki/Q136218 (ZIP)"
                    onChange={handleInputChange}
                    error={errors.system}
                  />
                </Grid>
                <Grid item xs={1}>
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
                <Grid item xs={11}>
                  <ItemPicker
                    name="purpose"
                    label="Restrict the usage of your data to specific applications*"
                    defaultValue=""
                    ItemList={purpose_list}
                    onChange={handleInputChange}
                    error={errors.purpose}
                  />
                </Grid>
                <Grid item xs={1}>
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
                <Grid item xs={11}>
                  <Input
                    name="event"
                    label="Event*"
                    value={values.event}
                    placeholder="e.g. https://wikidata.org/wiki/Q136218 (ZIP)"
                    onChange={handleInputChange}
                    error={errors.event}
                  />
                </Grid>
                <Grid item xs={1}>
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
                <Grid item xs={11} sm={5}>
                  <Date
                    name="restrictTimeIntervalStart"
                    label="Start Time*"
                    value={values.restrictTimeIntervalStart}
                    onChange={handleInputChange}
                    error={errors.restrictTimeIntervalStart}
                  />
                </Grid>
                <Grid item xs={11} sm={1}/>
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
                  <Remove
                    onClick={() => {
                      removeEnteredData("restrictTimeIntervalEnd","restrictTimeIntervalStart");
                      removeComponent("interval");
                    }}
                  />
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
                    placeholder="e.g. 10"
                    onChange={handleInputChange}
                    error={errors.price}
                  />
                </Grid>
                <Grid item xs={11} sm={1}/>
                <Grid item xs={11} sm={5}>
                  <ItemPicker
                    name="payment"
                    label="For Sale or Rent*"
                    defaultValue=""
                    ItemList={sale_rent_list}
                    onChange={handleInputChange}
                    error={errors.payment}
                  />
                </Grid>
                <Grid item xs={1}>
                  <Remove
                    onClick={() => {
                      removeEnteredData("price", "payment");
                      removeComponent("payment");
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
                  </Menu>
                </Grid>
              </>
            ) : null}
            <Grid item xs={12}>
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
          </Grid>
        </Form>
      </div>
    </>
  );
}
