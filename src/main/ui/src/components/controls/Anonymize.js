import React from "react";
import { Grid } from "@material-ui/core";
import { modifier_list } from "./InitialFieldListValues";
import Input from "./Input";
import ItemPicker from "./ItemPicker";

export default function Anonymize(props) {
  const {
    valueHook,
    errors,
    xs = 12,
    sm = 12,
    md = 12,
  } = props;
  return (
    <>
      <Grid item xs={xs} sm={sm} md={md}>
        <Grid container>
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
                  "Value that you want to replace the field with"
                }
                placeholder="e.g. XXXX"
                valueHook={valueHook}
                errors={errors}
              />
            </Grid>
          </>
        ) : (
          (valueHook[0].preduties_valueToChange = "")
        )}

        <Grid container>
          <Input
            label="Field (jsonPathQuery) you want to modify"
            name="preduties_fieldToChange"
            placeholder="e.g. $.name"
            valueHook={valueHook}
            errors={errors}
          />
        </Grid>
      </Grid>
    </>
  );
}
