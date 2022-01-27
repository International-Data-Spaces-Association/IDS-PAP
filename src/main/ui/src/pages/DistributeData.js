import React, { useState } from "react";
import { Grid, Button, Paper } from "@material-ui/core";
import PageHeader from "../components/PageHeader";
import ShareIcon from "@material-ui/icons/Share";
import { useStyle } from "../components/Style";
import Input from "../components/controls/Input";
import { useHistory } from "react-router-dom";
import Form from "../components/controls/Form";
import IdentifyPolicy from "../components/controls/IdentifyPolicy";
import { OdrlPolicy } from "../components/backend/OdrlPolicy";
import Submit from "../components/backend/Submit";
import ItemPicker from "../components/controls/ItemPicker";
import Title from "../components/controls/Title";
import { artifact_state_list } from "../components/controls/InitialFieldListValues";

const selected_components = {
  page: "DistributeData",
};
export default function DistributeData() {
  const classes = useStyle();
  const [values, setValues] = useState(OdrlPolicy);
  const [errors, setErrors] = useState({});
  const history = useHistory();

  const handleInputChange = (e) => {
    setValues({ ...values, [e.target.name]: e.target.value });
  };
  const handleSubmit = (e) => {
    Submit(
      "/policy/DistributePolicyForm",
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
          title="This policy allows a specified IDS data consumer to distribute your data only if the data is encoded (compressed or encrypted)."
          icon={<ShareIcon />}
        />

        <Grid container>
          <Grid item xs={12}>
            <Paper elevation={3} className={classes.paperWithoutRemoveBtn}>
              <IdentifyPolicy
                values={values}
                handleInputChange={handleInputChange}
                errors={errors}
              />

              <Grid container>
                <Title label="Artifact State" />
                <ItemPicker
                  name="artifactState"
                  defaultValue=""
                  ItemList={artifact_state_list}
                  onChange={handleInputChange}
                  error={errors.artifactState}
                />
              </Grid>

              <Grid container>
                <Title label="Policy to be sent to the third party" />
                <Input
                  name="policy"
                  value={values.policy}
                  placeholder="e.g. http://example.com/policy/third-party-policy"
                  onChange={handleInputChange}
                  error={errors.policy}
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
