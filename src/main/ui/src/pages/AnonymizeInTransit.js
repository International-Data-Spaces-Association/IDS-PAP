import React, { useState } from "react";
import { Grid, Button, Paper } from "@material-ui/core";
import PageHeader from "../components/PageHeader";
import EnhancedEncryptionIcon from "@material-ui/icons/EnhancedEncryption";
import { useStyle } from "../components/Style";
import Input from "../components/controls/Input";
import ItemPicker from "../components/controls/ItemPicker";
import { useHistory } from "react-router-dom";
import { modifier_list } from "../components/controls/InitialFieldListValues";
import Form from "../components/controls/Form";
import IdentifyPolicy from "../components/controls/IdentifyPolicy";
import { OdrlPolicy } from "../components/backend/OdrlPolicy";
import Submit from "../components/backend/Submit";
import Title from "../components/controls/Title";
import { useLocation } from "react-router-dom";

const selected_components = {
  page: "AnonymizeInTransit",
};
export default function AnonymizeInTransit() {
  var initialValues = OdrlPolicy()
  var stateLocal = useLocation().state;
  
  if (stateLocal !== undefined) {
    initialValues = stateLocal;
  }

  const classes = useStyle();
  const valueHook = useState(initialValues);
  const [errors, setErrors] = useState({});
  const history = useHistory();

  const handleSubmit = (e) => {
    const values = valueHook[0]
    Submit(
      "/policy/AnonymizeInTransitPolicyForm",
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
          title="This policy gives permission to a specified IDS data consumer to use your data."
          icon={<EnhancedEncryptionIcon />}
        />
        <Grid container>
          <Grid item xs={12}>
            <Paper elevation={3} className={classes.paperWithoutRemoveBtn}>
              <IdentifyPolicy
                valueHook={valueHook}
                errors={errors}
              />

              <Grid container>
                <Title label="The modify duty action defines specific changes to be applied on a field of data." />
                <ItemPicker
                  name="preduties_modifier"
                  label={"Modify action"}
                  defaultValue="Replace modification method"
                  ItemList={modifier_list}
                  valueHook={valueHook}
                  errors={errors}
                />
              </Grid>
              {valueHook[0].preduties_modifier === "idsc:REPLACE" ? (
                <>
                  <Grid container>
                    <Input
                      name="preduties_valueToChange"
                      label={
                        "Enter the value that you want to replace the field with"
                      }
                      placeholder="e.g. XXXX"
                      valueHook={valueHook}
                      errors={errors}
                    />
                  </Grid>
                </>
              ) : (
                (valueHook[0].valueToChange = "")
              )}

              <Grid container>
                <Title label="Enter the field (jsonPathQuery) that you want to modify" />
                <Input
                  name="preduties_fieldToChange"
                  placeholder="e.g. $.name"
                  valueHook={valueHook}
                  errors={errors}
                />
              </Grid>
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
