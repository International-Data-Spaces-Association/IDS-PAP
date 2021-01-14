import React from "react";
import { Grid } from "@material-ui/core";
import Input from "../controls/Input";
import ItemPicker from "../controls/ItemPicker";
import {
  policy_types,
  data_consumers,
} from "../controls/InitialFieldListValues";
export default function IdentifyPolicy(props) {
  const { values, handleInputChange, errors } = props;

  const handleInputChangeLocal = (e)=> {
    if (e.target.value === "Offer") values.consumer = "";
    if (e.target.value === "Request") values.provider = "";
    handleInputChange(e);
  }
  return (
    <>
      <Grid item xs={11} lg={3}>
        <ItemPicker
          name="policyType"
          label="Policy Type*"
          defaultValue="Agreement"
          ItemList={policy_types}
          onChange={handleInputChangeLocal}
          error={errors.policyType}
        />
      </Grid>
      <Grid item xs={11} lg={8}>
        <Input
          name="target"
          label="Data URI*"
          value={values.target}
          placeholder="e.g. http://ontologi.es/place/DE"
          onChange={handleInputChange}
          error={errors.target}
        />
      </Grid>
      {values.policyType === "Agreement" || values.policyType === "Offer" ? (
        <>
          <Grid item xs={11} lg={11}>
            <Input
              name="provider"
              label="Data Provider*"
              value={values.provider}
              placeholder="My party"
              onChange={handleInputChange}
              error={errors.provider}
            />
          </Grid>
        </>
      ) : null}

      {values.policyType === "Agreement" || values.policyType === "Request" ? (
        <>
          <Grid item xs={11} lg={11}>
            <ItemPicker
              name="consumer"
              label="Data Consumer*"
              defaultValue=""
              ItemList={data_consumers}
              onChange={handleInputChange}
              error={errors.consumer}
            />
          </Grid>
        </>
      ) : null}
    </>
  );
}
