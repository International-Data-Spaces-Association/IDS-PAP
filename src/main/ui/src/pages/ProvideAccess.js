import React, { useState } from "react";
import { Grid, Menu, Button, Paper } from "@material-ui/core";
import PageHeader from "../components/PageHeader";
import { useStyle } from "../components/Style";
import LockOpenIcon from "@material-ui/icons/LockOpen";
import { useHistory } from "react-router-dom";
import Form from "../components/controls/Form";
import IdentifyPolicy from "../components/controls/IdentifyPolicy";
import { OdrlPolicy } from "../components/backend/OdrlPolicy";
import Submit from "../components/backend/Submit";

import FormComponents from "../components/FormComponents";
import MenuItems from "../components/controls/MenuItems";

export default function ProvideAccess() {
  const selected_components = {
    order: [],
    availableComponents: [
      { id: "application", name: "Application", isVisible: false },
      { id: "connector", name: "Connector", isVisible: false },
      { id: "duration", name: "Duration", isVisible: false },
      { id: "endTime", name: "EndTime", isVisible: false },
      { id: "event", name: "Event", isVisible: false },
      { id: "interval", name: "Interval", isVisible: false },
      { id: "location", name: "Location", isVisible: false },
      { id: "payment", name: "Payment", isVisible: false },
      { id: "purpose", name: "Purpose", isVisible: false },
      { id: "role", name: "Role", isVisible: false },
      { id: "securityLevel", name: "SecurityLevel", isVisible: false },
      { id: "state", name: "State", isVisible: false },
    ],
  };

  const classes = useStyle();
  const valueHook = useState(OdrlPolicy);
  const [errors, setErrors] = useState({});
  const history = useHistory();
  const [selectedComponents, setSelectedComponents] =
    useState(selected_components);
  const [anchorEl, setAnchorEl] = useState(null);

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };

  const resetStates = () => {
    setSelectedComponents({ ...selected_components });
  };

  const handleSubmit = (e) => {
    const [values, setValues] = valueHook
    const dict = selectedComponents.availableComponents;
    var state = {};
    var state = {page: "ProvideAccess"};
    dict.forEach(function (item) {
      state[item.id] = item.isVisible;
    });
    Submit("/policy/ProvideAccess", values, state, setErrors, history, e);
  };

  return (
    <div className={classes.page}>
      <Form onSubmit={handleSubmit}>
        <PageHeader
          title="This policy gives permission to a specified IDS data consumer to use your data."
          icon={<LockOpenIcon />}
        />

        <Grid container spacing={1}>
          <Grid item xs={12}>
            <Paper elevation={3} className={classes.paperWithoutRemoveBtn}>
              <IdentifyPolicy
                valueHook={valueHook}
                errors={errors}
              />
              <FormComponents
                selectedComponents={selectedComponents}
                valueHook={valueHook}
                errors={errors}
                removeComponent={resetStates}
              />
              {Object.values(selectedComponents.availableComponents).every(
                (x) => x.isVisible === false
              ) ? (
                <Grid item xs={12} container justify="center">
                  <Grid item xs={2}>
                    <Button
                      color="primary"
                      aria-controls="simple-menu"
                      aria-haspopup="true"
                      onClick={handleClick}
                      id="Add Restriction"
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
                    onClose={handleClose}
                  >
                    <MenuItems
                      selectedComponents={selectedComponents}
                      setAnchorEl={setAnchorEl}
                    />
                  </Menu>
                </Grid>
              ) : null}
            </Paper>
          </Grid>

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
      </Form>
    </div>
  );
}
