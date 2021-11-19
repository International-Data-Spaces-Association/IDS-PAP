import React, { useState } from "react";
import {
  Grid,
  MenuItem,
  Menu,
  Button,
  Paper,
} from "@material-ui/core";
import MenuItems from "./MenuItems";
import Title from "./Title";
import Remove from "./Remove";
import { Typography } from "@material-ui/core";
import Anonymize from "./Anonymize";

export default function PreDuty(props) {
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
    title = "",
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
      case "anonymizeTransit":
        return () => (
          <>
            <Grid container key={"anonymizeTransit" + type}>
                  <Title label="Anonymize in Transit" seperator={true} xs={12} />
                <Grid container xs={11} spacing={2}>
                      <Anonymize
                       values={values}
                       handleInputChange={handleInputChange}
                       errors={errors}
                      />
                </Grid>
                <Remove
                    onClick={() => {
                      setSelectedDeleteComponents({
                        duration: false,
                        timeDate: false,
                      });
                      removeComponent(type, "anonymizeTransit");
                      removeEnteredData([
                        type + "_modifier",
                        type + "_valueToChange",
                        type + "_fieldToChange",
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
              (x) => x.isVisible === true
            ) ? (
              <>
                <Grid item xs={12} container >
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
          </Paper>
        </>
      ) : (
        <>
          {Object.values(selectedComponents.availableComponents).some(
            (x) => x.isVisible === true
          ) ? (
            <>
              <Grid item xs={12} container justify="center">
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
        </>
      )}
    </>
  );
}
