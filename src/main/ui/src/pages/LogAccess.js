import React, { useState } from "react";
import { Grid, Button } from "@material-ui/core";
import PageHeader from "../components/PageHeader";
import AssignmentIcon from "@material-ui/icons/Assignment";
import { useStyle } from "../components/Style";
import Input from "../components/controls/Input";
import { useHistory } from "react-router-dom";
import Form from "../components/controls/Form";
import IdentifyPolicy from "../components/controls/IdentifyPolicy";
import { OdrlPolicy } from "../components/backend/OdrlPolicy";
import Submit from "../components/backend/Submit";
import ItemPicker from "../components/controls/ItemPicker";
import Title from "../components/controls/Title";
import { log_level_list } from "../components/controls/InitialFieldListValues";
const selected_components = {
  logAccess: true,
};

export default function LogAccess() {
  const classes = useStyle();
  const [values, setValues] = useState(OdrlPolicy);
  const [errors, setErrors] = useState({});
  const history = useHistory();
  const [selectedComponents] = useState(selected_components);

  const handleInputChange = (e) => {
    setValues({ ...values, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    Submit(
      "/policy/LogAccessPolicyForm",
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
          title="This policy gives permission to a specified IDS data consumer to use your data and requests to log the usage information on a specified system device."
          icon={<AssignmentIcon />}
        />
        <Grid container spacing={1}>
          <IdentifyPolicy
            values={values}
            handleInputChange={handleInputChange}
            errors={errors}
          />

          <Grid container>
            <Title label="Log Level" />
            <ItemPicker
              name="logLevel"
              defaultValue=""
              ItemList={log_level_list}
              onChange={handleInputChange}
              error={errors.logLevel}
            />
          </Grid>

          <Grid container>
            <Title label="System Device" />
            <Input
              name="systemDevice"
              value={values.systemDevice}
              placeholder="e.g. http://example.com/system:ESN-database"
              onChange={handleInputChange}
              error={errors.systemDevice}
            />
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
