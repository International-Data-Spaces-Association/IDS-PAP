import React, { useState } from "react";
import { Grid, Menu, MenuItem, Button } from "@material-ui/core";
import PageHeader from "../components/PageHeader";
import DeleteIcon from "@material-ui/icons/Delete";
import Input from "../components/controls/Input";
import Date from "../components/controls/Date";
import ItemPicker from "../components/controls/ItemPicker";
import { useHistory } from "react-router-dom";
import { time_units } from "../components/controls/InitialFieldListValues";
import { useStyle } from "../components/Style";
import Form from "../components/controls/Form";
import IdentifyPolicy from "../components/controls/IdentifyPolicy";
import { OdrlPolicy } from "../components/backend/OdrlPolicy";
import Submit from "../components/backend/Submit";
import Remove from "../components/controls/Remove";
import Title from "../components/controls/Title";

const selected_components = {
  time: false,
  timeDate: false,
};

export default function DeleteDataAfter() {
  const classes = useStyle();
  const [values, setValues] = useState(OdrlPolicy);
  const [errors, setErrors] = useState({});
  const history = useHistory();
  const [selectedComponents] = useState(selected_components);

  const [anchorEl, setAnchorEl] = useState(null);

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };
  const resetStates = () => {
    for (var key in selectedComponents) {
      if (selectedComponents.hasOwnProperty(key)) {
        selectedComponents[key] = false;
      }
    }
    setValues({ ...values, time: "", timeAndDate: "", timeUnit: "" });
  };
  const handleSelectedClose = (e) => {
    selectedComponents[e.target.id] = true;
    setAnchorEl(null);
  };

  const handleInputChange = (e) => {
    setValues({ ...values, [e.target.name]: e.target.value });
  };
  const handleSubmit = (e) => {
    if (Object.values(selectedComponents).some((x) => x === true)) {
      Submit(
        "/policy/deletePolicyAfterUsage",
        values,
        selectedComponents,
        setErrors,
        history,
        e
      );
    } else {
      Submit(
        "/policy/deletePolicyAfterUsagePeriod",
        values,
        selectedComponents,
        setErrors,
        history,
        e
      );
    }
  };
  return (
    <div className={classes.page}>
      <Form onSubmit={handleSubmit}>
        {Object.values(selectedComponents).every((x) => x === false) ? (
          <PageHeader
            title="The assumption is that your data is stored in a database on consumer side."
            icon={<DeleteIcon />}
          />
        ) : null}
        {selectedComponents.time ? (
          <PageHeader
            title="The assumption is that your data is stored in a database on consumer side.
                    This policy allows the Data Consumer to use your data and requests to delete it immediately after.s"
            icon={<DeleteIcon />}
          />
        ) : null}
        {selectedComponents.timeDate ? (
          <PageHeader
            title="The assumption is that your data is stored in a database on consumer side.
                    This policy requests to delete your data after a specified period of time."
            icon={<DeleteIcon />}
          />
        ) : null}
        <Grid container spacing={1}>
          <IdentifyPolicy
            values={values}
            handleInputChange={handleInputChange}
            errors={errors}
          />

          {selectedComponents.time ? (
            <>
              <Grid container>
                <Title label="Specify a time duration that the application has to wait before deleting the data" />
                <Input
                  name="time"
                  label="Duration value*"
                  value={values.time}
                  placeholder="1"
                  onChange={handleInputChange}
                  error={errors.time}
                  xs={11}
                  sm={5}
                />
                <Grid sm={1} />

                <ItemPicker
                  name="timeUnit"
                  label="Unit"
                  defaultValue=""
                  ItemList={time_units}
                  onChange={handleInputChange}
                  error={errors.timeUnit}
                  xs={11}
                  sm={5}
                />
                <Remove onClick={resetStates} />
              </Grid>
            </>
          ) : null}

          {selectedComponents.timeDate ? (
            <>
              <Grid container>
                <Title label="Specify an exact date and time to delete the data:" />
                <Date
                  name="timeAndDate"
                  label="Date and Time*"
                  defaultValue=""
                  onChange={handleInputChange}
                  error={errors.timeAndDate}
                />
                <Remove onClick={resetStates} />
              </Grid>
            </>
          ) : null}
          {Object.values(selectedComponents).every((x) => x === false) ? (
            <Grid item xs={12} container justify="center">
              <Grid item xs={2}>
                {" "}
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
                <MenuItem onClick={handleSelectedClose} id="time">
                  Specify time to wait before deleting
                </MenuItem>
                <MenuItem onClick={handleSelectedClose} id="timeDate">
                  Specify exact time and date
                </MenuItem>
              </Menu>
            </Grid>
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
  );
}
