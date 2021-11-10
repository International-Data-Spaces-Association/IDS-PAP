import React, { useState } from "react";
import { Grid, Button, Paper } from "@material-ui/core";
import { useStyle } from "../components/Style";
import { useHistory } from "react-router-dom";
import PageHeader from "../components/PageHeader";
import PostAddIcon from "@material-ui/icons/PostAdd";
import Form from "../components/controls/Form";
import IdentifyPolicy from "../components/controls/IdentifyPolicy";
import { OdrlPolicy } from "../components/backend/OdrlPolicy";
import Submit from "../components/backend/Submit";
import DeleteIcon from "@material-ui/icons/Delete";
import PostDuty from "../components/controls/PostDuty";
import PreDuty from "../components/controls/PreDuty";
import AddRestrictions from "../components/AddRestrictions";

export default function ComplexPolicyForm() {
  const selected_components = {
    type: "restrictions",
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

  const selected_preduties_components = {
    type: "preduties",
    order: [],
    availableComponents: [
      { id: "anonymizeRest", name: "Anonymize in Rest", isVisible: true },
      { id: "anonymizeTransit", name: "Anbonymize in Transit", isVisible: true },
    ],
  };

  const selected_postduties_components = {
    type: "postduties",
    order: [],
    availableComponents: [
      { id: "delete", name: "Delete Data After", isVisible: true },
      { id: "log", name: "Log Data Usage", isVisible: true },
      { id: "inform", name: "Inform Party", isVisible: true },
    ],
  };

  const classes = useStyle();
  const [values, setValues] = useState(OdrlPolicy);
  const [errors, setErrors] = useState({});
  const history = useHistory();
  const [selectedComponents, setSelectedComponents] =
    useState(selected_components);
  const [selectedPreDuties, setSelectedPreDuties] = useState(
    selected_preduties_components
  );
  const [selectedPostDuties, setSelectedPostDuties] = useState(
    selected_postduties_components
  );

  const handleInputChange = (e) => {
    setValues({ ...values, [e.target.name]: e.target.value });
    console.log(values)
  };

  const handleSubmit = (e) => {
    OdrlPolicy.location_input = [""];
    OdrlPolicy.application_input = [""];
    OdrlPolicy.connector_input = [""];
    OdrlPolicy.role_input = [""];
    OdrlPolicy.purpose_input = [""];
    OdrlPolicy.event_input = [""];
    OdrlPolicy.state_input = [""];
    OdrlPolicy.securityLevel_input = [""];
    const dict = selectedComponents.availableComponents;
    var state = {};
    dict.forEach(function (item) {
      state[item.id] = !item.isVisible;
    });
    Submit("/policy/ComplexPolicyForm", values, state, setErrors, history, e);
  };

  const resetStates = () => {
    OdrlPolicy.location_input = [""];
    OdrlPolicy.application_input = [""];
    OdrlPolicy.connector_input = [""];
    OdrlPolicy.role_input = [""];
    OdrlPolicy.purpose_input = [""];
    OdrlPolicy.event_input = [""];
    OdrlPolicy.state_input = [""];
    OdrlPolicy.securityLevel_input = [""];
    setValues({ ...OdrlPolicy });
    setSelectedComponents({ ...selected_components });
    setSelectedPostDuties({ ...selected_postduties_components });
    setSelectedPreDuties({ ...selected_preduties_components });
  };

  const removeComponent = (type, id) => {
    const states = [selectedComponents, selectedPostDuties, selectedPreDuties];
    const setStates = [
      setSelectedComponents,
      setSelectedPostDuties,
      setSelectedPreDuties,
    ];
    states.forEach(function (state, index) {
      if (state.type == type) {
        const dict = state.availableComponents;
        const list = state.order;
        const setState = setStates[index];

        dict.forEach(function (item, key) {
          if (item.id === id) {
            const obj = JSON.parse(JSON.stringify(state));
            obj.order = list.filter((e) => e !== id);
            obj.availableComponents[key].isVisible = true;
            setState({ ...obj });
          }
        });
      }
    });
  };

  const removeEnteredData = (ids) => {
    ids.forEach(function (id) {
      if (OdrlPolicy[id] instanceof Array) {
        values[id] = [""];
        OdrlPolicy[id] = [""];
      } else {
        values[id] = "";
      }
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
            <Grid item xs={12}>
              <Paper elevation={3}>
                <IdentifyPolicy
                  classes={classes}
                  values={values}
                  handleInputChange={handleInputChange}
                  errors={errors}
                />
              </Paper>
            </Grid>

            <Grid item xs={12}>
                <AddRestrictions
                  selectedComponents={selectedComponents}
                  values={values}
                  setValues={setValues}
                  errors={errors}
                  handleInputChange={handleInputChange}
                  removeComponent={removeComponent}
                  removeEnteredData={removeEnteredData}
                  classes={classes}
                  type={"preduties"}
                />
            </Grid>

            <Grid item xs={12}>
                <PreDuty
                  selectedComponents={selectedPreDuties}
                  values={values}
                  setValues={setValues}
                  errors={errors}
                  handleInputChange={handleInputChange}
                  removeComponent={removeComponent}
                  removeEnteredData={removeEnteredData}
                  classes={classes}
                  name={"Pre Duty"}
                  title={"Pre Duties"}
                  type={"preduties"}
                />
            </Grid>

            <Grid item xs={12}>
                <PostDuty
                  selectedComponents={selectedPostDuties}
                  values={values}
                  setValues={setValues}
                  errors={errors}
                  handleInputChange={handleInputChange}
                  removeComponent={removeComponent}
                  removeEnteredData={removeEnteredData}
                  classes={classes}
                  name={"Post Duty"}
                  title={"Post Duties"}
                  type={"postduties"}
                />
            </Grid>

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
