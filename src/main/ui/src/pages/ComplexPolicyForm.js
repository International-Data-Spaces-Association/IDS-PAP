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
import DistributeDataComplex from "../components/controls/DistributeDataComplex";
export default function ComplexPolicyForm() {
  
  const selected_components = {
    type: "restrictions",
    order: [],
    availableComponents: [
      { id: "application", name: "Application", isVisible: false },
      { id: "connector", name: "Connector", isVisible: false },
      { id: "counter", name: "Counter", isVisible: false },
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

  const selected_distribute_components = {
    type: "distributeData",
    order: [],
    availableComponents: [
      { id: "distribute", name: "Distribute Data", isVisible: false },
    ],
  };


  const selected_preduties_components = {
    type: "preduties",
    order: [],
    availableComponents: [
      { id: "anonymizeTransit", name: "Anonymize in Transit", isVisible: false },
      { id: "anonymizeInRest", name: "Anonymize in Rest", isVisible: false },
    ],
  };

  const selected_postduties_components = {
    type: "postduties",
    order: [],
    availableComponents: [
      { id: "delete", name: "Delete Data After", isVisible: false },
      { id: "log", name: "Log Data Usage", isVisible: false },
      { id: "inform", name: "Inform Party", isVisible: false },
    ],
  };

  const selected_delete_data_components = {
    dda_duration: false,
    dda_timeDate: false,
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
  const [selectedDistributeDataComponents, setSelectedDistributeDataComponents] = useState(selected_distribute_components);

  const [selectedPostDuties, setSelectedPostDuties] = useState(
    selected_postduties_components
  );

  const [selectedDeleteComponents, setSelectedDeleteComponents] = useState(
    selected_delete_data_components
  );

  const handleInputChange = (e) => {
    setValues({ ...values, [e.target.name]: e.target.value });
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
    var state = {page: "CreatePolicy"};
    selectedComponents.availableComponents.forEach(function (item) {
      console.log(item.id)
      state[item.id] = item.isVisible;
    });
    selectedPreDuties.availableComponents.forEach(function (item) {
      state[item.id] = item.isVisible;
    });
    selectedPostDuties.availableComponents.forEach(function (item) {
      state[item.id] = item.isVisible;
    });
    selectedDistributeDataComponents.availableComponents.forEach(function (item) {
      state[item.id] = item.isVisible;
    });
    for (const [key, value] of Object.entries(selectedDeleteComponents)) {
      state[key] = value;
    }
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
    OdrlPolicy.preduties_anomInRest ="";
    setValues({ ...OdrlPolicy });
    setSelectedComponents({ ...selected_components });
    setSelectedPostDuties({ ...selected_postduties_components });
    setSelectedPreDuties({ ...selected_preduties_components });
    setSelectedDistributeDataComponents({...selected_distribute_components});
  };

  const removeComponent = (type, id) => {
    const states = [selectedComponents, selectedDistributeDataComponents, selectedPostDuties, selectedPreDuties];
    const setStates = [
      setSelectedComponents,
      setSelectedDistributeDataComponents,
      setSelectedPostDuties,
      setSelectedPreDuties,
    ];
    states.forEach(function (state, index) {
      if (state.type === type) {
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
    ids.forEach(function (id) {
      if (OdrlPolicy[id] instanceof Array) {
        values[id] = [""];
        OdrlPolicy[id] = [""];
      } else {
        values[id] = "";
      }
    });
    console.log(ids,  values[ids])
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
              <Paper elevation={3} className={classes.paperWithoutRemoveBtn}>
                <IdentifyPolicy
                  classes={classes}
                  values={values}
                  handleInputChange={handleInputChange}
                  errors={errors}
                />
                <AddRestrictions
                  values={values}
                  setValues={setValues}
                  errors={errors}
                  handleInputChange={handleInputChange}
                  selectedComponents={selectedComponents}
                  removeComponent={removeComponent}
                  removeEnteredData={removeEnteredData}
                  classes={classes}
                  type={"preduties"}
                />
              </Paper>
            </Grid>

            <Grid item xs={12}>
              <DistributeDataComplex
                values={values}
                setValues={setValues}
                errors={errors}
                handleInputChange={handleInputChange}
                removeEnteredData={removeEnteredData}
                selectedComponents={selectedDistributeDataComponents}
                setSelectedComponents={setSelectedDistributeDataComponents}
                removeComponent={removeComponent}
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
                setSelectedComponents={setSelectedDistributeDataComponents}
                selectedDeleteComponents={selectedDeleteComponents}
                setSelectedDeleteComponents={setSelectedDeleteComponents}
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
