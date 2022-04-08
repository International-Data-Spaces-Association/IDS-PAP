import React from "react";
import { TextField, MenuItem, Grid } from "@material-ui/core";
import { handleInputChange } from "../controls/Utils";

export default function ItemPicker(props) {
  const {
    name,
    label,
    valueHook,
    defaultValue,
    ItemList,
    overrideValue = null,
    overrideOnChange = null,
    errors = null,
    xs = 11,
    sm = 11,
    md = 11,
  } = props;

  const values = valueHook[0];
  var value = values[name];
  var error = "";
  var onChange = handleInputChange;
  if (value === undefined) {
    value = "";
  }
  if (overrideValue !== null) {
    value= overrideValue;
  }
  if (errors !== null && errors[name] !== undefined) {
    error = errors[name];
  }
  if (overrideOnChange !== null) {
    onChange = overrideOnChange;
  }
  if (values[name]=== undefined) {
    values[name] = "";
  }
  return (
    <>
      <Grid item xs={xs} sm={sm} md={md}>
        <TextField
          select
          name={name}
          label={label}
          value={value}
          defaultValue={defaultValue}
          variant="outlined"
          onChange={(e) => {
            onChange(e, valueHook);
          }}
          InputLabelProps={{
            shrink: true,
          }}
          {...(error && { error: true, helperText: error })}
        >
          {ItemList.map((option) => (
            <MenuItem key={option.value} value={option.value}>
              {option.id}
            </MenuItem>
          ))}
        </TextField>
      </Grid>
    </>
  );
}
