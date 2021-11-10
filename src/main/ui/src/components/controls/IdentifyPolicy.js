import React from "react";
import { Grid } from "@material-ui/core";
import Input from "../controls/Input";
import ItemPicker from "../controls/ItemPicker";
import {
  policy_types,
  data_consumers,
} from "../controls/InitialFieldListValues";
import Title from "../controls/Title";

export default function IdentifyPolicy(props) {
  const {classes, values, handleInputChange, errors } = props;

  const handleInputChangeLocal = (e) => {
    if (e.target.value === "Offer") values.consumer = "";
    if (e.target.value === "Request") values.provider = "";
    handleInputChange(e);
  };
  return (
    <Grid container spacing={2} justify="center" className={classes.container}>
      <Grid item xs={3}>
        <Title label="Policy Type*" seperator={false} />
        <ItemPicker
          name="policyType"
          value={values.policyType}
          defaultValue="Agreement"
          ItemList={policy_types}
          onChange={handleInputChangeLocal}
          error={errors.policyType}
          xs={12}
          sm={12}
          md={12}
        />
      </Grid>

      {values.policyType === "Agreement" || values.policyType === "Request" ? (
        <>
          <Grid item xs={3}>
            <Title label="Data Consumer*" seperator={false} />
            <ItemPicker
              name="consumer"
              defaultValue=""
              value={values.consumer}
              ItemList={data_consumers}
              onChange={handleInputChange}
              error={errors.consumer}
              xs={12}
              sm={12}
              md={12}
            />
          </Grid>
        </>
      ) : null}

      {values.policyType === "Agreement" || values.policyType === "Offer" ? (
        <>
          <Grid item xs={5}>
          <Title label="Data Provider*" seperator={false} />
          <Input
            name="provider"
            value={values.provider}
            placeholder="My party"
            onChange={handleInputChange}
            error={errors.provider}
            xs={12}
            sm={12}
            md={12}
          />
          </Grid>
        </>
      ) : null}

      <Grid item xs={11}>
        <Title label="Data URI*" seperator={false} />
        <Input
          name="target"
          value={values.target}
          placeholder="e.g. http://example.com/ids-data/production-plan"
          onChange={handleInputChange}
          error={errors.target}
          xs={12}
          sm={12}
          md={12}
        />
      </Grid>
    </Grid>
  );
}
