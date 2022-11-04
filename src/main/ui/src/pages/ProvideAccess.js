import React, { useState } from "react";
import { Grid, Menu, Button, Paper } from "@material-ui/core";
import PageHeader from "../components/PageHeader";
import { useStyle } from "../components/Style";
import LockOpenIcon from "@material-ui/icons/LockOpen";
import { useHistory } from "react-router-dom";
import Form from "../components/controls/Form";
import IdentifyPolicy from "../components/controls/IdentifyPolicy";
import {
  OdrlPolicy,
  recreateSelectedCompFromJson,
} from "../components/backend/OdrlPolicy";
import Submit from "../components/backend/Submit";

import FormComponents from "../components/FormComponents";
import MenuItems from "../components/controls/MenuItems";
import { useLocation } from "react-router-dom";
import SplitButtons from "../components/controls/SplitButtons";

export default function ProvideAccess() {
  const selected_components = {
    prefix: "components",
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

  var initialValues = OdrlPolicy();
  initialValues = recreateSelectedCompFromJson(
    useLocation().state,
    initialValues,
    selected_components
  );

  const classes = useStyle();
  const valueHook = useState(initialValues);
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

  const removeComponent = (id) => {
    const prefix = "components";
    const states = [selectedComponents];
    const setStates = [setSelectedComponents];
    states.forEach(function (state, index) {
      if (state.prefix === prefix) {
        const dict = state.availableComponents;
        const list = state.order;
        const setState = setStates[index];

        dict.forEach(function (item, key) {
          if (item.id === id) {
            const obj = JSON.parse(JSON.stringify(state));
            obj.order = list.filter((e) => e !== id);
            obj.availableComponents[key].isVisible = false;
            setState({ ...obj });
          }
        });
      }
    });
  };

  const removeEnteredData = (ids) => {
    const values = valueHook[0];
    ids.forEach(function (id) {
      if (values[id] instanceof Array) {
        values[id] = [""];
      } else {
        values[id] = "";
      }
    });
  };

  const [selectedLanguage, setSelectedLanguage] = React.useState(1);

  const handleSubmit = (e) => {
    const values = valueHook[0];
    const dict = selectedComponents.availableComponents;
    var state = { page: "ProvideAccess" };
    dict.forEach(function (item) {
      state[item.id] = item.isVisible;
    });
    Submit(
      "/policy/ProvideAccessPolicyForm",
      values,
      state,
      setErrors,
      history,
      e
    );
  };

  return (
    <div className={classes.page}>
      <Form>
        <PageHeader
          title="This policy gives permission to a specified IDS data consumer to use your data."
          icon={<LockOpenIcon />}
        />

        <Grid container spacing={1}>
          <Grid item xs={12}>
            <Paper elevation={3} className={classes.paperWithoutRemoveBtn}>
              <IdentifyPolicy valueHook={valueHook} errors={errors} />
              <FormComponents
                selectedComponents={selectedComponents}
                valueHook={valueHook}
                errors={errors}
                removeComponent={removeComponent}
                removeEnteredData={removeEnteredData}
              />
              {Object.values(selectedComponents.availableComponents).every(
                (x) => x.isVisible === false
              ) ? (
                <Grid item xs={12} container justifyContent="center">
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
          <Grid item xs={3} xm={2}>
                <SplitButtons
                  valueHook={valueHook}
                  selectedLanguage={selectedLanguage}
                  setSelectedLanguage={setSelectedLanguage}
                  handleClick={handleSubmit}
                />
              </Grid>
        </Grid>
      </Form>
    </div>
  );
}
