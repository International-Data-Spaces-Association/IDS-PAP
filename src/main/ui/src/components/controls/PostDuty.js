/**
 * @file This contains the post duty component 
 * @author Tom Kollmer 
 */

import React, { useState } from "react";
import { Grid, MenuItem, Menu, Button, Paper } from "@material-ui/core";
import InformParty from "./InformParty";
import DeleteData from "./DeleteData";
import LogData from "./LogData";
import MenuItems from "./MenuItems";
import Title from "./Title";
import Remove from "./Remove";
import { Typography } from "@material-ui/core";

/**
 * Components for the post duty data component
 * @component
 * @param {object} valueHook access to the user input
 * @param {object} errors contains all error messages
 * @param {object} selectedComponents contains all selected components
 * @param {object} selectedDeleteComponents contains all selected delete components
 * @param {func} setSelectedDeleteComponents is called to set delete components
 * @param {func} removeComponent is called to remove components
 * @param {func} removeEnteredData is called to remove entered data
 * @param {object} classes contains the css definitions
 * @param {string} name that should be used for the components
 * @param {string} title that should be used for the components
 * @param {string} prefix that should be used for the components
 * @returns component
 */
export default function PostDuty(props) {
  const {
    valueHook,
    errors,
    selectedComponents,
    selectedDeleteComponents,
    setSelectedDeleteComponents,
    removeComponent,
    removeEnteredData,
    classes,
    name = "",
    title = "",
    prefix = "",
  } = props;
  const [anchorEl, setAnchorEl] = useState(null);
  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const addAll = () => {
    const dict = selectedComponents.availableComponents;
    dict.forEach(function (item) {
      if (!item.isVisible) {
        item.isVisible = true;
        selectedComponents.order.push(item.id);
      }
    });
  };

  const components = selectedComponents.order.map((c) => {
    switch (c) {
      case "log":
        return () => (
          <>
            <Grid container key={"log_" + prefix}>
              <Title label="Log Data Usage" separator={false} xs={12} />
              <Grid container xs={11} spacing={2}>
                <LogData
                  valueHook={valueHook}
                  errors={errors}
                  xs={12}
                  sm={12}
                  md={12}
                  prefix={prefix + "_"}
                />
              </Grid>
              <Remove
                onClick={() => {
                  removeComponent(prefix, "log");
                  removeEnteredData([
                    prefix + "_logLevel",
                    prefix + "_systemDevice",
                  ]);
                }}
              />
            </Grid>
          </>
        );
      case "delete":
        return () => (
          <>
            <Grid container key={"delete_" + prefix}>
              <Title label="Delete Data After" separator={true} xs={12} />
              <Grid container xs={11} spacing={2}>
                <DeleteData
                  valueHook={valueHook}
                  removeEnteredData={removeEnteredData}
                  selectedComponents={selectedDeleteComponents}
                  setSelectedComponents={setSelectedDeleteComponents}
                  xs={11}
                  sm={11}
                  md={11}
                  separator={false}
                  errors={errors}
                  prefix={prefix + "_"}
                />
              </Grid>
              <Remove
                onClick={() => {
                  setSelectedDeleteComponents({
                    duration: false,
                    timeDate: false,
                  });
                  removeComponent(prefix, "delete");
                  removeEnteredData([
                    prefix + "_durationYear",
                    prefix + "_durationMonth",
                    prefix + "_durationDay",
                    prefix + "_durationHour",
                    prefix + "_timeAndDate",
                  ]);
                }}
              />
            </Grid>
          </>
        );
      case "inform":
        return () => (
          <>
            {" "}
            <Grid container key={"inform_" + prefix}>
              <Title label="Inform Party" separator={false} xs={11} />
              <Grid container xs={11} spacing={2}>
                <InformParty
                  valueHook={valueHook} 
                  errors={errors}
                  xs={12}
                  sm={12}
                  md={12}
                  prefix={prefix + "_"}
                />
              </Grid>
              <Remove
                onClick={() => {
                  removeComponent(prefix, "inform");
                  removeEnteredData([
                    prefix + "_notificationLevel",
                    prefix + "_informedParty",
                  ]);
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
      {selectedComponents.order.length > 0 ? (
        <>
          <Grid item xs={11} container>
            <Typography
              variant="h5"
              component="div"
              id="subTitle2"
              className={classes.subtitle}
            >
              {title}
            </Typography>
          </Grid>
          <Paper elevation={3} className={classes.paper}>
            {components.map((c) => c())}

            {Object.values(selectedComponents.availableComponents).some(
              (x) => x.isVisible === false
            ) ? (
              <>
                <Grid item xs={12} container justifyContent="center">
                  <Grid item xs={5}>
                    <Button
                      color="primary"
                      aria-controls="simple-menu"
                      aria-haspopup="true"
                      onClick={handleClick}
                      className={classes.addBtn}
                      id="Add Component"
                    >
                      {"Add " + name}
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
          </Paper>
        </>
      ) : (
        <>
           {Object.values(selectedComponents.availableComponents).some(
            (x) => x.isVisible === false
          ) ? (
            <>
              <Grid item xs={12} container justifyContent="center">
                <Grid item xs={3}>
                  <Button
                    color="primary"
                    aria-controls="simple-menu"
                    aria-haspopup="true"
                    onClick={handleClick}
                    className={classes.addBtn}
                    id="Add Component"
                  >
                    {"Add " + name}
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
      )}
    </>
  );
}
