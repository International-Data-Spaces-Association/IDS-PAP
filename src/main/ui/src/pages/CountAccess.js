import React, { useState } from "react";
import { Grid, Button, Paper } from "@material-ui/core";
import PageHeader from "../components/PageHeader";
import EqualizerIcon from "@material-ui/icons/Equalizer";
import { useStyle } from "../components/Style";
import Input from "../components/controls/Input";
import { useHistory } from "react-router-dom";
import Form from "../components/controls/Form";
import IdentifyPolicy from "../components/controls/IdentifyPolicy";
import { OdrlPolicy } from "../components/backend/OdrlPolicy";
import Submit from "../components/backend/Submit";
import Title from "../components/controls/Title";

const selected_components = {
  page: "CountAccess",
};

export default function CountAccess() {
  const classes = useStyle();
  const [errors, setErrors] = useState({});
  const valueHook = useState(OdrlPolicy);
  const history = useHistory();

  var state = {page: "CreatePolicy"};

  const handleSubmit = (e) => {
    const [values, setValues] = valueHook
    Submit(
      "/policy/CountAccessPolicyForm",
      values,
      selected_components,
      setErrors,
      history,
      e
    );
  };
  return (
    <div className={classes.page}>
      <Form onSubmit={handleSubmit}>
        <PageHeader
          title="This policy restricts the numeric count of using your data by a specified IDS data consumer."
          icon={<EqualizerIcon />}
        />
        <Grid container>
          <Grid item xs={12}>
            <Paper elevation={3} className={classes.paperWithoutRemoveBtn}>
              <IdentifyPolicy
                valueHook={valueHook}
                errors={errors}
              />

              <Grid container>
                <Title label="Count" />
                <Input
                  name="counter"
                  placeholder="0"
                  valueHook={valueHook}
                  errors={errors}
                />
              </Grid>
            </Paper>
          </Grid>
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
