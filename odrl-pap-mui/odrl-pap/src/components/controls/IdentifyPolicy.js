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
  const { values, handleInputChange, errors } = props;

  const handleInputChangeLocal = (e) => {
    if (e.target.value === "Offer") values.consumer = "";
    if (e.target.value === "Request") values.provider = "";
    handleInputChange(e);
  };
  return (
    <>
      <Grid container>
        <Title label="Policy Type*" seperator={false}/>
        <ItemPicker
          name="policyType"
          defaultValue="Agreement"
          ItemList={policy_types}
          onChange={handleInputChangeLocal}
          error={errors.policyType}
        />
      </Grid>
      <Grid container>
        <Title label="Data URI*" seperator={false} />
        <Input
          name="target"
          value={values.target}
          placeholder="e.g. http://ontologi.es/place/DE"
          onChange={handleInputChange}
          error={errors.target}
        />
      </Grid>
      {values.policyType === "Agreement" || values.policyType === "Offer" ? (
        <>
          <Grid container>
            <Title label="Data Provider*" seperator={false}/>
            <Input
              name="provider"
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
          <Grid container>
            <Title label="Data Consumer*" seperator={false}/>
            <ItemPicker
              name="consumer"
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
