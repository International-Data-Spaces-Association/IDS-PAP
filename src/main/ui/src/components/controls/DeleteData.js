import React, { useState } from "react";
import { Grid, Menu, MenuItem, Button } from "@material-ui/core";
import { useStyle } from "../Style";
import Input from "./Input";
import Title from "./Title";
import Date from "./Date";
import Remove from "./Remove";

export default function DeleteData(props) {
  const {
    valueHook,
    errors,
    selectedComponents,
    removeEnteredData,
    setSelectedComponents,
    xs = 12,
    sm = 12,
    md = 12,
    prefix = "",
    seperator = true,
  } = props;
  const [anchorEl, setAnchorEl] = useState(null);
  const classes = useStyle();

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };

  const handleSelectedClose = (e) => {
    var obj = {
      [prefix + "duration"]: false,
      [prefix + "timeDate"]: false,
    }
    obj[e.target.id] = true;
    setSelectedComponents(obj);
    setAnchorEl(null);
  };

  const resetStates = (e) => {
    setSelectedComponents({
      [prefix + "duration"]: false,
      [prefix + "timeDate"]: false,
    });
    removeEnteredData([
      prefix + "durationYear",
      prefix + "durationMonth",
      prefix + "durationDay",
      prefix + "durationHour",
      prefix + "timeAndDate",
    ]);
  };

  return (
    <>
      <Grid item xs={xs} sm={sm} md={md}>
        {selectedComponents[prefix + "duration"] ? (
          <>
            <Grid container className={classes.paperSubContainer}>
              <Title
                seperator={seperator}
                label="Specify a time duration that the application has to wait before deleting the data"
              />
              <Grid container xs={11} spacing={2}>
                <Input
                  name={prefix + "durationYear"}
                  label="Year"
                  placeholder="e.g. 2021"
                  valueHook={valueHook}
                  errors={errors}
                  sm={11}
                  md={3}
                />
                <Input
                  name={prefix + "durationMonth"}
                  label="Month"
                  placeholder="e.g. 01"
                  valueHook={valueHook}
                  errors={errors}
                  sm={11}
                  md={3}
                />
                <Input
                  name={prefix + "durationDay"}
                  label="Day"
                  placeholder="e.g. 01"
                  valueHook={valueHook}
                  errors={errors}
                  sm={11}
                  md={3}
                />
                <Input
                  name={prefix + "durationHour"}
                  label="Hour"
                  placeholder="e.g. 10"
                  valueHook={valueHook}
                  errors={errors}
                  sm={11}
                  md={3}
                />
              </Grid>
              <Remove onClick={resetStates} />
            </Grid>
          </>
        ) : null}

        {selectedComponents[prefix + "timeDate"] ? (
          <>
            <Grid container className={classes.paperSubContainer}>
              <Title
                seperator={seperator}
                label="Specify an exact date and time to delete the data:"
              />
              <Grid container xs={11} spacing={2}>
                <Date
                  name={prefix + "timeAndDate"}
                  label="Date and Time*"
                  defaultValue=""
                  valueHook={valueHook}
                  errors={errors}
                  sm={11}
                  md={5}
                />
              </Grid>
              <Remove onClick={resetStates} />
            </Grid>
          </>
        ) : null}
        {Object.values(selectedComponents).every((x) => x === false) ? (
          <Grid item xs={12} container justifyContent="center">
            <Grid item xs={2}>
              {" "}
              <Button
                color="primary"
                aria-controls="simple-menu"
                aria-haspopup="true"
                onClick={handleClick}
                className={classes.addBtn}
                id="Add Delete Component"
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
              <MenuItem onClick={handleSelectedClose} id= {prefix + "duration"}>
                Specify a period to wait before deleting
              </MenuItem>
              <MenuItem onClick={handleSelectedClose} id= {prefix + "timeDate"}>
                Specify exact time and date
              </MenuItem>
            </Menu>
          </Grid>
        ) : null}
      </Grid>
    </>
  );
}
