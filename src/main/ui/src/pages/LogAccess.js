import React, { useState } from "react";
import { Grid, Button, Paper } from "@material-ui/core";
import PageHeader from "../components/PageHeader";
import AssignmentIcon from "@material-ui/icons/Assignment";
import { useStyle } from "../components/Style";
import { useHistory } from "react-router-dom";
import Form from "../components/controls/Form";
import IdentifyPolicy from "../components/controls/IdentifyPolicy";
import { OdrlPolicy } from "../components/backend/OdrlPolicy";
import Submit from "../components/backend/Submit";
import LogData from "../components/controls/LogData";
import { useLocation } from "react-router-dom";
import SplitButtons from "../components/controls/SplitButtons";

const selected_components = {
  page: "LogAccess",
};

export default function LogAccess() {
  var initialValues = OdrlPolicy()
  var stateLocal = useLocation().state;
  
  if (stateLocal !== undefined) {
    initialValues = stateLocal;
  }

  const classes = useStyle();
  const valueHook = useState(initialValues);
  const [errors, setErrors] = useState({});
  const history = useHistory();
  const [selectedLanguage, setSelectedLanguage] = React.useState(1);


  const handleSubmit = (e) => {
    const values = valueHook[0]
    Submit(
      "/policy/LogAccessPolicyForm",
      values,
      selected_components,
      setErrors,
      history,
      e
    );
  };

  return (
    <div className={classes.page}>
      <Form>
        <PageHeader
          title="This policy gives permission to a specified IDS data consumer to use your data and requests to log the usage information on a specified system device."
          icon={<AssignmentIcon />}
        />
        <Grid container>
          <Grid item xs={12}>
            <Paper elevation={3} className={classes.paperWithoutRemoveBtn}>
              <IdentifyPolicy
                valueHook = {valueHook}
                errors={errors}
              />
              <LogData
                valueHook = {valueHook}
                errors={errors}
                xs={12}
                sm={12}
                md={12}
                prefix="postduties_"
              />
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
