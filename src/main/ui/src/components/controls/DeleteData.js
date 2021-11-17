import React, { useState } from "react";
import { Grid, Menu, MenuItem, Button, makeStyles } from "@material-ui/core";
import { useStyle } from "../Style";
import Input from "./Input";
import Title from "./Title";
import Date from "./Date";
import Remove from "./Remove";

export default function DeleteData(props) {
  const {
    handleInputChange,
    values,
    errors,
    selectedComponents,
    removeEnteredData,
    setSelectedComponents,
    xs = 12,
    sm = 12,
    md = 12,
    type = "",
    seperator = true,
  } = props;

  const selected_delete_data_components = {
    duration: false,
    timeDate: false,
  };
  const [selectedDeleteComponents, setSelectedDeleteComponents] = useState(
    selected_delete_data_components
  );
  const [anchorEl, setAnchorEl] = useState(null);
  const classes = useStyle();

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };

  const handleSelectedClose = (e) => {
    setSelectedComponents({ [e.target.id]: true });
    setAnchorEl(null);
  };

  const resetStates = (e) => {
    setSelectedComponents({
      duration: false,
      timeDate: false,
    });
    removeEnteredData([
      type + "durationYear",
      type + "durationMonth",
      type + "durationDay",
      type + "durationHour",
      type + "timeAndDate",
    ]);
  };
  return (
    <>
      <Grid item xs={xs} sm={sm} md={md}>
        {selectedComponents.duration ? (
          <>
            <Grid container className={classes.paperSubContainer}>
              <Title
                seperator={seperator}
                label="Specify a time duration that the application has to wait before deleting the data"
              />
              <Grid container xs={11} spacing={2}>
                <Input
                  name={type + "durationYear"}
                  label="Year"
                  value={values[type + "durationYear"]}
                  placeholder="e.g. 2021"
                  onChange={handleInputChange}
                  error={errors[type + "durationYear"]}
                  sm={11}
                  md={3}
                />
                <Input
                  name={type + "durationMonth"}
                  label="Month"
                  value={values[type + "durationMonth"]}
                  placeholder="e.g. 01"
                  onChange={handleInputChange}
                  error={errors[type + "durationMonth"]}
                  sm={11}
                  md={3}
                />
                <Input
                  name={type + "durationDay"}
                  label="Day"
                  value={values[type + "durationDay"]}
                  placeholder="e.g. 01"
                  onChange={handleInputChange}
                  error={errors[type + "durationDay"]}
                  sm={11}
                  md={3}
                />
                <Input
                  name={type + "durationHour"}
                  label="Hour"
                  value={values[type + "durationHour"]}
                  placeholder="e.g. 10"
                  onChange={handleInputChange}
                  error={errors[type + "durationHour"]}
                  sm={11}
                  md={3}
                />
              </Grid>
              <Remove onClick={resetStates} />
            </Grid>
          </>
        ) : null}

        {selectedComponents.timeDate ? (
          <>
            <Grid container className={classes.paperSubContainer}>
              <Title
                seperator={seperator}
                label="Specify an exact date and time to delete the data:"
              />
              <Grid container xs={11} spacing={2}>
                <Date
                  name={type + "timeAndDate"}
                  label="Date and Time*"
                  defaultValue=""
                  onChange={handleInputChange}
                  error={errors[type + "timeAndDate"]}
                  sm={11}
                  md={5}
                />
              </Grid>
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
      </Grid>
    </>
  );
}
