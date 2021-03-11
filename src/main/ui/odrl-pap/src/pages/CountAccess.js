import React, { useState } from "react";
import { Grid, Button } from "@material-ui/core";
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
  counter: true,
};

export default function CountAccess() {
  document.title = 'Count Access'
  const classes = useStyle();
  const [errors, setErrors] = useState({});
  const [values, setValues] = useState(OdrlPolicy);
  const history = useHistory();
  const [selectedComponents] = useState(selected_components);
  const handleInputChange = (e) => {
    setValues({ ...values, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    Submit(
      "/policy/CountAccessPolicyForm",
      values,
      selectedComponents,
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
        <Grid container spacing={1}>
          <IdentifyPolicy
            values={values}
            handleInputChange={handleInputChange}
            errors={errors}
          />

          <Grid container>
            <Title label="Count" />
            <Input
              name="counter"
              value={values.counter}
              placeholder="0"
              onChange={handleInputChange}
              error={errors.counter}
            />
          </Grid>

          <Grid item xs={2}>
            <Button
              variant="contained"
              color="primary"
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
