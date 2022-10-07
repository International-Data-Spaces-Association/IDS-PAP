/**
 * @file This file contains components for the policy header.
 * @author Tom Kollmer 
 */
import React from "react";
import { Grid } from "@material-ui/core";
import Input from "../controls/Input";
import ItemPicker from "../controls/ItemPicker";
import {
  policy_types,
  data_consumers,
} from "../controls/InitialFieldListValues";
import Title from "../controls/Title";

/**
 * This function defines the header of all policies and is used by all pages that create a new policy.
 * @component
 * @param {object} valueHook access to the user input
 * @param {object} errors contains all error messages
 * @returns component
 */
export default function IdentifyPolicy(props) {
  const {valueHook, errors } = props;
  const values = valueHook[0];

  return (
    <Grid container spacing={2}>
      <Grid item xs={4}>
        <Title label="Policy Type*" separator={false} />
        <ItemPicker
          name="policyType"
          defaultValue="Agreement"
          ItemList={policy_types}
          valueHook={valueHook}
          errors={errors}
          xs={12}
          sm={12}
          md={12}
        />
      </Grid>

      {values.policyType === "Agreement" || values.policyType === "Request" ? (
        <>
          <Grid item xs={4}>
            <Title label="Data Consumer*" separator={false} />
            <ItemPicker
              name="consumer"
              defaultValue=""
              ItemList={data_consumers}
              valueHook={valueHook}
              errors={errors}
              xs={12}
              sm={12}
              md={12}
            />
          </Grid>
        </>
      ) : null}

      {values.policyType === "Agreement" || values.policyType === "Offer" ? (
        <>
          <Grid item xs={4}>
          <Title label="Data Provider*" separator={false} />
          <Input
            name="provider"
            placeholder="My party"
            valueHook={valueHook}
            errors={errors}
            xs={12}
            sm={12}
            md={12}
          />
          </Grid>
        </>
      ) : null}

      <Grid item xs={12}>
        <Title label="Data URI*" separator={false} />
        <Input
          name="target"
          placeholder="e.g. http://example.com/ids/data/production-plan"
          valueHook={valueHook}
          errors={errors}
          xs={12}
          sm={12}
          md={12}
        />
      </Grid>
    </Grid>
  );
}
