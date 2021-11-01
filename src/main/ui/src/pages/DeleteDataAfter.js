import React, { useState } from "react";
import { Grid, Button } from "@material-ui/core";
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
    duration: false,
    timeDate: false,
  };
  const classes = useStyle();
  const [values, setValues] = useState(OdrlPolicy);
  const [errors, setErrors] = useState({});
  const history = useHistory();
  const [selectedComponents] = useState(selected_components);

  const resetStates = () => {
    for (var key in selectedComponents) {
      if (selectedComponents.hasOwnProperty(key)) {
        selectedComponents[key] = false;
      }
    }
    setValues({ ...values,
    timeAndDate: "",
    durationHour: "",
    durationDay: "",
    durationMonth: "",
    durationYear: ""
    });
  };

  const handleInputChange = (e) => {
    setValues({ ...values, [e.target.name]: e.target.value });
  };
  const handleSubmit = (e) => {
    if (Object.values(selectedComponents).some((x) => x === true)) {
      Submit(
        "/policy/deletePolicyAfterUsage",
        values,
        selectedComponents,
        setErrors,
        history,
        e
      );
    } else {
      Submit(
        "/policy/deletePolicyAfterUsagePeriod",
        values,
        selectedComponents,
        setErrors,
        history,
        e
      );
    }
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
        <Grid container spacing={1}>
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
          selectedComponents={selectedComponents}/>
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
