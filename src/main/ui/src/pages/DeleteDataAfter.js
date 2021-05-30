import React, { useState } from "react";
import { Grid, Menu, MenuItem, Button } from "@material-ui/core";
import PageHeader from "../components/PageHeader";
import DeleteIcon from "@material-ui/icons/Delete";
import Input from "../components/controls/Input";
import Date from "../components/controls/Date";
import { useHistory } from "react-router-dom";
import { useStyle } from "../components/Style";
import Form from "../components/controls/Form";
import IdentifyPolicy from "../components/controls/IdentifyPolicy";
import { OdrlPolicy } from "../components/backend/OdrlPolicy";
import Submit from "../components/backend/Submit";
import Remove from "../components/controls/Remove";
import Title from "../components/controls/Title";

export default function DeleteDataAfter() {
  const selected_components = {
    duration: false,
    timeDate: false,
  };
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
    setValues({ ...values,
    timeAndDate: "",
    durationHour: "",
    durationDay: "",
    durationMonth: "",
    durationYear: ""
    });
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
        {selectedComponents.duration ? (
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

          {selectedComponents.duration ? (
            <>
              <Grid container>
                <Title label="Specify a time duration that the application has to wait before deleting the data" />
                <Input
                  name="durationYear"
                  label="Year"
                  value={values.durationYear}
                  placeholder="e.g. 3"
                  onChange={handleInputChange}
                  error={errors.durationYear}
                  sm={11}
                  md={2}
                />
                <Grid item sm={1} />
                <Input
                  name="durationMonth"
                  label="Month"
                  value={values.durationMonth}
                  placeholder="e.g. 3"
                  onChange={handleInputChange}
                  error={errors.durationMonth}
                  sm={11}
                  md={2}
                />
                <Grid item sm={1} />
                <Input
                  name="durationDay"
                  label="Day"
                  value={values.durationDay}
                  placeholder="e.g. 3"
                  onChange={handleInputChange}
                  error={errors.durationDay}
                  sm={11}
                  md={2}
                />
                <Grid item sm={1} />
                <Input
                  name="durationHour"
                  label="Hour"
                  value={values.durationHour}
                  placeholder="e.g. 3"
                  onChange={handleInputChange}
                  error={errors.durationHour}
                  sm={11}
                  md={2}
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
                  sm={11}
                  md={3}
                />
                <Grid item sm={0} md={8} />
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
                  id="Add Component"
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
                <MenuItem onClick={handleSelectedClose} id="duration">
                  Specify a period to wait before deleting
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
                color="secondary"
                className={classes.saveBtn}
                type="submit"
                id="Save"
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
