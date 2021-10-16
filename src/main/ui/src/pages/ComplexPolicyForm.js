import React, { useState } from "react";
import { Grid, MenuItem, Menu, Button } from "@material-ui/core";
import { useStyle } from "../components/Style";
import { useHistory } from "react-router-dom";
import PageHeader from "../components/PageHeader";
import PostAddIcon from "@material-ui/icons/PostAdd";
import Form from "../components/controls/Form";
import IdentifyPolicy from "../components/controls/IdentifyPolicy";
import { OdrlPolicy, OdrlPolicyZero } from "../components/backend/OdrlPolicy";
import Submit from "../components/backend/Submit";
import DeleteIcon from "@material-ui/icons/Delete";

import FormComponents from "../components/FormComponents";
import MenuItems from "../components/controls/MenuItems";

export default function ComplexPolicyForm() {
  const selected_components = {
    order: [],
    availableComponents: [
      { id: "application", name: "Application", isVisible: true },
      { id: "connector", name: "Connector", isVisible: true },
      { id: "counter", name: "Counter", isVisible: true },
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
  const [selectedComponents, setSelectedComponents] =
    useState(selected_components);
  const [anchorEl, setAnchorEl] = useState(null);

  const handleInputChange = (e) => {
    setValues({ ...values, [e.target.name]: e.target.value });
  };

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleSubmit = (e) => {
    const dict = selectedComponents.availableComponents;
    var state = {};
    dict.forEach(function (item) {
      state[item.id] = !item.isVisible;
    });
    Submit("/policy/ComplexPolicyForm", values, state, setErrors, history, e);
  };

  const resetStates = () => {
    setValues({ ...OdrlPolicyZero });
    setSelectedComponents({ ...selected_components });
  };

  const removeComponent = (id) => {
    const dict = selectedComponents.availableComponents;
    const list = selectedComponents.order;
    dict.forEach(function (item) {
      if (item.id === id) {
        item.isVisible = true;
      }
    });
    selectedComponents.order = list.filter((e) => e !== id);
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
  const removeEnteredData = (id1, id2) => {
    setValues({
      ...values,
      [id1]: OdrlPolicyZero[id1],
      [id2]: OdrlPolicyZero[id2],
    });
  };

  return (
    <>
      <div className={classes.page}>
        <Form onSubmit={handleSubmit}>
          <PageHeader
            title="This policy gives permission to a specified IDS data consumer to use your data."
            icon={<PostAddIcon />}
          />
          <Grid container>
            <IdentifyPolicy
              values={values}
              handleInputChange={handleInputChange}
              errors={errors}
            />
            <FormComponents
              selectedComponents={selectedComponents}
              values={values}
              setValues={setValues}
              errors={errors}
              handleInputChange={handleInputChange}
              removeComponent={removeComponent}
              removeEnteredData={removeEnteredData}
            />
            {Object.values(selectedComponents.availableComponents).some(
              (x) => x.isVisible === true
            ) ? (
              <>
                <Grid item xs={12} container justify="center">
                  <Grid item xs={2}>
                    <Button
                      color="primary"
                      aria-controls="simple-menu"
                      aria-haspopup="true"
                      onClick={handleClick}
                      className={classes.addBtn}
                      id="Add Component"
                    >
                      Add Restriction
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

            <Grid container>
              <Grid item xs={2} xm={1}>
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
              <Grid item xs={7} xm={9} />

              <Grid item xs={2} xm={1}>
                <Button
                  variant="contained"
                  color="secondary"
                  className={classes.saveBtn}
                  onClick={resetStates}
                >
                  <DeleteIcon />
                </Button>
              </Grid>
            </Grid>
          </Grid>
        </Form>
      </div>
    </>
  );
}
