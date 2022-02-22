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
export default function DeleteDataAfter() {
  const selected_components = {
    postduties_duration: false,
    postduties_timeDate: false,
  };
  
  const classes = useStyle();
  const [values, setValues] = useState(OdrlPolicy);
  const [errors, setErrors] = useState({});
  const history = useHistory();
  const [selectedComponents, setSelectedComponents] = useState(selected_components);

  const resetStates = () => {
    for (var key in selectedComponents) {
      if (selectedComponents.hasOwnProperty(key)) {
        selectedComponents[key] = false;
      }
    }
    setValues({
      ...values,
      timeAndDate: "",
      durationHour: "",
      durationDay: "",
      durationMonth: "",
      durationYear: "",
    });
  };

  const handleInputChange = (e) => {
    setValues({ ...values, [e.target.name]: e.target.value });
  };
  const handleSubmit = (e) => {
    var state = {page: "DeleteDataAfter",
      postduties_duration: false,
      postduties_timeDate: false,};

    if (selectedComponents.postduties_duration) {
      state.postduties_duration = true;
    }
    if (selectedComponents.postduties_timeDate) {
      state.postduties_timeDate = true;
    }
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
        values[id] = [""];
        OdrlPolicy[id] = [""];
      } else {
        values[id] = "";
      }
    });
  };
  return (
    <div className={classes.page}>
      <Form onSubmit={handleSubmit}>
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
                values={values}
                handleInputChange={handleInputChange}
                errors={errors}
                resetStates={resetStates}
              />

              <DeleteData
                handleInputChange={handleInputChange}
                errors={errors}
                values={values}
                selectedComponents={selectedComponents}
                removeEnteredData={removeEnteredData}
                setSelectedComponents={setSelectedComponents}
                type = "postduties_"
              />
            </Paper>
          </Grid>
          <Grid item xs={12}>
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
        </Grid>
      </Form>
    </div>
  );
}
