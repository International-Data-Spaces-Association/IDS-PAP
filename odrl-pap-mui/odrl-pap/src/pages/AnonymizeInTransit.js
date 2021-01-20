import React, { useState } from "react";
import { Grid, Button } from "@material-ui/core";
import PageHeader from "../components/PageHeader";
import EnhancedEncryptionIcon from "@material-ui/icons/EnhancedEncryption";
import { useStyle } from "../components/Style";
import Input from "../components/controls/Input";
import ItemPicker from "../components/controls/ItemPicker";
import { useHistory } from "react-router-dom";
import { modificator_list } from "../components/controls/InitialFieldListValues";
import Form from "../components/controls/Form";
import IdentifyPolicy from "../components/controls/IdentifyPolicy";
import { OdrlPolicy } from "../components/backend/OdrlPolicy";
import Submit from "../components/backend/Submit";
import Title from "../components/controls/Title";

const selected_components = {
  anonymizeInTransit: true,
};
export default function AnonymizeInTransit() {
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
      "/policy/AnonymizeInTransitPolicyForm",
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
          title="This policy gives permission to a specified IDS data consumer to use your data."
          icon={<EnhancedEncryptionIcon />}
        />
        <Grid container spacing={1}>
          <IdentifyPolicy
            values={values}
            handleInputChange={handleInputChange}
            errors={errors}
          />

          <Grid container>
            <Title label="The modification method defines specific changes to be applied on a field of data." />
            <ItemPicker
              name="modificator"
              label={"Modification method"}
              defaultValue="Replace modification method"
              ItemList={modificator_list}
              onChange={handleInputChange}
              error={errors.modificator}
            />
          </Grid>
          {values.modificator === "http://example.com/anonymize/replace" ? (
            <>
              <Grid container>
                <Input
                  name="valueToChange"
                  label={"Enter the value that you want to replace the field with"}
                  value={values.valueToChange}
                  placeholder="e.g. XXXX"
                  onChange={handleInputChange}
                  error={errors.valueToChange}
                />
              </Grid>
            </>
          ) : (
            (values.valueToChange = "")
          )}

          <Grid container>
            <Title label="Enter the field (ids:jsonPath) that you want to modify" />
            <Input
              name="fieldToChange"
              value={values.fieldToChange}
              placeholder="e.g. $.name"
              onChange={handleInputChange}
              error={errors.fieldToChange}
            />
          </Grid>

          <Grid item xs={2}>
            <Button
              variant="contained"
              color="primary"
              className={classes.saveBtn}
              type="submit"
            >
              Save
            </Button>
          </Grid>
        </Grid>
      </Form>
    </div>
  );
}
