import React from "react";
import { Grid } from "@material-ui/core";
import { useStyle } from "../Style";
import Input from "../controls/Input";
import ItemPicker from "../controls/ItemPicker";
import {
  policy_types,
  data_consumers,
} from "../controls/InitialFieldListValues";
import Title from "../controls/Title";

export default function IdentifyPolicy(props) {
  const {valueHook, errors } = props;
  const classes = useStyle();
  const [values, setValues] = valueHook

  const handleInputChangeLocal = (e) => {
    if (e.target.value === "Offer") values.consumer = "";
    if (e.target.value === "Request") values.provider = "";
    //handleInputChange(e);
  };

  if (values.policyType === "Offer" || values.policyType === "Request") {
    values.consumer = "";
    values.provider = "";
  }

  
  return (
    <Grid container spacing={2}>
      <Grid item xs={4}>
        <Title label="Policy Type*" seperator={false} />
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
            <Title label="Data Consumer*" seperator={false} />
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
          <Title label="Data Provider*" seperator={false} />
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
        <Title label="Data URI*" seperator={false} />
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
