import React, { useState } from "react";
import { Grid, Menu, Button } from "@material-ui/core";
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
      { id: "application", name: "Application", isVisible: true },
      { id: "connector", name: "Connector", isVisible: true },
      { id: "duration", name: "Duration", isVisible: true },
      { id: "endTime", name: "EndTime", isVisible: true },
      { id: "event", name: "Event", isVisible: true },
      { id: "interval", name: "Interval", isVisible: true },
      { id: "location", name: "Location", isVisible: true },
      { id: "payment", name: "Payment", isVisible: true },
      { id: "purpose", name: "Purpose", isVisible: true },
      { id: "role", name: "Role", isVisible: true },
      { id: "securityLevel", name: "SecurityLevel", isVisible: true },
      { id: "state", name: "State", isVisible: true },
    ],
  };


  const classes = useStyle();
  const [values, setValues] = useState(OdrlPolicy);
  const [errors, setErrors] = useState({});
  const history = useHistory();
  const [selectedComponents, setSelectedComponents] = useState(
    selected_components
  );
  const [anchorEl, setAnchorEl] = useState(null);

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };
  const removeEnteredData = (id1, id2) => {
  };

  const resetStates = () => {
    setSelectedComponents({ ...selected_components });

    values.location = "";
    values.application = "";
    values.connector = "";
    values.state = "";
    values.role = "";
    values.securityLevel = "";
    values.purpose = "";
    values.event = "";
    values.restrictTimeIntervalStart = "";
    values.restrictTimeIntervalEnd = "";
    values.restrictEndTime = "";
    values.payment = "";
    values.price = "";
    values.specifyBeginTime = "";
    values.durationHour = "";
    values.durationDay = "";
    values.durationMonth = "";
    values.durationYear = "";
  };
  const handleInputChange = (e) => {
    setValues({ ...values, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    const dict = selectedComponents.availableComponents;
    var state = {}
    dict.forEach(function (item) {
      state[item.id] = !item.isVisible
    });
    Submit(
      "/policy/ProvideAccess",
      values,
      state,
      setErrors,
      history,
      e
    );
  };

  return (
    <div className={classes.page}>
      <Form onSubmit={handleSubmit}>
        <PageHeader
          title="This policy gives permission to a specified IDS data consumer to use your data."
          icon={<LockOpenIcon />}
        />
        <Grid container spacing={1}>
          <IdentifyPolicy
            values={values}
            handleInputChange={handleInputChange}
            errors={errors}
          />
          <FormComponents
            selectedComponents={selectedComponents}
            values={values}
            errors={errors}
            handleInputChange={handleInputChange}
            removeComponent={resetStates}
            removeEnteredData={removeEnteredData}
          />

          {Object.values(selectedComponents.availableComponents).some(
            (x) => x.isVisible === true
          ) ? (
            <Grid item xs={12} container justify="center">
              <Grid item xs={2}>
                <Button
                  color="primary"
                  aria-controls="simple-menu"
                  aria-haspopup="true"
                  onClick={handleClick}
                  id="Add Component"
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
                <MenuItems
                  selectedComponents={selectedComponents}
                  setAnchorEl={setAnchorEl}
                />
              </Menu>
            </Grid>
          ) : null}

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
