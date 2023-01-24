import React, { useState } from "react";
import { Grid, Button, Paper } from "@material-ui/core";
import PageHeader from "../components/PageHeader";
import DeleteIcon from "@material-ui/icons/Delete";
import { useHistory } from "react-router-dom";
import { useStyle } from "../components/Style";
import Form from "../components/controls/Form";
import IdentifyPolicy from "../components/controls/IdentifyPolicy";
import { OdrlPolicy } from "../components/backend/OdrlPolicy";
import Submit from "../components/backend/Submit";
import DeleteData from "../components/controls/DeleteData";
import { useLocation } from "react-router-dom";

export default function DeleteDataAfter() {
  const selected_components = {
    postduties_duration: false,
    postduties_timeDate: false,
  };

  var initialValues = OdrlPolicy()
  var stateLocal = useLocation().state;
  if (stateLocal !== undefined) {
    initialValues = stateLocal;
    if (initialValues.postduties_durationYear !== "") {
      selected_components.postduties_duration = true
    }
    if (initialValues.postduties_timeAndDate !== "") {
      selected_components.postduties_timeDate = true
    }
  }
  
  const classes = useStyle();
  const valueHook = useState(initialValues);
  const [errors, setErrors] = useState({});
  const history = useHistory();
  const [selectedComponents, setSelectedComponents] = useState(selected_components);

  const handleSubmit = (e) => {
    const values = valueHook[0]
    var state = {page: "DeleteDataAfter",
      postduties_duration: false,
      postduties_timeDate: false,};

    if (selectedComponents.postduties_duration) {
      state.postduties_duration = true;
    }
    if (selectedComponents.postduties_timeDate) {
      state.postduties_timeDate = true;
    }
    console.log(values)
    if (Object.values(selectedComponents).some((x) => x === true)) {
      Submit(
        "/policy/deletePolicyAfterUsage",
        values,
        state,
        setErrors,
        history,
        e
      );
    } else {
      Submit(
        "/policy/deletePolicyAfterUsagePeriod",
        values,
        state,
        setErrors,
        history,
        e
      );
    }
  };
  const removeEnteredData = (ids) => {
    ids.forEach(function (id) {
      if (OdrlPolicy[id] instanceof Array) {
        valueHook[0][id] = [""];
        OdrlPolicy[id] = [""];
      } else {
        valueHook[0][id] = "";
      }
    });
  };
  const handleClickSetODRL = (event, index) => {
    const values = valueHook[0];

    values["language"] = "ODRL" 
    handleSubmit();
  };

  const handleClickSetIDS = (event, index) => {
    const values = valueHook[0];

    values["language"] = "IDS" 
    handleSubmit();
  };
  return (
    <div className={classes.page}>
      <Form>
        {Object.values(selectedComponents).every((x) => x === false) ? (
          <PageHeader
            title="The assumption is that your data is stored in a database on consumer side."
            icon={<DeleteIcon />}
          />
        ) : null}
        {selectedComponents.duration ? (
          <PageHeader
            title="The assumption is that your data is stored in a database on consumer side.
                    This policy allows the Data Consumer to use your data and requests to delete it immediately after.s"
            icon={<DeleteIcon />}
          />
        ) : null}
        {selectedComponents.timeDate ? (
          <PageHeader
            title="The assumption is that your data is stored in a database on consumer side.
                    This policy requests to delete your data after a specified period of time."
            icon={<DeleteIcon />}
          />
        ) : null}
        <Grid container>
          <Grid item xs={12}>
            <Paper elevation={3} className={classes.paperWithoutRemoveBtn}>
            <IdentifyPolicy
                valueHook={valueHook}
                errors={errors}
              />

              <DeleteData
                valueHook={valueHook}
                errors={errors}
                selectedComponents={selectedComponents}
                removeEnteredData={removeEnteredData}
                setSelectedComponents={setSelectedComponents}
                prefix = "postduties_"
              />
            </Paper>
          </Grid>
          <Grid item xs={2} xm={1}>
                <Button
                  variant="contained"
                  color="primary"
                  className={classes.saveBtn}
                  onClick={handleClickSetIDS}
                >
                  generate IDS policy
                </Button>
              </Grid>

              <Grid item xs={2} xm={1}>
                <Button
                  variant="contained"
                  color="primary"
                  className={classes.saveBtn}
                  onClick={handleClickSetODRL}
                >
                  generate ODRL policy
                </Button>
              </Grid>
        </Grid>
      </Form>
    </div>
  );
}
