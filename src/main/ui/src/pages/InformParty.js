import React, { useState } from "react";
import { Grid, Button, Paper } from "@material-ui/core";
import PageHeader from "../components/PageHeader";
import NotificationsActiveIcon from "@material-ui/icons/NotificationsActive";
import { useStyle } from "../components/Style";
import { useHistory } from "react-router-dom";
import Form from "../components/controls/Form";
import IdentifyPolicy from "../components/controls/IdentifyPolicy";
import { OdrlPolicy } from "../components/backend/OdrlPolicy";
import Submit from "../components/backend/Submit";
import InformParty from "../components/controls/InformParty";
import { useLocation } from "react-router-dom";

const selected_components = {
  page: "InformParty",
};

export default function LogAccess() {
  var initialValues = OdrlPolicy();
  var stateLocal = useLocation().state;

  if (stateLocal !== undefined) {
    initialValues = stateLocal;
  }

  const classes = useStyle();
  const valueHook = useState(initialValues);
  const [errors, setErrors] = useState({});
  const history = useHistory();

  const handleSubmit = (e) => {
    const values = valueHook[0];

    Submit(
      "/policy/InformPolicyForm",
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
          title="This policy allows a specified IDS data consumer to use your data and requests notifications on each data usage."
          icon={<NotificationsActiveIcon />}
        />
        <Grid container>
          <Grid item xs={12}>
            <Paper elevation={3} className={classes.paperWithoutRemoveBtn}>
              <IdentifyPolicy valueHook={valueHook} errors={errors} />
              <InformParty
                valueHook={valueHook}
                errors={errors}
                prefix="postduties_"
              />
            </Paper>
          </Grid>
          <Grid item xs={2}>
            <Button
              variant="contained"
              color="primary"
              className={classes.saveBtn}
              prefix="submit"
              id="Save"
              onClick={handleSubmit}
            >
              Submit
            </Button>
          </Grid>
        </Grid>
      </Form>
    </div>
  );
}
