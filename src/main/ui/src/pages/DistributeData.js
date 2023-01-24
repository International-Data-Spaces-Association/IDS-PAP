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
import { useLocation } from "react-router-dom";

const selected_components = {
  page: "DistributeData",
};
export default function DistributeData() {
  var initialValues = OdrlPolicy();
  var stateLocal = useLocation().state;

  if (stateLocal !== undefined) {
    initialValues = stateLocal;
  }

  const classes = useStyle();
  const valueHook = useState(initialValues);
  const [errors, setErrors] = useState({});
  const history = useHistory();

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

  const handleSubmit = (e) => {
    const values = valueHook[0];
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
      <Form>
        <PageHeader
          title="This policy allows a specified IDS data consumer to distribute your data only if the data is encoded (compressed or encrypted)."
          icon={<ShareIcon />}
        />

        <Grid container>
          <Grid item xs={12}>
            <Paper elevation={3} className={classes.paperWithoutRemoveBtn}>
              <IdentifyPolicy valueHook={valueHook} errors={errors} />

              <Grid container>
                <Title label="Artifact State" />
                <ItemPicker
                  name="artifactState"
                  defaultValue=""
                  ItemList={artifact_state_list}
                  valueHook={valueHook}
                  errors={errors}
                />
              </Grid>

              <Grid container>
                <Title label="Policy to be sent to the third party" />
                <Input
                  name="policy"
                  placeholder="e.g. http://example.com/policy/third-party-policy"
                  valueHook={valueHook}
                  errors={errors}
                />
              </Grid>
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
