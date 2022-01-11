import React from "react";
import { Grid } from "@material-ui/core";
import { modifier_list } from "./InitialFieldListValues";
import Input from "./Input";
import ItemPicker from "./ItemPicker";

export default function Anonymize(props) {
  const {
    handleInputChange,
    values,
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
            onChange={handleInputChange}
            error={errors.preduties_modifier}
          />
        </Grid>
        {values.preduties_modifier === "idsc:REPLACE" ? (
          <>
            <Grid container>
              <Input
                name="preduties_valueToChange"
                label={
                  "Value that you want to replace the field with"
                }
                value={values.preduties_valueToChange}
                placeholder="e.g. XXXX"
                onChange={handleInputChange}
                error={errors.preduties_valueToChange}
              />
            </Grid>
          </>
        ) : (
          (values.preduties_valueToChange = "")
        )}

        <Grid container>
          <Input
            label="Field (jsonPathQuery) you want to modify"
            name="preduties_fieldToChange"
            value={values.preduties_fieldToChange}
            placeholder="e.g. $.name"
            onChange={handleInputChange}
            error={errors.preduties_fieldToChange}
          />
        </Grid>
      </Grid>
    </>
  );
}
