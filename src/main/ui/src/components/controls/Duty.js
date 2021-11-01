import React, { useState } from "react";
import { Grid, MenuItem, Menu, Button } from "@material-ui/core";
import InformParty from "./InformParty";
import DeleteData from "./DeleteData";
import LogData from "./LogData";
import MenuItems from "./MenuItems";
import Title from "./Title";
import Remove from "./Remove";
export default function Duty(props) {
  const {
    selectedComponents,
    values,
    setValues,
    errors,
    handleInputChange,
    removeComponent,
    removeEnteredData,
    classes,
    name = "",
    type = "",
  } = props;

  const selected_delete_data_components = {
    duration: false,
    timeDate: false,
  };
  const [selectedDeleteComponents, setSelectedDeleteComponents] = useState(
    selected_delete_data_components
  );

  const [anchorEl, setAnchorEl] = useState(null);
  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const addAll = () => {
    const dict = selectedComponents.availableComponents;
    dict.forEach(function (item) {
      if (item.isVisible) {
        item.isVisible = false;
        selectedComponents.order.push(item.id);
      }
    });
  };
  const components = selectedComponents.order.map((c) => {
    switch (c) {
      case "log":
        return () => (
          <>
            <Title label="Log Data Usage" seperator={false} xs={11} />
            <Grid container key={"log_" + type}>
              <Grid item xs={1} />
              <LogData
                handleInputChange={handleInputChange}
                values={values}
                errors={errors}
                xs={10}
                sm={10}
                md={10}
                type={type + "_"}
              />
              <Remove
                onClick={() => {
                  removeComponent(type, "log");
                  removeEnteredData([type + "_logLevel", type + "_systemDevice"]);
                }}
              />
            </Grid>
          </>
        );
      case "delete":
        return () => (
          <>
            <Title label="Delete Data After" seperator={false} xs={11} />
            <Grid container key={"delete_" + type}>
              <Grid item xs={1} />
              <DeleteData
                handleInputChange={handleInputChange}
                errors={errors}
                values={values}
                removeEnteredData={removeEnteredData}
                selectedComponents={selectedDeleteComponents}
                setSelectedComponents={setSelectedDeleteComponents}
                xs={10}
                sm={10}
                md={10}
                type={type + "_"}
              />
              <Remove
                onClick={() => {
                  setSelectedDeleteComponents({
                    duration: false,
                    timeDate: false,
                })
                  removeComponent(type, "delete");
                  removeEnteredData([
                    type + "_durationYear",
                    type + "_durationMonth",
                    type + "_durationDay",
                    type + "_durationHour",
                    type + "_timeAndDate"]
                  );
                }}
              />
            </Grid>
          </>
        );
      case "inform":
        return () => (
          <>
            <Title label="Inform Party" seperator={false} xs={11} />
            <Grid container key={"inform_" + type}>
              <Grid item xs={1} />
              <InformParty
                handleInputChange={handleInputChange}
                errors={errors}
                values={values}
                xs={10}
                sm={10}
                md={10}
                type={type + "_"}
              />
              <Remove
                onClick={() => {
                  removeComponent(type, "inform");
                  removeEnteredData([
                    type + "_notificationLevel",
                    type + "_informedParty"]
                  );
                }}
              />
            </Grid>
          </>
        );
      default:
        return <></>;
    }
  });

  return (
    <>
      {components.map((c) => c())}

      {Object.values(selectedComponents.availableComponents).some(
        (x) => x.isVisible === true
      ) ? (
        <>
          <Grid item xs={12} container justify="center">
            <Grid item xs={3}>
              <Button
                color="primary"
                aria-controls="simple-menu"
                aria-haspopup="true"
                onClick={handleClick}
                className={classes.addBtn}
                id="Add Component"
              >
                {name}
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
        </>
      ) : null}
    </>
  );
}
