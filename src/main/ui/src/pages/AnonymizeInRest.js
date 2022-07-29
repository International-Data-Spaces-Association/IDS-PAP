import React, { useState } from "react";
import { Grid, Button, Paper } from "@material-ui/core";
import PageHeader from "../components/PageHeader";
import EnhancedEncryptionIcon from "@material-ui/icons/EnhancedEncryption";
import { useStyle } from "../components/Style";
import { useHistory } from "react-router-dom";
import Form from "../components/controls/Form";
import IdentifyPolicy from "../components/controls/IdentifyPolicy";
import { OdrlPolicy } from "../components/backend/OdrlPolicy";
import Submit from "../components/backend/Submit";
import { useLocation } from "react-router-dom";

export default function AnonymizeInRest() {
  var initialValues = OdrlPolicy()
  var stateLocal = useLocation().state;
  
  if (stateLocal !== undefined) {
    initialValues = stateLocal;
  }

  const classes = useStyle();
  const valueHook = useState(initialValues);
  const [errors, setErrors] = useState({});
  const history = useHistory();
  const selected_components = {
    page: "AnonymizeInRest",
  };

  const handleSubmit = (e) => {
    const values = valueHook[0]
    Submit(
      "/policy/AnonymizeInRestPolicyForm",
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
          title="The assumption is that your data is stored in a database on consumer side.
                This policy requests a specified IDS data consumer to anonymize your stored data."
          icon={<EnhancedEncryptionIcon />}
        />
        <Grid container>
          <Grid item xs={12}>
            <Paper elevation={3} className={classes.paperWithoutRemoveBtn}>
              <IdentifyPolicy
                valueHook={valueHook}
                errors={errors}
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
